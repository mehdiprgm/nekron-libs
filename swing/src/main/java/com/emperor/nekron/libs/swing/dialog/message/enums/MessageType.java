package com.emperor.nekron.libs.swing.dialog.message.enums;

/**
 * Defines the semantic type of message displayed in a dialog.
 *
 * <p>
 * This enumeration is used by dialog utility classes (such as {@code MessageBox})
 * to determine the visual style and meaning of a message presented to the user.
 * Each value typically maps to a corresponding message type constant in
 * {@link javax.swing.JOptionPane}, influencing the icon, tone, and intended
 * interpretation of the dialog.
 * </p>
 *
 * <p>
 * Using this abstraction allows applications to remain independent of
 * Swing-specific constants while still providing clear semantic meaning
 * for dialog messages.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
public enum MessageType {
    ERROR,
    WARNING,
    INFORMATION,
    QUESTION,
    NONE
}