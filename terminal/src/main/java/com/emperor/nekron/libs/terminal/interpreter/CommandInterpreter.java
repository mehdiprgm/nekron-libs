package com.emperor.nekron.libs.terminal.interpreter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


/**
 * Parses and extracts arguments from a list of command-line tokens based on a target key.
 * Provides utilities for locating a key's associated value, removing matched entries,
 * and validating the size and content of the remaining arguments.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
@Data
@AllArgsConstructor
public class CommandInterpreter {
    private List<String> arguments;

    /**
     * Finds the value associated with the key, requiring a value and removing the key
     * and its value from the arguments list upon a match.
     *
     * @param key The key to search in the arguments.
     * @return the value following the key, an empty string if no value is required,
     * or null if the key was not found
     */
    public String findArgument(String key) {
        return findArgument(key, true, true);
    }

    /**
     * Finds the value associated with the key and removes the key and its value
     * from the arguments list upon a match.
     *
     * @param key             The key to search in the arguments.
     * @param isValueRequired whether a value following the key is expected
     * @return the value following the key, an empty string if no value is required,
     * or null if the key was not found
     */
    public String findArgument(String key, boolean isValueRequired) {
        return findArgument(key, isValueRequired, true);
    }

    /**
     * Finds the value associated with the key and optionally removes the matched entries
     * from the arguments list.
     *
     * @param key             The key to search in the arguments.
     * @param isValueRequired whether a value following the key is expected
     * @param removeKey       whether the key and its associated value should be removed
     *                        from the arguments list upon a match
     * @return the value following the key, an empty string if no value is required,
     * or null if the key was not found
     */
    public String findArgument(String key, boolean isValueRequired, boolean removeKey) {
        String value = null;

        for (int i = 0; i < arguments.size(); i++) {
            if (arguments.get(i).equals(key)) {
                if (isValueRequired) {
                    if (i + 1 < arguments.size()) {
                        value = arguments.get(i + 1);
                    }
                } else {
                    value = "";
                }

                if (removeKey) {
                    arguments.remove(i);

                    if (isValueRequired && i < arguments.size()) {
                        arguments.remove(i);
                    }
                }

                break;
            }
        }

        return value;
    }

    /**
     * Validates that the number of arguments does not exceed the specified maximum.
     * Prints an error message to standard output when the limit is exceeded.
     *
     * @param maxSize the maximum allowed number of arguments
     * @return true if the arguments size is within the limit, false otherwise
     */
    public boolean isArgumentsSizeValid(int maxSize) {
        if (arguments.size() > maxSize) {
            if (maxSize == 0) {
                System.out.println("error: command takes no arguments");
            } else {
                System.out.printf("error: command max arguments is %d.\n", maxSize);
            }

            return false;
        }

        return true;
    }

    /**
     * Validates that every argument starting with the given prefix is present in the
     * provided list of valid arguments.
     *
     * @param validArgs     the list of accepted argument values
     * @param argumentStart the prefix used to identify which arguments should be validated
     * @return true if all matching arguments are valid, false otherwise
     */
    public boolean isArgumentsValid(List<String> validArgs, String argumentStart) {
        for (String argument : arguments) {
            if (argument.startsWith(argumentStart)) {
                if (!validArgs.contains(argument)) {
                    return false;
                }
            }
        }

        return true;
    }
}