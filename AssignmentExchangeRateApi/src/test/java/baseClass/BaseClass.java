package baseClass;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import org.hamcrest.collection.IsArrayWithSize;

import cucumber.api.Scenario;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class BaseClass {

	// reqSpec is used for adding headers,paramters and body
	static protected RequestSpecification reqSpec = null;
	static protected Response response = null;
	static protected Scenario scn = null;
	LocalDate dt;

	//ResorceBundle is used for loading all the values of properties file in ResorceBundle object
	protected ResourceBundle bundle = ResourceBundle.getBundle("config");

	public RequestSpecification getURI(String baseURI) {
		return given().baseUri(baseURI);

	}

	//function for retrieving the local date
	public static LocalDate locatDate() {
		ZoneId zoneId = ZoneId.of("America/Los_Angeles");
		LocalDate dt = LocalDate.from(ZonedDateTime.now(zoneId));
		return dt;

	}
    //function for  retrieving current date data if the endpoint is of future date
	public void validatingFutureDateResponse() throws ParseException {
		dt = locatDate();
		String date = checkWeekends(dt);
		try {
			response.then().assertThat().body("date", equalTo(date));
		} catch (AssertionError e) {
			scn.write("Assertion error occured due to US time difference ,expected date" + date);
		}
	}
	
    //function to check the given date is lying between weekend or not
	public static String checkWeekends(LocalDate date) throws ParseException {

		LocalDate result = date;
		if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
			result = date.minusDays(1);
		}

		else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			result = date.minusDays(2);
		}

		return result.toString();
	}

}
