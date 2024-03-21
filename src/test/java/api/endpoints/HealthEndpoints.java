package api.endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class HealthEndpoints {
	
	public static Response checkHealth()
	{
		Response response = given()
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		.when()
		  .get(Routs.get_url);
		
		return response;
	}
}
