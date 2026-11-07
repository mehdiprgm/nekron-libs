package com.emperor.nekron.lib.swing.text;

import com.emperor.nekron.lib.swing.text.interfaces.NumberListener;
import com.emperor.nekron.lib.swing.text.interfaces.TextListener;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * A utility class providing extended functionality and management tools for Swing text components.
 *
 * <p>This class enables developers to easily implement input restrictions, validation types,
 * and advanced context menus for any JTextComponent subclass (e.g., JTextField, JTextArea, JPasswordField).
 * It simplifies the process of adding standard editing actions and custom behavior to text fields.</p>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 2.0
 * @since 21
 */
public class JSTextField {

    /**
     * Defines the permitted input types for the text component.
     * This enumeration is used to enforce validation rules on user input.
     */
    public enum TextInputType {
        NUMBER,
        TEXT,
        ANY
    }

    /**
     * Defines the supported languages for the context (popup) menu.
     * This selection determines the localization of menu items and orientation support.
     */
    public enum PopupMenuLanguage {
        ENGLISH,
        PERSIAN
    }

    /**
     * Sets a character limit on the specified text component.
     *
     * <p>Once the limit is reached, further key input events are consumed.
     * If the text length somehow exceeds the limit, it is cleared to enforce the constraint.</p>
     *
     * @param textField the text component to apply the limit to
     * @param limit the maximum number of characters allowed
     */
    public static void setLimit(JTextComponent textField, int limit) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (textField.getText().length() == limit) {
                    e.consume();
                } else if (textField.getText().length() > limit) {
                    textField.setText("");
                }
            }
        });
    }

    /**
     * Configures the input type validation for the specified text component.
     *
     * <p>This method removes all existing KeyListeners from the component and
     * assigns a specific listener based on the provided TextInputType to filter
     * subsequent keyboard inputs.</p>
     *
     * @param textField the text component to configure
     * @param type the type of input allowed (e.g., NUMBER, TEXT, ANY)
     */
    public static void setInputType(JTextComponent textField, TextInputType type) {
        for (int i = 0; i < textField.getKeyListeners().length; ++i) {
            textField.removeKeyListener(textField.getKeyListeners()[i]);
        }

        switch (type) {
            case NUMBER ->
                    textField.addKeyListener(new NumberListener());
            case TEXT ->
                    textField.addKeyListener(new TextListener());
        }
    }

    /**
     * Attaches a customizable context (popup) menu to the specified text component.
     *
     * <p>The menu provides standard editing actions (Copy, Cut, Paste, Delete, Clear, Select All)
     * and a utility to insert the current date and time. It supports localization for
     * English and Persian, and handles UI orientation accordingly.</p>
     *
     * <p>The menu visibility and action availability are dynamically updated based on
     * text selection, editability, and component type (e.g., hiding sensitive actions
     * for JPasswordField).</p>
     *
     * @param textField the text component to attach the menu to
     * @param language the localization language for the menu items
     * @param font the font to apply to all menu items
     */
    public static void setMenu(JTextComponent textField, PopupMenuLanguage language, Font font) {
        JPopupMenu pop = new JPopupMenu();
        
        JMenuItem miCopy, miCut, miPaste, miDelete;
        JMenuItem miClear, miSelectAll, miDateTime;
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        if (language == PopupMenuLanguage.ENGLISH) {
            miCopy = new JMenuItem("Copy");
            miCut = new JMenuItem("Cut");
            miPaste = new JMenuItem("Paste");
            miDelete = new JMenuItem("Delete");
            miClear = new JMenuItem("Clear");
            miSelectAll = new JMenuItem("Select all");
            miDateTime = new JMenuItem("Date / time");
        } else {
            miCopy = new JMenuItem("کپی");
            miCut = new JMenuItem("بریدن");
            miPaste = new JMenuItem("الحاق");
            miDelete = new JMenuItem("حذف");
            miClear = new JMenuItem("پاک کردن");
            miSelectAll = new JMenuItem("انتخاب همه");
            miDateTime = new JMenuItem("ناریخ / زمان");

            for (JMenuItem mi : Arrays.asList(miCopy, miCut, miPaste, miDelete, miClear, miSelectAll, miDateTime)) {
                mi.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            }
        }

        miCopy.addActionListener((ae) -> kit.getSystemClipboard().setContents(new StringSelection(textField.getSelectedText()), (ClipboardOwner) null));
        miCut.addActionListener((ae) -> {
            kit.getSystemClipboard().setContents(new StringSelection(textField.getSelectedText()), null);
            if (textField.getText().equals(textField.getSelectedText())) {
                textField.setText("");
            } else {
                int index = textField.getSelectionStart();
                String begin = textField.getText().substring(0, index);
                String end = textField.getText().substring(index + textField.getSelectedText().length());
                textField.setText(begin + end);
            }
        });

        miPaste.addActionListener((ae) -> {
            try {
                int caretPosition = textField.getCaretPosition();

                String selectedText = textField.getSelectedText();
                String text = textField.getText();
                String clipBoardText = kit.getSystemClipboard().getData(DataFlavor.stringFlavor).toString();

                if (selectedText == null) {
                    String startString = text.substring(0, caretPosition);
                    String endString = text.substring(caretPosition);

                    textField.setText(startString + clipBoardText + endString);
                } else {
                    String startString = text.substring(0, textField.getSelectionStart());
                    String endString = text.substring(textField.getSelectionEnd());
                    textField.setText(startString + clipBoardText + endString);
                }
            } catch (Exception ignore) {
            }
        });

        miDelete.addActionListener((ae) -> {
            int index = textField.getSelectionStart();

            String begin = textField.getText().substring(0, index);
            String end = textField.getText().substring(index + textField.getSelectedText().length());

            textField.setText(begin + end);
        });
        miClear.addActionListener((ae) -> textField.setText(""));
        miSelectAll.addActionListener((ae) -> textField.selectAll());
        miDateTime.addActionListener((ae) -> {
            int caretPosition = textField.getCaretPosition();
            String time, date, timeDate;

            String text = textField.getText();
            String startString = text.substring(0, caretPosition);
            String endString = text.substring(caretPosition);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            date = dtf.format(LocalDateTime.now());
            dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

            time = dtf.format(LocalDateTime.now());
            timeDate = time.concat(date);

            textField.setText(startString + timeDate + endString);
        });

        miCopy.setFont(font);
        miCut.setFont(font);
        miPaste.setFont(font);
        miDelete.setFont(font);
        miClear.setFont(font);
        miSelectAll.setFont(font);
        miDateTime.setFont(font);
        
        pop.add(miCopy);
        pop.add(miCut);
        pop.add(miPaste);
        pop.add(miDelete);
        pop.add(miClear);
        pop.add(miSelectAll);
        pop.add(miDateTime);

        textField.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) {
                    if (textField.getSelectedText() == null) {
                        miCopy.setEnabled(false);
                        miCut.setEnabled(false);
                        miDelete.setEnabled(false);
                    } else {
                        miCopy.setEnabled(true);
                        miCut.setEnabled(true);
                        miDelete.setEnabled(true);
                    }

                    if (textField.getText() == null) {
                        miClear.setEnabled(false);
                        miSelectAll.setEnabled(false);
                    } else {
                        miClear.setEnabled(true);
                        miSelectAll.setEnabled(true);
                    }

                    if (!textField.isEditable()) {
                        miCut.setVisible(false);
                        miDelete.setVisible(false);
                        miDateTime.setVisible(false);
                        miClear.setVisible(false);
                        miPaste.setVisible(false);
                    } else if (textField instanceof JPasswordField) {
                        miCopy.setVisible(false);
                        miCut.setVisible(false);
                        miDelete.setVisible(false);
                    } else {
                        miCut.setVisible(true);
                        miDelete.setVisible(true);
                        miDateTime.setVisible(true);
                        miClear.setVisible(true);
                        miPaste.setVisible(true);
                    }

                    pop.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
}