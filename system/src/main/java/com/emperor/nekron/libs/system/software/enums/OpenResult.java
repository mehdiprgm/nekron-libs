package com.emperor.nekron.libs.system.software.enums;

/**
 * Represents the possible results of an attempt to open a file or resource.
 * <p>
 * This enum provides a standardized set of outcomes that can be returned
 * from file and resource opening operations, helping to communicate the
 * result status to the calling code.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public enum OpenResult {
    SUCCESS,
    FAILED,
    PATH_NOT_FOUND
}