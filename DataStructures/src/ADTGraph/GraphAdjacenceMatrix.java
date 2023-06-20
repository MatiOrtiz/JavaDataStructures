package ADTGraph;

import ADTList.*;
import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;

public class GraphAdjacenceMatrix<V,E> implements Graph<V,E> {

	protected PositionList<Vertex<V>> vertices;
	protected PositionList<Edge<E>> edges;
	protected Edge<E> [][] matrix;
	protected int sizeVertices;
	
	public GraphAdjacenceMatrix(int n) {
		vertices= new DoubleLinkedList<Vertex<V>>();
		edges= new DoubleLinkedList<Edge<E>>();
		matrix= (Edge<E>[][]) new MEdge[n][n];
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
	
	private MVertex<V> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if(v==null)
			throw new InvalidVertexException("The vertex is null.");
		return (MVertex<V>) v;
	}
	
	private MEdge<V,E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		if(e==null)
			throw new InvalidEdgeException("The edge is null.");
		return (MEdge<V,E>) e;
	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		MVertex<V> vertex= checkVertex(v);
		PositionList<Edge<E>> iterable= new DoubleLinkedList<Edge<E>>();
		for(int i=0; i<matrix[0].length; i++) {
			if(matrix[vertex.getIndice()][i]!=null)
				iterable.addLast(matrix[vertex.getIndice()][i]);
		}
		return iterable;
	}

	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		MEdge<V,E> edge= checkEdge(e);
		checkVertex(v);
		Vertex<V> vertex= null;
		if(edge.getV1()==v && edge.getV2()!=null)
			vertex= edge.getV2();
		else if(edge.getV2()==v && edge.getV1()!=null)
			vertex= edge.getV1();
		return vertex;
	}

	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		MEdge<V,E> edge= checkEdge(e);
		Vertex<V>[] array= new MVertex[2];
		array[0]= edge.getV1();
		array[1]= edge.getV2();
		return array;
	}

	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		MVertex<V> v1= checkVertex(v);
		MVertex<V> v2= checkVertex(w);
		return matrix[v1.getIndice()][v2.getIndice()]!=null;
	}

	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		MVertex<V> vertex= checkVertex(v);
		V replaced= v.element();
		vertex.setElement(x);
		return replaced;
	}

	public Vertex<V> insertVertex(V x) {
		MVertex<V> vertex= new MVertex<V>(x, sizeVertices);
		sizeVertices++;
		vertices.addLast(vertex);
		try {
			vertex.setPosInVertices(vertices.last());
		} catch(EmptyListException e) {e.getMessage();}
		return vertex;
	}

	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		MVertex<V> v1= checkVertex(v);
		MVertex<V> v2= checkVertex(w);
		MEdge<V,E> edge= new MEdge<V,E>(e,v1,v2);
		matrix[v1.getIndice()][v2.getIndice()]= edge;
		matrix[v2.getIndice()][v1.getIndice()]= edge;
		return edge;
	}

	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		MVertex<V> vertex= checkVertex(v);
		for(int i=0; i<matrix.length; i++) {
			if(matrix[i][vertex.getIndice()]!=null)
				matrix[i][vertex.getIndice()]= null;
		}
		V removed= v.element();
		try {
			vertices.remove(vertex.getPosInVertices());
		} catch(InvalidPositionException e) {e.getMessage();}
		sizeVertices--;
		return removed;
	}

	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		MEdge<V,E> edge= checkEdge(e);
		E removed= null;
		MVertex<V> v1= edge.getV1();
		MVertex<V> v2= edge.getV2();
		matrix[v1.getIndice()][v2.getIndice()]= null;
		matrix[v2.getIndice()][v1.getIndice()]= null;
		removed= e.element();
		try {
			edges.remove(edge.getPosInEdges());
		} catch(InvalidPositionException exc) {exc.getMessage();}
		return removed;
	}
	
}
