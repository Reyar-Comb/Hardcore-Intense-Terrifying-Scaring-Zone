package edu.hitsz.dataAccess;

public interface ScoreRecordDao {
    public void addRecord(int score, String username, String difficulty);
    public void deleteRecord(String difficulty, String time);
    public void showRecords(String difficulty);
    public String[][] getRecords(String difficulty);
}
