package Services;

import Entities.ViewModels.HistoryVM;

import java.util.List;

public interface UserHistoryService {
    List<HistoryVM> getUserHistory(String username);
}
