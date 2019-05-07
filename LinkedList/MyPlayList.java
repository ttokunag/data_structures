package com.cse.ds;

/**
 * Tomoya Tokunaga (mailto:ttokunag@ucsd.edu)
 * This file is about MyPlayList class, which is designed to implement
 linkedlist data structure.
 */
import java.util.*;

public class MyPlayList<E> extends AbstractList<E> {

    private int nsongs;
    Song<E> dummy;

    /*
    * MyPlayList class implements linkedlink data structure
    * No parameter is needed to initiate
    */
    public MyPlayList()
    {
        this.dummy = new Song<E>(null);
        this.nsongs = 0;
    }

    /*
    * description: returns the number of songs in a playlist
    * parameter:   no parameter
    * return:      int
    */
    @Override
    public int size()
    {
        return this.nsongs;

    }

    /*
    * description: returns data of a node
    * parameter:   index of the element
    * return:      E
    */
    @Override
    public E get(int index) // use "throws"
    {
        if (index >= nsongs) {
            throw new IndexOutOfBoundsException();
        }
        int curIndex = 0;
        Song<E> curSong = this.dummy.getNext();
        while (curIndex != index) {
            curSong = curSong.getNext();
            curIndex++;
        }
        return curSong.getElement();
    }

    /*
    * description: insert a node into a specified position
    * parameter:   index to insert, data of a node
    * return:      void
    */
    @Override
    public void add(int index, E element)
    {
        if (element == null) {
            throw new NullPointerException();
        }
        if (index > this.nsongs) {
            throw new IndexOutOfBoundsException();
        }
        if (nsongs == 0) {
            Song<E> newSong = new Song<E>(element, this.dummy, null);
            dummy.setNext(newSong);
        }
        else {
            int curIndex = 0;
            Song<E> curSong = this.dummy.getNext();
            while (curIndex != index) {
                curSong = curSong.getNext();
                curIndex++;
            }
            Song<E> newSong = new Song<E>(element, curSong.getPrev(), curSong);
            newSong.getPrev().setNext(newSong);
            newSong.getNext().setPrev(newSong);
        }
        
        this.nsongs++;
    }

    /*
    * description: add a node at the end of the playlist
    * parameter:   data of a node to be added
    * return:      boolean (generally, returns true)
    */
    @Override
    public boolean add(E element)
    {
        if (element == null) {
            throw new NullPointerException();
        }
        int curIndex = 0;
        Song<E> curSong = this.dummy;
        while (curIndex != this.nsongs) {
            curSong = curSong.getNext();
            curIndex++;
        }
        Song<E> newSong = new Song<E>(element, curSong, null);
        curSong.setNext(newSong);
        this.nsongs++;

        return true;
    }

    /*
    * description: set data of a element
    * parameter:   index of an element, data to be set
    * return:      E (previous data of the node)
    */
    @Override
    public E set(int index, E element)
    {
        if (element == null) {
            throw new NullPointerException();
        }
        if (index >= this.nsongs) {
            throw new IndexOutOfBoundsException();
        }
        int curIndex = 0;
        Song<E> curSong = this.dummy.getNext();
        while (curIndex != index) {
            curSong = curSong.getNext();
            curIndex++;
        }
        E prevData = curSong.getElement();
        curSong.setElement(element);
        return prevData;
    }

    /*
    * description: removes a node
    * parameter:   index of an element to be removed
    * return:      E (data of removed node)
    */
    @Override
    public E remove(int index)
    {
        if (index >= this.nsongs) {
            throw new IndexOutOfBoundsException();
        }
        int curIndex = 0;
        Song<E> curSong = this.dummy.getNext();
        while (curIndex != index) {
            curSong = curSong.getNext();
            curIndex++;
        }
        E removedData = curSong.getElement();
        (curSong.getPrev()).setNext(curSong.getNext());
        if (curSong.getNext() != null) {
            (curSong.getNext()).setPrev(curSong.getPrev());
        }
        nsongs--;
        return removedData;
    }

    /*
    * description: clears the playlist
    * parameter:   no parameter
    * return:      void
    */
    @Override
    public void clear()
    {
        this.dummy = new Song<E>(null);
        this.nsongs = 0;
    }

    @Override
    public boolean isEmpty()
    {
        return this.nsongs == 0;
    }

    /*
    * description: shuffle the playlist
    * parameter:   no parameter
    * return:      void
    */
    public void shuffle()
    {
        // if a list has no element
        if (nsongs == 0) {
            return;
        }
        Random rand = new Random(1234);
        int i = rand.nextInt(this.nsongs);
        int j = rand.nextInt(this.nsongs);

        Song<E> iSong = this.getNth(i);
        Song<E> jSong = this.getNth(j);

        Song<E> newiSong = new Song<E>(jSong.getElement(), iSong.getPrev(), iSong.getNext());
        iSong.getPrev().setNext(newiSong);
        if (iSong.getNext() != null) {
            iSong.getNext().setPrev(newiSong);
        }

        Song<E> newjSong = new Song<E>(iSong.getElement(), jSong.getPrev(), jSong.getNext());
        jSong.getPrev().setNext(newjSong);
        if (jSong.getNext() != null) {
            jSong.getNext().setPrev(newjSong);
        }

    }

    /*
    * description: reverses the order of the playlist
    * parameter:   no parameter
    * return:      void
    */
    public void reverse()
    {
        // if a list contains no element
        if (nsongs == 0) {
            return;
        }
        
        int swapCount = 0;
        while (swapCount != nsongs-1) {
            Song<E> curSong = dummy.getNext();
            int numSwap = nsongs-swapCount-1;
            while (numSwap != 0) {
                Song<E> nextSong = curSong.getNext();
                Song<E> prevSong = curSong.getPrev(); //dummy?
                Song<E> succSong = nextSong.getNext();//null?
                // curSong <-> succSong
                curSong.setNext(succSong);
                if (succSong != null) {
                    succSong.setPrev(curSong);
                } 
                curSong.setPrev(nextSong);
                nextSong.setNext(curSong);
                nextSong.setPrev(prevSong);
                prevSong.setNext(nextSong);

                numSwap--;
            }
            swapCount++;
        }
    }

    /*
    * description: initialize an iterator for the playlist
    * parameter:   no parameter
    * return:      MyListIterator
    */
    public MyListIterator<E> myListIterator() {
        return new MyPlayListIterator<E>(this.dummy);
    }

    /*
    * description: private helper method which returns a node at an index
    * parameter:   index of a node
    * return:      Song
    */
    private Song<E> getNth(int index) {
        if (index >= this.nsongs) {
            throw new IndexOutOfBoundsException();
        }
        int curIndex = 0;
        Song<E> curSong = this.dummy.getNext();
        while (curIndex != index) {
            curSong = curSong.getNext();
            curIndex++;
        }
        return curSong;
    }

}


