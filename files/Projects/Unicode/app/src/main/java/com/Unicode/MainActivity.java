package com.Unicode;

import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {
	
	// Control variables
	private EditText inputText;
	private TextView outputText;
	private TextView charCountText;
	private Button toggleReverseBtn;
	private Button clearBtn;
	private Button copyBtn;
	private boolean reverseArabic = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Create the main interface
		ScrollView scrollView = new ScrollView(this);
		scrollView.setBackgroundColor(Color.parseColor("#F5F5F5"));
		scrollView.setFillViewport(true);
		
		LinearLayout mainLayout = createMainLayout();
		scrollView.addView(mainLayout);
		setContentView(scrollView);
		
		// Initialize elements
		initializeViews(mainLayout);
		setupListeners();
	}
	
	/**
	* Create the main layout of the application
	*/
	private LinearLayout createMainLayout() {
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setPadding(24, 48, 24, 24);
		layout.setLayoutParams(new LinearLayout.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.MATCH_PARENT
		));
		return layout;
	}
	
	/**
	* Initialize all interface elements
	*/
	private void initializeViews(LinearLayout mainLayout) {
		// Main title
		TextView titleText = createTitleView();
		mainLayout.addView(titleText);
		
		// Input card
		CardView inputCard = createCard();
		LinearLayout inputCardLayout = createCardLayout();
		
		TextView inputLabel = createLabel("النص الأصلي:");
		inputText = createInputField();
		charCountText = createCharCountView();
		
		inputCardLayout.addView(inputLabel);
		inputCardLayout.addView(inputText);
		inputCardLayout.addView(charCountText);
		inputCard.addView(inputCardLayout);
		mainLayout.addView(inputCard);
		
		// Buttons
		LinearLayout buttonLayout = createButtonLayout();
		toggleReverseBtn = createToggleButton();
		clearBtn = createClearButton();
		copyBtn = createCopyButton();
		
		buttonLayout.addView(toggleReverseBtn);
		buttonLayout.addView(clearBtn);
		buttonLayout.addView(copyBtn);
		mainLayout.addView(buttonLayout);
		
		// Output card
		CardView outputCard = createCard();
		LinearLayout outputCardLayout = createCardLayout();
		
		TextView outputLabel = createLabel("النص المعكوس:");
		outputText = createOutputField();
		
		outputCardLayout.addView(outputLabel);
		outputCardLayout.addView(outputText);
		outputCard.addView(outputCardLayout);
		mainLayout.addView(outputCard);
		
		// Additional information
		TextView infoText = createInfoView();
		mainLayout.addView(infoText);
		
		// Examples
		TextView examplesText = createExamplesView();
		mainLayout.addView(examplesText);
	}
	
	/**
	* Create the application title
	*/
	private TextView createTitleView() {
		TextView title = new TextView(this);
		title.setText("🔄 عاكس النص العربي");
		title.setTextSize(24);
		title.setTextColor(Color.parseColor("#2C3E50"));
		title.setGravity(Gravity.CENTER);
		title.setPadding(0, 0, 0, 32);
		title.setTypeface(null, android.graphics.Typeface.BOLD);
		return title;
	}
	
	/**
	* Create a CardView
	*/
	private CardView createCard() {
		CardView card = new CardView(this);
		card.setRadius(16);
		card.setCardElevation(8);
		card.setContentPadding(20, 20, 20, 20);
		card.setCardBackgroundColor(Color.WHITE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.WRAP_CONTENT
		);
		params.setMargins(0, 16, 0, 16);
		card.setLayoutParams(params);
		return card;
	}
	
	/**
	* Create a layout within the card
	*/
	private LinearLayout createCardLayout() {
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		return layout;
	}
	
	/**
	* Create a Label
	*/
	private TextView createLabel(String text) {
		TextView label = new TextView(this);
		label.setText(text);
		label.setTextSize(14);
		label.setTextColor(Color.parseColor("#7F8C8D"));
		label.setPadding(0, 0, 0, 8);
		label.setTypeface(null, android.graphics.Typeface.BOLD);
		return label;
	}
	
	/**
	* Create the input field
	*/
	private EditText createInputField() {
		EditText input = new EditText(this);
		input.setHint("اكتب النص هنا... مثال: السلام عليكم");
		input.setTextSize(16);
		input.setMinLines(3);
		input.setGravity(Gravity.TOP | Gravity.START);
		input.setBackground(createFieldBackground());
		input.setPadding(20, 20, 20, 20);
		input.setTextColor(Color.parseColor("#2C3E50"));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.WRAP_CONTENT
		);
		input.setLayoutParams(params);
		return input;
	}
	
	/**
	* Create the character counter
	*/
	private TextView createCharCountView() {
		TextView charCount = new TextView(this);
		charCount.setText("عدد الأحرف: 0");
		charCount.setTextSize(12);
		charCount.setTextColor(Color.parseColor("#95A5A6"));
		charCount.setGravity(Gravity.END);
		charCount.setPadding(0, 8, 0, 0);
		return charCount;
	}
	
	/**
	* Create the output field
	*/
	private TextView createOutputField() {
		TextView output = new TextView(this);
		output.setTextSize(16);
		output.setMinLines(3);
		output.setGravity(Gravity.TOP | Gravity.START);
		output.setBackground(createFieldBackground());
		output.setPadding(20, 20, 20, 20);
		output.setTextIsSelectable(true);
		output.setText("النص المعكوس سيظهر هنا...");
		output.setTextColor(Color.parseColor("#95A5A6"));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.WRAP_CONTENT
		);
		output.setLayoutParams(params);
		return output;
	}
	
	/**
	* Create the button layout
	*/
	private LinearLayout createButtonLayout() {
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.WRAP_CONTENT
		);
		params.setMargins(0, 16, 0, 16);
		layout.setLayoutParams(params);
		return layout;
	}
	
	/**
	* Create the toggle button
	*/
	private Button createToggleButton() {
		Button btn = createStyledButton("🔄 تفعيل العكس", "#3498DB");
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		0,
		ViewGroup.LayoutParams.WRAP_CONTENT,
		1f
		);
		params.setMargins(4, 0, 4, 0);
		btn.setLayoutParams(params);
		return btn;
	}
	
	/**
	* Create the clear button
	*/
	private Button createClearButton() {
		Button btn = createStyledButton("🗑️ مسح", "#E74C3C");
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		0,
		ViewGroup.LayoutParams.WRAP_CONTENT,
		0.5f
		);
		params.setMargins(4, 0, 4, 0);
		btn.setLayoutParams(params);
		return btn;
	}
	
	/**
	* Create the copy button
	*/
	private Button createCopyButton() {
		Button btn = createStyledButton("📋 نسخ", "#27AE60");
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		0,
		ViewGroup.LayoutParams.WRAP_CONTENT,
		0.5f
		);
		params.setMargins(4, 0, 4, 0);
		btn.setLayoutParams(params);
		btn.setEnabled(false);
		btn.setAlpha(0.5f);
		return btn;
	}
	
	/**
	* Create a styled button
	*/
	private Button createStyledButton(String text, String color) {
		Button btn = new Button(this);
		btn.setText(text);
		btn.setTextColor(Color.WHITE);
		btn.setTextSize(14);
		btn.setAllCaps(false);
		
		GradientDrawable background = new GradientDrawable();
		background.setColor(Color.parseColor(color));
		background.setCornerRadius(12);
		btn.setBackground(background);
		btn.setPadding(24, 16, 24, 16);
		
		return btn;
	}
	
	/**
	* Create a background for the fields
	*/
	private GradientDrawable createFieldBackground() {
		GradientDrawable background = new GradientDrawable();
		background.setColor(Color.parseColor("#F8F9FA"));
		background.setCornerRadius(8);
		background.setStroke(2, Color.parseColor("#E0E0E0"));
		return background;
	}
	
	/**
	* Create the info text view
	*/
	private TextView createInfoView() {
		TextView info = new TextView(this);
		info.setText("ℹ️ يحول النص العربي إلى أشكال Unicode المعكوسة");
		info.setTextSize(12);
		info.setTextColor(Color.parseColor("#7F8C8D"));
		info.setGravity(Gravity.CENTER);
		info.setPadding(0, 16, 0, 8);
		return info;
	}
	
	/**
	* Create the examples text view
	*/
	private TextView createExamplesView() {
		TextView examples = new TextView(this);
		examples.setText("📝 أمثلة:\n" +
		"السلام عليكم ← ﻢﻜﻴﻠﻋ ﻡﻼﺴﻟﺍ\n" +
		"مرحبا ← ﺎﺒﺣﺮﻣ\n" +
		"أهلا وسهلا ← ﻼﻬﺳﻭ ﻼﻫﺃ");
		examples.setTextSize(11);
		examples.setTextColor(Color.parseColor("#95A5A6"));
		examples.setGravity(Gravity.CENTER);
		examples.setPadding(16, 8, 16, 16);
		examples.setLineSpacing(8, 1);
		return examples;
	}
	
	/**
	* Set up event listeners
	*/
	private void setupListeners() {
		// Text change listener
		inputText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateCharCount(s.length());
				processText(); // Process text on every change
			}
			
			@Override
			public void afterTextChanged(Editable s) {}
		});
		
		// Toggle button listener
		toggleReverseBtn.setOnClickListener(v -> {
			reverseArabic = !reverseArabic;
			updateToggleButton();
			processText();
		});
		
		// Clear button listener
		clearBtn.setOnClickListener(v -> {
			inputText.setText("");
			outputText.setText("النص المعكوس سيظهر هنا...");
			outputText.setTextColor(Color.parseColor("#95A5A6"));
			copyBtn.setEnabled(false);
			copyBtn.setAlpha(0.5f);
			Toast.makeText(this, "✅ تم مسح النص", Toast.LENGTH_SHORT).show();
		});
		
		// Copy button listener
		copyBtn.setOnClickListener(v -> {
			String textToCopy = outputText.getText().toString();
			if (!textToCopy.isEmpty() && !textToCopy.equals("النص المعكوس سيظهر هنا...")) {
				android.content.ClipboardManager clipboard =
				(android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
				android.content.ClipData clip =
				android.content.ClipData.newPlainText("Reversed Text", textToCopy);
				clipboard.setPrimaryClip(clip);
				Toast.makeText(this, "✅ تم نسخ النص المعكوس", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	/**
	* Process and reverse the text
	*/
	private void processText() {
		String text = inputText.getText().toString();
		
		if (text.isEmpty()) {
			outputText.setText("النص المعكوس سيظهر هنا...");
			outputText.setTextColor(Color.parseColor("#95A5A6"));
			copyBtn.setEnabled(false);
			copyBtn.setAlpha(0.5f);
			return;
		}
		
		String processedText;
		
		if (reverseArabic) {
			// Reverse text using Unicode Presentation Forms
			processedText = reverseArabicText(text);
			} else {
			processedText = text;
		}
		
		outputText.setText(processedText);
		outputText.setTextColor(Color.parseColor("#2C3E50"));
		copyBtn.setEnabled(true);
		copyBtn.setAlpha(1f);
	}
	
	/**
	* Reverse Arabic text using Unicode Presentation Forms
	*/
	private String reverseArabicText(String text) {
		// Special handling for "لا" ligature before processing
		String originalText = text.replace("لا", "ﻻ");
		
		// Reverse the string to process from visual left to right
		String reversed = new StringBuilder(originalText).reverse().toString();
		
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < reversed.length(); i++) {
			char currentChar = reversed.charAt(i);
			
			// To get the correct context, we need the neighbors from the ORIGINAL string.
			// The character at `reversed[i]` was at `originalText[originalText.length() - 1 - i]`
			int originalIndex = originalText.length() - 1 - i;
			char originalPrev = (originalIndex > 0) ? originalText.charAt(originalIndex - 1) : ' ';
			char originalNext = (originalIndex < originalText.length() - 1) ? originalText.charAt(originalIndex + 1) : ' ';
			
			// Special handling for the "ﻻ" ligature
			if (currentChar == 'ﻻ') {
				// The form of Lam-Alef depends on whether the character BEFORE the Lam connects.
				// The character before the Lam is `originalPrev`.
				if (isArabicChar(originalPrev) && connectsAfter(originalPrev)) {
					result.append('\uFEFC'); // ﻼ (Final/Medial form)
					} else {
					result.append('\uFEFB'); // ﻻ (Initial/Isolated form)
				}
				} else {
				// Handle normal characters
				char convertedChar = getAppropriateForm(currentChar, originalPrev, originalNext);
				result.append(convertedChar);
			}
		}
		
		return result.toString();
	}
	
	
	/**
	* Get the appropriate form of the Arabic character based on its actual neighbors.
	*/
	private char getAppropriateForm(char c, char prev, char next) {
		if (!isArabicChar(c)) {
			return c;
		}
		boolean connectsWithPrev = isArabicChar(prev) && connectsAfter(prev);
		boolean connectsWithNext = isArabicChar(next) && connectsAfter(c);
		
		if (connectsWithPrev && connectsWithNext) {
			return getMedialForm(c);
			} else if (connectsWithPrev) { // and !connectsWithNext
			return getFinalForm(c);
			} else if (connectsWithNext) { // and !connectsWithPrev
			return getInitialForm(c);
			} else { // !connectsWithPrev && !connectsWithNext
			return getIsolatedForm(c);
		}
	}
	
	
	// --- Helper Methods for Arabic Shaping ---
	
	private boolean isArabicChar(char c) {
		return c >= '\u0600' && c <= '\u06FF' || c >= '\uFE70' && c <= '\uFEFF';
	}
	
	private boolean connectsAfter(char c) {
		if (!isArabicChar(c)) return false;
		// Characters that do NOT connect to the following character.
		String nonConnectingChars = "ادذرزوؤآأإىة";
		return nonConnectingChars.indexOf(c) == -1 && c != 'ﻻ';
	}
	
	/**
	* Get the isolated form of the Arabic character
	*/
	private char getIsolatedForm(char c) {
		switch (c) {
			case 'ا': return '\uFE8D'; // ﺍ
			case 'أ': return '\uFE83'; // ﺃ
			case 'إ': return '\uFE87'; // ﺇ
			case 'آ': return '\uFE81'; // ﺁ
			case 'ب': return '\uFE8F'; // ﺏ
			case 'ت': return '\uFE95'; // ﺕ
			case 'ث': return '\uFE99'; // ﺙ
			case 'ج': return '\uFE9D'; // ﺝ
			case 'ح': return '\uFEA1'; // ﺡ
			case 'خ': return '\uFEA5'; // ﺥ
			case 'د': return '\uFEA9'; // ﺩ
			case 'ذ': return '\uFEAB'; // ﺫ
			case 'ر': return '\uFEAD'; // ﺭ
			case 'ز': return '\uFEAF'; // ﺯ
			case 'س': return '\uFEB1'; // ﺱ
			case 'ش': return '\uFEB5'; // ﺵ
			case 'ص': return '\uFEB9'; // ﺹ
			case 'ض': return '\uFEBD'; // ﺽ
			case 'ط': return '\uFEC1'; // ﻁ
			case 'ظ': return '\uFEC5'; // ﻅ
			case 'ع': return '\uFEC9'; // ﻉ
			case 'غ': return '\uFECD'; // ﻍ
			case 'ف': return '\uFED1'; // ﻑ
			case 'ق': return '\uFED5'; // ﻕ
			case 'ك': return '\uFED9'; // ﻙ
			case 'ل': return '\uFEDD'; // ﻝ
			case 'م': return '\uFEE1'; // ﻡ
			case 'ن': return '\uFEE5'; // ﻥ
			case 'ه': return '\uFEE9'; // ﻩ
			case 'و': return '\uFEED'; // ﻭ
			case 'ي': return '\uFEF1'; // ﻱ
			case 'ى': return '\uFEEF'; // ﻯ
			case 'ة': return '\uFE93'; // ﺓ
			case 'ؤ': return '\uFE85'; // ﺅ
			case 'ئ': return '\uFE89'; // ﺉ
			case 'ء': return '\uFE80'; // ﺀ
			default: return c;
		}
	}
	
	/**
	* Get the initial form of the Arabic character
	*/
	private char getInitialForm(char c) {
		switch (c) {
			case 'ب': return '\uFE91'; // ﺑ
			case 'ت': return '\uFE97'; // ﺗ
			case 'ث': return '\uFE9B'; // ﺛ
			case 'ج': return '\uFE9F'; // ﺟ
			case 'ح': return '\uFEA3'; // ﺣ
			case 'خ': return '\uFEA7'; // ﺧ
			case 'س': return '\uFEB3'; // ﺳ
			case 'ش': return '\uFEB7'; // ﺷ
			case 'ص': return '\uFEBB'; // ﺻ
			case 'ض': return '\uFEBF'; // ﺿ
			case 'ط': return '\uFEC3'; // ﻃ
			case 'ظ': return '\uFEC7'; // ﻇ
			case 'ع': return '\uFECB'; // ﻋ
			case 'غ': return '\uFECF'; // ﻏ
			case 'ف': return '\uFED3'; // ﻓ
			case 'ق': return '\uFED7'; // ﻗ
			case 'ك': return '\uFEDB'; // ﻛ
			case 'ل': return '\uFEDF'; // ﻟ
			case 'م': return '\uFEE3'; // ﻣ
			case 'ن': return '\uFEE7'; // ﻧ
			case 'ه': return '\uFEEB'; // ﻫ
			case 'ي': return '\uFEF3'; // ﻳ
			case 'ئ': return '\uFE8B'; // ﺋ
			// Characters that don't have a distinct initial form use isolated form
			default: return getIsolatedForm(c);
		}
	}
	
	/**
	* Get the medial form of the Arabic character
	*/
	private char getMedialForm(char c) {
		switch (c) {
			case 'ب': return '\uFE92'; // ﺒ
			case 'ت': return '\uFE98'; // ﺘ
			case 'ث': return '\uFE9C'; // ﺜ
			case 'ج': return '\uFEA0'; // ﺠ
			case 'ح': return '\uFEA4'; // ﺤ
			case 'خ': return '\uFEA8'; // ﺨ
			case 'س': return '\uFEB4'; // ﺴ
			case 'ش': return '\uFEB8'; // ﺸ
			case 'ص': return '\uFEBC'; // ﺼ
			case 'ض': return '\uFEC0'; // ﻀ
			case 'ط': return '\uFEC4'; // ﻄ
			case 'ظ': return '\uFEC8'; // ﻈ
			case 'ع': return '\uFECC'; // ﻌ
			case 'غ': return '\uFED0'; // ﻐ
			case 'ف': return '\uFED4'; // ﻔ
			case 'ق': return '\uFED8'; // ﻘ
			case 'ك': return '\uFEDC'; // ﻜ
			case 'ل': return '\uFEE0'; // ﻠ
			case 'م': return '\uFEE4'; // ﻤ
			case 'ن': return '\uFEE8'; // ﻨ
			case 'ه': return '\uFEEC'; // ﻬ
			case 'ي': return '\uFEF4'; // ﻴ
			case 'ئ': return '\uFE8C'; // ﺌ
			// Characters that don't have a distinct medial form use final form
			default: return getFinalForm(c);
		}
	}
	
	/**
	* Get the final form of the Arabic character
	*/
	private char getFinalForm(char c) {
		switch (c) {
			case 'ا': return '\uFE8E'; // ﺎ
			case 'أ': return '\uFE84'; // ﺄ
			case 'إ': return '\uFE88'; // ﺈ
			case 'آ': return '\uFE82'; // ﺂ
			case 'ب': return '\uFE90'; // ﺐ
			case 'ت': return '\uFE96'; // ﺖ
			case 'ث': return '\uFE9A'; // ﺚ
			case 'ج': return '\uFE9E'; // ﺞ
			case 'ح': return '\uFEA2'; // ﺢ
			case 'خ': return '\uFEA6'; // ﺦ
			case 'د': return '\uFEAA'; // ﺪ
			case 'ذ': return '\uFEAC'; // ﺬ
			case 'ر': return '\uFEAE'; // ﺮ
			case 'ز': return '\uFEB0'; // ﺰ
			case 'س': return '\uFEB2'; // ﺲ
			case 'ش': return '\uFEB6'; // ﺶ
			case 'ص': return '\uFEBA'; // ﺺ
			case 'ض': return '\uFEBE'; // ﺾ
			case 'ط': return '\uFEC2'; // ﻂ
			case 'ظ': return '\uFEC6'; // ﻆ
			case 'ع': return '\uFECA'; // ﻊ
			case 'غ': return '\uFECE'; // ﻎ
			case 'ف': return '\uFED2'; // ﻒ
			case 'ق': return '\uFED6'; // ﻖ
			case 'ك': return '\uFEDA'; // ﻚ
			case 'ل': return '\uFEDE'; // ﻞ
			case 'م': return '\uFEE2'; // ﻢ
			case 'ن': return '\uFEE6'; // ﻦ
			case 'ه': return '\uFEEA'; // ﻪ
			case 'و': return '\uFEEE'; // ﻮ
			case 'ي': return '\uFEF2'; // ﻲ
			case 'ى': return '\uFEF0'; // ﻰ
			case 'ة': return '\uFE94'; // ﺔ
			case 'ؤ': return '\uFE86'; // ﺆ
			case 'ئ': return '\uFE8A'; // ﺊ
			// 'ء' doesn't have a final form
			case 'ء': return '\uFE80'; // ﺀ
			default: return c;
		}
	}
	
	/**
	* Update the character count
	*/
	private void updateCharCount(int count) {
		charCountText.setText("عدد الأحرف: " + count);
	}
	
	/**
	* Update the toggle button's appearance and text
	*/
	private void updateToggleButton() {
		if (reverseArabic) {
			toggleReverseBtn.setText("🔄 إلغاء العكس");
			GradientDrawable background = (GradientDrawable) toggleReverseBtn.getBackground();
			background.setColor(Color.parseColor("#2980B9"));
			} else {
			toggleReverseBtn.setText("🔄 تفعيل العكس");
			GradientDrawable background = (GradientDrawable) toggleReverseBtn.getBackground();
			background.setColor(Color.parseColor("#3498DB"));
		}
	}
}