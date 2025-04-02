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
}
