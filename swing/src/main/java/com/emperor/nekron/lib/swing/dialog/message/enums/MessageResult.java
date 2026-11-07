package com.emperor.nekron.lib.swing.dialog.message.enums;

/**
 * Represents the result of a user interaction with a message or confirmation dialog.
 *
 * <p>
 * This enumeration is typically returned by dialog utility methods such as those
 * in {@code MessageBox} to indicate the option selected by the user.
 * It provides a consistent abstraction over the raw integer constants used
 * internally by Swing's {@link javax.swing.JOptionPane}.
 * </p>
 *
 * <p>
 * The values correspond to the most common dialog outcomes such as confirmation,
 * rejection, cancellation, or no explicit user action.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
public enum MessageResult {
    NONE,
    YES,
    NO,
    CANCEL
}