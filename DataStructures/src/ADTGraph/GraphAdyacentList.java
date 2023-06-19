package ADTGraph;

import ADTList.*;
import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;

public class GraphAdyacentList<V,E> implements Graph<V,E> {

	private PositionList<LVertex<V,E>> nodes;
	private PositionList<LEdge<V,E>> edges;
	
	public GraphAdyacentList() {
		nodes= new DoubleLinkedList<LVertex<V,E>>();
		edges= new DoubleLinkedList<LEdge<V,E>>();
	}

	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> iterable= new DoubleLinkedList<Vertex<V>>();
		for(Vertex<V> v : nodes)
			iterable.addLast(v);
		return iterable;
	}

	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> iterable= new DoubleLinkedList<Edge<E>>();
		for(Edge<E> e : edges)
			iterable.addLast(e);
		return iterable;
	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		if(v==null)
			throw new InvalidVertexException("The vertex is null.");
		PositionList<Edge<E>> iterable= new DoubleLinkedList<Edge<E>>();
		LVertex<V,E> vertex= (LVertex<V, E>) v;
		for(Edge<E> e : vertex.getAdyacents())
			iterable.addLast(e);
		return iterable;
	}

	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		if(v==null)
			throw new InvalidVertexException("The vertex is null.");
		if(e==null)
			throw new InvalidEdgeException("The edge is null.");
		LEdge<V,E> edge= (LEdge<V, E>) e;
		if(edge.getV1()==null || edge.getV2()==null)
			throw new InvalidEdgeException("The edge is not connected.");
		Vertex<V> vertex= null;
		if(edge.getV1()==v)
			vertex= edge.getV2();
		else if(edge.getV2()==v)
			vertex= edge.getV1();
		return vertex;
	}

	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		if(e==null)
			throw new InvalidEdgeException("The edge is null.");
		LEdge<V,E> edge= (LEdge<V, E>) e;
		Vertex<V>[] array= new Vertex[2];
		array[0]= edge.getV1();
		array[1]= edge.getV2();
		return array;
	}

	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		if(v==null || w==null)
			throw new InvalidVertexException("The vertex is null.");
		boolean adyacents= false;
		LVertex<V,E> v1= (LVertex<V, E>) v;
		LVertex<V,E> v2= (LVertex<V, E>) w;
		try {
			for(LEdge<V,E> e : v2.getAdyacents()) {
				if(adyacents==false) {
					Position<LEdge<V,E>> posV1= v1.getAdyacents().first();
					while(posV1.element()!=e.element() && adyacents==false) 
						if(posV1!=v1.getAdyacents().last())
							posV1= v1.getAdyacents().next(posV1);
					if(posV1.element()==e.element())
						adyacents= true;
				} 
				break;
			}
		} catch(EmptyListException | InvalidPositionException | BoundaryViolationException exc) {exc.getMessage();}
		return adyacents;
	}

	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		if(v==null)
			throw new InvalidVertexException("The vertex is null.");
		V replaced= v.element();
		LVertex<V,E> vertex= (LVertex<V, E>) v;
		vertex.setElement(x);
		return replaced;
	}

	public Vertex<V> insertVertex(V x) {
		LVertex<V,E> vertex= new LVertex<V,E>(x);
		nodes.addLast(vertex);
		try {
			vertex.setPosInVertexList(nodes.last());
		} catch(EmptyListException e) {e.getMessage();}
		return vertex;
	}

	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		if(v==null || w==null)
			throw new InvalidVertexException("The vertex is null.");
		LVertex<V,E> v1= (LVertex<V, E>) v;
		LVertex<V,E> v2= (LVertex<V, E>) w;
		LEdge<V,E> edge= new LEdge<V,E>(e,v1,v2);
		edges.addLast(edge);
		try {
			edge.setPosInEdgesList(edges.last());
		} catch(EmptyListException exc) {exc.getMessage();}
		return edge;
	}

	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		if(v==null)
			throw new InvalidVertexException("The vertex is null.");
		V removed= null;
		LVertex<V,E> vertex= (LVertex<V, E>) v;
		try {
			for(Position<LEdge<V,E>> e : vertex.getAdyacents().positions()) {
				vertex.getAdyacents().remove(e);
			}
			removed= nodes.remove(vertex.getPosInVertexList()).element();
		}catch(InvalidPositionException exc) {exc.getMessage();}
		return removed;
	}

	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		if(e==null)
			throw new InvalidEdgeException("The edge is null.");
		E removed= null;
		LEdge<V,E> edge= (LEdge<V, E>) e;
		LVertex<V,E> v1= edge.getV1();
		LVertex<V,E> v2= edge.getV2();
		try {
			v1.getAdyacents().remove(edge.getPosEnlv1());
			v2.getAdyacents().remove(edge.getPosEnlv2());
			removed= edges.remove(edge.getPosInEdgesList()).element();
		} catch(InvalidPositionException exc) {exc.getMessage();}
		return removed;
	}
	
}
