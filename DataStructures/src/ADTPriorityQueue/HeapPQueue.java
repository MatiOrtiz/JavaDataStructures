package ADTPriorityQueue;

import Exceptions.EmptyPriorityQueueException;
import Exceptions.InvalidKeyException;
import ADTMap.*;

public class HeapPQueue<K,V> implements PriorityQueue<K,V> {
	
	protected Entrance<K,V>[] elems;
	protected Comparator<K> comp;
	protected int size;
	
	public HeapPQueue(Comparator<K> comp) {
		elems= new Entrance[20];
		this.comp= comp;
		size= 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}

	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if(isEmpty())
			throw new EmptyPriorityQueueException("The queue is empty.");
		return elems[1];
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("The key is null.");
		Entrance<K,V> entrada= new Entrance<K,V>(key, value);
		size++;
		elems[size]= entrada;
		
		int i= size;
		boolean seguir= true;
		while(i>1 && seguir) {
			Entrance<K,V> elemActual= elems[i];
			Entrance<K,V> elemPadre= elems[i/2];
			if(comp.compare(elemActual.getKey(), elemPadre.getKey())<0) {
				Entrance<K,V> aux= elems[i];
				elems[i]= elems[i/2];
				elems[i/2]= aux;
				i/=2;
			} else seguir= false;
		}
		return entrada;
	}

	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		Entry<K,V> entrada= min();
		if(size==1) {
			elems[1]= null;
			size= 0;
		} else {
			elems[1]= elems[size];
			elems[size]= null;
			size--;
			int i= 1;
			boolean seguir= true;
			while(seguir) {
				int hi= i*2;
				int hd= i*2+1;
				boolean tieneHijoIzq= hi<=size();
				boolean tieneHijoDer= hd<=size();
				if(!tieneHijoIzq)
					seguir= false;
				else {
					int m;
					if(tieneHijoDer) {
						if(comp.compare(elems[hi].getKey(), elems[hd].getKey())<0)
							m= hi;
						else m= hd;
					} else m= hi;
					if(comp.compare(elems[i].getKey(), elems[m].getKey())>0) {
						Entrance<K,V> aux= elems[i];
						elems[i]= elems[m];
						elems[m]= aux;
						i= m;
					} else seguir= false;
				}
			}
		}
		return entrada;
	}
	
	public Entry<K,V>[] order(Entry<K,V>[] a) {
		PriorityQueue<K,V> array= new HeapPQueue<K,V>(new Comparator<K>());
		try {
			for(int i=1; i<=a.length; i++) 
				if(a[i]!=null) {
					array.insert(a[i].getKey(), a[i].getValue());
					a[i]= null;
				}
			for(int i=1; i<=a.length && !array.isEmpty(); i++) {
				a[i]= removeMin();
			}
		}catch(InvalidKeyException | EmptyPriorityQueueException e) {e.getMessage();}
		return a;
	}
}
