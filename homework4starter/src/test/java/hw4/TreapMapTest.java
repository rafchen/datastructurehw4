package hw4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import java.util.Iterator;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to Treap.
 */
@SuppressWarnings("All")
public class TreapMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new TreapMap<>(1);
  }

  @BeforeEach
  public void setUp() {
    map = createMap();
  }

  @Test
  @DisplayName("Insert and verify treap structure")
  public void testInsertSingleElement() {
    map.insert("10", "j");
    String[] expected = { "10:j:-1155869325" };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Insert duplicate key throws exception")
  public void testInsertDuplicateKey() {
    map.insert("1", "a");
    try {
      map.insert("1", "duplicate");
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("duplicate key", e.getMessage());
    }
  }

  @Test
  @DisplayName("Insert null key throws exception")
  public void testInsertNullKey() {
    try {
      map.insert(null, "value");
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("key is null", e.getMessage());
    }
  }

  @Test
  @DisplayName("Remove a leaf node")
  public void testRemoveLeafNode() {
    map.insert("10", "j");
    map.insert("5", "e");
    map.remove("5");
    String[] expected = { "10:j:-1155869325" };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Remove a node with one child")
  public void testRemoveNodeWithOneChild() {
    map.insert("10", "j");
    map.insert("5", "e");
    map.insert("7", "g");
    map.remove("5");
    String[] expected = { "10:j:-1155869325\n" +
        "null 7:g:1761283695" };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Remove root node")
  public void testRemoveRootNode() {
    map.insert("10", "j");
    map.insert("5", "e");
    map.remove("10");
    String[] expected = { "5:e:431529176" };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }

  @Test
  @DisplayName("Put updates existing key")
  public void testPutUpdatesValue() {
    map.insert("1", "a");
    map.put("1", "alpha");
    assertEquals("alpha", map.get("1"));
  }

  @Test
  @DisplayName("Get method retrieves values")
  public void testGetValues() {
    map.insert("1", "a");
    map.insert("2", "b");
    assertEquals("a", map.get("1"));
    assertEquals("b", map.get("2"));
  }

  @Test
  @DisplayName("Size method reflects number of elements")
  public void testSizeMethod() {
    map.insert("1", "a");
    map.insert("2", "b");
    assertEquals(2, map.size());
    map.remove("1");
    assertEquals(1, map.size());
  }

  @Test
  @DisplayName("Has method checks key existence")
  public void testHasMethod() {
    map.insert("1", "a");
    assertTrue(map.has("1"));
    assertFalse(map.has("2"));
  }

  @Test
  @DisplayName("remove non-existing key throws exception")
  public void testRemoveNonExistingKey() {
    map.insert("1", "a");
    try {
      map.remove("2");
      fail("Expected IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("key not found", e.getMessage());
    }
  }

  @Test
  @DisplayName("Iterator returns keys in order")
  public void testIteratorInOrder() {
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");

    String[] expected = { "1", "2", "3" };
    int index = 0;
    for (String key : map) {
      assertEquals(expected[index++], key);
    }
    assertEquals(expected.length, index);
  }

  @Test
  @DisplayName("ToString represents treap structure")
  public void testToStringMethod() {
    map.insert("4", "d");
    map.insert("2", "b");
    map.insert("6", "f");
    String[] expected = { "4:d:-1155869325\n" +
        "2:b:431529176 6:f:1761283695" };
    assertEquals(String.join("\n", expected) + "\n", map.toString());
  }
}