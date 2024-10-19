package hw4;

import exceptions.EmptyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import java.util.Iterator;


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
    @DisplayName("Insert a single element and retrieve it")
    void insertSingleElement() throws EmptyException {
        pq.insert(10);
        assertFalse(pq.empty());
        assertEquals(10, pq.best());
    }

    @Test
    @DisplayName("Insert multiple elements and retrieve the minimum")
    void insertMultipleElements() throws EmptyException {
        pq.insert(5);
        pq.insert(3);
        pq.insert(8);
        pq.insert(1);
        pq.insert(9);

        assertEquals(1, pq.best());
    }

    @Test
    @DisplayName("Remove elements maintains the min-heap property")
    void removeElements() throws EmptyException {
        pq.insert(5);
        pq.insert(3);
        pq.insert(8);
        pq.insert(1);
        pq.insert(9);

        assertEquals(1, pq.best());

        pq.remove();
        assertEquals(3, pq.best());

        pq.remove();
        assertEquals(5, pq.best());

        pq.remove();
        assertEquals(8, pq.best());

        pq.remove();
        assertEquals(9, pq.best());

        pq.remove();
        assertTrue(pq.empty());
    }

    @Test
    @DisplayName("Best throws EmptyException when queue is empty")
    void bestEmptyException() {
        assertThrows(EmptyException.class, () -> {
            pq.best();
        });
    }

    @Test
    @DisplayName("Remove throws EmptyException when queue is empty")
    void removeEmptyException() {
        assertThrows(EmptyException.class, () -> {
            pq.remove();
        });
    }

    @Test
    @DisplayName("Inserting null throws IllegalArgumentException")
    void insertNullException() {
        assertThrows(IllegalArgumentException.class, () -> {
            pq.insert(null);
        });
    }

    @Test
    @DisplayName("Heap maintains min-heap property after multiple insertions and removals")
    void complexOperations() throws EmptyException {
        int[] elements = {20, 15, 30, 10, 5, 25, 35, 2, 8, 12};
        for (int elem : elements) {
            pq.insert(elem);
        }

        assertEquals(2, pq.best());

        pq.remove();
        assertEquals(5, pq.best());

        pq.insert(1);
        assertEquals(1, pq.best());

        pq.remove();
        assertEquals(5, pq.best());

        while (!pq.empty()) {
            pq.remove();
        }

        assertTrue(pq.empty());
    }
}