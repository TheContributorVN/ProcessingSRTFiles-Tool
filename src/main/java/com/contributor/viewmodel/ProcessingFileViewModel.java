package com.contributor.viewmodel;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import com.contributor.model.FileSystemModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProcessingFileViewModel {

    private final ObservableList<FileRow> fileRows = FXCollections.observableArrayList();
    private final ObservableSet<String> setLang = FXCollections.observableSet();
    private FileSystemModel fileSystemModel;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileRow {
        private String videoFile;
        private String subFile;
        private String lang;
        private boolean status;
    }

    public ProcessingFileViewModel() {
    }

    public void loadFileRows() {
        Map<String, List<FileSystemModel>> mapSrtFiles = new HashMap<>();
        try {
            List<FileSystemModel> listChildren = fileSystemModel.getListChildren().stream()
                    .filter(f -> !f.isDirectory()).filter(f -> f.getType().equals("mp4") || f.getType().equals("srt"))
                    .toList();
            listChildren.forEach(f -> {
                if (f.getType().equals("srt")) {
                    setLang.add(f.getLang());
                    String name = f.getFileName();
                    int underscoreIdx = name.lastIndexOf("_");
                    String baseName = underscoreIdx != -1
                            ? name.substring(0, underscoreIdx)
                            : name.substring(0, name.lastIndexOf("."));
                    mapSrtFiles.computeIfAbsent(baseName, k -> new ArrayList<>()).add(f);
                }
            });
            for (FileSystemModel file : listChildren) {
                String fileName = file.getFileName();
                if (file.getType().equals("mp4")) {
                    int dotIdx = fileName.lastIndexOf(".");
                    String baseName = dotIdx != -1 ? fileName.substring(0, dotIdx) : fileName;
                    List<FileSystemModel> srtFiles = mapSrtFiles.get(baseName);
                    if (srtFiles != null) {
                        for (FileSystemModel srtFile : srtFiles) {
                            fileRows.add(
                                    new FileRow(fileName, srtFile.getFileName(), srtFile.getLang(),
                                            srtFile.isStatus()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void activeFilterToList(String filter) {
        this.fileRows.clear();
        loadFileRows();
        List<FileRow> filterLstRows = this.fileRows.stream().filter(fr -> fr.getLang().equals(filter)).toList();
        this.fileRows.addAll(filterLstRows);
    }

    public ObservableList<FileRow> getFileRows() {
        return fileRows;
    }

    public void setFileSystemModel(Path path) {
        this.fileSystemModel = new FileSystemModel(path);
        loadFileRows();
    }

    public ObservableList<String> getSetLang() {
        return FXCollections.observableArrayList(setLang);
    }
}
