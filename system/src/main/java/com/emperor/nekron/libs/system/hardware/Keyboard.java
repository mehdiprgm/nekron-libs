package com.emperor.nekron.libs.system.hardware;

import java.awt.im.InputContext;

/**
 * Provides keyboard input locale information for the system.
 * <p>
 * This class retrieves the current input locale (keyboard layout) using the AWT
 * InputContext API. It allows detection of the active keyboard language setting
 * at the system level, which is useful for internationalization and input method
 * handling in applications.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
public class Keyboard {

    /**
     * Checks whether the current keyboard input locale is Persian (Farsi).
     * <p>
     * This method returns {@code true} if the active keyboard layout is set to
     * Persian/Farsi (locale code "fa_IR").
     * </p>
     *
     * @return {@code true} if the current layout is Persian (fa_IR),
     * {@code false} otherwise
     */
    public static boolean isPersian() {
        return InputContext.getInstance().getLocale().toString().equalsIgnoreCase("fa_IR");
    }

    /**
     * Checks whether the current keyboard input locale is English (US).
     * <p>
     * This method returns {@code true} if the active keyboard layout is set to
     * US English (locale code "en_US").
     * </p>
     *
     * @return {@code true} if the current layout is US English (en_US),
     * {@code false} otherwise
     */
    public static boolean isEnglish() {
        return InputContext.getInstance().getLocale().toString().equalsIgnoreCase("en_US");
    }

    /**
     * Retrieves the current keyboard input locale identifier.
     * <p>
     * Returns the full locale string representation of the active keyboard layout
     * (e.g., "fa_IR" for Persian, "en_US" for US English).
     * </p>
     *
     * @return The locale string representing the current keyboard layout
     */
    public static String getLayout() {
        return InputContext.getInstance().getLocale().toString();
    }
}