package Servlets;

import Services.DataBase;
//import Utils.DbUtils;
import Utils.HttpUtils;

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
    DataBase dataBase;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(PROPERTY_USERNAME) != null) {
            HttpUtils.redirectToHome(request, response);
        }
        String username = request.getParameter(PROPERTY_USERNAME);
        String password = request.getParameter(PROPERTY_PASSWORD);

        try {
//            System.out.println(dataBase.getAllUsers());
        } catch (Exception e) {
            // ignore for now
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }
}
