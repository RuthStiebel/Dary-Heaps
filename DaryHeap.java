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
            while ((line = br.readLine()) != null && heapEndPointer < MAX_SIZE) {
                    
                try { //if there is more than just a number in each line then will return an error
                    heap[heapEndPointer] = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println(BOLD + YELLOW + "Invalid integer input in line: " + BLACK + line + RESET);
                }
                
                heapEndPointer ++;
            }

            br.close(); //closes buffer file

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (heapEndPointer == MAX_SIZE)
            System.out.println(BOLD + YELLOW + "Numbers in file exceeded maximum size allowed (" + BLACK + MAX_SIZE + YELLOW + "). \nCopied only the first "
            + BLACK + MAX_SIZE + YELLOW + " numbers in the file." + RESET);
    }

    /**
     * This method turns an unsorted array into a d-ary heap.
     */
    private void buildHeap() {
        for (int i = (heapEndPointer - 1) / d; i >= 0; i--)
            maxHeap(heapEndPointer, i);
    }

    /**
     * This method turns a regular heap into a maximum heap.
     * @param len The length of the heap.
     * @param index The index from which to start sorting.
     */
    private void maxHeap(int len, int index) {

        //initializes an array with maximum amount of leaves per one parent
        int[] children = new int[d + 1];

        //sorts the heap
        while (true) {

            //places into children array the children values - the last child will get the value of PLACEHOLDER_NUM
            for (int i = 1; i <= d; i++) 
                children[i] = (d * index + i) < len ? (d * index + i) : PLACEHOLDER_NUM;
 
            //sortes the children's array
            int maxChild = PLACEHOLDER_NUM, maxChildIndex = 0;
            for (int i = 1; i <= d; i++) { 
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
 
                //swaps values when needed
                if (heap[index] < heap[maxChildIndex])
                swap(index, maxChildIndex);
                
                index = maxChildIndex;
            }
        }
        
    /**
     * This method swaps two numbers in the heap.
     * @param i The first number to be swapped.
     * @param j The second number to be swapped.
     */
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
        
    /**
     * This method inserts a number into heap and then reorganizes it in order to stay a maximum heap.
     * If the heap has reached maximum size then the number given will not be added and the user would be duly notified of that fact.
     * @param num The number to be added.
     */
    public void insert(int num) {
        if (heapEndPointer < MAX_SIZE) {
            heap[heapEndPointer] = num;
            heapEndPointer ++;
            for (int i = 1; i <= heapEndPointer; i++ ) {
                if (heap[heapEndPointer] > heap[heapEndPointer-i])
                    swap(heapEndPointer, heapEndPointer-i);
            }
            System.out.println(GREEN + "Number entered was successfully added to heap!" + RESET);
        }
        else {
            System.out.println(BOLD + YELLOW + "Numbers in file reached maximum size allowed (" + BLACK + MAX_SIZE + YELLOW + "). " +
            "\nTherefore the number inputted was not added to heap. " + RESET);
        }
    }
       
    /**
     * This method removes number in index given from heap and then reorganizes it in order to stay a maximum heap.
     * If the index is invalid then nothing will happen and the user would be duly notified of that fact.
     * @param index The index of the number to be removed.
     */
    public void remove (int index) {
       
        //if the index is larger than the number of number in the heap or is a negative number
        if (index >= heapEndPointer || index < 0) { 
            System.out.println(BOLD + YELLOW + "Index entered (" + BLACK + index + YELLOW + ")is invalid. " +
            "\nTherefore nothing happened. " + RESET);
        }
        else {
            heap[index] =  heap[heapEndPointer - 1];
            heapEndPointer --;
            maxHeap(heapEndPointer, 0);
            System.out.println(GREEN + "The number at the index entered was successfully removed from heap!" + RESET);
        }
    }
    
    /**
     * This method changes the value of a number at a certain index in the heap if the given number is larger than the number at the index.
     * Else, the number does not change.
     * @param index Index of number to be compared and maybe changed to 'k'
     * @param k Number to change number at index if larger
     */
    public void increaseKey (int index, int k) {
        //changes number in index to the value of k
        if (heap[index] < k) {
            heap[index] =  k;
            //fixes heap
            int i = 0;
            while (heap[index] > heap[index-i]) {
                swap(index, index-i);
                i++;
                System.out.println(GREEN + "The number at the index entered was successfully changed to number given!" + RESET);            
            }
        }
        else
            System.out.println(GREEN + "The number at the index entered was larger than number given - therefore it was not changed!" + RESET);            
    }
    
    /** 
     * This method returns the maximum number in the heap.
     * Inevitably, it would be the first parent.
     * @return The maximum number
     */
    public int extractMax() {
        //saves maximum number
        int max = heap[0];
        
        //removes the maximum number and replacing it with the last number in the heap
        heap[0] = heap[heapEndPointer - 1];
        heapEndPointer --;
        
        //fixes the heap
        maxHeap(heapEndPointer, 0);
        System.out.println(GREEN + "The maximum number was extracted successfully!" + RESET);            
        return max;
    }
    
    /**
     * This method prints the heap in the form of an array.
     */
    public void print() {
        int level = 1, index = 1, counter = 0;
        //prints the first level
        System.out.print("\n");
        System.out.println(BOLD + UNDERLINE + "Level 0:\n" + RESET + heap[0]); 

        //prints heap in levels according to 'd'
        while (level < heapEndPointer/d) {
            System.out.println(BOLD + UNDERLINE + "\nLevel " + level + ":" + RESET); 
            while (counter < d*level && index < heapEndPointer) {
                System.out.print(heap[index] + "\t");
                counter++;
                index++;
            }
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

    /**
     * Main fuction.
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println (BOLD + BLUE_DARK + "\nREAD BEFORE USING PROGRAM - FOR THE USER'S INFORMATION:\n" + RESET + 
         "This program " + UNDERLINE + "does not " + RESET + "check that the \"d\" entered is a valid number.\n" +
         "This program " + UNDERLINE + "does not " + RESET + "check that the file PATH entered is correct and that the file residing there is not empty.\n" +
         "Each number in the file should be on a different line than the one before it, with no other symbols or letters.\n" + 
          BLUE_LIGHT + "If the file given is built differently then specified then the program will glitch." + RESET + BOLD +
         "\n\nIf these instructions are not clear or acceptable to you, please - \n" + PURPLE + "DO NOT use the program for it is not meant for such as you!\n" + RESET +
          GREEN + "\nIf you would like to continue, please type 1 and then enter." + RESET);
        
         if (scan.nextInt() != 1) {
            System.out.println (UNDERLINE + RED + "Exiting program now."  + RESET);
            System.exit (0);
        }

        System.out.println ("Please enter a number that is the 'd' wanted and then press enter: ");
        int d = scan.nextInt();
    
        System.out.println("Please enter the file PATH and then press enter:" + RESET);
        String str = scan.next();

        //initialises heap
        DaryHeap dHeap = new DaryHeap (str, d);
        dHeap.buildHeap();

        //displays message for better readability
        System.out.println(BOLD + "Heap built: " + RESET);
        dHeap.print();
        
        System.out.println("Would you like to do a few actions with the heap above that you built?\n" +
        "If yes, type 1 and then enter.");

        if (scan.nextInt() != 1) {
            System.out.println (UNDERLINE + RED + "Exiting program now."  + RESET);
            System.exit (0);
        }

        //processes actions on the heap
        int flag = 1;
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
                    System.out.println ("Please enter the index of the number to remove:");
                    dHeap.remove(scan.nextInt());
                    break;
                case "increasekey":
                    System.out.println ("Please enter the index of the number to increase and the number by which to increase it by, respectivly:");
                    dHeap.increaseKey(scan.nextInt(), scan.nextInt());   
                    break;
                case "extractmax":
                    dHeap.extractMax();  
                    break;
                case "print":
                    dHeap.print();
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