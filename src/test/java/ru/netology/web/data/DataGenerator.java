package ru.netology.web.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static UserData sendRequest(String status) {
        var userData = userGenerate(status);
        Gson gson = new Gson();
        String jsonUser = gson.toJson(userData);
        given()
                .spec(requestSpec)
                .body(jsonUser)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        return userData;
    }

    public static UserData userGenerate(String status) {
        Faker faker = new Faker(new Locale("en"));
        return new UserData(
                faker.name().username(),
                faker.internet().password(),
                status
        );
    }

    public static UserData badUser(String status) {
        return new UserData(
                "wrongLogin",
                "wrongPassword",
                status
        );
    }
}