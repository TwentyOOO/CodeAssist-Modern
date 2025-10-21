# CodeAssist Modern v2.0 🎨

A modern Android IDE for mobile development with professional UI redesign.

## ⚠️ مهم جداً - اقرأ أولاً!

### ✅ ما يعمل:
- **`codeassist_modern_signed.apk`** - APK الأصلي (يعمل 100%)
- **`modified_design_files/`** - ملفات التصميم الجديد (للاستخدام مع MT Manager)

### ❌ ما لا يعمل:
- **`CodeAssist_Modern_v2.0_WORKING.apk`** - يحتاج zipalign (لا تستخدمه)
- **`CodeAssist_Modern_v2.0_Signed.apk`** - مشاكل توقيع (لا تستخدمه)

### 🎯 الطريقة الموصى بها:
استخدم **MT Manager** لتطبيق التصميم الجديد - [اقرأ الدليل](HOW_TO_APPLY_DESIGN.md)

---

## 📦 محتويات المشروع

### 📱 APK Files:
- ✅ `codeassist_modern_signed.apk` (93 MB) - **يعمل 100%**
- `codeassist_modern.apk` (93 MB)
- `codeassist_modern2.apk` (93 MB)
- `com-tyron-code-29-66222127-02451f313eec1f1029bbaa3c3f048542.apk` (92 MB)

### 🎨 التصميم الجديد:
- **`modified_design_files/`** - جميع ملفات XML المعدلة
  - `layout/home_fragment.xml` - الواجهة الرئيسية الجديدة
  - `drawable/bg_*.xml` (8 ملفات) - خلفيات متدرجة احترافية

### 📚 الأدلة:
- **[HOW_TO_APPLY_DESIGN.md](HOW_TO_APPLY_DESIGN.md)** - كيفية تطبيق التصميم الجديد
- **[TROUBLESHOOTING.md](TROUBLESHOOTING.md)** - حل جميع المشاكل
- **[INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md)** - دليل التثبيت
- **[IMPORTANT_NOTES.md](IMPORTANT_NOTES.md)** - ملاحظات مهمة

### 🔧 ملفات التطوير:
- **Decoded APK** - متوفر في 3 أجزاء (انظر أدناه)

## 🔧 Decoded APK Files

The decoded APK is split into 3 parts due to GitHub file size limits:
- `decoded.zip.part1` (90 MB)
- `decoded.zip.part2` (90 MB)
- `decoded.zip.part3` (51.92 MB)

### How to Merge the Parts

#### Windows (PowerShell):
```powershell
# Navigate to project directory
cd path\to\com.tyron.code

# Merge parts
Get-Content decoded.zip.part1, decoded.zip.part2, decoded.zip.part3 -Encoding Byte -ReadCount 0 | Set-Content decoded.zip -Encoding Byte

# Extract
Expand-Archive decoded.zip -DestinationPath codeassist_decoded
```

#### Linux/Mac:
```bash
# Navigate to project directory
cd path/to/com.tyron.code

# Merge parts
cat decoded.zip.part1 decoded.zip.part2 decoded.zip.part3 > decoded.zip

# Extract
unzip decoded.zip
mv decoded codeassist_decoded
```

#### Alternative (Simple):
```bash
# Windows (Command Prompt)
copy /b decoded.zip.part1+decoded.zip.part2+decoded.zip.part3 decoded.zip

# Then extract the decoded.zip file
```

## 📱 APK Files Included

- `codeassist_modern.apk` (92.55 MB)
- `codeassist_modern2.apk` (92.55 MB)
- `codeassist_modern_signed.apk` (92.54 MB)
- `com-tyron-code-29-66222127-02451f313eec1f1029bbaa3c3f048542.apk` (91.22 MB)

## 🚀 البدء السريع

### للمستخدمين:

#### الطريقة 1: تثبيت مباشر (بدون تصميم جديد)
```bash
# حمّل وثبت APK الأصلي
adb install codeassist_modern_signed.apk
```

#### الطريقة 2: مع التصميم الجديد (موصى بها)
1. حمّل **MT Manager** من: https://mt2.cn/download/
2. افتح `codeassist_modern_signed.apk` في MT Manager
3. استبدل الملفات من `modified_design_files/`
4. احفظ ووقع وثبت
5. اقرأ [HOW_TO_APPLY_DESIGN.md](HOW_TO_APPLY_DESIGN.md) للتفاصيل

### للمطورين:

```bash
# 1. استنساخ المشروع
git clone https://github.com/TwentyOOO/CodeAssist-Modern.git
cd CodeAssist-Modern

# 2. دمج ملفات APK المفككة (إذا لزم الأمر)
cat decoded.zip.part1 decoded.zip.part2 decoded.zip.part3 > decoded.zip
unzip decoded.zip

# 3. للتعديل وإعادة البناء (مهم!)
java -jar apktool.jar b decoded -o unsigned.apk
zipalign -v -p 4 unsigned.apk aligned.apk  # ضروري!
apksigner sign --ks keystore.jks aligned.apk
```

---

## 🎨 التصميم الجديد v2.0

### المميزات:
- ✅ خلفية متدرجة داكنة احترافية (#0F172A → #334155)
- ✅ شعار التطبيق في دائرة ملونة (تدرج بنفسجي-وردي)
- ✅ بطاقات Material Design 3 مع حواف دائرية
- ✅ 4 أزرار محسّنة بتصميم عصري
- ✅ نظام ألوان متناسق (Indigo/Violet/Cyan)
- ✅ 8 خلفيات متدرجة بألوان مختلفة

### معاينة:
```
╔════════════════════════════════╗
║   CodeAssist Modern            ║
╠════════════════════════════════╣
║          ╭─────────╮            ║
║          │ 🎨 LOGO │ ← دائرة   ║
║          ╰─────────╯   ملونة   ║
║                                ║
║     CodeAssist Modern          ║
║        Version 2.0             ║
║                                ║
║  ╭──────────────────────────╮  ║
║  │  Quick Actions           │  ║
║  │  ┌────────────────────┐  │  ║
║  │  │ ➕ New Project     │  │  ║
║  │  └────────────────────┘  │  ║
║  │  ┌────────────────────┐  │  ║
║  │  │ 🌿 Git Clone       │  │  ║
║  │  └────────────────────┘  │  ║
║  ╰──────────────────────────╯  ║
╚════════════════════════════════╝
```

---

## 🐛 استكشاف الأخطاء

### مشكلة: "Installation failed -124"
**الحل**: استخدم `codeassist_modern_signed.apk` الأصلي

### مشكلة: "No certificates found"
**الحل**: استخدم MT Manager لإعادة التوقيع

### مشكلة: "Package is invalid"
**الحل**: اقرأ [TROUBLESHOOTING.md](TROUBLESHOOTING.md)

---

## 📊 المتطلبات

- **Android**: 5.0+ (API 21)
- **المساحة**: ~100 ميجابايت
- **الأذونات**: التخزين، الإنترنت

---

## 📄 License

Check the original CodeAssist project for license information.

---

**Note**: The decoded APK directory contains ~73,000 smali files and is necessary for full project functionality.
