/*******
 COMP 2140 Fall 2024
 Assignment 3 Question 3 Solution
 Tifeoluwa Adegoke
 *******/
public class ClassList {
    private Node[] classList;
    private final int MAX_NUM;
    private Node currNode;
    private Node prevNode;
    
    
    public ClassList(){
        MAX_NUM = 99;
        this.classList = new Node[MAX_NUM];
        this.currNode = null;
        this.prevNode = null;
    }

   
    /*
     * Checks if the student is in the ClassList
     * 
     * @param student. Instance of student class to be check
     * 
     * @return boolean confirming if student is found in the list or not
     */
    public boolean contains(Student student){
        boolean isFound = false;
        String userName = student.getUserName();
        int stdIndex = hashFunc(userName);
        currNode = this.classList[stdIndex];
        while(currNode != null && !isFound){
            if(currNode.student.compareTo(student) == 0){
                isFound = true;
            }
            currNode = currNode.next;
            
        }
        return isFound;
    }

    /*
     * Checks if the student is in the classList, and if not adds student to the ClassList
     * 
     * @param student. Instance of student class to be added
     * 
     */
    public void enroll(Student student){
        Node stuToAdd = new Node(student, null);
        if(!this.contains(student)){
            int index = hashFunc(student.getUserName());
            if(this.classList[index] != null){
                currNode = classList[index];
                while(currNode.next != null){
                    currNode = currNode.next;
                }
                
                currNode.next = stuToAdd;
            }else{
                classList[index] = stuToAdd;
            }
        }else{
            System.out.println("The student with the username (" + student.getUserName() + ") has been previously enrolled");
        }
    }
    
    /*
     * Checks if the student is in the ClassList, and if so removes student
     * 
     * @param student. Instance of student class to be check
     * 
     */
    public void unenroll(Student student){
        if(contains(student)){
            int stdnIndex = hashFunc(student.getUserName());
            currNode = this.classList[stdnIndex];
            prevNode = null;
            while(currNode != null && currNode.student.compareTo(student) != 0){
                prevNode = currNode;
                currNode = currNode.next;
            }
            if(prevNode == null){  //deleting from the top
                this.classList[stdnIndex] = currNode.next;
            }else{
                prevNode.next = currNode.next;  //deleting from end and middle
            }
            System.out.println("The student with the username (" + student.getUserName() + ") has been unenrolled");


        }
    }
    /*
     * Checks if the student currently in the otherList is in this ClassList
     * 
     * Enrolls student that are in otherList but not in this ClassList
     * 
     * @param otherList. Instance of ClassList
     * 
     */
    public void enrollAll(ClassList otherList){
        for(int i = 0; i < otherList.classList.length; i++){
            Node otherCurrNode = otherList.classList[i];
            while(otherCurrNode != null ){
                Student otherStudent = otherCurrNode.student;
                this.enroll(otherStudent);
                otherCurrNode = otherCurrNode.next;
            }
        }
    }

    /*
     * Checks if each student currently in the otherList is also in this ClassList
     * 
     * Unenrolls all students that are both in otherList and this ClassList from this ClassList
     * 
     * @param otherList. Instance of ClassList
     * 
     */
    public void unenrollAll(ClassList otherList){
        for(int i = 0; i < otherList.classList.length; i++){
            Node otherCurrNode = otherList.classList[i];
            while(otherCurrNode != null ){
                Student otherStudent = otherCurrNode.student;
                this.unenroll(otherStudent);
                otherCurrNode = otherCurrNode.next;
            }
        }
    }

    /*
     * Creates a deep copy of this ClassList
     * 
     * @return a new instance of the deep copy of this ClassList
     */
    public ClassList duplicate(){
        ClassList newClassList = new ClassList();
        for(int i = 0; i < this.classList.length; i++){
            Node curNode = this.classList[i];
            
            while(curNode != null){
                newClassList.enroll(curNode.student);
                curNode = curNode.next;
            }
        }

        return newClassList;
    }

    /*
     * Checks if each student currently in the otherList is in this ClassList
     * 
     * Enrolls all students that are both in otherList and this ClassList into a new ClassList
     * 
     * @param otherList. Instance of ClassList
     * 
     * @return ClassList. A new instance of ClassList containing all students in both otherList and this ClassList
     */
    public ClassList alsoEnrolledIn(ClassList otherList){
        ClassList stdEnrolled = new ClassList();
        for(int i = 0; i < otherList.classList.length; i++){
            Node currStudents = otherList.classList[i];
            while(currStudents != null){
                Student otherStudent = currStudents.student;
                if(this.contains(otherStudent)){
                    stdEnrolled.enroll(otherStudent);
                }
                currStudents = currStudents.next;
            }
        }

        return stdEnrolled;
    }

    /*
     * Checks if each student currently in the otherList is in this ClassList
     * 
     * Enrolls all students that are in otherList but not in this ClassList into a new ClassList
     * 
     * @param otherList. Instance of ClassList
     * 
     * @return ClassList. A new instance of ClassList containing all students in otherList but not this ClassList
     */
    public ClassList notAlsoEnrolledIn(ClassList otherList){
        ClassList stdNotEnrolled = new ClassList();
        for(int i = 0; i < this.classList.length; i++){
            Node currStudents = this.classList[i];
            while(currStudents != null){
                Student currStud = currStudents.student;
                if(!(otherList.contains(currStud))){
                    stdNotEnrolled.enroll(currStud);
                }
                currStudents = currStudents.next;
            }
        }

        return stdNotEnrolled;
    }
    
    //prints the information of all students in this ClassList
    public void print(){
        if(this.isEmpty()){
            System.out.println("The hash table is empty");
        }else{
            System.out.println("The details of the students enrolled in the class are as follows: " );
            System.out.println("-----------------------------");
            for(int i=0; i<this.classList.length;i++){
                currNode = this.classList[i];
                while(currNode != null){
                    System.out.println(currNode.student.getFirstName() + " " + currNode.student.getLastName());
                    System.out.println("Username: " + currNode.student.getUserName()); 
                    System.out.println("Id: " + currNode.student.getId());
                    System.out.println("-----------------------------");
                    currNode = currNode.next;
                }
        }
        }
        
    }



     /*
     * Helper method to check if hash table is empty
     * 
     * Enrolls all students that are in otherList but not in this ClassList into a new ClassList
     * 
     * @return boolean tableEmpty. 
     */
    private boolean isEmpty(){
        boolean tableEmpty = true;
        for(int i=0; i<classList.length; i++){
            currNode = this.classList[i];
            if(currNode != null ){
                tableEmpty = false;
                
            }
        }

        return tableEmpty;
    }


     /*
     * This method gets the ASCII value of the character
     * 
     * @param char c. 
     * 
     * @return ASCII value of the character
     */
    private int hashCode(char c){
        return (int) c;
    }
    

     /*
     * This method convert all characters in a string to its ASCII value
     * 
     * @param String key. 
     * 
     * @return int[]. AN integer array containg the ASCII value of each character in the string.
     */
    private int[] toInt(String key){
        int[] charToInt = new int[key.length()];
        for(int i =0 ; i < charToInt.length; i++){
            charToInt[i] = hashCode(key.charAt(i));
        }

        return charToInt;
    }
    
    /*
     * This method uses honer's method and strToInt method a string to an index in the hash table
     * 
     * @param String key. 
     * 
     * @return int keyIndex. an integer representing the index of the string parameter in the hash table
     */
    private int hashFunc(String key){
        int[] strToInt = toInt(key);
        final int A = 13;
        int keyIndex = strToInt[0] % MAX_NUM;
        for(int i = 1; i < strToInt.length; i++ ){
            keyIndex = (A * keyIndex + strToInt[i]) % MAX_NUM;
        }
        return keyIndex;
    }

    private class Node{
        private Student student; 
        private Node next;

        public Node(Student student, Node next){
            this.student = student;
            this.next = null;
            
        }

    }

}

