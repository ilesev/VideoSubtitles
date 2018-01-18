package Services;

import Entities.Timer;
import Entities.Words;

import javax.faces.bean.ApplicationScoped;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

@ApplicationScoped
public class SubtitleParserImpl implements SubtitleParser {
    @Override
    public void parse(List<Words> w, String subtitleLocation) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        result.append("WEBVTT");
        result.append(System.lineSeparator());
        result.append(System.lineSeparator());
        StringBuilder lineBuilder = new StringBuilder();
        double elapsedTime = 0;
        double startTime = 0;
        boolean lineSeparated = false;
        double lastWordStartTime = 0;
        double lastWordDuration = 0;

        for (int index = 0; index < w.size(); index++) {
            Words word = w.get(index);
            if(startTime == 0) {
                startTime = word.getTime();
            }
            if (index > 0) {
                lastWordStartTime = w.get(index-1).getTime();
                lastWordDuration = w.get(index-1).getDuration();
                elapsedTime += word.getTime() - lastWordDuration - lastWordStartTime;
            }

            elapsedTime += word.getDuration();
            String currentWord = word.getName();
            if(currentWord.equals(".") && lineBuilder.length() > 0) {
                // make the dot stick to the previous word
                lineBuilder.setCharAt(lineBuilder.length()-1, '.');
            } else {
                lineBuilder.append(currentWord);
                // make sure there aren't dots on a new line
                if (index < w.size()-1 && w.get(index+1).getName().equals(".")) {
                    lineBuilder.append(w.get(index+1).getName());
                    index++;
                }
            }

            if (lineBuilder.length() > 40 && !lineSeparated) {
                lineSeparated = true;
                lineBuilder.append(System.lineSeparator());
            } else {
                lineBuilder.append(" ");
            }

            if (elapsedTime > 800 || lineBuilder.length() > 80) {
                Timer start = new Timer(startTime);
                Timer end = new Timer(startTime + elapsedTime);

                result.append(start.toString());
                result.append(" --> ");
                result.append(end.toString());
                result.append(System.lineSeparator());
                result.append(lineBuilder.toString());
                result.append(System.lineSeparator());
                result.append(System.lineSeparator());

                // reset the values
                lineBuilder = new StringBuilder();
                elapsedTime = 0;
                startTime = 0;
                lineSeparated=false;
            }
        }

        try(PrintWriter printWriter = new PrintWriter(subtitleLocation)) {
            printWriter.write(result.toString().trim());
        }
    }
}
