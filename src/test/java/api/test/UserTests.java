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

	    User loginPayload = new User();
	    loginPayload.setEmail(userPayload.getEmail());
	    loginPayload.setPassword(userPayload.getPassword());

	    Response loginResponse = UsersEndPoints.loginuser(loginPayload);
	    loginResponse.then().log().all();

	    JsonPath jsonPathEvaluator = loginResponse.jsonPath();
	    accessToken = jsonPathEvaluator.get("data.token");

	    Response profileResponse = UsersEndPoints.userprofile(accessToken);
	    profileResponse.then().log().all();
	    
	    Assert.assertEquals(profileResponse.getStatusCode(), 200);
	    Assert.assertTrue(profileResponse.getBody().asString().contains("Profile successful"));
	}
	
	@Test(priority=3,description="Update profile information and verify it’s updated successfully")
	public void updateprofile() {

	    userPayload.setName("test1234");
	    userPayload.setPhone("123456789");
	    userPayload.setCompany("mahmoud");

	    Response response = UsersEndPoints.updateProfile(userPayload, accessToken);
	    response.then().log().all();

	    Assert.assertEquals(response.getStatusCode(), 200);
	    
	    JsonPath jsonPathEvaluator = response.jsonPath();
	    Assert.assertEquals(jsonPathEvaluator.get("data.name"), "test1234");
	    Assert.assertEquals(jsonPathEvaluator.get("data.phone"), "123456789");
	    Assert.assertEquals(jsonPathEvaluator.get("data.company"), "mahmoud");
	}
	
	@Test(priority=4,description="Change the password and verify it’s updated successfully")
	public void changepassword() {
		
		userPayload.setCurrentPassword(userPayload.getPassword());
	    userPayload.setNewPassword("123456789");

	    Response response = UsersEndPoints.changePassword(userPayload, accessToken);
	    response.then().log().all();

	    Assert.assertEquals(response.getStatusCode(), 200);
	}

}
