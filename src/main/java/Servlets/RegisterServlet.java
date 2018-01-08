package Servlets;

import Services.DataBase;
import Utils.Constants;
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



@WebServlet("/register")
public class RegisterServlet extends HttpServlet{

    @Inject
    private DataBase dataBase;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.PROPERTY_USERNAME) != null) {
            HttpUtils.redirectToHome(request, response);
        }

        String username = request.getParameter(Constants.PROPERTY_USERNAME);
        String password = request.getParameter(Constants.PROPERTY_PASSWORD);
        String confirmedPassword = request.getParameter(Constants.PROPERTY_CONFIRM_PASSWORD);

        if(StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmedPassword)) {
            response.sendRedirect(String.format(Constants.LOGIN_AND_REGISTRATION_ERROR_URL,Constants.ERROR_BLANK_FIELDS));
            return;
        } else if(!StringUtils.equals(password, confirmedPassword)) {
            response.sendRedirect(String.format(Constants.LOGIN_AND_REGISTRATION_ERROR_URL, Constants.ERROR_PASSWORDS_NOT_MATCH));
            return;
        }
        try {
            if(dataBase.userExists(username)) {
                response.sendRedirect(String.format(Constants.LOGIN_AND_REGISTRATION_ERROR_URL, String.format(Constants.ERROR_USERNAME_EXISTS, username)));
                return;
            }
            dataBase.register(username, password);
            session.setAttribute(Constants.PROPERTY_USERNAME, username);
            HttpUtils.redirectToHome(request,response);
            password = null;
        } catch (Exception e) {
            response.sendRedirect(String.format(Constants.LOGIN_AND_REGISTRATION_ERROR_URL, Constants.ERROR_INTERNAL_SERVER));
        }
    }
}
