package Servlets;

import Utils.Constants;
import Utils.HttpUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

@WebServlet("/editorLoader")
public class EditorLoader extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        String relativeVideoPath = username + "/" + videoName + ".mp4";
        String relativeSubtitlePath = username + "/" + videoName + ".vtt";
        String subtitleContent = StringUtils.EMPTY;
        String absoluteSubtitlePath = Constants.FILE_SAVE_DIRECTORY + relativeSubtitlePath;
        try (FileInputStream fileInputStream = new FileInputStream(absoluteSubtitlePath)) {
            subtitleContent = IOUtils.toString(fileInputStream, Charset.defaultCharset());
        }

        session.setAttribute("fileName", videoName);
        session.setAttribute(Constants.PROPERTY_VIDEO_ADDR, relativeVideoPath);
        session.setAttribute(Constants.PROPERTY_SUBTITLE_ADDR, relativeSubtitlePath);
        session.setAttribute("subContent", subtitleContent);
        response.sendRedirect("/editor");
    }
}
