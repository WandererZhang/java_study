package com.tree;

import sun.reflect.generics.tree.Tree;

import java.util.*;

public class tree {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    private int value;
    private TreeNode root;

    public tree() {
        value = 0;
        root = null;
    }

    public TreeNode buildTreeNode(int[] preorder, int[] inorder) {
        if (preorder.length != inorder.length) {
            return null;
        }
        return myBuildTree(inorder, 0, inorder.length - 1, preorder, 0, preorder.length - 1);
    }

    public TreeNode myBuildTree(int[] inorder, int in_start, int in_end, int[] preorder, int pre_start, int pre_end) {
        if (in_start > in_end) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[pre_start]);
        int position = findPosition(inorder, in_start, in_end, preorder[pre_start]);
        root.left = myBuildTree(inorder, in_start, position - 1, preorder, pre_start + 1, pre_start + position - in_start);
        root.right = myBuildTree(inorder, position + 1, in_end, preorder, position - in_end + pre_end + 1, pre_end);
        return root;
    }

    int findPosition(int[] array, int start, int end, int key) {
        for (int i = start; i <= end; i++) {
            if (array[i] == key) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Integer> print_inOrder(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode current = root;
        while (current != null || !stack.empty()) {
            while (current != null) {
                stack.add(current);
                current = current.left;
            }
            current = stack.peek();
            stack.pop();
            list.add(current.val);
            current = current.right;

        }
        return list;
    }

    public ArrayList<Integer> print_preorder(TreeNode root) {
        ArrayList list = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode current = root;
        while (current != null || !stack.empty()) {
            while (current != null) {
                stack.add(current);
                list.add(current.val);
                current = current.left;
            }
            current = stack.peek();
            stack.pop();
            current = current.right;
        }
        return list;
    }

    public ArrayList<Integer> print_postorder(TreeNode root) {
        ArrayList list = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode current = root;
        while (current != null || !stack.empty()) {
            while (current != null) {
                stack.add(current);
                list.add(current.val);
                current = current.right;
            }
            current = stack.peek();
            stack.pop();
            current = current.left;
        }
        Collections.reverse(list);
        return list;
    }

    public int maxdepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = maxdepth(root.left);
        int right = maxdepth(root.right);
        return Math.max(left, right) + 1;
    }

    public int mindepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return Math.min(mindepth(root.left), mindepth(root.right)) + 1;
    }

    public int number_of_tree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = number_of_tree(root.left);
        int right = number_of_tree(root.right);
        return left + right + 1;
    }

    public boolean isCompleteTreeNode(TreeNode root) {
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        boolean result = true;
        boolean No_child = false;
        while (!queue.isEmpty()) {
            TreeNode cur = queue.remove();
            if (No_child) {
                if (cur.left != null || cur.right != null) {
                    result = false;
                    break;
                }
            } else {
                if (cur.left != null && cur.right != null) {
                    queue.add(cur.left);
                    queue.add(cur.right);
                } else if (cur.left != null && cur.right == null) {
                    queue.add(cur.left);
                    No_child = true;
                } else if (cur.left == null && cur.right != null) {
                    result = false;
                    break;
                } else {
                    No_child = true;
                }
            }
        }
        return result;
    }

    public TreeNode getLastCommonParent(TreeNode root, TreeNode t1, TreeNode t2) {
        if (findNode(root.left, t1)) {
            if (findNode(root.right, t2)) {
                return root;
            } else {
                return getLastCommonParent(root.left, t1, t2);
            }
        } else {
            if (findNode(root.left, t2)) {
                return root;
            } else {
                return getLastCommonParent(root.right, t1, t2);
            }
        }
    }

    public boolean findNode(TreeNode root, TreeNode node) {
        if (root == null || node == null) {
            return false;
        }
        if (root == node) {
            return true;
        }
        boolean found = findNode(root.left, node);
        if (!found) {
            found = findNode(root.right, node);
        }
        return found;
    }

    public TreeNode insertNode(TreeNode root, TreeNode node) {
        if (root == node) {
            return node;
        }
        TreeNode tmp;
        tmp = root;
        TreeNode last = null;
        while (tmp != null) {
            last = tmp;
            if (tmp.val > node.val) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
        }
        while (last != null) {
            if (last.val > node.val) {
                last.left = node;
            } else {
                last.right = node;
            }
        }
        return root;
    }

    public void find_path(TreeNode root, int key) {
        if (root == null) {
            return;
        }
        Stack<Integer> stack = new Stack<Integer>();
        int cur_sum = 0;
        find_path(root, key, stack, cur_sum);
    }

    public void find_path(TreeNode root, int key, Stack<Integer> stack, int cur_sum) {
        cur_sum += root.val;
        stack.push(root.val);
        if (cur_sum == key) {
            for (int path : stack) {
                System.out.print(path + " ");
            }
            System.out.println();
        }
        if (root.left != null) {
            find_path(root.left, key, stack, cur_sum);
        }
        if (root.right != null) {
            find_path(root.right, key, stack, cur_sum);
        }
        stack.pop();
    }

    public static void main(String[] args) {
        int[] preorder = {1, 2, 4, 11, 3, 5, 6, 8};
        int[] inorder = {4, 11, 2, 1, 5, 3, 8, 6};
        //int[] preorder={1};
        // int[] inorder={1};
        tree my_tree = new tree();
        my_tree.root = my_tree.buildTreeNode(preorder, inorder);
        System.out.println(my_tree.print_preorder(my_tree.root));
        my_tree.find_path(my_tree.root, 18);
        //System.out.println(my_tree.getLastCommonParent(my_tree.root,my_tree.root.right.left,my_tree.root.right.right.).val);
        /*System.out.println(my_tree.print_preorder(my_tree.root));
        System.out.println(my_tree.print_inOrder(my_tree.root));
        System.out.println(my_tree.print_postorder(my_tree.root));
        System.out.println(my_tree.number_of_tree(my_tree.root));
        System.out.println(my_tree.maxdepth(my_tree.root));
        System.out.println(my_tree.mindepth(my_tree.root));*/
    }
}
