package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinitions extends Utils {
	RequestSpecification res;
	ResponseSpecification resSpec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;

	@Given("Add Place Payload with {string}	{string}	{string}")
	public void add_place_payload_with(String name, String language, String address)
			throws IOException {
		// building response
		res = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
	}

	@When("users calls {string} with {string} http request")
	public void users_calls_with_http_request(String resource, String method) {
		//constructor will be passed with value of resources which you pass
		APIResources resourcesAPI = APIResources.valueOf(resource);
		System.out.println(resourcesAPI.getRsources());

		resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST"))
			response = res.when().post(resourcesAPI.getRsources());
		else if(method.equalsIgnoreCase("GET"))
			response = res.when().get(resourcesAPI.getRsources());
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);

	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		assertEquals(getJsonPath(response, keyValue), expectedValue);
	}
	
	@Then("verify place_Id created maps to {string} using {string}\"")
	//get API call 
	public void verify_place_id_created_maps_to_using(String expectedNmae, String resource) throws IOException {
		// requestSpec
		place_id=getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		users_calls_with_http_request(resource, "GET");
		String actualname=getJsonPath(response, "name");
		assertEquals(actualname, expectedNmae);
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    res=given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}
}
