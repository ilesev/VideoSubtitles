package Servlets;

import Entities.ViewModels.HistoryVM;
import Services.UserHistoryService;
import Utils.Constants;
import Utils.HttpUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet{

    @Inject
    UserHistoryService historyService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.PROPERTY_USERNAME) == null ) {
            HttpUtils.redirectToHome(request, response);
        }

        List<HistoryVM> list = historyService.getUserHistory(session.getAttribute(Constants.PROPERTY_USERNAME).toString());

        request.getRequestDispatcher("/WEB-INF/jsp/history.jsp").forward(request, response);
    }
}
