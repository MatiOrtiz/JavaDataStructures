package ADTBinaryTree;

import ADTList.Position;

public interface BTPosition<E> extends Position<E> {
	public E element();
	public BTPosition<E> parent();
	public BTPosition<E> left();
	public BTPosition<E> right();
}
