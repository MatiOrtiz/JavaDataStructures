package ADTGraph;

import ADTList.*;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidVertexException;

public class GraphAdjacenceMatrix<V,E> implements Graph<V,E> {

	protected PositionList<Vertex<V>> vertices;
	protected PositionList<Edge<E>> edges;
	protected Edge<E> [][] matrix;
	protected int sizeVertices;
	
	public GraphAdjacenceMatrix(int n) {
		vertices= new DoubleLinkedList<Vertex<V>>();
		edges= new DoubleLinkedList<Edge<E>>();
		matrix= (Edge<E>[][]) new LEdge[n][n];
		sizeVertices= 0;
	}

	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> iterable= new DoubleLinkedList<Vertex<V>>();
		for(Vertex<V> v : vertices)
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
