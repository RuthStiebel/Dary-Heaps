import java.util.Scanner;
import java.io.*;
import java.lang.System;

public class DaryHeap {

    private static int[] heap;
    private static int heapEndPointer;
    private static final int MAX_SIZE = 5000;
    private static int d;

   
    /**
     * Constructor for object of class DaryHeap.
     * The time complexity is O(n).
     * The space complexity is O(n).
     * @param fileName The PATH of the file from which the heap is built 
     */
    public DaryHeap (String fileName, int dNum)
    {
        // initializes an empty max heap with maximum capacity
        heapEndPointer = 0;
        heap = new int[MAX_SIZE];
        d = dNum;
 
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = br.readLine()) != null && heapEndPointer < MAX_SIZE) {
                    
                try {
                    heap[heapEndPointer]  = Integer.parseInt(line);
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

    public void buildHeap() {
        for (int i = (heapEndPointer - 1) / d; i >= 0; i--)
            restoreDown(heapEndPointer, i);
    }
 
    public void insert(int element) {
        heap[heapEndPointer - 1] = element;
        restoreUp(heapEndPointer - 1, d);
    }
 
    public int extractMax() {
        int max = heap[0];
        heap[0] = heap[heapEndPointer - 1];
        restoreDown(heapEndPointer - 1, 0);
        return max;
    }
 
    private void restoreDown(int len, int index) {
        int[] child = new int[d + 1];
        while (true) {
            for (int i = 1; i <= d; i++)
                child[i] = (d * index + i) < len ? (d * index + i) : -1;
 
            int maxChild = -1, maxChildIndex = 0;
            for (int i = 1; i <= d; i++) {
                if (child[i] != -1 && heap[child[i]] > maxChild) {
                    maxChildIndex = child[i];
                    maxChild = heap[child[i]];
                }
            }
 
            if (maxChild == -1)
                break;
 
            if (heap[index] < heap[maxChildIndex])
                swap(index, maxChildIndex);
 
            index = maxChildIndex;
        }
    }
 
    private void restoreUp(int index, int d) {
        int parent = (index - 1) / d;
        while (parent >= 0) {
            if (heap[index] > heap[parent]) {
                swap(index, parent);
                index = parent;
                parent = (index - 1) / d;
            } else
                break;
        }
    }
 
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public void print() {
        for (int i = 0; i < heapEndPointer; i++) {
            System.out.print(heap[i]);
        }
        System.out.println(); // for new line
    }



    public static void main(String[] args) {

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
    
        System.out.println("Please enter the file PATH:");
        String str = scan.next();

        // Initialising heap
        DaryHeap dHeap = new DaryHeap (str, d);

        // Displaying message for better readability
        
        
        System.out.println("Built Heap: ");
        dHeap.print();
        
        dHeap.buildHeap();
        System.out.println("The D-ary Heap after sorting looks like: ");
        dHeap.print();
        
        int element = 3;
        dHeap.insert(element);
        heapEndPointer++;
        
        System.out.println("\n\nHeap after insertion of " + element + ": ");
        dHeap.print();
        
        System.out.println("\n\nExtracted max is " + dHeap.extractMax());
        heapEndPointer--;
        
        System.out.println("\n\nHeap after extract max: ");
        dHeap.print();
    }
}