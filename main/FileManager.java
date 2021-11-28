package luxCamp.io.main;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    public static void main(String[] args) throws IOException {
        String path = "c:\\source";
        int countOfFiles = countFiles(path);
        int countOfDirectory = countDir(path);
        System.out.println(countOfFiles);
        System.out.println(countOfDirectory);
        copy(path, "c:\\source1");
        move(path, "c:\\source2");
    }

    public static int countFiles(String path) {
        int count = 0;
        File filePath = new File(path);
        File[] array = filePath.listFiles();
        for (File file : array) {
            int result = file.isFile() ? (count++) : (count += countFiles(file.getPath()));
        }
        return count;
    }

    public static int countDir(String path) throws IOException {
        int count = 0;
        File filePath = new File(path);
        File[] array = filePath.listFiles();
        for (File file : array) {
            if (file.isDirectory())
                count++;
            count += countDir(file.getPath());
        }
        return count;
}

    public static void copy(String from, String to) throws IOException {
        File copyFrom = new File(from);
        File putTo = new File(to);
        if (copyFrom.isDirectory()) {
            if (!putTo.exists()) {
                Files.createDirectories(Paths.get(to));
            }
        }

        if (copyFrom.isFile()) {
            copyFile(from, to);
        } else if (copyFrom.isDirectory()) {
            copyFolder(from, to);
        }
    }

    static void copyFile(String from, String to) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(from);
        FileOutputStream fileOutputStream = new FileOutputStream(to);

        int read;
        byte[] infoArray = new byte[1024];
        while ((read = fileInputStream.read(infoArray)) > 0) {
            fileOutputStream.write(infoArray, 0, read);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    static void copyFolder(String from, String to) throws IOException {
        File pathFrom = new File(from);
        File pathTo = new File(to);
        if (!pathTo.exists()) {
            pathTo.mkdir();
        }
        File[] files = pathFrom.listFiles();
        if (files.length != 0) {
            for (File file : files) {
                File fileFrom = new File(pathFrom, file.getName());
                File fileTo = new File(pathTo, file.getName());
                copy(fileFrom.toString(), fileTo.toString());
            }
        }
    }

    public static void move(String from, String to) {
        new File(from).renameTo(new File(to));
        new File(from).delete();
    }

  public static void deleteDirectory(String path) {
        File directory = new File(path);
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file.getAbsolutePath());
            }
        }
        directory.delete();
    }
}
