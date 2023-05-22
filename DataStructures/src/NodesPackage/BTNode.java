package NodesPackage;

import ADTBinaryTree.BTPosition;

public class BTNode<E> implements BTPosition<E> {
	protected E element;
	protected BTNode<E> parent;
	protected BTNode<E> left, right;
	
	public BTNode(E element, BTNode<E> parent) {
		this.element= element;
		this.parent= parent;
		left= null;
		right= null;
	}

	public E element() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public BTNode<E> parent() {
		return parent;
	}

	public void setParent(BTNode<E> parent) {
		this.parent = parent;
	}

	public BTNode<E> left() {
		return left;
	}

	public void setLeft(BTNode<E> left) {
		this.left = left;
	}

	public BTNode<E> right() {
		return right;
	}

	public void setRight(BTNode<E> right) {
		this.right = right;
	}
	
}
