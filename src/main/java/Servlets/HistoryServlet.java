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


        FFmpeg ffmpeg = new FFmpeg();
        FFprobe ffprobe = new FFprobe();
        FFmpegProbeResult probeResult = ffprobe.probe(Constants.FILE_SAVE_DIRECTORY + list.get(0).getFileName() + ".mp4");

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(Constants.FILE_SAVE_DIRECTORY + list.get(0).getFileName() + ".mp4")  // Filename, or a FFmpegProbeResult
                .overrideOutputFiles(true) // Override the output if it exists
                .addOutput(Constants.FILE_SAVE_DIRECTORY  + list.get(0).getFileName())   // Filename for the destination
                .setFormat("mp3")        // Format is inferred from filename, or can be set
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

        // Run a one-pass encode
        executor.createJob(builder).run();

        request.getRequestDispatcher("/WEB-INF/jsp/history.jsp").forward(request, response);
    }
}
