package com.emperor.nekron.libs.terminal.interpreter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses a raw command line string into a list of individual argument tokens.
 * Handles whitespace separation, double-quoted segments and backslash escaping.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class CommandParser {
    private String command;

    /**
     * Parses a raw input line into a list of arguments.
     *
     * <p>The parser splits the input by whitespace, supports double-quoted segments
     * as single arguments and allows escaping characters using a backslash. Quotes
     * themselves are not included in the resulting tokens.</p>
     *
     * @return a list of parsed argument tokens; never {@code null}
     */
    public List<String> parseArgs() {
        List<String> args = new ArrayList<>();
        if (command == null || command.isBlank()) {
            return args;
        }

        StringBuilder current = new StringBuilder();
        boolean inQuotes = false, escaping = false;

        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);

            if (escaping) {
                current.append(c);
                escaping = false;
                continue;
            }

            if (c == '\\') {
                escaping = true;
                continue;
            }

            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }

            if (Character.isWhitespace(c) && !inQuotes) {
                if (!current.isEmpty()) {
                    args.add(current.toString());
                    current.setLength(0);
                }

                continue;
            }

            current.append(c);
        }

        if (!current.isEmpty()) {
            args.add(current.toString());
        }

        return args;
    }
}