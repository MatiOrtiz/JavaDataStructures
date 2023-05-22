package ADTBinaryTree;

import java.util.Iterator;

import ADTList.*;
import Exceptions.BoundaryViolationException;
import Exceptions.EmptyTreeException;
import Exceptions.InvalidOperationException;
import Exceptions.InvalidPositionException;
import NodesPackage.BTNode;

public class BTree<E> implements BinaryTree<E> {

	protected BTNode<E> root;
	protected int size;
	
	public BTree() {
		root= null;
		size= 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}
	
	public void preOrder(BTNode<E> node, PositionList<Position<E>> list) {
		list.addLast(node);
		//TODO
	}

	public Iterator<E> iterator() {
		PositionList<E> list= new DoubleLinkedList<E>();
		for(Position<E> p : positions())
			list.addLast(p.element());
		return list.iterator();
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> iterable= new DoubleLinkedList<Position<E>>();
		if(!isEmpty())
			preOrder(root,iterable);
		return iterable;
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createRoot(E r) throws InvalidOperationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		// TODO Auto-generated method stub
		
	}

}
