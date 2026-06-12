package com.contributor.view;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;

import com.contributor.custom.LazyFileTreeItem;
import com.contributor.model.FileSystemModel;
import com.contributor.viewmodel.MainViewModel;

public class MainController implements Initializable {

    @FXML
    private TreeView<Path> tviewPath;
    @FXML
    private TableView<FileSystemModel> tFile;

    private final MainViewModel mViewModel;

    public MainController() {
        mViewModel = new MainViewModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tviewPath.setCellFactory(tv -> new TreeCell<Path>() {
            @Override
            protected void updateItem(Path item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label label = new Label(item.getFileName().toString());
                    setGraphic(label);
                }
            }
        });

        mViewModel.getCurrentPath().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                LazyFileTreeItem root = new LazyFileTreeItem(newVal);
                root.setExpanded(true);
                tviewPath.setRoot(root);
            }
        });
        tviewPath.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mViewModel.setFileSystemPath(newVal.getValue());
            }
        });
        tFile.setItems(mViewModel.getFileChildrenPath());
    }

    public void setPathToViewModel(Path path) {
        mViewModel.setCurrentPath(path);
    }

}
