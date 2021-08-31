package stepdefinitions;

import static org.junit.Assert.assertTrue;

import java.util.List;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.DecoderConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StepDefinition {

	private static RequestSpecification requestSpec;
	private Response response;
	private static String user_agent_key = "User-Agent";
	private static String user_agent_value = "PostmanRuntime/7.28.4";

	@Given("I am authorized user on colourlovers")
	public void setPreReq() {
		RestAssured.config()
				.decoderConfig(DecoderConfig.decoderConfig().defaultCharsetForContentType("UTF-16", "application/xml"));
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.addHeader(user_agent_key, user_agent_value);
		requestSpec = builder.build();
	}

	@When("I send request to {string}")
	public void sendRequest(String url) {
		response = RestAssured.given().spec(requestSpec).log().body().when().log().body().get(url).then()
				.statusCode(200).log().body().extract().response();
	}

	@Then("Response status code is {int}")
	public void testSuccessCode(Integer code) {
		response.then().statusCode(code);
	}

	@And("I get XML response with numViews are greater than {int}")
	public void testThatNumViewsAreGreaterThan_400(Integer value) {
		List<String> numViews = XmlPath.from(response.asString()).getList("patterns.pattern.numViews");
		for (String numView : numViews) {
			System.out.println(Integer.parseInt(numView));
			assertTrue((Integer.parseInt(numView)) > value);
		}
	}
}
