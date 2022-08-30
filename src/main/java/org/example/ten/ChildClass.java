package org.example.ten;

import org.testng.annotations.*;

public class ChildClass extends BaseClass{

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("ChildClass's Before Suite");
    }
    @AfterSuite
    public void afterSuite() {
        System.out.println("ChildClass's After Suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("ChildClass's Before Test");
    }
    @AfterTest
    public void afterTest() {
        System.out.println("ChildClass's After Test");
    }

    @BeforeMethod
    public void beforeChildMethod() {
        System.out.println("ChildClass's Before Test method");
    }

    @AfterMethod
    public void afterChildMethod() {
        System.out.println("ChildClass's After Test method");
    }

    @BeforeClass
    public void beforeChildClass() {
        System.out.println("ChildClass's Before Class method");
    }

    @AfterClass
    public void afterChildClass() {
        System.out.println("ChildClass's After Class method");
    }

    @Test
    public void testCase() {
        System.out.println("===== Executing actual test ======");
    }
}
