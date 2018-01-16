package Servlets;

import Entities.DTO.FileType;
import Entities.DTO.HistoryDTO;
import Services.UserHistoryService;
import Utils.Constants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/editor")
public class EditorServlet extends HttpServlet {

    @Inject
    private UserHistoryService historyService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute(Constants.PROPERTY_USERNAME) == null) {
            response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
            return;
        }

        List<HistoryDTO> list = historyService.getUserHistory(session.getAttribute(Constants.PROPERTY_USERNAME).toString());
        List<String> videoList = list.stream()
                .filter(x -> x.getFileType() == FileType.Video)
                .map(HistoryDTO::getFileName)
                .collect(Collectors.toList());

        request.setAttribute("videos", videoList);
        request.getRequestDispatcher("/WEB-INF/jsp/editor.jsp").forward(request, response);
    }
}
