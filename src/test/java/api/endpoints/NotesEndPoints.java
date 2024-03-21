package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.Note;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class NotesEndPoints {
	
	public static Response createNote(Note Payload, String accessToken)
	{
		Response response = given()
		  .header("x-auth-token", accessToken)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		  .body(Payload)
		.when()
		  .post(Routs.post_newnote_url);
		
		return response;
	}
	
	public static Response getAllNotes(String accessToken)
	{
		Response response = given()
		  .header("x-auth-token", accessToken)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		.when()
		  .get(Routs.get_allnote_url);
		
		return response;
	}
	
	public static Response updateNote(String id, boolean completed, String accessToken)
	{
		Response response = given()
		  .header("x-auth-token", accessToken)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		  .pathParam("id", id)
		  .body("{\"completed\": " + completed + "}")
		.when()
		  .patch(Routs.patch_note_url);
		
		return response;
	}
	
	public static Response deleteNote(String id,String accessToken)
	{
		Response response = given()
		  .header("x-auth-token", accessToken)
		  .contentType(ContentType.JSON)
		  .accept(ContentType.JSON)
		  .pathParam("id", id)
		.when()
		  .delete(Routs.delete_note_url);
		
		return response;
	}
}
