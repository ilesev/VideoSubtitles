package Servlets;

import Entities.DTO.HistoryDTO;
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
        if (HttpUtils.userIsNotLoggedIn(request, response) ) {
            HttpUtils.redirectToHome(request, response);
            return;
        }

        HttpSession session = request.getSession();
        List<HistoryDTO> list = historyService.getUserHistory(session.getAttribute(Constants.PROPERTY_USERNAME).toString());

        request.getRequestDispatcher("/WEB-INF/jsp/history.jsp").forward(request, response);
    }
}
