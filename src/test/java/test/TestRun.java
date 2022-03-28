package test;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import spec.Specifications;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.*;

public class TestRun {
    private final static String URL = "https://gorest.co.in/";
    String token = Token.accessToken;
    String invalidToken = Token.invalidAccessToken;

    @Test
    public void getUsersTest() {

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(200));
        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .when()
                .get("/public/v2/users")
                .then()
                .body("data", hasSize(20));

    }

    @Test
    public void createDeleteUser() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(201));
        Map<String, String> user = new HashMap<>();

        user.put("name", "Grigor Stepanyan");
        user.put("email", "delete1@gmail.com");
        user.put("gender", "male");
        user.put("status", "active");
        Response response =
                given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("authorization", token)
                        .body(user)
                        .post("public/v2/users/")
                        .then()
                        // .statusCode(201)
                        .extract().response();

        int id = response.getBody().jsonPath().get("id");
        response.then().assertThat().headers("Location", Matchers.containsString(URL + "public/v2/users/")).extract().response();
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(200));

        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .get("public/v2/users/" + id)
                .then()
                // .statusCode(200)
                .body("name", equalTo("Grigor Stepanyan"),
                        "email", equalTo("delete1@gmail.com"),
                        "gender", equalTo("male"),
                        "status", equalTo("active"))
                .extract().response();

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(204));

        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .body(user)
                .delete("public/v2/users/" + id)
                .then();
        //.statusCode(204);
    }

    @Test
    public void updateUserName() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(201));
        Map<String, String> user = new HashMap<>();

        user.put("name", "Grigor Stepanyan");
        user.put("email", "new221mail@gmail.com");
        user.put("gender", "male");
        user.put("status", "active");
        Response response =
                given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("authorization", token)
                        .body(user)
                        .post("public/v2/users/")
                        .then()
                        //.statusCode(201)
                        .extract().response();

        int id = response.getBody().jsonPath().get("id");
        user.put("name", "new name");

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(200));
        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .body(user)
                .patch("public/v2/users/" + id)
                .then();
        //.statusCode(200);

        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .get("public/v2/users/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("new name"))
                .extract().response();

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(204));
        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .body(user)
                .delete("public/v2/users/" + id)
                .then()
                .statusCode(204);
    }

    @Test
    public void updateUserStatus() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(201));
        Map<String, String> user = new HashMap<>();

        user.put("name", "Grigor Stepanyan");
        user.put("email", "status@gmail.com");
        user.put("gender", "male");
        user.put("status", "active");
        Response response =
                given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("authorization", token)
                        .body(user)
                        .post("public/v2/users/")
                        .then()
                        // .statusCode(201)
                        .extract().response();

        int id = response.getBody().jsonPath().get("id");
        user.put("status", "inactive");

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(200));
        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .body(user)
                .patch("public/v2/users/" + id)
                .then();
        // .statusCode(200);

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(200));
        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .get("public/v2/users/" + id)
                .then()
                //.statusCode(200)
                .body("status", equalTo("inactive"))
                .extract().response();

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(204));
        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .body(user)
                .delete("public/v2/users/" + id)
                .then();
        //.statusCode(204);
    }

    @Test
    public void updateUserAll() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(201));
        Map<String, String> user = new HashMap<>();

        user.put("name", "Grigor Stepanyan");
        user.put("email", "allUser@gmail.com");
        user.put("gender", "male");
        user.put("status", "active");
        Response response =
                given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("authorization", token)
                        .body(user)
                        .post("public/v2/users/")
                        .then()
                        //.statusCode(201)
                        .extract().response();

        int id = response.getBody().jsonPath().get("id");
        response.then().assertThat().headers("Location", Matchers.containsString(URL + "public/v2/users/")).extract().response();
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(200));

        user.put("name", "Poghosuhi Poghosyan");
        user.put("email", "poghosuhi@example.com");
        user.put("gender", "female");
        user.put("status", "inactive");

        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .when()
                .body(user)
                .patch("public/v2/users/" + id)
                .then();
        // .statusCode(200);
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(200));
        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .get("public/v2/users/" + id)
                .then()
                //.statusCode(200)
                .body("name", equalTo("Poghosuhi Poghosyan"),
                        "status", equalTo("inactive"))
                .extract().response();

        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(204));
        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .body(user)
                .delete("public/v2/users/" + id)
                .then();
        //  .statusCode(204);
    }

    @Test
    public void CreatUserInvalidToken() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(401));
        Map<String, String> user = new HashMap<>();

        user.put("name", "Grigor Stepanyan");
        user.put("email", "Step@gmail.com");
        user.put("gender", "male");
        user.put("status", "active");
        Response response =
                given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("authorization", invalidToken)
                        .body(user)
                        .post("public/v2/users/")
                        .then()
                        // .statusCode(401)
                        .body("message", equalTo("Authentication failed"))
                        .extract().response();
    }

    @Test
    public void createUserInvalidEmail() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(422));
        Map<String, String> user = new HashMap<>();

        user.put("name", "Grigor Stepanyan");
        user.put("email", "Step.com");
        user.put("gender", "male");
        user.put("status", "active");
        Response response =
                given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("authorization", token)
                        .body(user)
                        .post("public/v2/users/")
                        .then()
                        //.statusCode(422)
                        .body("[0].field", equalTo("email"),
                                "[0].message", equalTo("is invalid"))
                        .extract().response();
    }

    @Test
    public void createUserInvalidStatus() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(422));
        Map<String, String> user = new HashMap<>();

        user.put("name", "Grigor Stepanyan");
        user.put("email", "Step.com");
        user.put("gender", "male");
        user.put("status", "invalid");
        Response response =
                given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("authorization", token)
                        .body(user)
                        .post("public/v2/users/")
                        .then()
                        // .statusCode(422)
                        .body("[0].message", equalTo("can't be blank"))
                        .extract().response();
    }

    @Test
    public void createUserInvalidGender() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(422));
        Map<String, String> user = new HashMap<>();

        user.put("name", "Grigor Stepanyan");
        user.put("email", "Step.com");
        user.put("gender", "not_oriented");
        user.put("status", "inactive");
        Response response =
                given()
                        .header("Accept", "application/json")
                        //.header("Content-Type", "application/json")
                        .header("authorization", token)
                        .body(user)
                        .post("public/v2/users/")
                        .then()
                        // .statusCode(422)
                        .body("[0].message", equalTo("can't be blank"))
                        .extract().response();
    }


    @Test
    public void createUserWrongId() {

        Map<String, String> user = new HashMap<>();
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(201));

        user.put("name", "Grigor Stepanyan");
        user.put("email", "31@test.ru");
        user.put("gender", "male");
        user.put("status", "active");
        Response response =
                given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("authorization", token)
                        .body(user)
                        .post("public/v2/users/")
                        .then()
                        //.statusCode(201)
                        .extract().response();

        response.then().assertThat().headers("Location", Matchers.containsString(URL + "public/v2/users/")).extract().response();
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUniq(404));

        given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", token)
                .get("public/v2/users/" + "12345")
                .then()
                //.statusCode(404)
                .body("message", equalTo("Resource not found"))
                .extract().response();
    }
}
