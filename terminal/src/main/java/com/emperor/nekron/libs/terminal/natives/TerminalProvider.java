package com.emperor.nekron.libs.terminal.natives;

import com.emperor.nekron.libs.terminal.natives.interfaces.TerminalController;

/**
 * Provides access to the shared terminal controller instance.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
public final class TerminalProvider {
    private static volatile TerminalController controller;

    private TerminalProvider() {
    }

    /**
     * Returns the shared terminal controller instance.
     *
     * @return the terminal controller
     */
    public static TerminalController getController() {
        TerminalController local = controller;

        if (local == null) {
            synchronized (TerminalProvider.class) {
                local = controller;

                if (local == null) {
                    controller = local = new WindowsTerminalController();
                }
            }
        }

        return local;
    }
}