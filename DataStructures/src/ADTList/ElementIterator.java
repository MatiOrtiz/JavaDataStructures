package ADTList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import Exceptions.BoundaryViolationException;
import Exceptions.EmptyListException;
import Exceptions.InvalidPositionException;

public class ElementIterator<E> implements Iterator<E> {
	
	protected PositionList<E> list;
	protected Position<E> pos;
	
	public ElementIterator(PositionList<E> l) {
		list= l;
		try {
			if(list.isEmpty())
				pos= null;
			else pos= list.first();
		}catch(EmptyListException e) {e.getMessage();}
	}
	
	public boolean hasNext() {
		return pos!= null;
	}
	
	public E next() {
		if(pos==null)
			throw new NoSuchElementException("It has not next");
		E elem= pos.element();
		try {
			if(pos==list.last())
				pos= null;
			else pos= list.next(pos);
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {e.printStackTrace();}
		return elem;
	}
}
