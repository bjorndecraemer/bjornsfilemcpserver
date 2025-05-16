package com.bjornsmcpserver.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record ToInterpretFile(
        String fileName,
        String filePath,
        String fullFileName,
        String fileExtension,
        String pathFromSourceFolder,
        String fileContents
) {
    private static final Logger log = LoggerFactory.getLogger(ToInterpretFile.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Error converting ToInterpretFile to JSON", e);
            return "Error converting to JSON: " + e.getMessage();
        }
    }
}
