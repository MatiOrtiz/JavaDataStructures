package ADTBinaryTree;

import java.util.Comparator;
import NodesPackage.BSTNode;

public class BSTree<E extends Comparable<E>> {
	
	protected BSTNode<E> root;
	protected Comparator<E> comp;
	
	public BSTree(E e, Comparator<E> comp) {
		root= new BSTNode<E>(e, null);
		this.comp= comp;
	}
	
	public BSTNode<E> search(E x) {
		return searchRec(x, root);
	}
	
	private BSTNode<E> searchRec(E x, BSTNode<E> n) {
		BSTNode<E> node= null;
		if(n.getElement()==null)
			node= null;
		else {
			int c= comp.compare(x,n.getElement());
			if(c==0)
				node= n;
			else if(c<0)
				node= searchRec(x,n.getLeft());
			else node= searchRec(x,n.getRight());
		}
		return node;
	}
	
	/**
	 * Creates two dummies for the node n 
	 * @param n node that will has the nodes
	 */
	public void extend(BSTNode<E> n) {
		n.setLeft(new BSTNode<E>(null,n));
		n.setRight(new BSTNode<E>(null,n));
	}
	
	//TODO
	public void remove(E x) {
		
	}
	
	public BSTNode<E> getRoot() {
		return root;
	}
	
}
