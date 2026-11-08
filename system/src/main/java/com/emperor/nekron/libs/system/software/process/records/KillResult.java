package com.emperor.nekron.libs.system.software.process.records;

/**
 * Represents the result of a process termination operation.
 * <p>
 * This record encapsulates the outcome of killing or terminating a system process,
 * including the command output, the exit code from the termination command,
 * and a flag indicating whether the process was successfully terminated.
 * </p>
 *
 * @param output        The console output or error message returned from the termination command
 * @param exitCode      The exit code of the termination command (0 typically indicates success)
 * @param isTerminated  Flag indicating whether the process was successfully terminated
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
public record KillResult(String output, int exitCode, boolean isTerminated) {
}