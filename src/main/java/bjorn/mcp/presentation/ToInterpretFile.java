package bjorn.mcp.presentation;

public record ToInterpretFile(String fullFileName, String fileName, String fileExtension, String fileSizeInKB, String path, String contents, Boolean contentsFilled) {
}
