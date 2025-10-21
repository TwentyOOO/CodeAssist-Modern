package com.keyboardtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	
	private TextView statusText;
	private Button enableButton, selectButton;
	private Switch switchVibration, switchSound, switchPreview;
	private SharedPreferences preferences;
	private InputMethodManager imm;
	
	private static final String PREFS_NAME = "keyboard_prefs";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeViews();
		loadPreferences();
		setupListeners();
	}
	
	private void initializeViews() {
		statusText    = findViewById(R.id.status_text);
		enableButton  = findViewById(R.id.enable_button);
		selectButton  = findViewById(R.id.select_button);
		switchVibration = findViewById(R.id.switch_vibration);
		switchSound     = findViewById(R.id.switch_sound);
		switchPreview   = findViewById(R.id.switch_preview);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	}
	
	private void loadPreferences() {
		switchVibration.setChecked(preferences.getBoolean("vibration", true));
		switchSound.setChecked(preferences.getBoolean("sound", true));
		switchPreview.setChecked(preferences.getBoolean("preview", true));
	}
	
	private void setupListeners() {
		enableButton.setOnClickListener(v -> openInputMethodSettings());
		selectButton.setOnClickListener(v -> showInputMethodPicker());
		
		switchVibration.setOnCheckedChangeListener((btn, isChecked) ->
		savePreference("vibration", isChecked));
		switchSound.setOnCheckedChangeListener((btn, isChecked) ->
		savePreference("sound", isChecked));
		switchPreview.setOnCheckedChangeListener((btn, isChecked) ->
		savePreference("preview", isChecked));
	}
	
	private void openInputMethodSettings() {
		startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
		Toast.makeText(this,
		"الرجاء تفعيل لوحة المفاتيح من القائمة",
		Toast.LENGTH_LONG).show();
	}
	
	private void showInputMethodPicker() {
		if (imm != null) {
			imm.showInputMethodPicker();
		}
	}
	
	private void savePreference(String key, boolean value) {
		preferences.edit().putBoolean(key, value).apply();
	}
	
	private void updateKeyboardStatus() {
		boolean isEnabled  = isKeyboardEnabled();
		boolean isSelected = isKeyboardSelected();
		
		if (isSelected) {
			statusText.setText("✔ لوحة المفاتيح نشطة وجاهزة!");
			statusText.setTextColor(Color.parseColor("#4CAF50"));
			enableButton.setEnabled(false);
			selectButton.setEnabled(false);
			
			} else if (isEnabled) {
			statusText.setText("⚠ لوحة المفاتيح مفعلة لكن غير محددة");
			statusText.setTextColor(Color.parseColor("#FF9800"));
			enableButton.setEnabled(false);
			selectButton.setEnabled(true);
			
			} else {
			statusText.setText("✘ لوحة المفاتيح غير مفعلة");
			statusText.setTextColor(Color.parseColor("#F44336"));
			enableButton.setEnabled(true);
			selectButton.setEnabled(true);
		}
	}
	
	private boolean isKeyboardEnabled() {
		if (imm == null) return false;
		List<InputMethodInfo> enabledMethods = imm.getEnabledInputMethodList();
		for (InputMethodInfo imi : enabledMethods) {
			if (imi.getPackageName().equals(getPackageName())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isKeyboardSelected() {
		String currentIme = Secure.getString(
		getContentResolver(),
		Secure.DEFAULT_INPUT_METHOD
		);
		return currentIme != null && currentIme.contains(getPackageName());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateKeyboardStatus();
	}
}