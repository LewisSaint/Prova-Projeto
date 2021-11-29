package mapa;

public interface Entry<K, V> {

    public K getKey();
    // Retorna o valor armazenado nesta entrada.
    public V getValue();
    
}
