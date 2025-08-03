package api.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class TagApiSteps {
    private Response response;

    @When("I send GET request to tag API")
    public void i_send_get_request_to_tag_api() {
        response = given()
                .header("app-id", "63a804408eb0cb069b57e43a")
                .get("https://dummyapi.io/data/v1/tag");
        assertNotNull("Response is null", response);
    }

    @Then("the tag response status should be 200")
    public void the_tag_response_status_should_be_200() {
        assertEquals("Status code not 200", 200, response.getStatusCode());
    }

    @Then("the response should contain a list of tags")
    public void the_response_should_contain_a_list_of_tags() {
        assertNotNull("Response body is null", response.getBody());
        assertTrue("Tag list is empty", response.jsonPath().getList("data").size() > 0);
    }
}
