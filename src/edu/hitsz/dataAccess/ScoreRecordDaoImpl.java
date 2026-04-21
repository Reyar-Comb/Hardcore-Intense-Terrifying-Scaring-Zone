package edu.hitsz.dataAccess;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ScoreRecordDaoImpl implements ScoreRecordDao {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public void addRecord(int score, String username, String difficulty) {
        String time = new java.text.SimpleDateFormat("MM-dd HH:mm").format(new java.util.Date());
        ScoreRecord record = new ScoreRecord(username, score, time, difficulty);
        String dataDir = getDir(record);
        List<ScoreRecord> records = readRecords(dataDir);
        records.add(record);
        writeRecord(dataDir, records);
    }

    public void deleteRecord(String difficulty, String time) {
        String dataDir = getDir(difficulty);
        List<ScoreRecord> records = readRecords(dataDir);
        for (ScoreRecord record : records) {
            if (record.time.equals(time)){
                records.remove(record);
                break;
            }
        }
        writeRecord(dataDir, records);
    }

    public void showRecords(String difficulty) {
        formatPrintRecords(getSortedRecords(difficulty));
    }

    public String[][] getRecords(String difficulty) {
        return formatGetRecords(getSortedRecords(difficulty));
    }
    private static void formatPrintRecords(List<ScoreRecord> records){
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (ScoreRecord record : records){
            sb.append(String.format("%4s  ", i)).append(record.toString()).append("\n");
            i++;
        }
        System.out.printf("      %-10s %8s   %-12s %-20s \n", "Username", "Score", "Difficulty", "Time");
        System.out.println(sb.toString());
    }

    private static String[][] formatGetRecords(List<ScoreRecord> records){
        String[][] strings = new String[records.size()][5];
        int i = 0;
        for (ScoreRecord record : records){
            String[] current = new String[5];
            current[0] = Integer.toString(i+1);
            current[1] = record.userName;
            current[2] = Integer.toString(record.score);
            current[3] = record.difficulty;
            current[4] = record.time;
            strings[i] = current;
            i++;
        }
        return strings;
    }

    private static List<ScoreRecord> getSortedRecords(String difficult) {
        List<ScoreRecord> records = readRecords(getDir(difficult));
        Collections.sort(records);
        return records;
    }

    private static List<ScoreRecord> readRecords(String dataPath) {
        Path path = Paths.get(dataPath);
        try {
            String json = Files.readString(path).trim();
            if (json.isEmpty()) {
                return new ArrayList<>();
            }
            return GSON.fromJson(json, new TypeToken<ArrayList<ScoreRecord>>(){});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void writeRecord(String dataPath, List<ScoreRecord> records) {
        Path path = Paths.get(dataPath);
        try {
            Files.writeString(path, GSON.toJson(records));
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getDir(ScoreRecord record){
        return Paths.get("database", record.difficulty + ".json").toString();
    }

    private static String getDir(String difficulty){
        return Paths.get("database", difficulty + ".json").toString();
    }

    public static int getRecordSize(String difficulty) {
        List<ScoreRecord> records = readRecords(getDir(difficulty));
        return records.size();
    }
}
