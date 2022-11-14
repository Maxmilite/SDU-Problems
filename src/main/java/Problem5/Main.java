package Problem5;

import java.util.List;

public class Main {

    public static final String PATH = "D:/CodeSpace/SDU-Problems/src/main/java/Problem5/Data/";

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
//        manager.readFiles(PATH, 1);
//        manager.readFiles(PATH, 2);
//        manager.readFiles(PATH, 3);
        for (var i : StudentManager.FileTypes.values()) {
            for (int j = 1; j <= 3; ++j)
                manager.readFiles(PATH, j, i);
        }
        manager.printData();
    }
}
