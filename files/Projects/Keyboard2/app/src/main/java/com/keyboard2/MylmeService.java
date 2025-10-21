package com.keyboard2;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.text.InputType;

public class MyImeService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
	
	// تم حذف الأكواد غير المستخدمة
	private static final int CODE_DELETE = -5;
	private static final int CODE_MODE_CHANGE = -2;
	private static final int CODE_DONE = -4;
	
	private KeyboardView mInputView;
	private Keyboard mKeyboardAr;
	private Keyboard mKeyboardSymbols;
	private Keyboard mCurrentKeyboard;
	
	@Override
	public View onCreateInputView() {
		mInputView = (KeyboardView) getLayoutInflater().inflate(R.layout.ime_keyboard_view, null);
		
		// تحميل لوحة المفاتيح العربية والرموز فقط
		mKeyboardAr = new Keyboard(this, R.xml.keyboard_ar);
		mKeyboardSymbols = new Keyboard(this, R.xml.keyboard_symbols);
		
		// لوحة المفاتيح العربية هي الافتراضية
		mCurrentKeyboard = mKeyboardAr;
		mInputView.setKeyboard(mCurrentKeyboard);
		mInputView.setOnKeyboardActionListener(this);
		mInputView.setPreviewEnabled(true);
		
		return mInputView;
	}
	
	@Override
	public void onStartInputView(EditorInfo info, boolean restarting) {
		super.onStartInputView(info, restarting);
		
		int inputType = info.inputType & InputType.TYPE_MASK_CLASS;
		
		// التبديل إلى لوحة الرموز إذا كان الحقل رقميًا
		if (inputType == InputType.TYPE_CLASS_NUMBER || inputType == InputType.TYPE_CLASS_PHONE || inputType == InputType.TYPE_CLASS_DATETIME) {
			mCurrentKeyboard = mKeyboardSymbols;
			} else {
			// وإلا، تأكد من أن لوحة المفاتيح الحالية هي العربية
			if (mCurrentKeyboard == mKeyboardSymbols) {
				mCurrentKeyboard = mKeyboardAr;
			}
		}
		mInputView.setKeyboard(mCurrentKeyboard);
	}
	
	// دالة للتبديل بين الحروف والرموز
	private void switchMode() {
		if (mCurrentKeyboard == mKeyboardSymbols) {
			mCurrentKeyboard = mKeyboardAr;
			} else {
			mCurrentKeyboard = mKeyboardSymbols;
		}
		mInputView.setKeyboard(mCurrentKeyboard);
	}
	
	@Override
	public void onKey(int primaryCode, int[] keyCodes) {
		InputConnection ic = getCurrentInputConnection();
		if (ic == null) return;
		
		switch (primaryCode) {
			case CODE_DELETE:
			ic.deleteSurroundingText(1, 0);
			break;
			case CODE_MODE_CHANGE:
			switchMode();
			break;
			case CODE_DONE:
			ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
			break;
			default:
			// إرسال الحرف أو الرمز مباشرة
			char code = (char) primaryCode;
			ic.commitText(String.valueOf(code), 1);
			break;
		}
	}
	
	// دوال غير مستخدمة
	@Override public void onPress(int primaryCode) { }
	@Override public void onRelease(int primaryCode) { }
	@Override public void onText(CharSequence text) { }
	@Override public void swipeLeft() { }
	@Override public void swipeRight() { }
	@Override public void swipeDown() { }
	@Override public void swipeUp() { }
}