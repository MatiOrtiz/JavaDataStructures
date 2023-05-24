package ADTBinaryTree;

import java.util.Iterator;

import ADTList.*;
import Exceptions.BoundaryViolationException;
import Exceptions.EmptyTreeException;
import Exceptions.InvalidOperationException;
import Exceptions.InvalidPositionException;
import NodesPackage.BTNode;

public class BTree<E> implements BinaryTree<E> {

	BTNode<E> root;
	int size; 

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}
	
	private void preOrder(BTNode<E> n, PositionList<Position<E>> list) {
		list.addLast(n);
		try {
			if(hasLeft(n))
				preOrder(n.left(), list);
			if(hasRight(n))
				preOrder(n.right(), list);
		}catch(InvalidPositionException e) { e.getMessage();}
	}

	public Iterator<E> iterator() {
		PositionList<E> list= new DoubleLinkedList<E>();
		for(Position<E> p : positions())
			list.addLast(p.element());
		return list.iterator();
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> iterable= new DoubleLinkedList<Position<E>>();
		if(size!=0)
			preOrder(root, iterable);
		return iterable;
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTNode<E> node= checkPosition(v);
		E replaced= node.element();
		node.setElement(e);
		return replaced;
	}

	public Position<E> root() throws EmptyTreeException {
		if(size==0)
			throw new EmptyTreeException("The tree is empty.");
		return root;
	}

	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNode<E> node= checkPosition(v);
		if(isEmpty())
			throw new InvalidPositionException("Tree empty.");
		if(node==root)
			throw new BoundaryViolationException("The position is the root.");
		return node.parent();
	}

	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		BTNode<E> node= checkPosition(v);
		PositionList<Position<E>> iterable= new DoubleLinkedList<Position<E>>();
		iterable.addLast(node.left());
		iterable.addLast(node.right());
		return iterable;
	}

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		BTNode<E>node= checkPosition(v);
		return node.left()!=null && node.right()!=null;
	}

	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		BTNode<E>node= checkPosition(v);
		return node.left()==null && node.right()==null;
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTNode<E>node= checkPosition(v);
		return node==root;
	}

	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNode<E> node= checkPosition(v);
		return node.left();
	}

	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNode<E> node= checkPosition(v);
		return node.right();
	}

	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTNode<E> node= checkPosition(v);
		return node.left()!=null;
	}

	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTNode<E> node= checkPosition(v);
		return node.right()!=null;
	}

	public Position<E> createRoot(E r) throws InvalidOperationException {
		if(root!=null)
			throw new InvalidOperationException("The operation is invalid.");
		root= new BTNode<E>(r,null);
		return root;
	}

	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTNode<E> node= checkPosition(v);
		if(node.left()!=null)
			throw new InvalidOperationException("The operation is invalid.");
		BTNode<E> newNode= new BTNode<E>(r, node);
		node.setLeft(newNode);
		size++;
		return newNode;
	}

	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTNode<E> node= checkPosition(v);
		if(node.right()!=null)
			throw new InvalidOperationException("The operation is invalid.");
		BTNode<E> newNode= new BTNode<E>(r, node);
		node.setRight(newNode);
		size++;
		return newNode;
	}

	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		BTNode<E> node= checkPosition(v);
		BTNode<E> child= null;
		BTNode<E> parent= node.parent();
		if(isEmpty())
			throw new InvalidPositionException("Tree empty.");
		if(node.left()!=null && node.right()!=null)
			throw new InvalidOperationException("The node has more than a child");
		if(node.left()!=null)
			child= node.left();
		else if(node.right()!=null)
			child= node.right();
		E removed= node.element();
		if(parent.left()!=null)
			parent.setLeft(child);
		else parent.setRight(child);
		child.setParent(parent);
		size--;
		return removed;
	}

	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		BTNode<E> node= checkPosition(r);
		if(isInternal(r))
			throw new InvalidPositionException("The node has any child.");
		try {
			BTNode<E> n1= checkPosition(T1.root());
			BTNode<E> n2= checkPosition(T2.root());
			node.setLeft(n1);
			node.setRight(n2);
			n1.setParent(node);
			n2.setParent(node);
			size+=T1.size() + T2.size();
		} catch(EmptyTreeException e) {e.getMessage();}
	}
	
	private BTNode<E> checkPosition(Position<E> p) throws InvalidPositionException {
		if(p==null || p.element()==null)
			throw new InvalidPositionException("The position is invalid.");
		return (BTNode<E>) p;
	}
	
}