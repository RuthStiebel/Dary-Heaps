import java.util.Scanner;
import java.io.*;
import java.lang.System;

public class DaryHeap {

    private static int[] heap;
    private static int heapEndPointer;
    private static final int MAX_SIZE = 5000;
    private int dPlace = 1;
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

    public static void buildHeap() {
        for (int i = (heapEndPointer - 1) / d; i >= 0; i--)
            restoreDown(heapEndPointer, i);
    }
 
    public static void insert(int element) {
        heap[heapEndPointer - 1] = element;
        restoreUp(heapEndPointer - 1, d);
    }
 
    public static int extractMax() {
        int max = heap[0];
        heap[0] = heap[heapEndPointer - 1];
        restoreDown(heapEndPointer - 1, 0);
        return max;
    }
 
    public static void restoreDown(int len, int index) {
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
 
    public static void restoreUp(int index, int d) {
        int parent = (index - 1) / d;
        while (parent >= 0) {
            if (heap[index] > heap[parent]) {
                swap(heap, index, parent);
                index = parent;
                parent = (index - 1) / d;
            } else
                break;
        }
    }
 
    public static void swap( int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}

public static void main(String[] args) {

    buildHeap();

    System.out.println("Built Heap: ");
    for (int i = 0; i < heapEndPointer; i++)
        System.out.print(heap[i] + " ");

    int element = 3;
    insert(element);
    heapEndPointer++;

    System.out.println("\n\nHeap after insertion of " + element + ": ");
    for (int i = 0; i < heapEndPointer; i++)
        System.out.print(heap[i] + " ");

    System.out.println("\n\nExtracted max is " + extractMax());
    heapEndPointer--;

    System.out.println("\n\nHeap after extract max: ");
    for (int i = 0; i < heapEndPointer; i++)
        System.out.print(heap[i] + " ");
}