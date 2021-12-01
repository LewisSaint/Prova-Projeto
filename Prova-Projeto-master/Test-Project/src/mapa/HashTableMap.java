package mapa;


import exception.InvalidKeyException;
import mapa.nodelist.NodePositionList;
import mapa.nodelist.PositionList;

public class HashTableMap<K, V> implements Map<K, V>  {

    public static class HashEntry<K, V> implements Entry<K, V> {
        protected K key;
        protected V value;
        public HashEntry(K k, V v) {
            key = k;
            value = v;
        }
        public V getValue() { return value; }
        public K getKey() { return key; }
        public V setValue(V val) {
            V oldValue = value;
            value = val;
            return oldValue;
        }

        public boolean equals(Object o) {
            HashEntry<K, V> ent;
            try {
                ent = (HashEntry<K, V>) o;
            } catch (ClassCastException ex) { return false; }
            return (ent.getKey() == key) && (ent.getValue() == value);
            }
            public String toString() {
                return "(" + key + "," + value + ")";
                }

    }

    protected Entry<K, V> AVAILABLE = new HashEntry<K, V>(null, null);
    protected int n = 0; // número de entradas no mapa
    protected int prime, capacity; // fator primo e capacidade do array de buckets
    protected Entry<K, V>[] bucket;// array de buckets
    protected long scale, shift; // fatores de escala e de shift
    // Cria uma tabela hash com fator primo 109345121 e capacidade 1000
    public HashTableMap() { this(109345121, 1000); }
    // Cria uma tabela hash com fator primo 109345121 e capacidade fornecida
    public HashTableMap(int cap) { this(109345121, cap); }
    @SuppressWarnings("unchecked")
    public HashTableMap(int p, int cap) {
        prime = p;
        capacity = cap;
        bucket = (Entry<K, V>[]) new Entry[capacity]; // Cast seguro
        java.util.Random rand = new java.util.Random();
        scale = rand.nextInt(prime - 1) + 1;
        shift = rand.nextInt(prime);
    }

    protected void checkKey(K k) {
        if (k == null) throw new InvalidKeyException("Invalid key: null.");
    }

    // A função hash aplica o método MAD para o código hash padrão
    public int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    // Retorna o número de entradas da tabela hash
    public int size() { return n; }
    // Retorna se a tabela está vaiz ou não
    public boolean isEmpty() { return (n == 0); }
    
    public Iterable<K> keySet() {
        
        PositionList<K> keys = new NodePositionList<K>();
        for (int i = 0; i < capacity; i++)
            if ((bucket[i] != null) && (bucket[i] != AVAILABLE)) keys.addLast(bucket[i].getKey());
        return keys;
    }
    // Método de busca auxiliar - Retorna o índice da chave encontrada ou -(a+1),
    // onde a é o índice do primeiro slot vazio ou disponível encontrado

    protected int findEntry(K key) throws InvalidKeyException {
        int avail = -1;
        checkKey(key);
        int i = hashValue(key);
        int j = i;

        do {
            Entry<K, V> e = bucket[i];
            if (e == null) {
                if (avail < 0) avail = i; // a chave não está na tabela
                break;
            }
            if (key.equals(e.getKey())){return i;} // encontramos a chave
            
            if (e == AVAILABLE) {
                if (avail < 0) avail = i;
            }

            i = (i + 1) % capacity; 
        } while (i != j);

        return -(avail + 1);


    }

    public V get(K key) throws InvalidKeyException {
        int i = findEntry(key); // método auxiliar para encontrar uma chave
        if (i < 0) return null; // não existe valor para esta chave, então retorna null
        return bucket[i].getValue(); // retorna o valor encontrado neste caso
    }
    
    public V put(K key, V value) throws InvalidKeyException {

        int i = findEntry(key); // encontra o local apropriado de entrada
        if (i >= 0) // esta chave tem um valor anterior
            return ((HashEntry<K, V>) bucket[i]).setValue(value);
        
        if (n >= capacity / 2) {
            rehash(); // rehash para manter o faltor de carga <= 0.5
            i = findEntry(key); // encontrar o local apropriado novamente para esta entrada
        }
        bucket[-i - 1] = new HashEntry<K, V>(key, value); // converte para o índice apropriado
        n++;
        return null;
                    
    }

    // Dobra o tamanho da tabela hash e rehashes todas as entradas
    protected void rehash() {
        capacity = 2 * capacity;
        Entry<K, V>[] old = bucket;
        bucket = (Entry<K, V>[]) new Entry[capacity]; // novo bucket é duas vezes maior
        java.util.Random rand = new java.util.Random();
        scale = rand.nextInt(prime - 1) + 1; // novo fator de escala do hash
        shift = rand.nextInt(prime); // novo fator de deslocamento do hash
        for (int i = 0; i < old.length; i++) {
            Entry<K, V> e = old[i];
            if ((e != null) && (e != AVAILABLE)) {
                int j = -1 - findEntry(e.getKey()); // o método retorna -(i+1)
                bucket[j] = e;

            }
        }


    }
    public V remove(K key) throws InvalidKeyException {
        int i = findEntry(key); // encontra a chave primeiro
        if (i < 0) return null; // nada a remover
        V toReturn = bucket[i].getValue();
        bucket[i] = AVAILABLE; // marca este local como desativado
        n--;
        return toReturn;

    }

    public Iterable<Entry<K, V>> entrySet() {
        PositionList<Entry<K, V>> entries = new NodePositionList<Entry<K, V>>();
        for (int i = 0; i < capacity; i++)
            if ((bucket[i] != null) && (bucket[i] != AVAILABLE)) entries.addLast(bucket[i]);
        return entries;
        }

        public Iterable<V> values() {
            PositionList<V> values = new NodePositionList<V>();
            for (int i = 0; i < capacity; i++)
                if ((bucket[i] != null) && (bucket[i] != AVAILABLE)) values.addLast(bucket[i].getValue());
            return values;
            }
            
        
        


}



            
                    
    
