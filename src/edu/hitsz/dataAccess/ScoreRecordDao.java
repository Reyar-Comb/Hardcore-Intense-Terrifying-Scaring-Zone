package edu.hitsz.dataAccess;

public interface ScoreRecordDao {
    public void addRecord(int score, String username, String difficulty);
    public void deleteRecord(ScoreRecord scoreRecord);
    public void showRecords(String difficulty);
}
