package com.contributor.model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileSystemModel {
    private String fileName;
    private String type;
    private String size;
    private String lang;
    private boolean status;
    private Path path;
    private List<FileSystemModel> children = new ArrayList<>();

    public FileSystemModel(Path path) {
        this.path = path;
        this.fileName = path.getFileName().toString();
        this.type = fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
            this.size = String.valueOf(Files.size(path) / 1024.0);
        } catch (IOException e) {
            this.size = "0";
        }
        this.lang = "";
        this.status = false;
        if (!isDirectory()) {
            int underscoreIdx = fileName.lastIndexOf("_");
            int dotIdx = fileName.lastIndexOf(".");
            if (underscoreIdx != -1 && dotIdx > underscoreIdx) {
                String langBeforeProceesing = fileName.substring(underscoreIdx + 1, dotIdx);
                this.lang = langBeforeProceesing.isEmpty() ? "en" : langBeforeProceesing;
                this.status = langBeforeProceesing.isEmpty();
            } else {
                this.lang = "en";
                this.status = true;
            }
        }
    }

    public List<FileSystemModel> getListChildren() throws IOException {
        children.clear();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                FileSystemModel fileSystemModel = new FileSystemModel(entry);
                children.add(fileSystemModel);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        children.sort((a, b) -> {
            boolean aDir = Files.isDirectory(a.getPath());
            boolean bDir = Files.isDirectory(b.getPath());
            if (aDir != bDir) {
                return aDir ? -1 : 1;
            }
            return a.getFileName().toString()
                    .compareToIgnoreCase(b.getFileName().toString());
        });
        return children;
    }

    public boolean isDirectory() {
        return Files.isDirectory(path);
    }
}
