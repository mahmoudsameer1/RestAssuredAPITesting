package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.endpoints.HealthEndpoints;
import io.restassured.response.Response;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

public class HealtTests {

	
	@Test(description="Verify that the API is healthy")
	public void createuser() {
		
        // Positive test case: Verify that the health check endpoint returns a 200 status code
		
		Response response = HealthEndpoints.checkHealth();
		response.then().log().all();
        attachResponseToAllure(response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
    @Attachment(value = "Response Body", type = "text/plain")
    public byte[] attachResponseToAllure(String responseBody) {
        return responseBody.getBytes();
    }
}
