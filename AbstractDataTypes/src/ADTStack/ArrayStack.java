package ADTStack;

import Exceptions.EmptyStackException;

public class ArrayStack<E> implements Stack<E> {

	protected E[] array;
	protected int size;
	
	public ArrayStack() {
		array= (E[]) new Object[20];
		size= 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public E top() throws EmptyStackException {
		if(isEmpty())
			throw new EmptyStackException("The stack is empty.");
		return array[size-1];
	}
	
	public void push(E element) {
		if(size==array.length)
			array= copiar(array.length);
		array[size]= element;
		size++;
	}
	
	private E[] copiar(int n) {
		E[] aux= (E[]) new Object[n*2];
		if(!isEmpty())
			for(int i=0; i<size; i++) {
				aux[i]= array[i];
				array[i]= null;
			}
		return aux;
	}
	
	public E pop() throws EmptyStackException {
		if(size==0)
			throw new EmptyStackException("The stack is empty.");
		E removed= array[size-1];
		array[size-1]= null;
		size--;
		return removed;
	}
	
	//--------------- Methods out of Interface ---------------//
	
	//On ADT
	public void invert(Stack<E> s) {
		if(!s.isEmpty()) {
			Stack<E> stack= new ArrayStack<E>();
			try {
				while(!s.isEmpty()) {
					stack.push(s.pop());
				}
			}catch(EmptyStackException e) {e.getMessage();}
			s= stack;
		}
	}
	
	//Out of ADT
	public void invert() {
		E[] aux= (E[]) new Object[array.length];
		int ind= 0;
		for(int i=size-1; i>=0; i--) {
			aux[ind]= array[i];
			array[i]= null;
			size--;
			ind++;
		}
		array= aux;
	}
	
	public void invert(ArrayStack<Person> p) {
		p.invert();
	}
	
	public Stack<Person> invertA(Person[] p) {
		Stack<Person> p1= new ArrayStack<Person>();
		for(int i=p.length-1; i>=0; i--) {
			p1.push(p[i]);
		}
		return p1;
	}
	
}