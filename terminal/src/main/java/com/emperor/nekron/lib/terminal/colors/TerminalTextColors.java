package com.emperor.nekron.lib.terminal.colors;

/**
 * Provides ANSI escape sequences for setting terminal text (foreground) colors.
 * <p>
 * Each constant represents a text color code that can be concatenated with
 * text to apply the corresponding color in the terminal. The available
 * variants include standard, bold, underlined, bright, and bold-bright
 * combinations for each base color.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public class TerminalTextColors {
    public static final String FR_BLACK = "\u001b[0;30m";
    public static final String FR_RED = "\u001b[0;31m";
    public static final String FR_GREEN = "\u001b[0;32m";
    public static final String FR_YELLOW = "\u001b[0;33m";
    public static final String FR_BLUE = "\u001b[0;34m";
    public static final String FR_PURPLE = "\u001b[0;35m";
    public static final String FR_CYAN = "\u001b[0;36m";
    public static final String FR_WHITE = "\u001b[0;37m";
    public static final String FR_BLACK_BOLD = "\u001b[1;30m";
    public static final String FR_RED_BOLD = "\u001b[1;31m";
    public static final String FR_GREEN_BOLD = "\u001b[1;32m";
    public static final String FR_YELLOW_BOLD = "\u001b[1;33m";
    public static final String FR_BLUE_BOLD = "\u001b[1;34m";
    public static final String FR_PURPLE_BOLD = "\u001b[1;35m";
    public static final String FR_CYAN_BOLD = "\u001b[1;36m";
    public static final String FR_WHITE_BOLD = "\u001b[1;37m";

    public static final String FR_BLACK_UNDERLINED = "\u001b[4;30m";
    public static final String FR_RED_UNDERLINED = "\u001b[4;31m";
    public static final String FR_GREEN_UNDERLINED = "\u001b[4;32m";
    public static final String FR_YELLOW_UNDERLINED = "\u001b[4;33m";
    public static final String FR_BLUE_UNDERLINED = "\u001b[4;34m";
    public static final String FR_PURPLE_UNDERLINED = "\u001b[4;35m";
    public static final String FR_CYAN_UNDERLINED = "\u001b[4;36m";
    public static final String FR_WHITE_UNDERLINED = "\u001b[4;37m";

    public static final String FR_BLACK_BRIGHT = "\u001b[0;90m";
    public static final String FR_RED_BRIGHT = "\u001b[0;91m";
    public static final String FR_GREEN_BRIGHT = "\u001b[0;92m";
    public static final String FR_YELLOW_BRIGHT = "\u001b[0;93m";
    public static final String FR_BLUE_BRIGHT = "\u001b[0;94m";
    public static final String FR_PURPLE_BRIGHT = "\u001b[0;95m";
    public static final String FR_CYAN_BRIGHT = "\u001b[0;96m";
    public static final String FR_WHITE_BRIGHT = "\u001b[0;97m";

    public static final String FR_BLACK_BOLD_BRIGHT = "\u001b[1;90m";
    public static final String FR_RED_BOLD_BRIGHT = "\u001b[1;91m";
    public static final String FR_GREEN_BOLD_BRIGHT = "\u001b[1;92m";
    public static final String FR_YELLOW_BOLD_BRIGHT = "\u001b[1;93m";
    public static final String FR_BLUE_BOLD_BRIGHT = "\u001b[1;94m";
    public static final String FR_PURPLE_BOLD_BRIGHT = "\u001b[1;95m";
    public static final String FR_CYAN_BOLD_BRIGHT = "\u001b[1;96m";
    public static final String FR_WHITE_BOLD_BRIGHT = "\u001b[1;97m";

    public static final String FR_RESET = "\u001b[0m";
}