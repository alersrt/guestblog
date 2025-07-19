package org.student.guestblog.domain.internal;

import java.util.Arrays;

public class FileResource {

    private final byte[] content;
    private final String mime;
    private final String filename;

    /**
     * Creates this resource.
     *
     * @param content  bytes of the file.
     * @param filename name of the file.
     * @param mime     mime type of the file.
     */
    public FileResource(byte[] content, String filename, String mime) {
        this.content = Arrays.copyOf(content, content.length);
        this.filename = filename;
        this.mime = mime;
    }

    public String getMime() {
        return mime;
    }

    public String getFilename() {
        return filename;
    }
}
