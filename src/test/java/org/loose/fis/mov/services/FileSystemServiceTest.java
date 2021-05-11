package org.loose.fis.mov.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileSystemServiceTest {

    @Test
    void getApplicationHomePath() {
        assertEquals(
                Paths.get(System.getProperty("user.home"), "moviefy_test"),
                FileSystemService.getApplicationHomePath()
        );
    }

    @Test
    void setApplicationFolder() {
        FileSystemService.setApplicationFolder("changed_directory");
        assertEquals(
                Paths.get(System.getProperty("user.home"), "changed_directory"),
                FileSystemService.getApplicationHomePath()
        );
        FileSystemService.setApplicationFolder("moviefy_test");

    }

    @Test
    void initDirectory()
    throws IOException {
        if (Files.exists(FileSystemService.getApplicationHomePath())) {
            FileUtils.deleteDirectory(FileSystemService.getApplicationHomePath()
                                              .toFile());
        }
        FileSystemService.initDirectory();
        assertTrue(Files.exists(FileSystemService.getApplicationHomePath()));
        Files.createFile(
                FileSystemService.getApplicationHomePath()
                        .resolve(Paths.get(".", "new_file"))

        );
        /* check so that calls of initDirectory() when it exists do not wipe its contents */
        FileSystemService.initDirectory();
        assertTrue(Files.exists(FileSystemService.getApplicationHomePath()
                                        .resolve(Paths.get(".", "new_file")))
        );
    }

    @Test
    void getPathToFile() {
        assertEquals(FileSystemService.getPathToFile("test_file"),
                     FileSystemService.getApplicationHomePath()
                             .resolve(Paths.get(".", "test_file"))
        );
    }
}