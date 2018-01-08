package Servlets;

import Utils.Constants;
import Utils.HttpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.PROPERTY_USERNAME) == null) {
            response.sendRedirect("/login");
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

            response.sendRedirect(String.format(Constants.HOME_SUCCESSFUL_UPLOAD, Constants.FILE_UPLOADED));
        } catch (Exception e) {
            response.sendRedirect(String.format(Constants.HOME_ERROR_URL, Constants.ERROR_UPLOAD_FAILED));
        }
    }

    private void createFolderIfNonExistent(String dir) throws IOException {
        Path path = Paths.get(dir);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
    }
}
