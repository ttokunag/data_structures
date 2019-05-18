package com.cse.ds;

public interface MyListIterator<E> {

    boolean hasNext();

    E next();

    boolean hasPrevious();

    E previous();

    int nextIndex();

    int previousIndex();

    void set(E e);


}
