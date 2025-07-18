package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class Posts {

    @Test
    public void ListPosts() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .log().all()
                .when()
                .get("/posts")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("./Posts/Posts.json"));
    }

    @Test
    public void onePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

                given()
                    .log().all()
                .when()
                    .get("/posts/" + id)
                .then()
                    .log().all()
                    .statusCode(200)
                    .assertThat()
                    .body(matchesJsonSchemaInClasspath("./Posts/PostId.json"));
    }

    private String postId;
    private int id = 1;

    @Test
    public void creerPost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        String body = "{ \"title\": \"Ingénieur Informatique\", \"body\": \"Poste pour des personnes ayant plus de 5 ans d'expériences\" }";
        Response response =
                given()
                        .body(body)
                        .when()
                        .post("/posts")
                        .then()
                        .log().all()
                        .statusCode(201)
                        .assertThat()
                        .body(matchesJsonSchemaInClasspath("./Posts/CreerPost.json"))
                        .extract()
                        .response(); // On extrait la réponse entière

        // ✅ Extraction de "orderId" depuis le corps JSON
        postId = response.jsonPath().getString("id");
        System.out.println("Order ID récupéré : " + postId);
    }
}
