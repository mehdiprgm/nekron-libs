package com.emperor.nekron.libs.system.software.process;

import com.emperor.nekron.libs.system.software.process.records.ProcessRunResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class providing helper methods for executing operating system
 * commands and retrieving process-related information.
 *
 * <p>This class wraps {@link ProcessBuilder} to simplify common tasks such as:</p>
 * <ul>
 *      <li>Executing shell commands</li>
 *      <li>Capturing command output</li>
 *      <li>Listing running system processes</li>
 * </ul>
 *
 * <p>The methods in this class are designed primarily for Unix-like systems
 * where commands such as {@code ps} are available.</p>
 *
 * <p>All methods are static and the class is intended to be used as a
 * lightweight system interaction utility.</p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 6.0
 * @since 21
 */
public class ProcessUtils {

    /**
     * Executes the given command and waits for it to finish.
     * <p>
     * Standard output and standard error are captured together into a single
     * list of lines. The method blocks until the process terminates and then
     * returns its exit code along with the captured output.
     *
     * @param command the command and its arguments to execute
     * @return a result containing the process exit code and the captured output lines
     * @throws IOException          if the process cannot be started or its output cannot be read
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    public static ProcessRunResult runCommand(String... command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        processBuilder.redirectErrorStream(true);

        List<String> output = new ArrayList<>();

        Process runningProcess = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(runningProcess.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.add(line);
        }

        int resultCode = runningProcess.waitFor();
        return new ProcessRunResult(resultCode, output);
    }

}