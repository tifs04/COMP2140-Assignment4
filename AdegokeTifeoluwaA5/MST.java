/*******
 COMP 2140 Fall 2024
 Assignment 5 Question 3 Solution
 Tifeoluwa Adegoke
 *******/
import java.util.Arrays;

public class MST {
    public static void main(String[] args){
        if(args.length > 0){
            WeightedGraph newWeightedGraph = new WeightedGraph(args[0]); //creates graph from info in file
            int[][]  adMatrix = newWeightedGraph.getAdjMatrix(); //the adjacent matrix representing the graph created from the file
            int adjMatrixSize = newWeightedGraph.getSize(args[0]); //size of the empty graph to be created for the MST
            WeightedGraph mstGraph = new WeightedGraph(adjMatrixSize); //creates the proper size of the empty graph
            int[][] mstMatrix = mstGraph.getAdjMatrix();
            boolean[]  vertexVisited = new boolean[adjMatrixSize];
            newWeightedGraph.createMst(adMatrix, mstMatrix, vertexVisited);
            mstGraph.printGraph();
        }
       
        
    }   
    
}
