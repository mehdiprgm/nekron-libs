package com.emperor.nekron.lib.log.environment;

import com.emperor.nekron.lib.log.SystemLog;
import com.emperor.nekron.lib.log.emums.SystemLogModule;
import com.emperor.nekron.lib.log.emums.SystemLogType;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Writes log entries to the terminal with optional color highlighting.
 * <p>
 * This logger outputs log messages to either standard output or standard error
 * depending on the log type. Error and exception logs are written to standard error,
 * while all other log types are written to standard output. Color coding can be
 * enabled or disabled to improve readability in terminal environments.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 5.0
 * @since 21
 */
public class Logs {
    public static final String FR_RED_BOLD_BRIGHT = "\u001b[1;91m";
    public static final String FR_GREEN_BOLD_BRIGHT = "\u001b[1;92m";
    public static final String FR_YELLOW_BOLD_BRIGHT = "\u001b[1;93m";
    public static final String FR_BLUE_BOLD_BRIGHT = "\u001b[1;94m";
    public static final String FR_RESET = "\u001b[0m";

    /**
     * Writes a log entry to the terminal with appropriate color formatting.
     * <p>
     * Success logs are displayed in green, warnings in yellow, errors and exceptions
     * in red, and information logs in blue when colors are enabled.
     * Error and exception logs are directed to standard error, while all others
     * are directed to standard output.
     *
     * @param systemLog the log entry to be written to the terminal
     * @return a pair containing a status message and a boolean indicating
     * that the write operation was successful
     */
    public static Pair<String, Boolean> write(SystemLog systemLog) {
        switch (systemLog.getSystemLogType()) {
            case SUCCESS -> System.out.print(FR_GREEN_BOLD_BRIGHT);
            case WARNING -> System.out.print(FR_YELLOW_BOLD_BRIGHT);
            case ERROR, EXCEPTION -> System.out.print(FR_RED_BOLD_BRIGHT);
            case INFORMATION -> System.out.print(FR_BLUE_BOLD_BRIGHT);
        }

        if (systemLog.getSystemLogType() == SystemLogType.ERROR || systemLog.getSystemLogType() == SystemLogType.EXCEPTION) {
            System.err.printf("%s%s\n", systemLog, FR_RESET);
        } else {
            System.out.printf("%s%s\n", systemLog, FR_RESET);
        }

        return Pair.of("Log written successfully.", true);
    }

    /**
     * Logs a success message with the specified module.
     * <p>
     * This is a convenience method that creates a {@code SystemLog} entry of type
     * {@code SUCCESS} and delegates to {@link #write(SystemLog)}.
     * Success messages are displayed in green when colors are enabled.
     *
     * @param message the success message to log
     * @param module  the module associated with this log entry
     * @return a pair containing a status message and a boolean indicating
     * that the write operation was successful
     */
    public static Pair<String, Boolean> success(String message, SystemLogModule module) {
        return write(new SystemLog(message, module, SystemLogType.SUCCESS));
    }

    /**
     * Logs a warning message with the specified module.
     * <p>
     * This is a convenience method that creates a {@code SystemLog} entry of type
     * {@code WARNING} and delegates to {@link #write(SystemLog)}.
     * Warning messages are displayed in yellow when colors are enabled.
     *
     * @param message the warning message to log
     * @param module  the module associated with this log entry
     * @return a pair containing a status message and a boolean indicating
     * that the write operation was successful
     */
    public static Pair<String, Boolean> warning(String message, SystemLogModule module) {
        return write(new SystemLog(message, module, SystemLogType.WARNING));
    }

    /**
     * Logs an error message with the specified module.
     * <p>
     * This is a convenience method that creates a {@code SystemLog} entry of type
     * {@code ERROR} and delegates to {@link #write(SystemLog)}.
     * Error messages are displayed in red and written to standard error.
     *
     * @param message the error message to log
     * @param module  the module associated with this log entry
     * @return a pair containing a status message and a boolean indicating
     * that the write operation was successful
     */
    public static Pair<String, Boolean> error(String message, SystemLogModule module) {
        return write(new SystemLog(message, module, SystemLogType.ERROR));
    }

    /**
     * Logs an informational message with the specified module.
     * <p>
     * This is a convenience method that creates a {@code SystemLog} entry of type
     * {@code INFORMATION} and delegates to {@link #write(SystemLog)}.
     * Information messages are displayed in blue when colors are enabled.
     *
     * @param message the informational message to log
     * @param module  the module associated with this log entry
     * @return a pair containing a status message and a boolean indicating
     * that the write operation was successful
     */
    public static Pair<String, Boolean> information(String message, SystemLogModule module) {
        return write(new SystemLog(message, module, SystemLogType.INFORMATION));
    }
}