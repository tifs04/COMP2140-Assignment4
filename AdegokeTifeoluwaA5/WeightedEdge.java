/*******
 COMP 2140 Fall 2024
 Assignment 5 Question 1 Solution
 Tifeoluwa Adegoke
 *******/
public class WeightedEdge implements Comparable<WeightedEdge>{
    int vertex1;
    int vertex2;
    int weight;
    public WeightedEdge(int vertex1, int vertex2, int weight){
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    /**
     * Compares a weightedEdge to another
     * 
     * @param otherEdg. Instance of WeightedEdge
     * 
     * @return int. Integer representing if this weightedEdge is greater, less than or equal to otherEdg
     */
    public  int compareTo(WeightedEdge otherEdg){
        return Integer.compare(this.weight, otherEdg.weight);
    }
}