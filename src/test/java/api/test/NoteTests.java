package api.test;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.NotesEndPoints;
import api.endpoints.UsersEndPoints;
import api.payload.Note;
import api.payload.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class NoteTests {
	
	Note notePayload;
	private static String ID;
	Faker faker;
	
	@BeforeClass
	public void setupData() {
		
		faker = new Faker();
		notePayload = new Note();
		notePayload.setTitle(faker.name().firstName()+"test1234");
		notePayload.setDescription("testdesc test 1234 1234");
		notePayload.setCategory("Work");
	}
	
	@Test(priority=1,description="Create a note and verify it’s created successfully")
	public void createNote() {
		
		String accessToken = UserTests.getAccessToken();
		
		Response response = NotesEndPoints.createNote(notePayload, accessToken);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		JsonPath jsonPathEvaluator = response.jsonPath();
		ID = jsonPathEvaluator.get("data.id");
	}
	
	@Test(priority=2,description="Create a note and verify it’s added to the list of all notes")
	public void getAllNotes() {
		
		String accessToken = UserTests.getAccessToken();
		
		
	    Response createResponse = NotesEndPoints.createNote(notePayload, accessToken);
	    createResponse.then().log().all();
	    Response getAllResponse = NotesEndPoints.getAllNotes(accessToken);
	    getAllResponse.then().log().all();
	    JsonPath jsonPathEvaluator = getAllResponse.jsonPath();
	    List<Map<String, Object>> notes = jsonPathEvaluator.getList("data");
	    boolean noteFound = false;
	    for (Map<String, Object> note : notes) {
	        if (note.get("title").equals(notePayload.getTitle()) &&
	            note.get("description").equals(notePayload.getDescription()) &&
	            note.get("category").equals(notePayload.getCategory())) {
	            noteFound = true;
	            break;
	        }
	    }
	    Assert.assertTrue(noteFound, "Added note is not retrieved in the list of notes.");
	}
	
	@Test(priority=3,description="Update a note and verify it’s updated successfully")
	public void updateNote() {
		
		boolean completed= true;
		String accessToken = UserTests.getAccessToken();
		
		Response response = NotesEndPoints.updateNote(ID,completed, accessToken);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority=4,description="Delete a note and verify it’s deleted successfully")
	public void deleteNote() {
		
		String accessToken = UserTests.getAccessToken();
		
		Response response = NotesEndPoints.deleteNote(ID,accessToken);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
}
