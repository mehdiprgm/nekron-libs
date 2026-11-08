package com.emperor.nekron.libs.terminal.natives.interfaces;

import com.emperor.nekron.libs.terminal.records.CursorCoordinate;
import com.emperor.nekron.libs.terminal.records.WindowSize;

/**
 * Defines terminal control operations for clearing the screen, moving the cursor,
 * reading keyboard input, and querying terminal state.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
public interface TerminalController {
    /**
     * Clears the console screen.
     */
    void clearScreen();

    /**
     * Moves the cursor to the specified 1-based console coordinates.
     *
     * @param x the horizontal coordinate, starting at 1
     * @param y the vertical coordinate, starting at 1
     * @throws IllegalArgumentException if either coordinate is less than 1
     * @throws IllegalStateException    if moving the cursor fails
     */
    void gotoxy(int x, int y);

    /**
     * Reads a single key from the console.
     *
     * @param echo whether the typed key should be echoed to the console
     * @return the key that was read
     */
    int readKey(boolean echo);

    /**
     * Returns the current cursor position in the console.
     *
     * @return the current cursor coordinates
     * @throws IllegalStateException if the console information cannot be read
     */
    CursorCoordinate getCursorCoordinate();

    /**
     * Returns the current console window size.
     *
     * @return the terminal window size
     * @throws IllegalStateException if the console information cannot be read
     */
    WindowSize getWindowSize();
}
