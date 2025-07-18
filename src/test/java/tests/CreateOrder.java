package tests;

import base.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class CreateOrder extends TestBase {

    private String orderId;

    @Test
    public void testCreateUserWithToken() {
        String body = "{ \"bookId\": 1, \"customerName\": \"Une si longue lettre\" }";
        Response response =
                given()
                        .body(body)
                        .when()
                        .post("orders/")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .body("created", equalTo(true))
                        .assertThat()
                        .body(matchesJsonSchemaInClasspath("CreateOrder.json"))
                        .extract()
                        .response(); // On extrait la réponse entière

        // ✅ Extraction de "orderId" depuis le corps JSON
        orderId = response.jsonPath().getString("orderId");

        System.out.println("Order ID récupéré : " + orderId);

    }
}