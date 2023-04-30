package ADTList;

import java.util.Iterator;
import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;
import NodesPackage.DNode;

public class DoubleLinkedList<E> implements PositionList<E> {

	protected DNode<E> header, trailer;
	protected int size;
	
	public DoubleLinkedList() {
		header= new DNode<E>(null);
		trailer= new DNode<E>(null);
		header.setPrev(null);
		header.setNext(trailer);
		trailer.setNext(null);
		trailer.setPrev(header);
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
		return header.getNext();
	}

	public Position<E> last() throws EmptyListException {
		if(size==0)
			throw new EmptyListException("The list is empty.");
		return trailer.getPrev();
	}

	private DNode<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try { 
			if(p==null)
				throw new InvalidPositionException("The position is null.");
			if(p.element()==null)
				throw new InvalidPositionException("The position has been removed.");
		} catch(ClassCastException e) { throw new InvalidPositionException("The position is not a list node.");}
		return (DNode<E>) p;
	}

	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> node= checkPosition(p);
		if(node==trailer.getPrev())
			throw new BoundaryViolationException("The position is the last in the list.");
		return node.getNext();
	}

	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> node= checkPosition(p);
		if(node==header.getNext())
			throw new BoundaryViolationException("The position is the first in the list.");
		return node.getPrev();
	}

	public void addFirst(E element) {
		DNode<E> node= new DNode<E>(element, header, header.getNext());
		header.getNext().setPrev(node);
		header.setNext(node);
		size++;
	}

	public void addLast(E element) {
		if(isEmpty())
			addFirst(element);
		else {
			DNode<E> node= new DNode<E>(element, trailer.getPrev(), trailer);
			trailer.getPrev().setNext(node);
			trailer.setPrev(node);
			size++;
		}
	}

	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> nodeAux= checkPosition(p);
		DNode<E> node= new DNode<E>(element, nodeAux, nodeAux.getNext());
		nodeAux.getNext().setPrev(node);
		nodeAux.setNext(node);
		size++;
	}

	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> nodeAux= checkPosition(p);
		DNode<E> node= new DNode<E>(element, nodeAux.getPrev(), nodeAux);
		nodeAux.getPrev().setNext(node);
		nodeAux.setPrev(node);
		size++;
	}

	public E remove(Position<E> p) throws InvalidPositionException {
		DNode<E> node= checkPosition(p);
		E removed= node.element();
		node.getPrev().setNext(node.getNext());
		node.getNext().setPrev(node.getPrev());
		node.setElement(null);
		node.setNext(null);
		node.setPrev(null);
		size--;
		return removed;
	}

	public E set(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> node= checkPosition(p);
		E replaced= node.element();
		node.setElement(element);
		return replaced;
	}

	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> iterable= new DoubleLinkedList<Position<E>>();
		DNode<E> node= header.getNext();
		while(node!=trailer) {
			iterable.addLast(node);
			node= node.getNext();
		}
		return iterable;
	}

}
