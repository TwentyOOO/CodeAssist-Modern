package com.keyboard2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// إنشاء الواجهة برمجيًا بدلاً من XML
		TextView instructions = new TextView(this);
		instructions.setText("لتفعيل لوحة المفاتيح:\n\n1. اضغط على الزر أدناه لفتح إعدادات اللغة والإدخال.\n\n2. قم بتفعيل \"Dual IME (AR/EN)\" من قائمة لوحات المفاتيح.\n\n3. افتح أي تطبيق للكتابة واختر لوحة المفاتيح الجديدة كطريقة الإدخال الافتراضية.");
		instructions.setGravity(Gravity.CENTER_HORIZONTAL);
		instructions.setPadding(50, 50, 50, 50);
		instructions.setTextSize(18);
		
		Button settingsButton = new Button(this);
		settingsButton.setText("فتح إعدادات لوحة المفاتيح");
		settingsButton.setOnClickListener(v -> {
			// يفتح شاشة إعدادات لوحات المفاتيح المتاحة في النظام
			startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
		});
		
		// استخدام LinearLayout بسيط لترتيب العناصر
		android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
		layout.setOrientation(android.widget.LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER);
		layout.addView(instructions);
		layout.addView(settingsButton);
		
		setContentView(layout);
	}
}