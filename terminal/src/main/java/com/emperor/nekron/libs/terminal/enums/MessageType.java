package com.emperor.nekron.libs.terminal.enums;

/**
 * Represents predefined message types with their associated terminal colors.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public enum MessageType {
    ERROR("\u001b[0;91m"),
    WARNING("\u001b[0;93m"),
    SUCCESS("\u001b[0;92m"),
    INFORMATION("\u001b[0;96m");

    private final String color;

    /**
     * Creates a message type with the specified terminal color.
     *
     * @param color the ANSI color code for the message type
     */
    MessageType(String color) {
        this.color = color;
    }

    /**
     * Returns the ANSI color code of this message type.
     *
     * @return the ANSI color code
     */
    public String color() {
        return color;
    }
}