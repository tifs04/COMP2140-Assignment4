/*******
 COMP 2140 Fall 2024
 Assignment 3 Question 4 Solution
 Tifeoluwa Adegoke
 *******/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestClassList {


    public static void main(String[] args) {
        //main method
        if(args.length > 0){
            String fileName = args[0];
            commandProcessor(fileName);
        }else{
            System.out.println("Please provide valid input: ");
        }
    }

    /*
     * Create an instance of a ClassList
     * 
     * @param otherList. Instance of ClassList
     * 
     * @return ClassList. 
     */
    private static ClassList createClass(){ //creates a new class obj
       return new ClassList();
    }

     /*
     * Reads the first line in the file and creates instances of ClassList based on the number read from file
     * 
     * @param fileName. The file to be processed
     * 
     * @return a  ClassList array. 
     */
    private static ClassList[] listOfClass(String fileName){
        ClassList[] namesOfClass = null;
        try{
            BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
            String newLine = buffReader.readLine(); //reads the first line in the file
            int numClassObj = Integer.parseInt(newLine);
            namesOfClass = new ClassList[numClassObj];
            for(int i = 0; i < numClassObj; i++){
                namesOfClass[i] = createClass();
            }
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
        return namesOfClass; 
    }
     
    /*
     * Reads each line in the file and calls the method cmdInterpreter
     * to execute the instructions given in each line
     * 
     * @param fileName. File to be processed
     * 
     */
    public static void commandProcessor(String fileName){
        try{
            ClassList[] className = listOfClass(fileName); 
            BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
            String newLine = buffReader.readLine(); //reads the first line in the file
            newLine = buffReader.readLine();//read the second line
            while(newLine != null){
                String[] strArr = newLine.split(" "); 
                cmdInterpreter(strArr, className);
                newLine = buffReader.readLine();
            }
        }catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

     /*
     * This method interpretes the instruction in the first index of the string array and 
     * calls the appropriate method to execute the required instruction.
     * 
     * @param strArr[]. An array of Strings containing the instructions to be executed
     * @param classSect[]. An array of ClassLists
     * 
     */
    public static void cmdInterpreter(String strArr[], ClassList[] classSect){
        if(strArr[0].equals("C")){
            openFile(strArr[1], classSect[Integer.parseInt(strArr[2])]);
            System.out.println("The file " + strArr[1] + " has been opened" + "\n");
        }else if(strArr[0].equals("P")){
            System.out.println("\n" + "CLASS NAME:CLASS " + strArr[1] );
            prtCommand(classSect[Integer.parseInt(strArr[1])]);
        }else if(strArr[0].equals("D")){
            classSect[Integer.parseInt(strArr[2])] = deepCopy(classSect[Integer.parseInt(strArr[1])]);
        }else if(strArr[0].equals("E")){
            addStds(classSect[Integer.parseInt(strArr[1])], classSect[Integer.parseInt(strArr[2])]);
        }else if(strArr[0].equals("U")){
            removeStds(classSect[Integer.parseInt(strArr[1])], classSect[Integer.parseInt(strArr[2])]);
        }else if(strArr[0].equals("A")){
            classSect[Integer.parseInt(strArr[3])]= enrolledStds(classSect[Integer.parseInt(strArr[1])], classSect[Integer.parseInt(strArr[2])]);
        }else if(strArr[0].equals("N")){
            classSect[Integer.parseInt(strArr[3])] = unenrolledStds(classSect[Integer.parseInt(strArr[1])], classSect[Integer.parseInt(strArr[2])]);
        }
    }

    /*
     * This method opens the file and reads each line in the file 
     * and enrolls all the students in the file into a ClassList
     * 
     * @param fileName. The name of the file to be opened
     * @param className. The name of ClassList containing all students in the file
     * 
     */
    public static ClassList openFile(String fileName, ClassList className){
        try{
            BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
            String newLine = buffReader.readLine(); //reads the first line in the file
            while(newLine != null){
                String[] numArr = newLine.split(","); 
                if(numArr.length == 4){
                    Student newStudent = new Student(numArr[0], Integer.parseInt(numArr[1]), numArr[2], numArr[3]);
                    className.enroll(newStudent); 
                }
                newLine = buffReader.readLine();
            }
        }catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return className;
    }
    
     /*
     * This method calls the print method from the class ClassList
     * 
     * @param className. An instance of the ClassList
     * 
     */
    private static void prtCommand(ClassList className){
        className.print();
    }

    /*
     * This method calls the deepCopy method from the class ClassList
     * 
     * @param className. An instance of the ClassList
     * 
     * @return it returns a deep copy of the parameter
     */
    private static ClassList deepCopy(ClassList className){
        return className.duplicate();
    }

    /*
     * This method calls the enrollAll method from the class ClassList
     * Enrolls students that are in otherClass but not in className
     * 
     * @param className. An instance of the class ClassList
     * 
     * @param otherClass. An instance of the class ClassList
     */
    private static void addStds(ClassList className, ClassList otherClass){
        className.enrollAll(otherClass);
    }

    /*
     * This method calls the unenrollAll method from the class ClassList
     * Unenrolls students that are in otherClass and className
     * 
     * @param className. An instance of the class ClassList
     * 
     * @param otherClass. An instance of the class ClassList
     */
    private static void removeStds(ClassList className, ClassList otherClass){
        className.unenrollAll(otherClass);
    }

    /*
     * This method calls the alsoEnrolledIn method from the class ClassList
     * Enrolls all students that are both in otherClass and className
     * 
     * @param className. An instance of the class ClassList
     * 
     * @param otherClass. An instance of the class ClassList
     * 
     * @return A new instance of ClassList containing all students in both otherList and this ClassList
     */
    private static ClassList enrolledStds(ClassList className, ClassList otherClass){
        return className.alsoEnrolledIn(otherClass);
    }
    /*
     * This method calls the notAlsoEnrolledIn method from the class ClassList
     * Enrolls all students that are in otherClass but not className
     * 
     * @param className. An instance of the class ClassList
     * 
     * @param otherClass. An instance of the class ClassList
     * 
     * @return A new instance of ClassList containing all students in otherClass but not this className
     */
    private static ClassList unenrolledStds(ClassList className, ClassList otherClass){
        return className.notAlsoEnrolledIn(otherClass);
    }

}


