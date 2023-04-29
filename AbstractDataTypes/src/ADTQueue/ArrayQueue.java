package ADTQueue;

import Exceptions.EmptyQueueException;

public class ArrayQueue<E> implements Queue<E> {
	
	private E[] array;
	private int f;  //next element's position to deque
	private int r;  //next element's position to queue
	private static final int longitud=20;
	
	public ArrayQueue() {
		array= (E[]) new Object[longitud];
		f= 0;
		r= 0;
	}
	
	public int size() {
		return (array.length-f+r) % array.length;
	}
	
	public boolean isEmpty() {
		return f==r;
	}
	
	public E front() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("The queue is empty.");
		return array[f];
	}
	
	public void enqueue(E element) {
		if(size()==array.length-1) {
			E[] aux= copy(f);
			r= size();
			f= 0;
			array= aux;
		}
		array[r]= element;
		r= (r+1)%array.length;
	}
	
	private E[] copy(int n) {
		int j=0; 
		E[] aux= (E[]) new Object[array.length*2];
		for(int i=f; n!=r; i++) {
			n= i%array.length;
			aux[j]= array[n];
			j++;
		}
		return aux;
	}
	
	public E dequeue() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("The queue is empty.");
		E removed= array[f];
		array[f]= null;
		f=(f+1)%array.length;
		return removed;
	}

}
