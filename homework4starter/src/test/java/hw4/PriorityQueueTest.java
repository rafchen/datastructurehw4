package hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import exceptions.EmptyException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class PriorityQueueTest {

  private PriorityQueue<Integer> pq;

  @BeforeEach
  void setUp() {
    pq = this.createQueue();
  }

  abstract protected PriorityQueue<Integer> createQueue();

  @Test
  @DisplayName("PriorityQueue is empty after construction")
  void newQueueEmpty() {
    assertTrue(pq.empty());
  }
  @Test
  @DisplayName("Iterator works on empty queue")
  void iteratorOnEmptyQueue() {
    Iterator<Integer> iterator = pq.iterator();
    assertEquals(false, iterator.hasNext());

    try {
      iterator.next();
    } catch (NoSuchElementException e) {
      assertEquals(true, true);
    }
  }

  @Test
  @DisplayName("Iterator hasNext and next work correctly")
  void iteratorHasNextAndNext() {
    pq.insert(10);
    pq.insert(20);
    pq.insert(5);

    Iterator<Integer> iterator = pq.iterator();

    assertEquals(true, iterator.hasNext());
    assertEquals(5, iterator.next());

    assertEquals(true, iterator.hasNext());
    assertEquals(20, iterator.next());

    assertEquals(true, iterator.hasNext());
    assertEquals(10, iterator.next());

    assertEquals(false, iterator.hasNext());
    
    try {
      iterator.next();
    } catch (NoSuchElementException e) {
      assertEquals(true, true);
    }
  }

  @Test
  @DisplayName("Iterator correctly traverses queue after removal")
  void iteratorAfterRemoval() throws EmptyException {
    pq.insert(10);
    pq.insert(20);
    pq.insert(5);

    pq.remove();

    Iterator<Integer> iterator = pq.iterator();

    assertEquals(true, iterator.hasNext());
    assertEquals(10, iterator.next());

    assertEquals(true, iterator.hasNext());
    assertEquals(20, iterator.next());

    assertEquals(false, iterator.hasNext());

    try {
      iterator.next();
    } catch (NoSuchElementException e) {
      assertEquals(true, true);
    }
  }

  @Test
  @DisplayName("Iterator handles queue with one element")
  void iteratorOnSingleElement() {
    pq.insert(15);

    Iterator<Integer> iterator = pq.iterator();

    assertEquals(true, iterator.hasNext());
    assertEquals(15, iterator.next());

    assertEquals(false, iterator.hasNext());

    try {
      iterator.next();
    } catch (NoSuchElementException e) {
      assertEquals(true, true);
    }
  }
  @Test
  @DisplayName("Insert multiple elements and check iterator order")
  void insertMultipleElements() {
    pq.insert(10);
    pq.insert(3);
    pq.insert(6);

    Iterator<Integer> iterator = pq.iterator();

    assertEquals(3, iterator.next());  // 3 is the minimum element in the heap
    assertEquals(6, iterator.next());
    assertEquals(10, iterator.next());
    assertEquals(false, iterator.hasNext());

    try {
      iterator.next();
    } catch (NoSuchElementException e) {
      assertEquals("no elements left", e.getMessage());
    }
  }
  @Test
  @DisplayName("Insert duplicate elements and check iterator")
  void insertDuplicates() {
    pq.insert(7);
    pq.insert(7);
    pq.insert(7);

    Iterator<Integer> iterator = pq.iterator();

    assertEquals(7, iterator.next());
    assertEquals(7, iterator.next());
    assertEquals(7, iterator.next());
    assertEquals(false, iterator.hasNext());
  }

  @Test
  @DisplayName("Insert and remove several elements in succession")
  void multipleOperations() throws EmptyException {
    pq.insert(15);
    pq.insert(22);
    pq.insert(9);
    pq.insert(33);
    pq.insert(3);

    pq.remove();  // Removes 3, the smallest element
    pq.remove();  // Removes 9, the next smallest

    Iterator<Integer> iterator = pq.iterator();

    assertEquals(15, iterator.next());
    assertEquals(22, iterator.next());
    assertEquals(33, iterator.next());
    assertEquals(false, iterator.hasNext());

    try {
      iterator.next();
    } catch (NoSuchElementException e) {
      assertEquals("no elements left", e.getMessage());
    }
  }

  @Test
  @DisplayName("Insert with custom comparator (reverse order)")
  void insertWithCustomComparator() {
    BinaryHeapPriorityQueue<Integer> reversePQ = new BinaryHeapPriorityQueue<>((a, b) -> b - a);

    reversePQ.insert(5);
    reversePQ.insert(10);
    reversePQ.insert(3);

    Iterator<Integer> iterator = reversePQ.iterator();

    assertEquals(10, iterator.next());
    assertEquals(5, iterator.next());
    assertEquals(3, iterator.next());
    assertEquals(false, iterator.hasNext());
  }
}