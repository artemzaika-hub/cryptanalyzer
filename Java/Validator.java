import java.io.File;

public class Validator {
    public static boolean isShiftValid(int shift) {
        return shift >= 0 && shift <= 25;
    }

    public static boolean fileExists(String path) {
        File f = new File(path);
        return f.exists() && !f.isDirectory();
    }
}
