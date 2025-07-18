package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.UUID;

public class TokenManager {

    private static String token;

    public static String getToken() {
        if (token == null) {
            token = generateToken();
        }
        return token;
    }

    public static String generateRandomEmail() {
        return "user" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
    }

    public static String generateRandomPassword() {
        return "pass" + UUID.randomUUID().toString().substring(0, 8);
    }

    private static String generateToken() {
        String email = generateRandomEmail();
        String password = generateRandomPassword();
        String loginPayload = String.format("{ \"clientName\": \"%s\", \"clientEmail\": \"%s\" }", password, email);
        //String loginPayload = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        Response response = RestAssured
                .given()
                    .log().all()
                    .baseUri("https://simple-books-api.glitch.me/")
                    .header("Content-Type", "application/json")
                    .body(loginPayload)
                .when()
                    .post("api-clients/")
                .then()
                    .statusCode(201)
                    .extract()
                    .response();

        return response.jsonPath().getString("accessToken");
    }
}