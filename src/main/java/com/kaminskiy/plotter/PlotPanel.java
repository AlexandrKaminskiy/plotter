package com.kaminskiy.plotter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlotPanel extends JPanel {

    private static final int WINDOW_PADDING = 5;
    private static final int TEXT_MARGIN = 4;
    private static final int TRAIT_WIDTH = 5;
    private static final int POINT_RADIUS = 3;

    public static final int PIXEL_PER_UNIT = 20;
    public static final double MAX_SCALE = 20;
    public static final double MIN_SCALE = 0.1;

    private List<Double> x;
    private List<Double> y;
    private int centerX;
    private int centerY;
    private double scale = 1;

    public PlotPanel(List<Double> x, List<Double> y, int centerX, int centerY, double scale) {
        this(x, y);
        this.centerX = centerX;
        this.centerY = centerY;
        this.scale = scale;
        PlotMouseListener plotMouseListener = new PlotMouseListener(this);
        addMouseMotionListener(plotMouseListener);
        addMouseListener(plotMouseListener);
        addMouseWheelListener(plotMouseListener);

    }

    public PlotPanel(List<Double> x, List<Double> y) {
        this.x = x;
        this.y = y;
    }

    public void init() {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    private int getPixelPerUnit() {
        return (int) (scale * PIXEL_PER_UNIT);
    }


    private void drawAxes(Graphics g) {
        int counter = 0;
        for (int i = centerX; i < this.getWidth() - WINDOW_PADDING - getPixelPerUnit(); i += getPixelPerUnit()) {
            g.drawLine(i, centerY - TRAIT_WIDTH / 2, i, centerY + TRAIT_WIDTH / 2);
            g.drawString(String.valueOf(counter), i - TRAIT_WIDTH / 2 + TEXT_MARGIN, centerY - TRAIT_WIDTH / 2);
            counter++;
        }

        counter = 0;
        for (int i = centerX; i > WINDOW_PADDING + getPixelPerUnit(); i -= getPixelPerUnit()) {
            g.drawLine(i, centerY - TRAIT_WIDTH / 2, i, centerY + TRAIT_WIDTH / 2);
            g.drawString(String.valueOf(counter), i - TRAIT_WIDTH / 2 + TEXT_MARGIN, centerY - TRAIT_WIDTH / 2);
            counter--;
        }
        counter = 0;
        for (int i = centerY; i < this.getHeight() - WINDOW_PADDING - getPixelPerUnit(); i += getPixelPerUnit()) {
            g.drawLine(centerX - TRAIT_WIDTH / 2, i, centerX + TRAIT_WIDTH / 2, i);
            g.drawString(String.valueOf(counter), centerX - TRAIT_WIDTH / 2 + TEXT_MARGIN, i - TRAIT_WIDTH / 2);
            counter--;
        }
        counter = 0;
        for (int i = centerY; i > WINDOW_PADDING + getPixelPerUnit(); i -= getPixelPerUnit()) {
            g.drawLine(centerX - TRAIT_WIDTH / 2, i, centerX + TRAIT_WIDTH / 2, i);
            g.drawString(String.valueOf(counter), centerX - TRAIT_WIDTH / 2 + TEXT_MARGIN, i - TRAIT_WIDTH / 2);
            counter++;
        }
    }

    @Override
    public void paint(Graphics g) {
        init(); //todo remove every paint invocation
//        this.centerX = this.getWidth() / 2;
//        this.centerY = this.getHeight() / 2;
        g.drawRect(WINDOW_PADDING, WINDOW_PADDING, this.getWidth() - 2 * WINDOW_PADDING, this.getHeight() - 2 * WINDOW_PADDING);
        g.drawLine(centerX, WINDOW_PADDING, centerX, this.getHeight() - WINDOW_PADDING);
        g.drawLine(WINDOW_PADDING, centerY, this.getWidth() - WINDOW_PADDING, centerY);

        drawAxes(g);

        List<Integer> xs = new ArrayList<>();
        List<Integer> ys = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            int xStart = (int) (centerX + getPixelPerUnit() * x.get(i)) - POINT_RADIUS;
            int yStart = (int) (centerY - getPixelPerUnit() * y.get(i)) - POINT_RADIUS;
            if (xStart < WINDOW_PADDING || xStart > this.getWidth() - WINDOW_PADDING) {
                continue;
            }
            if (yStart < WINDOW_PADDING || yStart > this.getHeight() - WINDOW_PADDING) {
                continue;
            }
            g.fillOval(xStart, yStart, POINT_RADIUS * 2,POINT_RADIUS * 2);
            xs.add(xStart + POINT_RADIUS);
            ys.add(yStart + POINT_RADIUS);
        }
        int[] xsArray = new int[xs.size()];
        int[] ysArray = new int[ys.size()];
        for (int i = 0; i < xsArray.length; i++) {
            xsArray[i] = xs.get(i);
            ysArray[i] = ys.get(i);
        }
        g.drawPolyline(xsArray, ysArray, xsArray.length);

    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        if (scale < MIN_SCALE || scale > MAX_SCALE) {
            return;
        }

        this.scale = scale;
    }
}
