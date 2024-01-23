/**
 * The class represents a d-ary heap.
 *
 * @author Ruth Rivka Stiebel
 */

import java.io.FileReader; 


public class DaryHeap
{
    private HeapNode _head;

    /**
     * Default constructor for objects of class DaryHeap.
     * The time complexity is O(1).
     * The space complexity is O(1).
     */
    public DaryHeap ()
    {
        _head = null;
    }

    /**
     * Constructor for object of class DaryHeap.
     * The time complexity is O(nlogn).
     * The space complexity is O(n).
     * @param text The collection of words from which the heap is built 
     */
    public DaryHeap (String text)
    {
        if(text.equals("") || text.equals(" "))//if there are no words in the text
            _head = null;

        else
        {
            HeapNode ptr = null;
            while (!text.equals(""))
            {
                int indexOfFirstWord = text.indexOf(' ');
                if (_head==null) //initializing  _head
                {
                    if (indexOfFirstWord==-1) //if there is only one word left
                    {
                        _head = new HeapNode(text);
                        text = "";
                    }
                    else
                    { 
                        _head = new HeapNode(text.substring(0, indexOfFirstWord));
                        text = text.substring(indexOfFirstWord+1, text.length()); //shortening the String
                        ptr = _head;
                    }
                }
                else
                {
                    if (indexOfFirstWord==-1) //if there is only one word left
                    {
                        ptr.setNext(new HeapNode(text));
                        text = "";
                    }
                    else
                    { 
                        ptr.setNext(new HeapNode(text.substring(0, indexOfFirstWord)));
                        text = text.substring(indexOfFirstWord+1, text.length()); //shortening the String
                    }
                    ptr = ptr.getNext();
                }
            }
            _head = merge(_head);
        }
    }

    /**
     * Returns String representation of the heap.
     * The time complexity is O(n).
     * The space complexity is O(1).
     * @return String representation of the heap
     */ 
    public String toString ()
    {
        String str = "";
        HeapNode tmp = _head;
        while (tmp!=null)
        {
            str+= tmp.getValue() + "\t";
        }
        return str;
    }
}