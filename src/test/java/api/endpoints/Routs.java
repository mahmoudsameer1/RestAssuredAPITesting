package api.endpoints;

public class Routs {

	public static String base_url= "https://practice.expandtesting.com/notes/api";
	
	// Health
	public static String get_url = base_url+"/health-check";
	
	// Users
	public static String post_register_url = base_url+"/users/register";
	public static String post_login_url = base_url+"/users/login";
	public static String get_profile_url = base_url + "/users/profile";
	public static String patch_profile_url = base_url+"/users/profile";
	public static String post_forgotpwd_url = base_url+"/users/forgot-password";
	public static String post_verifyuser_url = base_url+"/users/verify-reset-password-token";
	public static String post_resetpwd_url = base_url+"/users/reset-password";
	public static String post_changepwd_url = base_url+"/users/change-password";
	public static String delete_logout_url = base_url+"/users/logout";
	public static String delete_user_url = base_url+"/users/delete-account";
	
	// Notes
	public static String post_newnote_url = base_url+"/notes";
	public static String get_allnote_url = base_url+"/notes";
	public static String get_note_url = base_url+"/notes/{id}";
	public static String put_note_url = base_url+"/notes/{id}";
	public static String patch_note_url = base_url+"/notes/{id}";
	public static String delete_note_url = base_url+"/notes/{id}";
}
