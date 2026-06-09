package com.contributor.viewmodel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import com.contributor.config.AppConfig;
import com.contributor.model.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class WelcomeViewModel {
    private final ObservableList<Path> pathList = FXCollections.observableArrayList();
    private final ObjectProperty<TreeNode> treeNode = new SimpleObjectProperty<>();

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
    // private TreeNode travesalFolder(Path path) {
    // FileTreeVisitor fileTreeVisitor = new FileTreeVisitor();
    // try {
    // Files.walkFileTree(path, fileTreeVisitor);
    // } catch (Exception e) {
    // return null;
    // }
    // return fileTreeVisitor.getRoot();
    //
    // }

    /*
     * Thêm folder vào danh sách file recent.json
     * 
     * @param path
     */
    public void addFolderToRecent(Path path) {
        try {
            String folderName = path.getFileName().toString();
            Map<String, Object> lastFolders = getDataFromRecentFile();
            Map<String, Object> folders = new LinkedHashMap<>();
            folders.put(folderName, path.toString());
            if (lastFolders != null) {

                for (Entry<String, Object> lastFolderItem : lastFolders.entrySet()) {
                    folders.putIfAbsent(lastFolderItem.getKey(), lastFolderItem.getValue().toString());
                }
            }
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                objectMapper.writeValue(AppConfig.getOpenRecentPath().toFile(), folders);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    public ObservableList<Path> getPastList() {
        return pathList;
    }

    public ObjectProperty<TreeNode> getTreeNode() {
        return treeNode;
    }
}
