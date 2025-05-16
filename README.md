# Bjorn's MCP Server

## Overview
This project is a Java-based server application that uses the **Model Context Protocol (MCP)** to process and manage files. It includes utilities for reading and interpreting files, managing configurations, and interacting with the MCP server.

## Features
- Reads and processes files from a specified directory.
- Filters files based on allowed extensions.
- Provides file details such as name, path, extension, and contents.
- Implements a sync tool specification for MCP server integration.
- Configurable via a `config.json` file.

## Project Structure
- **`src/main/java/com/bjornsmcpserver/model/ToInterpretFile.java`**: Defines the `ToInterpretFile` record for file metadata.
- **`src/main/java/bjorn/mcp/Main.java`**: Entry point for the application and MCP server setup.
- **`src/main/java/bjorn/mcp/utils/FileUtils.java`**: Utility class for reading and processing files.
- **`src/main/java/bjorn/mcp/utils/ConfigUtils.java`**: Utility class for loading and accessing configuration properties.

## Configuration
The project uses a `config.json` file located in the `resources` directory. Example configuration:
```json
{
  "serverName": "BjornMCPServer",
  "serverVersion": "1.0.0",
  "allowedFileExtensions": ["txt", "json", "xml"]
}