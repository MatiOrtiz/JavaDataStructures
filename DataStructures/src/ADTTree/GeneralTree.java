package ADTTree;

import java.util.Iterator;
import Exceptions.*;
import ADTList.*;
import NodesPackage.TNode;
import ADTQueue.*;

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
			size= 1;
		}

		public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
			TNode<E> parent= checkPosition(p);
			TNode<E>node= new TNode<E>(e, parent);
			if(isEmpty()) 
	            throw new InvalidPositionException("The tree is empty");
			parent.getChildren().addFirst(node);
			size++;
			return node;
		}

		public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
			TNode<E> parent= checkPosition(p);
			TNode<E>node= new TNode<E>(e, parent);
			if(isEmpty()) 
	            throw new InvalidPositionException("Arbol vacio.");
			parent.getChildren().addLast(node);
			size++;
			return node;
		}
		
		public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
			TNode<E> parent= checkPosition(p);
			TNode<E> brother= checkPosition(rb);
			TNode<E> node= new TNode<E>(e, parent);
			if(isEmpty())
				throw new InvalidPositionException("The tree is empty.");
			if(brother.getParent()!=parent)
				throw new InvalidPositionException("The node p is not the rb's parent");
			try {
				Position<TNode<E>> pos= parent.getChildren().first();
				while(pos!=parent.getChildren().last() && pos.element()!=brother) 
					pos= parent.getChildren().next(pos);
				parent.getChildren().addBefore(pos, node);
				size++;
			}catch(EmptyListException | BoundaryViolationException | InvalidPositionException exc) {exc.getMessage();}
			return node;
		}

		public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
			TNode<E> parent= checkPosition(p);
			TNode<E> brother= checkPosition(lb);
			TNode<E> node= new TNode<E>(e, parent);
			if(isEmpty())
				throw new InvalidPositionException("The tree is empty.");
			if(brother.getParent()!=parent)
				throw new InvalidPositionException("The node p is not the rb's parent");
			try {
				Position<TNode<E>> pos= parent.getChildren().first();
				while(pos!=parent.getChildren().last() && pos.element()!=brother) 
					pos= parent.getChildren().next(pos);
				parent.getChildren().addAfter(pos, node);
				size++;
			} catch(EmptyListException | InvalidPositionException | BoundaryViolationException exc) {exc.getMessage();}
			return node;
		}

		public void removeExternalNode(Position<E> p) throws InvalidPositionException {
			TNode<E> node= checkPosition(p);
			if(isEmpty() || isInternal(node))
				throw new InvalidPositionException("The tree is empty or the node is internal.");
			if(isExternal(p))
				removeNode(p);
		}

		public void removeInternalNode(Position<E> p) throws InvalidPositionException {
			TNode<E> node= checkPosition(p);
			if(isEmpty() || isExternal(node))
				throw new InvalidPositionException("The tree is empty or the node is external.");
			if(isInternal(p))
				removeNode(p);
		}

		public void removeNode(Position<E> p) throws InvalidPositionException {
			if(size==0)
				throw new InvalidPositionException("The tree is empty.");
			TNode<E> node= checkPosition(p);
			try {
				if(node==root) {
					if(node.getChildren().size()==1) {
						Position<TNode<E>> newRoot= node.getChildren().first();
						root= newRoot.element();
						root.setParent(null);
						size--;
					} else if(size==1) {
						root= null;
						size--;
					} else throw new InvalidPositionException("The root has more than one child.");
				} else {
					TNode<E> parent= node.getParent();
					PositionList<TNode<E>> nodesChildren= node.getChildren();
					PositionList<TNode<E>> parentsChildren= parent.getChildren();
					Position<TNode<E>> pos= parentsChildren.first();
					while(pos!=parentsChildren.last() && pos.element()!=node) 
						pos= parentsChildren.next(pos);
					while(!nodesChildren.isEmpty()) {
						Position<TNode<E>> toInsert= nodesChildren.first();
						parentsChildren.addBefore(pos, toInsert.element());
						toInsert.element().setParent(parent);
						nodesChildren.remove(toInsert);
					}
					parentsChildren.remove(pos);
					size--;
				}
				
			} catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {throw new InvalidPositionException("The node cannot remove.");}
		}
		
		public PositionList<Position<E>> recorrer(Tree<E> t) {
			PositionList<Position<E>> list= new DoubleLinkedList<Position<E>>();
			try {
				TNode<E> node= (TNode<E>)t.root();
				preOrder(node, list);
			} catch(EmptyTreeException e) { e.getMessage();}
			return list;
		}
		
		public void levels(Tree<E> t) {
			Queue<TNode<E>> queue= new LinkedQueue<TNode<E>>();
			try {
				TNode<E> node= (TNode<E>) t.root();
				queue.enqueue(node);
				queue.enqueue(null);
				while(!queue.isEmpty()) {
					node= queue.dequeue();
					if(node!=null) {
						System.out.print(node.element() + " ");
						for(TNode<E> n : node.getChildren())
							queue.enqueue(n);
					} else { 
						System.out.println();
						if(!queue.isEmpty())
							queue.enqueue(null);
					}
				}
			} catch(EmptyTreeException | EmptyQueueException e) {e.getMessage();}
		}
		
		public void level(Tree<E> t) {
			Queue<Position<E>> queue= new LinkedQueue<Position<E>>();
			try { 
				Position<E> pos= t.root();
				queue.enqueue(pos);
				queue.enqueue(null);
				while(!queue.isEmpty()) {
					pos= queue.dequeue();
					if(pos!=null) {
						System.out.print(pos.element() + " ");
						for(Position<E> p : t.children(pos))
							queue.enqueue(p);
					} else {
						System.out.println();
						if(!queue.isEmpty())
							queue.enqueue(null);
					}
				}
			}catch(EmptyTreeException | EmptyQueueException | InvalidPositionException e) {e.getMessage();}
		}
		
		//Calcula la profundidad (desde el nodo hacia arriba)
		public int depth(Position<E> p, Tree<E> t) {
			int depth= 0;
			TNode<E> node= (TNode<E>) p;
			try {
				if(t.isRoot(node))
					depth= 0;
				else depth= 1 + depth(t.parent(node),t);
			}catch(InvalidPositionException | BoundaryViolationException e) {e.getMessage();}
			return depth;
		}
		
		//Calcula la altura(desde el nodo hacia abajo)
		public int height(Position<E> p, Tree<E> t) {
			int height= 0;
			int h;
			TNode<E> node= (TNode<E>) p;
			try { 
				if(t.isExternal(node))
					height= 0;
				else {
					h= 0;
					for(TNode<E> n : node.getChildren()) {
						h= Math.max(height(n,t), h);
						height= h+1;
					}
				}
			} catch(InvalidPositionException e) {e.getMessage();}
			return height;
		}
		
		
		//TODO Consultar implementación
		public void levelsInverted(Tree<E> t) {
			PositionList<TNode<E>> list= new DoubleLinkedList<TNode<E>>();
			TNode<E> node= null;
			try {
				node= (TNode<E>)t.root();
				list.addFirst(node);
				list.addLast(null);
				int height= height(node,t);
				for(int i=1; i<height; i++) {
					for(TNode<E> n : node.getChildren()) {
						list.addLast(n);
						t.removeNode(n);
					}
					list.addLast(null);
				}
				while(!list.isEmpty()) {
					node= list.remove(list.last());
					if(node!=null)
						System.out.print(node.element() + " ");
					else System.out.println();
				}
			}catch(EmptyTreeException | EmptyListException | InvalidPositionException e) {e.getMessage();} 
		}
		
		public PositionList<TNode<E>> leftChildren(Tree<E> t) {
			PositionList<TNode<E>> list= new DoubleLinkedList<TNode<E>>();
			try {
				TNode<E> node= (TNode<E>) t.root();
				list.addLast(node);
				while(t.isInternal(node)) {
					TNode<E> leftChild= node.getChildren().first().element();
					if(t.isInternal(leftChild)) 
						list.addLast(leftChild);
					node= leftChild;
				}
			}catch(EmptyTreeException | EmptyListException | InvalidPositionException e) {e.getMessage();}
			return list;
		}
		
		public PositionList<Position<E>> leftChilden(Tree<E> t) {
			PositionList<Position<E>> list= new DoubleLinkedList<Position<E>>();
			try {
				preOrden(t, t.root(), list);
			} catch(EmptyTreeException e) {e.getMessage();}
			return list;
		}
		
		private void preOrden(Tree<E> t, Position<E> p, PositionList<Position<E>> list) {
			try {
				Iterator<Position<E>> it= t.children(p).iterator();
				if(it.hasNext()) 
					if(t.isInternal(it.next())) {
						list.addLast(it.next());
						preOrden(t,it.next(),list);
					}
			} catch(InvalidPositionException e) {e.getMessage();}
		}
		
		public void removeLeftChildren(Tree<E> t) {
			try {
				TNode<E> node= (TNode<E>) t.root();
				while(t.isInternal(node)) {
					TNode<E> leftChild= node.getChildren().first().element();
					if(t.isInternal(leftChild)) 
						t.removeInternalNode(leftChild);
					node= leftChild;
				}
			} catch(EmptyTreeException | InvalidPositionException | EmptyListException e) {e.getMessage();}
		}
		
		public void removeExternalChildren(Tree<E> t) {
			try {
				TNode<E> node= (TNode<E>) t.root();
				PositionList<TNode<E>> list= new DoubleLinkedList<TNode<E>>();
				list.addFirst(node);
				while(!list.isEmpty()) {
					for(TNode<E> n : list) {
						if(t.isExternal(n))
							t.removeExternalNode(n);
						else {
							for(TNode<E> m : n.getChildren()) {
								list.addLast(m);
							}
						}
						list.remove(list.first());
					}
				}
			} catch(EmptyTreeException | EmptyListException | InvalidPositionException e) {e.getMessage();}
		}
		
		public void removeNodeR(E r) {
			Iterable<Position<E>> iterable= this.positions();
			try {
				for(Position<E> p : iterable) {
					if(p.element().equals(r))
						removeNode(p);
				}
			} catch(InvalidPositionException e) {e.getMessage();}
		}
		
		
		//TODO implement without use firstCommonAncestor 
		public boolean existsRoad(TNode<E> n1, TNode<E> n2) {
			boolean aux= true;
			try {
				aux= firstCommonAncestor(n1,n2, this)!=null;
			}catch(InvalidPositionException e) {e.getMessage();}
			return aux;
		}
		
		//TODO check
		public PositionList<TNode<E>> road(TNode<E> n1, TNode<E> n2) {
			PositionList<TNode<E>> list= new DoubleLinkedList<TNode<E>>();
			PositionList<TNode<E>> listAux= new DoubleLinkedList<TNode<E>>();
			try {
				while(!n1.equals(n2) && !isRoot(n1) && !isRoot(n2)) {
					list.addLast(n1);
					listAux.addLast(n2);
					n1= n1.getParent();				
					n2= n2.getParent();
				}
				if(n1.equals(n2)) {
					listAux.remove(listAux.first());
					while(!listAux.isEmpty())
						list.addLast(listAux.remove(listAux.first()));
				}else{ 
					if(isRoot(n1))
						while(!isRoot(n2)) {
							listAux.addLast(n2);
					 		n2= n2.getParent();
						}
					else while(!isRoot(n1)) {
						list.addLast(n1);
						n1= n1.getParent();
					}
					if(n1.equals(n2)) {
						listAux.remove(listAux.first());
						while(!listAux.isEmpty())
							list.addLast(listAux.remove(listAux.first()));
					}
				}
				if(!n1.equals(n2))
					throw new InvalidPositionException("Any position is invalid.");
			} catch(InvalidPositionException | EmptyListException e) {e.getMessage();}
			return list;
		}
		
		public int heightR(E r, Tree<E> t) throws InvalidPositionException {
			Iterable<Position<E>> iterable= t.positions();
			TNode<E> node= null;
			for(Position<E> p : iterable) {
				if(p.element().equals(r))
					node= (TNode<E>) p;
			}
			if(node.element()==null)
				throw new InvalidPositionException("The node doesn't exists in the tree.");
			return height(node,t);
		}
		
		public void addRightChild(int a, E r) {
			Iterable<Position<E>> iterable= positions();
			for(Position<E> p : iterable) {
				TNode<E> n= (TNode<E>) p;
				if(height(n,this)==a)
					n.getChildren().addLast(new TNode<E>(r,n));
			}
		}
		
		private void invertList(PositionList<TNode<E>> p) {
			try {
				TNode<E> head= p.first().element();
				while(!p.last().element().equals(head)) {
					p.addFirst(p.last().element());
					p.remove(p.last());
				}
			}catch(EmptyListException | InvalidPositionException e) {e.getMessage();}
		}
		
		public void invertChildren(E r) {
			Iterable<Position<E>> iterable= positions();
			try {
				for(Position<E> p : iterable) {
					TNode<E> n= (TNode<E>) p;
					if(isInternal(n) && n.element().equals(r)) {
						invertList(n.getChildren());
					}
				}
			}catch(InvalidPositionException e){e.getMessage();}
		}
		
		public TNode<E> firstCommonAncestor(Position<E> p1, Position<E> p2, Tree<E> t) throws InvalidPositionException {
			TNode<E> node= null;
			try {
				TNode<E> n1= checkPosition(p1).getParent();
				TNode<E> n2= checkPosition(p2).getParent();
				while(!n1.equals(n2) && !t.isRoot(n1) && !t.isRoot(n2)) {
					n1= n1.getParent();
					n2= n2.getParent();
				}
				if(n1.equals(n2))
					node= n1;
				else{ 
					if(t.isRoot(n1))
						while(!t.isRoot(n2) && n2.getParent()!=null)
					 		n2= n2.getParent();
					else while(!t.isRoot(n1) && n1.getParent()!=null)
						n1= n1.getParent();
					if(n1.equals(n2))
						node= n1;
				}
				if(node==null)
					throw new InvalidPositionException("Any position is invalid.");
			} catch(InvalidPositionException e) {e.getMessage();}
			return node;
		}
		
}
