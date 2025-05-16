package bjorn.mcp.projectfilereader;

import bjorn.mcp.projectfilereader.utils.ConfigUtils;
import bjorn.mcp.projectfilereader.utils.FileUtils;
import bjorn.mcp.projectfilereader.utils.SchemaUtils;
import com.bjornsmcpserver.model.ToInterpretFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileMcpService {
    private static final Logger log = LoggerFactory.getLogger(FileMcpService.class);
    public void startServer() {
        var transportProvider = new StdioServerTransportProvider(new ObjectMapper());
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

    private McpServerFeatures.SyncToolSpecification getFileReadSynclSpecification() {
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
