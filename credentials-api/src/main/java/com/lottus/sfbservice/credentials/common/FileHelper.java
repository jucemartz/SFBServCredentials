package com.lottus.sfbservice.credentials.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class FileHelper {

    private static final int CHUNK_SIZE = 1024;

    /**
     * Gets a byte array from a file based on a url.
     *
     * @param url The url value.
     * @return The byte[] inside inputStream.
     */
    public byte[] getFileContentFromUrl(String url) throws IOException {
        InputStream in = new BufferedInputStream(new URL(url).openStream());
        return getFileContent(in);
    }

    /**
     * Gets a byte array from an InputStream.
     *
     * @param inputStream The inputStream value.
     * @return The byte[] inside inputStream.
     */
    public byte[] getFileContent(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[CHUNK_SIZE];
        int cursor = 0;
        while ((cursor = inputStream.read(buf)) > 0) {
            out.write(buf, 0, cursor);
        }
        out.close();
        inputStream.close();
        return out.toByteArray();
    }

}
