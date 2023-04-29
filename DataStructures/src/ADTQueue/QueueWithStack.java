package ADTQueue;

import ADTStack.ArrayStack;
import ADTStack.Stack;
import Exceptions.EmptyQueueException;


public class QueueWithStack<E> implements Queue<E> {
	
	private Stack<E> stack;

    public QueueWithStack() {
        stack = new ArrayStack<E>();
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }
    
    public E front() throws EmptyQueueException {
    	E aux= null;
    	if(stack.isEmpty())
    		throw new EmptyQueueException("The stack in the queue is empty.");
    	try {
    	aux= stack.top();
    	}catch(Exceptions.EmptyStackException e) {e.getMessage();}
    	return aux;
    }

    public void enqueue(E element) {
        stack.push(element);
    }

    public E dequeue() throws EmptyQueueException {
    	E element= null;
        if (stack.isEmpty()) {
            throw new EmptyQueueException("The stack in the queue is empty.");
        }
        try {
	        element = stack.pop();
	        if (!stack.isEmpty()) {
	            E next = dequeue();
	            stack.push(element);
	            element = next;
	        }
        } catch(Exceptions.EmptyStackException e) {e.printStackTrace();} 
        
        return element;
    }
    
    public void Cin1Cin2ToCout(Stack<Character> cin1, Stack<Character> cin2) {
    	Queue<Character> cout= new QueueWithStack<Character>();
	    try { 	
    		while(!cin1.isEmpty() && !cin2.isEmpty()) {
	    		if(cin1.top()<cin2.top())
	    			cout.enqueue(cin1.top());
	    		else cout.enqueue(cin2.pop());
	    	}
    		while(!cin1.isEmpty())
    			cout.enqueue(cin1.pop());
    		while(!cin2.isEmpty())
    			cout.enqueue(cin2.pop());
    	}catch(Exceptions.EmptyStackException e) { e.getMessage();}
    }
	
}
