package com.emperor.nekron.lib.swing.list;

import javax.swing.*;

/**
 * A utility class that provides helper methods for managing elements in Swing JList components.
 *
 * <p>This class simplifies common operations such as clearing list contents,
 * adding new elements, and removing elements by index. The methods operate
 * directly on the list model and are intended to streamline manipulation of
 * list data in Swing-based desktop applications.</p>
 *
 * <p>The utilities assume that the list is backed by a DefaultListModel when
 * performing operations that modify the model directly.</p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
public class JSList {

    /**
     * Removes all elements from the specified JList.
     *
     * <p>This method retrieves the current list model, casts it to DefaultListModel,
     * and removes all elements from it. The same model instance is then reapplied
     * to the list.</p>
     *
     * <p>This operation assumes that the list model is an instance of DefaultListModel.
     * If another implementation of ListModel is used, a ClassCastException may occur.</p>
     *
     * @param <E> the type of elements stored in the list
     * @param list the JList whose elements should be cleared
     */
    public static <E> void clear(JList<E> list) {
        DefaultListModel<E> model = (DefaultListModel<E>) list.getModel();
        model.removeAllElements();

        list.setModel(model);
    }

    /**
     * Adds a new element to the end of the specified JList.
     *
     * <p>This method creates a new DefaultListModel, copies all existing elements
     * from the current list model, and appends the provided item to the end.
     * The newly created model then replaces the existing model of the list.</p>
     *
     * <p>This approach ensures compatibility even if the original model is not
     * a DefaultListModel.</p>
     *
     * @param <E> the type of elements stored in the list
     * @param list the JList to which the element will be added
     * @param item the element to append to the list
     */
    public static <E> void addElement(JList<E> list, E item) {
        ListModel<E> listModel = list.getModel();
        DefaultListModel<E> defaultListModel = new DefaultListModel<>();

        for (int j = 0; j < listModel.getSize(); ++j) {
            defaultListModel.addElement(listModel.getElementAt(j));
        }

        defaultListModel.addElement(item);
        list.setModel(defaultListModel);
    }

    /**
     * Removes the element at the specified index from the JList.
     *
     * <p>This method accesses the underlying DefaultListModel of the list and
     * removes the element located at the given index.</p>
     *
     * <p>The method assumes that the list model is an instance of DefaultListModel.
     * If the model uses another implementation, a ClassCastException may occur.</p>
     *
     * @param <E> the type of elements stored in the list
     * @param list the JList from which the element should be removed
     * @param index the index of the element to remove
     */
    public static <E> void removeElement(JList<E> list, int index) {
        DefaultListModel<E> model = (DefaultListModel<E>) list.getModel();
        model.remove(index);
    }
}