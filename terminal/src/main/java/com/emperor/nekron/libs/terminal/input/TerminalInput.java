package com.emperor.nekron.libs.terminal.input;

import com.emperor.nekron.libs.terminal.records.CursorCoordinate;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.emperor.nekron.libs.terminal.Terminal.*;

/**
 * Handles terminal input operations including masked password entry,
 * echo customization, text length limitations, and navigation via special keys
 * across ANSI-compliant terminals and Linux consoles.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 4.0
 * @since 21
 */
@Data
@AllArgsConstructor
public class TerminalInput {
    private boolean password;
    private boolean echo;
    private int maxLength;

    public static final int KEY_ENTER = 13;
    public static final int KEY_TAB = 9;
    public static final int KEY_BACKSPACE = 8;
    public static final int KEY_ESCAPE = 27;

    // ANSI Constants
    public static final String ANSI_RIGHT = "\033[C";
    public static final String ANSI_LEFT = "\033[D";
    public static final String ANSI_HOME = "\033[H";
    public static final String ANSI_END = "\033[F";
    public static final String ANSI_DELETE = "\033[3~";

    // Windows Special Key Identifiers
    public static final int KEY_SPECIAL_PREFIX = 224; // 0xE0
    public static final int KEY_SPECIAL_PREFIX_ALT = 0;  // 0x00

    public static final int WIN_KEY_RIGHT = 77;
    public static final int WIN_KEY_LEFT = 75;
    public static final int WIN_KEY_HOME = 71;
    public static final int WIN_KEY_END = 79;
    public static final int WIN_KEY_DELETE = 83;

    private int handleAnsiSpecialKeys(String sequence, StringBuilder result, int cursor, String message, int startX, int startY) {
        switch (sequence) {
            case ANSI_RIGHT -> {
                if (cursor < result.length()) {
                    cursor++;

                    if (echo) {
                        System.out.print(ANSI_RIGHT);
                    }
                }
            }

            case ANSI_LEFT -> {
                if (cursor > 0) {
                    cursor--;

                    if (echo) {
                        System.out.print(ANSI_LEFT);
                    }
                }
            }

            case ANSI_HOME -> {
                if (cursor > 0) {
                    cursor = 0;

                    if (echo) {
                        redrawLine(message, result, cursor, startX, startY);
                    }
                }
            }

            case ANSI_END -> {
                if (cursor < result.length()) {
                    cursor = result.length();

                    if (echo) {
                        redrawLine(message, result, cursor, startX, startY);
                    }
                }
            }

            case ANSI_DELETE -> {
                if (cursor < result.length()) {
                    result.deleteCharAt(cursor);

                    if (echo) {
                        redrawLine(message, result, cursor, startX, startY);
                    }
                }
            }
        }

        return cursor;
    }

    private int handleWindowsSpecialKeys(int keyCode, StringBuilder result, int cursor, String message, int startX, int startY) {
        switch (keyCode) {
            case WIN_KEY_RIGHT -> {
                if (cursor < result.length()) {
                    cursor++;

                    if (echo) {
                        System.out.print(ANSI_RIGHT);
                    }
                }
            }

            case WIN_KEY_LEFT -> {
                if (cursor > 0) {
                    cursor--;

                    if (echo) {
                        System.out.print(ANSI_LEFT);
                    }
                }
            }

            case WIN_KEY_HOME -> {
                if (cursor > 0) {
                    cursor = 0;

                    if (echo) {
                        redrawLine(message, result, cursor, startX, startY);
                    }
                }
            }

            case WIN_KEY_END -> {
                if (cursor < result.length()) {
                    cursor = result.length();

                    if (echo) {
                        redrawLine(message, result, cursor, startX, startY);
                    }
                }
            }

            case WIN_KEY_DELETE -> {
                if (cursor < result.length()) {
                    result.deleteCharAt(cursor);

                    if (echo) {
                        redrawLine(message, result, cursor, startX, startY);
                    }
                }
            }
        }

        return cursor;
    }

    private void redrawLine(String message, StringBuilder text, int cursor, int startX, int startY) {
        gotoxy(startX, startY);
        System.out.print("\033[0K");

        System.out.print(message);

        if (password) {
            for (int i = 0; i < text.length(); i++) {
                System.out.print("*");
            }
        } else {
            System.out.print(text);
        }

        int cursorPos = startX + message.length() + cursor;
        gotoxy(cursorPos, startY);
    }

    /**
     * Creates a new terminal input handler with default configurations:
     * password mode disabled, echo enabled, and unlimited input length.
     */
    public TerminalInput() {
        this(false, true, Integer.MAX_VALUE);
    }

    /**
     * Creates a new terminal input handler with a specified password mode,
     * default echoing enabled, and unlimited input length.
     *
     * @param password {@code true} to mask typed characters with asterisks; {@code false} for normal text
     */
    public TerminalInput(boolean password) {
        this(password, true, Integer.MAX_VALUE);
    }

    /**
     * Creates a new terminal input handler with specified password and echo settings,
     * and unlimited input length.
     *
     * @param password {@code true} to mask typed input; {@code false} for plain text
     * @param echo     {@code true} to print typed characters to the console; {@code false} to hide output
     */
    public TerminalInput(boolean password, boolean echo) {
        this(password, echo, Integer.MAX_VALUE);
    }

    /**
     * Reads a line of text from the terminal with no prompt message and without trimming.
     *
     * @return the raw input string captured from the terminal
     */
    public String read() {
        return read("", false);
    }

    /**
     * Reads a line of text from the terminal after displaying a prompt message, without trimming.
     *
     * @param message the prompt message printed before input collection
     * @return the raw input string captured from the terminal
     */
    public String read(String message) {
        return read(message, false);
    }

    /**
     * Reads a line of text from the terminal after displaying a prompt message,
     * with an option to trim leading and trailing whitespace.
     *
     * @param message  the prompt message displayed to the user
     * @param trimText {@code true} to remove leading/trailing whitespaces from the result; {@code false} to keep raw input
     * @return the captured input string, trimmed if specified
     */
    public String read(String message, boolean trimText) {
        StringBuilder result = new StringBuilder();

        System.out.print(message);

        CursorCoordinate coordinate = getCursorCoordinate();
        int startX = coordinate.x() - message.length(), startY = coordinate.y();
        int cursor = 0;

        while (true) {
            int key = readKey(false);

            if (key == KEY_ENTER) {
                break;
            } else if (key == KEY_TAB) {
                int spacesToInsert = Math.min(4, maxLength - result.length());

                for (int i = 0; i < spacesToInsert; i++) {
                    result.insert(cursor, ' ');
                    cursor++;
                }

                if (echo && spacesToInsert > 0) {
                    redrawLine(message, result, cursor, startX, startY);
                }
            } else if (key == KEY_BACKSPACE) {
                if (cursor > 0) {
                    result.deleteCharAt(cursor - 1);
                    cursor--;

                    if (echo) {
                        redrawLine(message, result, cursor, startX, startY);
                    }
                }
            } else if (key == KEY_ESCAPE) {
                // Read ANSI escape sequence (Linux / Mac / Windows Terminal)
                StringBuilder seq = new StringBuilder();
                seq.append((char) key);

                int next = readKey(false);
                if (next == '[') {
                    seq.append((char) next);
                    while (true) {
                        int ch = readKey(false);
                        seq.append((char) ch);

                        if (ch >= 64 && ch <= 126) { // End of CSI sequence
                            break;
                        }
                    }

                    cursor = handleAnsiSpecialKeys(seq.toString(), result, cursor, message, startX, startY);
                }
            } else if (key == KEY_SPECIAL_PREFIX || key == KEY_SPECIAL_PREFIX_ALT) {
                // Handling Native Windows Console Special Keys (224 or 0 prefix)
                int keyCode = readKey(false);
                cursor = handleWindowsSpecialKeys(keyCode, result, cursor, message, startX, startY);
            } else {
                if (result.length() >= maxLength) {
                    continue;
                }

                char ch = (char) key;

                // Only printable characters
                if (ch >= 32 && ch < 127) {
                    result.insert(cursor, ch);
                    cursor++;

                    if (echo) {
                        if (cursor == result.length()) {
                            if (password) {
                                System.out.print("*");
                            } else {
                                System.out.print(ch);
                            }
                        } else {
                            redrawLine(message, result, cursor, startX, startY);
                        }
                    }
                }
            }
        }

        System.out.println();

        String output = result.toString();
        return trimText ? output.trim() : output;
    }
}