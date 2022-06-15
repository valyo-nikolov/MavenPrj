package org.example.ten;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DataProviderMultipleParams {

    @DataProvider(name = "data-provider")
    public Object[][] dpMethod() {
        return new Object[][]{{2, 3, 5}, {5, 7, 9}};
    }

    @Test(dataProvider = "data-provider")
    public void myTest(int a, int b, int result) {
        int sum = a + b;

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(result, sum);
        softAssert.assertTrue(result == 9);

        softAssert.assertAll();
    }
}
