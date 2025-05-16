package bjorn.mcp.utils;

import com.bjornsmcpserver.model.ToInterpretFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    private static final List<String> ALLOWED_FILE_EXTENSIONS = ConfigUtils.getConfigPropertyAsList("allowedFileExtensions", List.class);
    private static final Path DIRECTORY_PATH = Paths.get("/Users/bjorn/Desktop/tointerpretfiles");

    public static List<ToInterpretFile> readToInterpretFiles() {
        List<ToInterpretFile> toInterpretFiles = new ArrayList<>();
        log.info("Reading files from directory: {}", DIRECTORY_PATH);
        try {
            Files.walk(DIRECTORY_PATH)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            String fileName = file.getFileName().toString();
                            log.info("Found file: {}", fileName);
                            String fileExtension = getFileExtension(fileName);
                            String fileContentsAsString = "";
                            if (!isAllowedFileExtension(fileExtension)) {
                                log.info("Skipping file READ with disallowed extension: {}", fileName);
                                fileContentsAsString = "File with disallowed extension";
                            } else {
                                fileContentsAsString = Files.readString(file, Charset.defaultCharset());
                            }

                            String filePath = file.toAbsolutePath().toString();
                            String fullFileName = file.toString();
                            String pathFromSourceFolder = DIRECTORY_PATH.relativize(file).toString();

                            toInterpretFiles.add(new ToInterpretFile(
                                    fileName,
                                    filePath,
                                    fullFileName,
                                    fileExtension,
                                    pathFromSourceFolder,
                                    fileContentsAsString
                            ));
                        } catch (MalformedInputException e) {
                            log.warn("Skipping non-text file: {}", file, e);
                        } catch (IOException e) {
                            log.error("Error reading file: {}", file, e);
                        }
                    });
        } catch (IOException e) {
            log.error("Error reading files from directory: {}", DIRECTORY_PATH, e);
        }
        log.info("Found {} files to interpret", toInterpretFiles.size());
        return toInterpretFiles;
    }

    private static boolean isAllowedFileExtension(String extension) {
        return ALLOWED_FILE_EXTENSIONS.contains(extension.toLowerCase());
    }

    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex + 1);
    }
}
