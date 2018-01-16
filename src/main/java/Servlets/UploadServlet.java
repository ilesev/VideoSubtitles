package Servlets;

import Services.AudioConverter;
import Services.HttpSenderService;
import Utils.Constants;
import Utils.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {

    @Inject
    private HttpSenderService senderService;

    @Inject
    private AudioConverter audioConverter;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.PROPERTY_USERNAME) == null) {
            response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
            return;
        }
        try {
            ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> file = sfu.parseRequest(request);
            if (CollectionUtils.isEmpty(file)) {
                HttpUtils.redirectToHome(request, response);
                return;
            }

            String username = session.getAttribute(Constants.PROPERTY_USERNAME).toString();
            String fileDir = Constants.FILE_SAVE_DIRECTORY + username;
            createFolderIfNonExistent(fileDir);

            String fileName = null;
            String filePath = null;

            // should only have one file
            for (FileItem item : file) {
                fileName = item.getName();
                filePath = fileDir + "/" + fileName;
                item.write(new File(filePath));
            }

            audioConverter.convert(filePath);
            String audioPath = StringUtils.substringBeforeLast(filePath, ".") + ".mp3";
            senderService.transcribeAudio(audioPath);
            String subtitlePath = StringUtils.substringBeforeLast(filePath, ".") + ".vtt";

            session.setAttribute(Constants.PROPERTY_VIDEO_ADDR,   username + "/" + fileName);
            session.setAttribute(Constants.PROPERTY_SUBTITLE_ADDR,  username + "/" + StringUtils.substringBeforeLast(fileName, ".") + ".vtt");
            response.sendRedirect(Constants.EDITOR_URL);
        } catch (Exception e) {
            session.setAttribute(Constants.PROPERTY_ERROR_MESSAGE, Constants.ERROR_UPLOAD_FAILED);
            response.sendRedirect(Constants.HOME_URL);
        }
    }

    private void createFolderIfNonExistent(String dir) throws IOException {
        Path path = Paths.get(dir);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
    }

}
