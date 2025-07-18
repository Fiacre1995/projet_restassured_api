package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utils.TokenManager;

public class TestBase {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://simple-books-api.glitch.me/";
        RestAssured.requestSpecification = RestAssured
                .given()
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .header("Content-Type", "application/json");
    }
}