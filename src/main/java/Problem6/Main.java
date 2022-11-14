package Problem6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String PATH = "D:/CodeSpace/SDU-Problems/src/main/java/Problem6/";
    public static Manager manager;
    public static void main(String[] args) throws FileNotFoundException {
        manager = new Manager();
        Scanner scanner = new Scanner(new FileInputStream(PATH + "data6.txt"));
        while (scanner.hasNextLine()) {
            Reader.readLine(scanner);
        }
//        manager.readData(System.in);
        manager.printByAgeAndNumber();
        System.out.println();
        manager.printByOrder();
    }
}
