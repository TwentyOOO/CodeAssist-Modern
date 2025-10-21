package com.aide;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getSupportActionBar() != null) {
			getSupportActionBar().hide();
		}
		
		RelativeLayout mainLayout = new RelativeLayout(this);
		mainLayout.setLayoutParams(new RelativeLayout.LayoutParams(
		ViewGroup.LayoutParams.MATCH_PARENT,
		ViewGroup.LayoutParams.MATCH_PARENT
		));
		mainLayout.setBackgroundColor(Color.WHITE);
		
		TextView title = new TextView(this);
		title.setText("AIDE");
		title.setTextColor(Color.BLACK);
		title.setTextSize(28);
		title.setTypeface(null, Typeface.BOLD);
		
		RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
		ViewGroup.LayoutParams.WRAP_CONTENT,
		ViewGroup.LayoutParams.WRAP_CONTENT
		);
		titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		titleParams.setMargins(0, 60, 0, 0);
		mainLayout.addView(title, titleParams);
		
		Button addButton = new Button(this);
		addButton.setText("+");
		addButton.setTextColor(Color.BLACK);
		addButton.setTextSize(24);
		
		GradientDrawable buttonShape = new GradientDrawable();
		buttonShape.setShape(GradientDrawable.RECTANGLE);
		buttonShape.setColor(Color.parseColor("#AED581"));
		buttonShape.setCornerRadius(25);
		addButton.setBackground(buttonShape);
		
		RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(200, 200);
		buttonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		buttonParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		buttonParams.setMargins(0, 0, 80, 80);
		mainLayout.addView(addButton, buttonParams);
		
		// *** التعديل الجديد هنا ***
		// عند الضغط على الزر، افتح صفحة القوالب
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// إنشاء نية (Intent) للانتقال من الصفحة الحالية إلى صفحة القوالب
				Intent intent = new Intent(MainActivity.this, TemplatesActivity.class);
				startActivity(intent); // ابدأ النشاط الجديد
			}
		});
		
		setContentView(mainLayout);
	}
}