package by.patapovich.calculator.persistence;

import java.util.HashMap;
import java.util.Map;

public enum FileType {
    PLAIN("txt"),
    JSON("json"),
    XML("xml"),
    ZIP("zip");

    private final String extension;

    private static final Map<String, FileType> stringToFileTypeMap = new HashMap<>();

    static {
        for (FileType ft : FileType.values()) {
            stringToFileTypeMap.put(ft.extension, ft);
        }
    }

    FileType(String extension) {
        this.extension = extension;
    }

    public static FileType fromString(String extension) {
       return stringToFileTypeMap.get(extension);
    }

    public String getExtension() {
        return extension;
    }
}
