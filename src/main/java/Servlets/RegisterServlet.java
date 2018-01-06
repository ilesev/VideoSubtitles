package Servlets;

import Services.DataBase;
import Utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static Utils.Constants.PROPERTY_PASSWORD;
import static Utils.Constants.PROPERTY_USERNAME;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{

    @Inject
    DataBase dataBase;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(PROPERTY_USERNAME) != null) {
            HttpUtils.redirectToHome(request, response);
        }

        String username = request.getParameter(PROPERTY_USERNAME);
        String password = request.getParameter(PROPERTY_PASSWORD);

        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            response.sendError(400);
        }
        try {
            if(dataBase.userExists(username)) {
                response.sendError(409, "User already exists");
            }
            dataBase.register(username, password);
            password = null;
        } catch (Exception e) {
            response.sendError(500, "Couldn't register user");
        }

    }
}
