package com.emperor.nekron.libs.terminal.table;

import com.emperor.nekron.libs.terminal.natives.interfaces.TerminalController;
import com.emperor.nekron.libs.terminal.natives.TerminalProvider;
import com.emperor.nekron.libs.terminal.records.WindowSize;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a text-based table for terminal output.
 *
 * <p>This class stores table headers and rows, calculates column widths,
 * and renders the table based on the current terminal width.</p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 7.0
 * @since 21
 */
@Data
public class Table {
    private List<String> headers;
    private List<TableRow> rows;

    private int columnMaxSize;
    private boolean showHeaderLine;

    private String setPadding(String text, int width) {
        StringBuilder sb = new StringBuilder(text);

        /* Fill the remaining space with spaces */
        while (sb.length() < width) {
            sb.append(" ");
        }

        return sb.toString();
    }

    private String truncate(String text, int width) {
        if (text.length() <= width) {
            return text;
        }

        if (width <= 3) {
            return text.substring(0, width);
        }

        return text.substring(0, width - 3) + "...";
    }

    public Table(int columnMaxSize) {
        headers = new ArrayList<>();
        rows = new ArrayList<>();

        this.columnMaxSize = columnMaxSize;
    }

    /**
     * Renders the table as a formatted string.
     *
     * <p>This method calculates column widths from headers and rows, adjusts them
     * to fit the terminal width, and returns the final table output.</p>
     *
     * @return the rendered table as a string
     */
    public String render() {
        TerminalController controller = TerminalProvider.getController();
        WindowSize windowSize = controller.getWindowSize();

        int cols = headers.size();
        int[] widths = new int[cols];

        int terminalWidth = windowSize.width();
        int separatorWidth = (cols - 1) * 2;

        /* Calculate the column widths from headers */
        for (int i = 0; i < cols; i++) {
            widths[i] = headers.get(i).length();
        }

        /* Calculate the column widths from rows */
        for (TableRow row : rows) {
            List<Object> items = row.getItems();

            for (int i = 0; i < items.size() && i < cols; i++) {
                Object cell = items.get(i);
                int len = cell == null ? 0 : String.valueOf(cell).length();

                if (len > widths[i]) {
                    widths[i] = len;
                }
            }
        }

        if (columnMaxSize > 0) {
            for (int i = 0; i < widths.length; i++) {
                widths[i] = Math.min(widths[i], columnMaxSize);
            }
        }

        int totalWidth = separatorWidth;

        /* Sum of all cell widths */
        for (int w : widths) {
            totalWidth += w;
        }

        if (totalWidth > terminalWidth) {
            int excess = totalWidth - terminalWidth;

            while (excess > 0) {
                boolean reduced = false;

                for (int i = 0; i < cols && excess > 0; i++) {
                    if (widths[i] > 4) {
                        widths[i]--;
                        excess--;

                        reduced = true;
                    }
                }

                if (!reduced) {
                    break;
                }
            }
        }

        StringBuilder headerLine = new StringBuilder();
        StringBuilder output = new StringBuilder();

        if (showHeaderLine) {
            for (int i = 0; i < cols; i++) {
                String value = truncate(headers.get(i), widths[i]);
                headerLine.append(setPadding(value, widths[i]));

                /* Check to see if this is the last column or not */
                if (i < cols - 1) {
                    headerLine.append("  ");
                }
            }

            output.append(headerLine).append("\n");
            output.repeat("-", headerLine.length()).append("\n");
        }

        for (TableRow row : rows) {
            StringBuilder line = new StringBuilder();
            List<Object> items = row.getItems();

            for (int i = 0; i < cols; i++) {
                String value = "";

                if (i < items.size() && items.get(i) != null) {
                    value = String.valueOf(items.get(i));
                }

                value = truncate(value, widths[i]);
                line.append(setPadding(value, widths[i]));

                if (i < cols - 1) {
                    line.append("  ");
                }
            }

            output.append(line).append("\n");
        }

        return output.toString();
    }
}