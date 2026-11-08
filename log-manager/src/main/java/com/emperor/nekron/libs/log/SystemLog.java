package com.emperor.nekron.libs.log;

import com.emperor.nekron.libs.log.emums.SystemLogModule;
import com.emperor.nekron.libs.log.emums.SystemLogType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Optional;

/**
 * Represents a structured log entry containing all relevant information about a log event.
 * <p>
 * This class captures the complete context of a log message including the message content,
 * source location (file name, function name, line number), module and type classification,
 * and timestamp. The source location is automatically captured from the call stack when
 * using the primary constructor.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 3.0
 * @since 21
 */
@Data
@AllArgsConstructor
public class SystemLog {
    private String message;
    private String fileName;
    private String functionName;

    private SystemLogModule systemLogModule;
    private SystemLogType systemLogType;

    private Instant timestamp;
    private Integer lineNumber;

    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * Constructs a SystemLog entry with automatic capture of the caller's source location.
     * <p>
     * This constructor automatically captures the file name, function name, and line number
     * of the calling code using StackWalker. The timestamp is set to the current time.
     * If the caller information cannot be determined, default values are used.
     *
     * @param message         the log message content
     * @param systemLogModule the module categorizing this log entry
     * @param systemLogType   the type or severity level of this log entry
     */
    public SystemLog(String message, SystemLogModule systemLogModule, SystemLogType systemLogType) {
        this.message = message;
        this.systemLogModule = systemLogModule;
        this.systemLogType = systemLogType;

        this.timestamp = Instant.now();

        Optional<StackWalker.StackFrame> callerFrame = STACK_WALKER.walk(frames -> frames
                .dropWhile(frame -> {
                    String className = frame.getClassName();

                    return className.equals(SystemLog.class.getName()) ||
                            (className.equals("com.emperor.nekron.file.log.environment.Logs") &&
                                    (frame.getMethodName().equals("success") || frame.getMethodName().equals("warning") ||
                                            frame.getMethodName().equals("error") || frame.getMethodName().equals("information")));
                })
                .findFirst()
        );

        if (callerFrame.isPresent()) {
            StackWalker.StackFrame frame = callerFrame.get();

            this.fileName = frame.getFileName();
            this.functionName = frame.getMethodName();
            this.lineNumber = frame.getLineNumber();
        } else {
            this.fileName = "unknown";
            this.functionName = "unknown";
            this.lineNumber = 0;
        }
    }

    /**
     * Returns a formatted string representation of this log entry.
     * <p>
     * The format follows the pattern:
     * [MODULE] [TYPE] [functionName() - fileName:lineNumber] [timestamp] message
     *
     * @return a formatted string containing all log entry details
     */
    @Override
    public String toString() {
        return "[%s] [%s] [%s() - %s:%d] [%s] %s ".formatted(
                systemLogModule,
                systemLogType,
                functionName,
                fileName,
                lineNumber,
                timestamp,
                message
        );
    }
}