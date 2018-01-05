package Utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtils {
    public static final String dbUsername = "root";
    public static final String dbPassword = "root";
    public static final String dbUrl = "jdbc:mysql//localhost:3306/VIDEO_SUBTITLES";

    private static final String sqlUserSelect = "SELECT username FROM accounts WHERE username = asd";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    public static boolean userExists(String username) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
//        PreparedStatement statement = connection.prepareStatement(sqlUserSelect);
//        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery(sqlUserSelect);
        return resultSet.first();
    }
}
