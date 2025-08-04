package api.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class UserApiSteps {
    private Response response;
    private String userId;
    private String createdUserId;
    private boolean isInvalidDataTest = false;

    private final String APP_ID = "63a804408eb0cb069b57e43a";

    @Given("I have a valid user ID")
    public void i_have_a_valid_user_id() {
        userId = "60d0fe4f5311236168a109d7";
        isInvalidDataTest = false;
    }

    @When("I send GET request to user API")
    public void i_send_get_request_to_user_api() {
        response = given()
                .header("app-id", APP_ID)
                .get("https://dummyapi.io/data/v1/user/" + userId);
        assertNotNull(response);
    }

    @Then("the user response status should be 200")
    public void the_user_response_status_should_be_200() {
        assertEquals(200, response.getStatusCode());
    }

    @Then("the response should contain user details")
    public void the_response_should_contain_user_details() {
        String id = response.jsonPath().getString("id");
        assertNotNull(id);
        assertEquals(userId, id);
    }

    @Given("I have user data to create")
    public void i_have_user_data_to_create() {
        isInvalidDataTest = false;
    }

    @Given("I have invalid user data to create")
    public void i_have_invalid_user_data_to_create() {
        isInvalidDataTest = true;
    }

    @When("I send POST request to create user")
    public void i_send_post_request_to_create_user() {
        String body;
        if (!isInvalidDataTest) {
            String uniqueEmail = "testuser" + System.currentTimeMillis() + "@example.com";
            body = String.format("{\"firstName\":\"Test\",\"lastName\":\"User\",\"email\":\"%s\"}", uniqueEmail);
        } else {
            body = "{}";
        }
        response = given()
                .header("app-id", APP_ID)
                .contentType("application/json")
                .body(body)
                .post("https://dummyapi.io/data/v1/user/create");
        assertNotNull(response);
    }

    @Then("the response should contain the created user ID")
    public void the_response_should_contain_the_created_user_id() {
        assertEquals(200, response.getStatusCode());
        createdUserId = response.jsonPath().getString("id");
        assertNotNull(createdUserId);
    }

    @Then("the user response status should be 400")
    public void the_user_response_status_should_be_400() {
        int statusCode = response.getStatusCode();
        System.out.println("DEBUG POST create invalid data status: " + statusCode);
        System.out.println("DEBUG Response body: " + response.getBody().asString());

        // Bisa 400, 422, 403 tergantung API
        assertTrue("Expected 400 or 422 or 403",
                statusCode == 400 || statusCode == 422 || statusCode == 403);
    }

    @Before("@update or @delete")
    public void setupUserBeforeUpdateOrDelete() {
        if (createdUserId == null) {
            i_have_user_data_to_create();
            i_send_post_request_to_create_user();
            the_response_should_contain_the_created_user_id();
        }
    }

    @Given("I have an existing user ID and new data")
    public void i_have_an_existing_user_id_and_new_data() {
        userId = createdUserId;
        isInvalidDataTest = false;
    }

    @When("I send PUT request to update user")
    public void i_send_put_request_to_update_user() {
        String updateBody = "{\"firstName\":\"Updated\",\"lastName\":\"User\",\"email\":\"updateduser@example.com\"}";
        response = given()
                .header("app-id", APP_ID)
                .contentType("application/json")
                .body(updateBody)
                .put("https://dummyapi.io/data/v1/user/" + userId);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
    }

    @Then("the response should contain updated user data")
    public void the_response_should_contain_updated_user_data() {
        assertEquals(200, response.getStatusCode());
        assertEquals("Updated", response.jsonPath().getString("firstName"));
    }

    @Given("I have an existing user ID to delete")
    public void i_have_an_existing_user_id_to_delete() {
        userId = createdUserId;
        isInvalidDataTest = false;
    }

    @When("I send DELETE request to delete user")
    public void i_send_delete_request_to_delete_user() {
        response = given()
                .header("app-id", APP_ID)
                .delete("https://dummyapi.io/data/v1/user/" + userId);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        createdUserId = null;
    }

    @Then("the response should confirm deletion")
    public void the_response_should_confirm_deletion() {
        assertEquals(200, response.getStatusCode());
    }
}
