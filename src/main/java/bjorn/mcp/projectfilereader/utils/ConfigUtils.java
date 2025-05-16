package bjorn.mcp.projectfilereader.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class ConfigUtils {
    private static final Logger log = LoggerFactory.getLogger(ConfigUtils.class);
    private static final Map<String, Object> config;

    static {
        try (InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream("config.json")) {
            if (input == null) {
                throw new IOException("config.json file not found in resources");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readValue(input, Map.class);
        } catch (IOException e) {
            log.error("Failed to load config.json file", e);
            throw new RuntimeException(e);
        }
    }

    public static String getConfigProperty(String key) {
        Object value = config.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        throw new IllegalArgumentException("Property '" + key + "' is not a string");
    }

    @SuppressWarnings("unchecked")
    public static <T> T getConfigPropertyAsList(String key, Class<T> type) {
        Object value = config.get(key);
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        throw new IllegalArgumentException("Property '" + key + "' is not of type " + type.getName());
    }
}
