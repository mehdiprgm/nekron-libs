package com.emperor.nekron.lib.file.text;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * Provides comprehensive text file operations including reading, writing,
 * searching, counting, and content manipulation.
 * <p>
 * This class offers a wide range of file handling capabilities such as reading
 * with various methods (first line, last line, entire file), counting words,
 * characters, lines, searching with case sensitivity and whole word matching,
 * truncating content, and performing replace operations.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 7.0
 */
@Data
@AllArgsConstructor
public class TextFile {
    private String filePath;

    /**
     * Defines the types of countable items in a text file.
     */
    public enum CountItem {
        WORD,
        CHARACTER,
        LINE,
        EMPTY_LINE
    }

    /**
     * Defines the methods for reading content from a text file.
     */
    public enum ReadMethod {
        FIRST_LINE,
        LAST_LINE,
        ENTIRE_FILE
    }

    /**
     * Reads the entire content of the file as a string.
     *
     * @return the complete file content as a string
     * @throws IOException if an I/O error occurs while reading the file
     */
    public String read() throws IOException {
        return read(ReadMethod.ENTIRE_FILE, false);
    }

    /**
     * Reads the file content using the specified read method.
     *
     * @param method the read method to use (FIRST_LINE, LAST_LINE, or ENTIRE_FILE)
     * @return the file content according to the specified method
     * @throws IOException if an I/O error occurs while reading the file
     */
    public String read(ReadMethod method) throws IOException {
        return read(method, false);
    }

    /**
     * Reads the file content using the specified read method and order.
     *
     * @param method the read method to use (FIRST_LINE, LAST_LINE, or ENTIRE_FILE)
     * @param reverse if true, the content is read in reverse order (for ENTIRE_FILE method)
     * @return the file content according to the specified method and order
     * @throws IOException if an I/O error occurs while reading the file
     */
    public String read(ReadMethod method, boolean reverse) throws IOException {
        List<String> fileContent = Files.readAllLines(Path.of(filePath));
        if (fileContent.isEmpty()) {
            return "";
        }

        List<String> content = reverse ? new ArrayList<>(fileContent.reversed()) : fileContent;
        return switch (method) {
            case ReadMethod.FIRST_LINE -> content.getFirst();
            case ReadMethod.LAST_LINE -> content.getLast();
            case ReadMethod.ENTIRE_FILE -> {
                StringBuilder text = new StringBuilder();

                for (int i = 0; i < content.size(); i++) {
                    text.append(content.get(i));

                    if (i + 1 < content.size()) {
                        text.append(System.lineSeparator());
                    }
                }

                yield text.toString();
            }
        };
    }

    /**
     * Reads all lines from the file into a list.
     *
     * @return a list containing all lines from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<String> readAllLines() throws IOException {
        return Files.readAllLines(Path.of(filePath));
    }

    /**
     * Writes content to the file with optional append mode.
     *
     * @param append if true, content is appended to the end of the file;
     *               if false, the file is overwritten
     * @param content the objects to write to the file (converted to string)
     * @return true if the write operation was successful
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public boolean write(boolean append, Object... content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append));
        for (Object object : content) {
            writer.write(object.toString());
        }

        writer.close();
        return true;
    }

    /**
     * Truncates the file to the specified maximum number of lines.
     * <p>
     * This method keeps the most recent lines and removes older lines
     * when the file exceeds the specified limit.
     *
     * @param maxLines the maximum number of lines to keep
     * @return true if the truncation was successful
     * @throws IOException if an I/O error occurs while processing the file
     */
    public boolean truncate(int maxLines) throws IOException {
        Path path = Path.of(filePath);
        Deque<String> buffer = new ArrayDeque<>(maxLines);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.addLast(line);

                if (buffer.size() > maxLines) {
                    buffer.removeFirst();
                }
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String line : buffer) {
                writer.write(line);
                writer.newLine();
            }
        }

        return true;
    }

    /**
     * Searches for a key string within the file and returns the count of occurrences.
     *
     * @param key the search string
     * @param caseSensitive if true, the search is case-sensitive
     * @param wholeWord if true, only whole word matches are counted
     * @return the number of occurrences found
     * @throws IOException if an I/O error occurs while reading the file
     */
    public int find(String key, boolean caseSensitive, boolean wholeWord) throws IOException {
        String searchKey = caseSensitive ? key : key.toLowerCase(), line;
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                String processedLine = caseSensitive ? line : line.toLowerCase();

                for (String word : processedLine.split("\\s+")) {
                    if (wholeWord && word.equals(searchKey)) {
                        count++;
                    } else if (!wholeWord && word.contains(searchKey)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    /**
     * Counts the specified item type in the file.
     *
     * @param item the type of item to count (WORD, CHARACTER, LINE, or EMPTY_LINE)
     * @return the total count of the specified item
     * @throws IOException if an I/O error occurs while reading the file
     */
    public int count(CountItem item) throws IOException {
        int words = 0, characters = 0;
        int lines = 0, emptyLines = 0;

        try (Stream<String> stream = Files.lines(Path.of(filePath), StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) stream::iterator) {
                lines++;
                characters += line.length() + 1;

                if (line.trim().isEmpty()) {
                    emptyLines++;
                }

                words += line.trim().isEmpty() ? 0 : line.trim().split("\\s+").length;
            }
        }

        return switch (item) {
            case WORD -> words;
            case CHARACTER -> characters;
            case LINE -> lines;
            case EMPTY_LINE -> emptyLines;
        };
    }

    /**
     * Clears all content from the file.
     *
     * @return true if the clear operation was successful
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public boolean clear() throws IOException {
        Files.writeString(Path.of(filePath), "");
        return true;
    }

    /**
     * Replaces all occurrences of a key string with a new value in the file.
     *
     * @param key the string to search for
     * @param newValue the string to replace the key with
     * @param caseSensitive if true, the search is case-sensitive
     * @param wholeWord if true, only whole word matches are replaced
     * @return true if the replace operation was successful
     * @throws IOException if an I/O error occurs while processing the file
     */
    public boolean replace(String key, String newValue, boolean caseSensitive, boolean wholeWord) throws IOException {
        StringBuilder newText = new StringBuilder();

        for (String line : Files.readAllLines(Path.of(filePath))) {
            String originalLine = line;

            if (!caseSensitive) {
                line = line.toLowerCase();
                key = key.toLowerCase();
            }

            if (!line.isBlank()) {
                for (String word : originalLine.split("\\s+")) {
                    if (wholeWord && word.equals(key)) {
                        newText.append(newValue).append(" ");
                    } else if (!wholeWord && word.contains(key)) {
                        newText.append(word.replace(key, newValue)).append(" ");
                    } else {
                        newText.append(word).append(" ");
                    }
                }

                newText = new StringBuilder(newText.toString().trim());
            }

            newText.append(System.lineSeparator());
        }

        Files.writeString(Path.of(filePath), newText.toString());
        return true;
    }
}