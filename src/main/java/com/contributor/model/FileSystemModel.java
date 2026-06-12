package com.contributor.model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileSystemModel {

    public List<Path> listChildren(Path directory) throws IOException {
        List<Path> children = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path entry : stream) {
                children.add(entry);
            }
        }

        children.sort((a, b) -> {
            boolean aDir = Files.isDirectory(a);
            boolean bDir = Files.isDirectory(b);
            if (aDir != bDir) {
                return aDir ? -1 : 1;
            }
            return a.getFileName().toString()
                    .compareToIgnoreCase(b.getFileName().toString());
        });

        return children;
    }

    public boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }
}
