package Servlets;

import Services.HttpSenderService;
import Utils.Constants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet("")
public class HomeServlet extends HttpServlet {

//    @Inject
//    HttpSenderService senderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute(Constants.PROPERTY_USERNAME) == null) {
            response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
            return;
        }

//        try {
//            senderService.transcribeAudio();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
    }
}
