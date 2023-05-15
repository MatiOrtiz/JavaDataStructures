package ADTDictionary;

import ADTMap.*;
import Exceptions.InvalidEntryException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;
import ADTList.*;

public class OpenHashDictionary<K,V> implements Dictionary<K,V> {

	protected PositionList<Entrance<K,V>>[] array;
	protected int N;
	protected int n;
	protected static final float fc= 0.9F;
	
	public OpenHashDictionary() {
		N= 11;
		n= 0;
		array= new DoubleLinkedList[N];
		for(int i=0; i<N; i++)
			array[i]= new DoubleLinkedList<Entrance<K,V>>();
	}
	
	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return n==0;
	}
	
	private int hashFunction(K key) {
		return Math.abs(key.hashCode()%N);
	}
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("The key is null.");
	}
	
	private void checkEntry(Entry<K,V> e) throws InvalidEntryException {
		if(e==null)
			throw new InvalidEntryException("The entry is null.");
	}

	public Entry<K,V> find(K key) throws InvalidKeyException {
		checkKey(key);
		int aux= hashFunction(key);
		Entry<K,V> entry= null;
		for(Entry<K,V> e : array[aux]) {
			if(e.getKey().equals(key))
				entry= e;
		}
		return entry;
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		PositionList<Entry<K,V>> iterable= new DoubleLinkedList<Entry<K,V>>();
		checkKey(key);
		int aux= hashFunction(key);
		for(Entry<K,V> e : array[aux]) {
			if(e.getKey().equals(key))
				iterable.addLast(e);
		}
		return iterable;
	}
	
	private int nextPrime(int num) {
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
		Iterable<Entry<K,V>> entrances= entries();
		N= nextPrime(N*2);
		n= 0;
		try {
			array= new DoubleLinkedList[N];
			for(int i=0; i<N; i++)
				array[i]= new DoubleLinkedList<Entrance<K,V>>();
			for(Entry<K,V> e : entrances)
				this.insert(e.getKey(), e.getValue());
		} catch(InvalidKeyException e) {e.getMessage();}
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		if(n/N> fc)
			reHash();
		
		int aux= hashFunction(key);
		Entrance<K,V> entry= new Entrance<K,V>(key, value);
		array[aux].addLast(entry);
		n++;
		return entry;
	}

	public Entry<K,V> remove(Entry<K, V> e) throws InvalidEntryException {
		checkEntry(e);
		Entry<K,V> entry= null;
		int aux= hashFunction(e.getKey());
		boolean found= false;
		try {
			for(Position<Entrance<K,V>> en : array[aux].positions()) {
				if(e.equals(en)) {
					entry= e;
					array[aux].remove(en);
					found= true;
					n--;
				}
			}
			if(found==true)
				throw new InvalidEntryException("The entry doesn't exists");
		} catch(InvalidPositionException exc) {exc.getMessage();}
		return entry;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> iterable= new DoubleLinkedList<Entry<K,V>>();
		for(int i=0; i<N; i++)
			for(Entry<K,V> e : array[i])
				iterable.addLast(e);
		return iterable;
	}

}
