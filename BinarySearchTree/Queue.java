package com.cse.ds;

/**
 * @author Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)
 * About this file:
 * This file implements a queue data structure
 */
public class Queue<E> {

	private E[] queue;
	private int size = 0;
	private float expandFactor = 0.5f;
	private float shrinkFactor = 0.5f;
    
    /**
     * a constructor for a queue
     */
	public Queue() {
		Object[] obj = new Object[5];
		queue = (E[]) obj;
	}
    
    /**
     * add an element to the queue
     * @param E val: the value to be added
     */
	public void enqueue(E val) {
		if (((float)size / (float)this.capacity()) >= expandFactor) {
            Object[] obj = new Object[2 * queue.length]; 
            E[] temp = (E[])obj;
            for (int i = 0; i < size; i++) {
                temp[i] = queue[i];
            }
            this.queue = temp;
		}
		queue[size] = val;
		size++;
	}
    
    /**
     * removes and returns the top element of the queue
     */
	public E dequeue() {
        // shrinks if the size reaches a certain amount
		if (((float)this.size()/(float)this.capacity()) <= shrinkFactor) {
            Object[] obj = new Object[this.capacity()/2]; 
            E[] temp = (E[])obj;
            for (int i = 0; i < size; i++) {
                temp[i] = queue[i];
            }
            this.queue = temp;
        }

        E popped = queue[0];
        Object[] obj = new Object[this.capacity()]; 
        E[] temp = (E[])obj;
        for (int i = 1; i < size; i++) {
            temp[i-1] = queue[i];
        }

        queue = temp;
        size--;
        return popped;
    }
    
	/**
     * returns the number of elements in the queue
     */
	public int size() {
		return size;
    }

    /**
     * returns the size of the queue
     */
    public int capacity() {
        return this.queue.length;
    }
}
