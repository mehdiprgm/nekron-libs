package com.emperor.nekron.lib.terminal.table;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a single row in a terminal table.
 *
 * <p>This class stores the cell values of one table row as a list of objects.</p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
@Data
public class TableRow {
    private List<Object> items;

    public TableRow(Object... objects) {
        items = new ArrayList<>();
        Collections.addAll(items, objects);
    }
}