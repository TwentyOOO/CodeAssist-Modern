package com.editor.view;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
	
	private static final int REQUEST_CODE_STORAGE = 100;
	private static final int REQUEST_CODE_MANAGE_STORAGE = 101;
	private static final String PREFS_NAME = "ViewEditorPrefs";
	private static final String KEY_PROJECT_PATH = "project_path";
	
	// UI Elements
	private MaterialButton btnSelectProject;
	private MaterialButton btnExport;
	private MaterialButton btnOpenFile;
	private MaterialButton btnSettings;
	private TextView tvProjectName;
	private TextView tvProjectPath;
	private TextView tvStatus;
	private TextView tvFileCount;
	private CardView cardProject;
	private CardView cardStatus;
	private LinearLayout layoutEmpty;
	private ImageView imgEmptyState;
	private View progressOverlay;
	
	private String currentProjectPath = null;
	private String lastExportedFile = null;
	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initViews();
		prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
		
		// استرجاع المسار المحفوظ
		currentProjectPath = prefs.getString(KEY_PROJECT_PATH, null);
		
		checkPermissions();
		setupClickListeners();
		updateUI();
		
		// Animation للدخول
		animateEntrance();
	}
	
	private void initViews() {
		btnSelectProject = findViewById(R.id.btnSelectProject);
		btnExport = findViewById(R.id.btnExport);
		btnOpenFile = findViewById(R.id.btnOpenFile);
		btnSettings = findViewById(R.id.btnSettings);
		tvProjectName = findViewById(R.id.tvProjectName);
		tvProjectPath = findViewById(R.id.tvProjectPath);
		tvStatus = findViewById(R.id.tvStatus);
		tvFileCount = findViewById(R.id.tvFileCount);
		cardProject = findViewById(R.id.cardProject);
		cardStatus = findViewById(R.id.cardStatus);
		layoutEmpty = findViewById(R.id.layoutEmpty);
		imgEmptyState = findViewById(R.id.imgEmptyState);
		progressOverlay = findViewById(R.id.progressOverlay);
	}
	
	private void setupClickListeners() {
		btnSelectProject.setOnClickListener(v -> {
			animateClick(v);
			showProjectPicker();
		});
		
		btnExport.setOnClickListener(v -> {
			animateClick(v);
			exportProject();
		});
		
		btnOpenFile.setOnClickListener(v -> {
			animateClick(v);
			openExportedFile();
		});
		
		btnSettings.setOnClickListener(v -> {
			animateClick(v);
			showSettings();
		});
	}
	
	private void checkPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			// Android 11+
			if (!Environment.isExternalStorageManager()) {
				showPermissionDialog();
			}
			} else {
			// Android 10 وأقل
			if (ContextCompat.checkSelfPermission(this,
			Manifest.permission.WRITE_EXTERNAL_STORAGE)
			!= PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this,
				new String[]{
					Manifest.permission.READ_EXTERNAL_STORAGE,
					Manifest.permission.WRITE_EXTERNAL_STORAGE
				}, REQUEST_CODE_STORAGE);
			}
		}
	}
	
	private void showPermissionDialog() {
		new MaterialAlertDialogBuilder(this)
		.setTitle("⚠️ صلاحية مطلوبة")
		.setMessage("التطبيق يحتاج صلاحية الوصول لجميع الملفات لكي يعمل بشكل صحيح.\n\n" +
		"سيتم نقلك لصفحة الإعدادات لمنح الصلاحية.")
		.setPositiveButton("منح الصلاحية", (dialog, which) -> {
			try {
				Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
				intent.setData(Uri.parse("package:" + getPackageName()));
				startActivityForResult(intent, REQUEST_CODE_MANAGE_STORAGE);
				} catch (Exception e) {
				Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
				startActivityForResult(intent, REQUEST_CODE_MANAGE_STORAGE);
			}
		})
		.setNegativeButton("إلغاء", (dialog, which) -> {
			showSnackbar("⚠️ التطبيق لن يعمل بدون الصلاحية", true);
		})
		.setCancelable(false)
		.show();
	}
	
	private void showProjectPicker() {
		List<File> projects = findProjects();
		
		if (projects.isEmpty()) {
			showManualPathInput();
			return;
		}
		
		String[] projectNames = new String[projects.size() + 1];
		for (int i = 0; i < projects.size(); i++) {
			projectNames[i] = "📁 " + projects.get(i).getName();
		}
		projectNames[projects.size()] = "➕ إدخال مسار يدوياً";
		
		new MaterialAlertDialogBuilder(this)
		.setTitle("اختر المشروع")
		.setItems(projectNames, (dialog, which) -> {
			if (which == projects.size()) {
				showManualPathInput();
				} else {
				selectProject(projects.get(which));
			}
		})
		.setNegativeButton("إلغاء", null)
		.show();
	}
	
	private List<File> findProjects() {
		List<File> projects = new ArrayList<>();
		
		String[] possiblePaths = {
			"/storage/emulated/0/Android/data/com.tyron.code/files/Projects",
			"/storage/emulated/0/CodeAssist/Projects",
			"/storage/emulated/0/Projects",
			Environment.getExternalStorageDirectory() + "/Android/data/com.tyron.code/files/Projects"
		};
		
		for (String path : possiblePaths) {
			File dir = new File(path);
			if (dir.exists() && dir.isDirectory()) {
				File[] files = dir.listFiles();
				if (files != null) {
					for (File file : files) {
						if (file.isDirectory()) {
							projects.add(file);
						}
					}
				}
				if (!projects.isEmpty()) break; // وجدنا مشاريع، لا داعي للبحث أكثر
			}
		}
		
		return projects;
	}
	
	private void showManualPathInput() {
		final android.widget.EditText input = new android.widget.EditText(this);
		input.setHint("مثال: /storage/emulated/0/Projects/MyProject");
		input.setPadding(50, 30, 50, 30);
		
		new MaterialAlertDialogBuilder(this)
		.setTitle("إدخال المسار يدوياً")
		.setMessage("أدخل المسار الكامل لمجلد المشروع:")
		.setView(input)
		.setPositiveButton("تأكيد", (dialog, which) -> {
			String path = input.getText().toString().trim();
			File project = new File(path);
			if (project.exists() && project.isDirectory()) {
				selectProject(project);
				} else {
				showSnackbar("❌ المسار غير صحيح أو غير موجود", true);
			}
		})
		.setNegativeButton("إلغاء", null)
		.show();
	}
	
	private void selectProject(File projectDir) {
		currentProjectPath = projectDir.getAbsolutePath();
		
		// حفظ المسار
		prefs.edit().putString(KEY_PROJECT_PATH, currentProjectPath).apply();
		
		// تحديث الواجهة
		updateUI();
		
		// Animation
		animateCardIn(cardProject);
		
		showSnackbar("✅ تم اختيار المشروع: " + projectDir.getName(), false);
	}
	
	private void exportProject() {
		if (currentProjectPath == null) {
			showSnackbar("⚠️ الرجاء اختيار مشروع أولاً", true);
			return;
		}
		
		showProgress(true);
		tvStatus.setText("جاري التصدير...");
		
		new Thread(() -> {
			try {
				JsonExporter exporter = new JsonExporter(currentProjectPath);
				lastExportedFile = exporter.exportToJson();
				
				runOnUiThread(() -> {
					showProgress(false);
					if (lastExportedFile != null) {
						tvStatus.setText("✅ تم التصدير بنجاح!");
						btnOpenFile.setVisibility(View.VISIBLE);
						animateSuccess();
						showSnackbar("تم حفظ الملف: " + new File(lastExportedFile).getName(), false);
						} else {
						tvStatus.setText("❌ فشل التصدير");
						showSnackbar("حدث خطأ أثناء التصدير", true);
					}
				});
				
				} catch (Exception e) {
				runOnUiThread(() -> {
					showProgress(false);
					tvStatus.setText("❌ خطأ: " + e.getMessage());
					showSnackbar("خطأ: " + e.getMessage(), true);
				});
			}
		}).start();
	}
	
	private void openExportedFile() {
		if (lastExportedFile == null || !new File(lastExportedFile).exists()) {
			showSnackbar("لا يوجد ملف لفتحه", true);
			return;
		}
		
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.parse("file://" + lastExportedFile);
			intent.setDataAndType(uri, "application/json");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(Intent.createChooser(intent, "فتح الملف باستخدام"));
			} catch (Exception e) {
			showSnackbar("لا يمكن فتح الملف", true);
		}
	}
	
	private void showSettings() {
		new MaterialAlertDialogBuilder(this)
		.setTitle("⚙️ الإعدادات")
		.setItems(new String[]{
			"🗑️ مسح المسار المحفوظ",
			"📂 فتح مجلد التصدير",
			"ℹ️ حول التطبيق"
			}, (dialog, which) -> {
			switch (which) {
				case 0:
				prefs.edit().clear().apply();
				currentProjectPath = null;
				lastExportedFile = null;
				updateUI();
				showSnackbar("تم مسح البيانات", false);
				break;
				case 1:
				openDownloadsFolder();
				break;
				case 2:
				showAbout();
				break;
			}
		})
		.show();
	}
	
	private void openDownloadsFolder() {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(
			Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
			intent.setDataAndType(uri, "resource/folder");
			startActivity(Intent.createChooser(intent, "فتح المجلد باستخدام"));
			} catch (Exception e) {
			showSnackbar("لا يمكن فتح المجلد", true);
		}
	}
	
	private void showAbout() {
		new MaterialAlertDialogBuilder(this)
		.setTitle("ViewEditor Exporter")
		.setMessage("📱 النسخة: 4.0 Final\n" +
		"👨‍💻 تطوير: ViewEditor Team\n" +
		"🎨 تصميم: Material Design 3\n\n" +
		"✨ تطبيق لتصدير مشاريع CodeAssist إلى JSON")
		.setPositiveButton("حسناً", null)
		.show();
	}
	
	private void updateUI() {
		if (currentProjectPath != null) {
			File project = new File(currentProjectPath);
			tvProjectName.setText(project.getName());
			tvProjectPath.setText(currentProjectPath);
			
			// حساب عدد الملفات
			int fileCount = countFiles(project);
			tvFileCount.setText("📄 " + fileCount + " ملف");
			
			layoutEmpty.setVisibility(View.GONE);
			cardProject.setVisibility(View.VISIBLE);
			btnExport.setEnabled(true);
			} else {
			layoutEmpty.setVisibility(View.VISIBLE);
			cardProject.setVisibility(View.GONE);
			btnExport.setEnabled(false);
			btnOpenFile.setVisibility(View.GONE);
		}
	}
	
	private int countFiles(File dir) {
		int count = 0;
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						count++;
						} else if (file.isDirectory()) {
						count += countFiles(file);
					}
				}
			}
		}
		return count;
	}
	
	private void showProgress(boolean show) {
		progressOverlay.setVisibility(show ? View.VISIBLE : View.GONE);
	}
	
	private void showSnackbar(String message, boolean isError) {
		Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message,
		isError ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);
		if (isError) {
			snackbar.setBackgroundTint(getColor(android.R.color.holo_red_dark));
			} else {
			snackbar.setBackgroundTint(getColor(android.R.color.holo_green_dark));
		}
		snackbar.show();
	}
	
	// Animations
	private void animateEntrance() {
		findViewById(R.id.headerCard).setAlpha(0f);
		findViewById(R.id.headerCard).animate()
		.alpha(1f)
		.setDuration(600)
		.start();
	}
	
	private void animateClick(View view) {
		view.animate()
		.scaleX(0.95f)
		.scaleY(0.95f)
		.setDuration(100)
		.withEndAction(() ->
		view.animate()
		.scaleX(1f)
		.scaleY(1f)
		.setDuration(100)
		.start()
		).start();
	}
	
	private void animateCardIn(View view) {
		view.setAlpha(0f);
		view.setTranslationY(50f);
		view.animate()
		.alpha(1f)
		.translationY(0f)
		.setDuration(400)
		.setInterpolator(new AccelerateDecelerateInterpolator())
		.start();
	}
	
	private void animateSuccess() {
		ObjectAnimator.ofFloat(cardStatus, "rotation", 0f, 5f, -5f, 0f)
		.setDuration(500)
		.start();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_MANAGE_STORAGE) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
				if (Environment.isExternalStorageManager()) {
					showSnackbar("✅ تم منح الصلاحية", false);
					} else {
					showSnackbar("⚠️ لم يتم منح الصلاحية", true);
				}
			}
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions,
	int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_CODE_STORAGE) {
			if (grantResults.length > 0 &&
			grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				showSnackbar("✅ تم منح الصلاحية", false);
				} else {
				showSnackbar("⚠️ لم يتم منح الصلاحية", true);
			}
		}
	}
}