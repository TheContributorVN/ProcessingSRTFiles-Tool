package com.contributor.viewmodel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import com.contributor.model.FileSystemModel;

public class MainViewModel {

    private final FileSystemModel fileSystemModel;
    private final ObjectProperty<Path> currentPath = new SimpleObjectProperty<>();

    public MainViewModel() {
        this.fileSystemModel = new FileSystemModel();
    }

    public List<Path> loadChildren(Path directory) {
        try {
            return fileSystemModel.listChildren(directory);
        } catch (IOException e) {
            System.err.println("Không thể đọc thư mục: " + directory + " - " + e.getMessage());
            return List.of();
        }
    }

    public boolean isDirectory(Path path) {
        return fileSystemModel.isDirectory(path);
    }

    public void setCurrentPath(Path path) {
        currentPath.setValue(path);
    }

    public ObjectProperty<Path> getCurrentPath() {
        return currentPath;
    }

}
