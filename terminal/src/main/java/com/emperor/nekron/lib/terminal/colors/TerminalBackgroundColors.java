package com.emperor.nekron.lib.terminal.colors;

/**
 * Provides ANSI escape sequences for setting terminal background colors.
 * <p>
 * Each constant represents a background color code that can be concatenated
 * with text to apply the corresponding color in the terminal. Both standard
 * and bright variants are available.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public class TerminalBackgroundColors {

    public static final String BG_BLACK = "\u001b[40m";
    public static final String BG_RED = "\u001b[41m";
    public static final String BG_GREEN = "\u001b[42m";
    public static final String BG_YELLOW = "\u001b[43m";
    public static final String BG_BLUE = "\u001b[44m";
    public static final String BG_PURPLE = "\u001b[45m";
    public static final String BG_CYAN = "\u001b[46m";
    public static final String BG_WHITE = "\u001b[47m";
    public static final String BG_BLACK_BRIGHT = "\u001b[0;100m";
    public static final String BG_RED_BRIGHT = "\u001b[0;101m";
    public static final String BG_GREEN_BRIGHT = "\u001b[0;102m";
    public static final String BG_YELLOW_BRIGHT = "\u001b[0;103m";
    public static final String BG_BLUE_BRIGHT = "\u001b[0;104m";
    public static final String BG_PURPLE_BRIGHT = "\u001b[0;105m";
    public static final String BG_CYAN_BRIGHT = "\u001b[0;106m";
    public static final String BG_WHITE_BRIGHT = "\u001b[0;107m";

    public static final String FR_RESET = "\u001b[0m";
}