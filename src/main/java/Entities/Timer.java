package Entities;

public class Timer {
    private int hours;
    private int minutes;
    private int seconds;
    private int milliseconds;

    public Timer(double seconds) {
        int minutes = (int)seconds / 60;
        int hours = 0;
        int milliseconds = (int)((seconds - Math.floor(seconds)) * 100);
        if (minutes > 60) {
            hours = minutes/60;
            minutes = minutes%60;
        }

        this.milliseconds = milliseconds;
        this.seconds = (int)seconds%60;
        this.minutes = minutes;
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public void reset() {
        hours = 0;
        milliseconds = 0;
        seconds = 0;
        minutes = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds))
                .append(".")
                .append(String.format("%02d", milliseconds));
        return sb.toString();
    }
}
