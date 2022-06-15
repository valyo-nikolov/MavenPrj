package org.example.ten;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParametersExample {

    @Test(groups = "parameters")
    @Parameters({ "suiteParam"})
    void singleParamTest(String suiteParam){
        System.out.println("Test singleParamTest suite param is: " + suiteParam);
    }

    @Test(groups = "parameters")
    @Parameters({ "suiteParam", "testParam" })
    void multiParamTest(String suiteParam, String testParam) {
        System.out.println("Test multiParamTest suite param is: " + suiteParam);
        System.out.println("Test multiParamTest param is: " + testParam);
    }

    @Parameters({ "optionalParam", "number" })
    @Test
    public void optionTest(@Optional("default") String optionalParam, @Optional("5") int number) {
        System.out.println("This is: " + optionalParam + " " + number);
    }
}
