package com.emperor.nekron.libs.swing.table;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

/**
 * A utility class providing helper methods for customizing and managing Swing JTable components.
 * <p>
 * This class offers convenience functions for configuring table headers, adjusting column sizes,
 * clearing table data, and disabling cell editing. It is designed to simplify common UI-related
 * operations and improve JTable usability in desktop applications.
 * <p>
 * The methods in this class do not alter table models beyond the explicitly requested operations
 * and are safe to use for production-grade table configurations.
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
public class JSTable {

    /**
     * Sets the font used by the table header.
     * <p>
     * This method modifies the font of the table's header component without affecting
     * the column renderers or cell styles. It is useful when applying consistent UI themes
     * across packaged applications.
     *
     * @param table the JTable whose header font will be changed
     * @param font  the font to apply to the table header
     */
    public static void setHeaderFont(JTable table, Font font) {
        table.getTableHeader().setFont(font);
    }

    /**
     * Sets the horizontal alignment of the text displayed in the table header cells.
     * <p>
     * The alignment is applied to the default header renderer. Valid alignment values
     * include standard Swing constants such as:
     * <ul>
     *   <li>SwingConstants.LEFT</li>
     *   <li>SwingConstants.CENTER</li>
     *   <li>SwingConstants.RIGHT</li>
     * </ul>
     * This method does not alter the model; it only affects the visual representation
     * of header labels.
     *
     * @param table     the JTable whose header alignment will be modified
     * @param alignment a valid horizontal alignment constant
     */
    public static void setHeaderAlignment(JTable table, int alignment) {
        DefaultTableCellRenderer fileManagerCenterRenderer =
                (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        fileManagerCenterRenderer.setHorizontalAlignment(alignment);
    }

    /**
     * Sets the preferred width of a specific table column.
     * <p>
     * This overload applies the width as a recommendation but does not lock the column size,
     * allowing users to resize the column if the table UI permits.
     *
     * @param table       the JTable whose column width will be updated
     * @param columnIndex the index of the column to resize
     * @param width       the desired width in pixels
     */
    public static void setColumnWidth(JTable table, int columnIndex, int width) {
        setColumnWidth(table, columnIndex, width, false);
    }

    /**
     * Sets the width of a specific column and optionally locks it to prevent resizing.
     * <p>
     * When {@code lockColumnSize} is true, the method sets the minimum and maximum widths
     * equal to the preferred width, effectively preventing the user from resizing the column.
     * <p>
     * This method checks for null or invalid column models to ensure safe operation.
     *
     * @param table          the JTable containing the column to modify
     * @param columnIndex    the index of the target column
     * @param width          the desired width in pixels
     * @param lockColumnSize if true, the column size becomes fixed and non-resizable
     */
    public static void setColumnWidth(JTable table, int columnIndex, int width, boolean lockColumnSize) {
        TableColumnModel model = table.getColumnModel();

        if (model != null) {
            TableColumn column = model.getColumn(columnIndex);

            if (column != null) {
                column.setPreferredWidth(width);

                if (lockColumnSize) {
                    column.setMinWidth(width);
                    column.setMaxWidth(width);
                }
            }
        }
    }

    /**
     * Clears table content, with an option to remove all columns.
     * <p>
     * This method operates in two modes:
     * <ul>
     *     <li><strong>clearColumns = true:</strong> replaces the table model with an empty DefaultTableModel</li>
     *     <li><strong>clearColumns = false:</strong> removes all rows from the existing model while preserving structure</li>
     * </ul>
     * This flexibility allows the developer to choose between total model reset and simple row clearing.
     *
     * @param table        the JTable whose data will be cleared
     * @param clearColumns if true, clears both rows and columns; otherwise only rows are cleared
     */
    public static void clear(JTable table, boolean clearColumns) {
        if (clearColumns) {
            table.setModel(new DefaultTableModel());
        } else {
            DefaultTableModel dm = (DefaultTableModel) table.getModel();

            while (dm.getRowCount() > 0) {
                dm.removeRow(0);
            }
        }
    }

    /**
     * Disables cell editing for all columns and all data types in the specified table.
     * <p>
     * This method effectively makes the JTable read‑only by setting the default editor
     * for {@code Object.class} to {@code null}. Developers can still manually reassign
     * custom editors if needed after calling this method.
     *
     * @param table the JTable to make non-editable
     */
    public static void disableEdit(JTable table) {
        table.setDefaultEditor(Object.class, null);
    }
}