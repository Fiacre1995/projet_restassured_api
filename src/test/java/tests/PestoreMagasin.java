package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PestoreMagasin {

    public static String idCommande;

    @Test
    @Order(1)
    public void creerCommande() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/store/order";

        String body = null;
        try {
            body = new String(Files.readAllBytes(Paths.get("src/test/resources/PoststoreMagasin/creerCommande.json")));
            System.out.println(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Response response =
                given()
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(body)
                        .when()
                        .post("")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .assertThat()
                        .body(matchesJsonSchemaInClasspath("./PoststoreMagasin/creerCommandeSchema.json"))
                        .extract()
                        .response(); // On extrait la réponse entière

        // ✅ Extraction de "orderId" depuis le corps JSON
        idCommande = response.jsonPath().getString("id");
        System.out.println("Order ID récupéré : " + idCommande);
    }

    @Test
    @Order(2)
    public void uneCommande() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/store/order";

        given()
                .log().all()
                .when()
                .get("/" + idCommande)
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("./PoststoreMagasin/uneCommandeSchema.json"));
    }

    @Test
    @Order(3)
    public void inventaireCommande() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/store/inventory";

        given()
                .log().all()
                .when()
                .get("")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("./PoststoreMagasin/uneCommandeSchema.json"));
    }


    @Test
    @Order(4)
    public void supprimerCommande() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/store/order";

        given()
                .log().all()
                .when()
                .delete("/" + idCommande)
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("./PoststoreMagasin/supprimerCommandeSchema.json"));
    }


}
