package com.emperor.nekron.libs.terminal.natives;

import com.emperor.nekron.libs.terminal.natives.interfaces.TerminalController;
import com.emperor.nekron.libs.terminal.records.CursorCoordinate;
import com.emperor.nekron.libs.terminal.records.WindowSize;

/**
 * Windows implementation of {@code TerminalController} backed by native calls.
 * <p>
 * Provides basic terminal operations such as clearing the screen, moving the cursor,
 * reading a key, and retrieving cursor/window state.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
public final class WindowsTerminalController implements TerminalController {

    @Override
    public void clearScreen() {
        nativeClearScreen();
    }

    @Override
    public void gotoxy(int x, int y) {
        nativeGotoxy(x, y);
    }

    @Override
    public int readKey(boolean echo) {
        return nativeReadKey(echo);
    }

    @Override
    public CursorCoordinate getCursorCoordinate() {
        return new CursorCoordinate(getCursorXPosition(), getCursorYPosition());
    }

    @Override
    public WindowSize getWindowSize() {
        return new WindowSize(getWindowWidth(), getWindowHeight());
    }

    private native void nativeClearScreen();
    private native void nativeGotoxy(int x, int y);

    private native int nativeReadKey(boolean echo);
    private native int getCursorXPosition();
    private native int getCursorYPosition();

    private native int getWindowWidth();
    private native int getWindowHeight();
}
