package com.emperor.nekron.libs.system.hardware;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.util.List;

/**
 * Provides network interface information for the system.
 * <p>
 * This class acts as a wrapper around the OSHI (Operating System and Hardware Information)
 * library to expose network adapter details. It retrieves information about all available
 * network interfaces, including both physical and virtual adapters, with their respective
 * network statistics and configuration data.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
public class NetworkCard {
    private static final HardwareAbstractionLayer layer;

    static {
        layer = new SystemInfo().getHardware();
    }

    /**
     * Retrieves a list of all network interfaces available on the system.
     * <p>
     * Each {@link NetworkIF} object contains detailed information such as interface name,
     * MAC address, IP addresses, network statistics (bytes sent/received, packets, etc.),
     * and connection speed. This includes both physical network adapters (Ethernet, Wi-Fi)
     * and virtual interfaces (loopback, VPN, virtual machine adapters).
     * </p>
     *
     * @return A {@link List} of {@link NetworkIF} objects representing all detected network interfaces
     */
    public static List<NetworkIF> getNetworkInterfaces() {
        return layer.getNetworkIFs();
    }
}