package com.emperor.nekron.libs.swing.table;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * A customizable table cell renderer that extends {@link DefaultTableCellRenderer}
 * and adds support for rendering {@link JSeparator} components inside table cells.
 *
 * <p>
 * The class enhances standard Swing rendering by allowing two rendering modes:
 * </p>
 *
 * <ul>
 *   <li><b>Normal rendering:</b> Renders cell values as {@link JLabel} components.</li>
 *   <li><b>Separator rendering:</b> When enabled and a cell value is a {@link JSeparator},
 *       the separator is drawn horizontally inside the table cell.</li>
 * </ul>
 *
 * <p>
 * This renderer is useful in cases where a table needs logical separators between rows,
 * such as file managers, categorized lists, dashboards, configuration tables, and more.
 * </p>
 *
 * <p>
 * Cell selection and focus coloring are preserved to ensure full visual integration with
 * the native table look and feel.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * table.setDefaultRenderer(Object.class, new TableCellRenderer(true));
 * </pre>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @since 21
 * @version 1.0
 */
public class TableCellRenderer extends DefaultTableCellRenderer {
    private boolean supportSeparator;

    /**
     * Constructs a new cell renderer with optional support for rendering separators.
     *
     * @param supportSeparator {@code true} to render {@link JSeparator} values,
     *                         {@code false} to treat them as normal values
     */
    public TableCellRenderer(boolean supportSeparator) {
        this.supportSeparator = supportSeparator;
    }

    /**
     * Returns whether separator rendering is currently enabled.
     *
     * @return {@code true} if {@link JSeparator} values will be rendered,
     *         otherwise {@code false}
     */
    public boolean isSupportSeparator() {
        return supportSeparator;
    }

    /**
     * Enables or disables separator rendering mode.
     *
     * @param supportSeparator {@code true} to enable separator support,
     *                         {@code false} to disable it
     */
    public void setSupportSeparator(boolean supportSeparator) {
        this.supportSeparator = supportSeparator;
    }

    /**
     * Returns the component used for rendering a table cell.
     *
     * <p>
     * Behavior:
     * </p>
     *
     * <ul>
     *   <li>If the cell value is a {@link JSeparator} and separator support is enabled,
     *       a horizontal separator is returned directly.</li>
     *   <li>Otherwise, the value is converted to or treated as a {@link JLabel} and
     *       rendered normally with proper selection and default table colors.</li>
     * </ul>
     *
     * @param table the table containing the cell
     * @param value the value to render
     * @param isSelected whether the cell is selected
     * @param hasFocus whether the cell has focus
     * @param row the row index
     * @param column the column index
     * @return the component used to render the cell content
     */
    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        if (value instanceof JSeparator separator && supportSeparator) {
            separator.setOrientation(SwingConstants.HORIZONTAL);
            return separator;
        }

        JLabel label = (value instanceof JLabel)
                ? (JLabel) value
                : new JLabel(value == null ? "" : value.toString());

        super.getTableCellRendererComponent(table, label.getText(), isSelected, hasFocus, row, column);

        label.setOpaque(true);

        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        } else {
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());
        }

        return label;
    }
}