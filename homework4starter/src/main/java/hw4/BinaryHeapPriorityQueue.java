package hw4;

import exceptions.EmptyException;
import java.util.NoSuchElementException;
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
    if (t == null){
      throw new IllegalArgumentException("null element");
    }
    heap.add(t);
    System.out.println("Inserting: " + t);
    swimUp(heap.size() - 1);
    System.out.println("Heap after insertion: " + heap);
  }

  private void swimUp(int child){
    while (child > 1) {
      int parent = child / 2;
      T parentVal = heap.get(parent);
      T childVal = heap.get(child);

      if (cmp.compare(childVal, parentVal) < 0) {
          heap.set(parent, childVal);
          heap.set(child, parentVal);
          child = parent;  
      } else {
          break;
      }
  }
}

  @Override
  public void remove() throws EmptyException {
    if (empty()) {
      throw new EmptyException("queue is empty");
    }
    if (heap.size() == 2){
      heap.remove(1);
      return;
    }
    heap.set(1, heap.remove(heap.size() - 1));
    sinkDown(1);
  }

  private void sinkDown(int index){
    int size = heap.size() - 1;
    while (index * 2 <= size) {
      int leftChild = index * 2;
      int rightChild = index*2 + 1;
      int swapIdx = leftChild;

      if (rightChild <= size && cmp.compare(heap.get(rightChild), heap.get(leftChild)) < 0) {
        swapIdx = rightChild;
      }
      if (cmp.compare(heap.get(swapIdx), heap.get(index)) < 0) {
        T temp = heap.get(index);
        heap.set(index, heap.get(swapIdx));
        heap.set(swapIdx, temp);
        index = swapIdx;
      } else {
        break;
      }
    }
  }

  @Override
  public T best() throws EmptyException {
    if (empty()) {
      throw new EmptyException("queue is empty");
    }
    return heap.get(1);
  }

  @Override
  public boolean empty() {
    return heap.size() == 1;
  }

  @Override
  public Iterator<T> iterator() {
    return new HeapIterator();
  }

  public class HeapIterator implements Iterator<T> {
    private int index;

    public HeapIterator() {
      this.index = 1;
    }

    @Override
    public boolean hasNext() {
      return index < heap.size();
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException("no elements left");
      }
      return heap.get(index++);
    }
  }
  // Default comparator is the natural order of elements that are Comparable.
  private static class DefaultComparator<T extends Comparable<T>> implements Comparator<T> {
    public int compare(T t1, T t2) {
      return t1.compareTo(t2);
    }
  }
}
