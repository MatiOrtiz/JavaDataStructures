package TDAStack;
import Exceptions.EmptyStackException;

public class ArrayStack<E> implements Stack<E> {

	protected E[] array;
	protected int size;
	
	public ArrayStack(int max) {
		array= (E[]) new Object[max];
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
		array[size]= element;
		size++;
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
			Stack<E> stack= new ArrayStack<E>(s.size());
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
		for(int i=size; i>=0; i--) {
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
	
}
