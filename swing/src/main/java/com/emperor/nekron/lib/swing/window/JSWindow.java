package com.emperor.nekron.lib.swing.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Utility class for common Swing {@link Window} operations.
 *
 * <p>This class provides helper methods for managing Swing windows such as:</p>
 * <ul>
 *     <li>Centering windows on the screen</li>
 *     <li>Closing frames or dialogs using keyboard shortcuts</li>
 *     <li>Enabling window dragging using the mouse</li>
 *     <li>Basic window initialization</li>
 * </ul>
 *
 * <p>All methods are static and operate directly on {@link JSWindow},
 * {@link JFrame}, or {@link JDialog} instances.</p>
 *
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
public class JSWindow {

    /**
     * Centers the specified window on the screen.
     *
     * @param window the Swing window to center
     */
    public static void setCenterScreen(Window window) {
        window.setLocationRelativeTo(null);
    }

    /**
     * Registers a keyboard shortcut that closes the specified {@link JFrame}.
     *
     * <p>When the specified key is pressed, the frame will be disposed.</p>
     *
     * @param frame   the frame to close
     * @param keyCode the key code from {@link KeyEvent}
     * @see KeyEvent
     */
    public static void closeFrameByKey(JFrame frame, int keyCode) {
        frame.getRootPane()
                .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(keyCode, 0), "Cancel");

        frame.getRootPane()
                .getActionMap()
                .put("Cancel", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
    }

    /**
     * Registers a keyboard shortcut that closes the specified {@link JDialog}.
     *
     * <p>When the specified key is pressed, the dialog will be disposed.</p>
     *
     * @param dialog  the dialog to close
     * @param keyCode the key code from {@link KeyEvent}
     * @see KeyEvent
     */
    public static void closeDialogByKey(JDialog dialog, int keyCode) {
        dialog.getRootPane()
                .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(keyCode, 0), "Cancel");

        dialog.getRootPane()
                .getActionMap()
                .put("Cancel", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });
    }

    /**
     * Enables dragging of the window using the mouse.
     *
     * <p>This method allows the user to click and drag anywhere
     * on the window to move it across the screen.</p>
     *
     * <p>This is especially useful for undecorated windows.</p>
     *
     * @param window the window to enable dragging for
     */
    public static void moveWindowByMouse(Window window) {
        Point point = new Point();

        window.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });

        window.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = window.getLocation();
                window.setLocation(
                        p.x + e.getX() - point.x,
                        p.y + e.getY() - point.y
                );
            }
        });
    }

    /**
     * Initializes a window with basic utilities.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *     <li>Centers the window on the screen</li>
     *     <li>Enables mouse dragging</li>
     * </ul>
     *
     * @param window the window to initialize
     */
    public static void initialize(Window window) {
        setCenterScreen(window);
        moveWindowByMouse(window);
    }

    /**
     * Initializes a window with optional keyboard close support.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *     <li>Centers the window on the screen</li>
     *     <li>Enables mouse dragging</li>
     *     <li>Optionally binds a key to close the window</li>
     * </ul>
     *
     * @param window       the window to initialize
     * @param closeKeyCode key code used to close the window.
     *                     Use {@code -1} to disable the close key.
     * @see KeyEvent
     */
    public static void initialize(Window window, int closeKeyCode) {
        setCenterScreen(window);
        moveWindowByMouse(window);

        if (closeKeyCode != -1) {
            if (window instanceof JFrame) {
                closeFrameByKey((JFrame) window, closeKeyCode);
            } else if (window instanceof JDialog) {
                closeDialogByKey((JDialog) window, closeKeyCode);
            }
        }
    }
}