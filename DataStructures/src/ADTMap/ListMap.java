package ADTMap;
import java.util.Iterator;

import ADTList.DoubleLinkedList;
import ADTList.Position;
import ADTList.PositionList;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;

public class ListMap<K,V> implements Map<K,V> {

	protected DoubleLinkedList<Entrance<K,V>> list;
	
	public ListMap() {
		list= new DoubleLinkedList<Entrance<K,V>>();
	}
	
	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();	
	}
	
	private K checkKey(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("The key is null.");
		return key;
	}

	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		V value= null;
		for(Position<Entrance<K,V>> p : list.positions()) {
			if(p.element().getKey().equals(key))
				value= p.element().getValue();
		}
		return value;
	}

	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		V replaced= null;
		for(Position<Entrance<K,V>> p : list.positions()) 
			if(p.element().getKey().equals(key)) {
				replaced= p.element().getValue();
				p.element().setValue(value);
			}
		if(replaced==null)
			list.addLast(new Entrance<K,V>(key,value));
		return replaced;
	}

	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		V removed= null;
		try {
			for(Position<Entrance<K,V>> p : list.positions())
				if(p.element().getKey().equals(key)) {
					removed= p.element().getValue();
					list.remove(p);
				}
		} catch(InvalidPositionException e) {e.getMessage();}
		return removed;
	}

	public Iterable<K> keys() {
		DoubleLinkedList<K> iterable= new DoubleLinkedList<K>();
		for(Position<Entrance<K,V>> p : list.positions())
			iterable.addLast(p.element().getKey());
		return iterable;
	}

	public Iterable<V> values() {
		DoubleLinkedList<V> iterable= new DoubleLinkedList<V>();
		for(Position<Entrance<K,V>> p : list.positions())
			iterable.addLast(p.element().getValue());
		return iterable;
	}

	public Iterable<Entry<K, V>> entries() {
		DoubleLinkedList<Entry<K,V>> iterable= new DoubleLinkedList<Entry<K,V>>();
		for(Position<Entrance<K,V>> p : list.positions())
			iterable.addLast(p.element());
		return iterable;
	}
	
	
	//Extra Method: return a list with the entries that have the same key.
	public PositionList<Entry<K,V>> luNotas(Map<K,V> m1, Map<K,V> m2) {
		PositionList<Entry<K,V>> list= new DoubleLinkedList<Entry<K,V>>();
		for(Entry<K,V> e1 : m1.entries()) {
			for(Entry<K,V> e2 : m2.entries()) {
				if(e1.getKey().equals(e2.getKey())) {
					list.addLast(e1);
					list.addLast(e2);
				}
			}
		}
		return list;
	}
	
	public PositionList<Entry<K,V>> luNotas1(Map<K,V> m1, Map<K,V> m2) {
		PositionList<Entry<K,V>> list= new DoubleLinkedList<Entry<K,V>>();
		for(Entry<K,V> e : m1.entries()) {
			Iterator<Entry<K,V>> it= m1.entries().iterator();
			while(it.hasNext() && !it.next().getKey().equals(e.getKey())) {
				it.next();
			}
			if(e.getKey().equals(it.next().getKey()))
				list.addLast(e);
		}
		return list;
	}

}
