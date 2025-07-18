package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@TestMethodOrder(OrderAnnotation.class)
public class PetstoreUser {

    public static String username;
    public static String password;

    @Test
    @Order(1)
    public void creerUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/user";

        String body = null;
        try {
            body = new String(Files.readAllBytes(Paths.get("src/test/resources/Poststore/creerUser.json")));
            System.out.println(body);

            // ✅ Récupération du username depuis le contenu JSON (déjà lu dans body)
            JSONObject json = new JSONObject(body);
            username = json.getString("username");
            password = json.getString("password");
            System.out.println("Username extrait : " + username);
            System.out.println("Password extrait : " + password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .body(body)
                        .when()
                        .post("")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .assertThat()
                        .body(matchesJsonSchemaInClasspath("./Poststore/creerUserSchema.json"))
                        .extract()
                        .response(); // On extrait la réponse entière

    }


    @Test
    @Order(2)
    public void unUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/user";

        given()
                .log().all()
                .when()
                .get("/" + username)
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("./Poststore/oneUserSchema.json"));
    }

    @Test
    @Order(3)
    public void updateUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/user";

        String body = null;
        try {
            body = new String(Files.readAllBytes(Paths.get("src/test/resources/Poststore/updateUser.json")));
            System.out.println(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Response response =
                        given()
                        .header("Content-Type", "application/json")
                        .body(body)
                        .when()
                        .put("/" + username)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .assertThat()
                        .body(matchesJsonSchemaInClasspath("./Poststore/updateUserSchema.json"))
                        .extract()
                        .response(); // On extrait la réponse entière
    }


    @Test
    @Order(4)
    public void connexionUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/user/login";


        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .queryParam("username", username)
                        .queryParam("password", password)
                        .when()
                        .get("")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .assertThat()
                        .body(matchesJsonSchemaInClasspath("./Poststore/connexionUserSchema.json"))
                        .extract()
                        .response(); // On extrait la réponse entière
    }

    @Test
    @Order(5)
    public void deconnexionUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/user/logout";


        Response response =
                given()
                        .header("Content-Type", "application/json")
                        .when()
                        .get("")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .assertThat()
                        .body(matchesJsonSchemaInClasspath("./Poststore/deconnexionUserSchema.json"))
                        .extract()
                        .response(); // On extrait la réponse entière
    }

    @Test
    @Order(6)
    public void deleteUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/user";

        Response response =
                given()
                        .header("accept", "application/json")
                        .when()
                        .delete("/" + username)
                        .then()
                        .log().all()
                        .statusCode(200)
                        .assertThat()
                        .body(matchesJsonSchemaInClasspath("./Poststore/deleteUserSchema.json"))
                        .extract()
                        .response(); // On extrait la réponse entière
    }
}
