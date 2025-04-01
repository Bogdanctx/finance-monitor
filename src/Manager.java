import java.util.Scanner;

public abstract class Manager<T> {
    static Manager instance;

    public abstract void run();
}
