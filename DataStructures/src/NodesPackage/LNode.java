package NodesPackage;
import ADTList.Position;

public class LNode<E> implements Position<E> {

	protected E element;
	protected LNode<E> node;
	
	public LNode(E element, LNode<E> node) {
		this.element= element;
		this.node= node;
	}
}
