# CodeAssist Modern

A modern Android IDE for mobile development.

## 📦 Project Structure

This repository contains:
- **Source files and projects** - All development files
- **APK files** - Pre-built application packages
- **Templates** - Project templates for quick start
- **Decoded APK** - Available in 3 parts (see below)

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

## 🚀 Quick Start

1. Clone the repository
2. Merge the decoded parts (if needed)
3. Open in your preferred IDE
4. Start developing!

## 📄 License

Check the original CodeAssist project for license information.

---

**Note**: The decoded APK directory contains ~73,000 smali files and is necessary for full project functionality.
