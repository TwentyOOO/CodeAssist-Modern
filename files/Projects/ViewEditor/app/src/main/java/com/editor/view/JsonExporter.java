package com.editor.view;

import android.os.Environment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JsonExporter {
    
    private String projectPath;
    private String projectName;
    
    public JsonExporter(String projectPath) {
        this.projectPath = projectPath;
        this.projectName = new File(projectPath).getName();
    }
    
    public String exportToJson() {
        try {
            JSONObject root = new JSONObject();
            
            // معلومات المشروع
            root.put("project_name", projectName);
            root.put("project_path", projectPath);
            root.put("export_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", 
                Locale.getDefault()).format(new Date()));
            
            // إحصائيات المشروع
            File projectDir = new File(projectPath);
            root.put("statistics", getStatistics(projectDir));
            
            // بنية المشروع
            root.put("structure", getDirectoryStructure(projectDir, 0));
            
            // محتويات الملفات
            root.put("files", getFilesContent(projectDir));
            
            // حفظ الملف
            return saveToFile(root);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private JSONObject getStatistics(File dir) throws Exception {
        JSONObject stats = new JSONObject();
        
        int[] counts = new int[]{0, 0, 0}; // files, folders, totalSize
        calculateStats(dir, counts);
        
        stats.put("total_files", counts[0]);
        stats.put("total_folders", counts[1]);
        stats.put("total_size_bytes", counts[2]);
        stats.put("total_size_mb", String.format("%.2f", counts[2] / (1024.0 * 1024.0)));
        
        return stats;
    }
    
    private void calculateStats(File dir, int[] counts) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    counts[1]++; // folders
                    calculateStats(file, counts);
                } else {
                    counts[0]++; // files
                    counts[2] += file.length(); // size
                }
            }
        }
    }
    
    private JSONObject getDirectoryStructure(File dir, int depth) throws Exception {
        JSONObject structure = new JSONObject();
        structure.put("name", dir.getName());
        structure.put("type", dir.isDirectory() ? "folder" : "file");
        structure.put("path", dir.getAbsolutePath());
        
        if (dir.isDirectory()) {
            JSONArray children = new JSONArray();
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (depth < 5) { // عمق محدود لتجنب ملفات كبيرة جداً
                        children.put(getDirectoryStructure(file, depth + 1));
                    }
                }
            }
            structure.put("children", children);
        } else {
            structure.put("size", dir.length());
        }
        
        return structure;
    }
    
    private JSONArray getFilesContent(File dir) throws Exception {
        JSONArray filesArray = new JSONArray();
        List<File> allFiles = new ArrayList<>();
        collectFiles(dir, allFiles);
        
        for (File file : allFiles) {
            if (shouldIncludeFile(file)) {
                JSONObject fileObj = new JSONObject();
                fileObj.put("name", file.getName());
                fileObj.put("path", file.getAbsolutePath());
                fileObj.put("size", file.length());
                fileObj.put("extension", getFileExtension(file));
                
                // قراءة محتوى ملفات النصوص فقط
                if (isTextFile(file) && file.length() < 1024 * 100) { // أقل من 100KB
                    fileObj.put("content", readFileContent(file));
                }
                
                filesArray.put(fileObj);
            }
        }
        
        return filesArray;
    }
    
    private void collectFiles(File dir, List<File> filesList) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    collectFiles(file, filesList);
                } else {
                    filesList.add(file);
                }
            }
        }
    }
    
    private boolean shouldIncludeFile(File file) {
        String name = file.getName().toLowerCase();
        // تجاهل الملفات المخفية وملفات النظام
        return !name.startsWith(".") && 
               !name.equals("thumbs.db") && 
               !name.endsWith(".class");
    }
    
    private boolean isTextFile(File file) {
        String ext = getFileExtension(file).toLowerCase();
        return ext.matches("java|xml|txt|json|gradle|md|properties|kt|html|css|js");
    }
    
    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return lastDot > 0 ? name.substring(lastDot + 1) : "";
    }
    
    private String readFileContent(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (Exception e) {
            content.append("Error reading file: ").append(e.getMessage());
        }
        return content.toString();
    }
    
    private String saveToFile(JSONObject jsonObject) {
        try {
            // اسم ديناميكي: اسم_المشروع_التاريخ_الوقت.json
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", 
                Locale.getDefault()).format(new Date());
            String filename = projectName + "_" + timestamp + ".json";
            
            File outputDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            
            File outputFile = new File(outputDir, filename);
            
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(jsonObject.toString(2)); // Pretty print with indent
            }
            
            return outputFile.getAbsolutePath();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}	public String getProjectName() {
		return projectName;
	}
}