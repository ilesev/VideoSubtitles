package Services;

import Utils.Constants;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.faces.bean.ApplicationScoped;
import java.io.IOException;

@ApplicationScoped
public class AudioConverterImpl implements AudioConverter{
    @Override
    public void convert(String videoPath) throws IOException {
        FFmpeg ffmpeg = new FFmpeg(Constants.FFMPEG_LOCATION);
        FFprobe ffprobe = new FFprobe(Constants.FFPROBE_LOCATION);

        String audioPath = StringUtils.substringBeforeLast(videoPath, ".") + ".mp3";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(videoPath)
                .overrideOutputFiles(true)
                .addOutput(audioPath)
                .setFormat("mp3")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
    }
}
