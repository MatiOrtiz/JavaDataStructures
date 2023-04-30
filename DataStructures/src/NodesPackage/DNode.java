package NodesPackage;
import ADTList.Position;

public class DNode<E> implements Position<E> {

	protected DNode<E> prev, next;
	protected E element;
	
	public DNode(E element) {
		this.element= element;
		prev= null;
		next= null;
	}
	
	public void setElement(E e) {
		element= e;
	}
	
	public void setNext(DNode<E>next) {
		this.next= next;
	}
	
	public void setPrev(DNode<E> prev) {
		this.prev= prev;
	}
	
	public E element() {
		return element;
	}
	
	public DNode<E> getNext() {
		return next;
	}
	
	public DNode<E> getPrev() {
		return prev;
	}
	
}
