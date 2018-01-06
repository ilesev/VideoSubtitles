package Services;

import Entities.User;
import Utils.Encryptor;
import Utils.Mapper;

import javax.faces.bean.ApplicationScoped;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@ApplicationScoped
public class DataBase {
    public static final String dbUsername = "root";
    public static final String dbPassword = "root";
    public static final String dbUrl = "jdbc:mysql://localhost:3306/VIDEO_SUBTITLES";

    private static final String sqlUserSelect = "SELECT username FROM accounts WHERE username = ?";
    private static final String sqlRegisterUser = "INSERT INTO accounts(username, password, salt)" +
                                                    "VALUES(?,?,?)";

    private Connection getConnection() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    public boolean userExists(String username) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlUserSelect);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.first();
    }

    public void register(String username, String password) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, GeneralSecurityException, IOException {
        Connection connection = getConnection();
        String salt = Encryptor.getSalt();
        String encryptedPassword = Encryptor.generateSecurePassword(password, salt);
        password = null;
        PreparedStatement statement = connection.prepareStatement(sqlRegisterUser);
        statement.setString(1, username);
        statement.setString(2, encryptedPassword);
        statement.setString(3, salt);
        statement.executeUpdate();
    }
}
