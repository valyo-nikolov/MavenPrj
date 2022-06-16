package org.example.twelve;

import java.io.*;

public class Student implements Serializable {

    String name;
    Double gpa;

    //Static field will not be serialized
    static int regNo;

    //Transient field will be reset to its default type value after deserialization
    transient boolean isHosteller;

    @Serial
    private static final long serialVersionUID = 1234L;

    Student(String name, Double gpa, int regNo, boolean isHosteller) {
        this.name = name;
        this.gpa = gpa;
        this.regNo = regNo;
        this.isHosteller = isHosteller;
    }

    public void print() {
        System.out.println("Name: " + this.name);
        System.out.println("GPA: " + this.gpa);
        System.out.println("Registration Number: " + this.regNo);
        System.out.println("Is Hosteller: " + this.isHosteller);
        System.out.println("Serial version UID: " + serialVersionUID);
//        System.out.println("Serial version UID: " + serialVersionUID);
    }
}


class Demo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student student = new Student("Justin", 8.51, 101, true);
        System.out.println("Before Serialization:");
        student.print();

        //Serialization
        FileOutputStream fileOutputStream = new FileOutputStream("demo.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(student);
        objectOutputStream.close();
        fileOutputStream.close();

        Student.regNo = 102; //Changing the static field


        //Deserialization
        FileInputStream fileInputStream = new FileInputStream("demo.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Student deserializedStudent = (Student) objectInputStream.readObject();

        System.out.println("\nAfter Deserialization:");
        deserializedStudent.print();
        objectInputStream.close();
        fileInputStream.close();
    }

}
