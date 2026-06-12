package com.contributor;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.contributor.config.AppConfig;
import com.contributor.util.ViewLoader;
import com.contributor.util.ViewLoader.ViewTuple;
import com.contributor.view.WelcomeController;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppConfig.initialDataDirectory();
        ViewTuple<Parent, WelcomeController> welcomeView = ViewLoader.load(
                "/view/layout/WelcomeView.fxml",
                (WelcomeController c) -> {
                });
        Scene scene = new Scene(welcomeView.view(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setTitle("ProcessingSRTFile");
        primaryStage.show();
    }
}
