package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UneCommande {

    @Test(dependsOnMethods = "testCreateOrderWithToken")
    public void testGetOrderById() {

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