package com.contributor.viewmodel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.contributor.model.FileSystemModel;

public class MainViewModel {

    private final ObjectProperty<Path> currentPath = new SimpleObjectProperty<>();

    private final ObservableList<FileSystemModel> fileChildrenPath = FXCollections.observableArrayList();
    private Path fileSystemPath;

    public MainViewModel() {
    }

    public void loadFileChildren() {
        fileChildrenPath.clear();
        try {
            List<FileSystemModel> listChildren = new FileSystemModel(fileSystemPath).getListChildren();
            listChildren.stream().filter(f -> !f.isDirectory()).toList();
            fileChildrenPath.addAll(listChildren);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setFileSystemPath(Path path) {
        this.fileSystemPath = path;
        loadFileChildren();
    }

    public void setCurrentPath(Path path) {
        currentPath.set(path);
    }

    public ObservableList<FileSystemModel> getFileChildrenPath() {
        return fileChildrenPath;
    }

    public ObjectProperty<Path> getCurrentPath() {
        return currentPath;
    }
}
