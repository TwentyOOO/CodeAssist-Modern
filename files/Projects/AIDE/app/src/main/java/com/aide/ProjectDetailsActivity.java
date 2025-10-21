package com.aide;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProjectDetailsActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ScrollView scrollView = new ScrollView(this);
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setPadding(40, 40, 40, 40);
		
		TextView title = new TextView(this);
		title.setText("Templates");
		title.setTextSize(28);
		title.setTypeface(null, Typeface.BOLD);
		title.setPadding(0, 0, 0, 80);
		mainLayout.addView(title);
		
		// جعل الحقول final للوصول إليها لاحقاً
		final EditText appNameInput = new EditText(this);
		appNameInput.setHint("App name");
		appNameInput.setBackgroundResource(android.R.drawable.edit_text);
		appNameInput.setPadding(32, 32, 32, 32);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT,
		LinearLayout.LayoutParams.WRAP_CONTENT
		);
		layoutParams.setMargins(0, 0, 0, 40);
		mainLayout.addView(appNameInput, layoutParams);
		
		final EditText packageNameInput = new EditText(this);
		packageNameInput.setText("com.my.myapplication");
		packageNameInput.setBackgroundResource(android.R.drawable.edit_text);
		packageNameInput.setPadding(32, 32, 32, 32);
		mainLayout.addView(packageNameInput, layoutParams);
		
		// ... (بقية كود الواجهة يبقى كما هو)
		
		LinearLayout saveLocationLayout = new LinearLayout(this);
		saveLocationLayout.setOrientation(LinearLayout.HORIZONTAL);
		saveLocationLayout.setBackgroundResource(android.R.drawable.edit_text);
		EditText saveLocationInput = new EditText(this);
		saveLocationInput.setText("/storage/emulated/0/Android/da");
		saveLocationInput.setBackground(null);
		saveLocationInput.setPadding(32, 32, 32, 32);
		LinearLayout.LayoutParams saveInputParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
		saveLocationLayout.addView(saveLocationInput, saveInputParams);
		ImageButton folderButton = new ImageButton(this);
		folderButton.setImageResource(android.R.drawable.ic_menu_save);
		folderButton.setBackground(null);
		LinearLayout.LayoutParams folderBtnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
		folderBtnParams.setMargins(0,0,16,0);
		saveLocationLayout.addView(folderButton, folderBtnParams);
		mainLayout.addView(saveLocationLayout, layoutParams);
		TextView helperText = new TextView(this);
		helperText.setText("Due to Android 11 storage restrictions, you can only save files on the app's internal storage.");
		helperText.setTextSize(12);
		helperText.setPadding(8, 8, 8, 40);
		mainLayout.addView(helperText);
		Spinner languageSpinner = new Spinner(this, Spinner.MODE_DROPDOWN);
		String[] languages = {"Java", "Kotlin"};
		ArrayAdapter<String> langAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, languages);
		languageSpinner.setAdapter(langAdapter);
		languageSpinner.setBackgroundResource(android.R.drawable.btn_dropdown);
		LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		spinnerParams.setMargins(0, 0, 0, 40);
		mainLayout.addView(languageSpinner, spinnerParams);
		Spinner sdkSpinner = new Spinner(this, Spinner.MODE_DROPDOWN);
		String[] sdks = {"API 21: Android 5.0 (Lollipop)", "API 22: Android 5.1 (Lollipop)", "API 23: Android 6.0 (Marshmallow)"};
		ArrayAdapter<String> sdkAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sdks);
		sdkSpinner.setAdapter(sdkAdapter);
		sdkSpinner.setBackgroundResource(android.R.drawable.btn_dropdown);
		mainLayout.addView(sdkSpinner, spinnerParams);
		RelativeLayout bottomButtonsLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams bottomParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		bottomParams.setMargins(0, 80, 0, 0);
		Button previousButton = new Button(this, null, android.R.attr.borderlessButtonStyle);
		previousButton.setText("Previous");
		previousButton.setTextColor(Color.DKGRAY);
		RelativeLayout.LayoutParams prevBtnParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		prevBtnParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		bottomButtonsLayout.addView(previousButton, prevBtnParams);
		Button createButton = new Button(this);
		createButton.setText("Create");
		createButton.setBackgroundColor(Color.parseColor("#C64218"));
		createButton.setTextColor(Color.WHITE);
		RelativeLayout.LayoutParams createBtnParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		createBtnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		bottomButtonsLayout.addView(createButton, createBtnParams);
		mainLayout.addView(bottomButtonsLayout, bottomParams);
		
		// -- تعديل حدث الضغط على زر "Create" --
		createButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String appName = appNameInput.getText().toString();
				String packageName = packageNameInput.getText().toString(); // <-- الحصول على اسم الحزمة
				
				if (appName.isEmpty() || packageName.isEmpty()) {
					Toast.makeText(ProjectDetailsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
					} else {
					Intent intent = new Intent(ProjectDetailsActivity.this, CodeEditorActivity.class);
					// **إضافة البيانات إلى الـ Intent**
					intent.putExtra("APP_NAME", appName);
					intent.putExtra("PACKAGE_NAME", packageName);
					startActivity(intent);
				}
			}
		});
		
		scrollView.addView(mainLayout);
		setContentView(scrollView);
	}
}