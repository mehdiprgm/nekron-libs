package com.emperor.nekron.libs.system.hardware;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

/**
 * Provides hardware-level CPU information for the system.
 * <p>
 * This class acts as a thin wrapper around the OSHI (Operating System and Hardware Information)
 * library to expose processor details such as vendor, model, core counts, and identification data.
 * All methods are static and retrieve real-time hardware information from the underlying OS.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public class CPU {
    private final static SystemInfo systemInfo;
    private final static CentralProcessor processor;

    static {
        systemInfo = new SystemInfo();
        processor = systemInfo.getHardware().getProcessor();
    }

    /**
     * Retrieves the processor name as reported by the hardware.
     * <p>
     * This typically includes the brand, family, and model designation
     * (e.g., "Intel Core i7-10750H CPU @ 2.60GHz").
     * </p>
     *
     * @return The processor name string
     */
    public static String getName() {
        return processor.getProcessorIdentifier().getName();
    }

    /**
     * Retrieves the vendor name of the processor.
     * <p>
     * Common values include "GenuineIntel" for Intel processors
     * and "AuthenticAMD" for AMD processors.
     * </p>
     *
     * @return The processor vendor string
     */
    public static String getVendor() {
        return processor.getProcessorIdentifier().getVendor();
    }

    /**
     * Retrieves the processor model identifier.
     * <p>
     * This returns the specific model number or family code
     * as defined by the manufacturer.
     * </p>
     *
     * @return The processor model string
     */
    public static String getModel() {
        return processor.getProcessorIdentifier().getModel();
    }

    /**
     * Retrieves the stepping level of the processor.
     * <p>
     * The stepping indicates the revision or minor version of the processor die,
     * used for identifying production changes and errata fixes.
     * </p>
     *
     * @return The stepping string
     */
    public static String getStepping() {
        return processor.getProcessorIdentifier().getStepping();
    }

    /**
     * Retrieves the unique processor ID string.
     * <p>
     * This is a platform-specific identifier that can include
     * vendor, family, model, and stepping information.
     * </p>
     *
     * @return The processor ID string
     */
    public static String getProcessorID() {
        return processor.getProcessorIdentifier().getProcessorID();
    }

    /**
     * Retrieves the microarchitecture name of the processor.
     * <p>
     * This identifies the internal architecture generation
     * (e.g., "Skylake", "Zen 3", "Alder Lake").
     * </p>
     *
     * @return The microarchitecture string
     */
    public static String getMicroarchitecture() {
        return processor.getProcessorIdentifier().getMicroarchitecture();
    }

    /**
     * Retrieves the number of physical CPU cores available.
     * <p>
     * Physical cores represent the actual hardware processing units
     * present on the processor die.
     * </p>
     *
     * @return The number of physical cores
     */
    public static int getNumberOfPhysicalCores() {
        return processor.getPhysicalProcessorCount();
    }

    /**
     * Retrieves the number of logical CPU cores available.
     * <p>
     * Logical cores include physical cores plus additional virtual cores
     * enabled by technologies like Intel Hyper-Threading or AMD SMT.
     * </p>
     *
     * @return The number of logical cores
     */
    public static int getNumberOfLogicalCores() {
        return processor.getLogicalProcessorCount();
    }
}