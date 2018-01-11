package Servlets;

import Utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getParameter(Constants.PROPERTY_USERNAME) != null) {
            request.removeAttribute(Constants.PROPERTY_USERNAME);
        }
        session.invalidate();
        response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
    }
}
