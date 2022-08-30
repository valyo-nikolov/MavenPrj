package org.example.ten.exercises;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomeworkCalculator {

    @DataProvider(name = "data-provider-homework")
    public Object[][] dpMethod() {
        return new Object[][]{{17, 5}, {3, 9}, {12, -6}, {1, 4}, {89, 111}};
    }

    @Test(groups = "sum", dataProvider = "data-provider-homework")
    public void sumABTest(int a, int b) {
        int result = a + b;
        System.out.format("%d + %d = %d\n", a, b, (a + b));
        Assert.assertEquals(result, Math.addExact(a, b));
    }

    @Test(groups = "minus", dataProvider = "data-provider-homework")
    public void minusABTest(int a, int b) {
        int result = a - b;
        System.out.format("%d - %d = %d\n", a, b, result);
        Assert.assertEquals(result, Math.subtractExact(a, b));
    }

    @Test(groups = "multiply", dataProvider = "data-provider-homework")
    public void multiplyABTest(int a, int b) {
        int result = a * b;
        System.out.format("%d * %d = %d\n", a, b, (a * b));
        Assert.assertEquals(result, Math.multiplyExact(a, b));
    }

    @Test(groups = "division", dataProvider = "data-provider-homework")
    public void divideABTest(int a, int b) {
        int result = a / b;
        System.out.format("%d / %d = %d\n", a, b, result);
        Assert.assertEquals(result, Math.floorDiv(a, b));
    }

    @Test(groups = "modulus", dataProvider = "data-provider-homework")
    public void modulusABTest(int a, int b) {
        int result = a % b;
        System.out.format("%d %% %d = %d\n", a, b, result);
        Assert.assertEquals(result, Math.floorMod(a, b));
    }
}
