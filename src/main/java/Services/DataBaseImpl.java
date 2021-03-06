package Services;

import Entities.User;
import Utils.Constants;
import Utils.Encryptor;
import Utils.Mapper;
import org.apache.commons.collections.CollectionUtils;

import javax.faces.bean.ApplicationScoped;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class DataBaseImpl implements DataBase {

    private static final String sqlUserSelect = "SELECT username FROM accounts WHERE username = ?";
    private static final String sqlUserSelectWithPassword = "SELECT * FROM accounts WHERE username = ?";
    private static final String sqlRegisterUser = "INSERT INTO accounts(username, password, salt)" +
                                                    "VALUES(?,?,?)";

    private Connection getConnection() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        return DriverManager.getConnection(Constants.dbUrl, Constants.dbUsername, Constants.dbPassword);
    }

    public boolean userExists(String username) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlUserSelect);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.first();
    }

    public void register(String username, String password) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
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

    public User getUser(String username) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sqlUserSelectWithPassword);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        // should only have 1 user
        List<User> users = Mapper.map(resultSet, User.class);
        if (CollectionUtils.isNotEmpty(users)) {
            return users.get(0);
        }
        return null;
    }
}
