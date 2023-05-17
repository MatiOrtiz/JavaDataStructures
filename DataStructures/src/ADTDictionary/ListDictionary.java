package ADTDictionary;

import ADTMap.*;
import ADTList.*;
import Exceptions.InvalidEntryException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;

public class ListDictionary<K,V> implements Dictionary<K,V> {

	protected PositionList<Entry<K,V>> list;

	public ListDictionary() {
		list= new DoubleLinkedList<Entry<K,V>>();
	}
	
	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("The key is null.");
	}
	
	private void checkEntry(Entry<K,V> e) throws InvalidEntryException {
		if(e==null)
			throw new InvalidEntryException("The entry is empty.");
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		Entry<K,V> entry= null;
		checkKey(key);
		for(Entry<K,V> e : list) {
			if(e.getKey().equals(key))
				entry= e;
		}
		return entry;
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		PositionList<Entry<K,V>> iterable= new DoubleLinkedList<Entry<K,V>>();
		checkKey(key);
		for(Entry<K,V> e : list) {
			if(e.getKey().equals(key))
				iterable.addLast(e);
		}
		return iterable;
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entry<K,V> entry= new Entrance<K,V>(key,value);
		list.addLast(entry);
		return entry;
	}

	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		checkEntry(e);
		Entry<K,V> removed= null;
		boolean found= false;
		try {
			for(Position<Entry<K,V>> en : list.positions()) {
				if(en.element().equals(e)) {
					removed= e;
					list.remove(en);
					found= true;
				}
			}
			if(found==false)
				throw new InvalidEntryException("The entry doesn't exists.");
		} catch(InvalidPositionException exc) {exc.getMessage();}
		return removed;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> iterable= new DoubleLinkedList<Entry<K,V>>();
		for(Entry<K,V> e : list)
			iterable.addLast(e);
		return iterable;
	}

}
