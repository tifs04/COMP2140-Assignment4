/*******
 COMP 2140 Fall 2024
 Assignment 3 Question 2 Solution 
 Tifeoluwa Adegoke
 *******/
public class ClassList {
    private Node dummyHead;
    private Node dummyTail;
    private Node currNode;
    private Node prevNode;
    

    public ClassList(){
        this.dummyHead = new Node(null);
        this.dummyTail = new Node(null);
        this.dummyHead.fwdLink = dummyTail;
        this.dummyTail.backLink = dummyHead;
        this.dummyHead.backLink = null;
        this.dummyTail.fwdLink = null;
        this.currNode = null;
    }


    /*
     * Checks if the student is in the ClassList
     * 
     * @param student. Instance of student class to be check
     * 
     * @return boolean confirming if student is found in the list or not
     */
    public boolean contains(Student student) {
        boolean found = false;
        Node currNode = dummyHead.fwdLink;
        while(currNode != dummyTail && !found){  //for as long as we are not at the end of the list
            if(currNode.student.compareTo(student) > 0){
                currNode = dummyTail;  //we break out of loop
            }else if(currNode.student.compareTo(student) < 0){
                //keep searching
                currNode = currNode.fwdLink;
            }else{
                found = true;
            }
        }
	return found;
    }
    
    /*
     * Checks if the student is in the classList, and if not adds student to the ClassList
     * 
     * @param student. Instance of student class to be added
     * 
     */
    public void enroll(Student student) {
        if(!contains(student)){
            prevNode = dummyHead;
            currNode = dummyHead.fwdLink;  
            while(currNode!=dummyTail && currNode.student.compareTo(student) < 0 ){ //as long the current student's name comes before, keep checking to know where to insert
                prevNode = currNode;
                currNode = currNode.fwdLink;
            }
            Node studentNode = new Node(student);
            //do the insert
            //edge case 1: checks for when the list is not empty
            if(prevNode == dummyHead && currNode == dummyTail){   
                dummyHead.fwdLink = studentNode;
                studentNode.backLink = dummyHead;
                studentNode.fwdLink = dummyTail;
                dummyTail.backLink = studentNode;
            }
            //edge case 2: adding to the front
            else if(prevNode == dummyHead){
                dummyHead.fwdLink = studentNode;
                studentNode.backLink = dummyHead;
                studentNode.fwdLink = currNode;
                currNode.backLink = studentNode;
            }
            //edge case 3: adding to the middle
            else {
                prevNode.fwdLink = studentNode;
                studentNode.backLink = prevNode;
                studentNode.fwdLink = currNode;
                currNode.backLink = studentNode; 
            }
            
            //edge case 4: adding to the back
            if(currNode == dummyTail){
                currNode.fwdLink = studentNode;
                studentNode.backLink = currNode;
                studentNode.fwdLink = dummyTail;
                dummyTail.backLink = studentNode;
            } 
            
        }
       
        
    }
    
     /*
     * Checks if the student is in the ClassList, and if so removes student
     * 
     * @param student. Instance of student class to be check
     * 
     */
    public void unenroll(Student student) {
        if((contains(student))){ //if the student is found in the list
            currNode = dummyHead.fwdLink;
            prevNode = dummyHead;
            while(currNode!=dummyTail &&  currNode.student.compareTo(student) != 0 ){ //traverse the list to be able find the student
                prevNode = currNode;
                currNode = currNode.fwdLink;
            }
            //when the student is found, delete it 
            if(currNode != dummyTail){  //to check the list is not empty
                //edge case 1: deleting from the middle
                if(prevNode != dummyHead && currNode.fwdLink != dummyTail){
                    prevNode.fwdLink = currNode.fwdLink;
                    currNode.fwdLink.backLink = prevNode;
                }
                //edge case 2: deleting from the front
                else if(prevNode == dummyHead){
                    dummyHead.fwdLink = currNode.fwdLink;
                    currNode.fwdLink.backLink = dummyHead;
                }
                
                //edge case 3: deleting from the back
                else if(currNode.fwdLink == dummyTail){
                    prevNode.fwdLink = dummyTail;
                    dummyTail.backLink = prevNode;

                }
            }
        }
    }

    /*
     * Checks if the student currently in the otherList is in this ClassList
     * 
     * Enrolls all students that are in otherList but not in this ClassList
     * 
     * @param otherList. Instance of ClassList
     * 
     */
    public void enrollAll(ClassList otherList) {
        Node currOtherNode = otherList.dummyHead.fwdLink;
        while(currOtherNode!= otherList.dummyTail){
            this.enroll(currOtherNode.student);  
            currOtherNode = currOtherNode.fwdLink; //move to check the next student 
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
    public void unenrollAll(ClassList otherList) {
        Node currOtherNode = otherList.dummyHead.fwdLink;
        while(currOtherNode!= otherList.dummyTail){
            this.unenroll(currOtherNode.student);  
            currOtherNode = currOtherNode.fwdLink; //move to check the next student 
        }
    }

    /*
     * Creates a deep copy of this ClassList
     * 
     * @return a new instance of the deep copy of this ClassList
     */
    public ClassList duplicate() {
        ClassList newList = new ClassList();
        this.currNode = this.dummyHead.fwdLink;
        while(this.currNode!= this.dummyTail){
            newList.enroll(this.currNode.student);
            this.currNode = this.currNode.fwdLink;
        }
	    return newList;
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
    public ClassList alsoEnrolledIn(ClassList otherList) {
        ClassList enrolledList = new ClassList();
        Node currOtherNode = otherList.dummyHead.fwdLink;
        while(currOtherNode!= otherList.dummyTail){
            if(this.contains(currOtherNode.student)){
                enrolledList.enroll(currOtherNode.student);
            }
            currOtherNode = currOtherNode.fwdLink;
        }
	    return enrolledList;
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
    public ClassList notAlsoEnrolledIn(ClassList otherList) {
	    ClassList notEnrolledList = new ClassList();
        Node curOtherNode = this.dummyHead.fwdLink;
        while(curOtherNode!= this.dummyTail){
            if(!(otherList.contains(curOtherNode.student))){
                
                notEnrolledList.enroll(curOtherNode.student);
                
            }
            curOtherNode = curOtherNode.fwdLink;
        }
	    return notEnrolledList;
    }


    //prints the information of all students in this ClassList
    public void print() {
        currNode = dummyHead.fwdLink;
        if(currNode == dummyTail){
            System.out.println("There are no students currently enrolled in the class." );
            
        }else{
            System.out.println("The details of the students enrolled in the class are as follows: " );
            System.out.println("-----------------------------");
            while(currNode!= dummyTail){
                System.out.println(currNode.student.getFirstName() + " " + currNode.student.getLastName());
                System.out.println("Username: " + currNode.student.getUserName()); 
                System.out.println("Id: " + currNode.student.getId());
                System.out.println("-----------------------------");
                currNode = currNode.fwdLink;
            }
        }
    }

    private class Node{
        private Student student; 
        private Node fwdLink;
        private Node backLink;

        public Node(Student student){
            this.student = student;
            this.backLink = null;
            this.fwdLink = null;
        }

    }
    
}

public String returnFirst(){
    String firstWord = "";
    for(int i = 0; i < array.length; i++){
        //go through the array
        Node top = array[i];
        if(top != null){
            if(top.key.compareTo(firstWord) < 0){
                firstWord = top.key;
            }
            if(top.next!= null){
                //a linked list exists
                Node curNode = top;
                while(curNode != null){
                    if(curNode.key.compareTo(firstWord) < 0){
                        firstWord = curNode.key;
                    }
                    curNode = curNode.next;
                }
            }
        }
       
    }
}

