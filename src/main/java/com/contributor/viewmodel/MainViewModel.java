package com.contributor.viewmodel;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.contributor.model.FileSystemModel;

public class MainViewModel {
    private final StringProperty selectedDirectory = new SimpleStringProperty();
    private final ObjectProperty<Path> currentPath = new SimpleObjectProperty<>();
    private final IntegerProperty refreshTrigger = new SimpleIntegerProperty(0);
    private final BooleanProperty showProcessingButton = new SimpleBooleanProperty(false);
    private final ObservableList<FileSystemModel> fileChildrenPath = FXCollections.observableArrayList();
    private Path fileSystemPath;

    public MainViewModel() {
    }

    public void loadFileChildren() {
        fileChildrenPath.clear();
        try {
            List<FileSystemModel> listChildren = new FileSystemModel(fileSystemPath).getListChildren();
            List<FileSystemModel> filterListChildren = listChildren.stream().filter(f -> !f.isDirectory())
                    .filter(f -> f.getType().equals("srt")).toList();
            fileChildrenPath.addAll(filterListChildren);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setFileSystemPath(Path path) {
        this.fileSystemPath = path;
        this.showProcessingButton.setValue(true);
        loadFileChildren();
    }

    public void refreshDirectoryPath() {
        selectedDirectory.setValue(currentPath.getValue().toString());
    }

    public void refreshSelectedDirectory() {
        Path path = Paths.get(selectedDirectory.getValue());
        currentPath.setValue(path);
        refreshDirectoryPath();
        refreshTrigger.set(refreshTrigger.get() + 1);
    }

    public void setCurrentPath(Path path) {
        currentPath.setValue(path);
        refreshDirectoryPath();
        refreshTrigger.set(refreshTrigger.get() + 1);
    }

    public ObservableList<FileSystemModel> getFileChildrenPath() {
        return fileChildrenPath;
    }

    public ObjectProperty<Path> getCurrentPath() {
        return currentPath;
    }

    public IntegerProperty getRefreshTrigger() {
        return refreshTrigger;
    }

    public StringProperty getSelectedDiretory() {
        return selectedDirectory;
    }

    public Path getCurrentFileSystemPath() {
        return fileSystemPath;
    }

    public BooleanProperty getShowProcessingButton() {
        return showProcessingButton;
    }
}
