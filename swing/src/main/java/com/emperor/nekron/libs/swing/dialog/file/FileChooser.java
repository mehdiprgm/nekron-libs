package com.emperor.nekron.libs.swing.dialog.file;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Provides a configurable file selection dialog utility for Swing applications.
 * <p>
 * This class wraps the {@link JFileChooser} component with a fluent and flexible API
 * for both open and save dialogs. It supports file filtering, selection mode configuration
 * (files, directories, or both), and multiple file selection. The class uses a builder-like
 * pattern where properties can be set before invoking open or save operations.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 2.0
 */
@Data
public class FileChooser {
    private List<FileNameExtensionFilter> filters;
    private boolean multipleSelection;

    private int convertFileSelect(FileSelect fileSelect) {
        return switch (fileSelect) {
            case ALL -> JFileChooser.FILES_AND_DIRECTORIES;
            case DIRECTORY -> JFileChooser.DIRECTORIES_ONLY;
            case FILE -> JFileChooser.FILES_ONLY;
        };
    }

    private JFileChooser createFileChooser(String title, String startPath, FileSelect fileSelect) {
        JFileChooser fileChooser;

        if (startPath == null) {
            fileChooser = new JFileChooser(new File(System.getProperty("user.home")));
        } else {
            fileChooser = new JFileChooser(new File(startPath));
        }

        fileChooser.setDialogTitle(title);
        fileChooser.setMultiSelectionEnabled(multipleSelection);
        fileChooser.setFileSelectionMode(convertFileSelect(fileSelect));

        if (filters != null) {
            filters.forEach(fileChooser::addChoosableFileFilter);
        }

        return fileChooser;
    }

    /**
     * Enumeration for specifying file selection mode in the chooser dialog.
     */
    public enum FileSelect {
        ALL,
        DIRECTORY,
        FILE
    }

    /**
     * Constructs a new FileChooser with default settings.
     * Default configuration:
     * <ul>
     *   <li>No file filters</li>
     *   <li>Single selection mode</li>
     * </ul>
     */
    public FileChooser() {
        this.filters = new ArrayList<>();
        this.multipleSelection = false;
    }

    /**
     * Constructs a new FileChooser with the specified filters.
     * <p>
     * Multiple selection is disabled by default.
     * </p>
     *
     * @param filters The list of file filters to apply
     */
    public FileChooser(List<FileNameExtensionFilter> filters) {
        this(filters, false);
    }

    /**
     * Constructs a new FileChooser with the specified filters and selection mode.
     *
     * @param filters           The list of file filters to apply
     * @param multipleSelection Whether multiple files can be selected
     */
    public FileChooser(List<FileNameExtensionFilter> filters, boolean multipleSelection) {
        this.filters = filters;
        this.multipleSelection = multipleSelection;
    }


    /**
     * Displays an open file dialog with default settings.
     * <p>
     * Uses "Open" as the title, user.home as the starting path,
     * and allows selection of both files and directories.
     * </p>
     *
     * @return A list of selected paths, empty if the user cancels
     */
    public List<Path> open() {
        return open("Open", System.getProperty("user.home"), FileSelect.ALL);
    }

    /**
     * Displays an open file dialog with the specified title.
     * <p>
     * Uses user.home as the starting path and allows selection of both files and directories.
     * </p>
     *
     * @param title The dialog title
     * @return A list of selected paths, empty if the user cancels
     */
    public List<Path> open(String title) {
        return open(title, System.getProperty("user.home"), FileSelect.ALL);
    }

    /**
     * Displays an open file dialog with the specified title and starting path.
     * <p>
     * Allows selection of both files and directories.
     * </p>
     *
     * @param title      The dialog title
     * @param startPath  The initial directory path
     * @return A list of selected paths, empty if the user cancels
     */
    public List<Path> open(String title, String startPath) {
        return open(title, startPath, FileSelect.ALL);
    }

    /**
     * Displays an open file dialog with full configuration options.
     * <p>
     * This method shows the dialog and returns the selected file paths.
     * The selection behavior is controlled by the configured filters and
     * multipleSelection property.
     * </p>
     *
     * @param title      The dialog title
     * @param startPath  The initial directory path
     * @param fileSelect The file selection mode
     * @return A list of selected paths, empty if the user cancels
     */
    public List<Path> open(String title, String startPath, FileSelect fileSelect) {
        JFileChooser fileChooser = createFileChooser(title, startPath, fileSelect);
        List<Path> selectedFiles = new ArrayList<>();

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            if (multipleSelection) {
                Arrays.asList(fileChooser.getSelectedFiles()).forEach(file -> selectedFiles.add(file.toPath()));
            } else {
                selectedFiles.add(fileChooser.getSelectedFile().toPath());
            }
        }

        return selectedFiles;
    }

    /**
     * Displays a save file dialog with default settings.
     * <p>
     * Uses "Open" as the title, user.home as the starting path,
     * and allows selection of both files and directories.
     * </p>
     *
     * @return A list of selected paths, empty if the user cancels
     */
    public List<Path> save() {
        return save("Open", System.getProperty("user.home"), FileSelect.ALL);
    }

    /**
     * Displays a save file dialog with the specified title.
     * <p>
     * Uses user.home as the starting path and allows selection of both files and directories.
     * </p>
     *
     * @param title The dialog title
     * @return A list of selected paths, empty if the user cancels
     */
    public List<Path> save(String title) {
        return save(title, System.getProperty("user.home"), FileSelect.ALL);
    }

    /**
     * Displays a save file dialog with the specified title and starting path.
     * <p>
     * Allows selection of both files and directories.
     * </p>
     *
     * @param title      The dialog title
     * @param startPath  The initial directory path
     * @return A list of selected paths, empty if the user cancels
     */
    public List<Path> save(String title, String startPath) {
        return save(title, startPath, FileSelect.ALL);
    }

    /**
     * Displays a save file dialog with full configuration options.
     * <p>
     * This method shows the dialog and returns the selected file paths.
     * The selection behavior is controlled by the configured filters and
     * multipleSelection property.
     * </p>
     *
     * @param title      The dialog title
     * @param startPath  The initial directory path
     * @param fileSelect The file selection mode
     * @return A list of selected paths, empty if the user cancels
     */
    public List<Path> save(String title, String startPath, FileSelect fileSelect) {
        JFileChooser fileChooser = createFileChooser(title, startPath, fileSelect);
        List<Path> selectedFiles = new ArrayList<>();

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            if (multipleSelection) {
                Arrays.asList(fileChooser.getSelectedFiles()).forEach(file -> selectedFiles.add(file.toPath()));
            } else {
                selectedFiles.add(fileChooser.getSelectedFile().toPath());
            }
        }

        return selectedFiles;
    }
}