/*******
 COMP 2140 Fall 2024
 Assignment 5 Question 1 Solution
 Tifeoluwa Adegoke
 *******/
public class MinPQWeightedEdge {
    WeightedEdge[] priorityArr;
    int availableSpace;
    int ARRAY_LENGTH;

    public MinPQWeightedEdge(){
        ARRAY_LENGTH = 20;
        priorityArr = new WeightedEdge[ARRAY_LENGTH];
        availableSpace = 0;
    }


    /**
     * Inserts a weightedEdge object into a minimum priority queue
     * 
     * @param edgeNum. Weighted object to be inserted into priority array
     * 
     * @return void. 
     */
    public void insert(WeightedEdge edgeNum){
        if(priorityArr == null){  //empty array
            priorityArr[availableSpace] = edgeNum;
            availableSpace++;
        }else{
            
            if(availableSpace < ARRAY_LENGTH){  //the array is not full
                helperInsert(edgeNum);
            }else{
                //double the lenght of the array
                ARRAY_LENGTH *=2;
                WeightedEdge[] newPriorityArr = new WeightedEdge[ARRAY_LENGTH];
                //copy everything in the old array into the new array
                for(int i = 0; i < priorityArr.length; i++){
                    newPriorityArr[i] = priorityArr[i];
                }
                priorityArr = newPriorityArr;
                helperInsert(edgeNum);

            }
        }

       
    }


    /**
     * Checks if there are no entries in the minimum priority queue
     * 
     * @param edgeNum. Weighted object to be inserted into priority array
     * 
     * @return boolean. boolean indicating if the priority queue is empty or not
     */
    public boolean isEmpty(){
        return availableSpace == 0;
    }

     /**
     *Removes and returns the WeightedEdge with the lowest weight from queue
     * 
     * @return minWeight. WeightedEdge object with the lowest weight 
     */
    public WeightedEdge retrieveMin(){
        WeightedEdge minWeight  = priorityArr[0];
        priorityArr[0] = priorityArr[availableSpace-1];
        priorityArr = decreaseArray(priorityArr);
        priorityArr = heapify(priorityArr, 0);
        availableSpace--;
        return minWeight;
    }

    /**
     * prints the weights of all of the edges currently in the minimum priority
     * queue in the order that they appear in the array.
     * 
     */
    public void print(){
        if(priorityArr != null){
            System.out.print("[");
            for(int i = 0; i < priorityArr.length; i++){
                if(i < priorityArr.length-1 ){
                    if(priorityArr[i] != null){
                        System.out.print(priorityArr[i].weight + ", ");
                    }else{
                        System.out.print("null, ");
                    }
                }else{
                    if(priorityArr[i] != null){
                        System.out.print(priorityArr[i].weight);
                    }else{
                        System.out.print("null");
                    }
                }
            }
            System.out.print("]");
        }else{
            System.out.println("Queue is empty");
        }
        
    }


    /**
     * Reduces length of given array by 1
     * 
     * @param arrName. WeightedEdge array to be reduced
     * 
     * @return newWeightedEdges. New  WeightedEdge array containing one less WeightedEdge object
     */
    private WeightedEdge[] decreaseArray(WeightedEdge[] arrName){
        if(arrName == null || arrName.length == 0){
            System.out.println("null");
        }
        WeightedEdge[] newWeightedEdges = new WeightedEdge[arrName.length-1];
        for(int i = 0; i < arrName.length-1; i ++){
            newWeightedEdges[i] = arrName[i];
        }

        return newWeightedEdges;

    }

    /**
     * Transforms a given WeightedEdge[] into a valid heap structure, starting from the specified index.
     *
     * @param weightedArr The array of `WeightedEdge` objects to be heapified.
     * @param index The starting index from which to begin the heapification process.
     * 
     * @return weightedArr The heapified `WeightedEdge` array.
     * 
     */
    private WeightedEdge[] heapify(WeightedEdge[] weightedArr, int index){
        Stack indexStack = new Stack(ARRAY_LENGTH);
        indexStack.push(index);
        while(!indexStack.isEmpty()){
            int i = indexStack.pop();
            int rightChild = (2*i)+2;
            int leftChild = (2*i)+1;
            int smallerEdge = i;
            //check if the children are in the array
            if(rightChild < weightedArr.length && weightedArr[rightChild]!= null){
                //check for violation
                if(weightedArr[smallerEdge].compareTo(weightedArr[rightChild]) > 0){
                    smallerEdge = rightChild;
                }
            }

            if(leftChild < weightedArr.length && weightedArr[leftChild]!= null){
                if(weightedArr[smallerEdge].compareTo(weightedArr[leftChild]) > 0){
                    smallerEdge = leftChild;
                }
            }

            //do swap only if necessary
            if(smallerEdge != i){
                WeightedEdge tempHolder = weightedArr[i];
                weightedArr[i] = weightedArr[smallerEdge];
                weightedArr[smallerEdge] = tempHolder;
                indexStack.push(smallerEdge);
            }

        }

        return weightedArr;
    }

    /**
     * Inserts a `WeightedEdge` into the priority array and maintains the heap property by "bubbling up" the new edge if necessary.
     *
     * @param newEdge The `WeightedEdge` object to be inserted.
     */
    private void helperInsert(WeightedEdge newEdge){
        int currPosn = availableSpace;
        WeightedEdge tempHolder = null;
        //insert into the next available space in the array
        priorityArr[currPosn] = newEdge;
        //bubble up
        int parentIndex = (currPosn - 1)/2;
        while(priorityArr[currPosn].weight < priorityArr[parentIndex].weight && currPosn != 0){
            //do switch
            tempHolder = priorityArr[currPosn];
            priorityArr[currPosn] = priorityArr[parentIndex]; 
            priorityArr[parentIndex] = tempHolder;
            //update parent
            currPosn = parentIndex;
            parentIndex =(currPosn - 1)/2;
        }
        availableSpace++;
    }

}
