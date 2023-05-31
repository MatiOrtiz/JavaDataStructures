package ADTMap;

import java.util.Comparator;
import ADTList.*;
import Exceptions.InvalidKeyException;
import NodesPackage.BSTNode;

public class BSTMap<K,V> implements Map<K,V> {

	private int size;
	private Comparator<K> comp;
	private BSTNode<Entry<K,V>> root;
	
	public BSTMap(Comparator<K> comp) {
		root= new BSTNode<Entry<K,V>>(null,null);
		this.comp= comp;
		size= 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}

	private void checkKey(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("The key is null.");
	}
	
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		BSTNode<Entry<K,V>> node= search(key);
		V value= null;
		if(node!=null)
			value= node.getElement().getValue();
		return value;
	}
	
	private BSTNode<Entry<K,V>> search(K key) {
		return searchRec(key, root);
	}
	
	private BSTNode<Entry<K,V>> searchRec(K key, BSTNode<Entry<K,V>> n) {
		BSTNode<Entry<K,V>> node;
		if(n.getElement()==null)
			node= null;
		else {
			int c= comp.compare(key, n.getElement().getKey());
			if(c==0)
				node= n;
			else { 
				if(c>0)
					node= searchRec(key,n.getRight());
				else node= searchRec(key, n.getLeft());
			}
		}
		return node;
	}

	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entry<K,V> entry= new Entrance<K,V>(key,value);
		BSTNode<Entry<K,V>> node= search(key);
		V replaced= null;
		if(node.getElement()!=null) {
			replaced= node.getElement().getValue();
			node.setElement(entry);
		} else {
			node.setElement(entry);
			node.setLeft(new BSTNode<Entry<K,V>>(null,node));
			node.setRight(new BSTNode<Entry<K,V>>(null,node));
			size++;
		}
		return replaced;
	}
	
	private void removeAux(BSTNode<Entry<K,V>> n) {
		if(n==root) {
			n.setElement(null);
			n.setLeft(null);
			n.setRight(null);
		} else {
			if(n.getLeft()!=null && n.getRight()==null) {
				if(n.getParent().getLeft()==n)
					n.getParent().setLeft(n.getLeft());
				else n.getParent().setRight(n.getLeft());
				n.getLeft().setParent(n.getParent());
			} else
				if(n.getLeft()==null && n.getRight()!=null) {
					if(n.getParent().getLeft()==n)
						n.getParent().setLeft(n.getRight());
					else n.getParent().setRight(n.getRight());
					n.getRight().setParent(n.getParent());
				}
		}
		size--;
	}

	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		BSTNode<Entry<K,V>> node= search(key);
		V removed= null;
		if(node.getElement()!=null) {
			removed= node.getElement().getValue();
			removeAux(node);
		}
		return removed;
	}
	
	private void preOrder(BSTNode<Entry<K,V>> n, PositionList<Entry<K,V>> list) {
		list.addLast(n.getElement());
		if(n.getLeft()!=null)
			preOrder(n.getLeft(), list);
		if(n.getRight()!=null)
			preOrder(n.getRight(), list);
	}

	public Iterable<K> keys() {
		PositionList<Entry<K,V>> list= new DoubleLinkedList<Entry<K,V>>();
		PositionList<K> iterable= new DoubleLinkedList<K>();
		preOrder(root,list);
		for(Entry<K,V> e : list)
			iterable.addLast(e.getKey());
		return iterable;
	}

	public Iterable<V> values() {
		PositionList<Entry<K,V>> list= new DoubleLinkedList<Entry<K,V>>();
		PositionList<V> iterable= new DoubleLinkedList<V>();
		preOrder(root,list);
		for(Entry<K,V> e : list)
			iterable.addLast(e.getValue());
		return iterable;
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> list= new DoubleLinkedList<Entry<K,V>>();
		preOrder(root,list);
		return list;
	}

}
