package hw4;

import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("key is null");
    }
    root = insertNode(root, k, v);
  }

  private Node<K, V> insertNode(Node<K, V> node, K k, V v) {
    if (node == null) {
      size++;
      return new Node<>(k, v);
    }

    int comp = k.compareTo(node.key);
    if (comp < 0) {
      node.left = insertNode(node.left, k, v);
    } else if (comp > 0) {
      node.right = insertNode(node.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key not allowed");
    }
    node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    return balanceTree(node, k);
  }

  private Node<K, V> balanceTree(Node<K, V> node, K k) {
    int bf = getBF(node);
    // left heavy
    if (bf > 1) {
      if (k.compareTo(node.left.key) < 0) {
        return rotateRight(node);
      } else {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
      }
    }
    // right heavy
    if (bf < -1) {
      if (k.compareTo(node.right.key) > 0) {
        return rotateLeft(node);
      } else {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
      }
    }
    return node;
  }

  private Node<K, V> rotateLeft(Node<K, V> node) {
    Node<K, V> newRoot = node.right;
    Node<K, V> temp = newRoot.left;
    newRoot.left = node;
    node.right = temp;
    node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;
    return newRoot;
  }

  private Node<K, V> rotateRight(Node<K, V> node) {
    Node<K, V> newRoot = node.left;
    Node<K, V> temp = newRoot.right;
    newRoot.right = node;
    node.left = temp;
    node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    newRoot.height = Math.max(getHeight(newRoot.left), getHeight(newRoot.right)) + 1;
    return newRoot;
  }

  private Node<K, V> findNode(Node<K, V> node, K k) {
    if (node == null) {
      return null;
    }
    int comp = k.compareTo(node.key);
    if (comp < 0) {
      return findNode(node.left, k);
    } else if (comp > 0) {
      return findNode(node.right, k);
    } else {
      return node;
    }
  }

  private int getHeight(Node<K, V> node) {
    if (node == null) {
      return -1;
    } else {
      return node.height;
    }
  }

  private int getBF(Node<K, V> node) {
    if (node == null) {
      return 0;
    } else {
      return getHeight(node.left) - getHeight(node.right);
    }
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("key is null");
    }
    Node<K, V> node = findNode(root, k);
    if (node != null) {
      V value = node.value;
      root = remove(root, k);
      size--;
      return value;
    }
    throw new IllegalArgumentException("key not found");
  }

  private Node<K, V> remove(Node<K, V> node, K k) {
    if (node == null) {
      return null;
    }
    int comp = k.compareTo(node.key);
    if (comp < 0) {
      node.left = remove(node.left, k);
    } else if (comp > 0) {
      node.right = remove(node.right, k);
    } else {
      if (node.left == null && node.right == null) {
        return null;
      } else if (node.left == null) {
        return node.right;
      } else if (node.right == null) {
        return node.left;
      } else {
        node = removeInOrderPredecessor(node);
      }
    }
    node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    return balanceAfterRemove(node);
  }

  private Node<K, V> removeInOrderPredecessor(Node<K, V> node) {
    Node<K, V> temp = getMax(node.left);
    node.key = temp.key;
    node.value = temp.value;
    node.left = remove(node.left, temp.key);
    return node;
  }

  private Node<K, V> getMax(Node<K, V> node) {
    while (node.right != null) {
      node = node.right;
    }
    return node;
  }

  private Node<K, V> balanceAfterRemove(Node<K, V> node) {
    int bf = getBF(node);
    if (bf > 1) {
      if (getBF(node.left) >= 0) {
        return rotateRight(node);
      } else {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
      }
    }
    if (bf < -1) {
      if (getBF(node.right) <= 0) {
        return rotateLeft(node);
      } else {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
      }
    }
    return node;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("key is null");
    }
    Node<K, V> node = findNode(root, k);
    if (node != null) {
      node.value = v;
      return;
    }
    throw new IllegalArgumentException("key not found");
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("key is null");
    }
    Node<K, V> node = findNode(root, k);
    if (node != null) {
      return node.value;
    }
    throw new IllegalArgumentException("key not found");
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return findNode(root, k) != null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  // Iterative in-order traversal over the keys
  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }

  /**
   * Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers.
   *
   * @param <K> Type for keys.
   * @param <V> Type for values.
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int height;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      height = 0;
    }

    @Override
    public String toString() {
      return key + ":" + value;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }

    // Feel free to add whatever you want to the Node class (e.g. new fields).
    // Just avoid changing any existing names, deleting any existing variables, or
    // modifying the overriding methods.
  }
}
