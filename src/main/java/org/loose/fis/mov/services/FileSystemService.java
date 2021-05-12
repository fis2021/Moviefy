package org.loose.fis.mov.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileSystemService {

    private static final String USER_FOLDER = System.getProperty("user.home");
    private static String APPLICATION_FOLDER = ".moviefy";

    public static Path getApplicationHomePath() {
        return Paths.get(USER_FOLDER, APPLICATION_FOLDER);
    }

    public static void setApplicationFolder(String applicationFolder) {
        APPLICATION_FOLDER = applicationFolder;
    }

    public static void initDirectory() {
        Path applicationHomePath = FileSystemService.getApplicationHomePath();
        if (!Files.exists(applicationHomePath)) {
            applicationHomePath.toFile().mkdirs();
        }
    }

    public static Path getPathToFile(String... path) {
        return getApplicationHomePath().resolve(Paths.get(".", path));
    }
}
