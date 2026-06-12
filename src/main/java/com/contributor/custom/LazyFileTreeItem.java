package com.contributor.custom;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import com.contributor.viewmodel.LazyFileHandlerViewModel;

public class LazyFileTreeItem extends TreeItem<Path> {

    private boolean childrenLoaded = false;
    private final LazyFileHandlerViewModel viewModel;

    public LazyFileTreeItem(Path path) {
        super(path);
        this.viewModel = new LazyFileHandlerViewModel(path);
    }

    @Override
    public ObservableList<TreeItem<Path>> getChildren() {
        if (!childrenLoaded) {
            childrenLoaded = true;
            super.getChildren().setAll(loadChildren());
        }
        return super.getChildren();
    }

    @Override
    public boolean isLeaf() {
        return !viewModel.isDirectory();
    }

    private List<TreeItem<Path>> loadChildren() {

        return viewModel.loadChildren().stream()
                .map(f -> new LazyFileTreeItem(f.getPath()))
                .collect(Collectors.toList());
    }

    public void refresh() {
        childrenLoaded = false;
        super.getChildren().clear();
    }
}
