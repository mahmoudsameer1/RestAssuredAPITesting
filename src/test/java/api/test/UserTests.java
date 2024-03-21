package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UsersEndPoints;
import api.payload.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserTests {
	
	User userPayload;
	private static String accessToken;
	Faker faker;

    public static String getAccessToken() {
        return accessToken;
    }
	
	@BeforeClass
	public void setupData() {
		
		faker = new Faker();
		userPayload = new User();
		userPayload.setName("test21");
		userPayload.setEmail(faker.name().firstName()+"@test.test");
		userPayload.setPassword("test123");
	}
	
	@Test(priority=1,description="Register a new user and verify it’s created")
	public void createuser() {
		
		Response response = UsersEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertTrue(response.getBody().asString().contains("User account created successfully"));
	}
	
	
	@Test(priority=2,description="Log in with a user and verify the profile information")
	public void userlogin() {
	    // Create a new User object with the same email and password
	    User loginPayload = new User();
	    loginPayload.setEmail(userPayload.getEmail());
	    loginPayload.setPassword(userPayload.getPassword());

	    // Call loginuser() method with the loginPayload
	    Response loginResponse = UsersEndPoints.loginuser(loginPayload);
	    loginResponse.then().log().all();

	    // Extract the access token from the login response
	    JsonPath jsonPathEvaluator = loginResponse.jsonPath();
	    accessToken = jsonPathEvaluator.get("data.token");

	    // Call userprofile() method with the access token in the header
	    Response profileResponse = UsersEndPoints.userprofile(accessToken);
	    profileResponse.then().log().all();
	    
	    Assert.assertEquals(profileResponse.getStatusCode(), 200);
	    Assert.assertTrue(profileResponse.getBody().asString().contains("Profile successful"));
	}
	
	@Test(priority=3,description="Update profile information and verify it’s updated successfully")
	public void updateprofile() {
		
	    // Modify the userPayload with updated information
	    userPayload.setName("test1234");
	    userPayload.setPhone("123456789");
	    userPayload.setCompany("mahmoud");
	    
	    // Call updateProfile() method with the updated userPayload
	    Response response = UsersEndPoints.updateProfile(userPayload, accessToken);
	    response.then().log().all();
	    
	    // Assert that the response status code is 200
	    Assert.assertEquals(response.getStatusCode(), 200);
	    
	    JsonPath jsonPathEvaluator = response.jsonPath();
	    Assert.assertEquals(jsonPathEvaluator.get("data.name"), "test1234");
	    Assert.assertEquals(jsonPathEvaluator.get("data.phone"), "123456789");
	    Assert.assertEquals(jsonPathEvaluator.get("data.company"), "mahmoud");
	}
	
	@Test(priority=4,description="Change the password and verify it’s updated successfully")
	public void changepassword() {
		
	    // Modify the userPayload with updated information
		userPayload.setCurrentPassword(userPayload.getPassword());
	    userPayload.setNewPassword("123456789");
	    
	    // Call updateProfile() method with the updated userPayload
	    Response response = UsersEndPoints.changePassword(userPayload, accessToken);
	    response.then().log().all();
	    
	    // Assert that the response status code is 200
	    Assert.assertEquals(response.getStatusCode(), 200);
	}

}
