package com.emperor.nekron.lib.log.interfaces;

import com.emperor.nekron.lib.log.SystemLog;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Core logging interface for writing log entries to various outputs.
 * <p>
 * Implementing classes are responsible for writing {@link SystemLog} entries
 * to their designated destinations such as console, files, or other outputs.
 * Each implementation determines how the log entry is formatted, stored,
 * and whether any additional processing is applied.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
public interface Logger {
    /**
     * Writes the specified system log entry to the output destination.
     * <p>
     * The implementation handles the actual writing process, which may include
     * formatting, colorizing, or transforming the log entry before output.
     *
     * @param systemLog the log entry to be written, containing all necessary
     *                  information such as type, message, timestamp, and source
     * @return a pair containing a status message and a boolean indicating
     *         whether the write operation was successful (true for success,
     *         false for failure)
     */
    Pair<String, Boolean> write(SystemLog systemLog);
}
