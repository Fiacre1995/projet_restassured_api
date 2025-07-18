package tests;

import base.TestBase;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ListerCommande {

    @Test
    public void testGetUserWithToken() {

                given().
                log().all()
                .when()
                .get("https://simple-books-api.glitch.me/orders/")
                .then()
                        .log().all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("ListOrders.json"));
    }
}