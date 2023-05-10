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
	
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		V value= null;
		int aux= hashFunction(key);
		for(int i=0; i<N && array[aux]!=null && value==null; i++) {
			if(array[aux]!=available && array[aux].getKey().equals(key)) {
				value= array[aux].getValue();
			}
			aux= (aux+1)%N;
		}
		return value;
	}

	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		int aux= hashFunction(key);
		V val= null;
		if(array[aux].getKey()!=null && array[aux].getKey().equals(key)) {
			val= array[aux].getValue();
			array[aux].setValue(value);
		} else {
			Entrance<K,V> nuevo= new Entrance<K,V>(key, value);
			if(array[aux].getKey()==null)
				array[aux]= nuevo;
			else {
				while(array[aux]!=null)
					aux= (aux+1)%N;
				array[aux]= nuevo;
			}
			n++;
		}
		return val; 
	}

	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		int aux= hashFunction(key);
		V value= null;
		if(array[aux]!=available && array[aux].getKey().equals(key)) {
			value= array[aux].getValue();
			array[aux]= available;
			n--;
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
