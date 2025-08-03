package api.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class UserApiSteps {
    private Response response;
    private String userId;
    private String createdUserId;

    private final String APP_ID = "63a804408eb0cb069b57e43a";

    // --- GET User by ID ---

    @Given("I have a valid user ID")
    public void i_have_a_valid_user_id() {
        userId = "60d0fe4f5311236168a109d7";
    }

    @When("I send GET request to user API")
    public void i_send_get_request_to_user_api() {
        response = given()
                .header("app-id", APP_ID)
                .get("https://dummyapi.io/data/v1/user/" + userId);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
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

    // --- CREATE User ---

    @Given("I have user data to create")
    public void i_have_user_data_to_create() {
        // Tidak ada aksi, hanya log
        System.out.println("[DEBUG] Preparing user data for create");
    }

    @When("I send POST request to create user")
    public void i_send_post_request_to_create_user() {
        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@example.com";
        String createBody = String.format(
                "{\"firstName\":\"Test\",\"lastName\":\"User\",\"email\":\"%s\"}", uniqueEmail);

        response = given()
                .header("app-id", APP_ID)
                .contentType("application/json")
                .body(createBody)
                .post("https://dummyapi.io/data/v1/user/create");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());

        createdUserId = response.jsonPath().getString("id");
        assertNotNull(createdUserId);
    }

    @Then("the response should contain the created user ID")
    public void the_response_should_contain_the_created_user_id() {
        assertNotNull(createdUserId);
    }

    @Before("@update or @delete")
    public void setupUserBeforeUpdateOrDelete() {
        if (createdUserId == null) {
            i_have_user_data_to_create();
            i_send_post_request_to_create_user();
        }
    }

    // --- UPDATE User ---

    @Given("I have an existing user ID and new data")
    public void i_have_an_existing_user_id_and_new_data() {
        userId = createdUserId;
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

    // --- DELETE User ---

    @Given("I have an existing user ID to delete")
    public void i_have_an_existing_user_id_to_delete() {
        userId = createdUserId;
    }

    @When("I send DELETE request to delete user")
    public void i_send_delete_request_to_delete_user() {
        response = given()
                .header("app-id", APP_ID)
                .delete("https://dummyapi.io/data/v1/user/" + userId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
    }

    @Then("the response should confirm deletion")
    public void the_response_should_confirm_deletion() {
        assertEquals(200, response.getStatusCode());
    }
}
