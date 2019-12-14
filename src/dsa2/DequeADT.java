package dsa2;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sehall
 * @param <E>
 */
public interface DequeADT<E> 
{
     // adds one element to the rear of this queue
   public void enqueueRear(E element);
   // removes and returns the front element of the queue
   public E dequeueFront() throws NoSuchElementException;
   // returns without removing the front element of this queue
   public E first() throws NoSuchElementException;
   // adds one element to the front of this queue
   public void enqueueFront(E element);
   // removes and returns the rear element of the queue
   public E dequeueRear() throws NoSuchElementException;
   // returns without removing the rear element of this queue
   public E last() throws NoSuchElementException;
   // returns true if this queue contains no elements
   public boolean isEmpty();
   // returns the number of elements in this queue
   public int size();
   //returns an Iterator which iterates from front or back
   public Iterator<E> iterator(boolean fromFront);
   //clears the deque colmpletly
   public void clear();
}

