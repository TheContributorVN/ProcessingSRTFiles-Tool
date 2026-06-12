package com.contributor.viewmodel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.contributor.model.FileSystemModel;

public class LazyFileHandlerViewModel {

    private final FileSystemModel fileSystemModel;

    public LazyFileHandlerViewModel(Path path) {
        this.fileSystemModel = new FileSystemModel(path);
    }

    public boolean isDirectory() {
        return fileSystemModel.isDirectory();
    }

    public List<FileSystemModel> loadChildren() {
        try {
            return fileSystemModel.getListChildren().stream().filter(f -> f.isDirectory()).toList();
        } catch (IOException e) {
            return List.of();
        }
    }
}
