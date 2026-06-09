package com.contributor.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;
import java.util.Deque;

import com.contributor.model.TreeNode;

public class FileTreeVisitor extends SimpleFileVisitor<Path> {
    private final Deque<TreeNode> stack = new ArrayDeque<>();
    private TreeNode root;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        TreeNode node = new TreeNode(dir, true);
        if (!stack.isEmpty()) {
            stack.peek().addChildNode(node);
        }
        stack.push(node);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        TreeNode node = new TreeNode(file, false);
        stack.peek().addChildNode(node);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        TreeNode fisinhed = stack.pop();
        if (stack.isEmpty()) {
            root = fisinhed;
        }
        return FileVisitResult.CONTINUE;
    }

    public TreeNode getRoot() {
        return root;
    }

}
