package com.emperor.nekron.libs.file.path;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;

/**
 * Utility class providing common operations for file and directory path manipulation.
 * This class simplifies path handling by abstracting away platform-specific details
 * and providing consistent methods for path normalization, parsing, and navigation.
 *
 * <p>All methods are static and can be used without instantiation. The class leverages
 * Apache Commons IO and Lang libraries for robust path operations.</p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 5.0
 * @since 21
 */
public class PathUtils {
    private static boolean isPathRoot(String path) {
        for (File root : File.listRoots()) {
            if (path.equals(root.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Enumeration of standard user home directory locations.
     * These directories are commonly used for storing user-specific files and documents.
     */
    public enum HomeDirectory {
        DESKTOP,
        DOCUMENTS,
        DOWNLOADS,
        MUSIC,
        VIDEOS,
        PICTURES
    }

    /**
     * Enumeration specifying which part of the parent path should be returned.
     * Used to control the output format when retrieving parent path information.
     */
    public enum ParentPath {
        PATH,
        NAME
    }

    /**
     * Normalizes a path string by removing redundant separators and resolving dot notations.
     * This method converts a path to a standard form, making it suitable for further operations.
     *
     * @param path          the path string to normalize
     * @param unixSeparator if {@code true}, separators are converted to forward slashes (/);
     *                      if {@code false}, system default separators are used
     * @return the normalized path string, or {@code null} if the path is invalid
     * @see FilenameUtils#normalize(String, boolean)
     */
    public static String normalize(String path, boolean unixSeparator) {
        return FilenameUtils.normalize(path, unixSeparator);
    }

    /**
     * Retrieves the parent directory of the specified path.
     * If the path is a root directory, the original path is returned.
     *
     * @param path       the path whose parent is to be retrieved
     * @param parentPath specifies whether to return the full parent path or just the directory name
     * @return the parent path as specified by the {@code parentPath} parameter,
     * or the original path if it is a root directory
     * @throws NullPointerException if the path is {@code null}
     */
    public static String getParent(String path, ParentPath parentPath) {
        if (isPathRoot(path)) {
            return path;
        }

        Path pth = Path.of(path);

        return switch (parentPath) {
            case PATH -> pth.getParent().toString();
            case NAME -> pth.getParent().getFileName().toString();
        };
    }

    /**
     * Extracts the file or directory name from the end of the specified path.
     * If the path is a root directory, the original path is returned.
     *
     * @param path the path from which to extract the name
     * @return the last component of the path (file name or directory name),
     * or the original path if it is a root directory
     * @throws NullPointerException if the path is {@code null}
     */
    public static String getName(String path) {
        if (isPathRoot(path)) {
            return path;
        }

        return Path.of(path).getFileName().toString();
    }

    /**
     * Returns the base name of the specified file path, excluding the extension.
     * For example, for "document.pdf", this method returns "document".
     *
     * @param path the file path from which to extract the base name
     * @return the base name without the extension, or {@code null} if the path is {@code null}
     * @see FilenameUtils#getBaseName(String)
     */
    public static String getBaseName(String path) {
        return FilenameUtils.getBaseName(path);
    }

    /**
     * Extracts the file extension from the specified path.
     * The extension is the portion of the filename after the last dot.
     * For example, for "document.pdf", this method returns "pdf".
     *
     * @param path the file path from which to extract the extension
     * @return the file extension (without the dot), or an empty string if no extension exists
     * @see FilenameUtils#getExtension(String)
     */
    public static String getExtension(String path) {
        return FilenameUtils.getExtension(path);
    }

    /**
     * Returns the full path to a specified standard user home directory.
     * This method constructs the path by combining the user's home directory
     * with the requested directory name.
     *
     * <p>Example: For DESKTOP, returns "C:\Users\Username\Desktop" on Windows.</p>
     *
     * @param homeDirectory the standard home directory to locate
     * @return the absolute path to the requested home directory
     * @throws NullPointerException if the homeDirectory is {@code null}
     * @see System#getProperty(String)
     */
    public static String getHomeDirectoryPath(HomeDirectory homeDirectory) {
        return "%s\\%s".formatted(System.getProperty("user.home"),
                StringUtils.capitalize(homeDirectory.toString().toLowerCase()));
    }
}