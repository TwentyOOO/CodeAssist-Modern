package com.codeeditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import java.io.FileOutputStream;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
	
	public static class ThemeColors {
		// --- لوحة الألوان الأساسية لتمييز الصيغة ---
		static final int KEYWORD_BLUE = Color.parseColor("#007ACC");
		static final int TYPE_GREEN = Color.parseColor("#228B22");
		static final int STRING_ORANGE = Color.parseColor("#D2691E");
		static final int NUMBER_PURPLE = Color.parseColor("#9400D3");
		static final int COMMENT_GREY = Color.parseColor("#808080");
		static final int METHOD_YELLOW = Color.parseColor("#B8860B");
		static final int IMPORTANT_RED = Color.parseColor("#FF0000");
		
		// --- واجهة الوضع المظلم ---
		static final int DARK_BACKGROUND = Color.parseColor("#1E1E1E");
		static final int DARK_EDITOR_BACKGROUND = Color.parseColor("#1E1E1E");
		static final int DARK_LINE_NUMBERS_BG = Color.parseColor("#2B2B2B");
		static final int DARK_TOOLBAR_BG = Color.parseColor("#2B2B2B");
		static final int DARK_STATUS_BAR_BG = Color.parseColor("#007ACC");
		static final int DARK_TEXT_PRIMARY = Color.parseColor("#D4D4D4");
		static final int DARK_LINE_NUMBERS_TEXT = Color.parseColor("#858585");
		static final int DARK_DECORATION_LINES = Color.parseColor("#444444");
		static final int DARK_TOOLBAR_TEXT = Color.WHITE;
		
		// --- واجهة الوضع النهاري ---
		static final int LIGHT_BACKGROUND = Color.parseColor("#F5F5F5");
		static final int LIGHT_EDITOR_BACKGROUND = Color.parseColor("#FFFFFF");
		static final int LIGHT_LINE_NUMBERS_BG = Color.parseColor("#EEEEEE");
		static final int LIGHT_TOOLBAR_BG = Color.parseColor("#EEEEEE");
		static final int LIGHT_STATUS_BAR_BG = Color.parseColor("#007ACC");
		static final int LIGHT_TEXT_PRIMARY = Color.parseColor("#000000");
		static final int LIGHT_LINE_NUMBERS_TEXT = Color.parseColor("#858585");
		static final int LIGHT_DECORATION_LINES = Color.parseColor("#DDDDDD");
		static final int LIGHT_TOOLBAR_TEXT = Color.BLACK;
	}
	
	// متغيرات الألوان الحالية (تتغير مع المظهر)
	private int COLOR_KEYWORD;
	private int COLOR_TYPE;
	private int COLOR_CONTROL_FLOW;
	private int COLOR_NUMBER;
	private int COLOR_STRING;
	private int COLOR_COMMENT;
	private int COLOR_ANNOTATION;
	private int COLOR_METHOD;
	private int COLOR_OPERATOR;
	private int COLOR_BRACKET;
	private int COLOR_SEMICOLON;
	private int COLOR_IMPORTANT;
	
	// نصوص الواجهة
	private static final String TEXT_NEW = "جديد";
	private static final String TEXT_SAVE = "حفظ";
	private static final String TEXT_CLEAR = "مسح";
	private static final String TEXT_FORMAT = "تنسيق";
	private static final String TEXT_THEME_TOGGLE = "تبديل المظهر";
	private static final String TEXT_ZOOM_IN = "تكبير +";
	private static final String TEXT_ZOOM_OUT = "تصغير -";
	private static final String TEXT_RESET = "إعادة تعيين";
	
	// متغيرات الواجهة
	private LinearLayout mainLayout;
	private LinearLayout toolbarLayout;
	private LinearLayout toolbarContent;
	private LinearLayout editorContent; // ## بداية الإصلاح: تعريف المتغير هنا ##
	private CodeEditorView codeEditor;
	private LineNumbersView lineNumbers;
	private TextView statusBar;
	private PanZoomContainer panZoomContainer;
	private FastScroller fastScroller;
	
	// متغيرات للتحكم
	private boolean isDarkMode = true;
	private boolean isHighlighting = false;
	private boolean isFormatting = false;
	private boolean isUserTyping = true;
	private final Handler highlightingHandler = new Handler(Looper.getMainLooper());
	private final Runnable highlightingRunnable = () -> {
		if (codeEditor == null) return;
		int cursorPosition = codeEditor.getSelectionStart();
		isHighlighting = true;
		highlightEnhancedSyntax(codeEditor.getText());
		isHighlighting = false;
		if (cursorPosition <= codeEditor.length()) {
			codeEditor.setSelection(cursorPosition);
		}
	};
	private final Runnable formatAndHighlightRunnable = () -> {
		formatCode();
		highlightingHandler.post(highlightingRunnable);
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		
		setupToolbar(mainLayout);
		
		FrameLayout contentFrame = new FrameLayout(this);
		LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f
		);
		mainLayout.addView(contentFrame, frameParams);
		
		panZoomContainer = new PanZoomContainer(this);
		editorContent = new LinearLayout(this); // ## بداية الإصلاح: تهيئة المتغير ##
		editorContent.setOrientation(LinearLayout.HORIZONTAL);
		panZoomContainer.addView(editorContent);
		contentFrame.addView(panZoomContainer);
		
		setupEditorViews(editorContent);
		
		fastScroller = new FastScroller(this);
		FrameLayout.LayoutParams scrollerParams = new FrameLayout.LayoutParams(
		(int) (24 * getResources().getDisplayMetrics().density),
		FrameLayout.LayoutParams.MATCH_PARENT
		);
		scrollerParams.gravity = Gravity.END;
		contentFrame.addView(fastScroller, scrollerParams);
		
		setupStatusBar(mainLayout);
		
		applyTheme();
		
		setupEditorEvents();
		setContentView(mainLayout);
	}
	
	private void setupEditorViews(LinearLayout parent) {
		final float editorFontSize = 14f;
		final float numberFontSize = 14f;
		final float lineSpacingMultiplier = 1.15f;
		
		lineNumbers = new LineNumbersView(this);
		lineNumbers.setPadding(12, 16, 12, 16);
		lineNumbers.setTypeface(Typeface.MONOSPACE);
		lineNumbers.setTextSize(numberFontSize);
		lineNumbers.setLineSpacing(0f, lineSpacingMultiplier);
		lineNumbers.setGravity(Gravity.TOP | Gravity.END);
		lineNumbers.setMinimumWidth(80);
		parent.addView(lineNumbers, new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.MATCH_PARENT));
		
		codeEditor = new CodeEditorView(this);
		codeEditor.setGravity(Gravity.TOP | Gravity.START);
		codeEditor.setTextDirection(View.TEXT_DIRECTION_LTR);
		codeEditor.setTypeface(Typeface.MONOSPACE);
		codeEditor.setTextSize(editorFontSize);
		codeEditor.setLineSpacing(0f, lineSpacingMultiplier);
		codeEditor.setInputType(codeEditor.getInputType() |
		android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS |
		android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		codeEditor.setPadding(16, 16, 16, 16);
		codeEditor.setHorizontallyScrolling(false);
		codeEditor.setMinimumWidth(2000);
		codeEditor.setVerticalScrollBarEnabled(false);
		
		LinearLayout.LayoutParams editorParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.MATCH_PARENT);
		editorParams.weight = 1;
		parent.addView(codeEditor, editorParams);
		
		lineNumbers.setEditor(codeEditor);
	}
	
	private void setupToolbar(LinearLayout parent) {
		toolbarLayout = new LinearLayout(this);
		toolbarLayout.setOrientation(LinearLayout.HORIZONTAL);
		toolbarLayout.setPadding(8, 8, 8, 8);
		
		HorizontalScrollView toolbarScrollView = new HorizontalScrollView(this);
		toolbarContent = new LinearLayout(this);
		toolbarContent.setOrientation(LinearLayout.HORIZONTAL);
		
		Button newButton = createToolbarButton(TEXT_NEW);
		Button saveButton = createToolbarButton(TEXT_SAVE);
		Button clearButton = createToolbarButton(TEXT_CLEAR);
		Button formatButton = createToolbarButton(TEXT_FORMAT);
		Button themeButton = createToolbarButton(TEXT_THEME_TOGGLE);
		Button zoomInButton = createToolbarButton(TEXT_ZOOM_IN);
		Button zoomOutButton = createToolbarButton(TEXT_ZOOM_OUT);
		Button resetButton = createToolbarButton(TEXT_RESET);
		
		toolbarContent.addView(newButton);
		toolbarContent.addView(saveButton);
		toolbarContent.addView(clearButton);
		toolbarContent.addView(formatButton);
		toolbarContent.addView(themeButton);
		toolbarContent.addView(zoomInButton);
		toolbarContent.addView(zoomOutButton);
		toolbarContent.addView(resetButton);
		
		toolbarScrollView.addView(toolbarContent);
		toolbarLayout.addView(toolbarScrollView);
		parent.addView(toolbarLayout);
		
		setupButtonListeners(newButton, saveButton, clearButton, formatButton, themeButton, zoomInButton, zoomOutButton, resetButton);
	}
	
	private void setupStatusBar(LinearLayout parent) {
		statusBar = new TextView(this);
		statusBar.setText("السطر: 1, العمود: 1");
		statusBar.setTextSize(12);
		statusBar.setPadding(16, 8, 16, 8);
		parent.addView(statusBar);
	}
	
	private void setupEditorEvents() {
		codeEditor.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!isUserTyping || isFormatting || isHighlighting) return;
				
				if (count == 1 && before == 0) {
					char insertedChar = s.charAt(start);
					handleAutoClosingBrackets(insertedChar, start);
				}
				
				if (count > 1 && before == 0) {
					highlightingHandler.removeCallbacks(formatAndHighlightRunnable);
					highlightingHandler.postDelayed(formatAndHighlightRunnable, 500);
				}
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				if (isFormatting || isHighlighting) return;
				
				highlightingHandler.removeCallbacks(highlightingRunnable);
				highlightingHandler.postDelayed(highlightingRunnable, 250);
				updateLineNumbers();
				updateStatusBar();
				
				if (editable.length() == 0) {
					codeEditor.post(() -> codeEditor.setSelection(0));
				}
			}
		});
		
		codeEditor.getViewTreeObserver().addOnScrollChangedListener(() -> {
			if (lineNumbers != null) {
				lineNumbers.scrollTo(0, codeEditor.getScrollY());
			}
			if (fastScroller != null) {
				fastScroller.onScrollChanged();
			}
		});
		
		fastScroller.setEditor(codeEditor);
		
		codeEditor.setOnKeyListener((v, keyCode, event) -> {
			if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
				handleAutoIndent();
				return true;
			}
			if (keyCode == KeyEvent.KEYCODE_TAB && event.getAction() == KeyEvent.ACTION_DOWN) {
				codeEditor.getText().insert(codeEditor.getSelectionStart(), "    ");
				return true;
			}
			return false;
		});
		
		isUserTyping = false;
		codeEditor.setText(generateInitialCode());
		isUserTyping = true;
		
		codeEditor.post(() -> {
			updateLineNumbers();
			updateStatusBar();
			highlightingHandler.post(highlightingRunnable);
		});
	}
	
	private void handleAutoClosingBrackets(char insertedChar, int start) {
		String closingChar = "";
		switch (insertedChar) {
			case '(': closingChar = ")"; break;
			case '{': closingChar = "}"; break;
			case '[': closingChar = "]"; break;
			case '"': closingChar = "\""; break;
			case '\'': closingChar = "'"; break;
		}
		
		if (!closingChar.isEmpty()) {
			isUserTyping = false;
			codeEditor.getText().insert(start + 1, closingChar);
			codeEditor.setSelection(start + 1);
			isUserTyping = true;
		}
	}
	
	private void handleAutoIndent() {
		Editable editable = codeEditor.getText();
		int start = codeEditor.getSelectionStart();
		Layout layout = codeEditor.getLayout();
		if (start < 0 || layout == null) return;
		
		int line = layout.getLineForOffset(start);
		int lineStart = layout.getLineStart(line);
		
		String currentLineTextBeforeCursor = editable.subSequence(lineStart, start).toString();
		StringBuilder indent = new StringBuilder();
		for (char c : currentLineTextBeforeCursor.toCharArray()) {
			if (c == ' ' || c == '\t') {
				indent.append(c);
				} else {
				indent.setLength(0);
			}
		}
		
		boolean previousLineEndsWithBrace = false;
		if (start > 0) {
			for (int i = start - 1; i >= lineStart; i--) {
				char prevChar = editable.charAt(i);
				if (!Character.isWhitespace(prevChar)) {
					if (prevChar == '{') {
						previousLineEndsWithBrace = true;
					}
					break;
				}
			}
		}
		
		StringBuilder toInsert = new StringBuilder("\n");
		toInsert.append(indent);
		if (previousLineEndsWithBrace) {
			toInsert.append("    ");
		}
		
		isUserTyping = false;
		editable.insert(start, toInsert);
		isUserTyping = true;
	}
	
	private void setupButtonListeners(Button newBtn, Button saveBtn, Button clearBtn, Button formatBtn, Button themeBtn,
	Button zoomInBtn, Button zoomOutBtn, Button resetBtn) {
		newBtn.setOnClickListener(v -> {
			isUserTyping = false;
			codeEditor.setText("");
			codeEditor.post(() -> codeEditor.setSelection(0));
			isUserTyping = true;
			showToast("مستند جديد");
		});
		
		saveBtn.setOnClickListener(v -> saveCodeToFile());
		
		clearBtn.setOnClickListener(v -> {
			isUserTyping = false;
			codeEditor.setText(generateInitialCode());
			codeEditor.post(() -> codeEditor.setSelection(0));
			isUserTyping = true;
			showToast("تم مسح المحتوى");
			highlightingHandler.post(highlightingRunnable);
		});
		
		formatBtn.setOnClickListener(v -> {
			formatCode();
			highlightingHandler.post(highlightingRunnable);
			showToast("تم تنسيق الكود");
		});
		
		themeBtn.setOnClickListener(v -> {
			isDarkMode = !isDarkMode;
			applyTheme();
			showToast(isDarkMode ? "الوضع المظلم" : "الوضع النهاري");
		});
		
		zoomInBtn.setOnClickListener(v -> panZoomContainer.zoomIn());
		zoomOutBtn.setOnClickListener(v -> panZoomContainer.zoomOut());
		resetBtn.setOnClickListener(v -> panZoomContainer.resetZoom());
	}
	
	private void updateLineNumbers() {
		if (lineNumbers == null || codeEditor == null) return;
		lineNumbers.post(() -> {
			final int lineCount = codeEditor.getLineCount();
			final StringBuilder linesText = new StringBuilder();
			int count = Math.max(1, lineCount);
			for (int i = 1; i <= count; i++) {
				linesText.append(String.format("%3d", i)).append("\n");
			}
			lineNumbers.setText(linesText.toString());
			lineNumbers.invalidate();
		});
	}
	
	private Button createToolbarButton(String text) {
		Button button = new Button(this);
		button.setText(text);
		button.setTextSize(12);
		button.setBackgroundColor(Color.TRANSPARENT);
		button.setPadding(24, 16, 24, 16);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(8, 0, 8, 0);
		button.setLayoutParams(params);
		return button;
	}
	
	private void updateStatusBar() {
		if (codeEditor == null || statusBar == null || panZoomContainer == null) return;
		int selectionStart = codeEditor.getSelectionStart();
		if (selectionStart < 0) return;
		
		String text = codeEditor.getText().toString();
		int line = 1, column = 1;
		for (int i = 0; i < selectionStart; i++) {
			if (text.charAt(i) == '\n') {
				line++;
				column = 1;
				} else {
				column++;
			}
		}
		int totalChars = text.length();
		float zoomLevel = panZoomContainer.getCurrentZoom();
		statusBar.setText(String.format("السطر: %d, العمود: %d | الأحرف: %d | التكبير: %.1fx",
		line, column, totalChars, zoomLevel));
	}
	
	private void formatCode() {
		isFormatting = true;
		
		Editable editable = codeEditor.getText();
		String originalText = editable.toString();
		
		StringBuilder formattedText = new StringBuilder();
		int indentLevel = 0;
		final String indentString = "    ";
		
		String[] lines = originalText.split("\n");
		
		for (String line : lines) {
			String trimmedLine = line.trim();
			
			if (trimmedLine.isEmpty()) {
				formattedText.append("\n");
				continue;
			}
			
			if (trimmedLine.startsWith("}") || trimmedLine.startsWith(")") || trimmedLine.startsWith("]")) {
				if (indentLevel > 0) {
					indentLevel--;
				}
			}
			
			for (int i = 0; i < indentLevel; i++) {
				formattedText.append(indentString);
			}
			
			formattedText.append(trimmedLine).append("\n");
			
			if (trimmedLine.endsWith("{") || trimmedLine.endsWith("(") || trimmedLine.endsWith("[")) {
				indentLevel++;
			}
		}
		
		if (formattedText.length() > 0) {
			formattedText.setLength(formattedText.length() - 1);
		}
		
		int cursorPosition = codeEditor.getSelectionStart();
		editable.replace(0, editable.length(), formattedText.toString());
		
		try {
			codeEditor.setSelection(Math.min(cursorPosition, editable.length()));
			} catch (Exception e) {
			codeEditor.setSelection(editable.length());
		}
		
		isFormatting = false;
	}
	
	private void highlightEnhancedSyntax(Editable editable) {
		if (isFormatting) return;
		int startOffset = 0;
		int endOffset = editable.length();
		
		ForegroundColorSpan[] spans = editable.getSpans(startOffset, endOffset, ForegroundColorSpan.class);
		for (ForegroundColorSpan span : spans) {
			editable.removeSpan(span);
		}
		
		highlightPattern(editable, "//.*", COLOR_COMMENT, startOffset, endOffset);
		highlightPattern(editable, "/\\*[\\s\\S]*?\\*/", COLOR_COMMENT, startOffset, endOffset);
		highlightPattern(editable, "\"([^\"\\\\]|\\\\.)*\"", COLOR_STRING, startOffset, endOffset);
		highlightPattern(editable, "'([^'\\\\]|\\\\.)*?'", COLOR_STRING, startOffset, endOffset);
		highlightPattern(editable, "\\b\\d+(\\.\\d+)?[fFdDlL]?\\b", COLOR_NUMBER, startOffset, endOffset);
		highlightPattern(editable, "\\b0[xX][0-9a-fA-F]+[lL]?\\b", COLOR_NUMBER, startOffset, endOffset);
		
		highlightPattern(editable, "\\b(return|new|throw|throws|final|static|super|this|true|false|null)\\b", COLOR_IMPORTANT, startOffset, endOffset);
		
		highlightPattern(editable, "\\b(public|private|protected|abstract|synchronized|volatile|transient|native|strictfp)\\b", COLOR_KEYWORD, startOffset, endOffset);
		highlightPattern(editable, "\\b(class|interface|enum|extends|implements|package|import)\\b", COLOR_TYPE, startOffset, endOffset);
		highlightPattern(editable, "\\b(int|double|float|boolean|char|byte|short|long|String|void|Object|List|Map|Set|ArrayList|HashMap|HashSet)\\b", COLOR_TYPE, startOffset, endOffset);
		highlightPattern(editable, "\\b(if|else|for|while|do|switch|case|default|break|continue|try|catch|finally)\\b", COLOR_CONTROL_FLOW, startOffset, endOffset);
		highlightPattern(editable, "@\\w+", COLOR_ANNOTATION, startOffset, endOffset);
		highlightPattern(editable, "\\b\\w+(?=\\s*\\()", COLOR_METHOD, startOffset, endOffset);
		highlightPattern(editable, "[\\{\\}]", COLOR_BRACKET, startOffset, endOffset);
		highlightPattern(editable, "[\\[\\]]", COLOR_BRACKET, startOffset, endOffset);
		highlightPattern(editable, "[\\(\\)]", COLOR_BRACKET, startOffset, endOffset);
		highlightPattern(editable, "[+\\-*/%=<>!&|^~]", COLOR_OPERATOR, startOffset, endOffset);
		highlightPattern(editable, "(==|!=|<=|>=|&&|\\|\\||\\+\\+|--|\\+=|-=|\\*=|/=|%=|<<|>>|>>>)", COLOR_OPERATOR, startOffset, endOffset);
		highlightPattern(editable, ";", COLOR_SEMICOLON, startOffset, endOffset);
	}
	
	private void highlightPattern(Editable editable, String pattern, int color, int start, int end) {
		try {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(editable.subSequence(start, end));
			while (m.find()) {
				int spanStart = start + m.start();
				int spanEnd = start + m.end();
				
				if (spanStart < spanEnd && spanEnd <= editable.length()) {
					ForegroundColorSpan[] existingSpans = editable.getSpans(spanStart, spanEnd, ForegroundColorSpan.class);
					boolean shouldApply = true;
					
					for (ForegroundColorSpan existingSpan : existingSpans) {
						if (existingSpan.getForegroundColor() == COLOR_COMMENT ||
						existingSpan.getForegroundColor() == COLOR_STRING) {
							shouldApply = false;
							break;
						}
					}
					
					if (shouldApply) {
						editable.setSpan(new ForegroundColorSpan(color),
						spanStart, spanEnd, Editable.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
				}
			}
			} catch (Exception e) {
			// تجاهل أخطاء تحليل التعبيرات النمطية
		}
	}
	
	private void saveCodeToFile() {
		try {
			FileOutputStream fos = openFileOutput("code.txt", MODE_PRIVATE);
			fos.write(codeEditor.getText().toString().getBytes());
			fos.close();
			showToast("تم الحفظ بنجاح!");
			} catch (Exception e) {
			showToast("خطأ في الحفظ: " + e.getMessage());
		}
	}
	
	private void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	private String generateInitialCode() {
		return "package com.example;\n\n" +
		"import java.util.*;\n" +
		"import java.io.*;\n\n" +
		"/**\n" +
		" * This is a sample Java class to demonstrate\n" +
		" * enhanced syntax highlighting features\n" +
		" */\n" +
		"@SuppressWarnings(\n" +
		"// تعليق عربي للتجربة\n" +
		"\"unused\")\n" +
		"public class MyClass {\n" +
		"    \n" +
		"    // Static variables\n" +
		"    private static final String GREETING = \"Hello, World!\";\n" +
		"    private static final int MAX_COUNT = 100;\n" +
		"    private static final double PI = 3.14159;\n" +
		"    \n" +
		"    // Instance variables\n" +
		"    private String name;\n" +
		"    private int age;\n" +
		"    private boolean isActive;\n" +
		"    \n" +
		"    /**\n" +
		"     * Constructor\n" +
		"     */\n" +
		"    public MyClass(String name, int age) {\n" +
		"        this.name = name;\n" +
		"        this.age = age;\n" +
		"        this.isActive = true;\n" +
		"    }\n" +
		"    \n" +
		"    /**\n" +
		"     * Main method - entry point of the program\n" +
		"     */\n" +
		"    public static void main(String[] args) {\n" +
		"        // Create instance\n" +
		"        MyClass obj = new MyClass(\"Ahmed\", 25);\n" +
		"        \n" +
		"        // Print greeting\n" +
		"        System.out.println(GREETING);\n" +
		"        System.out.println(\"Name: \" + obj.getName());\n" +
		"        \n" +
		"        // Demonstrate control flow\n" +
		"        if (obj.isActive()) {\n" +
		"            System.out.println(\"User is active\");\n" +
		"            \n" +
		"            for (int i = 0; i < MAX_COUNT; i++) {\n" +
		"                if (i % 10 == 0) {\n" +
		"                    System.out.printf(\"Progress: %d%%\\n\", (i * 100) / MAX_COUNT);\n" +
		"                }\n" +
		"                \n" +
		"                // Nested condition\n" +
		"                switch (i % 3) {\n" +
		"                    case 0:\n" +
		"                        processEven(i);\n" +
		"                        break;\n" +
		"                    case 1:\n" +
		"                        processOdd(i);\n" +
		"                        break;\n" +
		"                    default:\n" +
		"                        processDefault(i);\n" +
		"                        break;\n" +
		"                }\n" +
		"            }\n" +
		"            \n" +
		"            // Try-catch example\n" +
		"            try {\n" +
		"                double result = calculateArea(5.0);\n" +
		"                System.out.println(\"Area: \" + result);\n" +
		"            } catch (IllegalArgumentException e) {\n" +
		"                System.err.println(\"Error: \" + e.getMessage());\n" +
		"            } finally {\n" +
		"                System.out.println(\"Calculation completed\");\n" +
		"            }\n" +
		"        }\n" +
		"    }\n" +
		"    \n" +
		"    // Getter methods\n" +
		"    public String getName() { return name; }\n" +
		"    public int getAge() { return age; }\n" +
		"    public boolean isActive() { return isActive; }\n" +
		"    \n" +
		"    // Utility methods\n" +
		"    private static void processEven(int number) {\n" +
		"        System.out.println(\"Even: \" + number);\n" +
		"    }\n" +
		"    \n" +
		"    private static void processOdd(int number) {\n" +
		"        System.out.println(\"Odd: \" + number);\n" +
		"    }\n" +
		"    \n" +
		"    private static void processDefault(int number) {\n" +
		"        System.out.println(\"Default: \" + number);\n" +
		"    }\n" +
		"    \n" +
		"    private static double calculateArea(double radius) {\n" +
		"        if (radius <= 0) {\n" +
		"            throw new IllegalArgumentException(\"Radius must be positive\");\n" +
		"        }\n" +
		"        return PI * radius * radius;\n" +
		"    }\n" +
		"}";
	}
	
	public class LineNumbersView extends AppCompatTextView {
		private CodeEditorView mEditor;
		
		public LineNumbersView(Context context) {
			this(context, null);
		}
		
		public LineNumbersView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
		
		public void setEditor(CodeEditorView editor) {
			mEditor = editor;
		}
		
		public void applyTheme(int textColor, int bgColor) {
			getPaint().setColor(textColor);
			setBackgroundColor(bgColor);
			invalidate();
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			if (mEditor == null || mEditor.getLayout() == null) {
				super.onDraw(canvas);
				return;
			}
			
			Layout editorLayout = mEditor.getLayout();
			int firstVisibleLine = editorLayout.getLineForVertical(mEditor.getScrollY());
			int lastVisibleLine = editorLayout.getLineForVertical(mEditor.getScrollY() + mEditor.getHeight());
			
			for (int i = firstVisibleLine; i <= lastVisibleLine; i++) {
				if (i < getLineCount()) {
					int lineTop = editorLayout.getLineTop(i);
					int lineBottom = editorLayout.getLineBottom(i);
					
					String lineNumberText = String.format("%3d", i + 1);
					float textHeight = getPaint().descent() - getPaint().ascent();
					float textY = lineTop + (lineBottom - lineTop - textHeight) / 2 - getPaint().ascent();
					float textX = getWidth() - getPaddingRight() - getPaint().measureText(lineNumberText);
					canvas.drawText(lineNumberText, textX, textY, getPaint());
				}
			}
		}
	}
	
	public class CodeEditorView extends AppCompatEditText {
		private final Paint indentPaint = new Paint();
		private final Paint bracketLinePaint = new Paint();
		private final Path bracketPath = new Path();
		private float spaceWidth;
		
		public CodeEditorView(Context context) {
			this(context, null);
		}
		
		public CodeEditorView(Context context, AttributeSet attrs) {
			super(context, attrs);
			bracketLinePaint.setStyle(Paint.Style.STROKE);
			bracketLinePaint.setAntiAlias(true);
		}
		
		public void applyTheme(int bgColor, int textColor, int decorationColor) {
			setBackgroundColor(bgColor);
			setTextColor(textColor);
			indentPaint.setColor(decorationColor);
			indentPaint.setStrokeWidth(1.5f);
			indentPaint.setAlpha(80);
			bracketLinePaint.setColor(decorationColor);
			bracketLinePaint.setStrokeWidth(2.0f);
			invalidate();
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			drawIndentGuides(canvas);
			drawBracketPairLines(canvas);
			super.onDraw(canvas);
		}
		
		private void drawIndentGuides(Canvas canvas) {
			final Layout layout = getLayout();
			if (layout == null) return;
			
			if (spaceWidth == 0) {
				spaceWidth = getPaint().measureText(" ");
			}
			if (spaceWidth <= 0) return;
			
			final int indentLevelSize = 4;
			final float indentWidth = spaceWidth * indentLevelSize;
			final int firstVisibleLine = Math.max(0, layout.getLineForVertical(getScrollY()));
			final int lastVisibleLine = Math.min(getLineCount() - 1, layout.getLineForVertical(getScrollY() + getHeight()));
			
			for (int i = firstVisibleLine; i <= lastVisibleLine; i++) {
				final int lineStartOffset = layout.getLineStart(i);
				if (lineStartOffset >= getText().length()) continue;
				
				final CharSequence lineText = getText().subSequence(lineStartOffset, layout.getLineEnd(i));
				int leadingSpaces = 0;
				for (int j = 0; j < lineText.length(); j++) {
					if (lineText.charAt(j) == ' ') leadingSpaces++;
					else break;
				}
				
				final int numGuides = leadingSpaces / indentLevelSize;
				final float lineTop = layout.getLineTop(i);
				final float lineBottom = layout.getLineBottom(i);
				
				for (int j = 1; j <= numGuides; j++) {
					final float x = getPaddingLeft() + (float)j * indentWidth;
					canvas.drawLine(x, lineTop, x, lineBottom, indentPaint);
				}
			}
		}
		
		private void drawBracketPairLines(Canvas canvas) {
			Layout layout = getLayout();
			if (layout == null) return;
			
			Editable text = getText();
			if (text == null) return;
			
			Stack<Integer> openBracePositions = new Stack<>();
			
			int firstVisibleLine = layout.getLineForVertical(getScrollY());
			int lastVisibleLine = layout.getLineForVertical(getScrollY() + getHeight());
			int startSearch = layout.getLineStart(Math.max(0, firstVisibleLine - 50));
			int endSearch = layout.getLineEnd(Math.min(getLineCount() - 1, lastVisibleLine + 50));
			
			for (int i = 0; i < startSearch; i++) {
				char c = text.charAt(i);
				if (c == '{') {
					openBracePositions.push(i);
					} else if (c == '}') {
					if (!openBracePositions.isEmpty()) {
						openBracePositions.pop();
					}
				}
			}
			
			for (int i = startSearch; i < endSearch && i < text.length(); i++) {
				char c = text.charAt(i);
				if (c == '{') {
					openBracePositions.push(i);
					} else if (c == '}') {
					if (!openBracePositions.isEmpty()) {
						int openPos = openBracePositions.pop();
						int closePos = i;
						
						int openLine = layout.getLineForOffset(openPos);
						int closeLine = layout.getLineForOffset(closePos);
						
						if (openLine != closeLine && openLine <= lastVisibleLine && closeLine >= firstVisibleLine) {
							float openY = layout.getLineBottom(openLine);
							float closeY = layout.getLineTop(closeLine);
							
							int openLineStart = layout.getLineStart(openLine);
							float indentX;
							int spaceCount = 0;
							for(int k = openLineStart; k < text.length() && k < layout.getLineEnd(openLine); k++) {
								if(text.charAt(k) == ' ') {
									spaceCount++;
									} else if(text.charAt(k) == '\t') {
									spaceCount = ((spaceCount / 4) + 1) * 4;
									} else {
									break;
								}
							}
							indentX = getPaddingLeft() + (spaceCount / 4) * (spaceWidth * 4);
							
							bracketPath.reset();
							bracketPath.moveTo(indentX, openY);
							bracketPath.lineTo(indentX, closeY);
							canvas.drawPath(bracketPath, bracketLinePaint);
						}
					}
				}
			}
		}
	}
	
	private class PanZoomContainer extends ViewGroup {
		private ScaleGestureDetector scaleGestureDetector;
		private Matrix matrix = new Matrix();
		private float scaleFactor = 1.0f;
		private float translateX = 0f;
		private float translateY = 0f;
		private float lastTouchX = 0f;
		private float lastTouchY = 0f;
		private boolean isDragging = false;
		private int activePointerId = -1;
		private int touchSlop;
		private static final float MIN_ZOOM = 0.3f;
		private static final float MAX_ZOOM = 5.0f;
		
		public PanZoomContainer(Context context) {
			super(context);
			init(context);
		}
		
		public PanZoomContainer(Context context, AttributeSet attrs) {
			super(context, attrs);
			init(context);
		}
		
		private void init(Context context) {
			scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
			touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
			setWillNotDraw(false);
		}
		
		@Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			if (getChildCount() > 0) {
				View child = getChildAt(0);
				child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
			}
		}
		
		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			if (getChildCount() > 0) {
				View child = getChildAt(0);
				child.measure(
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
				);
			}
		}
		
		@Override
		public boolean onInterceptTouchEvent(MotionEvent ev) {
			if (ev.getPointerCount() > 1) {
				return true;
			}
			final int action = ev.getActionMasked();
			switch (action) {
				case MotionEvent.ACTION_DOWN:
				lastTouchX = ev.getX();
				lastTouchY = ev.getY();
				activePointerId = ev.getPointerId(0);
				isDragging = false;
				break;
				case MotionEvent.ACTION_MOVE:
				if (activePointerId == -1) break;
				final int pointerIndex = ev.findPointerIndex(activePointerId);
				if (pointerIndex == -1) {
					break;
				}
				final float x = ev.getX(pointerIndex);
				final float y = ev.getY(pointerIndex);
				
				final float dx = Math.abs(x - lastTouchX);
				final float dy = Math.abs(y - lastTouchY);
				if (dx > touchSlop || dy > touchSlop) {
					isDragging = true;
					return true;
				}
				break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
				isDragging = false;
				activePointerId = -1;
				break;
				case MotionEvent.ACTION_POINTER_UP:
				final int pointerId = ev.getPointerId(ev.getActionIndex());
				if (pointerId == activePointerId) {
					final int newPointerIndex = ev.getActionIndex() == 0 ? 1 : 0;
					lastTouchX = ev.getX(newPointerIndex);
					lastTouchY = ev.getY(newPointerIndex);
					activePointerId = ev.getPointerId(newPointerIndex);
				}
				break;
			}
			return isDragging;
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			scaleGestureDetector.onTouchEvent(ev);
			
			final int action = ev.getActionMasked();
			switch (action) {
				case MotionEvent.ACTION_DOWN:
				lastTouchX = ev.getX();
				lastTouchY = ev.getY();
				activePointerId = ev.getPointerId(0);
				break;
				case MotionEvent.ACTION_MOVE:
				final int pointerIndex = ev.findPointerIndex(activePointerId);
				if (pointerIndex == -1) {
					break;
				}
				final float x = ev.getX(pointerIndex);
				final float y = ev.getY(pointerIndex);
				
				if (!scaleGestureDetector.isInProgress()) {
					float dx = x - lastTouchX;
					float dy = y - lastTouchY;
					translateX += dx;
					translateY += dy;
					applyTransformations();
				}
				
				lastTouchX = x;
				lastTouchY = y;
				break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
				isDragging = false;
				activePointerId = -1;
				break;
				case MotionEvent.ACTION_POINTER_UP:
				final int pointerId = ev.getPointerId(ev.getActionIndex());
				if (pointerId == activePointerId) {
					final int newPointerIndex = ev.getActionIndex() == 0 ? 1 : 0;
					lastTouchX = ev.getX(newPointerIndex);
					lastTouchY = ev.getY(newPointerIndex);
					activePointerId = ev.getPointerId(newPointerIndex);
				}
				break;
			}
			return true;
		}
		
		private void constrainTranslation() {
			if (getChildCount() == 0) return;
			View child = getChildAt(0);
			float scaledWidth = child.getWidth() * scaleFactor;
			float scaledHeight = child.getHeight() * scaleFactor;
			
			float maxTranslateX = Math.max(0, scaledWidth - getWidth());
			float maxTranslateY = Math.max(0, scaledHeight - getHeight());
			
			translateX = Math.max(-maxTranslateX, Math.min(0, translateX));
			translateY = Math.max(-maxTranslateY, Math.min(0, translateY));
		}
		
		private void applyTransformations() {
			constrainTranslation();
			
			if (getChildCount() > 0) {
				View child = getChildAt(0);
				child.setTranslationX(translateX);
				child.setTranslationY(translateY);
				child.setScaleX(scaleFactor);
				child.setScaleY(scaleFactor);
				child.setPivotX(0);
				child.setPivotY(0);
			}
			invalidate();
			if (statusBar != null) {
				updateStatusBar();
			}
		}
		
		public float getCurrentZoom() {
			return scaleFactor;
		}
		
		public void zoomIn() {
			scaleFactor = Math.min(scaleFactor * 1.2f, MAX_ZOOM);
			applyTransformations();
		}
		
		public void zoomOut() {
			scaleFactor = Math.max(scaleFactor / 1.2f, MIN_ZOOM);
			applyTransformations();
		}
		
		public void resetZoom() {
			scaleFactor = 1.0f;
			translateX = 0f;
			translateY = 0f;
			applyTransformations();
		}
		
		private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				float oldScale = scaleFactor;
				scaleFactor *= detector.getScaleFactor();
				scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));
				
				float focusX = detector.getFocusX();
				float focusY = detector.getFocusY();
				
				if (oldScale > 0) {
					translateX = focusX - (focusX - translateX) * (scaleFactor / oldScale);
					translateY = focusY - (focusY - translateY) * (scaleFactor / oldScale);
				}
				
				applyTransformations();
				return true;
			}
		}
	}
	
	private void applyTheme() {
		// تعيين ألوان الصيغة الأساسية
		COLOR_KEYWORD = ThemeColors.KEYWORD_BLUE;
		COLOR_TYPE = ThemeColors.TYPE_GREEN;
		COLOR_NUMBER = ThemeColors.NUMBER_PURPLE;
		COLOR_STRING = ThemeColors.STRING_ORANGE;
		COLOR_COMMENT = ThemeColors.COMMENT_GREY;
		COLOR_METHOD = ThemeColors.METHOD_YELLOW;
		COLOR_IMPORTANT = ThemeColors.IMPORTANT_RED;
		COLOR_CONTROL_FLOW = COLOR_KEYWORD;
		COLOR_ANNOTATION = COLOR_METHOD;
		
		int bgColor, editorBgColor, lineNumBgColor, toolbarBgColor, statusBarBgColor,
		primaryTextColor, lineNumTextColor, decorationColor, toolbarTextColor;
		
		if (isDarkMode) {
			bgColor = ThemeColors.DARK_BACKGROUND;
			editorBgColor = ThemeColors.DARK_EDITOR_BACKGROUND;
			lineNumBgColor = ThemeColors.DARK_LINE_NUMBERS_BG;
			toolbarBgColor = ThemeColors.DARK_TOOLBAR_BG;
			statusBarBgColor = ThemeColors.DARK_STATUS_BAR_BG;
			primaryTextColor = ThemeColors.DARK_TEXT_PRIMARY;
			lineNumTextColor = ThemeColors.DARK_LINE_NUMBERS_TEXT;
			decorationColor = ThemeColors.DARK_DECORATION_LINES;
			toolbarTextColor = ThemeColors.DARK_TOOLBAR_TEXT;
			COLOR_OPERATOR = primaryTextColor;
			} else {
			bgColor = ThemeColors.LIGHT_BACKGROUND;
			editorBgColor = ThemeColors.LIGHT_EDITOR_BACKGROUND;
			lineNumBgColor = ThemeColors.LIGHT_LINE_NUMBERS_BG;
			toolbarBgColor = ThemeColors.LIGHT_TOOLBAR_BG;
			statusBarBgColor = ThemeColors.LIGHT_STATUS_BAR_BG;
			primaryTextColor = ThemeColors.LIGHT_TEXT_PRIMARY;
			lineNumTextColor = ThemeColors.LIGHT_LINE_NUMBERS_TEXT;
			decorationColor = ThemeColors.LIGHT_DECORATION_LINES;
			toolbarTextColor = ThemeColors.LIGHT_TOOLBAR_TEXT;
			COLOR_OPERATOR = primaryTextColor;
		}
		
		COLOR_BRACKET = COLOR_OPERATOR;
		COLOR_SEMICOLON = COLOR_OPERATOR;
		
		mainLayout.setBackgroundColor(bgColor);
		toolbarLayout.setBackgroundColor(toolbarBgColor);
		statusBar.setBackgroundColor(statusBarBgColor);
		statusBar.setTextColor(Color.WHITE);
		
		if (toolbarContent != null) {
			for (int i = 0; i < toolbarContent.getChildCount(); i++) {
				View v = toolbarContent.getChildAt(i);
				if (v instanceof Button) {
					((Button) v).setTextColor(toolbarTextColor);
				}
			}
		}
		
		if (codeEditor != null) codeEditor.applyTheme(editorBgColor, primaryTextColor, decorationColor);
		if (lineNumbers != null) lineNumbers.applyTheme(lineNumTextColor, lineNumBgColor);
		
		highlightingHandler.post(highlightingRunnable);
	}
	
	public class FastScroller extends View {
		private CodeEditorView mEditor;
		private final Paint mThumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		private final Paint mTrackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		private final RectF mThumbRect = new RectF();
		private boolean mIsDragging = false;
		private final Handler mFadeHandler = new Handler(Looper.getMainLooper());
		private final Runnable mFadeRunnable = () -> setVisibility(View.GONE);
		
		public FastScroller(Context context) {
			super(context);
			mThumbPaint.setColor(Color.parseColor("#80FFFFFF"));
			mTrackPaint.setColor(Color.parseColor("#40FFFFFF"));
			setVisibility(View.GONE);
		}
		
		public void setEditor(CodeEditorView editor) {
			mEditor = editor;
		}
		
		public void onScrollChanged() {
			if (mEditor == null || mEditor.getLayout() == null) return;
			int contentHeight = mEditor.getLayout().getHeight();
			if (contentHeight <= mEditor.getHeight()) {
				if (getVisibility() == VISIBLE) setVisibility(GONE);
				return;
			}
			
			if (getVisibility() == GONE) {
				setVisibility(VISIBLE);
			}
			mFadeHandler.removeCallbacks(mFadeRunnable);
			mFadeHandler.postDelayed(mFadeRunnable, 2000);
			invalidate();
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			if (mEditor == null || mEditor.getLayout() == null) return;
			
			int contentHeight = mEditor.getLayout().getHeight();
			if (contentHeight <= mEditor.getHeight()) {
				return;
			}
			
			float scrollProportion = (float) mEditor.getScrollY() / (contentHeight - mEditor.getHeight());
			float thumbHeight = ((float) getHeight() / contentHeight) * getHeight();
			float thumbTop = scrollProportion * (getHeight() - thumbHeight);
			
			canvas.drawRect(0, 0, getWidth(), getHeight(), mTrackPaint);
			
			mThumbRect.set(0, thumbTop, getWidth(), thumbTop + thumbHeight);
			canvas.drawRect(mThumbRect, mThumbPaint);
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (mEditor == null) return false;
			
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				if (mThumbRect.contains(event.getX(), event.getY())) {
					mIsDragging = true;
					} else {
					scrollToPosition(event.getY());
				}
				mFadeHandler.removeCallbacks(mFadeRunnable);
				return true;
				case MotionEvent.ACTION_MOVE:
				if (mIsDragging) {
					scrollToPosition(event.getY());
				}
				return true;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
				mIsDragging = false;
				mFadeHandler.postDelayed(mFadeRunnable, 2000);
				return true;
			}
			return super.onTouchEvent(event);
		}
		
		private void scrollToPosition(float y) {
			if (mEditor == null || mEditor.getLayout() == null) return;
			
			int contentHeight = mEditor.getLayout().getHeight();
			int editorHeight = mEditor.getHeight();
			if (contentHeight <= editorHeight) return;
			
			float scrollProportion = y / getHeight();
			int newScrollY = (int) (scrollProportion * (contentHeight - editorHeight));
			mEditor.scrollTo(mEditor.getScrollX(), newScrollY);
			invalidate();
		}
	}
}