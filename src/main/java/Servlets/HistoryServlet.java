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
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet{

    @Inject
    private UserHistoryService historyService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (HttpUtils.userIsNotLoggedIn(request, response) ) {
            HttpUtils.redirectToHome(request, response);
            return;
        }

        HttpSession session = request.getSession();
        List<HistoryDTO> list = historyService.getUserHistory(session.getAttribute(Constants.PROPERTY_USERNAME).toString());
        request.setAttribute("list", list);

        request.getRequestDispatcher("/WEB-INF/jsp/history.jsp").forward(request, response);
    }
}
