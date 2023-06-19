package ADTGraph;
import ADTList.*;

public class LVertex<V,E> implements Vertex<V> {

	private Position<LVertex<V,E>> posInVertexList;
	private V element;
	private PositionList<LEdge<V,E>> adyacents;
	
	public LVertex(V element) {
		this.element= element;
		posInVertexList= null;
		adyacents= new DoubleLinkedList<LEdge<V,E>>();
	}

	public Position<LVertex<V, E>> getPosInVertexList() {
		return posInVertexList;
	}

	public V element() {
		return element;
	}

	public PositionList<LEdge<V, E>> getAdyacents() {
		return adyacents;
	}

	public void setPosInVertexList(Position<LVertex<V, E>> posInVertexList) {
		this.posInVertexList = posInVertexList;
	}

	public void setElement(V element) {
		this.element = element;
	}

	public void setAdyacents(PositionList<LEdge<V, E>> adyacents) {
		this.adyacents = adyacents;
	}
	
}
