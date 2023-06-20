package ADTGraph;

import ADTList.*;

public class MVertex<V> implements Vertex<V> {

	private Position<Vertex<V>> posInVertices;
	private V element;
	private int indice;
	
	public MVertex(V element, int indice) {
		this.element= element;
		this.indice= indice;
		posInVertices= null;
	}

	public Position<Vertex<V>> getPosInVertices() {
		return posInVertices;
	}

	public V element() {
		return element;
	}

	public int getIndice() {
		return indice;
	}

	public void setPosInVertices(Position<Vertex<V>> posInVertices) {
		this.posInVertices = posInVertices;
	}

	public void setElement(V element) {
		this.element = element;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}
	
}
