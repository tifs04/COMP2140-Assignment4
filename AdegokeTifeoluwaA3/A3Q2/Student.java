/*******
 COMP 2140 Fall 2024
 Assignment 3 Question 1 Solution
 Tifeoluwa Adegoke
 *******/
public class Student implements Comparable<Student> {
    private String username;
    private int id;
    private String firstName;
    private String lastName;

    public Student(String username, int id, String firstName, String lastName){
       this.username = username;
       this.id = id;
       this.firstName = firstName;
       this.lastName = lastName;
       
    }

    public String getUserName(){
        return username; 
    }

    public int getId(){
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    
    public int compareTo(Student otherStudent) { 
        return this.username.compareTo(otherStudent.username);
    }
    
}
