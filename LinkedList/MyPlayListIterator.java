package com.cse.ds;

import java.util.NoSuchElementException;

/*
* Tomoya Tokunaga (mailto: ttokunag@ucsd.edu)
* This file is about MyPlayListIterator class which is designed for 
listIterator implementation
*/
public class MyPlayListIterator<E> implements MyListIterator<E> {

    private boolean forward;  //flag telling if we can go next
    private boolean backward; //flag telling if we can go previous
    //flag telling if we've called next() or previous() method
    private boolean nextCalled, prevCalled;
    private Song<E> left, right; //song in the previous/next
    private int index; //current position of an iterator
    
    /*
    * description: constructor of MyPlayListIterator
    * parameter:   Song node (the first node in a playlist)
    */
    public MyPlayListIterator(Song<E> headNode) {
        this.left = headNode;
        this.right = headNode.getNext();
        this.index = -1;
        this.nextCalled = false;
        this.prevCalled = false;
    }

   /*
    * description: tells if we have a node in the next
    * parameter:   no parameter
    * return:      boolean
    */
    @Override
    public boolean hasNext() {
        if (this.right != null) {
            this.forward = true;
        } else {
            this.forward = false;
        }
        return this.forward;
    }

   /*
    * description: move the iterator to the next position
    * parameter:   no parameter
    * return:      data of a next node which a current iterator points to
    */
    @Override
    public E next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.left = this.right;
        this.right = this.right.getNext();
        this.index++;
        this.nextCalled = true;
        this.prevCalled = false;

        return this.left.getElement();
    }

   /*
    * description: tells if we have a node in the previous
    * parameter:   no parameter
    * return:      boolean
    */
    @Override
    public boolean hasPrevious() {
        if (this.left.getElement() != null) {
            this.backward = true;
        } else {
            this.backward = false;
        }
        return this.backward;
    }

   /*
    * description: move the iterator to the previous position
    * parameter:   no parameter
    * return:      data of a prev node which a current iterator points to
    */
    @Override
    public E previous() {
        if (!this.hasPrevious()) {
            throw new NoSuchElementException();
        }
        this.right = this.left;
        this.left = this.left.getPrev();
        this.index--;
        this.nextCalled = false;
        this.prevCalled = true;

        return this.right.getElement();
    }

   /*
    * description: returns the index of the next iterator position
    * parameter:   no parameter
    * return:      the index of the next iterator position
    */
    @Override
    public int nextIndex() {
        if (!hasNext()) {
            return this.index;
        }
        return this.index + 1;
    }

   /*
    * description: returns the index of the previous iterator position
    * parameter:   no parameter
    * return:      data of a next node which a current iterator points to
    */
    @Override
    public int previousIndex() {
        if (!hasPrevious()) {
            return -1;
        }
        return this.index;
    }

   /*
    * description: set a data of a node which is returned by the last next() or previous() call
    * parameter:   data to be set
    * return:      void
    */
    @Override
    public void set(E o) {
        if (!this.nextCalled && !this.prevCalled) {
            return;
        }
        else if (this.nextCalled && !this.prevCalled) {
            this.left.setElement(o);
        }
        else if (!this.nextCalled && this.prevCalled) {
            this.right.setElement(o);
        }
    }

}
