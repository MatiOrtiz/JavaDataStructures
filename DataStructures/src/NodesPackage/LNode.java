package NodesPackage;
import ADTList.Position;

public class LNode<E> implements Position<E> {

	protected E element;
	protected LNode<E> next;
	
	public LNode(E element, LNode<E> next) {
		this.element= element;
		this.next= next;
	}
	
	public LNode(E element) {
		this(element, null);
	}
	
	public void setElement(E element) {
		this.element= element;
	}
	
	public void setNext(LNode<E> next) {
		this.next= next;
	}
	
	public LNode<E> getNext() {
		return next;
	}
	public E element() {
		return element;
	}
	
}
