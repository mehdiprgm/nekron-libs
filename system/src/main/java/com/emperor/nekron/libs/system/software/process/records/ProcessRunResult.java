package com.emperor.nekron.libs.system.software.process.records;

import java.util.List;

/**
 * Represents the result of executing a process, including its exit status code and output lines.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public record ProcessRunResult(int resultCode, List<String> output) {
}