package com.emperor.nekron.libs.system.software;

import com.emperor.nekron.libs.system.software.enums.OpenResult;
import com.emperor.nekron.libs.system.software.records.Uptime;
import oshi.SystemInfo;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides comprehensive operating system information and system utilities.
 * <p>
 * This class offers a wide range of static methods for retrieving OS details
 * including system properties, user information, clipboard operations,
 * file/directory opening, and system uptime calculations. It serves as a
 * central utility for interacting with the underlying operating system.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 7.0
 * @since 21
 */
public class OS {

    /**
     * Enumeration for selecting system time or date output format.
     */
    public enum SystemTimeDate {
        DATE,
        TIME,
        ALL
    }

    /**
     * Retrieves the name of the operating system.
     * <p>
     * This returns the OS name as reported by the system property
     * (e.g., "Windows 10", "Windows 11", "Linux", "macOS").
     * </p>
     *
     * @return The operating system name string
     */
    public static String getName() {
        return System.getProperty("os.name");
    }

    /**
     * Retrieves the architecture of the operating system.
     * <p>
     * This returns the OS architecture as reported by the system property
     * (e.g., "amd64", "x86", "aarch64").
     * </p>
     *
     * @return The operating system architecture string
     */
    public static String getArchitecture() {
        return System.getProperty("os.arch");
    }

    /**
     * Retrieves the kernel version of the operating system.
     * <p>
     * This returns the OS version string which typically represents the kernel
     * or build number (e.g., "10.0", "6.1.7601").
     * </p>
     *
     * @return The operating system version string
     */
    public static String getKernelModel() {
        return System.getProperty("os.version");
    }

    /**
     * Retrieves the host name of the system.
     * <p>
     * This returns the network name of the local machine as configured
     * in the operating system.
     * </p>
     *
     * @return The host name string
     * @throws UnknownHostException If the local host name cannot be resolved
     */
    public static String getHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

    /**
     * Retrieves the username of the current system user.
     * <p>
     * This returns the account name under which the JVM is currently running.
     * </p>
     *
     * @return The username string
     */
    public static String getUsername() {
        return System.getProperty("user.name");
    }

    /**
     * Retrieves the home directory path of the current user.
     * <p>
     * This returns the user's home directory path (e.g., "C:\Users\Username"
     * on Windows, "/home/username" on Linux).
     * </p>
     *
     * @return The home directory path string
     */
    public static String getHomeDirectory() {
        return System.getProperty("user.home");
    }

    /**
     * Retrieves the current working directory of the JVM.
     * <p>
     * This returns the directory from which the Java application was launched.
     * </p>
     *
     * @return The working directory path string
     */
    public static String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * Retrieves the system language code.
     * <p>
     * This returns the ISO language code (e.g., "en", "fa", "de")
     * as configured in the operating system.
     * </p>
     *
     * @return The language code string
     */
    public static String getLanguage() {
        return System.getProperty("user.language");
    }

    /**
     * Retrieves the current system time or date formatted with a custom pattern.
     * <p>
     * This method allows flexible formatting using {@link DateTimeFormatter}
     * pattern strings (e.g., "yyyy-MM-dd HH:mm:ss").
     * </p>
     *
     * @param customFormat The custom date-time format pattern
     * @return The formatted time/date string
     */
    public static String getTimeDate(String customFormat) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(customFormat);
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    /**
     * Retrieves the current system time or date using predefined formats.
     * <p>
     * This method returns either the current time in "HH:mm:ss" format
     * or the current date in "yyyy/MM/dd" format based on the selected
     * {@link SystemTimeDate} enum value.
     * </p>
     *
     * @param timeDate The enum value specifying whether to return DATE or TIME
     * @return The formatted date or time string
     */
    public static String getTimeDate(SystemTimeDate timeDate) {
        LocalDateTime now = LocalDateTime.now();

        return switch (timeDate) {
            case TIME -> DateTimeFormatter.ofPattern("HH:mm:ss").format(now);
            case DATE -> DateTimeFormatter.ofPattern("yyyy/MM/dd").format(now);
            case ALL -> DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(now);
        };
    }

    /**
     * Retrieves the current content of the system clipboard as text.
     * <p>
     * This method checks if the clipboard contains string data and returns it.
     * If the clipboard is empty or does not contain text, an empty string is returned.
     * </p>
     *
     * @return The text content from the clipboard, or empty string if no text is available
     * @throws IOException                If an I/O error occurs accessing the clipboard
     * @throws UnsupportedFlavorException If the clipboard data is not in string format
     */
    public static String pasteClipboard() throws IOException, UnsupportedFlavorException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        return clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor) ? clipboard.getData(DataFlavor.stringFlavor).toString() : "";
    }

    /**
     * Copies the specified text to the system clipboard.
     * <p>
     * This method sets the clipboard content to the provided text string,
     * making it available for pasting in other applications.
     * </p>
     *
     * @param text The text to be copied to the clipboard
     * @return {@code true} always (operation is always performed)
     */
    public static boolean copyText(String text) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        return true;
    }

    /**
     * Opens a file, directory, or executable using the system's default application.
     * This method supports opening various types of paths:
     * <ul>
     *   <li><b>Directories:</b> Opens in the system file explorer</li>
     *   <li><b>Executables:</b> Launches .exe, .bat, .cmd, .com files directly</li>
     *   <li><b>Other files:</b> Opens with the associated default application</li>
     * </ul>
     *
     * @param path The file system path to open
     * @return An {@link OpenResult} indicating the outcome:
     * {@code SUCCESS} if opened successfully,
     * {@code PATH_NOT_FOUND} if the path does not exist,
     * {@code FAILED} if an error occurs during opening
     */
    public static OpenResult open(String path) {
        try {
            Path pth = Paths.get(path);
            if (!Files.exists(pth)) {
                return OpenResult.PATH_NOT_FOUND;
            }

            Desktop.getDesktop().open(pth.toFile());
            return OpenResult.SUCCESS;
        } catch (IOException ex) {
            return OpenResult.FAILED;
        }
    }

    /**
     * Retrieves the current system uptime.
     *
     * @return an {@code Uptime} containing the number of days, hours, minutes,
     * and seconds the system has been running; the milliseconds field
     * is always zero since the source resolution is one second
     * @throws IOException if the system uptime cannot be retrieved
     */
    public static Uptime getSystemUptime() throws IOException {
        long uptimeSeconds = new SystemInfo().getOperatingSystem().getSystemUptime();

        int second = (int) (uptimeSeconds % 60);
        long totalMinutes = uptimeSeconds / 60;

        int minute = (int) (totalMinutes % 60);
        long totalHours = totalMinutes / 60;

        int hour = (int) (totalHours % 24), day = (int) (totalHours / 24);
        return new Uptime(day, hour, minute, second, 0);
    }
}