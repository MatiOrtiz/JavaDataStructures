package ADTMap;

import ADTList.*;
import Exceptions.InvalidKeyException;

public class CloseHashMap<K,V> implements Map<K,V>{

	protected Entry<K,V>[] array;
	protected Entry<K,V> available;
	protected int n, N;
	protected static final double fc= 0.9;
	
	public CloseHashMap() {
		n= 0;
		N= 11;
		array= (Entry<K,V>[]) new Entrance[N];
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
		array= (Entry<K,V>[]) new Entrance[N];
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
		while(i<N && value==null) {
			if(array[aux]!=null && array[aux]!=available) 
				if(array[aux].getKey().equals(key)) 
					value= array[aux].getValue();
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
			if(array[aux]==null || array[aux]==available) {
				array[aux]= new Entrance<K,V>(key,value);
				n++;
				added= true;
			} else if(array[aux].getKey().equals(key)) {
					val= array[aux].getValue();
					Entrance<K,V> e= (Entrance<K,V>) array[aux];
					e.setValue(value);
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
		PositionList<K> iterable= new DoubleLinkedList<K>();
		for(Entry<K,V> e : array) 
			if(e!=null && e!=available)
				iterable.addLast(e.getKey());
		return iterable;
	}

	public Iterable<V> values() {
		PositionList<V> iterable= new DoubleLinkedList<V>();
		for(Entry<K,V> e : array)
			if(e!=null && e!=available)	
				iterable.addLast(e.getValue());
		return iterable;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> iterable= new DoubleLinkedList<Entry<K,V>>();
		for(Entry<K,V> e : array)
			if(e!=null && e!=available)	
				iterable.addLast(e);
		return iterable;
	}

}
