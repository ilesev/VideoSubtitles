package Services;

import Entities.ViewModels.FileType;
import Entities.ViewModels.HistoryVM;
import Utils.Constants;
import org.apache.commons.lang3.StringUtils;

import javax.faces.bean.ApplicationScoped;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class UserHistoryServiceImpl implements UserHistoryService {
    @Override
    public List<HistoryVM> getUserHistory(String username) {
        String path = Constants.FILE_SAVE_DIRECTORY + username;
        if (!Files.exists(Paths.get(path))) {
            return Collections.emptyList();
        }

        File folder = new File(path);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }

        List<HistoryVM> history = new LinkedList<>();
        for (File file : files) {
            String fileName = file.getName();
            String fileNameWithoutExtension = StringUtils.substringBeforeLast(fileName, ".");
            String extension = StringUtils.substringAfterLast(fileName, ".");
            HistoryVM vm = new HistoryVM();
            vm.setFileName(fileNameWithoutExtension);
            vm.setFileType( StringUtils.equalsIgnoreCase(extension, "mp4") ? FileType.Video : FileType.Subtitle);
            history.add(vm);
        }

        return history;
    }
}
