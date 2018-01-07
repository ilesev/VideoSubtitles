package Utils;

public interface Constants {
    String PROPERTY_USERNAME = "username";
    String PROPERTY_PASSWORD = "password";
    String PROPERTY_CONFIRM_PASSWORD = "confirm";

    // URLS
    String LOGIN_AND_REGISTRATION_ERROR_URL = "/login?errorMessage=%s";

    //Errors
    String ERROR_LOGGING_IN = "Username or password were incorrect.";
    String ERROR_BLANK_FIELDS = "User or password was not set.";
    String ERROR_USERNAME_EXISTS = "Username %s already exists.";
    String ERROR_PASSWORDS_NOT_MATCH = "The passwords did not match.";
    String ERROR_INTERNAL_SERVER = "Something went wrong. Please try again.";
}
