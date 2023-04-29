package ADTStack;
import Exceptions.EmptyStackException;
import NodesPackage.Node;

public class LinkedStack<E> implements Stack<E>{
	protected Node<E> head;
	protected int size;
	
	public LinkedStack() {
		head= null;
		size= 0;
	} 
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public E top() throws EmptyStackException {
		if(size==0)
			throw new EmptyStackException("The stack is empty.");
		return head.getElement();
	}
	
	public void push(E element) {
		Node<E> node= new Node<E>(element);
		node.setNext(head);
		head= node;
		size++;
	}
	
	public E pop() throws EmptyStackException {
		if(size==0)
			throw new EmptyStackException("The stack is empty.");
		E removed= head.getElement();
		head= head.getNext();
		size--;
		return removed;
	}
	
	public Stack<Integer> convert(Stack<Stack<Integer>> s1) {
		Stack<Integer> stack= new LinkedStack<Integer>();
		Stack<Integer> stackToReturn= new LinkedStack<Integer>();
		Stack<Integer> aux;
		try {
			while(!s1.isEmpty()) {
				aux= s1.pop();
				while(!aux.isEmpty())
					stack.push(aux.pop());
			}
			while(!stack.isEmpty())
				stackToReturn.push(stack.pop());
		} catch(EmptyStackException e) {e.getMessage();}
		return stackToReturn;
	}
	
}
