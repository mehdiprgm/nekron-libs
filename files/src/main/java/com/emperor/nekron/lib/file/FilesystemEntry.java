package com.emperor.nekron.lib.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a filesystem entry (file or directory) with comprehensive
 * metadata access and manipulation capabilities.
 *
 * <p>This class provides a rich API for inspecting and modifying file and
 * directory attributes including timestamps, permissions, ownership, icons,
 * and size information. It serves as a bridge between low-level filesystem
 * operations and high-level application needs.</p>
 *
 * <p>All operations on the filesystem entry are performed through the instance
 * which maintains the current path state. Modifications to the entry
 * (like renaming) automatically update the internal path reference.</p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 17
 */
@Data
@AllArgsConstructor
public class FilesystemEntry {
    private String path;

    private FileTime getAttribute(String path, TimeAttribute attribute) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(Path.of(path), BasicFileAttributes.class);

        return switch (attribute) {
            case MODIFY -> attrs.lastModifiedTime();
            case CREATE -> attrs.creationTime();
            case ACCESS -> attrs.lastAccessTime();
        };
    }

    /**
     * Specifies which component of a file timestamp should be retrieved.
     * Used to extract date or time portions from modification, access, or creation times.
     */
    public enum FileTimeOption {
        MODIFY_DATE, MODIFY_TIME, ACCESS_DATE, ACCESS_TIME, CREATE_DATE, CREATE_TIME
    }

    /**
     * Specifies which timestamp attribute to retrieve from a filesystem entry.
     * Used internally for selecting the appropriate time attribute.
     */
    public enum TimeAttribute {
        MODIFY, CREATE, ACCESS
    }

    /**
     * Returns a formatted date or time string for the specified timestamp attribute.
     * This method provides convenient access to individual date and time components.
     *
     * @param option specifies which date or time component to retrieve
     * @return the formatted date or time string
     * @throws IOException if an I/O error occurs while reading attributes
     */
    public String getTimeAttribute(FileTimeOption option) throws IOException {
        return switch (option) {
            case MODIFY_DATE ->
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date(getAttribute(path, TimeAttribute.MODIFY).toMillis()));
            case MODIFY_TIME ->
                    new SimpleDateFormat("HH:mm:ss").format(new Date(getAttribute(path, TimeAttribute.MODIFY).toMillis()));
            case ACCESS_DATE ->
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date(getAttribute(path, TimeAttribute.ACCESS).toMillis()));
            case ACCESS_TIME ->
                    new SimpleDateFormat("HH:mm:ss").format(new Date(getAttribute(path, TimeAttribute.ACCESS).toMillis()));
            case CREATE_DATE ->
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date(getAttribute(path, TimeAttribute.CREATE).toMillis()));
            case CREATE_TIME ->
                    new SimpleDateFormat("HH:mm:ss").format(new Date(getAttribute(path, TimeAttribute.CREATE).toMillis()));
        };
    }

    /**
     * Retrieves the system's default icon for this filesystem entry.
     * The icon is platform-specific and represents the file type.
     *
     * @return the system icon for this entry, or {@code null} if unavailable
     */
    public Icon getIcon() {
        return FileSystemView.getFileSystemView().getSystemIcon(new File(path));
    }

    /**
     * Retrieves a scaled version of the system icon for this entry.
     * The icon is resized to the specified dimensions.
     *
     * @param size the desired width and height in pixels
     * @return a scaled ImageIcon of the specified size
     */
    public ImageIcon getIcon(int size) {
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(new File(path));

        Image img = ((ImageIcon) icon).getImage();
        Image scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);

        return new ImageIcon(scaled);
    }

    /**
     * Returns the total size of this filesystem entry.
     * For directories, this includes the size of all contained files.
     *
     * @return the size in bytes, or 0 if the entry does not exist
     */
    public double getSize() {
        Path pth = Path.of(path);
        if (Files.isDirectory(pth)) {
            return FileUtils.sizeOfDirectory(new File(path));
        }

        return pth.toFile().length();
    }

    /**
     * Retrieves the owner name of this filesystem entry.
     *
     * @return the owner name as a string
     * @throws IOException if an I/O error occurs while reading ownership information
     */
    public String getOwner() throws IOException {
        return Files.getOwner(Path.of(path)).getName();
    }

    /**
     * Marks this filesystem entry as hidden on Windows systems.
     * This sets the DOS hidden attribute on the file or directory.
     *
     * @return {@code true} if the operation was successful
     * @throws IOException if an I/O error occurs while setting the attribute
     */
    public boolean makeHidden() throws IOException {
        Files.setAttribute(Path.of(path), "dos:hidden", true);
        return true;
    }

    /**
     * Makes this filesystem entry visible on Windows systems.
     * This removes the DOS hidden attribute from the file or directory.
     *
     * @return {@code true} if the operation was successful
     * @throws IOException if an I/O error occurs while setting the attribute
     */
    public boolean makeVisible() throws IOException {
        Files.setAttribute(Path.of(path), "dos:hidden", false);
        return true;
    }

    /**
     * Marks this filesystem entry as read-only on Windows systems.
     * This sets the DOS readonly attribute on the file or directory.
     *
     * @return {@code true} if the operation was successful
     * @throws IOException if an I/O error occurs while setting the attribute
     */
    public boolean makeReadOnly() throws IOException {
        Files.setAttribute(Path.of(path), "dos:readonly", true);
        return true;
    }

    /**
     * Makes this filesystem entry writable on Windows systems.
     * This removes the DOS readonly attribute from the file or directory.
     *
     * @return {@code true} if the operation was successful
     * @throws IOException if an I/O error occurs while setting the attribute
     */
    public boolean makeWritable() throws IOException {
        Files.setAttribute(Path.of(path), "dos:readonly", false);
        return true;
    }

    /**
     * Checks if this filesystem entry is hidden on Windows systems.
     *
     * @return {@code true} if the DOS hidden attribute is set
     * @throws IOException if an I/O error occurs while reading attributes
     */
    public boolean isHidden() throws IOException {
        return Files.readAttributes(Path.of(path), DosFileAttributes.class).isHidden();
    }

    /**
     * Checks if this filesystem entry is read-only on Windows systems.
     *
     * @return {@code true} if the DOS readonly attribute is set
     * @throws IOException if an I/O error occurs while reading attributes
     */
    public boolean isReadOnly() throws IOException {
        return Files.readAttributes(Path.of(path), DosFileAttributes.class).isReadOnly();
    }

    /**
     * Determines if this entry represents a directory.
     *
     * @return {@code true} if the path points to a directory
     */
    public boolean isDirectory() {
        return Files.isDirectory(Path.of(path));
    }

    /**
     * Determines if this entry represents a regular file.
     *
     * @return {@code true} if the path points to a regular file
     */
    public boolean isFile() {
        return Files.isRegularFile(Path.of(path));
    }

    /**
     * Renames this filesystem entry to the specified new name.
     * The entry remains in the same directory but gets a new filename.
     *
     * <p>After successful renaming, the internal path is updated to reflect
     * the new location.</p>
     *
     * @param newName the new name for this entry (without path)
     * @return {@code true} if the rename was successful
     * @throws IOException if an I/O error occurs during the move operation
     */
    public boolean rename(String newName) throws IOException {
        Path pth = Path.of(path);

        String parentDirPath = pth.getParent().toString();
        String newPath = parentDirPath + "\\" + newName;

        Files.move(pth, Path.of(newPath), StandardCopyOption.REPLACE_EXISTING);
        path = newPath;

        return true;
    }
}