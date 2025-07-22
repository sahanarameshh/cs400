import java.util.NoSuchElementException;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A hashtable implementation of the MapADT interface using chaining with linked lists
 * to handle collisions.
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
    
    protected LinkedList<Pair>[] table = null;
    private int size;
    
    /**
     * Inner class to store key-value pairs
     */
    protected class Pair {
        public KeyType key;
        public ValueType value;
        
        public Pair(KeyType key, ValueType value) {
            this.key = key;
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public HashtableMap(int capacity) {
        table = (LinkedList<Pair>[]) new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public HashtableMap() {
        table = (LinkedList<Pair>[]) new LinkedList[64];
        for (int i = 0; i < 64; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    /**
     * Resizes the hash table by doubling its capacity and rehashing all elements
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        // store old table and create new one
        LinkedList<Pair>[] oldTable = table;
        table = (LinkedList<Pair>[]) new LinkedList[oldTable.length * 2];
        
        // initialize all buckets in the new table
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
        
        // iterate through buckets in old table and rehash each pair
        for (int i = 0; i < oldTable.length; i++) {
            LinkedList<Pair> bucket = oldTable[i];
            if (bucket != null) {
                for (Pair pair : bucket) {
                    int newIndex = Math.abs(pair.key.hashCode()) % table.length;
                    table[newIndex].add(pair);
                    size++;
                }
            }
        }
    }

    /**
     * Adds a new key,value pair/mapping to this collection. It is ok that the value is null.
     * @param key the key of the key,value pair
     * @param value the value that key maps to
     * @throws IllegalArgumentException if key already maps to a value
     * @throws NullPointerException if key is null
     */
    public void put(KeyType key, ValueType value) throws IllegalArgumentException {
        // check for null keys
        if (key == null) {
            throw new NullPointerException("Key cannot be null.");
        }

        // check for pre-existing keys
        if (containsKey(key)) {
            throw new IllegalArgumentException("Key already maps to a value.");
        }

        // generate index
        int index = Math.abs(key.hashCode()) % table.length;

        // add into table and update size
        table[index].add(new Pair(key, value));
        size++;

        // check for the need to resize
        double loadFactor = (double) size / table.length;
        if (loadFactor >= 0.8) {
            resize();
        }
    }

    /**
     * Checks whether a key maps to a value in this collection.
     * @param key the key to check
     * @return true if the key maps to a value, and false is the
     *         key doesn't map to a value
     */
    public boolean containsKey(KeyType key) {
        // check for null keys
        if (key == null) {
            throw new NullPointerException("Key cannot be null.");
        }

        // generate index
        int index = Math.abs(key.hashCode()) % table.length;

        // loop through table and check each linked lists' key
        for (int i = 0; i < table[index].size(); i++) {
            Pair p = table[index].get(i);
            if (p.key.equals(key)) {
                return true;
            }
        }

        // otherwise the key does not map to a value
        return false;
    }

    /**
     * Retrieves a list of all keys in this hashtable
     * @return a LinkedList containing all keys in the hashtable
     */
    public LinkedList<KeyType> getKeys() {
        LinkedList<KeyType> keyList = new LinkedList<>();
        
        // iterate through all buckets in the table
        for (int i = 0; i < table.length; i++) {
            // add each key to the result list
            for (Pair p : table[i]) {
                keyList.add(p.key);
            }
        }
        
        return keyList;
    }

    /**
     * Retrieves the specific value that a key maps to.
     * @param key the key to look up
     * @return the value that key maps to
     * @throws NoSuchElementException when key is not stored in this
     *         collection
     */
    public ValueType get(KeyType key) throws NoSuchElementException {
        // check for null keys
        if (key == null) {
            throw new NullPointerException("Key cannot be null.");
        }

        // generate index
        int index = Math.abs(key.hashCode()) % table.length;
        
        // loop through table and check each linked lists' key
        for (int i = 0; i < table[index].size(); i++) {
            Pair p = table[index].get(i);
            if (p.key.equals(key)) {
                return p.value;
            }
        }

        throw new NoSuchElementException("Key is not stored in this collection.");
    }

    /**
     * Remove the mapping for a key from this collection.
     * @param key the key whose mapping to remove
     * @return the value that the removed key mapped to
     * @throws NoSuchElementException when key is not stored in this
     *         collection
     */
    public ValueType remove(KeyType key) throws NoSuchElementException {
        // check for null keys
        if (key == null) {
            throw new NullPointerException("Key cannot be null.");
        }

        // generate index
        int index = Math.abs(key.hashCode()) % table.length;

        // loop through table and check each linked lists' key
        for (int i = 0; i < table[index].size(); i++) {
            Pair p = table[index].get(i);
            if (p.key.equals(key)) {
                table[index].remove(i);
                size--;
                return p.value;
            }
        }

        throw new NoSuchElementException("Key is not stored in this collections.");
    }

    /**
     * Removes all key,value pairs from this collection.
     */
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i].clear();
        }
        size = 0;

    }

    /**
     * Retrieves the number of keys stored in this collection.
     * @return the number of keys stored in this collection
     */
    public int getSize() {
        return size;
    }

    /**
     * Retrieves this collection's capacity.
     * @return the size of the underlying array for this collection
     */
    public int getCapacity() {
        return table.length;
    }


    // TESTERS
    /**
     * Test both HashTable constructors
     */
    @Test
    public void testConstructorAndCapacity() {
        HashtableMap<String, Integer> map1 = new HashtableMap<>(100);
        assertEquals(100, map1.getCapacity());
        
        HashtableMap<String, Integer> map2 = new HashtableMap<>();
        assertEquals(64, map2.getCapacity());
    }
    
    /**
     * Test put(), get(), and getSize()
     */
    @Test
    public void testPutAndGet() {
        HashtableMap<String, Integer> map = new HashtableMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertEquals(3, map.get("three"));
        assertEquals(3, map.getSize());
    }
    
    /**
     * Test remove()
     */
    @Test
    public void testRemove() {
        HashtableMap<String, Integer> map = new HashtableMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        assertEquals(3, map.getSize());
        
        Integer removed = map.remove("two");
        assertEquals(2, map.getSize());
        assertEquals(2, removed);
        assertTrue(!map.containsKey("two"));
    }
    
    /**
     * Test clear()
     */
    @Test
    public void testClear() {
        HashtableMap<Integer, String> map = new HashtableMap<>();
        map.put(1, "one");
        map.put(2, "two");
        
        assertEquals(2, map.getSize());
        
        map.clear();
        assertEquals(0, map.getSize());
        assertTrue(!map.containsKey(1));
        
        map.put(3, "three");
        assertEquals("three", map.get(3)); // Fixed to check for key 3 instead of key 1
    }

    /**
     * Test duplicate keys
     */
    @Test
    public void testDuplicateKeys() {
        HashtableMap<Integer, String> map = new HashtableMap<>();
        map.put(1, "one");
        map.put(2, "two");
        
        assertThrows(IllegalArgumentException.class, () -> map.put(1, "duplicate"));
        
        assertEquals("one", map.get(1));
    }

    @Test
    public void debugging() {
        HashtableMap<Integer, String> map = new HashtableMap<>();
        map.put(1, "one");
        map.put(2, "two");
    }
}