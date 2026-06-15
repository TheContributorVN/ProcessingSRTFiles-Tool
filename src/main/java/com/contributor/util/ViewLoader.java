package com.contributor.util;

import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;

public class ViewLoader {

    public record ViewTuple<V, C>(V view, C controller) {
    }

    public static <V, C> ViewTuple<V, C> load(String fxmlPath, Consumer<C> controllerInitializer) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewLoader.class.getResource(fxmlPath));
            V view = loader.load();
            C controller = loader.getController();
            if (controller != null) {
                controllerInitializer.accept(controller);
            }
            return new ViewTuple<>(view, controller);
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            return null;
        }
    }
}
