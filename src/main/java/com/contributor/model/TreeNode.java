package com.contributor.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TreeNode {
    private Path rootPath;
    private boolean isDirectory;
    private List<TreeNode> parentNodes;

    public TreeNode(Path rootNode, boolean isDirectory) {
        this.rootPath = rootNode;
        this.isDirectory = isDirectory;
        this.parentNodes = this.isDirectory ? new ArrayList<>() : null;
    }

    public void addChildNode(TreeNode childNode) {
        this.parentNodes.add(childNode);
    }
}
