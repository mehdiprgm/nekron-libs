package com.emperor.nekron.lib.system.hardware;

import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.List;

/**
 * Provides access to graphics hardware information for the system.
 * <p>
 * This class serves as a wrapper around the OSHI (Operating System and Hardware Information)
 * library to expose GPU (Graphics Processing Unit) details. It retrieves information about
 * all graphics cards installed in the system, including dedicated and integrated GPUs.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public class GPU {
    private final static SystemInfo systemInfo;
    private final static HardwareAbstractionLayer layer;

    static {
        systemInfo = new SystemInfo();
        layer = systemInfo.getHardware();

    }

    /**
     * Retrieves a list of all graphics cards present in the system.
     * <p>
     * Each {@link GraphicsCard} object contains detailed information such as
     * card name, vendor, VRAM size, device ID, and driver version.
     * The list may include both dedicated and integrated GPUs.
     * </p>
     *
     * @return A {@link List} of {@link GraphicsCard} objects representing all detected GPUs.
     * Returns an empty list if no graphics cards are found.
     */
    public static List<GraphicsCard> getGraphicsCards() {
        return layer.getGraphicsCards();
    }
}