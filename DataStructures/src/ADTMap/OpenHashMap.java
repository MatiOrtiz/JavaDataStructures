package ADTMap;

import java.util.Iterator;

import ADTList.*;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;

public class OpenHashMap<K,V> implements Map<K,V> {

	protected PositionList<Entry<K,V>>[] array;
	protected int N;
	protected int n;
	protected static final float fc= 0.9F;
	
	public OpenHashMap() {
		N= 11;
		n= 0;
		array= (PositionList<Entry<K,V>>[]) new DoubleLinkedList[N];
		for(int i=0; i<N; i++) {
			array[i]= new DoubleLinkedList<Entry<K,V>>();
		}
	}
	
	protected int hashFunction(K key) throws InvalidKeyException {
		checkKey(key);
		return Math.abs(key.hashCode() % N);
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
		int aux= hashFunction(key);
		V value= null;
		for(Entry<K,V> p : array[aux]) {
			if(p.getKey().equals(key))
				value= p.getValue();
		}
		return value;
	}
	
	private void reHash() {
		Iterable<Entry<K,V>> entrances= entries();
		N= nextPrime(N*2);
		n= 0;
		try {
			array= (PositionList<Entry<K,V>>[]) new DoubleLinkedList[N];
			for(int i=0; i<N; i++)
				array[i]= new DoubleLinkedList<Entry<K,V>>();
			for(Entry<K,V> e : entrances)
				this.put(e.getKey(), e.getValue());
		}catch(InvalidKeyException e) {e.getMessage();}
	}
	
	private int nextPrime(int n) {
		int aux=n*2;
		boolean primo=false;
		while(!primo) {
			primo=true;
			for(int i=2;i<aux/2;i++)
				if(aux%i==0)
					primo=false;
			if(primo==false)
				aux++;
		}
		return aux;
	}

	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		int aux= hashFunction(key);
		V val= null;
		boolean found= false;
		
		for(Entry<K,V> p : array[aux]) 
			if(p.getKey().equals(key)) {
				val= p.getValue();
				Entrance<K,V>e= (Entrance<K,V>)p;
				e.setValue(value);
				found= true;
			}
		if(found==false) {
			Entrance<K,V> nuevo= new Entrance<K,V>(key, value);
			array[hashFunction(key)].addLast(nuevo);
			n++;
			if(n/N>fc)
				reHash();
		}
		
		return val;
	}

	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		int aux= hashFunction(key);
		V value= null;
		try {
			for(Position<Entry<K,V>> p : array[aux].positions()) {
				if(p.element().getKey().equals(key)) {
					value= p.element().getValue();
					array[aux].remove(p);
					n--;
				}
			}
		}catch(InvalidPositionException e) {e.getMessage();}
		return value;
	}

	public Iterable<K> keys() {
		DoubleLinkedList<K> iterable= new DoubleLinkedList<K>();
		for(int i=0; i<N; i++)
			for(Entry<K,V> e : array[i]) {
				iterable.addLast(e.getKey());
			}	
		return iterable;
	}

	public Iterable<V> values() {
		DoubleLinkedList<V> iterable= new DoubleLinkedList<V>();
		for(int i=0; i<N; i++)
			for(Entry<K,V> e : array[i])
				iterable.addLast(e.getValue());
		return iterable;
	}

	public Iterable<Entry<K, V>> entries() {
		DoubleLinkedList<Entry<K,V>> iterable= new DoubleLinkedList<Entry<K,V>>();
		for(int i=0; i<N; i++)
			for(Entry<K,V> e : array[i])
				iterable.addLast(e);
		return iterable;
	}

	public Map<V,K> invertedMap(Map<K,V> m) throws InvalidKeyException{
		Map<V,K> map= new OpenHashMap<V,K>();
		for(Entry<K,V> e : m.entries()) {
			map.put(e.getValue(), e.getKey());
		}
		return map;
	}
	
	public boolean contained(Map<K,V> m1, Map<K,V> m2) {
		boolean is= true;
		Iterator<Entry<K,V>> it1= m1.entries().iterator();
		try {
			for(Entry<K,V> e : m1.entries()) {
				if(m2.get(e.getKey())!=null)
					is= true;
				else is= false;
			}
		} catch(InvalidKeyException e) {e.getMessage();}
		return is;
	}
	
}

