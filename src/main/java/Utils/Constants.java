package Utils;

public interface Constants {
    String PROPERTY_USERNAME = "username";
    String PROPERTY_PASSWORD = "password";
    String PROPERTY_CONFIRM_PASSWORD = "confirm";
    String PROPERTY_ERROR_MESSAGE = "errorMessage";
    String PROPERTY_VIDEO_ADDR = "video";
    String PROPERTY_SUBTITLE_ADDR = "subtitle";

    // DB
    String dbUsername = "root";
    String dbPassword = "root";
    String dbUrl = "jdbc:mysql://localhost:3306/VIDEO_SUBTITLES";

    //Directories
    String FILE_SAVE_DIRECTORY = "C:\\Users\\Iliyan\\Desktop\\WEB\\";
    //FFMPEG
    String BASE_FFMPEG_BIN_LOCATION = "C:\\Users\\Iliyan\\Desktop\\ffmpeg\\bin\\";
    String FFMPEG_LOCATION = BASE_FFMPEG_BIN_LOCATION + "ffmpeg";
    String FFPROBE_LOCATION = BASE_FFMPEG_BIN_LOCATION + "ffprobe";

    // URLS
    String LOGIN_AND_REGISTRATION_URL = "/login";
    String HOME_URL = "/";
    String EDITOR_URL = "/editor";

    //Errors
    String ERROR_LOGGING_IN = "Username or password were incorrect.";
    String ERROR_BLANK_FIELDS = "User or password was not set.";
    String ERROR_USERNAME_EXISTS = "Username %s already exists.";
    String ERROR_PASSWORDS_NOT_MATCH = "The passwords did not match.";
    String ERROR_INTERNAL_SERVER = "Something went wrong. Please try again.";
    String ERROR_UPLOAD_FAILED = "File upload failed. Please try again.";
}
