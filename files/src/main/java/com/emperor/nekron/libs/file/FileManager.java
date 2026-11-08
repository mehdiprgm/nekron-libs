package com.emperor.nekron.libs.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Comprehensive file and directory management utility class.
 * Provides high-level operations for file system manipulation including
 * creation, deletion, copying, moving, and searching operations.
 *
 * <p>This class abstracts away complex file I/O operations and provides
 * a simple, consistent API for common file management tasks. It leverages
 * Apache Commons IO and Java NIO for robust and efficient file handling.</p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 4.0
 * @since 17
 */
public class FileManager {

    /**
     * Specifies the type of file system entry for filtering operations.
     * Used to distinguish between regular files and directories during
     * existence checks, listing, and walking operations.
     */
    public enum FileType {
        FILE,
        DIRECTORY,
        ANY
    }

    /**
     * Determines whether the specified path is a root directory on the filesystem.
     *
     * @param path the path to check
     * @return {@code true} if the path is a root directory (e.g., C:\, /),
     * {@code false} otherwise
     */
    public static boolean isRoot(String path) {
        for (File root : File.listRoots()) {
            if (root.getAbsolutePath().equalsIgnoreCase(path)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if a file or directory exists at the specified path.
     * The check can be filtered to verify the entry type.
     *
     * @param path     the path to check
     * @param fileType the type of entry to check for (FILE, DIRECTORY, or ANY)
     * @return {@code true} if an entry of the specified type exists at the path,
     * {@code false} otherwise
     */
    public static boolean exists(String path, FileType fileType) {
        Path pth = Path.of(path);

        if (Files.exists(pth)) {
            return switch (fileType) {
                case FILE -> Files.isRegularFile(pth);
                case DIRECTORY -> Files.isDirectory(pth);
                case ANY -> true;
            };
        }

        return false;
    }

    /**
     * Creates a new directory at the specified path.
     * If the directory already exists, the behavior depends on the overwrite flag.
     *
     * @param path      the path where the directory should be created
     * @param overwrite if {@code true}, existing directory will be deleted and recreated;
     *                  if {@code false}, existing directory will be preserved
     * @return {@code true} if the directory exists or was successfully created
     * @throws IOException if an I/O error occurs during directory creation
     */
    public static boolean mkdir(String path, boolean overwrite) throws IOException {
        File dir = new File(path);
        if (exists(path, FileType.DIRECTORY) & !overwrite) {
            return true;
        }

        FileUtils.deleteDirectory(dir);
        FileUtils.forceMkdir(dir);

        return true;
    }

    /**
     * Creates a new empty file at the specified path.
     * If the file already exists, the behavior depends on the overwrite flag.
     *
     * @param path      the path where the file should be created
     * @param overwrite if {@code true}, existing file will be deleted and recreated;
     *                  if {@code false}, existing file will be preserved
     * @return {@code true} if the file exists or was successfully created
     * @throws IOException if an I/O error occurs during file creation
     */
    public static boolean mkfile(String path, boolean overwrite) throws IOException {
        Path pth = Path.of(path);
        if (exists(path, FileType.FILE) && !overwrite) {
            return true;
        }

        Files.deleteIfExists(pth);
        Files.createFile(pth);

        return true;
    }

    /**
     * Deletes the file or directory at the specified path.
     * For directories, the entire content will be deleted recursively.
     *
     * @param path the path to delete
     * @return {@code true} if deletion was successful or the path didn't exist
     * @throws IOException if an I/O error occurs during deletion
     */
    public static boolean delete(String path) throws IOException {
        Path pth = Path.of(path);

        if (Files.isDirectory(pth)) {
            FileUtils.deleteDirectory(new File(path));
            return true;
        }

        Files.deleteIfExists(pth);
        return true;
    }

    /**
     * Copies a file or directory from source to destination.
     * The operation handles both files and directories automatically.
     *
     * @param src       the source path to copy from
     * @param dest      the destination path to copy to
     * @param overwrite if {@code true}, existing destination will be overwritten;
     *                  if {@code false}, operation will be skipped if destination exists
     * @return {@code true} if the copy was successful or skipped due to existing destination
     * @throws IOException if an I/O error occurs during copying
     */
    public static boolean copy(String src, String dest, boolean overwrite) throws IOException {
        if (exists(dest, FileType.ANY) && !overwrite) {
            return true;
        }

        if (Files.isRegularFile(Path.of(src))) {
            FileUtils.copyFile(new File(src), new File(dest));
        } else {
            FileUtils.copyDirectory(new File(src), new File(dest));
        }

        return true;
    }

    /**
     * Moves a file or directory from source to destination.
     * The operation copies the content to the destination and then deletes the source.
     *
     * @param src       the source path to move from
     * @param dest      the destination path to move to
     * @param overwrite if {@code true}, existing destination will be overwritten;
     *                  if {@code false}, operation will be skipped if destination exists
     * @return {@code true} if the move was successful or skipped due to existing destination
     * @throws IOException if an I/O error occurs during moving
     */
    public static boolean move(String src, String dest, boolean overwrite) throws IOException {
        if (exists(dest, FileType.ANY) && !overwrite) {
            return true;
        }

        if (Files.isRegularFile(Path.of(src))) {
            FileUtils.copyFile(new File(src), new File(dest));
            FileUtils.delete(new File(src));
        } else {
            FileUtils.copyDirectory(new File(src), new File(dest));
            FileUtils.deleteDirectory(new File(src));
        }

        return true;
    }

    /**
     * Lists the immediate content of a directory without traversing subdirectories.
     * Results can be filtered by file type and hidden status.
     *
     * @param path       the directory path to list
     * @param showHidden if {@code true}, hidden files and directories are included;
     *                   if {@code false}, they are excluded
     * @param fileType   the type of entries to include (FILE, DIRECTORY, or ANY)
     * @return a list of paths representing the directory content,
     * or a single entry if the path is not a directory
     */
    public static List<Path> list(String path, boolean showHidden, FileType fileType) {
        List<Path> content = new ArrayList<>();
        Path dir = Path.of(path);

        if (!Files.isDirectory(dir)) {
            content.add(dir);
            return content;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                try {
                    if (!showHidden && Files.isHidden(entry)) {
                        continue;
                    }

                    switch (fileType) {
                        case FILE -> {
                            if (Files.isRegularFile(entry)) {
                                content.add(entry);
                            }
                        }

                        case DIRECTORY -> {
                            if (Files.isDirectory(entry)) {
                                content.add(entry);
                            }
                        }

                        case ANY -> {
                            content.add(entry);
                        }
                    }

                } catch (IOException | SecurityException ignored) {
                }
            }
        } catch (IOException | SecurityException ignored) {
        }

        return content;
    }

    /**
     * Recursively traverses a directory and returns all content within it.
     * This method walks through all subdirectories and collects entries
     * based on the specified filters.
     *
     * @param path       the starting directory path
     * @param showHidden if {@code true}, hidden files and directories are included;
     *                   if {@code false}, they are excluded
     * @param fileType   the type of entries to include (FILE, DIRECTORY, or ANY)
     * @return a list of paths representing all content in the directory tree,
     * excluding the root directory itself
     */
    public static List<Path> walk(String path, boolean showHidden, FileType fileType) {
        List<Path> content = new ArrayList<>();
        Path start = Path.of(path);

        if (!Files.isDirectory(start)) {
            content.add(start);
            return content;
        }

        try {
            Files.walkFileTree(start, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    try {
                        if (!showHidden && Files.isHidden(file)) {
                            return FileVisitResult.CONTINUE;
                        }

                        if (fileType == FileType.FILE || fileType == FileType.ANY) {
                            if (Files.isRegularFile(file)) {
                                content.add(file);
                            }
                        }
                    } catch (IOException ignored) {
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    try {
                        if (!showHidden && Files.isHidden(dir)) {
                            return FileVisitResult.SKIP_SUBTREE;
                        }

                        if (fileType == FileType.DIRECTORY || fileType == FileType.ANY) {
                            content.add(dir);
                        }
                    } catch (IOException ignored) {
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ignored) {
        }

        content.remove(start);
        return content;
    }

    /**
     * Creates a symbolic link to the given source path inside the specified
     * destination directory.
     * <p>
     * The name of the created link matches the file name of the source path.
     * If a file or link with the same name already exists in the destination
     * directory, it is removed before the new link is created.
     *
     * @param path            the path of the file or directory to link to
     * @param destinationPath the directory where the symbolic link will be created
     * @return {@code true} if the shortcut was created successfully
     * @throws IOException if the link cannot be created or an existing entry
     *                     cannot be removed
     */
    public static boolean createShortcut(String path, String destinationPath) throws IOException {
        Path source = Path.of(path);
        String fileName = source.getFileName().toString();

        Path linkPath = Path.of(destinationPath, fileName);

        Files.deleteIfExists(linkPath);
        Files.createSymbolicLink(linkPath, source.toAbsolutePath());

        return true;
    }
}