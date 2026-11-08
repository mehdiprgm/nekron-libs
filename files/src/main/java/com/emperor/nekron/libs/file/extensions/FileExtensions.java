package com.emperor.nekron.libs.file.extensions;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


/**
 * Contains common file extension groups and utility methods for detecting
 * file type based on its extension or path. Useful for categorizing files
 * across different domains such as images, documents, audio, and executables.
 * <p>
 * Each category provides a list of recognized extensions for quick matching.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
public class FileExtensions {
    public static final List<String> IMAGE_FILES;
    public static final List<String> AUDIO_FILES;
    public static final List<String> VIDEO_FILES;
    public static final List<String> EXECUTABLE_FILES;
    public static final List<String> DOCUMENT_FILES;
    public static final List<String> FONT_FILES;
    public static final List<String> ARCHIVE_FILES;
    public static final List<String> STREAM_FILES;
    public static final List<String> DATABASE_FILES;
    public static final List<String> LIBRARY_FILES;
    public static final List<String> TEXT_FILES;

    public static final String PDF_FILE = "pdf";
    public static final String SIGNATURE_FILE = "cat";
    public static final String SETTINGS_FILE = "ini";
    public static final String SCREEN_SAVER_FILE = "scr";
    public static final String ZIP_FILE = "zip";

    static {
        IMAGE_FILES = List.of(
                "jpg", "jpeg", "png", "gif", "bmp",
                "webp", "tiff", "svg", "ico"
        );

        AUDIO_FILES = List.of(
                "mp3", "wav", "flac", "aac", "ogg",
                "mpa"
        );

        VIDEO_FILES = List.of(
                "mp4", "mkv", "avi", "mov", "wmv",
                "flv", "webm"
        );

        EXECUTABLE_FILES = List.of(
                "exe", "msi", "bat", "cmd", "jar",
                "sys"
        );

        DOCUMENT_FILES = List.of(
                "doc", "docx", "xls", "xlsx", "ppt",
                "pptx", "rtf", "odt"
        );

        FONT_FILES = List.of(
                "ttf", "otf", "woff"
        );

        ARCHIVE_FILES = List.of(
                "rar", "7z", "tar", "gz"
        );

        STREAM_FILES = List.of(
                "m3u8", "bin"
        );

        DATABASE_FILES = List.of(
                "db", "sqlite", "sqlite3"
        );

        LIBRARY_FILES = List.of(
                "dll", "so"
        );

        TEXT_FILES = List.of(
                "txt", "log", "inf", "java", "c",
                "h", "cpp", "cc", "cxx", "hpp",
                "hh", "hxx", "kt", "kts", "py",
                "pyw", "php", "phtml", "cs", "js",
                "mjs", "cjs", "ts", "tsx", "go",
                "hs", "lhs", "lua", "sh", "bash",
                "zsh", "ksh", "html", "htm", "css",
                "rs", "dart", "asm", "s", "rb"
        );
    }

    /**
     * Determines the descriptive file type for the specified path.
     * <p>It checks if the path is a directory, or matches one of the known
     * extension categories like image, audio, document, etc.</p>
     *
     * @param path the file path or directory path
     * @return a simple textual description of the file type or "Unknown" if not matched
     */
    public static String getFileType(String path) {
        String extension = FilenameUtils.getExtension(path).toLowerCase();
        if (Files.isDirectory(Path.of(path))) {
            return "Directory";
        }

        if (extension.isEmpty()) {
            return "Unknown";
        }

        if (IMAGE_FILES.contains(extension)) return "Image file";
        if (AUDIO_FILES.contains(extension)) return "Audio file";
        if (VIDEO_FILES.contains(extension)) return "Video file";
        if (EXECUTABLE_FILES.contains(extension)) return "Executable file";
        if (DOCUMENT_FILES.contains(extension) || extension.equals(PDF_FILE)) return "Document";
        if (FONT_FILES.contains(extension)) return "Font file";
        if (ARCHIVE_FILES.contains(extension) || extension.equals(ZIP_FILE)) return "Archive file";
        if (STREAM_FILES.contains(extension)) return "Stream file";
        if (DATABASE_FILES.contains(extension)) return "Database file";
        if (LIBRARY_FILES.contains(extension)) return "Library file";
        if (TEXT_FILES.contains(extension)) return "Text file";
        if (extension.equals(SIGNATURE_FILE)) return "Signature file";
        if (extension.equals(SETTINGS_FILE)) return "Settings file";
        if (extension.equals(SCREEN_SAVER_FILE)) return "Screen saver file";

        return "Unknown";
    }
}
