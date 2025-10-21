# 📝 ملاحظات مهمة للنسخة 2.0

## ⚠️ مشكلة معروفة: APK المعدل يحتاج zipalign

### المشكلة:
ملف `CodeAssist_Modern_v2.0_WORKING.apk` **لا يعمل** على Android 11+ بسبب:
```
Error -124: resources.arsc must be stored uncompressed and aligned on a 4-byte boundary
```

### الحل:

#### للمستخدمين:
✅ **استخدم APK الأصلي**: `codeassist_modern_signed.apk`
✅ **طبق التصميم يدوياً** باستخدام MT Manager (انظر `HOW_TO_APPLY_DESIGN.md`)

#### للمطورين:
```bash
# الطريقة الصحيحة لبناء APK معدل:

# 1. البناء
java -jar apktool.jar b decoded -o unsigned.apk

# 2. المحاذاة (مهم جداً!)
zipalign -v -p 4 unsigned.apk aligned.apk

# 3. التوقيع
apksigner sign --ks keystore.jks --out final.apk aligned.apk

# 4. التحقق
apksigner verify final.apk
```

---

## 📦 الملفات المتوفرة

| الملف | الحالة | الاستخدام |
|-------|--------|-----------|
| `codeassist_modern_signed.apk` | ✅ يعمل 100% | APK الأصلي بدون تعديلات |
| `CodeAssist_Modern_v2.0_WORKING.apk` | ❌ لا يعمل | يحتاج zipalign - لا تستخدمه |
| `CodeAssist_Modern_v2.0_Signed.apk` | ❌ لا يعمل | مشاكل توقيع - لا تستخدمه |
| `modified_design_files/` | ✅ موثوق | استخدمه مع MT Manager |

---

## ✅ الطريقة الصحيحة للحصول على التصميم الجديد

### الخيار 1: MT Manager (موصى به - سهل)
```
1. حمّل MT Manager من https://mt2.cn/download/
2. افتح codeassist_modern_signed.apk
3. استبدل الملفات من modified_design_files/
4. احفظ ووقع
5. ثبت
```

### الخيار 2: APKTool (متقدم - للكمبيوتر)
```bash
# 1. فك APK
java -jar apktool.jar d codeassist_modern_signed.apk

# 2. نسخ الملفات المعدلة
cp modified_design_files/layout/* codeassist_modern_signed/res/layout/
cp modified_design_files/drawable/* codeassist_modern_signed/res/drawable/

# 3. البناء
java -jar apktool.jar b codeassist_modern_signed -o unsigned.apk

# 4. المحاذاة (IMPORTANT!)
zipalign -v -p 4 unsigned.apk aligned.apk

# 5. التوقيع
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
  -keystore debug.keystore aligned.apk alias

# 6. التحقق والتثبيت
jarsigner -verify aligned.apk
adb install aligned.apk
```

---

## 🐛 المشاكل المعروفة

### 1. Installation Error -124
**السبب**: APK غير محاذي (unaligned)
**الحل**: استخدم zipalign قبل التوقيع

### 2. "No certificates found"
**السبب**: توقيع غير صحيح
**الحل**: استخدم jarsigner أو apksigner بشكل صحيح

### 3. "Package is invalid"
**السبب**: شهادة ذاتية غير موثوقة
**الحل**: استخدم MT Manager لإعادة التوقيع

---

## 📚 مراجع إضافية

- [TROUBLESHOOTING.md](TROUBLESHOOTING.md) - جميع المشاكل والحلول
- [HOW_TO_APPLY_DESIGN.md](HOW_TO_APPLY_DESIGN.md) - دليل تطبيق التصميم
- [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md) - دليل التثبيت

---

## ⭐ الخلاصة

**استخدم**: `codeassist_modern_signed.apk` + MT Manager + `modified_design_files/`

هذا هو **الحل الوحيد المضمون 100%** للحصول على التصميم الجديد!

---

**آخر تحديث**: 21 أكتوبر 2025
