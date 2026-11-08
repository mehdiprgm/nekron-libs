package com.emperor.nekron.libs.swing.resources;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

/**
 * Utility class for loading and applying icon resources to Swing components.
 *
 * <p>
 * This class provides helper methods that simplify assigning image icons
 * to commonly used Swing components. Icons are loaded from the application's
 * classpath using {@link Class#getResource(String)} and automatically scaled
 * to the desired size before being applied to the target component.
 * </p>
 *
 * <p>
 * The utility currently supports the following Swing components:
 * </p>
 *
 * <ul>
 * <li>{@link JMenuItem}</li>
 * <li>{@link JButton}</li>
 * <li>{@link JLabel}</li>
 * <li>{@link JToggleButton}</li>
 * </ul>
 *
 * <p>
 * Icons are internally scaled using {@link Image#getScaledInstance(int, int, int)}
 * with a scaling hint value of {@code 4}, which corresponds to
 * {@code Image.SCALE_SMOOTH}. This ensures smoother visual rendering when
 * resizing image resources.
 * </p>
 *
 * <p>
 * The resource path must reference a valid file located in the application's
 * classpath. If the resource cannot be located, a {@link NullPointerException}
 * will be thrown due to the use of {@link Objects#requireNonNull(Object)}.
 * </p>
 *
 * <p>
 * This class is designed as a static utility container and is not intended
 * to be instantiated.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 2.0
 */
public class Resources {
    /**
     * Sets an icon on the specified Swing component using the provided
     * resource path. The icon is automatically scaled to a default
     * size of <b>25x25 pixels</b>.
     *
     * <p>
     * This method is a convenience overload that internally delegates
     * to {@link #setComponentIcon(Component, String, int, int)}.
     * </p>
     *
     * @param component the Swing component that will receive the icon
     * @param path the classpath resource path of the image
     */
    public static void setComponentIcon(Component component, String path) {
        setComponentIcon(component, path, 25, 25);
    }

    /**
     * Sets an icon on the specified Swing component using an image
     * resource from the classpath and scales it to the specified
     * dimensions.
     *
     * <p>
     * The method performs the following steps:
     * </p>
     *
     * <ol>
     * <li>Loads the image resource using {@link Class#getResource(String)}</li>
     * <li>Creates an {@link ImageIcon} instance from the resource</li>
     * <li>Scales the image to the requested width and height</li>
     * <li>Applies the icon to the supported Swing component</li>
     * </ol>
     *
     * <p>
     * Supported component types include:
     * </p>
     *
     * <ul>
     * <li>{@link JMenuItem}</li>
     * <li>{@link JButton}</li>
     * <li>{@link JLabel}</li>
     * <li>{@link JToggleButton}</li>
     * </ul>
     *
     * <p>
     * If the provided component is not one of the supported types,
     * the method performs no action.
     * </p>
     *
     * <p>
     * If the specified resource path cannot be resolved, a
     * {@link NullPointerException} will be thrown.
     * </p>
     *
     * @param component the Swing component that will receive the icon
     * @param path the classpath resource path of the image
     * @param x the target icon width in pixels
     * @param y the target icon height in pixels
     */
    public static void setComponentIcon(Component component, String path, int x, int y) {
        ImageIcon i = new ImageIcon((URL) Objects.requireNonNull(Resources.class.getResource(path)));
        Image image = i.getImage();
        Image newImg = image.getScaledInstance(x, y, 4);

        i = new ImageIcon(newImg);
        if (component instanceof JMenuItem) {
            ((JMenuItem) component).setIcon(i);
        } else if (component instanceof JButton) {
            ((JButton) component).setIcon(i);
        } else if (component instanceof JLabel) {
            ((JLabel) component).setIcon(i);
        } else if (component instanceof JToggleButton) {
            ((JToggleButton) component).setIcon(i);
        }
    }
}