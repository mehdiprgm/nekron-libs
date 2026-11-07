package com.emperor.nekron.lib.swing.dialog.message;

import com.emperor.nekron.lib.swing.dialog.message.enums.MessageResult;
import com.emperor.nekron.lib.swing.dialog.message.enums.MessageType;

import javax.swing.*;
import java.awt.*;

/**
 * Provides a high-level utility wrapper for displaying message dialogs
 * and confirmation dialogs using the Swing {@link JOptionPane} API.
 *
 * <p>
 * This class centralizes common dialog operations used in desktop
 * applications such as:
 * </p>
 *
 * <ul>
 * <li>Displaying informational, warning, error, or question messages</li>
 * <li>Showing confirmation dialogs with Yes/No options</li>
 * <li>Showing confirmation dialogs with Yes/No/Cancel options</li>
 * <li>Customizing dialog appearance globally through {@link UIManager}</li>
 * </ul>
 *
 * <p>
 * The class internally translates {@link MessageType} values into the
 * corresponding {@link JOptionPane} message constants and maps user
 * responses into {@link MessageResult} values for consistent handling
 * throughout an application.
 * </p>
 *
 * <p>
 * All methods in this class are static, making it suitable as a
 * reusable utility in libraries or application frameworks without
 * requiring instantiation.
 * </p>
 *
 * <p>
 * In addition, the {@code setup(...)} methods allow configuring
 * global dialog appearance such as colors and fonts before dialogs
 * are displayed.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
public class MessageBox {

    private static int convertMessageType(MessageType messageType) {
        return switch (messageType) {
            case ERROR -> JOptionPane.ERROR_MESSAGE;
            case WARNING -> JOptionPane.WARNING_MESSAGE;
            case INFORMATION -> JOptionPane.INFORMATION_MESSAGE;
            case QUESTION -> JOptionPane.QUESTION_MESSAGE;
            case NONE -> JOptionPane.PLAIN_MESSAGE;
        };
    }

    /**
     * Initializes dialog appearance using default Swing settings.
     *
     * <p>
     * This method resets dialog customization by delegating to the
     * main setup method with {@code null} values for all parameters.
     * </p>
     */
    public static void setup() {
        setup(null, null, null, null);
    }

    /**
     * Configures the background color used by option pane dialogs.
     *
     * @param background background color applied to dialog components
     */
    public static void setup(Color background) {
        setup(background, null, null, null);
    }

    /**
     * Configures the background and foreground colors for dialogs.
     *
     * <p>
     * The foreground color affects message text rendering inside
     * the dialog.
     * </p>
     *
     * @param background background color of the dialog
     * @param foreground foreground color of message text
     */
    public static void setup(Color background, Color foreground) {
        setup(background, foreground, null, null);
    }

    /**
     * Configures dialog colors and button font.
     *
     * @param background background color of the dialog
     * @param foreground message text color
     * @param buttonFont font applied to dialog buttons
     */
    public static void setup(Color background, Color foreground, Font buttonFont) {
        setup(background, foreground, buttonFont, null);
    }

    /**
     * Fully configures dialog appearance including colors and fonts.
     *
     * <p>
     * Internally this method updates Swing {@link UIManager} properties
     * affecting {@link JOptionPane} components globally for the current
     * application runtime.
     * </p>
     *
     * <p>
     * Any parameter provided as {@code null} will leave the corresponding
     * UI property unchanged.
     * </p>
     *
     * @param background  background color applied to option pane dialogs
     * @param foreground  message text foreground color
     * @param buttonFont  font applied to dialog buttons
     * @param messageFont font applied to dialog message text
     */
    public static void setup(Color background, Color foreground, Font buttonFont, Font messageFont) {
        if (background != null) {
            UIManager.put("OptionPane.background", background);
        }

        if (foreground != null) {
            UIManager.put("OptionPane.messageForeground", foreground);
        }

        if (buttonFont != null) {
            UIManager.put("OptionPane.buttonFont", buttonFont);
        }

        if (messageFont != null) {
            UIManager.put("OptionPane.messageFont", messageFont);
        }

        UIManager.put("OptionPane.yesButtonText", "Yes");
        UIManager.put("OptionPane.noButtonText", "No");

        UIManager.put("OptionPane.cancelButtonText", "Cancel");
    }

    /**
     * Displays a simple message dialog with default parameters.
     *
     * <p>
     * The dialog will have no parent component, an empty title,
     * and a plain message type.
     * </p>
     *
     * @return {@link MessageResult#NONE} since no user choice is returned
     */
    public static MessageResult show() {
        return show(null, "", "", MessageType.NONE, null);
    }

    /**
     * Displays a message dialog using the specified parent component.
     *
     * @param parent the parent component used for dialog positioning
     * @return {@link MessageResult#NONE} since no user choice is returned
     */
    public static MessageResult show(Component parent) {
        return show(parent, "", "", MessageType.NONE, null);
    }

    /**
     * Displays a message dialog with a custom title.
     *
     * @param parent parent component used for dialog positioning
     * @param title  dialog window title
     * @return {@link MessageResult#NONE}
     */
    public static MessageResult show(Component parent, String title) {
        return show(parent, "", title, MessageType.NONE, null);
    }

    /**
     * Displays a message dialog with a custom title and message.
     *
     * @param parent  parent component used for dialog positioning
     * @param title   dialog title
     * @param message message text displayed to the user
     * @return {@link MessageResult#NONE}
     */
    public static MessageResult show(Component parent, String title, String message) {
        return show(parent, message, title, MessageType.NONE, null);
    }

    /**
     * Displays a message dialog with a specified message type.
     *
     * @param parent      parent component used for dialog positioning
     * @param title       dialog title
     * @param message     message text
     * @param messageType type of message icon and semantic meaning
     * @return {@link MessageResult#NONE}
     */
    public static MessageResult show(Component parent, String title, String message, MessageType messageType) {
        return show(parent, message, title, messageType, null);
    }

    /**
     * Displays a fully configurable message dialog.
     *
     * <p>
     * This method internally delegates to
     * {@link JOptionPane#showMessageDialog(Component, Object, String, int, Icon)}.
     * </p>
     *
     * @param parent      parent component used for dialog positioning
     * @param title       dialog title
     * @param message     message text displayed to the user
     * @param messageType semantic type of the message
     * @param icon        optional custom icon displayed in the dialog
     * @return {@link MessageResult#NONE} because message dialogs do not return a choice
     */
    public static MessageResult show(Component parent, String title, String message, MessageType messageType, Icon icon) {
        JOptionPane.showMessageDialog(parent, title, message, convertMessageType(messageType), icon);
        return MessageResult.NONE;
    }

    /**
     * Displays a confirmation dialog with default parameters.
     *
     * @return the user's selected {@link MessageResult}
     */
    public static MessageResult showConfirm() {
        return showConfirm(null, "", "", MessageType.NONE, null);
    }

    /**
     * Displays a confirmation dialog with a parent component.
     *
     * @param parent parent component used for dialog positioning
     * @return the user's selected {@link MessageResult}
     */
    public static MessageResult showConfirm(Component parent) {
        return showConfirm(parent, "", "", MessageType.NONE, null);
    }

    /**
     * Displays a confirmation dialog with a custom title.
     *
     * @param parent parent component used for dialog positioning
     * @param title  dialog title
     * @return the user's selected {@link MessageResult}
     */
    public static MessageResult showConfirm(Component parent, String title) {
        return showConfirm(parent, title, "", MessageType.NONE, null);
    }

    /**
     * Displays a confirmation dialog with a custom title and message.
     *
     * @param parent  parent component used for dialog positioning
     * @param title   dialog title
     * @param message message displayed to the user
     * @return the user's selected {@link MessageResult}
     */
    public static MessageResult showConfirm(Component parent, String title, String message) {
        return showConfirm(parent, title, message, MessageType.NONE, null);
    }

    /**
     * Displays a confirmation dialog with a specific message type.
     *
     * @param parent      parent component used for dialog positioning
     * @param title       dialog title
     * @param message     message displayed to the user
     * @param messageType message semantic type
     * @return the user's selected {@link MessageResult}
     */
    public static MessageResult showConfirm(Component parent, String title, String message, MessageType messageType) {
        return showConfirm(parent, title, message, messageType, null);
    }

    /**
     * Displays a fully configurable confirmation dialog with
     * Yes and No options.
     *
     * <p>
     * The returned {@link MessageResult} represents the user's
     * selected action.
     * </p>
     *
     * @param parent      parent component used for dialog positioning
     * @param title       dialog title
     * @param message     confirmation message text
     * @param messageType semantic message type
     * @param icon        optional custom icon
     * @return {@link MessageResult#YES}, {@link MessageResult#NO},
     * or {@link MessageResult#NONE} if closed without selection
     */
    public static MessageResult showConfirm(Component parent, String title, String message, MessageType messageType, Icon icon) {
        return switch (JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION, convertMessageType(messageType), icon)) {
            case JOptionPane.NO_OPTION -> MessageResult.NO;
            case JOptionPane.YES_OPTION -> MessageResult.YES;
            default -> MessageResult.NONE;
        };
    }

    /**
     * Displays a confirmation dialog with Yes, No, and Cancel options
     * using default parameters.
     *
     * @return the user's selected {@link MessageResult}
     */
    public static MessageResult showConfirmCancel() {
        return showConfirmCancel(null, "", "", MessageType.NONE, null);
    }

    /**
     * Displays a confirmation dialog with Yes, No, and Cancel options
     * using the specified parent component.
     *
     * @param parent parent component used for dialog positioning
     * @return the user's selected {@link MessageResult}
     */
    public static MessageResult showConfirmCancel(Component parent) {
        return showConfirmCancel(parent, "", "", MessageType.NONE, null);
    }

    /**
     * Displays a confirmation dialog with Yes, No, and Cancel options
     * and a custom title.
     *
     * @param parent parent component used for dialog positioning
     * @param title  dialog title
     * @return the user's selected {@link MessageResult}
     */
    public static MessageResult showConfirmCancel(Component parent, String title) {
        return showConfirmCancel(parent, title, "", MessageType.NONE, null);
    }

    /**
     * Displays a confirmation dialog with Yes, No, and Cancel options
     * including a custom message.
     *
     * @param parent  parent component used for dialog positioning
     * @param title   dialog title
     * @param message confirmation message text
     * @return the user's selected {@link MessageResult}
     */
    public static MessageResult showConfirmCancel(Component parent, String title, String message) {
        return showConfirmCancel(parent, title, message, MessageType.NONE, null);
    }

    /**
     * Displays a confirmation dialog with Yes, No, and Cancel options
     * and a specific message type.
     *
     * @param parent      parent component used for dialog positioning
     * @param title       dialog title
     * @param message     confirmation message text
     * @param messageType semantic message type
     * @return the user's selected {@link MessageResult}
     */
    public static MessageResult showConfirmCancel(Component parent, String title, String message, MessageType messageType) {
        return showConfirmCancel(parent, title, message, messageType, null);
    }

    /**
     * Displays a fully configurable confirmation dialog with
     * Yes, No, and Cancel options.
     *
     * <p>
     * The returned {@link MessageResult} represents the user's
     * selected option.
     * </p>
     *
     * @param parent      parent component used for dialog positioning
     * @param title       dialog title
     * @param message     confirmation message text
     * @param messageType semantic message type
     * @param icon        optional custom icon displayed in the dialog
     * @return {@link MessageResult#YES}, {@link MessageResult#NO},
     * {@link MessageResult#CANCEL}, or {@link MessageResult#NONE}
     * if the dialog is closed without selection
     */
    public static MessageResult showConfirmCancel(Component parent, String title, String message, MessageType messageType, Icon icon) {
        return switch (JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_CANCEL_OPTION, convertMessageType(messageType), icon)) {
            case JOptionPane.NO_OPTION -> MessageResult.NO;
            case JOptionPane.YES_OPTION -> MessageResult.YES;
            case JOptionPane.CANCEL_OPTION -> MessageResult.CANCEL;
            default -> MessageResult.NONE;
        };
    }
}