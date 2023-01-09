package dynamicFunctions;

import io.restassured.response.Response;
import spec.BaseURL;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static test.Token.accessToken;


public class API_Function {
    static String baseURL = BaseURL.baseURL;

    public static Response getRequest(String url) {
        return given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .get(baseURL + url);
    }

    public static Response postRequest(Map<String, String> user, String url) {
        return given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", accessToken)
                .body(user)
                .post(baseURL + url);
    }

    public static Response deleteRequest(Map<String, String> user, String url) {
        return given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", accessToken)
                .body(user)
                .delete(baseURL + url);
    }

    public static Response patchRequest(Map<String, String> user, String url) {
        return given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("authorization", accessToken)
                .when()
                .body(user)
                .patch(baseURL + url);
    }

}
