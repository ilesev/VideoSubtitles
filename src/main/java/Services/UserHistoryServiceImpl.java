package Services;

import Entities.DTO.FileType;
import Entities.DTO.HistoryDTO;
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
    public List<HistoryDTO> getUserHistory(String username) {
        String path = Constants.FILE_SAVE_DIRECTORY + username;
        if (!Files.exists(Paths.get(path))) {
            return Collections.emptyList();
        }

        File folder = new File(path);
        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }

        List<HistoryDTO> history = new LinkedList<>();
        for (File file : files) {
            String fileName = file.getName();
            String extension = StringUtils.substringAfterLast(fileName, ".");
            if (!(extension.equals("mp4") || extension.equals("vtt"))) {
                continue;
            }
            String fileNameWithoutExtension = StringUtils.substringBeforeLast(fileName, ".");
            HistoryDTO vm = new HistoryDTO();
            vm.setFileName(fileNameWithoutExtension);
            if (StringUtils.equalsIgnoreCase(extension, "mp4")) {
                vm.setFileType(FileType.Video);
            } else if(StringUtils.equalsIgnoreCase(extension, "vtt")) {
                vm.setFileType(FileType.Subtitle);
            }
            history.add(vm);
        }

        return history;
    }
}
