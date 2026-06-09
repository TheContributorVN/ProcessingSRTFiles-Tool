package com.contributor.view;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;

import com.contributor.viewmodel.WelcomeViewModel;

public class WelcomeController implements Initializable {
    /**
     *
     */
    @FXML
    private Button btnOpenDir;

    @FXML
    private ListView<Path> recentListView;

    private DirectoryChooser directoryChooser;

    private final WelcomeViewModel welcomeViewModel;

    public WelcomeController() {
        welcomeViewModel = new WelcomeViewModel();
    }

    @FXML
    private void handleOnOpenDir(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        recentListView.setItems(welcomeViewModel.getPastList());
        TreeView treeView = new TreeView();

    }
}
