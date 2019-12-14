/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsa2;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 *
 * @author macbookpro
 */
public class DoublyLinkedDeque<E extends Comparable> implements DequeADT{
    private Node<E> front;
    private Node<E> rear;
    int numElement;
    
    DoublyLinkedDeque(){
        numElement = 0;
        front = null;
        rear = null;
    }
   
 // adds one element to the rear of this queue
   public void enqueueRear(E element)
   {
        Node<E> newNode = new Node<E>();
        newNode.setElement(element);
        newNode.setPrev(rear);
        if(rear != null) rear.setNext(newNode);
        if(rear == null) front = newNode;
        rear = newNode;
        numElement++;
   }
   
   // adds one element to the front of this queue
   public void enqueueFront(E element)
   {
        
         Node<E> newNode = new Node<>(element);
         newNode.setElement(element);
         newNode.setNext(front);
         if(front != null){
             front.setPrev(newNode); 
         }
         
         if(front == null)
         {
          rear = newNode;   
         }
         front = newNode;
              
         numElement++;
   }
   
   // removes and returns the front element of the queue
   @Override
   public E dequeueFront() throws NoSuchElementException
   { 
       if(front == null){
            System.out.println("Underflow state.");
            return null;
        }
        Node<E> tmpFront = front.getNext();
        if(tmpFront != null) tmpFront.setPrev(null);
        if(tmpFront == null) rear = null;
        front = tmpFront;
        return front.getElement();
        
   }
   
    
   
   // removes and returns the rear element of the queue
   @Override
   public E dequeueRear() throws NoSuchElementException
   {
        if(rear == null){
            System.out.println("Underflow state.");
            return null;
        }
        Node<E> tmpRear = rear.getPrev();
        if(tmpRear != null) tmpRear.setNext(null);
        if(tmpRear == null) front = null;
        System.out.println("Removed element from rear: "+rear.getElement());
        rear = tmpRear;
        return rear.getElement();
   }
   
   // returns without removing the front element of this queue
   @Override
   public E first() throws NoSuchElementException
   {   
       return front.getElement();
   }
   // returns without removing the rear element of this queue
    @Override
   public E last() throws NoSuchElementException
   {
        return rear.getElement();
       
   }
   // returns true if this queue contains no elements
    @Override
   public boolean isEmpty()
   {
       if(front == null && rear == null)
           return true;
       return false;
   }
   // returns the number of elements in this queue
    @Override
   public int size()
   {
        return numElement;
       
   }
   //returns an Iterator which iterates from front or back
    @Override
   public Iterator<E> iterator(boolean fromFront)
   {
      
       if(fromFront){
           return new doublyLinkedListIterator();
       }
          
      return null; 
   }
   
   public class doublyLinkedListIterator implements Iterator<E> {
       Node<E> current = null;
        @Override
        public boolean hasNext() {
            if(current == null && front != null)
                return true;
             if(current != null)
                 return current.getElement() != null;
            return false;
        }

        @Override
        public E next() {
            if(current == null && front!= null)
            {
                current = front;
                return current.getElement();
            }
            else if(current != null)
            {
                current = current.getNext();
                current.prev = null;
                
                return current.getElement();
                
            }
            
            throw new NoSuchElementException();
        }

    
}
   
   //clears the deque colmpletly
    @Override
   public void clear()
   {
       
   }

    @Override
    public void enqueueRear(Object element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void enqueueFront(Object element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

