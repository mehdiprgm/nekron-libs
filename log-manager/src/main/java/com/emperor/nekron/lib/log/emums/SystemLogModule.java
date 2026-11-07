package com.emperor.nekron.lib.log.emums;

/**
 * System modules for categorizing logs.
 * <p>
 * Each module represents a specific part or subsystem of the application,
 * allowing logs to be identified and filtered by their origin.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 3.0
 */
public enum SystemLogModule {
    NETWORK,
    HTTP,
    DATABASE,
    FILE,
    IO,
    SECURITY,
    CONFIGURATION,
    APPLICATION,
    SYSTEM,
    SCHEDULER,
    INTEGRATION,
    LIFECYCLE
}