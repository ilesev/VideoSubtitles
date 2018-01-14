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
    public void parse(List<Words> w) {
        StringBuilder result = new StringBuilder();
        StringBuilder lineBuilder = new StringBuilder();
        double elapsedTime = 0;
        double startTime = 0;
        boolean lineSeparated = false;

        for (Words word : w) {
            if(startTime == 0) {
                startTime = word.getTime();
            }

            elapsedTime += word.getDuration();
            String currentWord = word.getName();
            if(currentWord.equals(".") && lineBuilder.length() > 0) {
                lineBuilder.setCharAt(lineBuilder.length()-1, '.');
            } else {
                lineBuilder.append(currentWord);
            }

            if (lineBuilder.length() > 38 && !lineSeparated) {
                lineSeparated = true;
                lineBuilder.append(System.lineSeparator());
            } else {
                lineBuilder.append(" ");
            }

            if (elapsedTime > 400 || lineBuilder.length() > 76) {
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
                lineBuilder.setLength(0);
                elapsedTime = 0;
                startTime = 0;
                lineSeparated=false;
            }
        }

        try(PrintWriter printWriter = new PrintWriter("/Users/ilesev/Desktop/Web/subtitle.txt")) {
            printWriter.write(result.toString());
        } catch (FileNotFoundException e) {
            // ignore
        }
    }
}
