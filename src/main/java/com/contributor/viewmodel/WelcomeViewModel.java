package com.contributor.viewmodel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.contributor.config.AppConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
        }
    }

    public ObservableList<Path> getPastList() {
        return pathList;
    }
}
