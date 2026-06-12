package com.contributor.viewmodel;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MainViewModel {

    private final ObjectProperty<Path> currentPath = new SimpleObjectProperty<>();

    public MainViewModel() {
    }

    public void setCurrentPath(Path path) {
        currentPath.setValue(path);
    }

    public ObjectProperty<Path> getCurrentPath() {
        return currentPath;
    }

}
