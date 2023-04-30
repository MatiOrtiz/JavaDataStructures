package ADTList;
import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;
import NodesPackage.LNode;
import java.util.Iterator;

public class LinkedList<E> implements PositionList<E> {

	protected LNode<E> head; 
	protected int size; 
	
	public LinkedList() {
		head= null;
		size= 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}

	public Position<E> first() throws EmptyListException {
		if(size==0)
			throw new EmptyListException("The list is empty.");
		return head;
	}

	public Position<E> last() throws EmptyListException {
		if(size==0)
			throw new EmptyListException("The list is empty.");
		LNode<E> aux= head;
		while(aux.getNext()!=null)
			aux= aux.getNext();
		return aux;
	}
	
	private LNode<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try { 
			if(p==null)
				throw new InvalidPositionException("The position is null.");
			if(p.element()==null)
				throw new InvalidPositionException("The position has been removed.");
		} catch(ClassCastException e) { throw new InvalidPositionException("The position is not a list node.");}
		return (LNode<E>) p;
	}

	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		LNode<E> aux= checkPosition(p);
		if(aux.getNext()==null)
			throw new BoundaryViolationException("The position is the last in the list.");
		return aux.getNext();
	}

	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		LNode<E> aux= checkPosition(p);
		LNode<E> aux2= head;
		if(aux==head)
			throw new BoundaryViolationException("The position is the first.");
		while(aux2.getNext()!=aux && aux2.getNext()!=null)
			aux2.getNext();
		return aux2;
	}

	public void addFirst(E element) {
		LNode<E> node= new LNode<E>(element);
		node.setNext(head);
		head= node;
		size++;
	}

	public void addLast(E element) {
		LNode<E> aux= head;
		LNode<E> node= new LNode<E>(element);
		while(aux.getNext()!=null)
			aux= aux.getNext();
		aux.setNext(node);
		size++;
	}

	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		LNode<E> aux= checkPosition(p);
		LNode<E> aux2= head;
		LNode<E> node= new LNode<E>(element);
		while(aux2!=aux)
			aux2= aux2.getNext();
		node.setNext(aux2.getNext());
		aux2.setNext(node);
		size++;
	}

	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		LNode<E> aux= checkPosition(p);
		LNode<E> aux2= head;
		LNode<E> node= new LNode<E>(element);
		while(aux2.getNext()!=aux)
			aux2= aux2.getNext();
		node.setNext(aux);
		aux2.setNext(node);
		size++;
	}

	public E remove(Position<E> p) throws InvalidPositionException {
		E removed;
		LNode<E> aux= checkPosition(p);
		LNode<E> aux2= head;
		while(aux2.getNext()!=aux)
			aux2.getNext();
		aux2.setNext(aux.getNext());
		removed= aux.element();
		size--;
		return removed;
	}

	public E set(Position<E> p, E element) throws InvalidPositionException {
		E replaced;
		LNode<E> aux= checkPosition(p);
		replaced= aux.element();
		aux.setElement(element);
		return replaced;
	}

	public Iterator<E> iterator() {
		return new ElementIterator(this);
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> iterable= new LinkedList<Position<E>>();
		LNode<E> node= head;
		while(node!=null) {
			iterable.addLast(node);
			node= node.getNext();
		}
		return iterable;
	}
	
	
}
