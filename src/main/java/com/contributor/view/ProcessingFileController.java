package com.contributor.view;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.contributor.viewmodel.ProcessingFileViewModel;
import com.contributor.viewmodel.ProcessingFileViewModel.FileRow;

public class ProcessingFileController implements Initializable {

    @FXML
    private TableView<FileRow> tblFile;
    @FXML
    private TableColumn<FileRow, Void> tbcCheck;
    @FXML
    private TableColumn<FileRow, String> tbcVideoFile;
    @FXML
    private TableColumn<FileRow, String> tbcSubFile;
    @FXML
    private TableColumn<FileRow, String> tbcLang;
    @FXML
    private TableColumn<FileRow, Boolean> tbcProcessing;
    @FXML
    private ComboBox<String> cbxLang;

    private final ProcessingFileViewModel processingViewModel;

    public ProcessingFileController() {
        processingViewModel = new ProcessingFileViewModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tbcCheck.setCellFactory(c -> new TableCell<FileRow, Void>() {

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(null);
                    setGraphic(new CheckBox());
                }
            }
        });
        tbcVideoFile.setCellValueFactory(new PropertyValueFactory<>("videoFile"));
        tbcSubFile.setCellValueFactory(new PropertyValueFactory<>("subFile"));
        tbcLang.setCellValueFactory(new PropertyValueFactory<>("lang"));
        tbcProcessing.setCellValueFactory(new PropertyValueFactory<>("status"));
        processingViewModel.getFileRows().addListener((ListChangeListener<FileRow>) change -> {
            tblFile.getItems().addAll(processingViewModel.getFileRows());
        });
        cbxLang.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            processingViewModel.activeFilterToList(newVal);
            tblFile.getItems().clear();
            tblFile.getItems().addAll(processingViewModel.getFileRows());
        });
    }

    private void refesh() {
        cbxLang.setItems(processingViewModel.getSetLang());
    }

    public void setFileSystemToViewModel(Path path) {
        processingViewModel.setFileSystemModel(path);
        refesh();
    }

    @FXML
    private void handlerOnProcessingButton(ActionEvent event) {

    }

}
