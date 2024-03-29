import java.util.Scanner;
import java.io.*;
import java.lang.System;

public class DaryHeap {

    //class variables
    private static int[] heap;
    private static int heapEndPointer;
    private static final int MAX_SIZE = 5000;
    private static final int PLACEHOLDER_NUM = Integer.MIN_VALUE;
    private static int d;

    //formatting
    private static final String BLACK = "\u001B[30m";  
    private static final String RED = "\u001B[31m"; 
    private static final String GREEN = "\u001B[32m";  
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE_DARK = "\u001B[34m"; 
    private static final String PURPLE = "\u001B[35m"; 
    private static final String BLUE_LIGHT = "\u001B[36m";
    private static final String BOLD = "\033[0;1m";
    private static final String UNDERLINE = "\033[0;4m";
    private static final String RESET = "\033[0m";
   
    /**
     * Constructor for objects of class DaryHeap.
     * Time complexity is O(n).
     * @param fileName The PATH of the file from which the heap is built.
     * @param dNum The number that decides how many children each parent could have.
     */
    private DaryHeap (String fileName, int dNum) {

        //initializes an empty max heap with maximum capacity
        heapEndPointer = 0;
        heap = new int[MAX_SIZE+1];
        d = dNum;

        //reads input file and places numbers into heap array
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null && heapEndPointer < MAX_SIZE) {
                    
                try { //if there is more than just a number in each line then will return an error
                    heap[heapEndPointer] = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println(YELLOW + "Invalid integer input in line: " + RESET + lineNumber + YELLOW + "\nReplaced it with '0'." + RESET);
                }
                lineNumber ++;
                heapEndPointer ++;
            }

            br.close(); //closes buffer file

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (heapEndPointer == MAX_SIZE)
            System.out.println(YELLOW + "Numbers in file exceeded maximum size allowed (" + RESET + MAX_SIZE + YELLOW + "). \nCopied only the first "
            + RESET + MAX_SIZE + YELLOW + " numbers in the file." + RESET);
        }
        
    /**
     * This method turns an unsorted array into a d-ary heap.
     * Time complexity is O(nlogdn).
     */
    private void buildHeap () {
        for (int i = (heapEndPointer - 1) / d; i >= 0; i--)
            maxHeapify(heapEndPointer, i);
    }

    
    /**
     * This method turns an creates a d-ary heap..
     * Time complexity is O(nlogdn).
     * @param scan The scanner which scans the user's input.
     */
    private static DaryHeap newHeap (Scanner scan)
    {
        System.out.println ("Please enter a number that is the 'd' wanted and then press enter: ");
        int d = scan.nextInt();
        
        if (d <= 0) {
            System.out.println(YELLOW + "'d' entered must be larger than zero. Since the 'd' inputted was " + d + " which is not larger than zero then it was changed to one." + RESET);
            d = 1;
        }
    
        System.out.println("Please enter the file PATH and then press enter:" + RESET);
        String str = scan.next();

        //initialises heap
        DaryHeap dHeap = new DaryHeap (str, d); //O(n)
        dHeap.buildHeap(); //O(nlogdn)
    
        return dHeap;
    }

    /**
     * This method turns a regular heap into a maximum heap.
     * Time complexity is O(dlogdn).
     * @param len The length of the heap.
     * @param index The index from which to start sorting.
     */
    private void maxHeapify(int len, int index) {
        int child = getMaxChild(index, len); //O(d)
    
        while (child != -1) { //logdn jumps
            if (heap[child] > heap[index])
                swap(index, child);
            index = child;
            child = getMaxChild(index, len); //O(d)
        }
    }
    
    /**
     * This method turns a regular heap into a maximum heap.
     * Time complexity is O(d).
     * @param len The length of the heap.
     * @param index The index from which to start checking children nodes.
     * @return The index of the maximum child of a specific parent
     */
    private int getMaxChild(int index, int len) {
        int maxChildIndex = -1;
        int maxChildValue = PLACEHOLDER_NUM;
    
        for (int i = 1; i <= d; i++) { //O(d)
            int childIndex = d * index + i;
    
            if (childIndex < len && heap[childIndex] > maxChildValue) { 
                maxChildIndex = childIndex;
                maxChildValue = heap[childIndex];
            }
        }
        return maxChildIndex;
    }
    
    /**
     * This method swaps two numbers in the heap.
     * Time complexity is O(1).
     * @param i The first number to be swapped.
     * @param j The second number to be swapped.
     */
    private void swap (int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    /**
     * This method returns the index of the parent of the node at given index.
     * Time complexity is O(1).
     * @param i The index.
     * @return Index of parent of node at index i.
     */
    private int parent (int i) {
        if (i > (1 + d)) //if the index's parent is not the head of the heap
            return (i - 1) / d;
        else 
            return 0;
    }
    /**
     * This method inserts a number into heap and then reorganizes it in order to stay a maximum heap.
     * If the heap has reached maximum size then the number given will not be added and the user would be duly notified of that fact.
     * Time complexity is O(logdn).
     * @param num The number to be added.
     */
    public void insert (int num) {
        if (heapEndPointer < MAX_SIZE) {
            heap[heapEndPointer] = num;
            heapEndPointer ++;
            for (int i = heapEndPointer; i >=0; i--) {
                if (heap[i] > heap[parent(i)])
                    swap(i, parent(i));
            }
            System.out.println(GREEN + "Number entered was successfully added to heap!" + RESET);
        }
        else {
            System.out.println(YELLOW + "Numbers in file reached maximum size allowed (" + BLACK + MAX_SIZE + YELLOW + "). " +
            "\nTherefore the number inputted was not added to heap. " + RESET);
        }
    }
       
    /**
     * This method removes number in index given from heap and then reorganizes it in order to stay a maximum heap.
     * If the index is invalid then nothing will happen and the user would be duly notified of that fact.
     * Time complexity is O(dlogdn).
     * @param index The index of the number to be removed.
     */
    public void remove (int index) {
       
        //if the index is larger than the number of number in the heap or is a negative number
        if (index >= heapEndPointer || index < 0) { 
            System.out.println(YELLOW + "Index entered (" + BLACK + index + YELLOW + ") is invalid. " +
            "\nTherefore nothing happened. " + RESET);
        }
        else {
            heap[index] =  heap[heapEndPointer - 1];
            heapEndPointer --;
            maxHeapify(heapEndPointer, index);
            System.out.println(GREEN + "The number at the index entered was successfully removed from heap!" + RESET);
        }
    }
    
    /**
     * This method changes the value of a number at a certain index in the heap if the given number is larger than the number at the index.
     * Else, the number does not change.
     * Time complexity is O(logdn).
     * @param index Index of number to be compared and maybe changed to 'k'
     * @param k Number to change number at index if larger
     */
    public void increaseKey (int index, int k) {
        //changes number in index to the value of k
        if (heap[index] < k) {
            heap[index] =  k;
            //fixes heap
            while (index >= d && heap[index] > heap[parent(index)]) {
                swap(index, parent(index));
                index = parent(index);
            }
            //checking if node is larger that the first node
            if (heap[index] > heap[0]) {
                swap(index, 0);
            }
            System.out.println(GREEN + "The number at the index entered was successfully changed to number given!" + RESET);            
        }
        else
            System.out.println(GREEN + "The number at the index entered was larger than number given - therefore it was not changed!" + RESET);            
    }
    
    /** 
     * This method returns the maximum number in the heap.
     * Inevitably, it would be the first parent.
     * Time complexity is O(dlogdn).
     * @return The maximum number
     */
    public int extractMax () {
        //saves maximum number
        int max = heap[0];
        
        //removes the maximum number and replacing it with the last number in the heap
        heap[0] = heap[heapEndPointer - 1];
        heapEndPointer --;
        
        //fixes heap
        maxHeapify(heapEndPointer, 0);
        System.out.println(GREEN + "The maximum number was extracted successfully!" + RESET);            
        return max;
    }
    
    /**
     * This method prints the heap in the form of an array.
     * Time complexity is O(n).
     */
    public void print () {
        int level = 1, index = 1, counter = 0, dCounter = 1;
        if (heapEndPointer > 0) {

            //prints the first level
            System.out.print("\n");
            System.out.println(BOLD + UNDERLINE + "Level 0:\n" + RESET + heap[0]); 
    
            //prints heap in levels according to 'd'
            while (level <= heapEndPointer/d && index < heapEndPointer) {
                System.out.println(BOLD + UNDERLINE + "\nLevel " + level + ":" + RESET); 
                while (counter < d*dCounter && index < heapEndPointer) {
                    System.out.print(heap[index] + "\t");
                    counter++;
                    index++;
                }
                dCounter = counter;
                counter = 0;
                level++;
            }
            
            //prints the leftover leaves
            if (index < heapEndPointer) {
                System.out.println(BOLD + UNDERLINE + "\nLevel " + level + ": " + RESET); 
                while (index < heapEndPointer) {
                    System.out.print(heap[index] + "\t");
                    index++;
                }
            }
            System.out.println(); //prints new line at the end of the heap
        }
        else
            System.out.println(YELLOW + "Empty heap." + RESET); //prints new line at the end of the heap
    }

    /**
     * Main fuction.
     */
    public static void main (String[] args) {

        Scanner scan = new Scanner(System.in);
        int flag = 1;

        System.out.println (BOLD + BLUE_DARK + "\nREAD BEFORE USING PROGRAM - FOR THE USER'S INFORMATION:\n" + RESET + 
         "This program " + UNDERLINE + "does not " + RESET + "check that the \"d\" entered is a valid number.\n" +
         "This program " + UNDERLINE + "does not " + RESET + "check that the file PATH entered is correct and that the file residing there is not empty.\nThe file should end in '.txt'.\n" +
         "Each number in the file should be on a different line than the one before it. \nBut, if there is a space or a character that is not a number on the line, then the program will substitute a zero insead.\n" + 
          BLUE_LIGHT + "If the file given is built differently then specified then the program might not work as wanted." + RESET + BOLD +
         "\n\nIf these instructions are not clear or acceptable to you, please - \n" + PURPLE + "DO NOT use the program for it is not meant for such as you!\n" + RESET +
          GREEN + "\nIf you would like to continue, please type 1 and then enter. Else, type 0 and then enter." + RESET);
        
         if (scan.nextInt() != flag) {
            System.out.println (UNDERLINE + RED + "Exiting program now."  + RESET);
            System.exit (0);
        }

        //initialises heap
        DaryHeap dHeap = newHeap(scan); 

        //displays message for better readability
        if (heapEndPointer > 0)
            System.out.println(BOLD + "Heap built: " + RESET);
        dHeap.print();
        
        System.out.println("Would you like to do a few actions with the heap above that you built?\n" +
        "If yes, type 1 and then enter. If not, type 0 and then enter.");

        if (scan.nextInt() != flag) {
            System.out.println (UNDERLINE + RED + "Exiting program now."  + RESET);
            System.exit (0);
        }

        //processes actions on the heap
        System.out.println("It has been understood that you wish to input commands." + 
        "\nThe program will continue to prompt you for new commands until you type \"exit\" or an illegal command.");

        while (flag == 1) {
            System.out.println ("Please enter command while making sure that it is spelt correctly and then press enter.");
            String word = scan.next();
            word = word.toLowerCase();
    
            switch (word) {
                case "insert":
                    System.out.println ("Please enter the number to insert:");
                    dHeap.insert(scan.nextInt());
                    break;
                case "remove":
                    System.out.println ("Please enter the index of the number to remove: (index numbers start at 0)");
                    dHeap.remove(scan.nextInt());
                    break;
                case "increasekey":
                    System.out.println ("Please enter the index of the number to compare and maybe change and the number you wish to compare and maybe change it to, respectivly:");
                    dHeap.increaseKey(scan.nextInt(), scan.nextInt());   
                    break;
                case "extractmax":
                    int max = dHeap.extractMax();  
                    System.out.println ("The maximum number is " + max + ".");
                    break;
                    case "print":
                    dHeap.print();
                    break;
                case "newheap":
                    System.out.println (RED + "The old heap will be deleted and all its contents will be lost." + RESET + "\nAre you sure you wish to continue? Type 1 if yes and 0 if not.");
                    if (scan.nextInt() == flag)
                    {
                        dHeap = newHeap(scan); 
                        System.out.println ("New heap created. \nWould you like to do a few actions with the heap above that you built?\n"
                        + "If yes, type 1 and then enter. If not, type 0 and then enter.");
                        if (scan.nextInt() != flag) {
                            System.out.println (UNDERLINE + RED + "Exiting program now."  + RESET);
                            flag = 0;
                        }
                    } 
                    break;
                case "exit":
                    System.out.println (UNDERLINE + RED + "Exiting program now."  + RESET);
                    flag = 0;
                    break;
                default:
                    System.out.println(UNDERLINE + RED + "Illegal command. Exiting program now."  + RESET);
                    flag = 0;
                    break;
            }
        }
        //closes scanner
        scan.close();

    }
}