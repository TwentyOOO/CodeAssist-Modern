package com.editor.view;

import android.os.FileObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProjectMonitor {
	
	private String projectPath;
	private List<FileObserver> observers;
	private OnFileChangeListener listener;
	
	public interface OnFileChangeListener {
		void onFileChanged(String filePath);
		void onFileCreated(String filePath);
		void onFileDeleted(String filePath);
	}
	
	public ProjectMonitor(String projectPath, OnFileChangeListener listener) {
		this.projectPath = projectPath;
		this.listener = listener;
		this.observers = new ArrayList<>();
	}
	
	public void startWatching() {
		File projectDir = new File(projectPath);
		if (projectDir.exists() && projectDir.isDirectory()) {
			watchDirectory(projectDir);
		}
	}
	
	private void watchDirectory(File directory) {
		FileObserver observer = new FileObserver(directory.getAbsolutePath()) {
			@Override
			public void onEvent(int event, String path) {
				if (path == null) return;
				
				String fullPath = directory.getAbsolutePath() + "/" + path;
				
				switch (event) {
					case FileObserver.MODIFY:
					case FileObserver.CLOSE_WRITE:
					if (listener != null) {
						listener.onFileChanged(fullPath);
					}
					break;
					
					case FileObserver.CREATE:
					if (listener != null) {
						listener.onFileCreated(fullPath);
					}
					File newFile = new File(fullPath);
					if (newFile.isDirectory()) {
						watchDirectory(newFile);
					}
					break;
					
					case FileObserver.DELETE:
					if (listener != null) {
						listener.onFileDeleted(fullPath);
					}
					break;
				}
			}
		};
		
		observer.startWatching();
		observers.add(observer);
		
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					watchDirectory(file);
				}
			}
		}
	}
	
	public void stopWatching() {
		for (FileObserver observer : observers) {
			observer.stopWatching();
		}
		observers.clear();
	}
}