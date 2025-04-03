import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Service {
    private final static String os = System.getProperty("os.name").toLowerCase();

    public static void clearConsole() {
        try {
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("[ERROR] > " + e.getMessage());
        }
    }

    public static File createFile(String fileName) {
        File exportFile = new File(fileName);

        try {
            if(exportFile.exists()) {
                if(!exportFile.delete()) {
                    System.out.println("[ERROR] > " + fileName + " could not be deleted");
                }
            }
            else {
                if (!exportFile.createNewFile()) {
                    System.out.println("[ERROR] > Could not create " + fileName);
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] > " + e.getMessage());
        }

        return exportFile;
    }

    public static void writeToFile(File file, List<String> lines) {
        try {
            Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("[ERROR] > " + e.getMessage());
        }
    }
}
