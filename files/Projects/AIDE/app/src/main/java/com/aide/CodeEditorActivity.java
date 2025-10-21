package com.aide;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeEditorActivity extends AppCompatActivity {
	
	private EditText codeEditor;
	private TextView lineNumbers;
	private TextView statusBar;
	private boolean isEditing = false;
	
	@Override
	protected void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// --- الواجهة الرئيسية ---
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setBackgroundColor(Color.parseColor("#1E1E1E"));
		
		// --- شريط الأدوات العلوي ---
		LinearLayout toolbarLayout = new LinearLayout(this);
		toolbarLayout.setOrientation(LinearLayout.HORIZONTAL);
		toolbarLayout.setBackgroundColor(Color.parseColor("#2D2D30"));
		toolbarLayout.setPadding(8, 8, 8, 8);
		
		Button newButton = createToolbarButton("جديد");
		Button openButton = createToolbarButton("فتح");
		Button saveButton = createToolbarButton("حفظ");
		Button undoButton = createToolbarButton("تراجع");
		Button redoButton = createToolbarButton("إعادة");
		Button clearButton = createToolbarButton("مسح");
		
		toolbarLayout.addView(newButton);
		toolbarLayout.addView(openButton);
		toolbarLayout.addView(saveButton);
		toolbarLayout.addView(undoButton);
		toolbarLayout.addView(redoButton);
		toolbarLayout.addView(clearButton);
		
		mainLayout.addView(toolbarLayout);
		
		// --- منطقة التحرير ---
		LinearLayout editorContainer = new LinearLayout(this);
		editorContainer.setOrientation(LinearLayout.HORIZONTAL);
		editorContainer.setBackgroundColor(Color.parseColor("#1E1E1E"));
		LinearLayout.LayoutParams editorContainerParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
		
		// --- أرقام الأسطر ---
		lineNumbers = new TextView(this);
		lineNumbers.setPadding(12, 16, 12, 16);
		lineNumbers.setTextColor(Color.parseColor("#858585"));
		lineNumbers.setTextSize(12);
		lineNumbers.setTypeface(Typeface.MONOSPACE);
		lineNumbers.setBackgroundColor(Color.parseColor("#262626"));
		lineNumbers.setGravity(Gravity.TOP | Gravity.END);
		LinearLayout.LayoutParams lineNumbersParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
		
		// --- فاصل ---
		View separator = new View(this);
		separator.setBackgroundColor(Color.parseColor("#3E3E42"));
		LinearLayout.LayoutParams separatorParams = new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.MATCH_PARENT);
		
		// --- محرر الكود ---
		codeEditor = new EditText(this);
		codeEditor.setGravity(Gravity.TOP | Gravity.START);
		codeEditor.setBackgroundColor(Color.parseColor("#1E1E1E"));
		codeEditor.setTextColor(Color.parseColor("#D4D4D4"));
		codeEditor.setPadding(16, 16, 16, 16);
		codeEditor.setTextSize(12);
		codeEditor.setTypeface(Typeface.MONOSPACE);
		codeEditor.setSingleLine(false);
		codeEditor.setHorizontallyScrolling(true);
		codeEditor.setVerticalScrollBarEnabled(true);
		codeEditor.setHorizontalScrollBarEnabled(true);
		
		ScrollView editorScrollView = new ScrollView(this);
		editorScrollView.addView(codeEditor);
		LinearLayout.LayoutParams editorParams = new LinearLayout.LayoutParams(
		0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
		
		editorContainer.addView(lineNumbers, lineNumbersParams);
		editorContainer.addView(separator, separatorParams);
		editorContainer.addView(editorScrollView, editorParams);
		
		mainLayout.addView(editorContainer, editorContainerParams);
		
		// --- شريط الحالة ---
		statusBar = new TextView(this);
		statusBar.setText("السطر: 1, العمود: 1 | الأحرف: 0");
		statusBar.setTextColor(Color.parseColor("#CCCCCC"));
		statusBar.setTextSize(10);
		statusBar.setBackgroundColor(Color.parseColor("#007ACC"));
		statusBar.setPadding(16, 8, 16, 8);
		mainLayout.addView(statusBar);
		
		// --- إعداد الأحداث ---
		setupEditor();
		setupButtons(newButton, openButton, saveButton, undoButton, redoButton, clearButton);
		
		// --- تحميل الكود الافتراضي ---
		String initialCode = generateInitialCode();
		codeEditor.setText(initialCode);
		codeEditor.post(this::updateAll);
		
		setContentView(mainLayout);
	}
	
	private Button createToolbarButton(String text) {
		Button button = new Button(this);
		button.setText(text);
		button.setTextColor(Color.WHITE);
		button.setTextSize(10);
		button.setBackgroundColor(Color.parseColor("#0E639C"));
		button.setPadding(16, 8, 16, 8);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(4, 0, 4, 0);
		button.setLayoutParams(params);
		return button;
	}
	
	private void setupEditor() {
		codeEditor.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void afterTextChanged(Editable editable) {
				if (isEditing) return;
				isEditing = true;
				updateAll();
				highlightSyntax(editable);
				isEditing = false;
			}
		});
		
		// دعم Tab
		codeEditor.setOnKeyListener((v, keyCode, event) -> {
			if (keyCode == KeyEvent.KEYCODE_TAB && event.getAction() == KeyEvent.ACTION_DOWN) {
				int start = codeEditor.getSelectionStart();
				int end = codeEditor.getSelectionEnd();
				codeEditor.getText().replace(start, end, "    ");
				return true;
			}
			return false;
		});
		
		// تحديث موضع المؤشر
		codeEditor.setOnClickListener(v -> updateCursorPosition());
	}
	
	private void setupButtons(Button newBtn, Button openBtn, Button saveBtn,
	Button undoBtn, Button redoBtn, Button clearBtn) {
		
		newBtn.setOnClickListener(v -> {
			codeEditor.setText("");
			updateAll();
			showToast("ملف جديد");
		});
		
		saveBtn.setOnClickListener(v -> saveCodeToFile(codeEditor.getText().toString()));
		
		clearBtn.setOnClickListener(v -> {
			codeEditor.setText(generateInitialCode());
			updateAll();
			showToast("تم المسح");
		});
		
		undoBtn.setOnClickListener(v -> {
			// تنفيذ التراجع يدوياً - سيتم إضافته لاحقاً
			showToast("التراجع غير متاح حالياً");
		});
		
		redoBtn.setOnClickListener(v -> {
			// تنفيذ الإعادة يدوياً - سيتم إضافته لاحقاً
			showToast("الإعادة غير متاحة حالياً");
		});
	}
	
	private void updateAll() {
		updateLineNumbers();
		updateCursorPosition();
	}
	
	private void updateLineNumbers() {
		String text = codeEditor.getText().toString();
		// حساب دقيق للأسطر
		int lineCount = text.isEmpty() ? 1 : text.split("\n", -1).length;
		
		StringBuilder linesText = new StringBuilder();
		for (int i = 1; i <= lineCount; i++) {
			linesText.append(String.format("%3d", i));
			if (i < lineCount) {
				linesText.append("\n");
			}
		}
		lineNumbers.setText(linesText.toString());
	}
	
	private void updateCursorPosition() {
		int cursor = codeEditor.getSelectionStart();
		String text = codeEditor.getText().toString();
		
		// حساب السطر والعمود
		int line = 1;
		int column = 1;
		for (int i = 0; i < cursor && i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				line++;
				column = 1;
				} else {
				column++;
			}
		}
		
		int charCount = text.length();
		int wordCount = text.trim().isEmpty() ? 0 : text.trim().split("\\s+").length;
		
		statusBar.setText(String.format("السطر: %d, العمود: %d | الأحرف: %d | الكلمات: %d",
		line, column, charCount, wordCount));
	}
	
	private void highlightSyntax(Editable editable) {
		// إزالة التنسيق السابق
		ForegroundColorSpan[] spans = editable.getSpans(0, editable.length(), ForegroundColorSpan.class);
		for (ForegroundColorSpan span : spans) {
			editable.removeSpan(span);
		}
		
		String text = editable.toString();
		
		// الكلمات المفتاحية
		highlightPattern(editable, "\\b(public|private|protected|static|final|abstract|synchronized|native|volatile|transient)\\b", "#569CD6");
		highlightPattern(editable, "\\b(class|interface|enum|extends|implements)\\b", "#4EC9B0");
		highlightPattern(editable, "\\b(if|else|for|while|do|switch|case|default|break|continue|return)\\b", "#C586C0");
		highlightPattern(editable, "\\b(try|catch|finally|throw|throws)\\b", "#C586C0");
		highlightPattern(editable, "\\b(int|double|float|boolean|char|byte|short|long|void|String)\\b", "#4EC9B0");
		highlightPattern(editable, "\\b(true|false|null)\\b", "#569CD6");
		highlightPattern(editable, "\\b(this|super|new)\\b", "#569CD6");
		highlightPattern(editable, "\\b(import|package)\\b", "#C586C0");
		
		// الأرقام
		highlightPattern(editable, "\\b\\d+(\\.\\d+)?[fFdDlL]?\\b", "#B5CEA8");
		
		// النصوص
		highlightPattern(editable, "\"([^\"\\\\]|\\\\.)*\"", "#CE9178");
		highlightPattern(editable, "'([^'\\\\]|\\\\.)*'", "#CE9178");
		
		// التعليقات
		highlightPattern(editable, "//.*$", "#6A9955");
		highlightPattern(editable, "/\\*[\\s\\S]*?\\*/", "#6A9955");
		
		// الحواف والأقواس
		highlightPattern(editable, "[{}()\\[\\];,.]", "#D4D4D4");
	}
	
	private void highlightPattern(Editable editable, String pattern, String color) {
		Pattern p = Pattern.compile(pattern, Pattern.MULTILINE);
		Matcher m = p.matcher(editable.toString());
		while (m.find()) {
			editable.setSpan(
			new ForegroundColorSpan(Color.parseColor(color)),
			m.start(), m.end(),
			Editable.SPAN_EXCLUSIVE_EXCLUSIVE
			);
		}
	}
	
	private void saveCodeToFile(String code) {
		try {
			FileOutputStream fos = openFileOutput("code.java", MODE_PRIVATE);
			fos.write(code.getBytes());
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
		return "package com.example.app;\n\n" +
		"import androidx.appcompat.app.AppCompatActivity;\n" +
		"import android.os.Bundle;\n\n" +
		"public class MainActivity extends AppCompatActivity {\n\n" +
		"    @Override\n" +
		"    protected void onCreate(Bundle savedInstanceState) {\n" +
		"        super.onCreate(savedInstanceState);\n" +
		"        setContentView(R.layout.activity_main);\n" +
		"        \n" +
		"        // Your code here\n" +
		"        \n" +
		"    }\n" +
		"}";
	}
}