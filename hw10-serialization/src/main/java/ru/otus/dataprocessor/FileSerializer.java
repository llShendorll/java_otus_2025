package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class FileSerializer implements Serializer {

    private final Path filePath;

    public FileSerializer(String fileName) {
        this.filePath = Path.of(fileName);
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // формирует результирующий json и сохраняет его в файл
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Map<String, Double> sorted = new TreeMap<>(data);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(filePath.toFile(), sorted);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
