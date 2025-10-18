public interface MyMapInt<K, V> {
    void remove(K key);

    /**
     *Коментарий 1
     *Коментарий 2
     * @param key
     * @return
     */
    V get(K key);
    V feature2(K key);
    V feature1(K key);
    int size();
    void put(K key, V value);
    int hash(K key);
}