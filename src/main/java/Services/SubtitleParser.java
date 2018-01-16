package Services;

import Entities.Words;

import java.io.FileNotFoundException;
import java.util.List;

public interface SubtitleParser {
    void parse(List<Words> w, String subtitleLocation) throws FileNotFoundException;
}
