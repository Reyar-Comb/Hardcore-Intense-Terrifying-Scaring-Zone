package edu.hitsz.dataAccess;

public class ScoreRecord implements Comparable<ScoreRecord>{
    public String userName;
    public int score;
    public String time;
    public String difficulty;

    public ScoreRecord(String userName, int score, String time, String difficulty) {
        this.userName = userName;
        this.score = score;
        this.time = time;
        this.difficulty = difficulty;
    }

    @Override
    public int compareTo(ScoreRecord r) {
        return -Integer.compare(score, r.score);
    }

    @Override
    public String toString() {
        return String.format("%-10s %8d   %-12s %-20s", userName, score, difficulty, time);
    }
}
