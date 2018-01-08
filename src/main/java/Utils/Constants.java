package Utils;

public interface Constants {
    String PROPERTY_USERNAME = "username";
    String PROPERTY_PASSWORD = "password";
    String PROPERTY_CONFIRM_PASSWORD = "confirm";

    //Directories
    String FILE_SAVE_DIRECTORY = "/Users/ilesev/Desktop/Web/";

    // URLS
    String LOGIN_AND_REGISTRATION_ERROR_URL = "/login?errorMessage=%s";
    String HOME_ERROR_URL = "/?errorMessage=%s";
    String HOME_SUCCESSFUL_UPLOAD = "/?uploadMessage=%s";

    //Success
    String FILE_UPLOADED = "File was uploaded successfully.";

    //Errors
    String ERROR_LOGGING_IN = "Username or password were incorrect.";
    String ERROR_BLANK_FIELDS = "User or password was not set.";
    String ERROR_USERNAME_EXISTS = "Username %s already exists.";
    String ERROR_PASSWORDS_NOT_MATCH = "The passwords did not match.";
    String ERROR_INTERNAL_SERVER = "Something went wrong. Please try again.";
    String ERROR_UPLOAD_FAILED = "File upload failed. Please try again.";
}
