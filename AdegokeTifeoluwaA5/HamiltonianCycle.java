/*******
 COMP 2140 Fall 2024
 Assignment 5 Question 4 Solution
 Tifeoluwa Adegoke
 *******/
import java.util.ArrayList;

public class HamiltonianCycle {
    public static void main(String[] args){
        if(args.length > 1){
            WeightedGraph filledGraph = new WeightedGraph(args[0]); 
            int[][]  adjMatrix = filledGraph.getAdjMatrix(); //the adjacent matrix representing the graph created from the file
            findCycle(args[0], filledGraph, adjMatrix);
        }
    }

    /**
     * Finds cycles in a graph using depth-first search and identifies all possible cycles.
     *
     * @param fileName The name of the file containing the graph data.
     * @param fullWeightedGraph An instance of the WeightedGraph class representing the graph.
     * @param fullAdjMatrix The adjacency matrix of the graph.
     */
    private static void findCycle(String fileName, WeightedGraph fullWeightedGraph, int[][]fullAdjMatrix){
        int adjMatrixSize = fullWeightedGraph.getSize(fileName);
        ArrayList<Integer> currCycle = new ArrayList<>(); //inner list containing a cycle
        ArrayList<ArrayList<Integer>> possibleCycles= new ArrayList<>(); //outerlist

        Stack currNodes = new Stack(adjMatrixSize);
        Stack currNodeNeighbours = new Stack(adjMatrixSize);
        boolean[] visitedNodes = new boolean[adjMatrixSize]; //initialize the boolean array
    
        int CURR_NODE = 0;
        currNodes.push(CURR_NODE);
        currCycle.add(CURR_NODE);
        visitedNodes[CURR_NODE]=true;

        //get all the neighbours of 0 and push them unto the neighbours stack
        addNeighbours(CURR_NODE, fullAdjMatrix, visitedNodes, currNodeNeighbours);

        while(!currNodes.isEmpty() ){
            if(!currNodeNeighbours.isEmpty()){
                int neighbour = currNodeNeighbours.pop();
            
                if(neighbour == currNodes.top() && currCycle.size() > 2){
                    currCycle.add(CURR_NODE);
                    possibleCycles.add(new ArrayList<>(currCycle));   
                    currCycle.remove(currCycle.size() -1 );
                }else if(!visitedNodes[neighbour]){
                    visitedNodes[neighbour] = true;
                    currNodes.push(neighbour);
                    currCycle.add(neighbour);
                    for(int i = 0;i < fullAdjMatrix[neighbour].length; i++){
                        if(fullAdjMatrix[neighbour][i] != 0 && !visitedNodes[i]){ //checks if edge is valid
                            currNodeNeighbours.push(i); 
                        }
                    }
                    
                }
            }else{
                backTrack(currNodes, visitedNodes, currCycle);
            }
        }
        
        
        minCycle(possibleCycles, fullAdjMatrix);

    }

    /**
     * Adds the unvisited neighboring nodes of a given node to a stack.
     *
     * @param node The current node whose neighbors are being processed.
     * @param adMatrix The adjacency matrix representing the graph.
     * @param visitedNodes The array indicating whether each node has been visited.
     * @param neighboursStack The stack where neighboring nodes will be pushed.
     */
    private static void addNeighbours(int node, int[][] adMatrix, boolean[] visitedNodes, Stack neighboursStack) {
        for (int i = 0; i < adMatrix[node].length; i++) {
            if (adMatrix[node][i] != 0 && !visitedNodes[i]) {
                neighboursStack.push(i);
            }
        }
    }
    
    /**
     * Backtracks by popping the last visited node from the stack and marking it as unvisited.
     * 
     * @param currNodes The stack that keeps track of the current path in the graph.
     * @param visitedNodes An array where each entry indicates if the corresponding node has been visited (`true`) or not (`false`).
     * @param currCycle The current list of nodes forming a path in the graph.
     */
    private static void backTrack(Stack currNodes, boolean[] visitedNodes, ArrayList<Integer> currCycle) {
        int lastVisited = currNodes.pop();
        visitedNodes[lastVisited] = false;
        currCycle.remove(currCycle.size() - 1);
    }

    /**
     * Finds the minimum weight Hamiltonian cycle from a list of cycles.
     *
     * @param list The list of possible Hamiltonian cycles.
     * @param graph The adjacency matrix representing the weighted graph.
     */

    private static void minCycle(ArrayList<ArrayList<Integer>> list, int[][] graph){
        if(list.isEmpty()){
            System.out.println("This graph does not contain Hamiltonian cycles.")
        }else{
            int minWeigth = 0;
            ArrayList<Integer> minCycle = new ArrayList<>();
            for(int i=0; i < list.size(); i ++){
                ArrayList<Integer> row = list.get(i);
                for(int j = 0; j<row.size(); j++){
                    int cycleWeigth = graph[row.get(j)][row.get(j+1)];
                    if(cycleWeigth < minWeigth){
                        minCycle = row;
                        minWeigth = cycleWeigth;
                    }

                }
            }

            System.out.println("This graph contains Hamiltonian cycles");
            System.out.print("A minimum cycle is [ ");
            for(int i = 0; i < minCycle.size(); i++){
                System.out.print(minCycle.get(i));
            }
            System.out.print("]");
            System.out.println("The minimal weight of a Hamiltonian cycle is " + minWeigth);
        }
        
    }


}