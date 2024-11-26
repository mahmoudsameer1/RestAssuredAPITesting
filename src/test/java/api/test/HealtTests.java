package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.endpoints.HealthEndpoints;
import io.restassured.response.Response;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

public class HealtTests {

	
	@Test(description="Verify that the API is healthy")
	public void healthcheck() {
		
		Response response = HealthEndpoints.checkHealth();
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
}

