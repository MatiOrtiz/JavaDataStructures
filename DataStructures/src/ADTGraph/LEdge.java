package ADTGraph;

import ADTList.Position;

public class LEdge<V,E> implements Edge<E> {

	private Position<LEdge<V,E>> posInEdgesList;
	private LVertex<V,E> v1, v2;
	private E element;
	private Position<LEdge<V,E>> posEnlv1, posEnlv2;
	
	public LEdge(E element, LVertex<V,E> v1, LVertex<V,E> v2) {
		this.element= element;
		this.v1= v1;
		this.v2= v2;
		posInEdgesList= null;
		posEnlv1= null;
		posEnlv2= null;
	}

	public Position<LEdge<V, E>> getPosInEdgesList() {
		return posInEdgesList;
	}

	public LVertex<V, E> getV1() {
		return v1;
	}

	public LVertex<V, E> getV2() {
		return v2;
	}

	public E element() {
		return element;
	}

	public Position<LEdge<V, E>> getPosEnlv1() {
		return posEnlv1;
	}

	public Position<LEdge<V, E>> getPosEnlv2() {
		return posEnlv2;
	}

	public void setPosInEdgesList(Position<LEdge<V, E>> posInEdgesList) {
		this.posInEdgesList = posInEdgesList;
	}

	public void setV1(LVertex<V, E> v1) {
		this.v1 = v1;
	}

	public void setV2(LVertex<V, E> v2) {
		this.v2 = v2;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public void setPosEnlv1(Position<LEdge<V, E>> posEnlv1) {
		this.posEnlv1 = posEnlv1;
	}

	public void setPosEnlv2(Position<LEdge<V, E>> posEnlv2) {
		this.posEnlv2 = posEnlv2;
	}
	
	
	
}
