package org.example.ten;

import org.testng.annotations.Test;

public class GroupsAndPriority {

    @Test(groups = "example2", description = "This is my first test case with testng description"
            , dependsOnGroups = "example", priority = 1)
    public void dependsOnGroupExample() {
        System.out.println("This is a depends on test example");
    }

    @Test(groups = "example4", dependsOnMethods = "myFirstTestNGTestCase", priority = -2)
    public void dependsOnMethodExample() {
        System.out.println("This is a depends on test example");
    }

    @Test
    public void myFirstTestNGTestCase() {
        System.out.println("This is my first testNG test case");
    }

    @Test(groups = "example", priority = 1)
    public void groupExample() {
        System.out.println("This is a test");
    }


//    @Test(expectedExceptions = { IOException.class }, expectedExceptionsMessageRegExp = "Pass Message test")
//    public void exceptionTestOne() throws Exception {
//        throw new IOException("Pass Message test");
//    }
}