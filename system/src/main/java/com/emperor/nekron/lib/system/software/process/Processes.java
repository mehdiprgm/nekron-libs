package com.emperor.nekron.lib.system.software.process;

import com.emperor.nekron.lib.system.software.process.records.KillResult;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;

import java.io.IOException;
import java.util.List;

/**
 * Provides access to system process information and process control operations.
 * <p>
 * This class allows retrieving a single process or all running processes, and
 * forcefully terminating a process by its process ID.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public class Processes {

    /**
     * Retrieves the process with the given process ID.
     *
     * @param pid the process ID to look up
     * @return the matching process, or {@code null} if no process with the given ID exists
     */
    public static OSProcess get(int pid) {
        List<OSProcess> processes = new SystemInfo().getOperatingSystem().getProcesses();

        for (OSProcess process : processes) {
            if (process.getProcessID() == pid) {
                return process;
            }
        }

        return null;
    }

    /**
     * Retrieves all processes currently running on the system.
     *
     * @return a list of all running processes
     */
    public static List<OSProcess> getAll() {
        return new SystemInfo().getOperatingSystem().getProcesses();
    }

    /**
     * Forcefully terminates a system process identified by the given process ID.
     *
     * @param pid the process ID of the target process to terminate
     * @return the result containing termination output, exit code, and success status
     * @throws IOException          if an I/O error occurs during process execution
     * @throws InterruptedException if the execution thread is interrupted while waiting
     */
    public static KillResult kill(int pid) throws IOException, InterruptedException {
        Process process = new ProcessBuilder("taskkill.exe", "/PID", String.valueOf(pid), "/F")
                .redirectErrorStream(true).start();

        int exitCode = process.waitFor();
        return new KillResult(new String(process.getInputStream().readAllBytes()).trim(), exitCode, exitCode == 0);
    }
}