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

        if (!RegistrationIsValid(session, response, username, password, confirmedPassword)) {
            return;
        }
        try {
            if(dataBase.userExists(username)) {
                session.setAttribute(Constants.PROPERTY_ERROR_MESSAGE, String.format(Constants.ERROR_USERNAME_EXISTS, username));
                response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
                return;
            }
            dataBase.register(username, password);
            session.setAttribute(Constants.PROPERTY_USERNAME, username);
            HttpUtils.redirectToHome(request,response);
            password = null;
        } catch (Exception e) {
            session.setAttribute(Constants.PROPERTY_ERROR_MESSAGE, Constants.ERROR_INTERNAL_SERVER);
            response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
        }
    }

    private boolean RegistrationIsValid(HttpSession session, HttpServletResponse response, String username, String password, String confirmedPassword) throws IOException {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmedPassword)) {
            session.setAttribute(Constants.PROPERTY_ERROR_MESSAGE, Constants.ERROR_BLANK_FIELDS);
            response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
            return false;
        } else if(!StringUtils.equals(password, confirmedPassword)) {
            session.setAttribute(Constants.PROPERTY_ERROR_MESSAGE, Constants.ERROR_PASSWORDS_NOT_MATCH);
            response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
            return false;
        }
        return true;
    }
}
