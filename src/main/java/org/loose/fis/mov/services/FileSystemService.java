package org.loose.fis.mov.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileSystemService {
    private static final String APPLICATION_FOLDER = ".moviefy";
    private static final String USER_FOLDER = System.getProperty("user.home");
    private static final Path APPLICATION_HOME_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);

    public static Path getApplicationHomePath() {
        return APPLICATION_HOME_PATH;
    }

    public static void initDirectory() {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if (!Files.exists(applicationHomePath)) {
            applicationHomePath.toFile().mkdirs();
        }
    }

    public static Path getPathToFile(String... path) {
        return APPLICATION_HOME_PATH.resolve(Paths.get(".", path));
    }
}
