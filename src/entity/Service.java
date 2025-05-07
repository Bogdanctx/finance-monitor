package entity;

import database.Database;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Service {
    private static File logs = null;
    private final static String os = System.getProperty("os.name").toLowerCase();


    static {
        logs = createFile("LOGS.csv");

        List<String> csvHeaders = new ArrayList<>();
        csvHeaders.add("nume_actiune,timestamp");
        writeToFile(logs, csvHeaders);
    }

    private Service() {}

    public static File createFile(String fileName) {
        File exportFile = new File(fileName);

        try {
            if (exportFile.exists()) {
                if (!exportFile.delete()) {
                    System.out.println("[ERROR] > " + fileName + " could not be deleted");
                }
            }
            if (!exportFile.createNewFile()) {
                System.out.println("[ERROR] > Could not create " + fileName);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] > " + e.getMessage());
        }

        return exportFile;
    }

    public static void writeToFile(File file, List<String> lines) {
        try {
            Files.write(file.toPath(), lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("[ERROR] > " + e.getMessage());
        }
    }

    public static void registerLog(String log) {
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss");
        String time = timeNow.format(timeFormat);

        writeToFile(logs, new ArrayList<>(Collections.singletonList(log + "," + time)));
    }

    public static Integer readInt(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Double readDouble(Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
