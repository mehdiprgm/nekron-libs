package com.emperor.nekron.lib.swing.progressbar;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

/**
 * A custom {@link JProgressBar} UI implementation that renders the progress
 * text left-aligned instead of the default centered alignment.
 *
 * <p>
 * This class extends {@link BasicProgressBarUI} and overrides the
 * {@link #paintString(Graphics, int, int, int, int, int, Insets)} method
 * to draw the progress string with custom styling and positioning.
 * </p>
 *
 * <p>
 * Features:
 * </p>
 * <ul>
 * <li>Left-aligned progress text</li>
 * <li>Custom font support</li>
 * <li>Optional bold font rendering</li>
 * <li>Custom text color</li>
 * <li>Improved rendering quality using antialiasing</li>
 * </ul>
 *
 * <p>
 * This UI delegate can be applied to a {@link JProgressBar} using:
 * </p>
 *
 * <pre>
 * progressBar.setUI(new LeftAlignedProgressBarUI(font, color));
 * </pre>
 *
 * @author Mehdi Lavasani (zenDEv2)
 * @version 1.0
 * @since 21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LeftAlignedProgressBarUI extends BasicProgressBarUI {
    private Font font;
    private Color color;

    private boolean boldFont;

    /**
     * Constructs a new {@code LeftAlignedProgressBarUI} with the specified
     * font and text color.
     *
     * @param font  the font used for rendering the progress text
     * @param color the color used for rendering the progress text
     */
    public LeftAlignedProgressBarUI(Font font, Color color) {
        this.font = font;
        this.color = color;
    }

    /**
     * Paints the progress string inside the progress bar with left alignment.
     *
     * <p>
     * This method enables high-quality rendering hints and draws the progress
     * text slightly offset from the left edge of the progress bar while keeping
     * it vertically centered.
     * </p>
     *
     * @param g          the graphics context used for painting
     * @param x          the x-coordinate of the progress bar
     * @param y          the y-coordinate of the progress bar
     * @param width      the width of the progress bar
     * @param height     the height of the progress bar
     * @param amountFull the amount of the progress bar that is filled
     * @param b          the border insets
     */
    @Override
    protected void paintString(Graphics g, int x, int y, int width, int height, int amountFull, Insets b) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (boldFont) {
            g2.setFont(font.deriveFont(Font.BOLD));
        }

        String text = progressBar.getString();
        FontMetrics fm = g2.getFontMetrics();

        int textY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();

        g2.setColor(color);
        g2.drawString(text, x + 6, textY);
        g2.dispose();
    }
}