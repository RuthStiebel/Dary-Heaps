// Java program to implement Max D-ary heap
import java.util.Scanner;

// Main class
public class DaryHeap {
	private int[] Heap;
	private int heapEndPointer;
	private final int maxSize = 5000;
    private int d;

	// Constructor to initialize an empty max heap with maximum capacity
	public DaryHeap()
	{
		this.heapEndPointer = 0;
		this.Heap = new int[this.maxSize];
	}

    // Second constructor 
    // Initializes a heap with up to maximum capacity from a file
    
    /**
     * Constructor for object of class DaryHeap.
     * The time complexity is O(n).
     * The space complexity is O(n).
     * @param filename The PATH of the file from which the heap is built 
     */
    public DaryHeap (String filename)
    {
        // turn numbers in file into a linked list that represents the heap
        DaryHeap();
        BufferedReader br = null;

        try {

            br = new BufferedReader(new FileReader(fileName));

            String line;
            while ((line = br.readLine()) != null && heapEndPointer < maxSize) {
                this.Heap[heapEndPointer] = (int)line;
                heapEndPointer ++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }

        if (heapEndPointer = maxSize)
            System.out.println("Numbers in file exceeded maximum size allowed (" + maxSize + "). \nCopied only the first "
            + maxSize + " numbers in the file.");
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

	// Method 4
	// Returning true if given node is leaf
	private boolean isLeaf(int position)
	{
		if (position > (heapEndPointer / this.d) && position <= heapEndPointer) 
			return true;
		return false;
	}

	// Method 5
	// Swapping nodes
	private void swap(int firstPosition, int swapPosition)
	{
		int tmp;
		tmp = Heap[firstPosition];
		Heap[firstPosition] = Heap[swapPosition];
		Heap[swapPosition] = tmp;
	}

	// Method 6
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
                if (Heap[position] < Heap[dChild(position, dPlace)]) {

                    //if the value on the left is larger than the value on the right
                    if (Heap[dChild(position, dPlace)] > Heap[dChild(position, dPlace + counter)]) { 
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

	// Method 7
	// Inserts a new element to max heap
    // The time complexity is O(n)
	public void insert(int element)
	{
		Heap[this.heapEndPointer] = element;

		// Fix the heap so it stays a maximum heap
		int current = this.heapEndPointer;
		while (Heap[current] > Heap[parent(current)]) {
			swap(current, parent(current));
			current = parent(current);
		}
		this.heapEndPointer++;
	}

	// Method 8
	// To display heap
	public void print()
	{
        int dCounter = 1;

		for (int i = 0; i < heapEndPointer / this.d; i++) {

			System.out.print("Parent Node : " + Heap[i]);
            while (dCounter < this.d) 
            {
                if (dChild(i, dCounter) < heapEndPointer) // checks if the child is out of the bound of the array
                    System.out.print(" Left Child Node: "+ Heap[dChild(i, dCounter)]);
                dCounter++;
            }

            dCounter = 1;
			System.out.println(); // for new line
		}
	}

//in extract max I need to save the highest value and then reheapify the the heap
	// Method 9
	// Remove an element from max heap
	
    public int extractMax (int heap[])
	{
		int heapEndPointer = heap.length();
		int max = heap[0];
		heap[0] = heap[--heapEndPointer];
		maxHeapify(0);
		return max;
	}

	// Method 10
	// main method
	public static void main(String[] arg)
	{
        Scanner scan = new Scanner(System.in);
        System.out.println ("READ BEFORE USING PROGRAM!\nFOR THE USER'S INFORMATION:\n" +
         "This program does not check that the \"d\" entered is a valid number.\n" +
         "This program does not check that the file PATH entered is correct and that the file residing there is not empty.\n" +
         "The numbers in the file should be seperated by \"\\n\" - or an enter, else the program will glitch." + 
         "If these instructions are not clear or acceptable to you, please do not use the program for it is not meant for such as you." +
         "If you would like to continue, please type 1 and then enter.");
        
         if (scan.nextInt() != 1) {
            System.out.println ("Exiting program now.");
            exit (0);
        }

        System.out.println ("Please enter a number that is the 'd' wanted: ");
        int d = scan.nextInt();
    
        System.out.println ("Please enter the file PATH: ");
        String str = scan.next();

		// Initialising heap
        DaryHeap dHeap = new DaryHeap (str);

        // Displaying message for better readability
		System.out.println("The D-ary Heap after sorting looks like: ");
		dHeap.print();

		// Print and display the maximum value in heap
		System.out.println("The max val is "
						+ dHeap.extractMax());
	}
}
