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
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Inject
    DataBase dataBase;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username").toString();
        String password = request.getParameter("password").toString();
        HttpSession session = request.getSession();
        if(session.getAttribute("username") != null) {
            HttpUtils.redirectToHome(request, response);
        } else {
            try {
                System.out.println(dataBase.getAllUsers());
            } catch (Exception e) {
                // ignore for now
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
    }
}
