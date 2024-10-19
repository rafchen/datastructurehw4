package hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}