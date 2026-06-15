package com.contributor.view;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;

import com.contributor.util.ViewLoader;
import com.contributor.util.ViewLoader.ViewTuple;
import com.contributor.viewmodel.WelcomeViewModel;

public class WelcomeController implements Initializable {
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
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory == null) {
            return;
        }
        welcomeViewModel.addFolderToRecent(selectedDirectory.toPath());
        ViewTuple<Parent, MainController> tuple = ViewLoader.load("/view/layout/MainView.fxml", (MainController c) -> {
            c.setPathToViewModel(selectedDirectory.toPath());
        });
        Scene currentScene = btnOpenDir.getScene();
        currentScene.setRoot(tuple.view());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        recentListView.setItems(welcomeViewModel.getPastList());
    }
}
