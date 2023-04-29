package ADTQueue;

import Exceptions.EmptyQueueException;
import NodesPackage.Node;

public class LinkedQueue<E> implements Queue<E> {

	private Node<E> head, tail;
	private int size;
	
	public LinkedQueue() {
		head= null;
		tail= null;
		size= 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public E front() throws EmptyQueueException {
		if(size==0)
			throw new EmptyQueueException("The queue is empty.");
		return head.getElement();
	}
	
	public void enqueue(E element) {
		Node<E> node= new Node<E>(element);
		if(size==0)
			head= node;
		else {
			tail.setNext(node);
			tail= node;
			size++;
		}
	}
	
	public E dequeue() throws EmptyQueueException {
		if(size==0)
			throw new EmptyQueueException("The queue is empty.");
		E removed= head.getElement();
		head= head.getNext();
		size--;
		if(size==0)
			tail= null;
		return removed;
	}
}

