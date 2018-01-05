package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtils {
    public static final String dbUsername = "root";
    public static final String dbPassword = "root";
    public static final String dbUrl = "jdbc:mysql//localhost:3306/VIDEO_SUBTITLES";

    private static final String sqlUserSelect = "SELECT username FROM accounts WHERE username = ?";

    private static Connection getConnection() {
        try(Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            return connection;
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean userExists(String username) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlUserSelect);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.first();
    }
}
