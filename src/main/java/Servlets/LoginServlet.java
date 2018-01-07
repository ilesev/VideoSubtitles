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
        HttpSession session = request.getSession();
        if (session.getAttribute(PROPERTY_USERNAME) != null) {
            HttpUtils.redirectToHome(request, response);
        }
        String username = request.getParameter(PROPERTY_USERNAME);
        String password = request.getParameter(PROPERTY_PASSWORD);

        try {
            User user = dataBase.getUser(username);
            String pageToRedirect = "/";
            if (user == null || !Encryptor.verifyUserPassword(password, user.getPassword(), user.getSalt())) {
                pageToRedirect = String.format(Constants.LOGIN_AND_REGISTRATION_ERROR_URL, Constants.ERROR_LOGGING_IN);
            }

            if(StringUtils.equalsIgnoreCase("/", pageToRedirect)){
                session.setAttribute("username", username);
            }
            response.sendRedirect(pageToRedirect);
        } catch (Exception e) {
            response.sendRedirect(String.format(Constants.LOGIN_AND_REGISTRATION_ERROR_URL, Constants.ERROR_INTERNAL_SERVER));
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
