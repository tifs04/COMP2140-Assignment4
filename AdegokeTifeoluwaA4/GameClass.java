/*******
 COMP 2140 Fall 2024
 Assignment 4 Question 1 Part B Solution
 Tifeoluwa Adegoke
 *******/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameClass {
    static int firstPNum = 0;
    static int secPNum = 0;
    static ArrayList<String> usedWords = new ArrayList<String>();
    public static void main(String[]args){
        if(args.length == 2){
            firstPNum = playerToStart(); //randomly decide who starts first
            if(firstPNum == 2){
                    secPNum = 1;
            }else{
                secPNum = 2;
            }
            //create player tree
            GameTree player1Tree =readFile(args[0]);
            //create opponent tree
            GameTree player2Tree = readFile(args[1]);

            gameConsole(firstPNum, secPNum, player1Tree, player2Tree);
            
        }
    }
        
        
        
//--------------------------------------------------HELPER METHODS--------------------------------------------------//

    /*
     * This helper method reads the specified and creates the appropriate tree stated on the first line in the file.
     * 
     * @param String fileName. The file to be read.
     * 
     * @return GameTree . The appropriate tree stated in the first line of the file
     */
    public static GameTree readFile(String fileName){
        GameTree tree = null;
       try{
            
            BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
            String newLine = buffReader.readLine(); //reads the first line in the file
            //create the appropriate Tree
            if(newLine.compareToIgnoreCase("BST") == 0){
               tree = (BST) createBst();
            }else{
                tree = (TwoThreeTree)createTwoThree() ;
            }
            //read the rest of the lines
            newLine = buffReader.readLine();
            while(newLine!=null){
                String[] wordArr= newLine.split(" ");
                for(int i = 0; i < wordArr.length; i++){
                    tree.addWord(wordArr[i]);
                }
                newLine = buffReader.readLine();
            }
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        return tree;
    }

    /*
     * This helper method randomly determines the number of rounds to be played and updates the scores of each player
     * This hepler method prints the result of the game after all the rounds have been played
     * 
     * @param int firstPlayerNum. The number of player that goes first.
     * 
     * @param int secPlayerNum. The number of player that goes first.
     *
     * @param GameTree firstPTree. The tree of the first player containg all the words from the file.
     * 
     * @param GameTree secPTree. The tree of the second player containg all the words from the file.
     */
    public static void gameConsole(int firstPlayerNum, int secPlayerNum, GameTree firstPTree, GameTree secPTree){
        int availableRounds = numOfRounds(); //randomly choose the number of rounds to be played
        int firstPScore = 0;
        int secPScore = 0;
        while(availableRounds > 0){
            playersInfo(firstPlayerNum,secPlayerNum, availableRounds, firstPTree, secPTree);
            firstPScore += processTurn(firstPlayerNum, firstPTree, secPTree);
            secPScore += processTurn(secPlayerNum,  secPTree, firstPTree);
            availableRounds--;
            printRoundResult(firstPNum, secPNum, firstPScore, secPScore);
        }

        System.out.println("------ END GAME STATUS ------");
        if(firstPScore > secPScore){
            System.out.println("Player" + firstPlayerNum + " is the winner with a score of " + firstPScore);
        }else{
            System.out.println("Player" + secPlayerNum + " is the winner with a score of " + secPScore);
        }
    }

    /*
     * This helper method asks the player for the move to perform and returns the score of the player
     * 
     * @param int playerNum. The number of player that goes first.
     *
     * @param GameTree playerOneTree. The tree of the first player containg all the words from the file.
     * 
     * @param GameTree playerTwoTree. The tree of the second player containg all the words from the file.
     * 
     * @return int score. The score of the player at the end of a round.
     */
    private static int processTurn(int playerNum, GameTree playerOneTree, GameTree playerTwoTree ){
       
            String playerChoice = playersTurn(firstPNum);; //ask the player for the move he wants to perform
            int score = 0;
            String playerWord = selectWord(playerChoice);//ask the player to input the word for the attack move
            if(playerChoice.compareToIgnoreCase("Attack") == 0){
                score = attack(playerWord, playerOneTree, playerTwoTree);
                
            }else if(playerChoice.compareToIgnoreCase("Defend") == 0){
                defend(playerWord, playerOneTree);
            }else if(playerChoice.compareToIgnoreCase("Swap") == 0){
                String[] wordsToSwap = playerWord.split(" ");
                if(wordsToSwap.length == 2){
                    swap(wordsToSwap[0], wordsToSwap[1], playerOneTree);
                }else{
                    System.out.println("Invalid option for Swap.");
                }
            }

            return score;
    }

    /*
     * This helper method doubles the frequency of the specified word.
     * 
     * @param String word. The word whose frequency is to be doubled.
     *
     * @param GameTree playerTree. The tree of the player containing all the words from the file.
     * 
     */
    private static void defend(String word, GameTree playerTree){
        int originalFrequency = 0;
        if(playerTree.containsWord(word) && !(usedWords.contains(word))){
             originalFrequency = playerTree.getFrequency(word);
             if(playerTree instanceof BST){
                BST newPlayerTree = (BST) playerTree;
                newPlayerTree.addFrequency(word, originalFrequency);
             }else{
                //two-three tree
                TwoThreeTree newPlayerTree = (TwoThreeTree) playerTree;
                newPlayerTree.addToFrequency(word, originalFrequency);
             }
             System.out.println(word + " has been successfully defend");
        }else{
            System.out.println("Word is invalid! What a waste!");
        }
    }
    
    /*
     * This helper method swaps the frequency of the two specified words.
     * 
     * @param String word1. The word whose frequency is to be swapped with the word2.
     *
     * @param String word2. The word whose frequency is to be swapped with word1.
     * 
     */
    private static void swap(String word1, String word2, GameTree playerTree){
        if((playerTree.containsWord(word1) && playerTree.containsWord(word2))){
            if(!(usedWords.contains(word2) && usedWords.contains(word1))){
                swapFrequency(word1, word2, playerTree);
            }
        }
    }

    /*
     * This helper method swaps the frequency of the two specified words.
     * 
     * @param String word1. The word whose frequency is to be swapped with the word2.
     *
     * @param String word2. The word whose frequency is to be swapped with word1.
     * 
     * @param GameTree tree. The tree containing the specified words.
     */
    private static void swapFrequency(String word1, String word2, GameTree tree){
        int firstWordFrequency = tree.getFrequency(word1); //frequency of the first word
        int secWordFrequency = tree.getFrequency(word2); //frquency of the second word
        if(tree instanceof BST){
            BST bstPlayerTree = (BST) tree; //cast the gametree to a bst tree
            bstPlayerTree.changeFrequency(word2, firstWordFrequency);
            bstPlayerTree.changeFrequency(word1,secWordFrequency);
        }else{
            TwoThreeTree twoThreePlayer = (TwoThreeTree) tree; //cast the gametree to a 2-3 tree
            twoThreePlayer.changeFrequency(word2, firstWordFrequency);
            twoThreePlayer.changeFrequency(word1,secWordFrequency);
        }
    }
     

    /*
     * This helper method prints out the current players turn and asks it to select a command.
     * This helper method asks the player to input a valid move in the game
     * 
     * @param int currPlayer. The number of the player currently playing.
     *
     */   
    private static String playersTurn(int currPlayer){ 
        System.out.println("Player " + currPlayer + "'s Turn");//print out the current players turn and asks it to select a command
        Scanner sc = new Scanner(System.in);
		System.out.println("\nPlease select from one of the following options:\n" + "ATTACK\n" + "DEFEND\n" + "SWAP\n");
		String answer = sc.nextLine();
        while(!isValidCommand(answer)){
            System.out.println("You have entered an invalid option");
            sc = new Scanner(System.in);
		    System.out.println("\nPlease select from one of the following options:\n" + "ATTACK\n" + "DEFEND\n" + "SWAP\n");
		    answer = sc.nextLine();
        }
        return answer;
    }

     /*
     * This helper method checks if the specified move is a valid move that can be used in the game
     * 
     * @String command.The move that can be used in the game
     *
     */
    private static boolean isValidCommand(String command){
        return command.compareToIgnoreCase("Attack") == 0 || command.compareToIgnoreCase("Defend") == 0 || command.compareToIgnoreCase("Swap") == 0;
    }

     /*
     * This helper method prints the tree of each player.
     * 
     * @param int playerNum. The number of the player that goes first.
     * 
     * @param int opponentNum. The number of the player that goes second.
     * 
     * @param int numOfRound. The number of roounds available
     *
     * @param GameTree playerTree. The tree of the first player containg all the words from the file.
     * 
     * @param GameTree playerTree. The tree of the second player containg all the words from the file.
     */
    private static void playersInfo(int playerNum, int opponentNum, int numOfRounds, GameTree playerTree, GameTree opponentTree){ //prints the tree of each player
            System.out.println("------ ROUNDS LEFT: " + numOfRounds +"------");
            System.out.println("------ Player " + playerNum + " Tree ------");
            playerTree.printTree();
            System.out.println("------ Player" + opponentNum + "Tree ------");
            opponentTree.printTree();
            
    }

    /*
     * This helper method confirms the move the player selcted
     * This helper method asks the player to input the necessary numbers of words required to perform the move
     * 
     * @param String playerMove. The move the player selected
     * 
     * @return String answer. The words th epalyer wishes to perform the move with
     */
    private static String selectWord(String playerMove){
        Scanner sc = new Scanner(System.in);
        System.out.println("You selected: " + playerMove);
        if(playerMove.compareToIgnoreCase("Swap")==0){
            System.out.println("Please enter two space-separated words you wish to swap:");
        }else{
             System.out.println("What word do you want to " + playerMove+ " ?");
        }
       
        String answer = sc.nextLine();
        return answer;
    }

    
    private static BST createBst(){
        return new BST(); //return a new BST tree
    }

    private static TwoThreeTree createTwoThree(){
        return new TwoThreeTree(); //return a new 2-3 tree
    }


    /*
     * This helper method randomly picks the number of rounds between 1 and 6 that 
     * need to be played for the game to end
     * 
     * @return int . The number of rounds to be played.
     */
    private static int numOfRounds(){
        Random randomNUm = new Random();
        return randomNUm.nextInt(2,6);
    } 

    /*
     * This helper method prints the result at the end of each round.
     * 
     * @param int firNum. The number of player who goes first.
     *
     * @param int secNum. The number of player who goes second.
     * 
     * @param int firstPlayerScore. The score of the player who goes first.
     *  
     * @param int secondPlayerScore. The score of the player who goes second.
     */
    private static void printRoundResult(int firNum, int secNum, int firstPlayerScore , int secondPlayerScore){
        System.out.println("Player: Player" + firNum);
        System.out.println("Score: " + firstPlayerScore);
        System.out.println("Player: Player" + secNum);
        System.out.println("Score: " + secondPlayerScore);
    }

    /*
     * This helper method randomly decides who starts the game.
     * 
     * @return a random number that is either 1 or 2.
     */
    private static int playerToStart(){
        Random randomNum = new Random();
        return randomNum.nextInt(1,3);
    }

    /*
     * This helper method checks if the specified word is found in the both the players' trees.
     * This helper method checks if the frequncy of the specified is higher in the first player's tree
     * 
     * @param String word. The word to be checked for in the trees.
     *
     * @param GameTree firstPlayer. The tree of the first player containg all the words from the file.
     * 
     * @param GameTree secPlayer. The tree of the second player containg all the words from the file.
     *  
     * @return int playerFrequency. The frequency of the specified word.
     */
    private static int attack(String word, GameTree firstPlayer, GameTree secPlayer){
        //check if the word is in usedWords
        int playerFrequency = 0;
        if(!isUsedWord(word)){
            addUsedWord(word);
            //if word frequncy is higher in opponent the player scores points equal to the frequency.
            if(!(firstPlayer.containsWord(word) && secPlayer.containsWord(word))){
                System.out.println("Word not found");
            }else if(firstPlayer.getFrequency(word) < secPlayer.getFrequency(word)){
                System.out.println("No points Scored");
            }else if(firstPlayer.getFrequency(word) > secPlayer.getFrequency(word)){
                playerFrequency = firstPlayer.getFrequency(word);
                System.out.println("Score has been increased by:" + playerFrequency);
                
            }
            
        }else{
            System.out.println("Word has been used previously");
        }

        return playerFrequency;
       
    }

    private static boolean isUsedWord(String word){
        return usedWords.contains(word); //return boolean indicating if a found is found in the arrayList
    }

    
    /*
     * This helper method adds the specifed word to an arrayList of words that are no longer allowed in the game.
     * 
     * @param String word. The word to be added to the arrayList.
     *
     */
    private static void addUsedWord(String word){
        ArrayList<String> usedWords = new ArrayList<String>();
        //add the word to the arrayList
        usedWords.add(word);
    }

    
}
