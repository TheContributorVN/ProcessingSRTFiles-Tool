package com.contributor;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.contributor.util.ViewLoader;
import com.contributor.util.ViewLoader.ViewTuple;
import com.contributor.view.WelcomeController;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewTuple<Parent, WelcomeController> welcomeView = ViewLoader.load(
                "/view/layout/WelcomeView.fxml",
                (WelcomeController c) -> {
                    return;
                });
        Parent root = welcomeView.view();
        Scene scene = new Scene(root, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("ProcessingSRTFile");
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
