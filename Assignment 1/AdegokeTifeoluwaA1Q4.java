import java.io.*;

public class AdegokeTifeoluwaA1Q4 {
    static int numA = 0;
    static int numB = 0;
    static int numC = 0;
    static int arraySize = 0;
    public static void main(String[] args) {
        String FILE_NAME = "TestThreeSum.txt";
        String[] stringInFile = readFile(FILE_NAME);
        int[] numInFile = stringToInt(stringInFile);
        
        if(hasThreeSum(numInFile)){
            System.out.println("There exist three entries in the sorted array whose sum is 0. They are:" + "\n" + numA + "\n" + numB + "\n" + numC );
        }else{
            System.out.println("There are no three entries in the sorted array whose sum is 0." );
        }
        

    } 


    /*
     * Reads the content of the file and stores in an array of Strings
     * 
     * @param fileName. Name of the file to be read
     * 
     * @return an array containing content read from file
     */
    public static String[] readFile(String fileName) {
        String fileRead = fileName;
        String[] numArr = null;
        try{
            BufferedReader buffReader = new BufferedReader(new FileReader(fileRead));
            String newLine = buffReader.readLine(); //reads the first line in the file
            arraySize = Integer.parseInt(newLine);
            newLine = buffReader.readLine(); //reads the second line in the file
            while(newLine != null){
                numArr = newLine.split(" "); 
                newLine = buffReader.readLine();
            }

            

        }catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        return numArr;
    } 

    /*
     * converts an array of strings to an array of integers
     * 
     * @param stringArr. An array of strings
     * 
     * @return numArr. An array of integers
     */
    public static int[] stringToInt(String[] stringArr){
        int[] numArr = new int[arraySize];
        for(int i = 0; i < numArr.length; i++){
            numArr[i] = Integer.parseInt(stringArr[i]);
        }
        return numArr;
    }


    
    /*
     * Checks if two numbers in an array add up to the target number
     * 
     * @param arrName. Array to be checked. 
     * @param int j, int k. Numbers in the array to be checked. 
     * @param int t. The target number
     * 
     * @return answer. Boolean variable to confirm if two numbers in the array add up to target number
     */
    public static boolean hasTargetSum(int[] arrName, int j, int k, int t){
        boolean answer = false;
        while((j<k) && (!answer)){
            if ((arrName[j] + arrName[k]) < t){
                j = j + 1;
            } else if((arrName[j] + arrName[k]) > t){
                k = k - 1;
            } else {
                answer = true;
                numA = arrName[j];
                numB = arrName[k];
                numC = -t;
            }
        }

        return answer;

    }

    /*
     * Checks if two numbers in the array add up to a number in the same array
     * 
     * @param arrName. Array of integers to be iterated through
     * 
     * @return answer. Boolean variable to confirm if two numbers in the array add up to any other number in the array
     */
    public static boolean hasThreeSum(int[] arrName){
        int arrLength = arrName.length;
        int i = 0;
        boolean answer = false;
        while ((i < arrLength-2) && (!answer)){
            answer = hasTargetSum(arrName, i+1, arrLength-1, -(arrName[i]));
            i = i+1;
        }
        
        return answer;
    }






    
}
