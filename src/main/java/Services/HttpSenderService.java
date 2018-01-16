package Services;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpSenderService {
    void transcribeAudio(String audioPath) throws URISyntaxException, IOException;
}
