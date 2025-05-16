package bjorn.mcp;

import bjorn.mcp.presentation.Presentation;
import bjorn.mcp.presentation.PresentationTools;
import bjorn.mcp.utils.FileUtils;
import bjorn.mcp.utils.ConfigUtils; // Import the new utility class
import bjorn.mcp.utils.SchemaUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bjornsmcpserver.model.ToInterpretFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final PresentationTools presentationTools = new PresentationTools();

    public static void main(String[] args) {
        // STDIO Server Transport
        var transportProvider = new StdioServerTransportProvider(new ObjectMapper());
        // Sync tool specification
        var syncToolSpec = getFileReadSynclSpecification();

        String serverName = ConfigUtils.getConfigProperty("serverName");
        String serverVersion = ConfigUtils.getConfigProperty("serverVersion");

        McpServer.sync(transportProvider)
                .serverInfo(serverName, serverVersion)
                .capabilities(McpSchema.ServerCapabilities.builder()
                        .tools(true)
                        .logging()
                        .build())
                .tools(syncToolSpec)
                .build();
        log.info("Starting server...");
    }

    private static McpServerFeatures.SyncToolSpecification getFileReadSynclSpecification() {
        return new McpServerFeatures.SyncToolSpecification(
                new McpSchema.Tool("get_to_interpret_files", "Get a list of found and to interpret files", SchemaUtils.SCHEMA),
                (McpSyncServerExchange exchange, Map<String, Object> arguments) -> {
                    List<ToInterpretFile> toInterpretFiles = FileUtils.readToInterpretFiles();
                    List<McpSchema.Content> contents = new ArrayList<>();
                    for (ToInterpretFile toInterpretFile : toInterpretFiles) {
                        log.info(toInterpretFile.toString());
                        contents.add(new McpSchema.TextContent(toInterpretFile.toString()));
                    }
                    return new McpSchema.CallToolResult(contents, false);
                });
    }
}
