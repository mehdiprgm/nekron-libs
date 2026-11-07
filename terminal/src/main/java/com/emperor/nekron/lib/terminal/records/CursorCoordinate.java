package com.emperor.nekron.lib.terminal.records;

/**
 * Represents a two-dimensional coordinate used to address a position on the terminal.
 * <p>
 * This record is typically used to specify the location of the cursor or any other
 * point within the terminal grid, where {@code x} refers to the horizontal axis
 * (column) and {@code y} refers to the vertical axis (row).
 *
 * @param x the horizontal position (column) on the terminal
 * @param y the vertical position (row) on the terminal
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public record CursorCoordinate(int x, int y) {
}
