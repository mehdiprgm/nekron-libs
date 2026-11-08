package com.emperor.nekron.libs.system.software.partition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages and retrieves information about storage partitions on the system.
 * <p>
 * This class provides functionality to enumerate all available storage volumes
 * (partitions) and gather detailed information about each, including capacity,
 * usage statistics, file system type, and access permissions.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
public class PartitionManager {

    /**
     * Creates a {@code Partition} instance from the given root file.
     *
     * @param partitionFile the root file representing the partition
     * @return a {@code Partition} populated with the partition's details
     * @throws IOException if the file store information cannot be read
     */
    private static Partition createPartition(File partitionFile) throws IOException {
        return new Partition(
                partitionFile.getAbsolutePath(),
                Files.getFileStore(partitionFile.toPath()).type(),
                partitionFile.getTotalSpace(),
                partitionFile.getUsableSpace(),
                partitionFile.getTotalSpace() - partitionFile.getUsableSpace(),
                partitionFile.canRead(),
                partitionFile.canWrite()
        );
    }

    /**
     * Retrieves the storage partitions available on the system.
     *
     * @param mounted if {@code true}, only readable (mounted) partitions are
     *                returned; if {@code false}, all detected partitions are
     *                returned regardless of readability
     * @return a list of partitions matching the requested criteria
     * @throws IOException if information about a partition cannot be read
     */
    public static List<Partition> getPartitions(boolean mounted) throws IOException {
        List<Partition> partitions = new ArrayList<>();

        for (File root : File.listRoots()) {
            if (mounted) {
                if (root.canRead()) {
                    partitions.add(createPartition(root));
                }
            } else {
                partitions.add(createPartition(root));
            }
        }

        return partitions;
    }
}