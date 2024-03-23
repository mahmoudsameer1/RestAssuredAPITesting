package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.User;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

public class UsersEndPoints {

	public static Response createUser(User Payload)
	{
		Response response = given()
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		  .body(Payload)
		.when()
		  .post(Routs.post_register_url);
		
		return response;
	}
	
	public static Response loginuser(User Payload)
	{
		Response response = given()
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		  .body(Payload)
		.when()
		  .post(Routs.post_login_url);
		
		return response;
	}
	
	public static Response userprofile(String accessToken) {
	    Response response = given()
	      .header("x-auth-token", accessToken)
	      .contentType(ContentType.JSON)
	      .accept(ContentType.JSON)
	    .when()
	      .get(Routs.get_profile_url);
	    
	    return response;
	}
	
	public static Response userprofile(User Payload)
	{
		Response response = given()
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		  .body(Payload)
		.when()
		  .post(Routs.post_register_url);
		
		return response;
	}
	
	public static Response updateProfile(User Payload, String accessToken)
	{
		Response response = given()
		  .header("x-auth-token", accessToken)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		  .body(Payload)
		.when()
		  .patch(Routs.patch_profile_url);
		
		return response;
	}
	
	public static Response changePassword(User Payload, String accessToken)
	{
		Response response = given()
		  .header("x-auth-token", accessToken)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		  .body(Payload)
		.when()
		  .post(Routs.post_changepwd_url);
		
		return response;
	}
	
}
