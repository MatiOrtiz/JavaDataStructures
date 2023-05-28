package ADTPriorityQueue;

import ADTList.*;
import ADTMap.Entrance;
import ADTMap.Entry;
import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.EmptyPriorityQueueException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;

public class OrderPQueueList<K,V> implements PriorityQueue<K,V> {

	protected PositionList<Entrance<K,V>> list;
	protected Comparator<K> comp;
	
	public OrderPQueueList(Comparator<K> comp) {
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
			throw new EmptyPriorityQueueException("The queue is empty.");
		Entry<K,V> entry= null;
		try {
			entry= list.first().element();
		}catch(EmptyListException e) {e.getMessage();}
		return entry;
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("The key is null.");
		Entrance<K,V> entry= new Entrance<K,V>(key,value);
		try {
			Position<Entrance<K,V>> pos= list.first();
			while(pos!=list.last() && comp.compare(entry.getKey(), pos.element().getKey())>0)
				pos= list.next(pos);
			if(comp.compare(entry.getKey(), pos.element().getKey())>0)
				list.addAfter(pos, entry);
			else list.addBefore(pos, entry);
		} catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {e.getMessage();}
		return entry;
	}

	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if(isEmpty())
			throw new EmptyPriorityQueueException("The queue is empty.");
		Entry<K,V> removed= min();
		try {
			list.remove(list.first());
		} catch(EmptyListException | InvalidPositionException e) {e.getMessage();}
		return removed;
	}

}
