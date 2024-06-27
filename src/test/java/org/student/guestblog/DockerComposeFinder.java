package org.student.guestblog;

import java.nio.file.Files;
import java.nio.file.Path;

public class DockerComposeFinder {

    public static Path findCompose(String filename) {
        var partial = Path.of("docker", filename);
        var searched = partial.toAbsolutePath();
        if (!Files.exists(searched)) {
            for (int i = 0; i <= partial.getNameCount(); i++) {
                if (searched.getParent() == null) {
                    break;
                }
                searched = searched.getParent();
            }
            searched = searched.resolve(partial);
        }
        return searched;
    }
}
