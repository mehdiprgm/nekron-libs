package com.emperor.nekron.libs.log.environment;

import com.emperor.nekron.libs.log.SystemLog;
import com.emperor.nekron.libs.log.emums.SystemLogModule;
import com.emperor.nekron.libs.log.emums.SystemLogType;
import com.emperor.nekron.libs.log.interfaces.Logger;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Manages log file operations including writing, reading, searching, and file maintenance.
 * <p>
 * This class provides comprehensive file-based logging capabilities with support for
 * parsing structured log entries, searching by various criteria, and managing the
 * underlying log file. Each log entry follows a specific format that includes module,
 * type, location, timestamp, and message.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class LogFileManager implements Logger {
    private String filePath;
    private boolean safeRead;

    private SystemLog parseLog(String line) {
        Pattern pattern = Pattern.compile(
                "^\\[(.+?)] \\[(.+?)] \\[(.+?) - (.+?):(\\d+)] \\[(.+?)] (.*)$"
        );

        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            if (safeRead) {
                return null;
            } else {
                throw new IllegalStateException("Corrupted log entry detected: %s".formatted(line));
            }
        }

        return new SystemLog(
                matcher.group(7),
                matcher.group(4),
                matcher.group(3).replace("()", ""),
                SystemLogModule.valueOf(matcher.group(1)),
                SystemLogType.valueOf(matcher.group(2)),
                Instant.parse(matcher.group(6)),
                Integer.parseInt(matcher.group(5))
        );
    }

    /**
     * Constructs a LogFileManager with the specified file path and default safe read mode.
     *
     * @param filePath the path to the log file
     */
    public LogFileManager(String filePath) {
        this(filePath, true);
    }

    /**
     * Writes a log entry to the file by appending it to the end.
     * <p>
     * The log entry is converted to string format using its {@code toString()} method
     * before being written. If the file does not exist, it will be created automatically.
     *
     * @param systemLog the log entry to be written
     * @return a pair containing a status message and a boolean indicating
     *         whether the write operation was successful
     */
    @Override
    public Pair<String, Boolean> write(SystemLog systemLog) {
        try {
            Path path = Path.of(getFilePath());
            Files.writeString(path, "%s\n".formatted(systemLog.toString()),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            return Pair.of("Log written successfully.", true);
        } catch (IOException ex) {
            return Pair.of(ex.getMessage(), false);
        }
    }

    /**
     * Creates a new empty log file at the configured path.
     * <p>
     * If a file already exists at the path, it will be deleted before creating
     * the new empty file.
     *
     * @return a pair containing a status message and a boolean indicating
     *         whether the operation was successful
     */
    public Pair<String, Boolean> createEmptyFile() {
        try {
            Path path = Path.of(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            Files.createFile(path);
            return Pair.of("Empty log file created successfully.", true);
        } catch (IOException ex) {
            return Pair.of(ex.getMessage(), false);
        }

    }

    /**
     * Creates the log file if it does not already exist.
     * <p>
     * This method checks for the existence of the log file at the configured path.
     * If the file already exists, no action is taken and a success status is returned.
     *
     * @return a pair containing a status message and a boolean indicating
     *         whether the file exists or was created successfully
     */
    public Pair<String, Boolean> createIfNotExists() {
        try {
            Path path = Path.of(filePath);
            if (Files.exists(path)) {
                return Pair.of("Log file already exists", true);
            }

            Files.createFile(path);
            return Pair.of("Empty log file created successfully.", true);
        } catch (IOException ex) {
            return Pair.of(ex.getMessage(), false);
        }
    }

    /**
     * Reads the entire content of the log file as a string.
     *
     * @return a pair containing the file content as a string and a boolean indicating
     *         whether the read operation was successful; on failure, the message contains
     *         the error description
     */
    public Pair<String, Boolean> read() {
        try {
            return Pair.of(Files.readString(Path.of(filePath)), true);
        } catch (IOException ex) {
            return Pair.of(ex.getMessage(), false);
        }
    }

    /**
     * Clears all content from the log file by creating a new empty file.
     * <p>
     * This operation replaces the existing file with a new empty one at the same path.
     *
     * @return a pair containing a status message and a boolean indicating
     *         whether the clear operation was successful
     */
    public Pair<String, Boolean> clear() {
        try {
            Files.writeString(Path.of(filePath), "", StandardOpenOption.CREATE_NEW);
            return Pair.of("Log file cleared successfully.", true);
        } catch (IOException ex) {
            return Pair.of(ex.getMessage(), false);
        }
    }

    /**
     * Reads all log entries from the file and parses them into SystemLog objects.
     *
     * @return a list of SystemLog objects representing all entries in the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<SystemLog> readAll() throws IOException {
        List<SystemLog> logs = new ArrayList<>();
        for (String line : Files.readAllLines(Path.of(filePath))) {
            logs.add(parseLog(line));
        }

        return logs;
    }

    /**
     * Searches for log entries that belong to the specified module.
     *
     * @param systemLogModule the module to search for
     * @return a list of SystemLog objects matching the specified module,
     *         or null if no logs could be read
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<SystemLog> searchByModule(SystemLogModule systemLogModule) throws IOException {
        List<SystemLog> logs = readAll(), foundItems = new ArrayList<>();
        if (logs == null) {
            return null;
        }

        for (SystemLog log : logs) {
            if (log.getSystemLogModule() == systemLogModule) {
                foundItems.add(log);
            }
        }

        return foundItems;
    }

    /**
     * Searches for log entries that have the specified log type.
     *
     * @param systemLogType the log type to search for
     * @return a list of SystemLog objects matching the specified type,
     *         or null if no logs could be read
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<SystemLog> searchByType(SystemLogType systemLogType) throws IOException {
        List<SystemLog> logs = readAll(), foundItems = new ArrayList<>();
        if (logs == null) {
            return null;
        }

        for (SystemLog log : logs) {
            if (log.getSystemLogType() == systemLogType) {
                foundItems.add(log);
            }
        }

        return foundItems;
    }

    /**
     * Searches for log entries within a specific time range.
     *
     * @param from the start timestamp (inclusive)
     * @param to   the end timestamp (inclusive)
     * @return a list of SystemLog objects with timestamps within the specified range
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<SystemLog> searchByTime(Instant from, Instant to) throws IOException {
        List<SystemLog> logs = readAll(), foundItems = new ArrayList<>();
        if (logs == null) {
            return null;
        }

        logs.stream()
                .filter(log -> !log.getTimestamp().isBefore(from))
                .filter(log -> !log.getTimestamp().isAfter(to))
                .forEach(foundItems::add);

        return foundItems;
    }

    /**
     * Checks whether the specified log entry exists in the file.
     *
     * @param systemLog the log entry to search for
     * @return true if the log entry exists in the file, false otherwise
     * @throws IOException if an I/O error occurs while reading the file
     */
    public boolean contains(SystemLog systemLog) throws IOException {
        List<SystemLog> logs = readAll();
        if (logs == null) {
            return false;
        }

        for (SystemLog log : logs) {
            if (log.equals(systemLog)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Counts the total number of log entries in the file.
     *
     * @return the total number of log entries
     * @throws IOException if an I/O error occurs while reading the file
     */
    public int count() throws IOException {
        int lines = 0;

        try (Stream<String> stream = Files.lines(Path.of(filePath))) {
            Iterator<String> iterator = stream.iterator();

            while (iterator.hasNext()) {
                SystemLog log = parseLog(iterator.next());
                if (log == null) {
                    lines++;
                }
            }
        }

        return lines;
    }
}