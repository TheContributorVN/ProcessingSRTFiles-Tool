package com.contributor.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AppConfig {
    private static final String APP_NAME = "ProcessingSRT";
    private static final String OPEN_RECENT = "recents.json";

    private static Path getDataDirectoryByOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        return switch (osName) {
            case "windows" -> Path.of(System.getenv("LOCALAPPDATA"), APP_NAME);
            case "linux" -> Path.of(System.getProperty("user.home"), "." + APP_NAME);
            case "mac" -> Path.of(System.getProperty("user.home"), "Library", APP_NAME);
            default -> Path.of(System.getProperty("user.home"), "." + APP_NAME);
        };
    }

    public static void initialDataDirectory() {
        Path dataDirectory = getDataDirectoryByOS();
        if (Files.notExists(dataDirectory)) {
            try {
                Files.createDirectories(dataDirectory);
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.WARNING, "Không thể tạo thư mục lưu trữ dữ liệu cho ứng dụng");
                alert.showAndWait();
            }
        }
        initialRequireFiles(dataDirectory);
    }

    private static final void initialRequireFiles(Path dataDirPath) {
        Path recentPath = Paths.get(OPEN_RECENT);
        Path finalPath = dataDirPath.resolve(recentPath);
        if (Files.notExists(finalPath)) {
            try {
                Files.createFile(finalPath);
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(AlertType.WARNING);
                alert.showAndWait();
            }
        }
    }

    public static Path getOpenRecentPath() {
        return getDataDirectoryByOS().resolve(OPEN_RECENT);
    }
}
