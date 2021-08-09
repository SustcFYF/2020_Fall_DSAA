//package Homework3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

public class BinaryTree {
    public TreeNode root;

    public boolean AddLeft(TreeNode parent, TreeNode left) {
        if (parent == null || parent.left != null) {
            return false;
        } else {
            parent.left = left;
            return true;
        }
    }

    public boolean AddRight(TreeNode parent, TreeNode right) {
        if (parent == null || parent.right != null) {
            return false;
        } else {
            parent.right = right;
            return true;
        }
    }

    public String TraversalInOrder() {
        StringBuilder in = new StringBuilder();
        if (root == null) {
            return in.toString();
        }
        TreeNode temp = root;
        Stack<TreeNode> stack = new Stack<>();
        while (temp != null || !stack.empty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.left;  // push all left children into the stack
            }
            temp = stack.pop();
            in.append(temp.val).append(" ");  // access the top of the stack
            if (temp.right != null) {
                temp = temp.right;  // push right child if it has into the stack
            } else {
                temp = null;  // access the top if it does not have
            }
        }
        return in.toString();
    }

    public String TraversalPreOrder() {
        StringBuilder pre = new StringBuilder();
        if (root == null) {
            return pre.toString();
        }
        TreeNode temp = root;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(temp);  // push the root node into the stack
        while (!stack.isEmpty()) {
            temp = stack.pop();  // access the top of the stack
            pre.append(temp.val).append(" ");
            if (temp.right != null) {
                stack.push(temp.right);  // push right child if it has into the stack
            }
            if (temp.left != null) {
                stack.push(temp.left);  // push left child if it has into the stack
            }
        }
        return pre.toString();
    }

    public String TraversalPostOrder() {
        StringBuilder post = new StringBuilder();
        if (root == null) {
            return post.toString();
        }
        TreeNode temp = root;
        TreeNode prev = null;
        Stack<TreeNode> stack = new Stack<>();
        while (temp != null || !stack.isEmpty()) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.left;  // push all left children into the stack
            }
            if (!stack.isEmpty()) {
                temp = stack.peek();
                if (temp.right == null || temp.right == prev) {
                    temp = stack.pop();  // access the top of the stack if has no right child
                    post.append(temp.val).append(" ");
                    prev = temp;  // record last element
                    temp = null;  // still access the top
                } else {
                    temp = temp.right;  // find temp.right == null
                }
            }
        }
        return post.toString();
    }

    public String TraversalLevelOrder() {
        StringBuilder level = new StringBuilder();
        if (root == null) {
            return level.toString();
        }
        TreeNode temp;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();  // the number of elements in each level
            level.append("[");  // in format
            for (int i = 0; i < size; i++) {
                temp = queue.remove();
                level.append(temp.val).append(" ");
                if (temp.left != null) {
                    queue.add(temp.left);
                }
                if (temp.right != null) {
                    queue.add(temp.right);
                }  // put the elements in next level at the end of the queue
            }
            level.append("]");  // in format
        }
        return level.toString();
    }

    /**
     * Use the pre-order and in-order traversal to rebuild the binary-tree structure, then the post-order traversal can be found directly.
     *
     * @param pre the pre-order in int array form
     * @param in  the in-order in int array form
     * @return the root of the rebuild-binary-tree
     */

    public TreeNode UsePreInToBuildBinaryTree(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        TreeNode node = new TreeNode(pre[0]);  // pre[0](the first one) is the root of the tree
        for (int i = 0; i < in.length; i++) {
            if (pre[0] == in[i]) {
                node.left = UsePreInToBuildBinaryTree(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                node.right = UsePreInToBuildBinaryTree(Arrays.copyOfRange(pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));
            }
        }
        return node;
    }

    public String PreIn2Post(String preTraversal, String inTraversal) {
        String[] preString = preTraversal.split(" ");
        String[] inString = inTraversal.split(" ");
        int[] pre = new int[preString.length];
        int[] in = new int[inString.length];
        for (int i = 0; i < preString.length; i++) {
            pre[i] = Integer.parseInt(preString[i]);
        }
        for (int i = 0; i < inString.length; i++) {
            in[i] = Integer.parseInt(inString[i]);  // convert string to int array
        }
        TreeNode rebuildNode = UsePreInToBuildBinaryTree(pre, in);
        BinaryTree rebuildBinaryTree = new BinaryTree();
        rebuildBinaryTree.root = rebuildNode;
        return rebuildBinaryTree.TraversalPostOrder();  // get the post-order of the rebuild-binary-tree
    }

    /**
     * Use the in-order and post-order traversal to rebuild the binary-tree structure, then the pre-order traversal can be found directly.
     *
     * @param post the post-order in int array form
     * @param in   the in-order in int array form
     * @return the root of the rebuild-binary-tree
     */

    public TreeNode UseInPostToBuildBinaryTree(int[] post, int[] in) {
        if (post.length == 0 || in.length == 0) {
            return null;
        }
        TreeNode node = new TreeNode(post[post.length - 1]);  // post[post.length - 1](the last one) is the root of the tree
        for (int i = 0; i < in.length; i++) {
            if (post[post.length - 1] == in[i]) {
                node.left = UseInPostToBuildBinaryTree(Arrays.copyOfRange(post, 0, i), Arrays.copyOfRange(in, 0, i));
                node.right = UseInPostToBuildBinaryTree(Arrays.copyOfRange(post, i, post.length - 1), Arrays.copyOfRange(in, i + 1, in.length));
            }
        }
        return node;
    }

    public String InPost2Pre(String inTraversal, String postTraversal) {
        String[] postString = postTraversal.split(" ");
        String[] inString = inTraversal.split(" ");
        int[] post = new int[postString.length];
        int[] in = new int[inString.length];
        for (int i = 0; i < postString.length; i++) {
            post[i] = Integer.parseInt(postString[i]);
        }
        for (int i = 0; i < inString.length; i++) {
            in[i] = Integer.parseInt(inString[i]);  // convert string to int array
        }
        TreeNode rebuildNode = UseInPostToBuildBinaryTree(post, in);
        BinaryTree rebuildBinaryTree = new BinaryTree();
        rebuildBinaryTree.root = rebuildNode;
        return rebuildBinaryTree.TraversalPreOrder();  // get the pre-order of the rebuild-binary-tree
    }
}