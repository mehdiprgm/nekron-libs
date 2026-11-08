package com.emperor.nekron.libs.swing.text.interfaces;

import org.apache.commons.lang3.StringUtils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A {@link KeyListener} implementation that restricts keyboard input by
 * preventing numeric characters from being entered.
 *
 * <p>
 * This listener is typically used with Swing text input components such as
 * {@code JTextField}, {@code JTextArea}, or similar components where the
 * application must enforce alphabetic or non-numeric text input.
 * </p>
 *
 * <p>
 * The validation logic is executed during the {@link #keyTyped(KeyEvent)}
 * event. Each typed character is inspected using
 * {@link StringUtils#isNumeric(CharSequence)} from the Apache Commons Lang
 * library. If the typed character represents a numeric value, the event
 * is consumed, preventing the character from being inserted into the
 * associated text component.
 * </p>
 *
 * <p>
 * This mechanism provides immediate input filtering at the UI level,
 * ensuring that numeric characters cannot be entered by the user.
 * </p>
 *
 * <p>
 * The {@link #keyPressed(KeyEvent)} and {@link #keyReleased(KeyEvent)}
 * methods are intentionally left empty because the validation logic
 * is exclusively handled during the {@code keyTyped} event.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 2.0
 */
public class TextListener implements KeyListener {

    /**
     * Invoked when a key has been typed.
     *
     * <p>
     * This method checks whether the typed character is numeric. If the
     * character is identified as numeric according to
     * {@link StringUtils#isNumeric(CharSequence)}, the event is consumed,
     * preventing the character from being inserted into the component.
     * </p>
     *
     * @param e the {@link KeyEvent} generated when a key is typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (StringUtils.isNumeric(String.valueOf(e.getKeyChar()))) {
            e.consume();
        }
    }

    /**
     * Invoked when a key has been pressed.
     *
     * <p>
     * This implementation intentionally performs no operation since
     * input filtering is handled entirely in {@link #keyTyped(KeyEvent)}.
     * </p>
     *
     * @param e the {@link KeyEvent} generated when a key is pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Invoked when a key has been released.
     *
     * <p>
     * This implementation intentionally performs no operation since
     * input filtering is handled entirely in {@link #keyTyped(KeyEvent)}.
     * </p>
     *
     * @param e the {@link KeyEvent} generated when a key is released
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
}