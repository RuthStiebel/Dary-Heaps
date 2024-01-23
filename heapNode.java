/**
 * This class represents a number node in a heap.
 * @author Ruth Rivka Stiebel
 */

public class HeapNode
{
    private int _value;
    private HeapNode _next;

    /**
     * Constructor for objects of class HeapNode.
     * @param value The value of the HeapNode
     */
    public HeapNode(int value)
    {
        _value = value;
        _next = null;
    }

    /**
     * Constructor for objects of class HeapNode.
     * @param value The value of the HeapNode
     * @param next The next HeapNode in the list
     */
    public HeapNode(int value, HeapNode next)
    {
        _value = value;
        _next = next;
    }

    //getters
    /**
     * Returns the value.
     * @return value
     */
    public int getValue ()
    {
        return _value;
    }

    /**
     * Returns next HeapNode in the list.
     * @return Next HeapNode in the list
     */
    public HeapNode getNext ()
    {
        return _next;   
    }

    //setters
    /**
     * Updates value to value given.
     * @param value New value
     */
    public void setValue (int value)
    {
        _value = value;   
    }

    /**
     * Updates next HeapNode to HeapNode given.
     * @param next New next HeapNode
     */
    public void setNext (HeapNode next)
    {
        _next = next;   
    }

    /**
     * Checks if the two HeapNodes have the same value.
     * @param other The other HeapNode
     * @return true If the two values are the same
     */
    public boolean equals (HeapNode other)
    {
        return _value.equals(other._value);   
    }
    
}

