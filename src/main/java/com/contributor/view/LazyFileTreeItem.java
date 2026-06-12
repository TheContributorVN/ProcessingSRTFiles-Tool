package com.contributor.view;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import com.contributor.viewmodel.MainViewModel;

public class LazyFileTreeItem extends TreeItem<Path> {

    private boolean childrenLoaded = false;
    private final MainViewModel viewModel;

    public LazyFileTreeItem(Path path, MainViewModel viewModel) {
        super(path);
        this.viewModel = viewModel;
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
        return !viewModel.isDirectory(getValue());
    }

    private List<TreeItem<Path>> loadChildren() {
        return viewModel.loadChildren(getValue()).stream()
                .map(path -> new LazyFileTreeItem(path, viewModel))
                .collect(Collectors.toList());
    }

    public void refresh() {
        childrenLoaded = false;
        super.getChildren().clear();
    }
}
