public interface MyMapInt<K, V> {
    void remove(K key);
    V get(K key);
    int size();
    void put(K key, V value);
    int hash(K key);
}