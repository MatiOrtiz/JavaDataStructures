package ADTPriorityQueue;

import ADTList.*;
import ADTMap.Entrance;
import ADTMap.Entry;
import Exceptions.EmptyListException;
import Exceptions.EmptyPriorityQueueException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;

public class UnorderListPQueue<K,V> implements PriorityQueue<K,V> {

	protected PositionList<Entrance<K,V>> list;
	protected Comparator<K> comp;
	
	public UnorderListPQueue(Comparator<K> comp) {
		list= new DoubleLinkedList<Entrance<K,V>>();
		this.comp= comp;
	}
	
	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if(isEmpty())
			throw new EmptyPriorityQueueException("The priorityQueue is empty.");
		Entry<K,V> entry= null;
		try {
			entry= list.first().element();
			for(Position<Entrance<K,V>> p : list.positions()) {
				if(comp.compare(entry.getKey(), p.element().getKey())<0)
					entry= p.element();
			}
		}catch(EmptyListException e) {e.getMessage();}
		return entry;
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("The key is null.");
		Entrance<K,V> e= new Entrance<K,V>(key, value);
		list.addFirst(e);
		return e;
	}

	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if(isEmpty())
			throw new EmptyPriorityQueueException("The priorityQueue is empty.");
		Entry<K,V> entry= null;
		try {
			Position<Entrance<K,V>> pos= list.first();
			for(Position<Entrance<K,V>> e : list.positions())
				if(comp.compare(pos.element().getKey(), e.element().getKey())>0)
					pos= e;
			entry= pos.element();
			list.remove(pos);
		}catch(EmptyListException | InvalidPositionException e) {e.getMessage();}
		return entry;
	}

}
