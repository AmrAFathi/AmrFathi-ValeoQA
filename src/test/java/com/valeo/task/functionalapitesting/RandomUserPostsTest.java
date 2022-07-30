package com.valeo.task.functionalapitesting;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.json.JSONObject;

public class RandomUserPostsTest {
    int randomId;
    String UserEmail ,UserPosts;
    RequestSpecification requestSpec ;
    RequestSpecBuilder reqBuilder;

    @BeforeSuite
    public void generateRandomUserId () {

        //Picking a random number from the interval [1-10]
        randomId = (int) Math.floor(Math.random() * (10) + 1);
        System.out.println("The random user's Id is " + randomId);
        reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri("https://jsonplaceholder.typicode.com");
        reqBuilder.addHeader("Content-Type", "application/json");
        requestSpec = reqBuilder.build();

    }

    @Test(priority = 1)
    public void getRandomUserAndExtractHisEmail() {

        UserEmail = given().
                spec(requestSpec).

                when().
                get("/users/" + randomId).

                then().
                statusCode(200).
                extract().path("email").toString();

        System.out.println("The random user's email is " + UserEmail);
    }

    @Test(priority = 2)
    public void getUserPostsAndVerifyIds() {

        UserPosts = given().
                spec(requestSpec).

                when().
                get("/posts/" + randomId).

                then().
                statusCode(200).
                //Assert that the found posts' IDs are in the interval[1-100]
                assertThat().body("id", greaterThanOrEqualTo(1)).
                assertThat().body("id", lessThanOrEqualTo(100)).

                extract().path("body").toString();

        System.out.println("The random user's posts are " + "\n" + "[ " + UserPosts + " ]");
    }

    @Test(priority = 3)
    public void createNewPostAndExpectSuccess() throws JSONException {

        JSONObject request = new JSONObject();

        request.put("userId", randomId)
                .put("title", "New Post")
                .put("body", "Hello, It's Amr Fathi. I am a senior software quality engineer.");

        ValidatableResponse body = given().
                spec(requestSpec).
                body(request.toString()).
                log().all().

                when().
                post("/posts/").

                //Assert that the created post's id is greater than 100
                then().
                statusCode(201).
                assertThat().body("id", greaterThan(100)).
                log().all();
    }
}