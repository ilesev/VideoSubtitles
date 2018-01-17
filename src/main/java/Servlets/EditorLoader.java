package Servlets;

import Utils.Constants;
import Utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/editorLoader")
public class EditorLoader extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (HttpUtils.userIsNotLoggedIn(request, response)) {
            HttpUtils.redirectToHome(request, response);
            return;
        }

        HttpSession session = request.getSession();
        String username = session.getAttribute(Constants.PROPERTY_USERNAME).toString();
        String videoName = request.getParameter("videoSelector");
        if (videoName == null || StringUtils.equals(videoName, StringUtils.EMPTY)) {
            HttpUtils.redirectToHome(request, response);
            return;
        }

        String relativeVideoPath = "/" + username + "/" + videoName + ".mp4";
        String relativeSubtitlePath = "/" + username + "/" + videoName + ".vtt";
        session.setAttribute(Constants.PROPERTY_VIDEO_ADDR, relativeVideoPath);
        session.setAttribute(Constants.PROPERTY_SUBTITLE_ADDR, relativeSubtitlePath);
        response.sendRedirect("/editor");
    }
}
