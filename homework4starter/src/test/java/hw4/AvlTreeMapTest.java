package hw4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Iterator;


/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to AVL Tree.
 */
@SuppressWarnings("All")
public class AvlTreeMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new AvlTreeMap<>();
  }

  @Test
  public void insertLeftRotation() {
    map.insert("1", "a");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a
     */

    map.insert("2", "b");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a,
        null 2:b
     */

    map.insert("3", "c"); // it must do a left rotation here!
    // System.out.println(avl.toString());
    // must print
    /*
        2:b,
        1:a 3:c
     */

    String[] expected = new String[] {
        "2:b",
        "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  // TODO Add more tests
  @Test
  @DisplayName("Insert elements causing right rotation")
  public void insertRightRotation() {
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("1", "a");

    String[] expected = new String[] {
        "2:b",
        "1:a 3:c"
    };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Insert elements causing left-right rotation")
  public void insertLeftRightRotation() {
    map.insert("1", "a");
    map.insert("3", "c");
    map.insert("2", "b");

    String[] expected = new String[] {
        "2:b",
        "1:a 3:c"
    };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Insert elements causing right-left rotation")
  public void insertRightLeftRotation() {
    map.insert("3", "c");
    map.insert("1", "a");
    map.insert("2", "b");

    String[] expected = new String[] {
        "2:b",
        "1:a 3:c"
    };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Remove elements causing rebalancing with left rotation")
  public void removeCausingLeftRebalance() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");

    map.remove("1");

    String[] expected = new String[] {
        "2:b",
        "null 3:c"
    };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Remove elements causing rebalancing with right rotation")
  public void removeCausingRightRebalance() {
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("1", "a");

    map.remove("3");

    String[] expected = new String[] {
        "2:b",
        "1:a null"
    };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Test duplicate key insertion")
  public void testDuplicateKeyInsertion() {
    map.insert("1", "a");
    try {
      map.insert("1", "duplicate");
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("duplicate key not allowed", e.getMessage());
    }
  }

  @Test
  @DisplayName("Test get method")
  public void testGet() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");

    assertEquals("a", map.get("1"));
    assertEquals("b", map.get("2"));
    assertEquals("c", map.get("3"));

    try {
      map.get("4");
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("key not found", e.getMessage());
    }
  }

  @Test
  @DisplayName("Test put method")
  public void testPut() {
    map.insert("1", "a");
    map.insert("2", "b");

    map.put("1", "alpha");
    map.put("2", "beta");

    assertEquals("alpha", map.get("1"));
    assertEquals("beta", map.get("2"));

    try {
      map.put("3", "gamma");
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("key not found", e.getMessage());
    }
  }

  @Test
  @DisplayName("Test has method")
  public void testHas() {
    map.insert("1", "a");
    map.insert("2", "b");

    assertTrue(map.has("1"));
    assertTrue(map.has("2"));
    assertFalse(map.has("3"));
    assertFalse(map.has(null));
  }

  @Test
  @DisplayName("Test size method")
  public void testSize() {
    assertEquals(0, map.size());

    map.insert("1", "a");
    assertEquals(1, map.size());

    map.insert("2", "b");
    map.insert("3", "c");
    assertEquals(3, map.size());

    map.remove("2");
    assertEquals(2, map.size());

    map.remove("1");
    map.remove("3");
    assertEquals(0, map.size());
  }

  @Test
  @DisplayName("Test iterator in-order traversal")
  public void testIteratorInOrder() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    map.insert("4", "d");
    map.insert("0", "z");

    Iterator<String> it = map.iterator();
    StringBuilder sb = new StringBuilder();
    while (it.hasNext()) {
      sb.append(it.next()).append(" ");
    }
    assertEquals("0 1 2 3 4 ", sb.toString());
  }

  @Test
  @DisplayName("Test toString method for complex tree")
  public void testToStringComplexTree() {
    map.insert("4", "d");
    map.insert("2", "b");
    map.insert("6", "f");
    map.insert("1", "a");
    map.insert("3", "c");
    map.insert("5", "e");
    map.insert("7", "g");

    String[] expected = new String[] {
        "4:d",
        "2:b 6:f",
        "1:a 3:c 5:e 7:g"
    };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Test remove root node")
  public void testRemoveRoot() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");

    String removedValue = map.remove("2");
    assertEquals("b", removedValue);

    String[] expected = new String[] {
        "1:a",
        "null 3:c"
    };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Test inserting null key")
  public void testInsertNullKey() {
    try {
      map.insert(null, "null");
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("key is null", e.getMessage());
    }
  }

  @Test
  @DisplayName("Test removing null key")
  public void testRemoveNullKey() {
    try {
      map.remove(null);
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("key is null", e.getMessage());
    }
  }

  @Test
  @DisplayName("Test getting null key")
  public void testGetNullKey() {
    try {
      map.get(null);
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("key is null", e.getMessage());
    }
  }

  @Test
  @DisplayName("Test putting null key")
  public void testPutNullKey() {
    try {
      map.put(null, "null");
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("key is null", e.getMessage());
    }
  }

  @Test
  @DisplayName("Test inserting multiple elements and ensuring AVL property")
  public void testMultipleInsertions() {
    map.insert("10", "j");
    map.insert("20", "t");
    map.insert("30", "x");
    map.insert("40", "y");
    map.insert("50", "z");
    map.insert("25", "m");

    String[] expected = new String[] {
        "30:x",
        "20:t 40:y",
        "10:j 25:m null 50:z"
    };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Delete root node with one child and verify balance")
  public void testDeleteRootWithOneChild() {
    map.insert("10", "j");
    map.insert("5", "e");

    map.remove("10");

    String[] expected = new String[] {
        "5:e"
    };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Delete root node when it is the only node in the tree")
  public void testDeleteRootWhenOnlyNode() {
    map.insert("10", "j");

    map.remove("10");

    assertEquals(0, map.size());
    assertEquals("", map.toString());
  }
  @Test
  @DisplayName("Test removing elements to empty the tree")
  public void testRemoveAllElements() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");

    assertEquals(3, map.size());

    map.remove("1");
    map.remove("2");
    map.remove("3");

    assertEquals(0, map.size());
    assertEquals("", map.toString());
  }
  @Test
  @DisplayName("Insert a large number of elements with mixed operations to ensure AVL balance")
  public void testInsertLargeNumberWithMixedOperationsMaintainsBalance() {
    for (int i = 1; i <= 500; i++) {
      map.insert(String.valueOf(i), "Value" + i);
    }
    for (int i = 1; i <= 250; i++) {
      map.remove(String.valueOf(i));
    }
    for (int i = 501; i <= 1000; i++) {
      map.insert(String.valueOf(i), "Value" + i);
    }
    for (int i = 251; i <= 500; i++) {
      map.remove(String.valueOf(i));
    }
    assertEquals(500, map.size());
    assertTrue(map.has("501"));
    assertTrue(map.has("1000"));

    try {
      map.get("250");
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("key not found", e.getMessage());
    }
  }
  @Test
  @DisplayName("Insert a key with null value, remove it, and verify removal")
  public void testInsertNullValueAndRemove() {
    map.insert("1", null);
    assertTrue(map.has("1"));
    assertEquals(map.get("1"), null);

    map.remove("1");
    assertFalse(map.has("1"));

    try {
      map.get("1");
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("key not found", e.getMessage());
    }
  }
}
