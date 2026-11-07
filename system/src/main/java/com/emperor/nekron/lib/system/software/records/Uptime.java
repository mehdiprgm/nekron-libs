package com.emperor.nekron.lib.system.software.records;

/**
 * Represents the system uptime broken down into its time components.
 * <p>
 * This record provides a structured representation of the system's runtime duration
 * since the last boot, expressed as separate units for days, hours, minutes, seconds,
 * and milliseconds. It is useful for displaying human-readable uptime information
 * in monitoring and diagnostic applications.
 * </p>
 *
 * @param day          The number of days the system has been running
 * @param hour         The number of hours (0-23) component of the uptime
 * @param minute       The number of minutes (0-59) component of the uptime
 * @param second       The number of seconds (0-59) component of the uptime
 * @param milliseconds The number of milliseconds (0-999) component of the uptime
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 2.0
 */
public record Uptime(int day, int hour, int minute, int second, int milliseconds) {
}