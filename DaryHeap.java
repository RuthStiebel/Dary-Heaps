import java.util.Scanner;
import java.io.*;
import java.lang.System;

public class DaryHeap {
	private int[] heap;
	private int heapEndPointer;
	private static final int MAX_SIZE = 5000;
    private int d;

    //colours  private final String ANSI_BLACK = "\u001B[30m";  private final String ANSI_RED = "\u001B[31m"; private final String ANSI_GREEN = "\u001B[32m";  private final String ANSI_BLUE = "\u001B[34m"; private final String ANSI_PURPLE = "\u001B[35m"; 

   // Initializes a heap with up to maximum capacity from a file
    
    /**
     * Constructor for object of class DaryHeap.
     * The time complexity is O(n).
     * The space complexity is O(n).
     * @param fileName The PATH of the file from which the heap is built 
     */
    public DaryHeap (String fileName, int d)
    {
        // initializes an empty max heap with maximum capacity
        this.heapEndPointer = 0;
		this.heap = new int[MAX_SIZE];
        this.d = d;
 
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = br.readLine()) != null && heapEndPointer < MAX_SIZE) {
                    
                try {
                    this.heap[heapEndPointer]  = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid integer input in line: " + line);
                }
                

                heapEndPointer ++;
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (heapEndPointer == MAX_SIZE)
            System.out.println("Numbers in file exceeded maximum size allowed (" + MAX_SIZE + "). \nCopied only the first "
            + MAX_SIZE + " numbers in the file.");
    }


	// Method 1
	// Returning position of parent
	private int parent (int position) 
    { 
        return (position - 1) / this.d; 
    }

	// Method 2
	// Returning a child in position given 
	private int dChild(int position, int dPlace) 
    {   
        if (dPlace <= this.d)
            return (2 * position) + d; 
        return 0;
    }

	// Method 3
	// Returning true if given node is leaf
	private boolean isLeaf(int position)
	{
		if (position > (heapEndPointer / this.d) && position <= heapEndPointer) 
			return true;
		return false;
	}

	// Method 4
	// Swapping nodes
	private void swap(int firstPosition, int swapPosition)
	{
		int tmp;
		tmp = heap[firstPosition];
		heap[firstPosition] = heap[swapPosition];
		heap[swapPosition] = tmp;
	}

	// Method 65
	// Recursive function to max heapify given subtree
	private void maxHeapify (int position)
	{
        int dPlace = 1;
        int counter = 1;

		if (isLeaf(position)) //then there is no leaf after
			return;

        while (dPlace <= this.d) {
            while (counter <= this.d) {

                 //if the value of the son is larger than the value of the father
                if (heap[position] < heap[dChild(position, dPlace)]) {

                    //if the value on the left is larger than the value on the right
                    if (heap[dChild(position, dPlace)] > heap[dChild(position, dPlace + counter)]) { 
                        swap(position, dChild(position, dPlace));
                        maxHeapify(dChild(position, dPlace));
                    }
                    //the value on the right is larger than the value on the left
                    else { 
                        swap(position, dChild(position, dPlace + 1));
                        maxHeapify(dChild(position, dPlace + 1));
                    }
                }
                counter ++;
            }
            dPlace ++;
        }	
	}

	// Method 6
	// Inserts a new element to max heap
    // The time complexity is O(n)
	public void insert(int element)
	{
		heap[this.heapEndPointer] = element;

		// Fix the heap so it stays a maximum heap
		int current = this.heapEndPointer;
		while (heap[current] > heap[parent(current)]) {
			swap(current, parent(current));
			current = parent(current);
		}
		this.heapEndPointer++;
	}

	// Method 7
	// To display heap
	public void print()
	{
        int dCounter = 1;

		for (int i = 0; i < heapEndPointer / this.d; i++) {

			System.out.print("Parent Node : " + heap[i]);
            while (dCounter < this.d) 
            {
                if (dChild(i, dCounter) < heapEndPointer) // checks if the child is out of the bound of the array
                    System.out.print(dCounter + " Child Node: " + heap[dChild(i, dCounter)]);
                dCounter++;
            }

            dCounter = 1;
			System.out.println(); // for new line
		}
	}

//in extract max I need to save the highest value and then reheapify the the heap
	// Method 8
	// Remove an element from max heap
	
    public int extractMax ()
	{
		int max = this.heap[0];
		this.heap[0] = this.heap[--this.heapEndPointer];
		maxHeapify(0);
		return max;
	}

	// Method 9
	// main method
	/**
	 * @param arg
	 */
	public static void main(String[] arg)
	{
        Scanner scan = new Scanner(System.in);
        System.out.println ("READ BEFORE USING PROGRAM!\nFOR THE USER'S INFORMATION:\n" +
         "This program does not check that the \"d\" entered is a valid number.\n" +
         "This program does not check that the file PATH entered is correct and that the file residing there is not empty.\n" +
         "Each number in the file should be on a different line than the one before it, with no other symbols or letters.\nIf the file is built differently then the program will glitch." + 
         "If these instructions are not clear or acceptable to you, please do not use the program for it is not meant for such as you." +
         "\nIf you would like to continue, please type 1 and then enter.");
        
         if (scan.nextInt() != 1) {
            System.out.println ("Exiting program now.");
            System.exit (0);
        }

        System.out.println ("Please enter a number that is the 'd' wanted: ");
        int d = scan.nextInt();
    
        System.out.println ("Please enter the file PATH: ");
        String str = scan.nextLine();

		// Initialising heap
        DaryHeap dHeap = new DaryHeap (str, d);

        // Displaying message for better readability
		System.out.println("The D-ary Heap after sorting looks like: ");
		dHeap.print();

		// Print and display the maximum value in heap
		//System.out.println("The max val is "	+ dHeap.extractMax());

        //closing scanner
        scan.close();
	}
}
