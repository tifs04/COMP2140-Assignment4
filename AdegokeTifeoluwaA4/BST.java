/*******
 COMP 2140 Fall 2024
 Assignment 4 Question 1 Part A Solution
 Tifeoluwa Adegoke
 *******/
import java.util.ArrayList;
public class BST implements GameTree {
    Node root;
    int START_NUM = 1;

    public BST(){
        this.root = null;
    }

    /*
     * Checks if the word is found in the tree, if it is it increases the frequency of the word
     * 
     * If the word is not found in the tree, it adds the word to the tree
     * 
     * @param String word. The word to be added to the tree
     * 
     * 
     */
    public void addWord(String word) {
        Node currNode = null;
        Node parentNode = null;
            if(root == null){
                KeyValPair rootKeyVal = new KeyValPair(word, START_NUM);
                root = new Node(rootKeyVal);
                //Node parentNode = root;
            }else{
                currNode = root;
                
                if(containsWord(word)){
                    addFrequency(word, START_NUM);
                }else{ //adding a new word to the tree
                    while(currNode!=null){
                        parentNode = currNode;
                        if(currNode.keyVal.word.compareToIgnoreCase(word) > 0){  //if greater than num go left
                            currNode = currNode.left;
                        }else {
                            currNode = currNode.right;
                        }
                    }
                    KeyValPair keyValPair = new KeyValPair(word, START_NUM);
                    Node newNode = new Node(keyValPair);
                    newNode.parent = parentNode;
                    if(parentNode.keyVal.word.compareToIgnoreCase(word) > 0){
                        parentNode.left = newNode;
                    }else{
                        parentNode.right = newNode;
                    }
                }     
                
            }
    }

    /*
     * Strictly Checks if the word is the tree.
     * 
     * @param String word. The is the word that needs to be checked 
     * 
     * @return boolean isFound, confirming if the word is found in the tree or not
     */
    public boolean containsWord(String word) {
        Node currentNode = root;
        boolean isFound = false;
        while(currentNode != null && !isFound){
            if(currentNode.keyVal.word.compareToIgnoreCase(word) > 0){
                currentNode = currentNode.left;
            }else if(currentNode.keyVal.word.compareToIgnoreCase(word) < 0){
                currentNode = currentNode.right;
            }else{
                isFound = true;
            }
        }
        return isFound;
        
    }

     /*
     * Checks if the word is found in the tree and gets its frequency.
     * 
     * @param String word. The word whose frequency is to be retrieved.
     * 
     * @return int wordFrequency. The frequency of the word.
     */
    @Override
    public int getFrequency(String word) {
        int frequency = 0;
        if(containsWord(word)){ //if the word is in the tree
            //get its node
            Node toRetrieve = getNode(word);
            frequency = toRetrieve.keyVal.frequency;
        }
        return frequency;
    }


    @Override
    public void print() { //Prints all the words in the tree as well as their frequency in a list format.
        Node cuNode = root;
        inOrderPrint(cuNode); 
    }

    /*
     * Calculates the number of edges on the longest route from the root to the leaf nodes.
     * 
     * @return int heightNum. The heigth of the tree.
     */
    @Override
    public int height() {
        return getTreeHeight(root);
    }

   
     /*
     * Checks if the otherTree is an instance of the same class as this tree.
     * 
     * Prints out words unique to each tree and those common to both.
     * 
     * @param GameTree otherTree. The word whose frequency is to be retrieved.
     * 
     */
    @Override
    public void compare(GameTree otherTree) {
        ArrayList<String> commonWords = new ArrayList<>(); //list of words common to both
        ArrayList<String> thisUniqueWords =  new ArrayList<>();
        ArrayList<String> otherUniqueWords =  new ArrayList<>();
        if(!(otherTree instanceof BST && this instanceof BST)){ //check if the trees are the same
            System.out.println("Trees are not Compatible");
        }else{//trees are the same
            BST otherBst = (BST) otherTree;
            ArrayList<String> currArr = this.getArrayList(); //list of all words in this BST
            ArrayList<String> otherArr = otherBst.getArrayList(); //list of all words in other BST

            //go through the first tree and check if each word is in the array
            for(int i = 0; i<currArr.size(); i++){
                String word = currArr.get(i);
                if(otherArr.contains(word)){ //if the word is in there add it to common words
                    
                    commonWords.add(word);//add to an arrayList
                }else{ //the word is not in the array, so add it to uniqueWords
                    
                    thisUniqueWords.add(word);
                }
            }
            //get the words not in the commonWords Array and add it to the uniqueWords for the otherTree
            for(int i= 0; i<otherArr.size(); i++){
                String word = otherArr.get(i);
                if(!(commonWords.contains(word))){
                    otherUniqueWords.add(word);
                }
            }
            //print out the words
            System.out.print("Common Words: ");
            printWords(commonWords);
            System.out.print("Unique to this tree: ");
            printWords(thisUniqueWords);  
            System.out.print("Unique to other tree: ");
            printWords(otherUniqueWords); 
        }
    }


    @Override
    public void printTree() { // Calls helper method  helperPrintTreerNode to visually print the tree showing the heirarchy of the tree
        Node curNode = root;
        int j = 8;
        helperPrintTree(curNode, j);
    }

    //--------------------------------------------------HELPER METHODS--------------------------------------------------//

     /*
     * This helper method recursively prints each node in  tree showing its heirarchy and levels of nodes
     * 
     * @param Node curNode. The node currently being processed and printed  
     * 
     * @param int indent. The number of spaces needed for the visual representation of the tree.
     * 
     */
    private void helperPrintTree(Node curNode, int indent) {
        if(curNode != null){
            for(int i = 0; i < indent;i++){
                System.out.print(" ");
            }
            System.out.println("[" + curNode.keyVal.word + " (" + curNode.keyVal.frequency + ")]");
            helperPrintTree(curNode.left, indent+3);
            helperPrintTree(curNode.right, indent+3);
        }else{
            for(int i = 0; i < indent;i++){
                System.out.print(" ");
            }
            System.out.println("[TREE GAP]"); //prints the TREEGAP to occupy internal nodes that have missing children nodes
        }   
    }

    /*
     * This helper method adds a specified number to the frequency of a word
     * 
     * @param String key. The word whose frequency is altered
     * 
     * @param int num. The number to be added to the frequency of the word
     * 
     */
    public void addFrequency(String word, int num){
            Node toAdd = getNode(word);
            toAdd.keyVal.frequency += num;//increase its frequency
        
    }

    /*
     * This helper method adds changes the frequency of the a word
     * to the specified number 
     * 
     * @param String key. The word whose frequency is altered
     * 
     * @param int num. The new frequency of the word
     * 
     */
    public void changeFrequency(String word, int num){
        Node toChange = getNode(word);
        toChange.keyVal.frequency = num;
    }

    /*
     * This helper method returns the node containing the specified word
     * if it exists in the tree
     * 
     * @param String word. The word to be searched in the tree
     * 
     * @return Node currnNode. The node containg the specified word
     * 
     */
    public Node getNode(String word){
        Node currNode = root;
        boolean isFound = false;
        while(currNode != null && !isFound){
            //if the string is less than currNode, go left
            if(word.compareToIgnoreCase(currNode.keyVal.getString()) < 0){
                currNode = currNode.left;
            }else if(word.compareToIgnoreCase(currNode.keyVal.getString()) > 0 ){  //if the string is greater than currNode, go right
                currNode = currNode.right;
            }else{//if they are equal isFound is true return currNode
                isFound = true;
            }  
        }
        return currNode;
    }

    /*
     * This helper method performs an in order traversal of the tree and prints the keys in a Node in the correct order 
     * 
     * @param Node curNode. The current node being traversed.
     * 
     */
    private void inOrderPrint(Node curnNode){
        if(curnNode != null){ 
            inOrderPrint(curnNode.left);

            //print out the cur
            if(curnNode == getMinNode(root)){
                System.out.print("[" + curnNode.keyVal.word + "(" + curnNode.keyVal.frequency + "), ");
            }else if(curnNode == getMaxNode(root)){
                System.out.print(curnNode.keyVal.word + "(" + curnNode.keyVal.frequency + ")]");
            }else{
                System.out.print(curnNode.keyVal.word + "(" + curnNode.keyVal.frequency + "), ");
            }

            inOrderPrint(curnNode.right);
            
        }
    }

    /*
     * This helper method iterates through the left children
     * in the tree to get the node with the minimum value
     * 
     * @return Node activeNode. The node containing the minimum value
     * 
     */
    private Node getMinNode(Node currNode ) {
        if ( currNode.left == null)
            return currNode;
        else{
            return getMinNode(currNode .left);
        }
        
    }

     /*
     * This helper method iterates through the right children
     * in the tree to get the node with the maximum value
     * 
     * @return Node activeNode. The node containing the maximum value
     * 
     */
    private Node getMaxNode(Node currNode ) {
        if ( currNode.right == null)
            return currNode;
        else{
            return getMaxNode(currNode.right);
        }
    }

    /*
     * This helper method returns the ArrayList containg all the words in the tree.
     * 
     * @retun ArrayList<String> wordArr. ArrayList of strings containing all the words in the tree
     * 
     */
    private ArrayList<String> getArrayList(){
        ArrayList<String> wordArr = new ArrayList<>(); //list of all words in the tree
        inOrderTraversal(root, wordArr);
        return wordArr;
    }

     /*
     * This helper method performs an in-order traversal of tree and adds the words in the tree to the provided ArrayList
     * 
     * @param Node currNode. The current node beig traversed.
     *
     * @param ArrayList<String> words. The list which words from the tree are added to
     * 
     */
    private void inOrderTraversal (Node currNode, ArrayList<String> words){
        if(currNode != null){ 
            inOrderTraversal(currNode.left, words);
            words.add(currNode.keyVal.word);
            inOrderTraversal(currNode.right, words);
        }
    }

     /* 
     * This helper method prints the words in the provided ArrayList
     * 
     * @param ArrayList<String> newArr. The ArrayList containing the words to be printed
     * 
     */
    private void printWords(ArrayList<String> newArr){
        if(newArr.isEmpty()){
            System.out.println("[ ]");
        }else{
            for(int i = 0; i < newArr.size(); i++){
                if(i == 0){ //if we are printing the first word in the arrayList
                    System.out.print( "[" + newArr.get(i)+ ", " );
                }else if(i == (newArr.size()-1)){ //if we are printing the last word in the arrayList
                    System.out.println( newArr.get(i)+ "]" );
                }else{
                    System.out.print(newArr.get(i)+ ", " );
                }
            }
        }
    }

     /* 
     * This helper method traverses the tree
     * 
     * @param ArrayList<String> newArr. The ArrayList containing the words to be printed
     * 
     * @return int greaterHeight. The height of the tree
     */
    private int getTreeHeight(Node currNode){
        int greaterHeight = 0;
        if(currNode == null){
            greaterHeight = -1;
        }else{
            int leftTreeHeight = getTreeHeight(currNode.left);
            int rightTreeHeight = getTreeHeight(currNode.right);

            
            if(leftTreeHeight > rightTreeHeight || leftTreeHeight == rightTreeHeight){
                greaterHeight = leftTreeHeight;
            }

            if(rightTreeHeight> leftTreeHeight){
                greaterHeight = rightTreeHeight;
            }
        }
        
 
        return (greaterHeight+1);
    }

 
    class Node{
        KeyValPair keyVal;
        Node left;
        Node right;
        Node parent;

        public Node(KeyValPair keyVal){
            this.keyVal = keyVal;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }


    
}
