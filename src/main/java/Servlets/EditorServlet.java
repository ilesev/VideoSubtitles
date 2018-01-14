package Servlets;

import Utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editor")
public class EditorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute(Constants.PROPERTY_USERNAME) == null) {
            response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/editor.jsp").forward(request, response);
    }
}
