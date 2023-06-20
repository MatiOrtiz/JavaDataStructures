package ADTGraph;

import ADTList.*;

public class MEdge<V,E> implements Edge<E> {

	private Position<Edge<E>> posInEdges;
	private MVertex<V> v1, v2;
	private E element;
	
	public MEdge(E element, MVertex<V> v1, MVertex<V> v2) {
		this.v1= v1;
		this.v2= v2;
		this.element= element;
		posInEdges= null;
	}

	public Position<Edge<E>> getPosInEdges() {
		return posInEdges;
	}

	public MVertex<V> getV1() {
		return v1;
	}

	public MVertex<V> getV2() {
		return v2;
	}

	public E element() {
		return element;
	}

	public void setPosInEdges(Position<Edge<E>> posInEdges) {
		this.posInEdges = posInEdges;
	}

	public void setV1(MVertex<V> v1) {
		this.v1 = v1;
	}

	public void setV2(MVertex<V> v2) {
		this.v2 = v2;
	}

	public void setElement(E element) {
		this.element = element;
	}
	
	
}
