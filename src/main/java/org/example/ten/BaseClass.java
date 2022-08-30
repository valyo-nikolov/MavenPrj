package org.example.ten;

import org.testng.annotations.*;

public class BaseClass {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BaseClass's Before Suite");
    }
    @AfterSuite
    public void afterSuite() {
        System.out.println("BaseClass's After Suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BaseClass's Before Test");
    }
    @AfterTest
    public void afterTest() {
        System.out.println("BaseClass's After Test");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BaseClass's Before Test method");
    }
    @AfterMethod
    public void afterMethod() {
        System.out.println("BaseClass's After Test method");
    }
    @BeforeClass
    public void beforeClass() {
        System.out.println("BaseClass's Before Class method");
    }
    @AfterClass
    public void afterClass() {
        System.out.println("BaseClass's After Class method");
    }
}
