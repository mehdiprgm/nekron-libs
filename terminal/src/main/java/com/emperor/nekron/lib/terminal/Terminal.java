package com.emperor.nekron.lib.terminal;

import com.emperor.nekron.lib.terminal.enums.MessageType;
import com.emperor.nekron.lib.terminal.natives.TerminalProvider;
import com.emperor.nekron.lib.terminal.records.CursorCoordinate;
import com.emperor.nekron.lib.terminal.records.WindowSize;

import java.util.List;
import java.util.Scanner;

/**
 * Provides utility methods for terminal-based input and output operations.
 * This class centralizes common console behavior such as printing, clearing,
 * cursor movement, key reading, prompts, and window information.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 12
 * @since 21
 */
public class Terminal {

    static {
        System.loadLibrary("zenterminal");
    }

    /**
     * Prints the given character repeatedly.
     *
     * @param ch       the character to print
     * @param length   the number of times to print the character
     * @param nextLine whether to print a new line after the characters
     */
    public static void printCharacters(char ch, int length, boolean nextLine) {
        for (int i = 0; i < length; i++) {
            System.out.print(ch);
        }

        if (nextLine) {
            System.out.println();
        }
    }

    /**
     * Prints the given objects without adding a line break.
     *
     * @param objects the objects to print
     */
    public static void print(Object... objects) {
        for (Object object : objects) {
            System.out.print(object);
        }
    }

    /**
     * Prints the given objects followed by a line break.
     *
     * @param objects the objects to print
     */
    public static void println(Object... objects) {
        for (Object object : objects) {
            System.out.print(object);
        }

        System.out.println();
    }

    /**
     * Prints formatted output using the specified format string.
     *
     * @param format the format string
     * @param args   the arguments referenced by the format specifiers
     */
    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    /**
     * Prints error messages to the standard error stream.
     *
     * <p>This method writes the specified objects to {@code System.err} sequentially
     * without any separators. After printing all objects, it outputs a newline
     * to the standard output stream.
     *
     * <p>This is useful for displaying error messages or debugging information
     * while maintaining a clean separation from normal program output.
     *
     * @param objects the objects to be printed to the error stream
     */
    public static void perror(Object... objects) {
        for (Object object : objects) {
            System.err.print(object);
        }
    }

    /**
     * Prints the contents of a list, optionally numbering items and placing
     * each item on a new line.
     *
     * @param list        the list to print
     * @param nextLine    whether to print each item on a new line
     * @param showNumbers whether to prefix each item with its position
     * @param <T>         the list element type
     */
    public static <T> void printList(List<T> list, boolean nextLine, boolean showNumbers) {
        for (int i = 0; i < list.size(); i++) {
            if (showNumbers) {
                System.out.printf("[%d] ", i + 1);
            }

            System.out.print(list.get(i));
            if (nextLine) {
                System.out.println();
            }
        }
    }

    /**
     * Prints a message using the color associated with the specified message type.
     *
     * @param type    the message type controlling the color
     * @param objects the objects to print
     */
    public static void printMessage(MessageType type, Object... objects) {
        for (Object object : objects) {
            System.out.printf("%s%s%s", type.color(), object, "\u001b[0m");
        }
    }

    /**
     * Clears the console screen.
     */
    public static void clearScreen() {
        TerminalProvider.getController().clearScreen();
    }

    /**
     * Moves the cursor to the specified 1-based console coordinates.
     *
     * @param x the horizontal coordinate, starting at 1
     * @param y the vertical coordinate, starting at 1
     * @throws IllegalArgumentException if either coordinate is less than 1
     * @throws IllegalStateException    if moving the cursor fails
     */
    public static void gotoxy(int x, int y) {
        TerminalProvider.getController().gotoxy(x, y);
    }

    /**
     * Prompts the user to press Enter using a default message.
     */
    public static void pressEnter() {
        pressEnter("Press enter to continue!...");
    }

    /**
     * Prompts the user to press Enter using the specified message.
     *
     * @param message the message to display before waiting for input
     */
    public static void pressEnter(String message) {
        System.out.print(message);
        new Scanner(System.in).nextLine();
    }

    /**
     * Prompts the user to press any key using a default message.
     *
     * @return the key pressed by the user
     */
    public static char pressAnyKey() {
        return pressAnyKey("Press any key to continue!... ");
    }

    /**
     * Prompts the user to press any key using the specified message.
     *
     * @param message the message to display before reading a key
     * @return the key pressed by the user
     */
    public static char pressAnyKey(String message) {
        char ch = ' ';

        System.out.print(message);
        ch = (char) TerminalProvider.getController().readKey(false);

        System.out.println();
        return ch;
    }

    /**
     * Reads a single key from the console.
     *
     * @param echo whether the typed key should be echoed to the console
     * @return the key that was read
     */
    public static int readKey(boolean echo) {
        return TerminalProvider.getController().readKey(echo);
    }

    /**
     * Clears the current console line.
     */
    public static void clearLine() {
        System.out.print("\r\u001B[2K");
        System.out.flush();
    }

    /**
     * Prompts the user for a yes/no confirmation.
     *
     * @param message the message to display
     * @param repeat  whether to repeat the prompt until a valid answer is entered
     * @return {@code true} if the answer is yes, otherwise {@code false}
     */
    public static boolean sure(String message, boolean repeat) {
        char ch;

        do {
            if (!message.isBlank()) {
                if (!message.toUpperCase().endsWith("[Y/N]: ")) {
                    System.out.printf("%s [Y/N]: ", message.trim());
                } else {
                    System.out.printf("%s: ", message.trim());
                }
            }

            ch = Character.toLowerCase((char) TerminalProvider.getController().readKey(true));
            System.out.println();

            if (ch == 'y') {
                return true;
            } else if (ch == 'n') {
                return false;
            }
        } while (repeat);

        return false;
    }

    /**
     * Prompts the user to select one option from the provided values.
     *
     * @param message the message to display
     * @param repeat  whether to repeat the prompt until a valid answer is entered
     * @param objects the available choices
     * @return the index of the selected option, or {@code -1} if no match is found
     */
    public static int ask(String message, boolean repeat, Object... objects) {
        StringBuilder sb = new StringBuilder("[");
        String answer;

        for (int i = 0; i < objects.length; i++) {
            sb.append(objects[i]);

            if (i + 1 < objects.length) {
                sb.append(", ");
            }
        }

        sb.append("]: ");

        do {
            System.out.printf("%s %s", message, sb);
            answer = new Scanner(System.in).nextLine();

            for (int i = 0; i < objects.length; i++) {
                if (answer.equals(objects[i])) {
                    return i;
                }
            }
        } while (repeat);
        return -1;
    }

    /**
     * Returns the current cursor position in the console.
     *
     * @return the current cursor coordinates
     * @throws IllegalStateException if the console information cannot be read
     */
    public static CursorCoordinate getCursorCoordinate() {
        return TerminalProvider.getController().getCursorCoordinate();
    }

    /**
     * Returns the current console window size.
     *
     * @return the terminal window size
     * @throws IllegalStateException if the console information cannot be read
     */
    public static WindowSize getWindowSize() {
        return TerminalProvider.getController().getWindowSize();
    }
}
