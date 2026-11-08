package com.emperor.nekron.libs.system.software;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides information about the Java runtime environment.
 * <p>
 * This class offers convenient access to various Java Virtual Machine (JVM)
 * and runtime configuration details. It exposes version information, VM name,
 * class file version, and runtime paths such as library and class paths.
 * Additionally, it provides access to the input arguments passed to the JVM.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 2.0
 */
public class Java {

    /**
     * Retrieves the version of the Java runtime.
     * <p>
     * This returns the runtime version string which typically includes
     * the major, minor, and update numbers (e.g., "1.8.0_431" or "17.0.12+7").
     * </p>
     *
     * @return The Java runtime version string
     */
    public static String getVersion() {
        return System.getProperty("java.runtime.version");
    }

    /**
     * Retrieves the name of the Java Virtual Machine (JVM).
     * <p>
     * This returns the specific VM implementation name
     * (e.g., "Java HotSpot(TM) 64-Bit Server VM").
     * </p>
     *
     * @return The Java VM name string
     */
    public static String getName() {
        return System.getProperty("java.vm.name");
    }

    /**
     * Retrieves the class file version that the JVM supports.
     * <p>
     * This returns the class file format version number,
     * which indicates the maximum Java version the JVM can execute
     * (e.g., "52.0" for Java 8, "61.0" for Java 17).
     * </p>
     *
     * @return The class file version string
     */
    public static String getClassVersion() {
        return System.getProperty("java.class.version");
    }

    /**
     * Retrieves the library paths used by the JVM.
     * <p>
     * The library paths specify directories where the JVM searches for
     * native libraries (DLLs on Windows, SO files on Linux).
     * The paths are split based on the system path separator (semicolon on Windows,
     * colon on Unix-like systems).
     * </p>
     *
     * @return A {@link List} of library paths as strings
     */
    public static List<String> getLibraryPaths() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        List<String> paths = new ArrayList<>();

        Collections.addAll(paths, runtimeBean.getLibraryPath().split(";"));
        return paths;
    }

    /**
     * Retrieves the class paths used by the JVM.
     * <p>
     * The class paths specify directories and JAR files where the JVM
     * searches for Java classes and resources. The paths are split based
     * on the system path separator (semicolon on Windows, colon on Unix-like systems).
     * </p>
     *
     * @return A {@link List} of class paths as strings
     */
    public static List<String> getClassPaths() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        List<String> paths = new ArrayList<>();

        Collections.addAll(paths, runtimeBean.getClassPath().split(";"));
        return paths;
    }

    /**
     * Retrieves the input arguments passed to the JVM at startup.
     * <p>
     * This includes VM-specific arguments such as heap size settings,
     * system properties, and other JVM tuning parameters.
     * </p>
     *
     * @return A {@link List} of JVM input arguments as strings
     */
    public static List<String> getInputArgs() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments();
    }
}