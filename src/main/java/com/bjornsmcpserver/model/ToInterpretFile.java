package com.bjornsmcpserver.model;

public record ToInterpretFile(
        String fileName,
        String filePath,
        String fullFileName,
        String fileExtension,
        String pathFromSourceFolder,
        String fileContentsAsString
) {
}
