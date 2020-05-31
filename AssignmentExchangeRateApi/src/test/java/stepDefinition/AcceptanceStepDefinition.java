package stepDefinition;

import static org.hamcrest.Matchers.*;

import java.text.ParseException;

import baseClass.BaseClass;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AcceptanceStepDefinition extends BaseClass {

	// ******************************hooks********

	@Before
	public void SetUp(Scenario s) {
		scn = s;
	}

	@After
	public void CleanUp() {
		reqSpec = null;
		response = null;
	}

	// *******************Given********************
	@Given("Foreign Exchange Rates API is up and running")
	public void givenAPI() {
		// Putting base URI value in requestSpecification object
		scn.write("URI under test -" + bundle.getString("uri"));
		reqSpec = getURI(bundle.getString("uri"));

	}

	// ***********When*****************************
	@When("Hit the API with end point as {string}")
	public void hitAPI(String endPoint) {
		scn.write("Hiting API with specific endpoint " + endPoint + " and getting response");
		// Getting response through get method
		response = reqSpec.get(endPoint);
		scn.write("Request of the response is "+response);

	}

	// *******Then*********************************

	@Then("API should return the status code as {int}")
	public void getStatusCode(Integer expected) {
		// Assertion of status code
		scn.write("Extracting status code to validate");
		// Here we are validating the status code and response format of the response received
		response.then().assertThat().statusCode(expected).and().header("Content-type", "application/json");
		scn.write("Reponse code recived "+expected);

	}

	@Then("API should return the current date rates")
	public void validateDate() throws ParseException {
		scn.write("Verifying current date data should return even if the endpoint is of future date");
		// function for retrieving current date data if the endpoint is of future date
		validatingFutureDateResponse();

	}

	@Then("base value should be {string}")
	public void validateBase(String expectedBase) {

		scn.write("Verifying base value");

		response.then().assertThat().body("base", equalTo(expectedBase));
		scn.write("Base Value Received "+expectedBase);

	}

	// *******And*********************************
	@And("Error message should displayed as {string}")
	public void validateErrorMessage(String expectedError) {
		scn.write("Verifying error message for incorrect endpoint");
		response.then().assertThat().body("error", equalTo(expectedError));
		scn.write("Error message received"+expectedError);
	}

	
	}
