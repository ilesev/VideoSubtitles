package Servlets;

import Utils.Constants;
import Utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/updateSubtitles")
public class UpdateSubtitles extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (HttpUtils.userIsNotLoggedIn(request, response)) {
            HttpUtils.redirectToHome(request, response);
        }

        String subtitles = request.getParameter(Constants.PROPERTY_SUBTITLE_ADDR).trim();
        String username = request.getSession().getAttribute(Constants.PROPERTY_USERNAME).toString();
        String fileName = request.getParameter("fileName");

        String subtitlePath = Constants.FILE_SAVE_DIRECTORY + username + "/" + fileName + ".vtt";
        if (Files.exists(Paths.get(subtitlePath))) {
            try(PrintWriter printWriter = new PrintWriter(subtitlePath)) {
                printWriter.write(subtitles);
            }
        }

        response.sendRedirect("/editorLoader?videoSelector=" + fileName);
    }
}
