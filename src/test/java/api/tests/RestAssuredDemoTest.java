package api.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.example.common.Constants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;

import static io.restassured.RestAssured.*;

class LoginDto {
    private String usernameOrEmail;
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

public class RestAssuredDemoTest {

    String accessToken;
    String username;
    SecureRandom random;
    int userId;

    @BeforeTest
    public void setUp() {
        baseURI = "http://training.skillo-bg.com:3100";
        random = new SecureRandom();
    }

    @Parameters({"password"})
    @Test(groups = "signup")
    public void testSignUp(@Optional("pass1234") String password) {
        username = "auto_" + String.valueOf(random.nextInt(100000, 999999));

        JSONObject body = new JSONObject();
        body.put("username", username);
        body.put("birthDate", "05.28.1983");
        body.put("email", username + "@test.com");
        body.put("password", password);
        body.put("publicInfo", "something here");

        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/users")
                .then().log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), HttpStatus.SC_CREATED);

        userId = response.jsonPath().get("id");
        Assert.assertTrue(userId != 0);
    }

    @Parameters({"password"})
    @Test(groups = "signup", dependsOnMethods = "testSignUp")
    public void testLogin(@Optional("pass1234") String password) {
        LoginDto loginDto = new LoginDto();

        loginDto.setUsernameOrEmail(username);
        loginDto.setPassword(password);

        Response response = given()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .body(loginDto)
                .when()
                .post("/users/login")
                .then()
                .log()
                .all()
                .extract()
                .response();

        int statusCode = response.statusCode();

        Assert.assertEquals(statusCode, HttpStatus.SC_CREATED);
        String usernameFromResponse = response.jsonPath().get("user.username");
        Assert.assertEquals(usernameFromResponse, username);
        int userIdFromResponse = response.jsonPath().get("user.id");
        Assert.assertEquals(userIdFromResponse, userId);
        String token = response.jsonPath().get("token");
        Assert.assertFalse(token.isEmpty());
        accessToken = "Bearer " + token;
    }

    @Test(dependsOnGroups = "signup")
    public void testListAllUsers() {
        Response response = given()
                .log()
                .all()
                .header("Authorization", accessToken)
                .when()
                .get("/users")
                .then()
                .extract()
                .response();

        int statusCode = response.statusCode();

        Assert.assertEquals(statusCode, HttpStatus.SC_OK);

        ArrayList<Object> listOfUsers = response.jsonPath().get();

        Assert.assertFalse(listOfUsers.isEmpty());
    }

}
