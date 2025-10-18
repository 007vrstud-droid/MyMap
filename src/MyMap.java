import java.util.Map;
import java.util.Objects;

public class MyMap<K, V> implements MyMapInt<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD = 0.7f;
    private int currentIndex = 0;
    private V nullKeyValue = null;
    private boolean hasNullKey = false;
    private Pair<K, V>[] pairs = new Pair[INITIAL_CAPACITY];

    private static class Pair<K, V> {
        private final K key;
        private V value;
        private Pair<K, V> next;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public int size() {
        return currentIndex;
    }

    @Override
    public V feature1(K key) {
        return null;
    }

    @Override
    public int hash(K key) {
        if (key == null) return 0;
        return Math.abs(key.hashCode() % pairs.length);
    }

    private void grow() {
        Pair<K, V>[] oldPairs = pairs;
        pairs = new Pair[oldPairs.length * 2];
        currentIndex = hasNullKey ? 1 : 0;

        for (int i = 0; i < oldPairs.length; i++) {
            Pair<K, V> temp = oldPairs[i];
            while (temp != null) {
                addPair(temp.key, temp.value);
                temp = temp.next;
            }
        }
    }

    private void addPair(K key, V value) {
        int index = hash(key);
        Pair<K, V> temp = pairs[index];

        if (temp == null) {
            pairs[index] = new Pair<>(key, value);
            currentIndex++;
        } else {
            while (true) {
                if (Objects.equals(temp.key, key)) {
                    temp.value = value;
                    return;
                }
                if (temp.next == null) {
                    temp.next = new Pair<>(key, value);
                    currentIndex++;
                    break;
                }
                temp = temp.next;
            }
        }
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            if (!hasNullKey) currentIndex++;
            nullKeyValue = value;
            hasNullKey = true;
            return;
        }
        addPair(key, value);
        if (currentIndex >= pairs.length * LOAD) {
            grow();
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return hasNullKey ? nullKeyValue : null;
        }
        int index = hash(key);
        Pair<K, V> temp = pairs[index];
        while (temp != null) {
            if (Objects.equals(temp.key, key)) {
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        if (key == null) {
            if (hasNullKey) {
                nullKeyValue = null;
                hasNullKey = false;
                currentIndex--;
            }
            return;
        }
        int index = hash(key);
        Pair<K, V> temp = pairs[index];
        Pair<K, V> previous = null;

        while (temp != null) {
            if (Objects.equals(temp.key, key)) {
                if (previous == null) {
                    pairs[index] = temp.next;
                } else {
                    previous.next = temp.next;
                }
                currentIndex--;
                return;
            }
            previous = temp;
            temp = temp.next;
        }
    }
}
