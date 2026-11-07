package com.emperor.nekron.lib.system.hardware;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * Provides access to motherboard (baseboard) hardware information.
 * <p>
 * This class uses the OSHI library to retrieve detailed information about
 * the system's motherboard, including manufacturer, model, version, and
 * serial number. The class is designed with lazy initialization and
 * cached static instances to ensure efficient access to hardware data.
 * </p>
 * <p>
 * The motherboard information is retrieved once during class initialization
 * and cached for subsequent calls, providing consistent data throughout
 * the application lifecycle.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public class Motherboard {
    private final static SystemInfo systemInfo;
    private final static HardwareAbstractionLayer layer;

    private final static Baseboard motherboard;
    static {
        systemInfo = new SystemInfo();
        layer = systemInfo.getHardware();

        motherboard = layer.getComputerSystem().getBaseboard();
    }

    /**
     * Retrieves the motherboard manufacturer name.
     *
     * @return the manufacturer name (e.g., "ASUS", "MSI", "Gigabyte", "Dell")
     */
    public static String getManufacturer() {
        return motherboard.getManufacturer();
    }

    /**
     * Retrieves the motherboard model name.
     *
     * @return the model name (e.g., "ROG STRIX Z490-E GAMING", "XPS 15 9500")
     */
    public static String getModel() {
        return motherboard.getModel();
    }

    /**
     * Retrieves the motherboard version.
     * <p>
     * The version number may indicate a revision or specific variant of
     * the motherboard model.
     * </p>
     *
     * @return the version string
     */
    public static String getVersion() {
        return motherboard.getVersion();
    }

    /**
     * Retrieves the motherboard serial number.
     * <p>
     * The serial number is a unique identifier assigned by the manufacturer
     * to the specific physical motherboard unit.
     * </p>
     *
     * @return the serial number string
     */
    public static String getSerialNumber() {
        return motherboard.getSerialNumber();
    }
}