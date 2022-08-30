package org.example.ten;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviders {

    @DataProvider(name = "data-provider2")
    public Object[][] dpMethod2() {
        return new Object[][]{{"First-Value"}, {"Second-Value"}, {"Ivan"}, {"Milena"}};
    }

    @Test(dataProvider = "data-provider2")
    public void myTest2(String val) {
        System.out.println("Passed Parameter Is : " + val);
    }
}
