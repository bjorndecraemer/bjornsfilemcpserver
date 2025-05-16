package bjorn.mcp;

import bjorn.mcp.projectfilereader.FileMcpService;

public class Main {
    public static void main(String[] args) {
        FileMcpService fileMcpService = new FileMcpService();
        fileMcpService.startServer();
    }
}
