package com.contributor.view;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.contributor.custom.LazyFileTreeItem;
import com.contributor.model.FileSystemModel;
import com.contributor.util.ViewLoader;
import com.contributor.util.ViewLoader.ViewTuple;
import com.contributor.viewmodel.MainViewModel;

public class MainController implements Initializable {

    @FXML
    private TreeView<Path> tviewPath;
    @FXML
    private TableView<FileSystemModel> tFile;
    @FXML
    private TableColumn<FileSystemModel, Void> iconCol;
    @FXML
    private TableColumn<FileSystemModel, String> fileNameCol;
    @FXML
    private TableColumn<FileSystemModel, String> typeCol;
    @FXML
    private TableColumn<FileSystemModel, String> sizeCol;
    @FXML
    private TableColumn<FileSystemModel, String> langCol;
    @FXML
    private TableColumn<FileSystemModel, String> statusCol;
    @FXML
    private TextField tfPath;
    @FXML
    private Button btnProcessing;

    private final MainViewModel mViewModel;

    public MainController() {
        mViewModel = new MainViewModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iconCol.setCellFactory(c -> new TableCell<FileSystemModel, Void>() {
            HBox hbox = new HBox();

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(hbox);
                } else {
                    setText(null);
                    setGraphic(hbox);
                }
            }
        });

        fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        langCol.setCellValueFactory(new PropertyValueFactory<>("lang"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
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

        btnProcessing.visibleProperty().bind(mViewModel.getShowProcessingButton());

        mViewModel.getRefreshTrigger().addListener((obs, old, newVal) -> {
            rebuildTreeView(mViewModel.getCurrentPath().get());
        });
        tviewPath.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mViewModel.setFileSystemPath(newVal.getValue());
            }
        });
        tfPath.textProperty().bindBidirectional(mViewModel.getSelectedDiretory());
        tFile.setItems(mViewModel.getFileChildrenPath());
    }

    public void setPathToViewModel(Path path) {
        mViewModel.setCurrentPath(path);
    }

    @FXML
    private void handlerOnRefreshButton(ActionEvent event) {
        mViewModel.refreshSelectedDirectory();
    }

    private void rebuildTreeView(Path path) {
        if (path != null) {
            LazyFileTreeItem root = new LazyFileTreeItem(path);
            root.setExpanded(true);
            tviewPath.setRoot(root);
        }
    }

    @FXML
    private void handlerOnBrowserButton(ActionEvent evennt) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Select Directory");
        dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = (Stage) tfPath.getScene().getWindow();
        File selectedDirectory = dirChooser.showDialog(stage);
        if (selectedDirectory != null) {
            mViewModel.setCurrentPath(selectedDirectory.toPath());
        }
    }

    @FXML
    private void handlerOnProcessingButton(ActionEvent event) {
        ViewTuple<Parent, ProcessingFileController> viewLoader = ViewLoader.load("/view/layout/ProcessingFileView.fxml",
                (ProcessingFileController c) -> {
                    c.setFileSystemToViewModel(mViewModel.getCurrentFileSystemPath());
                });
        Parent rootNode = viewLoader.view();
        Scene currentScene = (Scene) ((Node) event.getSource()).getScene();
        Stage stage = (Stage) currentScene.getWindow();
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(rootNode));
        dialogStage.initOwner(stage);
        dialogStage.centerOnScreen();
        dialogStage.sizeToScene();
        dialogStage.showAndWait();
    }

}
