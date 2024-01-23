/**
 * The class represents a d-ary heap.
 *
 * @author Ruth Rivka Stiebel
 */

import java.io.*;



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
     * Constructor for object of class DaryHeap. It just turns all the numbers into nodes, but does not organize them.
     * The time complexity is O(nlogn).
     * The space complexity is O(n).
     * @param filename The PATH of the file from which the heap is built 
     */
    public DaryHeap (String filename)
    {
        // turn numbers in file into a linked list that represents the heap
        DaryHeap heap = new DaryHeap();
        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(fileName));

            String line;
            while ((line = br.readLine()) != null) {
                heap.addNum((int)line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    /**
     * Adds a number to the heap.
     * The time complexity is O(1).
     * The space complexity is O(1).
     * @param num The number to be added
     */
    public void addNum (int num)
    {
        if (_head == null)
            _head = new HeapNode (num);
        else
        {
            HeapNode tmp = new HeapNode (num, _head);
            _head = tmp;
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

        while (tmp != null)
        {
            str+= tmp.getValue() + "\t";
        }
        
        return str;
    }
}