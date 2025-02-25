/*******
 COMP 2140 Fall 2024
 Assignment 4 Question 1 Part A Solution
 Tifeoluwa Adegoke
 *******/
import java.util.ArrayList;
public class TwoThreeTree implements GameTree{
    Node root;
    Node currNode;
    int START_NUM = 1;

    public TwoThreeTree(){
        root = null;
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
            throw new NullPointerException("I was not able to implement the method fully BUT the code is placed in a comment block");
            /* 
            if(containsWord(word)){ //word is in tree
                addToFrequency(word, START_NUM);
            }else{
                //when the tree is empty, create a new root
                if(root == null){
                    //add to the root
                    root = new Node(new KeyValPair(word, START_NUM));
                }else{
                    //insert into leaf node 
                    Node newNod = insert(root, word);
                    if(newNod != null){
                        root = newNod;
                    }
                }
            }*/
    }

    
    /*
     * Strictly Checks if the word is the tree.
     * 
     * @param String word. The is the word that needs to be checked 
     * 
     * @return boolean isFound, confirming if the word is found in the tree or not
     */
    
    public boolean containsWord(String word) {
        boolean isFound = false;
        Node curNode = root;
        while(curNode!= null && !isFound){
            if(curNode.rightKey == null){//only one key
                if(word.compareToIgnoreCase(curNode.leftKey.getString()) < 0){
                    curNode = curNode.leftNode;
                }else if(word.compareToIgnoreCase(curNode.leftKey.getString()) > 0){
                    curNode = curNode.rightNode;
                }else{
                    isFound = true;
                }
            }else{ //two keys
                if(word.compareToIgnoreCase(curNode.leftKey.getString()) < 0){
                    curNode = curNode.leftNode;
                }else if(word.compareToIgnoreCase(curNode.rightKey.getString()) > 0){
                    curNode = curNode.rightNode;
                }else if(word.compareToIgnoreCase(curNode.leftKey.getString()) > 0 && word.compareToIgnoreCase(curNode.rightKey.getString()) < 0){
                    curNode = curNode.middleNode;
                }else{
                    isFound = true;
                }
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
        int wordFrequency = 0;
        if(containsWord(word)){
            Node wordFound = getNode(word);
            if(isFull(wordFound)){
                //there a two keys
                if(word.compareToIgnoreCase(wordFound.leftKey.getString()) == 0){
                    wordFrequency = wordFound.leftKey.frequency;
                }else if(word.compareToIgnoreCase(wordFound.rightKey.getString()) == 0){
                    wordFrequency = wordFound.rightKey.frequency;
                }
            }else{
                wordFrequency = wordFound.leftKey.frequency;
            }
       }
       return wordFrequency;
    }

    
    public void print() {
        currNode = root;
        inOrderPrint(currNode); //Prints all the words in the tree as well as their frequency in a list format.
    }

    /*
     * Calculates the number of edges on the longest route from the root to the leaf nodes.
     * 
     * @return int heightNum. The heigth of the tree.
     */
    public int height() {
        int heightNum = 0;
        Node currNode = root;
        while(currNode!=null && currNode.leftNode != null){
            heightNum++;
            currNode = currNode.leftNode;
        }
        return heightNum;
    }

    /*
     * Checks if the otherTree is an instance of the same class as this tree.
     * 
     * Prints out words unique to each tree and those common to both.
     * 
     * @param GameTree otherTree. The word whose frequency is to be retrieved.
     * 
     */
    public void compare(GameTree otherTree) {
        
            ArrayList<String> commonWords = new ArrayList<>(); //list of words common to both
            ArrayList<String> thisUniqueWords =  new ArrayList<>();
            ArrayList<String> otherUniqueWords =  new ArrayList<>();
            if(!(otherTree instanceof TwoThreeTree && this instanceof TwoThreeTree)){ //check if the trees are the same
                System.out.println("Trees are not Compatible");
            }else{//trees are the same
                TwoThreeTree otherTwoThree = (TwoThreeTree) otherTree;
                ArrayList<String> currArr = this.getArrayList(); //list of all words in this 2-3 Tree
                ArrayList<String> otherArr = otherTwoThree.getArrayList(); //list of all words in other 2-3 Tree
    
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
            if(isFull(curNode)){
                System.out.print("[" + curNode.leftKey.word + " (" + curNode.leftKey.frequency + "), ");
                System.out.println(curNode.rightKey.word + " (" + curNode.rightKey.frequency + ")]"); //check
            }else{
                System.out.println("[" + curNode.leftKey.word + " (" + curNode.leftKey.frequency + ")]");
            }
            helperPrintTree(curNode.leftNode, indent+3);
            helperPrintTree(curNode.middleNode, indent+3);
            helperPrintTree(curNode.rightNode, indent+3);
        }
    }

     /*
     * This helper method splits an overflowing node into two child nodes 
     * 
     * @param Node cuNode. A full node that is to be split.
     *
     * 
     */
    private Node splitNode(Node cuNode){
        Node newNode = new Node(null);
        Node leftChild = new Node(null);
        Node rightChild =new Node(null);

        newNode.leftKey = cuNode.rightKey; //the middle key to be promoted is assigned to the left key of the newNode
        leftChild.leftKey = cuNode.leftKey; //the new left child is filled with the leftKey of the currNode
        rightChild.leftKey = cuNode.extraKey;//the new right child is filled with the rightKey of the currNode

        leftChild.leftNode = cuNode.leftNode; //the leftChild takes the cuNode's left Node as it's left Node
        leftChild.rightNode = cuNode.middleNode; //the leftChild takes the cuNode's middle Node as it's right Node

        rightChild.leftNode = cuNode.rightNode; //the rightChild takes the cuNode's right Node as it's left Node
        rightChild.rightNode = cuNode.extraNode; //the rightChild takes the cuNode's extra Node as it's right Node

        newNode.leftNode = leftChild;
        newNode.rightNode = rightChild;

        return newNode;
    }

    /*
     * This helper method travereses the tree starting at the specified node and inserts the specified word in to a leaf node
     * This helper method splits and merges a node with 3 keys
     * 
     * @param Node cuNode. The root node for the subtree to be traversed
     *
     * @param String word. The word to be inserted into the tree
     * 
     * @return Node n. The new parent node after the word has been inserted
     */
    private Node insert(Node curr, String word){
        Node n = null;
        //get the leafNode to insert into
       if(!isLeafNode(curr)){
            if(isFull(curr)){ //check if the Node has two keys to know which subtree to traverse
                if(word.compareToIgnoreCase(curr.rightKey.getString()) > 0){
                    curr = curr.rightNode;
                }else if(word.compareToIgnoreCase(curr.leftKey.getString()) < 0){
                    curr = curr.leftNode;
                }else{
                    curr = curr.middleNode;
                }
            }else{ //when the Node has one key
                if(word.compareToIgnoreCase(curr.leftKey.getString()) < 0){
                    curr = curr.leftNode;
                }else{
                    curr = curr.rightNode;
                }
            }
            n = insert(curr, word); //calls insert on next node

        }else{ //do the ordered insert
           
            insertToNode(curr, word);
            //check if the node is valid or overflowing
            if(curr.extraKey != null){
                n = splitNode(curr); //split the overflowing node
                return n;

            }
          
        }
        if(n.extraKey == null){
            return null;
        }else{
            Node mergeNode = merge(currNode, n);
            if(mergeNode.extraKey != null){
                splitNode(mergeNode);
                
            }
            return n;
        }
    }

    /*
     * This helper method appropriately merges two nodes together and connects their children to the merged node
     * 
     * @param Node nodeToMerge. The node to be merged
     *
     * @param Node splitNode. The second node to be merged
     * 
     * @return Node nodeToMerge. The updated node after both specified nodes have merged
     */
    private Node merge(Node nodeToMerge, Node splitNode){
        //merging to the parent
        if(!(isFull(nodeToMerge))){
            if(splitNode.leftKey.word.compareToIgnoreCase(nodeToMerge.leftKey.word) > 0){
                nodeToMerge.rightKey = splitNode.leftKey;
                nodeToMerge.middleNode = splitNode.leftNode;
                nodeToMerge.rightNode = splitNode.rightNode;
            }else{
                nodeToMerge.rightKey = nodeToMerge.leftKey;
                nodeToMerge.leftKey = splitNode.leftKey;
                nodeToMerge.middleNode = splitNode.rightNode;
                nodeToMerge.leftNode = splitNode.leftNode;
            }
        }else{
            //merging to the back of nodeToMerge
            if(splitNode.leftKey.word.compareToIgnoreCase(nodeToMerge.rightKey.word) > 0){
                nodeToMerge.extraKey = splitNode.leftKey;
                nodeToMerge.rightNode = splitNode.leftNode;
                nodeToMerge.extraNode = splitNode.rightNode;
            }else if(splitNode.leftKey.word.compareToIgnoreCase(nodeToMerge.leftKey.word) < 0){ //merging in the front
                //shift the keys in the nodeToMerge to create space in the front for the parentKey in the splitNode
                nodeToMerge.extraKey = nodeToMerge.rightKey;
                nodeToMerge.rightKey = nodeToMerge.leftKey;
                nodeToMerge.leftKey = splitNode.leftKey;

                //shift nodeToMerge's children to accomodate the children of splitNode
                nodeToMerge.extraNode = nodeToMerge.rightNode;
                nodeToMerge.rightNode = nodeToMerge.middleNode;
                nodeToMerge.middleNode = splitNode.rightNode;
                nodeToMerge.leftNode = splitNode.leftNode;
            }else{ //merging to the middle
                //shift the keys in the nodeToMerge to create space in the middle for the parentKey in the splitNode
                nodeToMerge.extraKey = nodeToMerge.rightKey;
                nodeToMerge.rightKey = splitNode.leftKey;

                //shift nodeToMerge's children to accomodate the children of splitNode
                nodeToMerge.extraNode = nodeToMerge.rightNode;
                nodeToMerge.rightNode = splitNode.rightNode;
                nodeToMerge.middleNode = splitNode.leftNode;

            }

        }
       
        return nodeToMerge;
    }
    
     /*
     * This helper method inserts a word into a leaf node 
     * 
     * @param Node newLeaf. The leaf node where the word is to be inserted
     * 
     * @param String newWord. The word to be inserted into the node
     * 
     */
    private void insertToNode(Node newLeaf, String newWord){
        if(isFull(newLeaf)){
            if(newWord.compareToIgnoreCase(newLeaf.leftKey.getString()) < 0){
                // adding to the front
                newLeaf.extraKey = newLeaf.rightKey;
                newLeaf.rightKey = newLeaf.leftKey;
                newLeaf.leftKey = new KeyValPair(newWord, START_NUM);
            } else if(newWord.compareToIgnoreCase(newLeaf.rightKey.getString()) > 0) {
                // adding to the back
                newLeaf.extraKey = new KeyValPair(newWord, START_NUM);
            } else { // adding to the middle
                newLeaf.extraKey = newLeaf.rightKey;
                newLeaf.rightKey = new KeyValPair(newWord, START_NUM);
            }
        }else{
            if(newWord.compareToIgnoreCase(newLeaf.leftKey.getString()) < 0){
                newLeaf.rightKey = newLeaf.leftKey;
                newLeaf.leftKey = new KeyValPair(newWord, START_NUM);
            }else{
                newLeaf.rightKey = new KeyValPair(newWord, START_NUM);
            }
        }
       
    }



     /*
     * This helper method checks if the node is full by checking if the right and left keys are not null
     * 
     * @param Node newNode. The node to be checked
     * 
     * @retun boolean. Indicating if the node is full or not
     * 
     */
    private boolean isFull(Node newNode){
        return  newNode!= null && newNode.leftKey!= null && newNode.rightKey!= null ;
    }

     /*
     * This helper method returns the ArrayList of all the words in the tree
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
            if(isLeafNode(currNode)){
            //if there are two keys in the leafNode
                if(isFull(currNode)){
                    words.add(currNode.leftKey.getString());
                    words.add(currNode.rightKey.word);
                } 
                else{ //if there is one key in the leafNode
                    words.add(currNode.leftKey.word);
                }
            }else if(currNode.middleNode == null){ //there are two children
                inOrderTraversal(currNode.leftNode, words);
                words.add(currNode.leftKey.word);
                inOrderTraversal(currNode.rightNode, words);
            }else if(currNode.middleNode != null){
                inOrderTraversal(currNode.leftNode, words);
                words.add(currNode.leftKey.word);
                inOrderTraversal(currNode.middleNode, words);
                words.add(currNode.rightKey.word);
                inOrderTraversal(currNode.rightNode, words);
            }
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
     * This helper method performs an in order traversal of the tree and prints the keys in a Node in the correct order 
     * 
     * @param Node curNode. The current node being traversed.
     * 
     */
    private void inOrderPrint(Node curNode){
        if(isLeafNode(curNode)){
            //if there are two keys in the leafNode
            if(isFull(curNode)){
                if(curNode == getMinNode()){  //if minNode print with opening bracket
                    System.out.print("[" + curNode.leftKey.word + "(" + curNode.leftKey.frequency + "), ");
                    System.out.print(curNode.rightKey.word + "(" + curNode.rightKey.frequency + "), ");
                }
                else if(curNode == getMaxNode()){//if maxNode print with closing bracket
                    System.out.print(curNode.leftKey.word + "(" + curNode.leftKey.frequency + "), ");
                    System.out.print(curNode.rightKey.word + "(" + curNode.rightKey.frequency + ")]");
                }
                else{//else just print normally
                    System.out.print(curNode.leftKey.word + "(" + curNode.leftKey.frequency + "), ");
                    System.out.print(curNode.rightKey.word + "(" + curNode.rightKey.frequency + "), ");
                }
            } 
            else{ //if there is one key in the leafNode
                if(curNode == getMinNode()){  //if minNode print with open bracket
                    System.out.print("[" + curNode.leftKey.word + "(" + curNode.leftKey.frequency + "), ");
                }
                else if(curNode == getMaxNode()){//if maxNode print with closed bracket
                    System.out.print(curNode.leftKey.word + "(" + curNode.leftKey.frequency + ")]");
                }
                else{//else just print normally
                    System.out.print(curNode.leftKey.word + "(" + curNode.leftKey.frequency + "), ");
                }
            }
        }else if(curNode.middleNode == null){ //there are two children
            inOrderPrint(curNode.leftNode);
            System.out.print(curNode.leftKey.word + "(" + curNode.leftKey.frequency + "), ");
            inOrderPrint(curNode.rightNode);
        }else if(curNode.middleNode != null){
            inOrderPrint(curNode.leftNode);
            System.out.print(curNode.leftKey.word + "(" + curNode.leftKey.frequency + "), ");
            inOrderPrint(curNode.middleNode);
            System.out.print(curNode.rightKey.word + "(" + curNode.rightKey.frequency + "), ");
            inOrderPrint(curNode.rightNode);
        }
    }

     /*
     * This helper method checks if a node is a leaf node by checking 
     * if it doesn't have a left node, right node or middle node connected to it 
     * 
     * @param Node activeNode. The node to be checked
     * 
     */
    private boolean isLeafNode(Node activeNode){
        return activeNode.leftNode == null && activeNode.rightNode == null && activeNode.middleNode == null;
    }

    /*
     * This helper method iterates through the left children
     * in the tree to get the node with the minimum value
     * 
     * @return Node activeNode. The node containing the minimum value
     * 
     */
    private Node getMinNode(){
        Node activeNode = root;
        while(!isLeafNode(activeNode)){
            activeNode = activeNode.leftNode;
        }
        return activeNode;
    }

    /*
     * This helper method iterates through the right children
     * in the tree to get the node with the maximum value
     * 
     * @return Node activeNode. The node containing the maximum value
     * 
     */
    private Node getMaxNode(){
        Node activeNode = root;
        while(!isLeafNode(activeNode)){
            activeNode = activeNode.rightNode;
        }
        return activeNode;
    }

    /*
     * This helper method adds a specified number to the frequency of a word
     * 
     * @param String key. The word whose frequency is altered
     * 
     * @param int num. The number to be added to the frequency of the word
     * 
     */
    public void addToFrequency(String key, int num){
        Node keyNode = getNode(key);
        if(isFull(keyNode)){
            if(key.compareToIgnoreCase(keyNode.leftKey.getString()) == 0){
                keyNode.leftKey.frequency+=num;
            }else if(key.compareToIgnoreCase(keyNode.rightKey.getString()) == 0){
                keyNode.rightKey.frequency+=num;
            }
        }else{
            keyNode.leftKey.frequency+=num;
        }
       
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
    public void changeFrequency(String word, int newFrequency){
        Node toChange = getNode(word);
        if(isFull(toChange)){
            //check which word we need to change
            if(word.compareToIgnoreCase(toChange.leftKey.word) == 0){
                toChange.leftKey.frequency = newFrequency; //if the word is the leftKey in the node
            }else{
                toChange.rightKey.frequency = newFrequency; //if the word is the rightkey in the node
            }
        }else{
            toChange.leftKey.frequency = newFrequency; //there is only one key in the node
        }
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
    private Node getNode(String word){
        Node curnNode = root;
        Boolean isFound = false;
        while(curnNode!= null && !isFound){
            if(curnNode.rightKey == null){//only one key
                if(word.compareToIgnoreCase(curnNode.leftKey.getString()) < 0){
                    curnNode = curnNode.leftNode;
                }else if(word.compareToIgnoreCase(curnNode.leftKey.getString()) > 0){
                    curnNode = curnNode.rightNode;
                }else{
                    isFound = true;
                }
            }else{ //two keys
                if(word.compareToIgnoreCase(curnNode.leftKey.getString()) < 0){
                    curnNode = curnNode.leftNode;
                }else if(word.compareToIgnoreCase(curnNode.rightKey.getString()) > 0){
                    curnNode = curnNode.rightNode;
                }else if(word.compareToIgnoreCase(curnNode.leftKey.getString()) > 0 && word.compareToIgnoreCase(curnNode.rightKey.getString()) < 0){
                    curnNode = curnNode.middleNode;
                }else{
                    isFound = true;
                }
            }
        }
        return curnNode;
    }


    private class Node{
        KeyValPair rightKey;
        KeyValPair leftKey;
        KeyValPair extraKey;
        Node leftNode;
        Node rightNode;
        Node middleNode;
        Node extraNode;
        Node parent;

        public Node(KeyValPair leftKey){
            this.leftKey = leftKey;
            this.rightKey = null;
            this.extraKey = null;
            this.rightNode = null;
            this.leftNode = null;
            this.middleNode = null;
            this.parent = null;
            this.extraNode = null;
        }

    }

    
}
