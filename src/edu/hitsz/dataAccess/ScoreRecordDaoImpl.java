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

    public void deleteRecord(ScoreRecord scoreRecord) {
        String dataDir = getDir(scoreRecord);
        List<ScoreRecord> records = readRecords(dataDir);
        records.remove(scoreRecord);
        writeRecord(dataDir, records);
    }

    public void showRecords(String difficulty) {
        formatPrintRecords(getSortedRecords(difficulty));
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

    private static List<ScoreRecord> getSortedRecords(String difficult) {
        String dataDir = getDir(new ScoreRecord("", 0, "", difficult));
        List<ScoreRecord> records = readRecords(dataDir);
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
}
