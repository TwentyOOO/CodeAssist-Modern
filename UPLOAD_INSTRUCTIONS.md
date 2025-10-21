# خطوات رفع المشروع إلى GitHub 🚀

## الخطوة 1: إنشاء Repository جديد على GitHub

1. اذهب إلى [github.com](https://github.com)
2. اضغط على زر **+** في الأعلى واختر **New repository**
3. املأ المعلومات التالية:
   - **Repository name**: `CodeAssist-Modern`
   - **Description**: `CodeAssist with modern Ocean Wave design system`
   - **Visibility**: اختر Public أو Private حسب رغبتك
   - ⚠️ **لا تختر** "Add a README file" (لأننا أنشأناه بالفعل)
4. اضغط **Create repository**

## الخطوة 2: ربط المشروع المحلي بـ GitHub

افتح Command Prompt أو Terminal في مجلد المشروع `C:\Users\ucobr\CodeAssist-Modern` ونفذ الأوامر التالية:

```bash
# استبدل YOUR_USERNAME باسم المستخدم الخاص بك على GitHub
git remote add origin https://github.com/YOUR_USERNAME/CodeAssist-Modern.git

# رفع الملفات إلى GitHub
git branch -M main
git push -u origin main
```

## الخطوة 3: رفع ملف APK على GitHub Releases

⚠️ **ملاحظة مهمة**: ملف APK كبير جداً (93MB)، لذلك يجب رفعه على GitHub Releases بدلاً من رفعه في Repository مباشرة.

### خطوات رفع Release:

1. اذهب إلى صفحة Repository على GitHub
2. اضغط على تبويب **Releases** (على اليمين)
3. اضغط **Create a new release**
4. املأ المعلومات:
   - **Tag version**: `v1.0.0`
   - **Release title**: `CodeAssist Modern v1.0.0`
   - **Description**: انسخ من CHANGELOG.md
5. في قسم **Attach binaries**:
   - اسحب وأفلت ملف `CodeAssist-Modern.apk`
   - أو اضغط "Attach files" واختر الملف
6. اضغط **Publish release**

## الخطوة 4: تحديث رابط التحميل في README

بعد رفع Release، احصل على رابط التحميل المباشر:

1. في صفحة Releases، اضغط بزر الفأرة الأيمن على اسم ملف APK
2. اختر "Copy link address"
3. الرابط سيكون بهذا الشكل:
   ```
   https://github.com/YOUR_USERNAME/CodeAssist-Modern/releases/download/v1.0.0/CodeAssist-Modern.apk
   ```

4. افتح ملف README.md وحدث السطر:
   ```markdown
   **[تحميل CodeAssist-Modern.apk](https://github.com/YOUR_USERNAME/CodeAssist-Modern/releases/latest/download/CodeAssist-Modern.apk)** (93MB)
   ```
   استبدل `YOUR_USERNAME` باسم المستخدم الخاص بك

5. احفظ التغييرات وارفعها:
   ```bash
   git add README.md
   git commit -m "Update download link in README"
   git push
   ```

## موقع الملفات 📁

المشروع موجود في:
```
C:\Users\ucobr\CodeAssist-Modern\
├── CodeAssist-Modern.apk (93MB) - سيتم رفعه على Releases
├── README.md
├── CHANGELOG.md
├── .gitignore
└── UPLOAD_INSTRUCTIONS.md (هذا الملف)
```

## نصائح إضافية 💡

### إضافة لقطات شاشة:
1. أنشئ مجلد `screenshots` في المشروع
2. ضع صور التطبيق فيه
3. حدث README.md بالصور

### إذا كنت تريد رفع APK مباشرة (غير مستحسن):
يمكنك استخدام Git LFS للملفات الكبيرة:
```bash
git lfs install
git lfs track "*.apk"
git add .gitattributes
git add CodeAssist-Modern.apk
git commit -m "Add APK file with Git LFS"
git push
```

## حل المشاكل الشائعة 🔧

### إذا حصلت على خطأ "authentication failed":
- تأكد من استخدام Personal Access Token بدلاً من كلمة المرور
- اذهب إلى GitHub Settings → Developer settings → Personal access tokens

### إذا كان الملف كبيراً جداً:
- استخدم GitHub Releases (الطريقة الموصى بها)
- أو استخدم Git LFS

---

✅ **بعد اتباع هذه الخطوات، سيكون مشروعك متاحاً على GitHub!**
