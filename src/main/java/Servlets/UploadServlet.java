package Servlets;

import Services.HttpSenderService;
import Utils.Constants;
import Utils.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.PROPERTY_USERNAME) == null) {
            response.sendRedirect(Constants.LOGIN_AND_REGISTRATION_URL);
            return;
        }
        try{
            ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> file = sfu.parseRequest(request);
            if (CollectionUtils.isEmpty(file)) {
                HttpUtils.redirectToHome(request,response);
                return;
            }

            String fileDir = Constants.FILE_SAVE_DIRECTORY + session.getAttribute(Constants.PROPERTY_USERNAME);
            createFolderIfNonExistent(fileDir);

            for(FileItem item : file) {
                item.write(new File(fileDir + "/" + item.getName()));
            }

            session.setAttribute(Constants.PROPERTY_UPLOAD_MESSAGE, Constants.FILE_UPLOADED);
            response.sendRedirect(Constants.HOME_URL);
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
