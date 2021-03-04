package com.sangedon.batis.resource;

import java.io.InputStream;

public final class ResourceReader {
    public static InputStream load(String path) {
        return ResourceReader.class.getClassLoader().getResourceAsStream(path);
    }
}
