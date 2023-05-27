package NodesPackage;

public class BSTNode<E extends Comparable<E>> {
	
	private E element;
	private BSTNode<E> parent, left, right;
	
	public BSTNode(E element, BSTNode<E> parent) {
		this.element= element;
		this.parent= parent;
	}

	public E getElement() {
		return element;
	}

	public BSTNode<E> getParent() {
		return parent;
	}

	public BSTNode<E> getLeft() {
		return left;
	}

	public BSTNode<E> getRight() {
		return right;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public void setParent(BSTNode<E> parent) {
		this.parent = parent;
	}

	public void setLeft(BSTNode<E> left) {
		this.left = left;
	}

	public void setRight(BSTNode<E> right) {
		this.right = right;
	}

}
