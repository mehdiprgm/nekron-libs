package com.emperor.nekron.libs.system.software.partition;

/**
 * Represents a storage partition or volume on the system.
 * <p>
 * This record encapsulates information about a logical storage unit (partition/volume)
 * including its name, file system type, capacity, and usage metrics. It also provides
 * access permission flags indicating whether the partition is readable and writable
 * by the current application context.
 * </p>
 *
 * @param name         The display name or label of the partition (e.g., "Local Disk (C:)", "Data (D:)")
 * @param fileSystem   The file system type of the partition (e.g., "NTFS", "FAT32", "exFAT", "ext4")
 * @param totalSpace   The total capacity of the partition in bytes
 * @param freeSpace    The available free space on the partition in bytes
 * @param usedSpace    The amount of used space on the partition in bytes
 * @param isReadable   Flag indicating whether the partition is readable by the current application
 * @param isWritable   Flag indicating whether the partition is writable by the current application
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 2.0
 */
public record Partition(String name, String fileSystem,
                        double totalSpace, double freeSpace, double usedSpace,
                        boolean isReadable, boolean isWritable) {
}