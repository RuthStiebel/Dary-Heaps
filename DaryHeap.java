import java.util.Scanner;
import java.io.*;
import java.lang.System;

public class DaryHeap {

    //class variables
    private static int[] heap;
    private static int heapEndPointer;
    private static final int MAX_SIZE = 500;
    private static final int PLACEHOLDER_NUM = -500000;
    private static int d;

    //formatting
    private static final String ANSI_BLACK_DEFAULT = "\u001B[30m";  
    private static final String ANSI_RED = "\u001B[31m"; 
    private static final String ANSI_GREEN = "\u001B[32m";  
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE_DARK = "\u001B[34m"; 
    private static final String ANSI_PURPLE = "\u001B[35m"; 
    private static final String ANSI_BLUE_DARK_LIGHT = "\u001B[36m";
    private static final String BOLD_STRING = "\033[0;1m";
    private static final String UNBOLD_STRING = "\u001B[0m";
   
    /**
     * Constructor for objects of class DaryHeap.
     * The time complexity is O(n).
     * The space complexity is O(n).
     * @param fileName The PATH of the file from which the heap is built.
     * @param dNum The number that decides how many children each parent could have.
     */
    public DaryHeap (String fileName, int dNum) {

        // initializes an empty max heap with maximum capacity
        heapEndPointer = 0;
        heap = new int[MAX_SIZE+1];
        d = dNum;

        //reads input file and places numbers into heap array
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = br.readLine()) != null && heapEndPointer < MAX_SIZE) {
                    
                try { //if there is more than just a number in each line then will return an error
                    heap[heapEndPointer]  = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println(BOLD_STRING + ANSI_YELLOW + "Invalid integer input in line: " + ANSI_BLACK_DEFAULT + line + UNBOLD_STRING);
                }
                
                heapEndPointer ++;
            }

            br.close(); //closes buffer file

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (heapEndPointer == MAX_SIZE)
            System.out.println(BOLD_STRING + ANSI_YELLOW + "Numbers in file exceeded maximum size allowed (" + ANSI_BLACK_DEFAULT + MAX_SIZE + ANSI_YELLOW + "). \nCopied only the first "
            + ANSI_BLACK_DEFAULT + MAX_SIZE + ANSI_YELLOW + " numbers in the file." + ANSI_BLACK_DEFAULT + UNBOLD_STRING);
    }

    /**
     * This method turns an unsorted array into a d-ary heap.
     * The time complexity is O(nlogn).QQ
     * The space complexity is O(1).
     */
    public void buildHeap() {
        for (int i = (heapEndPointer - 1) / d; i >= 0; i--)
            maxHeap(heapEndPointer, i);
    }
 
    /**
     * This method inserts a number into heap and then reorganizes it in order to stay a maximum heap.
     * If the heap has reached maximum size then the number given will not be added and the user would be duly notified of that fact.
     * The time complexity is QQ
     * The space complexity is O(1).
     * @param num The number to be added.
     */
    public void insert(int num) {
        if (heapEndPointer < MAX_SIZE) {
            heap[heapEndPointer] = num;
            heapEndPointer ++;
            maxHeap(heapEndPointer, 0);
        }
        else {
            System.out.println(BOLD_STRING + ANSI_YELLOW + "Numbers in file reached maximum size allowed (" + ANSI_BLACK_DEFAULT + MAX_SIZE + ANSI_YELLOW + "). " +
            "\nTherefore the number inputted was not added to heap. " + ANSI_BLACK_DEFAULT + UNBOLD_STRING);
        }
    }
    
    /** 
     * This method returns the maximum number in the heap.
     * Inevitably, it would be the first parent.
     * The time complexity is QQ
     * The space complexity is O(1).
     * @return The maximum number
     */
    public int extractMax() {
        //saving maximum number
        int max = heap[0];
        //removing the maximum number and replacing it with the last number in the heap
        heap[0] = heap[heapEndPointer - 1];
        heapEndPointer --;
        //resorting the heap
        maxHeap(heapEndPointer, 0);
        return max;
    }
 
    /**
     * This method turns a regular heap into a maximum heap.
     * @param len The length of the heap.
     * @param index The index from which to start sorting.
     */
    private void maxHeap(int len, int index) {

        //initializing an array with maximum amount of leaves per one parent
        int[] children = new int[d + 1];

        //sorting the heap
        while (true) {

            //placing into children array the children values - the last child will get the value of PLACEHOLDER_NUM
            for (int i = 1; i <= d; i++) 
                children[i] = (d * index + i) < len ? (d * index + i) : PLACEHOLDER_NUM;
 
            //sorting the 
            int maxChild = PLACEHOLDER_NUM, maxChildIndex = 0;
            for (int i = 1; i <= d; i++) { //time complexity is O(d)
                //if the children values aren't finished, and the value of the current child is larger than the current maximum value 
                //then updates maximum value and index
                if (children[i] != PLACEHOLDER_NUM && heap[children[i]] > maxChild) {
                    maxChildIndex = children[i];
                    maxChild = heap[children[i]];
                }
            }
            
            //stopping condition
            if (maxChild == PLACEHOLDER_NUM)
                break;
 
            if (heap[index] < heap[maxChildIndex])
                swap(index, maxChildIndex);
 
            index = maxChildIndex;
        }
    }

    /**
     * This method swaps two numbers in the heap.
     * The time complexity is O(1).
     * The space complexity is O(1).
     * @param i The first number to be swapped.
     * @param j The second number to be swapped.
     */
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * This method prints the heap in the form of an array.
     * The time complexity is O(n).
     * The space complexity is O(1).
     */
    public void print() {
        for (int i = 0; i < heapEndPointer; i++) {
            System.out.print(heap[i] + "\t");
        }
        System.out.println(); // prints new line at the end of the heap
    }

    /**
     * Main fuction.
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println (ANSI_RED + "READ BEFORE USING PROGRAM!\n\n" + ANSI_BLUE_DARK + "FOR THE USER'S INFORMATION:\n\n" + ANSI_BLACK_DEFAULT +
         "This program " + ANSI_PURPLE + "does not " + ANSI_BLACK_DEFAULT + "check that the \"d\" entered is a valid number.\n" +
         "This program " + ANSI_PURPLE + "does not " + ANSI_BLACK_DEFAULT + "check that the file PATH entered is correct and that the file residing there is not empty.\n" +
         "Each number in the file should be on a different line than the one before it, with no other symbols or letters.\n" + 
         ANSI_BLUE_DARK_LIGHT + "If the file given is built differently then specified then the program will glitch." + ANSI_BLACK_DEFAULT +
         "\n\nIf these instructions are not clear or acceptable to you, please " + ANSI_RED + "do not use the program for it is not meant for such as you." +
         ANSI_GREEN + "\nIf you would like to continue, please type 1 and then enter.");
        
         if (scan.nextInt() != 1) {
            System.out.println (ANSI_PURPLE + "Exiting program now."  + ANSI_BLACK_DEFAULT);
            System.exit (0);
        }

        System.out.println ("Please enter a number that is the 'd' wanted and then press enter: ");
        int d = scan.nextInt();
    
        System.out.println("Please enter the file PATH and then press enter:" + ANSI_BLACK_DEFAULT);
        String str = scan.next();

        // Initialising heap
        DaryHeap dHeap = new DaryHeap (str, d);

        // Displaying message for better readability
        System.out.println(BOLD_STRING + "Built Heap: " + UNBOLD_STRING);
        dHeap.print();
        
        dHeap.buildHeap();
        System.out.println(BOLD_STRING + "The D-ary Heap after sorting looks like: " + UNBOLD_STRING);
        dHeap.print();
        
        int num = 4;
        dHeap.insert(num);
        
        System.out.println(BOLD_STRING + "\n\nHeap after insertion of " + num + ": " + UNBOLD_STRING);
        dHeap.print();
        
        System.out.println(BOLD_STRING + "\n\nExtracted max is " + dHeap.extractMax());
        
        System.out.println(BOLD_STRING + "\n\nHeap after extract max: " + UNBOLD_STRING);
        dHeap.print();

        //closing scanner
        scan.close();

    }
}