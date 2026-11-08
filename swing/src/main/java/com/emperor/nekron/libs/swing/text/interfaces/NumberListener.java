package com.emperor.nekron.libs.swing.text.interfaces;

import org.apache.commons.lang3.StringUtils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A KeyListener implementation that restricts user input to numeric characters only.
 *
 * <p>
 * This listener is designed to be attached to Swing text components such as
 * {@code JTextField}, {@code JTextArea}, or any component derived from
 * {@code JTextComponent}. It validates the character entered during the
 * {@code keyTyped} event and prevents non-numeric characters from being
 * inserted into the component.
 * </p>
 *
 * <p>
 * The validation process relies on {@code StringUtils.isNumeric} from the
 * Apache Commons Lang library. If the typed character does not represent
 * a numeric digit, the event is consumed, preventing the character from
 * appearing in the target component.
 * </p>
 *
 * <p>
 * Typical usage scenario includes numeric-only input fields such as:
 * </p>
 *
 * <ul>
 * <li>Age fields</li>
 * <li>Quantity inputs</li>
 * <li>Numeric identifiers</li>
 * <li>Configuration parameters</li>
 * </ul>
 *
 * <p>
 * The {@code keyPressed} and {@code keyReleased} methods are implemented to
 * satisfy the {@code KeyListener} interface but intentionally left empty
 * because the validation logic only requires interception during the
 * {@code keyTyped} event.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
public class NumberListener implements KeyListener {

    /**
     * Handles the key typed event and validates that the entered character
     * is numeric.
     *
     * <p>
     * If the typed character is not recognized as a numeric digit according
     * to {@code StringUtils.isNumeric}, the event will be consumed. Consuming
     * the event prevents the character from being inserted into the target
     * text component.
     * </p>
     *
     * <p>
     * This method acts as the primary validation point for numeric-only input
     * fields.
     * </p>
     *
     * @param e the {@code KeyEvent} generated when a key is typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (!StringUtils.isNumeric(String.valueOf(e.getKeyChar()))) {
            e.consume();
        }

    }

    /**
     * Invoked when a key has been pressed.
     *
     * <p>
     * This implementation performs no action. The method exists only to
     * fulfill the {@code KeyListener} interface contract.
     * </p>
     *
     * @param e the {@code KeyEvent} generated when a key is pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Invoked when a key has been released.
     *
     * <p>
     * This implementation performs no action. The method exists only to
     * fulfill the {@code KeyListener} interface contract.
     * </p>
     *
     * @param e the {@code KeyEvent} generated when a key is released
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
}