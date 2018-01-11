package Servlets;

import Entities.User;
import Services.DataBase;
import Utils.Constants;
import Utils.Encryptor;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Inject
    private DataBase dataBase;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // if user is logged in redirect to home page
        if (!HttpUtils.userIsNotLoggedIn(request, response)) {
            HttpUtils.redirectToHome(request, response);
            return;
        }

        HttpSession session = request.getSession();
        String username = request.getParameter(PROPERTY_USERNAME);
        String password = request.getParameter(PROPERTY_PASSWORD);

        try {
            User user = dataBase.getUser(username);
            String pageToRedirect = Constants.HOME_URL;
            if (user == null || !Encryptor.verifyUserPassword(password, user.getPassword(), user.getSalt())) {
                session.setAttribute(Constants.PROPERTY_ERROR_MESSAGE, Constants.ERROR_LOGGING_IN);
                pageToRedirect = Constants.LOGIN_AND_REGISTRATION_URL;
            }

            if(StringUtils.equalsIgnoreCase(Constants.HOME_URL, pageToRedirect)){
                session.setAttribute(Constants.PROPERTY_USERNAME, username);
            }
            response.sendRedirect(pageToRedirect);
        } catch (Exception e) {
            session.setAttribute(Constants.PROPERTY_ERROR_MESSAGE, Constants.ERROR_INTERNAL_SERVER);
            response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(PROPERTY_USERNAME) != null) {
            HttpUtils.redirectToHome(request, response);
            return;
        }
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }
}
