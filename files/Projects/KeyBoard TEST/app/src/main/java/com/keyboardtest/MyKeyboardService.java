package com.keyboardtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MyKeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
	
	private MyKeyboardView keyboardView;
	private Keyboard keyboardEnglish, keyboardArabic, keyboardNumbers, keyboardSymbols, keyboardArabicShift;
	private boolean isCaps = false;
	private boolean isArabic = false;
	private boolean isArabicShift = false;
	private Vibrator vibrator;
	private AudioManager audioManager;
	private SharedPreferences preferences;
	
	private TextView unicodeButton;
	private LinearLayout unicodeInputContainer;
	private EditText unicodeInput;
	private ImageButton unicodeCloseButton;
	
	private static String savedUnicodeText = "";
	
	private static final int KEYCODE_LANGUAGE_SWITCH = -101;
	private static final int KEYCODE_SYMBOLS = -2;
	private static final int KEYCODE_ALPHA = -204;
	
	
	@Override
	public View onCreateInputView() {
		preferences = getSharedPreferences("keyboard_prefs", Context.MODE_PRIVATE);
		LinearLayout root = (LinearLayout) getLayoutInflater().inflate(R.layout.keyboard_container, null);
		
		keyboardView = root.findViewById(R.id.keyboard_view);
		unicodeButton = root.findViewById(R.id.unicode_button);
		unicodeInputContainer = root.findViewById(R.id.unicode_input_container);
		unicodeInput = root.findViewById(R.id.unicode_input);
		unicodeCloseButton = root.findViewById(R.id.unicode_close_button);
		
		unicodeInput.setText(savedUnicodeText);
		
		initializeKeyboards();
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		setupUnicodeBarListeners();
		
		keyboardView.setOnKeyboardActionListener(this);
		keyboardView.setPreviewEnabled(preferences.getBoolean("preview", true));
		
		return root;
	}
	
	private void setupUnicodeBarListeners() {
		unicodeButton.setOnClickListener(v -> {
			unicodeButton.setVisibility(View.GONE);
			unicodeInputContainer.setVisibility(View.VISIBLE);
		});
		unicodeCloseButton.setOnClickListener(v -> {
			unicodeInputContainer.setVisibility(View.GONE);
			unicodeButton.setVisibility(View.VISIBLE);
			unicodeInput.setText("");
		});
		unicodeInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				savedUnicodeText = s.toString();
				InputConnection ic = getCurrentInputConnection();
				if (ic != null) {
					String processedText = reverseArabicText(s.toString());
					ic.beginBatchEdit();
					ic.deleteSurroundingText(Integer.MAX_VALUE, Integer.MAX_VALUE);
					ic.commitText(processedText, 1);
					ic.endBatchEdit();
				}
			}
			@Override
			public void afterTextChanged(Editable s) {}
		});
	}
	
	@Override
	public void onStartInputView(EditorInfo info, boolean restarting) {
		super.onStartInputView(info, restarting);
		switch (info.inputType & EditorInfo.TYPE_MASK_CLASS) {
			case EditorInfo.TYPE_CLASS_NUMBER:
			case EditorInfo.TYPE_CLASS_DATETIME:
			case EditorInfo.TYPE_CLASS_PHONE:
			switchToNumbers();
			break;
			default:
			isArabic = preferences.getBoolean("last_used_arabic", false);
			switchToAlpha();
			break;
		}
		if (unicodeInputContainer != null && unicodeButton != null) {
			unicodeInputContainer.setVisibility(View.GONE);
			unicodeButton.setVisibility(View.VISIBLE);
		}
	}
	
	private void initializeKeyboards() {
		keyboardEnglish = new Keyboard(this, R.xml.keyboard_qwerty);
		keyboardArabic = new Keyboard(this, R.xml.keyboard_arabic);
		keyboardArabicShift = new Keyboard(this, R.xml.keyboard_arabic_shift);
		keyboardNumbers = new Keyboard(this, R.xml.keyboard_numbers);
		keyboardSymbols = new Keyboard(this, R.xml.keyboard_symbols);
	}
	
	@Override
	public void onKey(int primaryCode, int[] keyCodes) {
		InputConnection ic = getCurrentInputConnection();
		if (ic == null && unicodeInputContainer.getVisibility() != View.VISIBLE) return;
		if (preferences.getBoolean("vibration", true)) playVibration();
		if (preferences.getBoolean("sound", true)) playSound(primaryCode);
		if (unicodeInputContainer.getVisibility() == View.VISIBLE) {
			handleUnicodeInput(primaryCode);
			} else {
			handleAppInput(primaryCode, ic);
		}
	}
	
	private void handleAppInput(int primaryCode, InputConnection ic) {
		switch (primaryCode) {
			case Keyboard.KEYCODE_DELETE: handleDelete(ic); break;
			case Keyboard.KEYCODE_SHIFT: handleShift(); break;
			case Keyboard.KEYCODE_DONE: handleEnter(ic); break;
			case 32: ic.commitText(" ", 1); break;
			case KEYCODE_LANGUAGE_SWITCH: switchLanguage(); break;
			case KEYCODE_SYMBOLS: switchToSymbols(); break;
			case KEYCODE_ALPHA: switchToAlpha(); break;
			default: handleCharacter(primaryCode, ic); break;
		}
	}
	
	private void handleUnicodeInput(int primaryCode) {
		Editable editable = unicodeInput.getText();
		int start = unicodeInput.getSelectionStart();
		switch (primaryCode) {
			case Keyboard.KEYCODE_DELETE:
			if (start > 0) editable.delete(start - 1, start);
			break;
			case Keyboard.KEYCODE_DONE:
			unicodeCloseButton.performClick();
			break;
			default:
			char code = (char) primaryCode;
			editable.insert(start, String.valueOf(code));
			break;
		}
	}
	
	private String reverseArabicText(String text) {
		String originalText = text.replace("لا", "ﻻ");
		String reversed = new StringBuilder(originalText).reverse().toString();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < reversed.length(); i++) {
			char currentChar = reversed.charAt(i);
			int originalIndex = originalText.length() - 1 - i;
			char originalPrev = (originalIndex > 0) ? originalText.charAt(originalIndex - 1) : ' ';
			char originalNext = (originalIndex < originalText.length() - 1) ? originalText.charAt(originalIndex + 1) : ' ';
			if (currentChar == 'ﻻ') {
				if (isArabicChar(originalPrev) && connectsAfter(originalPrev)) {
					result.append('\uFEFC');
					} else {
					result.append('\uFEFB');
				}
				} else {
				char convertedChar = getAppropriateForm(currentChar, originalPrev, originalNext);
				result.append(convertedChar);
			}
		}
		return result.toString();
	}
	private char getAppropriateForm(char c, char prev, char next) {
		if (!isArabicChar(c)) return c;
		boolean connectsWithPrev = isArabicChar(prev) && connectsAfter(prev);
		boolean connectsWithNext = isArabicChar(next) && connectsAfter(c);
		if (connectsWithPrev && connectsWithNext) return getMedialForm(c);
		else if (connectsWithPrev) return getFinalForm(c);
		else if (connectsWithNext) return getInitialForm(c);
		else return getIsolatedForm(c);
	}
	private boolean isArabicChar(char c) {
		return c >= '\u0600' && c <= '\u06FF' || c >= '\uFE70' && c <= '\uFEFF';
	}
	private boolean connectsAfter(char c) {
		if (!isArabicChar(c)) return false;
		String nonConnectingChars = "ادذرزوؤآأإىة";
		return nonConnectingChars.indexOf(c) == -1 && c != 'ﻻ';
	}
	private char getIsolatedForm(char c) {
		switch (c) {
			case 'ا': return '\uFE8D'; case 'أ': return '\uFE83'; case 'إ': return '\uFE87';
			case 'آ': return '\uFE81'; case 'ب': return '\uFE8F'; case 'ت': return '\uFE95';
			case 'ث': return '\uFE99'; case 'ج': return '\uFE9D'; case 'ح': return '\uFEA1';
			case 'خ': return '\uFEA5'; case 'د': return '\uFEA9'; case 'ذ': return '\uFEAB';
			case 'ر': return '\uFEAD'; case 'ز': return '\uFEAF'; case 'س': return '\uFEB1';
			case 'ش': return '\uFEB5'; case 'ص': return '\uFEB9'; case 'ض': return '\uFEBD';
			case 'ط': return '\uFEC1'; case 'ظ': return '\uFEC5'; case 'ع': return '\uFEC9';
			case 'غ': return '\uFECD'; case 'ف': return '\uFED1'; case 'ق': return '\uFED5';
			case 'ك': return '\uFED9'; case 'ل': return '\uFEDD'; case 'م': return '\uFEE1';
			case 'ن': return '\uFEE5'; case 'ه': return '\uFEE9'; case 'و': return '\uFEED';
			case 'ي': return '\uFEF1'; case 'ى': return '\uFEEF'; case 'ة': return '\uFE93';
			case 'ؤ': return '\uFE85'; case 'ئ': return '\uFE89'; case 'ء': return '\uFE80';
			default: return c;
		}
	}
	private char getInitialForm(char c) {
		switch (c) {
			case 'ب': return '\uFE91'; case 'ت': return '\uFE97'; case 'ث': return '\uFE9B';
			case 'ج': return '\uFE9F'; case 'ح': return '\uFEA3'; case 'خ': return '\uFEA7';
			case 'س': return '\uFEB3'; case 'ش': return '\uFEB7'; case 'ص': return '\uFEBB';
			case 'ض': return '\uFEBF'; case 'ط': return '\uFEC3'; case 'ظ': return '\uFEC7';
			case 'ع': return '\uFECB'; case 'غ': return '\uFECF'; case 'ف': return '\uFED3';
			case 'ق': return '\uFED7'; case 'ك': return '\uFEDB'; case 'ل': return '\uFEDF';
			case 'م': return '\uFEE3'; case 'ن': return '\uFEE7'; case 'ه': return '\uFEEB';
			case 'ي': return '\uFEF3'; case 'ئ': return '\uFE8B';
			default: return getIsolatedForm(c);
		}
	}
	private char getMedialForm(char c) {
		switch (c) {
			case 'ب': return '\uFE92'; case 'ت': return '\uFE98'; case 'ث': return '\uFE9C';
			case 'ج': return '\uFEA0'; case 'ح': return '\uFEA4'; case 'خ': return '\uFEA8';
			case 'س': return '\uFEB4'; case 'ش': return '\uFEB8'; case 'ص': return '\uFEBC';
			case 'ض': return '\uFEC0'; case 'ط': return '\uFEC4'; case 'ظ': return '\uFEC8';
			case 'ع': return '\uFECC'; case 'غ': return '\uFED0'; case 'ف': return '\uFED4';
			case 'ق': return '\uFED8'; case 'ك': return '\uFEDC'; case 'ل': return '\uFEE0';
			case 'م': return '\uFEE4'; case 'ن': return '\uFEE8'; case 'ه': return '\uFEEC';
			case 'ي': return '\uFEF4'; case 'ئ': return '\uFE8C';
			default: return getFinalForm(c);
		}
	}
	private char getFinalForm(char c) {
		switch (c) {
			case 'ا': return '\uFE8E'; case 'أ': return '\uFE84'; case 'إ': return '\uFE88';
			case 'آ': return '\uFE82'; case 'ب': return '\uFE90'; case 'ت': return '\uFE96';
			case 'ث': return '\uFE9A'; case 'ج': return '\uFE9E'; case 'ح': return '\uFEA2';
			case 'خ': return '\uFEA6'; case 'د': return '\uFEAA'; case 'ذ': return '\uFEAC';
			case 'ر': return '\uFEAE'; case 'ز': return '\uFEB0'; case 'س': return '\uFEB2';
			case 'ش': return '\uFEB6'; case 'ص': return '\uFEBA'; case 'ض': return '\uFEBE';
			case 'ط': return '\uFEC2'; case 'ظ': return '\uFEC6'; case 'ع': return '\uFECA';
			case 'غ': return '\uFECE'; case 'ف': return '\uFED2'; case 'ق': return '\uFED6';
			case 'ك': return '\uFEDA'; case 'ل': return '\uFEDE'; case 'م': return '\uFEE2';
			case 'ن': return '\uFEE6'; case 'ه': return '\uFEEA'; case 'و': return '\uFEEE';
			case 'ي': return '\uFEF2'; case 'ى': return '\uFEF0'; case 'ة': return '\uFE94';
			case 'ؤ': return '\uFE86'; case 'ئ': return '\uFE8A'; case 'ء': return '\uFE80';
			default: return c;
		}
	}
	
	private void handleCharacter(int primaryCode, InputConnection ic) {
		char code = (char) primaryCode;
		if (!isArabic && Character.isLetter(code) && isCaps) {
			code = Character.toUpperCase(code);
		}
		ic.commitText(String.valueOf(code), 1);
		if (isCaps) {
			isCaps = false;
			updateShiftKeyState();
		}
	}
	private void handleDelete(InputConnection ic) {
		if (TextUtils.isEmpty(ic.getSelectedText(0))) {
			ic.deleteSurroundingText(1, 0);
			} else {
			ic.commitText("", 1);
		}
	}
	private void handleShift() {
		if (isArabic) {
			isArabicShift = !isArabicShift;
			keyboardView.setKeyboard(isArabicShift ? keyboardArabicShift : keyboardArabic);
			} else {
			isCaps = !isCaps;
			updateShiftKeyState();
		}
	}
	private void updateShiftKeyState() {
		if (keyboardEnglish != null) {
			keyboardEnglish.setShifted(isCaps);
			keyboardView.invalidateAllKeys();
		}
	}
	private void switchLanguage() {
		isArabic = !isArabic;
		isCaps = false;
		isArabicShift = false;
		switchToAlpha();
		preferences.edit().putBoolean("last_used_arabic", isArabic).apply();
	}
	
	private void switchToSymbols() {
		keyboardView.setKeyboard(keyboardSymbols);
	}
	
	private void switchToNumbers() {
		keyboardView.setKeyboard(keyboardNumbers);
	}
	private void switchToAlpha() {
		keyboardView.setKeyboard(isArabic ? keyboardArabic : keyboardEnglish);
		updateShiftKeyState();
	}
	private void handleEnter(InputConnection ic) {
		ic.commitText("\n", 1);
	}
	private void playVibration() {
		if (vibrator != null && vibrator.hasVibrator()) vibrator.vibrate(20);
	}
	private void playSound(int keyCode) {
		if (audioManager != null) {
			int sound;
			switch (keyCode) {
				case Keyboard.KEYCODE_DELETE: sound = AudioManager.FX_KEYPRESS_DELETE; break;
				case 32: sound = AudioManager.FX_KEYPRESS_SPACEBAR; break;
				case KeyEvent.KEYCODE_ENTER: case Keyboard.KEYCODE_DONE: sound = AudioManager.FX_KEYPRESS_RETURN; break;
				default: sound = AudioManager.FX_KEYPRESS_STANDARD; break;
			}
			audioManager.playSoundEffect(sound);
		}
	}
	
	// *** هنا تم تفعيل الدوال ***
	@Override public void onPress(int primaryCode) {}
	@Override public void onRelease(int primaryCode) {}
	@Override public void onText(CharSequence text) {}
	
	@Override public void swipeDown() {
		requestHideSelf(0);
	}
	
	@Override public void swipeLeft() {
		InputConnection ic = getCurrentInputConnection();
		if (ic == null) return;
		// تحريك المؤشر لليسار
		ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
		ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_LEFT));
	}
	
	@Override public void swipeRight() {
		InputConnection ic = getCurrentInputConnection();
		if (ic == null) return;
		// تحريك المؤشر لليمين
		ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
		ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_RIGHT));
	}
	
	@Override public void swipeUp() {
		// التبديل إلى Shift
		handleShift();
	}
}