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
	
	public BinaryTree<E> clone() {
		BinaryTree<E> t= new BTree<E>();
		try {
			if(!isEmpty()) {
				BTNode<E> node= (BTNode<E>) t.createRoot(root.element());
				cloneRec(t, root, node);
			}
		}catch(InvalidOperationException e) {e.getMessage();}
		return t;
	}
	
	public void cloneRec(BinaryTree<E> t, BTNode<E> n, BTNode<E> rootT) {
		BTNode<E> node;
		try {
			if(hasLeft(n)) {
				node= (BTNode<E>) t.addLeft(rootT,n.left().element());
				cloneRec(t, (BTNode<E>)n.left(), node);
			}
			if(hasRight(n)) {
				node= (BTNode<E>) t.addRight(rootT, n.right().element());
				cloneRec(t,(BTNode<E>)n.right(), node);
			}
		}catch(InvalidOperationException | InvalidPositionException e) {e.getMessage();}
	}
	
	public BinaryTree<E> mirror(BTree<E> t) {
		BinaryTree<E> bt= t.clone();
		try {
			BTNode<E> rootBt= (BTNode<E>) bt.root();
			invert(rootBt);
		} catch(EmptyTreeException e) {e.getMessage();}
		return bt;
	}
	
	private void invert(BTNode<E> n) {
		BTNode<E> aux= n.left();
		n.setLeft(n.right());
		n.setRight(aux);
		
		if(n.left()!=null)
			invert(n.left());
		if(n.right()!=null)
			invert(n.right());
	}
	
	public int depth(Position<E> p, BinaryTree<E> t) {
		int cont= 0;
		try {
			if(t.isRoot(p))
				cont+=0;
			else {
				BTNode<E> node= checkPosition(p);
				cont+= 1 + depth(node.parent(), t);
			}
		} catch(InvalidPositionException e) {e.getMessage();}
		return cont;
	}
	
	public int height(Position<E> p, BinaryTree<E> t) {
		int cont= 0;
		try {
			if(t.isExternal(p))
				cont+=0;
			else {
				BTNode<E> n= checkPosition(p);
				if(n.left()!=null)
					cont+= 1 + height(n.left(), t);
				else cont+= 1 + height(n.right(), t);
			}
		}catch(InvalidPositionException e) {e.getMessage();}
		return cont;
	}
	
	public boolean isOwn(BinaryTree<E> t) {
		boolean aux= true;
		try {
			if(t.size()==1)
				aux= true;
			else {
				Iterator<Position<E>> it= t.positions().iterator();
				while(it.hasNext() && aux==true) {
					if((t.hasLeft(it.next()) && !t.hasRight(it.next())) || (!t.hasLeft(it.next()) && t.hasRight(it.next())))
						aux= false;
				}
			}
		} catch(InvalidPositionException e) {e.getMessage();}
		return aux;
	}
	
	public boolean subTree(BinaryTree<E> t, BinaryTree<E> t1) {
		boolean aux= true;
		if(t1.size()>t.size())
			aux= false;
		else try {
				BTNode<E> n1= (BTNode<E>) t1.root();
				Iterator<Position<E>> it= t.positions().iterator();
				while(it.hasNext() && it.next()!=n1) 
					it.next();
				if(n1==it.next())
					aux= t1.children(n1)==t.children(it.next());
				else aux= false;
		} catch(EmptyTreeException | InvalidPositionException e) {e.getMessage();}
		return aux;
	}
	
	public boolean equals(BinaryTree<E> t1, BinaryTree<E> t2) {
		boolean aux= true;
		if(t1.size()!= t2.size())
			aux= false;
		else {
			Iterator<Position<E>> it1= t1.positions().iterator();
			Iterator<Position<E>> it2= t2.positions().iterator();
			while(it1.hasNext() && aux==true) {
				BTNode<E>n1= (BTNode<E>) it1.next();
				BTNode<E>n2= (BTNode<E>) it2.next();
				if(!n1.equals(n2))
					aux= false;
			}
		}
		return aux;
	}
	
	//TODO how to compare the elements of the list's positions with the arithmetical signs. 
	public void arithmeticExpression(BinaryTree<E> t) {
		PositionList<Position<E>> list= new DoubleLinkedList<Position<E>>();
		try {
			preOrder((BTNode<E>)t.root(), list);
		} catch(EmptyTreeException e) {e.getMessage();}
		for(Position<E> p : list) {
			
		}
	}
	
	
}