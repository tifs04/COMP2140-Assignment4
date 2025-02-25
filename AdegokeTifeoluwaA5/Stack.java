/*******
 COMP 2140 Fall 2024
 Assignment 5 Question 4 Solution
 Tifeoluwa Adegoke
 *******/
public class Stack {
    private int[] data;
    private int MAX_LENGTH;
    private int indexTop;



    public Stack(int MAX_LENGTH){
        this.data = new int[MAX_LENGTH];
        this.MAX_LENGTH = MAX_LENGTH;
        indexTop = -1;
    }

    
    /**
     * Adds a new element to the top of the stack. If the stack reaches its maximum capacity,
     * it doubles the size of the array to accommodate more elements. 
     * @param toAdd The element to be added to the stack.
     */

    public void push(int toAdd){
        //edge case 1: we are adding to a full array
        if(indexTop == MAX_LENGTH-1){  
            // Create a new array that's twice the size
            MAX_LENGTH *= 2;
            int[] newData = new int[MAX_LENGTH];
            for(int i = 0; i < data.length; i++){
                newData[i] = data[i];
            }
            data = newData;
            indexTop+=1;
            data[indexTop] = toAdd;
        }else{
            indexTop+=1;
            data[indexTop] = toAdd;
        }
        
    }

    /**
     * Removes and returns the element at the top of the stack. 
     * 
     * @return The element removed from the top of the stack, or -1 if the stack is empty.
     */
    public int pop(){
        int toRemove = -1;
        if(indexTop != -1){ //checks to see the array is not empty
            toRemove = data[indexTop];
            indexTop -= 1;
        }
        return toRemove;
    }

    /**
     * Retrieves the top element of the stack without removing it.
     *
     * @return The top element of the stack, or -1 if the stack is empty.
     */
    public int top(){
        int seeTop = -1;
        if(indexTop != -1){
            seeTop = data[indexTop];
        }
        return seeTop;
    }

    public boolean isEmpty(){
        return indexTop == -1; //Determines if the stack is empty 
    }

    /**
     * Determines if the stack is full by comparing the current `indexTop` with `MAX_LENGTH - 1`. 
     * @return true if the stack is full, false otherwise.
     */

    public boolean isFull(){
        return indexTop == (MAX_LENGTH-1); 
    }

    public static boolean isDirected(int[][] adj){
        boolean isDirected = true;
        for(int i = 0; i < adj.length; i++ ){
            for(int j = 0; j < adj[i].length; j++){
                if(adj[i][j]!=0 && adj[j][i] != adj[i][j]){
                    return false;
                }
            }
        }

        return isDirected;
    }


}
