package NodesPackage;

import ADTList.Position;
import ADTList.*;

public class TNode<E> implements Position<E> {

	protected E element;
	protected TNode<E> parent;
	protected PositionList<TNode<E>> children;
	
	public TNode(E element,TNode<E> parent) {
		this.element= element;
		this.parent= parent;
		children= new DoubleLinkedList<TNode<E>>();
	}

	public E element() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public TNode<E> getParent() {
		return parent;
	}

	public void setParent(TNode<E> parent) {
		this.parent = parent;
	}

	public PositionList<TNode<E>> getChildren() {
		return children;
	}

	public void setChildren(PositionList<TNode<E>> children) {
		this.children = children;
	}
	
}
