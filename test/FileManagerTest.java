package luxCamp.io.test;

import luxCamp.io.main.FileManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTest {


    @Test
    public void testCountFiles() throws IOException {
        Path path = Files.createTempDirectory("testTemp");

        assertEquals(0, FileManager.countFiles(path.toString()));

        Path path1 = Files.createTempDirectory(path, "temp1");
        Path path2 = Files.createTempDirectory(path1, "temp1");
        File.createTempFile("file1", ".txt", new File(path.toString()));

        assertEquals(1, FileManager.countFiles(path.toString()));

        File.createTempFile("file2", ".txt", new File(path1.toString()));
        File.createTempFile("file3", ".txt", new File(path2.toString()));

        assertEquals(3, FileManager.countFiles(path.toString()));
    }

    @Test
    public void testCountDirs() throws IOException {
        Path path = Files.createTempDirectory("dir1");
        assertEquals(0, FileManager.countDir(path.toString()));

        Path path1 = Files.createTempDirectory(path, "dir2");
        Path path2 = Files.createTempDirectory(path, "dir3");

        assertEquals(2, FileManager.countDir(path.toString()));
        Path path3 = Files.createTempDirectory(path1, "dir3");

        assertEquals(3, FileManager.countDir(path.toString()));

    }

    @Test
    public void testCopy() throws IOException {
        Path path = Files.createTempDirectory("rootDir");

        assertEquals(0, FileManager.countFiles(path.toString()));

        Files.createTempDirectory(path, "dir1");
        Files.createTempDirectory(path, "dir2");
        File.createTempFile("file1", ".txt", new File(path.toString()));
        File.createTempFile("file2", ".txt", new File(path.toString()));
        File.createTempFile("file3", ".txt", new File(path.toString()));

        Path path2 = Files.createTempDirectory("newDir2");
        FileManager.copy(path.toString(), path2.toString());
        assertEquals(3, FileManager.countFiles(path2.toString()));
        assertEquals(3, FileManager.countFiles(path.toString()));

    }

    @Test
    public void testMove() throws IOException {
        Path path = Files.createTempDirectory("rootDir");

        assertEquals(0, FileManager.countFiles(path.toString()));

        Files.createTempDirectory(path, "dir1");
        Files.createTempDirectory(path, "dir2");
        File.createTempFile("file1", ".txt", new File(path.toString()));
        File.createTempFile("file2", ".txt", new File(path.toString()));
        File.createTempFile("file3", ".txt", new File(path.toString()));

        Path path2 = Files.createTempDirectory("newDir");

        FileManager.move(path.toString(), path2.toString());
        assertEquals(0, FileManager.countFiles(path2.toString()));
        assertEquals(3, FileManager.countFiles(path.toString()));

    }
}
