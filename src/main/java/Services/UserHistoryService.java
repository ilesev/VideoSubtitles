package Services;

import Entities.DTO.HistoryDTO;

import java.util.List;

public interface UserHistoryService {
    List<HistoryDTO> getUserHistory(String username);
}
