package com.emperor.nekron.libs.swing.theme;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

/**
 * Provides a simple theming utility for Swing applications by applying
 * consistent foreground and selection colors across multiple UI components.
 *
 * <p>
 * This class centralizes the configuration of UI colors by delegating
 * the styling process to the {@code UIManager}. Once configured through
 * the {@link #setup()} method, the specified colors will affect the
 * default appearance of many Swing components including labels, buttons,
 * text fields, tables, menus, and selection highlights.
 * </p>
 *
 * <p>
 * The primary purpose of this class is to simplify global visual
 * customization in Swing-based desktop applications. Instead of manually
 * styling each component instance, developers can define a theme once
 * and apply it across the entire user interface.
 * </p>
 *
 * <p>
 * The theme consists of two main color categories:
 * </p>
 *
 * <ul>
 * <li><b>Theme Color</b> — used primarily as the foreground color for UI components.</li>
 * <li><b>Selection Color</b> — used for highlighting selected elements such as
 * menu items, table rows, and text selections.</li>
 * </ul>
 *
 * <p>
 * Note that this configuration modifies global UI defaults via
 * {@code UIManager.put}. As a result, the theme typically takes effect
 * for components created after the theme has been applied.
 * </p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
@Data
@AllArgsConstructor
public class Theme {
    private Color themeColor;
    private Color selectionColor;

    /**
     * Applies the configured theme settings globally to the Swing UIManager.
     *
     * <p>
     * This method registers the theme's colors as default values for a
     * variety of Swing component properties including foreground colors
     * and selection backgrounds.
     * </p>
     *
     * <p>
     * Components affected include (but are not limited to):
     * </p>
     *
     * <ul>
     * <li>JLabel</li>
     * <li>JButton</li>
     * <li>JTextField</li>
     * <li>JTextArea</li>
     * <li>JTable</li>
     * <li>JMenu and JMenuItem</li>
     * <li>JCheckBox</li>
     * <li>JRadioButton</li>
     * <li>JList</li>
     * <li>JTree</li>
     * <li>JComboBox</li>
     * <li>JTextPane</li>
     * </ul>
     *
     * <p>
     * Selection background colors are also applied to menu items, combo boxes,
     * tables, and text fields to maintain visual consistency throughout the UI.
     * </p>
     *
     * <p>
     * Since this method modifies global UI defaults, it is typically recommended
     * to call it during application initialization before creating UI components.
     * </p>
     */
    public void setup() {
        UIManager.put("Label.foreground", themeColor);       // JLabel
        UIManager.put("Button.foreground", themeColor);      // JButton
        UIManager.put("TextField.foreground", themeColor);   // JTextField
        UIManager.put("TextArea.foreground", themeColor);    // JTextArea
        UIManager.put("Table.foreground", themeColor);       // JTable
        UIManager.put("Menu.foreground", themeColor);        // JMenu
        UIManager.put("MenuItem.foreground", themeColor);    // JMenuItem
        UIManager.put("CheckBox.foreground", themeColor);    // JCheckBox
        UIManager.put("RadioButton.foreground", themeColor); // JRadioButton
        UIManager.put("List.foreground", themeColor);        // JList
        UIManager.put("Tree.foreground", themeColor);        // JTree
        UIManager.put("ComboBox.foreground", themeColor);    // JComboBox
        UIManager.put("TextPane.foreground", themeColor);    // JTextPane

        /* UIManager.put("MenuItem.background", themeColor); */

        UIManager.put("ComboBox.selectionBackground", selectionColor);
        UIManager.put("Menu.selectionBackground", selectionColor);
        UIManager.put("MenuItem.selectionBackground", selectionColor);
        UIManager.put("TextField.selectionBackground", selectionColor);
        UIManager.put("Table.selectionBackground", selectionColor);
    }
}