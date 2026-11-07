package com.emperor.nekron.lib.log.emums;

/**
 * Log levels for classifying the severity or nature of log messages.
 * <p>
 * Each type represents a specific category of log entry, allowing
 * developers to filter and handle logs appropriately based on their
 * importance and purpose.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 2.0
 */
public enum SystemLogType {
    SUCCESS,
    WARNING,
    ERROR,
    EXCEPTION,
    INFORMATION
}