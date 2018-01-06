package Utils;

import Entities.User;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DbUtils {
    public static final String dbUsername = "root";
    public static final String dbPassword = "root";
    public static final String dbUrl = "jdbc:mysql://localhost:3306/VIDEO_SUBTITLES";

    private static final String sqlUserSelect = "SELECT username FROM accounts WHERE username = ?";
    private static final String sqlUserSelectAll = "SELECT * FROM accounts";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    public static boolean userExists(String username) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = getConnection();
//        Statement statement = connection.createStatement();
        PreparedStatement statement = connection.prepareStatement(sqlUserSelect);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.first();
    }

    public static boolean getAllUsers() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlUserSelectAll);
        List<User> users = Mapper.map(resultSet, User.class);
        return true;
    }
}
