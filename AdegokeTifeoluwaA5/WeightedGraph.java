/*******
 COMP 2140 Fall 2024
 Assignment 5 Question 2 Solution
 Tifeoluwa Adegoke
 *******/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WeightedGraph {
    int numOfVertice;
    int[][] adjMatrix;

    public WeightedGraph(int numOfVertice){
        this.numOfVertice = numOfVertice;
        adjMatrix = new int[numOfVertice][numOfVertice];
    }

    /**
     * Constructs a weighted graph by reading data from a file and populates the adjacency matrix.
     *
     * @param fileName The name of the file containing the data.
     */
    public WeightedGraph(String fileName){
        try{
            BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
            String newLine = buffReader.readLine(); //reads the first line in the file
            int arrSize = Integer.parseInt(newLine);
            adjMatrix = new int[arrSize][arrSize];
            newLine = buffReader.readLine(); //read the second line in the file
            while(newLine != null){
                String[] lineInFile = newLine.split(" ");
                int vertex1 = Integer.parseInt(lineInFile[0]);
                int vertex2 = Integer.parseInt(lineInFile[1]);
                int weight = Integer.parseInt(lineInFile[2]);
                adjMatrix[vertex1][vertex2] = weight;
                adjMatrix[vertex2][vertex1] = weight;
                newLine = buffReader.readLine();
            }
        }catch(IOException ioe){
            System.out.println("An error has occured");
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * Reads the size of the graph from a file.
     *
     * @param fileName. The name of the file containing the graph size.
     * @return arrSize. The size of the graph read from the file, or -1 if an error occurs.
     */
    public int getSize(String fileName){
        int arrSize = -1;
        try{
            BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
            String newLine = buffReader.readLine(); //reads the first line in the file
            arrSize = Integer.parseInt(newLine);
            
            
        }catch(IOException ioe){
            System.out.println("An error has occured");
            System.out.println(ioe.getMessage());
        }

        return arrSize;
    }

    public int[][] getAdjMatrix(){
        return this.adjMatrix; //returns the size of the matrix
    }

    /**
     * Prints the edges of the graph in the adjacency matrix format along with their weights.
     */
    public void printGraph(){
        System.out.println("Here is a minimal spanning tree:");
        for(int i=0; i < numOfVertice; i++){
            for(int j= 0; j < numOfVertice; j++){
                if(this.adjMatrix[i][j] != 0){
                    System.out.println("Edge ("+ i + ", " + j + "), weight " + this.adjMatrix[i][j]);
                }
            }
        }
        System.out.print("The minimal weight of a spanning tree is " + calcWeight(this.adjMatrix));
    }

    /**
     * Calculates the total weight of the graph by summing the weights of all edges in the adjacency matrix.
     *
     * @param matrix The adjacency matrix representing the graph.
     * @return sum The total weight of all edges in the graph.
     */

    private int calcWeight(int[][] matrix){
        int sum = 0;
        for(int i=0; i < numOfVertice; i++){
            for(int j= 0; j < numOfVertice; j++){
                if(this.adjMatrix[i][j] != 0){
                    sum += this.adjMatrix[i][j];
                }
            }
        }
        return sum;
    }

     /** * Creates a minimum spanning tree (MST) using Prim's algorithm on the given weighted graph. 
      * 
      * @param filledGraph The adjacency matrix representing the weighted graph. 
      * @param emptyGraph The matrix where the MST will be stored. 
      * @param toBeVisited A boolean array indicating which vertices have been visited. 
      */ 
    public void createMst( int[][] filledGraph, int[][] emptyGraph, boolean[] toBeVisited){
        int CURR_VERTEX = 0;
        WeightedEdge minEdge = null;
        MinPQWeightedEdge priorityEdge = new MinPQWeightedEdge(); //creates a priority queue
        
        toBeVisited[CURR_VERTEX] = true;
        while(!isFull(toBeVisited)){
            ///traverse the weighted graph and see the nodes connected to the currVertex
           
                for(int i = 0; i < filledGraph[CURR_VERTEX].length; i++){
                    if(filledGraph[CURR_VERTEX][i] != 0){
                        //create a new weightedEdge object
                        WeightedEdge toInsert = new WeightedEdge(CURR_VERTEX, i, filledGraph[CURR_VERTEX][i]);
                        //add it to a min priority queue
                        priorityEdge.insert(toInsert);
                    }
                }
                minEdge = priorityEdge.retrieveMin();//get the min edge
               
                while(toBeVisited[minEdge.vertex2]){ //as long the second vertex has been visited
                    minEdge = priorityEdge.retrieveMin();
                }

                //Check if the vertex has not been visited
                if(!toBeVisited[minEdge.vertex2]){
                    //add this node and it's weight to the mst
                    emptyGraph[minEdge.vertex1][minEdge.vertex2] = minEdge.weight;

                    //mark the second vertex as visited
                    toBeVisited[minEdge.vertex2] = true;
                    
                    //increase the CURR_VERTEX
                    CURR_VERTEX = minEdge.vertex2;
                }
        }
    }
    public int getWeight(int from, int to) {
        return adjMatrix[from][to];   //returns the weight of edge between the specificied vertexes 
    }


    /**
     * Checks if all elements in a boolean array are `true`.
     *
     * @param booleanArr The boolean array to check.
     * @return arrFull `true` if all elements in the array are `true`, otherwise `false`.
     */
    private boolean isFull(boolean[] booleanArr){
        boolean arrFull = true;
        
        for(int i = 0; i < booleanArr.length; i++){
            if(!booleanArr[i]){
                arrFull = false;
            }
        
        }

        return arrFull;
    }

    
}
