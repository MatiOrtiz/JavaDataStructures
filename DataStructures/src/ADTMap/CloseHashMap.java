package ADTMap;

import ADTList.DoubleLinkedList;
import Exceptions.InvalidKeyException;

public class CloseHashMap<K,V> implements Map<K,V>{

	protected Entrance<K,V>[] array;
	protected Entrance<K,V> available;
	protected int n, N;
	protected static final double fc= 0.9;
	
	public CloseHashMap() {
		n= 0;
		N= 11;
		array= (Entrance<K,V>[]) new Entrance[N];
		available= new Entrance<K,V>(null, null);
	}
	
	private int hashFunction(K key) throws InvalidKeyException {
		checkKey(key);
		return Math.abs(key.hashCode()%N);
	}
	
	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return n==0;
	}

	private void checkKey(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("The key is null.");
	}
	
	private int nextPrime(int n) {
		int aux=n*2;
		boolean primo=false;
		while(!primo) {
			primo=true;
			for(int i=2;i<aux/2;i++)
				if(aux%i==0)
					primo=false;
			aux++;
		}
		return aux;
	}
	
	private void reHash() {
		Iterable<Entry<K,V>> entries= entries();
		N= nextPrime(N*2);
		array= (Entrance<K,V>[]) new Entrance[N];
		n= 0;
		try {
			for(Entry<K,V> e : entries) {
				this.put(e.getKey(), e.getValue());
			}
		} catch(InvalidKeyException e) {e.getMessage();}
	}
	
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		V value= null;
		int aux= hashFunction(key);
		int i=0;
		while(i<N && array[aux]!=null && value==null) {
			if(array[aux]!=available && array[aux].getKey().equals(key)) {
				value= array[aux].getValue();
			} 
			aux= (aux+1)%N;
			i++;
		}
		return value;
	}

	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		int aux= hashFunction(key);
		V val= null;
		boolean added= false;
		if((n/N)>fc)
			reHash();
		for(int i=0; i<N && added==false; i++) {
			if(array[aux]==null || array[aux]!=available) {
				array[aux]= new Entrance<K,V>(key,value);
				n++;
				added= true;
			} else if(array[aux].getKey().equals(key)) {
					val= array[aux].getValue();
					array[aux].setValue(value);
					added= true;
				} else {
				aux= (aux+1)%N;
			}
		}
		return val; 
	}

	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		int aux= hashFunction(key);
		V value= null;
		boolean removed= false;
		for(int i=0; i<N && removed==false; i++) {
			if(array[aux]!=null && array[aux]!=available) {
				if(array[aux].getKey().equals(key)) {
					value= array[aux].getValue();
					array[aux]= available;
					n--;
					removed= true;
				}  
			} 
			aux= (aux+1)%N;
		}
		return value;
	}

	public Iterable<K> keys() {
		DoubleLinkedList<K> iterable= new DoubleLinkedList<K>();
		for(int i=0; i<N; i++)
			iterable.addLast(array[i].getKey());
		return iterable;
	}

	public Iterable<V> values() {
		DoubleLinkedList<V> iterable= new DoubleLinkedList<V>();
		for(int i=0; i<N; i++)
			iterable.addLast(array[i].getValue());
		return iterable;
	}

	public Iterable<Entry<K, V>> entries() {
		DoubleLinkedList<Entry<K,V>> iterable= new DoubleLinkedList<Entry<K,V>>();
		for(int i=0; i<N; i++)
			iterable.addLast(array[i]);
		return iterable;
	}

}

/*
public class CloseHashMap<K,V> implements Map<K,V> {
    protected Entrance<K,V> [] buckets;
    protected Entrance<K,V> available;
    protected int size;
    protected int N;
    protected final float FACTOR_CARGA = 0.5F;

    public CloseHashMap(int N) {
        this.N = N;
        size = 0;
        buckets = (Entrance<K,V> []) new Entrance[N];
        available = new Entrance<K,V>(null, null);
    }

    public CloseHashMap() {
        this(997);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V get(K key) throws InvalidKeyException {
        checkKey(key);
        V toReturn = null;
        int i = hashValue(key);
        int aux = 1;
        while((aux <= N) && (buckets[i] != null) && (toReturn == null)) {
            if((buckets[i] != available) && (buckets[i].getKey().equals(key)))
                toReturn = buckets[i].getValue();
            i = (i+1) % N;
            aux++;
        }
        return toReturn;
    }

    @Override
    public V put(K key, V value) throws InvalidKeyException {
        checkKey(key);
        V toReturn = null;
        boolean estado = true;
        int i = hashValue(key);
        int aux = 1;

        if((float)(size/N) >= FACTOR_CARGA) {
            reHash();
        }

        while (aux <= N && estado){
            if(buckets[i] == null || buckets[i] == available){
                buckets[i] = new Entrance<K,V>(key, value);
                estado = false;
                size++;
            }
            else if(buckets[i].getKey().equals(key)) {
                toReturn = buckets[i].getValue();
                buckets[i].setValue(value);
                estado = false;
            }
            else {
                i = (i+1) % N;
            }
            aux++;
        }

        return toReturn;
    }

    @Override
    public V remove(K key) throws InvalidKeyException {
        checkKey(key);
        V toReturn = null;
        int i = hashValue(key);
        int aux = 1;

        while (aux <= N && buckets[i] != null && toReturn == null){
            if(buckets[i] != available && buckets[i].getKey().equals(key)) {
                toReturn = buckets[i].getValue();
                buckets[i] = available;
                size--;
            }
            else {
                i = (i+1) % N;
            }
            aux++;
        }
        return toReturn;
    }

    @Override
    public Iterable<K> keys() {
        DoubleLinkedList<K> keys = new DoubleLinkedList<K>();
        for(Entrance<K,V> e: buckets){
            if((e != null) && (e != available))
                keys.addLast(e.getKey());
        }
        return keys;
    }

    @Override
    public Iterable<V> values() {
        DoubleLinkedList<V> values = new DoubleLinkedList<V>();
        for(Entrance<K,V> e: buckets){
            if((e != null) && (e != available))
                values.addLast(e.getValue());
        }
        return values;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        DoubleLinkedList<Entry<K,V>> entries = new DoubleLinkedList<Entry<K,V>>();
        for(Entrance<K,V> e: buckets){
            if((e != null) && (e != available))
                entries.addLast(e);
        }
        return entries;
    }

    private void checkKey(K key) throws InvalidKeyException {
        if(key == null) {
            throw new InvalidKeyException("Clave nula.");
        }
    }

    private void reHash() throws InvalidKeyException{
        Iterable<Entry<K,V>> entries = this.entries();
        N = nextPrime(N*2);
        buckets = (Entrance<K,V> []) new Entrance[N];
        size = 0;
        for(Entry<K,V> e : entries) {
            this.put(e.getKey(), e.getValue());
        }
    }

    private int nextPrime(int num) {
        int toReturn = 0;
        boolean isPrime = false;
        while(!isPrime) {

            isPrime = true;
            for (int j = 2; (j<=Math.sqrt(num)) && (isPrime); j++) {
                if((num % j) == 0) {
                    isPrime=false;
                    num++;
                }
            }
            if(isPrime)
                toReturn= num;
        }
        return toReturn;
    }

    private int hashValue(K key){
        return Math.abs(key.hashCode() % N);
    }
}

*/