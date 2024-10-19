package hw4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;

/**
 * Map implemented as a Treap.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class TreapMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'rand'. ***/
  private static Random rand;
  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size = 0;

  /**
   * Make a TreapMap.
   */
  public TreapMap() {
    rand = new Random();
  }
 /**
  * Constructor for set seed for testing Random instance
  * @param seed 
  */

  public TreapMap(long seed) {
    TreapMap.rand = new Random(seed);
}

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("key is null");
    }
    root = insert(root, new Node<>(k, v));
  }

  private Node<K, V> insert(Node<K, V> node, Node<K, V> newNode) {
    if (node == null) {
      size++;
      return newNode;
    }

    if (newNode.key.compareTo(node.key) < 0) {
      node.left = insert(node.left, newNode);
      if (node.left.priority < node.priority) {
        node = rotateRight(node);
      }
    } else if (newNode.key.compareTo(node.key) > 0) {
      node.right = insert(node.right, newNode);
      if (node.right.priority < node.priority) {
        node = rotateLeft(node);
      }
    } else {
      throw new IllegalArgumentException("duplicate key");
    }
    node.size = calculateSize(node);
    return node;
  }

  private Node<K, V> rotateLeft(Node<K, V> node) {
    Node<K, V> rightKid = node.right;
    node.right = rightKid.left;
    rightKid.left = node;
    node.size = calculateSize(node);
    rightKid.size = calculateSize(rightKid);
    return rightKid;
  }

  private Node<K, V> rotateRight(Node<K, V> node) {
    Node<K, V> leftKid = node.left;
    node.left = leftKid.right;
    leftKid.right = node;
    node.size = calculateSize(node);
    leftKid.size = calculateSize(leftKid);
    return leftKid;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("key is null");
    }
    if (has(k)) {
      V val = get(k);
      root = remove(root, k);
      size--;
      return val;
    }
    throw new IllegalArgumentException("key not found");
  }

  public Node<K, V> remove(Node<K, V> node, K k) {
    if (node == null) {
      return null;
    }
    if (k.compareTo(node.key) < 0) {
      node.left = remove(node.left, k);
    } else if (k.compareTo(node.key) > 0) {
      node.right = remove(node.right, k);
    } else {
      node = merge(node.left, node.right);
    }
    if (node != null) {
      node.size = calculateSize(node);
    }
    return node;
  }

  private Node<K, V> merge(Node<K, V> leftNode, Node<K, V> rightNode) {
    if (leftNode == null) {
      return rightNode;
    }
    if (rightNode == null) {
      return leftNode;
    }
    if (leftNode.priority < rightNode.priority) {
      leftNode.right = merge(leftNode.right, rightNode);
      leftNode.size = calculateSize(leftNode);
      return leftNode;
    } else {
      rightNode.left = merge(leftNode, rightNode.left);
      rightNode.size = calculateSize(rightNode);
      return rightNode;
    }
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("key is null");
    }
    if (has(k)) {
      root = put(root, k, v);

    } else {
      throw new IllegalArgumentException("key not found");
    }
  }

  private Node<K, V> put(Node<K, V> node, K k, V v) throws IllegalArgumentException {
    if (node == null) {
      throw new IllegalArgumentException("key not found");
    }
    if (k.compareTo(node.key) < 0) {
      node.left = put(node.left, k, v);
    } else if (k.compareTo(node.key) > 0) {
      node.right = put(node.right, k, v);
    } else {
      node.value = v;
    }
    return node;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("key is null");
    }
    Node<K, V> node = getNode(root, k);
    if (node == null) {
      throw new IllegalArgumentException("key not found");
    }
    return node.value;
  }

  private Node<K, V> getNode(Node<K, V> node, K k) throws IllegalArgumentException {
    if (node == null) {
      return null;
    }
    if (k.compareTo(node.key) < 0) {
      return getNode(node.left, k);
    }
    if (k.compareTo(node.key) > 0) {
      return getNode(node.right, k);
    }
    return node;
  }

  private int calculateSize(Node<K, V> node) {
    if (node == null) {
      return 0;
    }
    node.size = 1 + getSize(node.left) + getSize(node.right);
    return node.size;
  }

  private int getSize(Node<K, V> node) {
    return (node == null) ? 0 : node.size;
  }

  @Override
  public boolean has(K k) {
    return getNode(root, k) != null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new TreapIterator();
  }

  private class TreapIterator implements Iterator<K> {
    private Stack<Node<K, V>> stack;

    public TreapIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> node) {
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException("No element left");
      }
      Node<K, V> node = stack.pop();
      pushLeft(node.right);
      return node.key;
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
   * long as we use recursive insert/remove helpers. Since this is
   * a node class for a Treap we also include a priority field.
   *
   * @param <K> Type for keys.
   * @param <V> Type for values.
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int priority;
    int size;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      priority = generateRandomInteger();
      size = 1;
    }

    // Use this function to generate random values
    // to use as node priorities as you insert new
    // nodes into your TreapMap.
    private int generateRandomInteger() {
      // Note: do not change this function!
      return rand.nextInt();
    }

    @Override
    public String toString() {
      return key + ":" + value + ":" + priority;
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