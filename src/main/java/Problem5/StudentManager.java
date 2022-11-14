package Problem5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Types;
import java.util.*;

public class StudentManager {
    public enum FileTypes {
        STUDENT, CURRICULUM, SCORE
    }
    public Map<Long, Student> studentMap;
    public static class Curriculum {
        public String name;
        public int credit;
        public Curriculum(String name, int credit) {
            this.name = name;
            this.credit = credit;
        }
    }
    public Map<String, Curriculum> lessonMap;

    public StudentManager() {
        studentMap = new HashMap<>();
        lessonMap = new HashMap<>();
    }

    public void readFile(String path, FileTypes types) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(path));
            while (scanner.hasNext()) {
                switch (types) {
                    case STUDENT -> {
                        long var2 = scanner.nextLong();
                        String var1 = scanner.next();
                        if (studentMap.containsKey(var2))
                            continue;
                        studentMap.put(var2, new Student(var2, var1, 0, 0));
                    }
                    case CURRICULUM -> {
//                        long var1 = scanner.nextLong();
//                        if (!studentMap.containsKey(var1))
//                            throw new RuntimeException(String.format(Library.STUDENT_NOT_FOUND, var1));
//                        while (scanner.hasNext() && !scanner.hasNextLong()) {
//                            studentMap.get(var1).curriculum.add(scanner.next());
//                        }
                        String var1 = scanner.next(), var2 = scanner.next();
                        int var3 = scanner.nextInt();
                        lessonMap.put(var1, new Curriculum(var2, var3));
                    }
                    case SCORE -> {
                        long var1 = scanner.nextLong();
                        if (!studentMap.containsKey(var1)) {
//                            throw new RuntimeException(String.format(Library.STUDENT_NOT_FOUND, var1));
                            System.err.printf((Library.STUDENT_NOT_FOUND) + "%n", var1);
                            scanner.next();
                            scanner.nextInt();
                            continue;
                        }
                        String var2 = scanner.next();
                        int var3 = scanner.nextInt();
                        try {
                            studentMap.get(var1).credit.put(var2, var3);
                            studentMap.get(var1).creditScoreTotal += (var3 / 10.0 - 5) * lessonMap.get(var2).credit;
                            studentMap.get(var1).creditTotal += lessonMap.get(var2).credit;
                        } catch (NullPointerException e) {
                            System.err.printf((Library.CURRICULUM_NOT_FOUND) + "%n", var2);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> getEntries() {
        return new ArrayList<>(studentMap.values());
    }
    public void readFiles(String path, int x) {
//        this.readFile(path + String.format("S%d.txt", x), FileTypes.STUDENT);
//        this.readFile(path + String.format("C%d.txt", x), FileTypes.CURRICULUM);
//        this.readFile(path + String.format("A%d.txt", x), FileTypes.SCORE);
        this.readFiles(path, x, FileTypes.STUDENT);
        this.readFiles(path, x, FileTypes.CURRICULUM);
        this.readFiles(path, x, FileTypes.SCORE);
    }

    public void readFiles(String path, int x, FileTypes type) {
        switch (type) {
            case STUDENT -> this.readFile(path + String.format("S%d.txt", x), FileTypes.STUDENT);
            case CURRICULUM -> this.readFile(path + String.format("C%d.txt", x), FileTypes.CURRICULUM);
            case SCORE -> this.readFile(path + String.format("A%d.txt", x), FileTypes.SCORE);
        }
    }

    public void printData() {
        List<Student> list = this.getEntries();
        list.sort((x, y) -> {
            double var1 = y.getGPA() - x.getGPA();
            if (var1 == 0)
                return 0;
            if (var1 < 0)
                return -1;
            return 1;
        });
        list.forEach((i) -> System.out.println(i.stuNum + " " + i.stuName + " " + String.format("%.2f", i.getGPA())));
    }
}
