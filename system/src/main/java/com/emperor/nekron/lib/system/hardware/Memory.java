package com.emperor.nekron.lib.system.hardware;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;

import java.util.List;

/**
 * Provides comprehensive memory information for the system.
 * <p>
 * This class acts as a wrapper around the OSHI (Operating System and Hardware Information)
 * library to expose both physical RAM and swap (virtual memory) details. It provides
 * methods to retrieve total, used, and free memory capacities for both types of memory,
 * and detailed information about individual physical memory modules.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 3.0
 * @since 21
 */
public class Memory {
    private final static SystemInfo systemInfo;
    private final static GlobalMemory memory;

    static {
        systemInfo = new SystemInfo();
        memory = systemInfo.getHardware().getMemory();
    }

    /**
     * Retrieves the total physical memory (RAM) installed in the system.
     *
     * @return Total RAM size in bytes
     */
    public static double getTotalSpace() {
        return memory.getTotal();
    }

    /**
     * Retrieves the amount of physical memory (RAM) currently in use.
     * <p>
     * This is calculated as the total RAM minus the available memory.
     * </p>
     *
     * @return Used RAM size in bytes
     */
    public static double getUsedSpace() {
        return memory.getTotal() - memory.getAvailable();
    }

    /**
     * Retrieves the amount of physical memory (RAM) currently available for use.
     * <p>
     * Available memory represents the portion of RAM that is not actively in use
     * and can be allocated to new processes.
     * </p>
     *
     * @return Available (free) RAM size in bytes
     */
    public static double getFreeSpace() {
        return memory.getAvailable();
    }

    /**
     * Retrieves the total swap (virtual memory) space configured on the system.
     * <p>
     * Swap space is used as an extension of physical memory when RAM is exhausted.
     * </p>
     *
     * @return Total swap space size in bytes
     */
    public static double getSwapTotalSpace() {
        return memory.getVirtualMemory().getSwapTotal();
    }

    /**
     * Retrieves the amount of swap (virtual memory) space currently in use.
     *
     * @return Used swap space size in bytes
     */
    public static double getSwapUsedSpace() {
        return memory.getVirtualMemory().getSwapUsed();
    }

    /**
     * Retrieves the amount of swap (virtual memory) space currently available.
     * <p>
     * This is calculated as the total swap minus the used swap space.
     * </p>
     *
     * @return Available (free) swap space size in bytes
     */
    public static double getSwapFreeSpace() {
        return memory.getVirtualMemory().getSwapTotal() - memory.getVirtualMemory().getSwapUsed();
    }

    /**
     * Retrieves detailed information about individual physical memory modules (RAM sticks).
     * <p>
     * Each {@link PhysicalMemory} object contains information such as module capacity,
     * clock speed, manufacturer, and memory type (e.g., DDR4, DDR5).
     * </p>
     *
     * @return A {@link List} of {@link PhysicalMemory} objects representing all installed RAM modules
     */
    public static List<PhysicalMemory> getPhysicalMemories() {
        return memory.getPhysicalMemory();
    }
}