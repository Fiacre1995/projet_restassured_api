package tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class Books {

    @Test
    public void ListBooks() {
        RestAssured.baseURI = "https://simple-books-api.glitch.me/";

        given()
                .log().all()
                .when()
                .get("books/")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("Books.json"));
    }

    @Test
    public void oneBook() {
        RestAssured.baseURI = "https://simple-books-api.glitch.me/";

        given()
                .log().all()
                .when()
                .get("books/1")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("BookId.json"));
    }
}
