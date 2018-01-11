package Services;

import Entities.User;
import java.sql.SQLException;

public interface DataBase {
    boolean userExists(String username) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException;

    void register(String username, String password) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException;

    User getUser(String username) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException;
}
