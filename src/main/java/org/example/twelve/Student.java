package org.example.twelve;

import java.io.*;

public class Student implements Serializable {

    String name;
    Double gpa;
    int regNo;

    //Static field will not be serialized
    static int universityId = 89;

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
        System.out.println("University Number: " + this.universityId);
        System.out.println("Is Hosteller: " + this.isHosteller);
        System.out.println("Serial version UID: " + serialVersionUID);
    }
}


class Demo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student student1 = new Student("Justin", 8.51, 101,true);
        Student student2 = new Student("Amelie", 6.25, 102,true);
        Student student3 = new Student("John", 7.13, 103, true);
        System.out.println("Before Serialization:");
        student1.print();
        System.out.println();
        student2.print();
        System.out.println();
        student3.print();
        System.out.println();
        System.out.println();

        //Serialization
//        FileOutputStream fileOutputStream = new FileOutputStream("demo1.txt");
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//        objectOutputStream.writeObject(student1);
//        objectOutputStream.close();
//        fileOutputStream.close();
//
//        fileOutputStream = new FileOutputStream("demo2.txt");
//        objectOutputStream = new ObjectOutputStream(fileOutputStream);
//        objectOutputStream.writeObject(student2);
//        objectOutputStream.close();
//        fileOutputStream.close();
//
//        fileOutputStream = new FileOutputStream("demo3.txt");
//        objectOutputStream = new ObjectOutputStream(fileOutputStream);
//        objectOutputStream.writeObject(student3);
//        objectOutputStream.close();
//        fileOutputStream.close();

        Student.universityId = 90; //Changing the static field


        //Deserialization
        FileInputStream fileInputStream = new FileInputStream("demo1.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Student deserializedStudent1 = (Student) objectInputStream.readObject();

        System.out.println("\nAfter Deserialization 1:");
        deserializedStudent1.print();
        objectInputStream.close();
        fileInputStream.close();

        fileInputStream = new FileInputStream("demo2.txt");
        objectInputStream = new ObjectInputStream(fileInputStream);

        Student deserializedStudent2 = (Student) objectInputStream.readObject();

        System.out.println("\nAfter Deserialization 2:");
        deserializedStudent2.print();
        objectInputStream.close();
        fileInputStream.close();


        fileInputStream = new FileInputStream("demo3.txt");
        objectInputStream = new ObjectInputStream(fileInputStream);

        Student deserializedStudent3 = (Student) objectInputStream.readObject();

        System.out.println("\nAfter Deserialization 3:");
        deserializedStudent3.print();
        objectInputStream.close();
        fileInputStream.close();
    }

}
