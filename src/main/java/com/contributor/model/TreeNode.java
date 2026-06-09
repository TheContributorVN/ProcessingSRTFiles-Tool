package com.contributor.model;

import java.nio.file.Path;
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
    private Path rootNode;
    private boolean isDirectory;
    private List<TreeNode> parentNodes;

    public TreeNode(Path rootNode, boolean isDirectory) {
        this.rootNode = rootNode;
        this.isDirectory = isDirectory;
        this.parentNodes = this.isDirectory ? List.of() : null;
    }

    public void addChildNode(TreeNode childNode) {
        this.parentNodes.add(childNode);
    }
}
