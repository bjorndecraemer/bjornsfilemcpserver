package bjorn.mcp.utils;

public class SchemaUtils {
    public static final String SCHEMA = """
        {
            "type": "object",
            "id": "urn:jsonschema:Operation",
            "properties" : {
                "operation" : {
                    "type": "string"
                }
            }
        }
        """;
}
