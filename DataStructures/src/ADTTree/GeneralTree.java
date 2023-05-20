package ADTTree;

import java.util.Iterator;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.EmptyTreeException;
import Exceptions.InvalidOperationException;
import Exceptions.InvalidPositionException;
import ADTList.*;
import NodesPackage.TNode;

public class GeneralTree<E> implements Tree<E> {

		protected TNode<E> root;
		protected int size;
		
		public GeneralTree() {
			root= null;
			size= 0;
		}

		public int size() {
			return size;
		}

		public boolean isEmpty() {
			return size==0;
		}
		
		private void preOrder(TNode<E> node, PositionList<Position<E>> list) {
			list.addLast(node);
			for(TNode<E> n : node.getChildren()) 
				preOrder(n, list);
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
				preOrder(root, iterable);
			return iterable;
		}
		
		private TNode<E> checkPosition(Position<E> p) throws InvalidPositionException {
			try {
				if(p==null)
					throw new InvalidPositionException("The position is null.");
				if(p.element()==null)
					throw new InvalidPositionException("The position has already removed.");
			} catch(ClassCastException e) { throw new InvalidPositionException("The node doesn´t exists in the tree.");}
			return (TNode<E>) p;
		}

		public E replace(Position<E> v, E e) throws InvalidPositionException {
			TNode<E> node= checkPosition(v);
			E replaced= node.element();
			node.setElement(e);
			return replaced;
		}

		public Position<E> root() throws EmptyTreeException {
			if(root==null)
				throw new EmptyTreeException("The root is null.");
			return root;
		}

		public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
			TNode<E> node= checkPosition(v);
			if(node==root)
				throw new BoundaryViolationException("The node is root.");
			return node.getParent();
		}

		public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
			TNode<E> node= checkPosition(v);
			PositionList<Position<E>> iterable= new DoubleLinkedList<Position<E>>();
			if(isInternal(node))
				for(TNode<E> n : node.getChildren())
					iterable.addLast(n);
			return iterable;
		}

		public boolean isInternal(Position<E> v) throws InvalidPositionException {
			TNode<E> node= checkPosition(v);
			return !node.getChildren().isEmpty();
		}

		public boolean isExternal(Position<E> v) throws InvalidPositionException {
			TNode<E> node= checkPosition(v);
			return node.getChildren().isEmpty();
		}

		public boolean isRoot(Position<E> v) throws InvalidPositionException {
			TNode<E> node= checkPosition(v);
			return node==root;
		}

		public void createRoot(E e) throws InvalidOperationException {
			if(root!=null)
				throw new InvalidOperationException("The tree already has root");
			root= new TNode<E>(e, null);
		}

		public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
			TNode<E> parent= checkPosition(p);
			TNode<E>node= new TNode<E>(e, parent);
			parent.getChildren().addFirst(node);
			size++;
			return node;
		}

		public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
			TNode<E> parent= checkPosition(p);
			TNode<E>node= new TNode<E>(e, parent);
			parent.getChildren().addLast(node);
			size++;
			return node;
		}

		public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
			TNode<E> parent= checkPosition(p);
			TNode<E> brother= checkPosition(rb);
			TNode<E> node= new TNode<E>(e, parent);
			Iterator<Position<TNode<E>>> it= parent.getChildren().positions().iterator();
			while(it.hasNext() && !it.next().element().equals(brother)) 
				it.next();
			parent.getChildren().addBefore(it.next(), node);
			size++;
			return node;
		}

		public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
			TNode<E> parent= checkPosition(p);
			TNode<E> brother= checkPosition(lb);
			TNode<E> node= new TNode<E>(e, parent);
			Iterator<Position<TNode<E>>> it= parent.getChildren().positions().iterator();
			while(it.hasNext() && !it.next().element().equals(brother)) 
				it.next();
			parent.getChildren().addAfter(it.next(), node);
			size++;
			return node;
		}

		public void removeExternalNode(Position<E> p) throws InvalidPositionException {
			TNode<E> node= checkPosition(p);
			if(isEmpty() || isInternal(node))
				throw new InvalidPositionException("The tree is empty or the node is internal.");
			if(node==root) {
				root= null;
				size= 0;
			} else {
				Iterator<Position<TNode<E>>> it= root.getChildren().positions().iterator();
				TNode<E> parent= node.getParent();
				while(it.hasNext() && !it.next().element().equals(node))
					it.next();
				parent.getChildren().remove(it.next());
				size--;
			}
		}

		public void removeInternalNode(Position<E> p) throws InvalidPositionException {
			TNode<E> node= checkPosition(p);
			if(isEmpty() || isExternal(node))
				throw new InvalidPositionException("The tree is empty or the node is external.");
			try {
				if(node==root) { 
					if(node.getChildren().size()>1) 
						throw new InvalidPositionException("The tree has more than one child.");
					root= root.getChildren().first().element();
					root.getChildren().remove(root.getChildren().first());
					size--;
				} else {
					TNode<E> parent= node.getParent();
					Iterator<Position<TNode<E>>> it= parent.getChildren().positions().iterator();
					
					while(it.hasNext() && !it.next().element().equals(node))
						it.next();
					if(!it.hasNext() && !it.next().element().equals(node))
						throw new InvalidPositionException("The node doesn't exists in the tree");
					
					for(TNode<E> n : node.getChildren()) {
						n.setParent(parent);
						parent.getChildren().addBefore(it.next(), n);
					}
					parent.getChildren().remove(it.next());
					size--;
				}
			}catch(EmptyListException e) { e.getMessage();}
			
		}

		public void removeNode(Position<E> p) throws InvalidPositionException {
			TNode<E> node= checkPosition(p);
			if(isExternal(node))
				removeExternalNode(node);
			else removeInternalNode(node);
		}

	}