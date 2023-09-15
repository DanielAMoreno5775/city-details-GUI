
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 */
public class OrderedAddOnce <E extends Comparable<? super E>> implements AddOnce <E>, Iterable<E>{
    //Aggregated private inner classes
    private class Node<E extends Comparable<? super E>> {
        private E data;
        private Node<E> next;
        public Node (E initData) {
            next = null;
            data = initData;
        }
    }
    
    private class AddOnceIterator implements Iterator<E> {
        private Node<E> current;
        
        public AddOnceIterator(){
            current = firstNode;
        }
        
        //check to see whether there is another item in the list
        @Override
        public boolean hasNext(){
            return current != null && firstNode != null;
        }
        
        @Override
        public E next(){
            //ensure that something was passed
            if (current == null) {
                throw new NoSuchElementException();
            }
            //return the next item and move the pointer forward
            E toReturn = current.data;
            current = current.next;
            return toReturn;
        }
    }
    
    //data fields
    private Node<E> firstNode;
    private int length;
    
    //constructor
    public OrderedAddOnce(){
        firstNode = null;
        length = 0;
    }
    
    //add a new item and return it or return the already-present item
    @Override
    public E addOnce(E item) {
        //create a new node with the given value
        Node <E> temp = new Node<E>(item);
        
        //check if the list is empty and special case for head node
        if (firstNode == null) {
            temp.next = firstNode;
            firstNode = temp;
            length += 1;
            return firstNode.data;
        }
        //if the new item matches the current head
        else if (firstNode.data.compareTo(temp.data) == 0) {
            return firstNode.data;
        }
        //will insert new node as head if head alphabetically greater than temp
        else if (firstNode.data.compareTo(temp.data) > 0) {
            temp.next = firstNode;
            firstNode = temp;
            length += 1;
            return firstNode.data;
        }
        //if the list already exists and head checks are met, insert the node at the proper spot
        else {
            //locate node before point of insertion
            Node current = firstNode;
            while ((current.next != null) && (current.next.data.compareTo(temp.data) <= 0)) {
                //if that node already exists
                if(current.next.data.compareTo(temp.data) == 0) {
                    temp = current.next;
                    return temp.data;
                }
                current = current.next;
            }
            
            //if node isn't found, add it
            temp.next = current.next;
            current.next = temp;
            length += 1;
            return temp.data;
        }
    }

    @Override
    public Iterator iterator() {
        return new AddOnceIterator();
    }
    
    public void empty () {
        //clear out most of the pointers in the list
        Node  current = firstNode;
        while (firstNode != null){
            current = firstNode.next;
            firstNode.next = null;
            firstNode = current;
        }
        
        //ensure that the firstNode is set to null which will leave the entire list for the garbage collector
        firstNode = null;
    }
    
    public int getLength(){
        return length;
    }
    
}
