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
    private Path path;
    private List<FileSystemModel> children = new ArrayList<>();

    public FileSystemModel(Path path) {
        this.path = path;
        this.fileName = path.getFileName().toString();
    }

    public List<FileSystemModel> getListChildren() throws IOException {
        children.clear();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                FileSystemModel fileSystemModel = new FileSystemModel(entry);
                children.add(fileSystemModel);
            }
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
