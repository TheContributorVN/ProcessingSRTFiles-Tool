package com.contributor.viewmodel;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.contributor.config.AppConfig;
import com.contributor.model.TreePath;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WelcomeViewModel {
    private final ObservableList<Path> pathList = FXCollections.observableArrayList();

    public WelcomeViewModel() {
        initData();
    }

    private void initData() {
        // Lấy dữ liệu từ json chứa các folder đã từng mở
        Map<String, Object> folders = getDataFromRecentFile();
        if (folders == null) {
            pathList.addAll(Collections.emptyList());
            return;
        }
        for (Entry<String, Object> entry : folders.entrySet()) {
            Path path = Paths.get(String.valueOf(entry.getValue()));
            if (Files.isDirectory(path)) {
                pathList.add(path);
            }
        }

    }
    /*
     * Đọc dữ liệu từ file recents.json
     * 
     * @Return Map<String, Object>
     * 
     */

    private Map<String, Object> getDataFromRecentFile() {
        Path openRecentPath = AppConfig.getOpenRecentPath();
        if (Files.exists(openRecentPath)) {
            try {
                File recentJsonFile = openRecentPath.toFile();
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> values = objectMapper.readValue(recentJsonFile,
                        new TypeReference<Map<String, Object>>() {
                        });
                return values;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /*
     * 
     * @param path
     * 
     * @return TreePath
     */
    private TreePath travesalFolder(Path path) {
        TreePath treePath = new TreePath();
        try {
            Files.walkFileTree(path, new FileVisitor<Path>() {
                TreePath tree = treePath;
                TreePath currentNode = treePath;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (dir == path) {
                        treePath.setRootNode(dir);
                    } else {
                        currentNode = new TreePath();
                        tree.setRootNode(dir);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.isDirectory(file)) {

                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
        return treePath;

    }

    private TreePath addFolderToRecent(Path path) {
        String folderName = path.getFileName().toString();
        return null;
    }

    public ObservableList<Path> getPastList() {
        return pathList;
    }
}
