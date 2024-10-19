package hw4;

import exceptions.EmptyException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Priority queue implemented as a binary heap with a ranked array representation.
 *
 * @param <T> Element type.
 */
public class BinaryHeapPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T> {
  private final List<T> heap;
  private Comparator<T> cmp;

  /**
   * Make a BinaryHeapPriorityQueue.
   */
  public BinaryHeapPriorityQueue() {
    this(new DefaultComparator<>());
  }

  /**
   * Make a BinaryHeapPriorityQueue with a custom comparator.
   *
   * @param cmp Comparator to use.
   */
  public BinaryHeapPriorityQueue(Comparator<T> cmp) {
    this.cmp = cmp;
    heap = new ArrayList<>();
    heap.add(null); // Add a dummy element at index 0 to simplify arithmetic
  }

  @Override
  public void insert(T t) {
    // TODO Implement Me!
  }

  @Override
  public void remove() throws EmptyException {
    // TODO Implement Me!
  }

  @Override
  public T best() throws EmptyException {
    // TODO Implement Me!
    return null;
  }

  @Override
  public boolean empty() {
    // TODO Implement Me!
    return false;
  }

  @Override
  public Iterator<T> iterator() {
    return null;
  }

  // Default comparator is the natural order of elements that are Comparable.
  private static class DefaultComparator<T extends Comparable<T>> implements Comparator<T> {
    public int compare(T t1, T t2) {
      return t1.compareTo(t2);
    }
  }
}
