package stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import spec.BaseURL;
import test.Token;

import java.util.HashMap;
import java.util.Map;

import static dynamicFunctions.API_Function.*;
import static dynamicFunctions.Utils.getRandomEmail;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class MyStepdefs {

    private static int id;
    private static Map<String, String> user;
    private static Response response;
    private static String email;
    private final String baseURL = BaseURL.baseURL;
    private final String invalidToken = Token.invalidAccessToken;


    @Given("I create new user")
    public void iCreateNewUser() {

        email = getRandomEmail();
        user = new HashMap<>();
        user.put("name", "Grigor Stepanyan");
        user.put("email", email);
        user.put("gender", "male");
        user.put("status", "active");

        response =
                postRequest(user, "/public/v2/users")
                        .then()
                        .extract().response();
    }

    @And("I assert that the Location header contains the URL pointing to the newly created resource")
    public void iAssertThatTheLocationHeaderContainsTheURLPointingToTheNewlyCreatedResource() {

        id = response.getBody().jsonPath().get("id");
        response.then().assertThat().headers("Location", Matchers.containsString(baseURL + "/public/v2/users/" + id)).extract().response();
    }

    @And("I get newly created user request")
    public void iGetNewlyCreatedUserRequest() {

        response = getRequest("/public/v2/users/" + id)
                .then()
                .extract().response();
    }

    @And("I assert that created user has details")
    public void iAssertThatCreatedUserHasDetails() {

        response.then()
                .body("name", equalTo("Grigor Stepanyan"),
                        "email", equalTo(email),
                        "gender", equalTo("male"),
                        "status", equalTo("active"));
    }

    @And("I remove the newly created user")
    public void iRemoveTheNewlyCreatedUser() {

        response = deleteRequest(user, "/public/v2/users/" + id)
                .then().extract().response();
    }

    @Given("I get users list")
    public void iGetUsers() {

        response = getRequest("/public/v2/users")
                .then()
                .extract().response();
    }

    @And("I assert to see {int} status code")
    public void iAssertToSeeStatusCode(int statusCode) {

        response.then()
                .statusCode(statusCode);
    }

    @And("I assert to see {int} users in list")
    public void checkUsersLength(int usersCount) {

        response.then()
                .body("data", hasSize(usersCount));
    }

    @Given("I Update the user name with PATCH")
    public void iUpdateTheUserNameWithPATCH() {

        user = new HashMap<>();
        user.put("name", "Grigor Stepanyan");
        user.put("email", email);
        user.put("gender", "male");
        user.put("status", "active");

        response = postRequest(user, "/public/v2/users")
                .then()
                .extract().response();

        id = response.getBody().jsonPath().get("id");
        user.put("name", "new name");

        patchRequest(user, "/public/v2/users")
                .then().extract().response();

    }

    @And("I assert that the user name has been updated")
    public void iAssertThatTheUserNameHasBeenUpdated() {
//        given()
//                .header("Accept", "application/json")
//                .header("Content-Type", "application/json")
//                .header("authorization", token)
//                .when()
//                .body(user)
//                .patch(URL + "/public/v2/users/" + id)
        patchRequest(user, "/public/v2/users/" + id)
                .then().body("name", equalTo("new name"))
                .extract().response().then().extract().response();
    }

    @Given("I Update the user status with PATCH")
    public void iUpdateTheUserStatusWithPATCH() {
        user = new HashMap<>();
        user.put("name", "Grigor Stepanyan");
        user.put("email", getRandomEmail());
        user.put("gender", "male");
        user.put("status", "active");
        response = postRequest(user, "/public/v2/users")
                .then()
                .extract().response();

        id = response.getBody().jsonPath().get("id");
        user.put("status", "inactive");

        patchRequest(user, "/public/v2/users/" + id)
                .then().extract().response();
    }

    @And("I assert that the user status has been updated")
    public void iAssertThatTheUserStatusHasBeenUpdated() {

        getRequest("/public/v2/users/" + id)
                .then()
                .body("status", equalTo("inactive"))
                .extract().response();
    }

    @Given("I Update the user all details")
    public void iUpdateTheUserAllDetails() {
        user = new HashMap<>();
        user.put("name", "Grigor Stepanyan");
        user.put("email", getRandomEmail());
        user.put("gender", "male");
        user.put("status", "active");
        response = postRequest(user, "/public/v2/users/")
                .then()
                .extract().response();

        id = response.getBody().jsonPath().get("id");
        user.put("name", "Poghosuhi Poghosyan");
        user.put("email", getRandomEmail());
        user.put("gender", "female");
        user.put("status", "inactive");

        patchRequest(user, "/public/v2/users/" + id)
                .then().extract().response();

    }

    @And("I assert that the user status and name has been updated")
    public void iAssertThatTheUserStatusAndNameHasBeenUpdated() {

        getRequest("/public/v2/users/" + id)
                .then()
                .body("name", equalTo("Poghosuhi Poghosyan"),
                        "status", equalTo("inactive"))
                .extract().response();

    }

    @Given("I create the new user with an invalid token")
    public void iCreateTheNewUserWithAnInvalidToken() {
        Map<String, String> user = new HashMap<>();

        user.put("name", "Grigor Stepanyan");
        user.put("email", "Step@gmail.com");
        user.put("gender", "male");
        user.put("status", "active");
        response =
                given()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .header("authorization", invalidToken)
                        .body(user)
                        .post(baseURL + "/public/v2/users/");
    }

    @And("I assert that the error message")
    public void iAssertThatTheErrorMessage() {
        response.then()
                .body("message", equalTo("Authentication failed"));
    }

    @Given("I create a new user with invalid email address")
    public void iCreateANewUserWithInvalidEmailAddress() {
        user = new HashMap<>();
        user.put("name", "Grigor Stepanyan");
        user.put("email", "gmail.com");
        user.put("gender", "male");
        user.put("status", "active");
        response = postRequest(user, "/public/v2/users")
                .then()
                .extract().response();

    }

    @And("I assert that the error message for invalid email")
    public void iAssertThatTheErrorMessageForInvalidEmail() {
        response.then()
                .body("[0].field", equalTo("email"),
                        "[0].message", equalTo("is invalid"))
                .extract().response();
    }

    @Given("I create a new user with invalid status")
    public void iCreateANewUserWithInvalidStatus() {
        user = new HashMap<>();
        user.put("name", "Grigor Stepanyan");
        user.put("email", getRandomEmail());
        user.put("gender", "male");
        user.put("status", "acte");
        response = postRequest(user, "/public/v2/users")
                .then()
                .extract().response();
    }

    @And("I assert that the error message for invalid status")
    public void iAssertThatTheErrorMessageForInvalidStatus() {
        response.then()
                .body("[0].message", equalTo("can't be blank"))
                .extract().response();
    }

    @Given("I create a new user with invalid gender")
    public void iCreateANewUserWithInvalidGender() {
        user = new HashMap<>();
        user.put("name", "Grigor Stepanyan");
        user.put("email", getRandomEmail());
        user.put("gender", "uni");
        user.put("status", "active");
        response = postRequest(user, "/public/v2/users")
                .then()
                .extract().response();
    }

    @And("I assert that the error message for invalid gender")
    public void iAssertThatTheErrorMessageForInvalidGender() {
        response.then()
                .body("[0].message", equalTo("can't be blank, can be male of female"))
                .extract().response();
    }

    @And("I get user with invalid id")
    public void iGetUserWithInvalidId() {
        response = getRequest("/public/v2/users/" + "121112")
                .then()
                .extract().response();
    }

    @And("I assert that the error message for an invalid id get request")
    public void iAssertThatTheErrorMessageForAnInvalidIdGetRequest() {
        response.then()
                .body("message", equalTo("Resource not found"))
                .extract().response();
    }
}
