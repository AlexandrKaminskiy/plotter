package com.kaminskiy.plotter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class PlotMouseListener extends MouseAdapter {

    public static final double SCALE_INCREMENT = 0.1;
    public static final int SCROLL_AMOUNT_TO_SCALE = 20;


    private final PlotPanel plotPanel;

    private int oldX = Integer.MIN_VALUE;
    private int oldY = Integer.MIN_VALUE;

    private boolean pressed = false;


    public PlotMouseListener(PlotPanel plotPanel) {
        this.plotPanel = plotPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!pressed) {
            oldX = Integer.MIN_VALUE;
            oldY = Integer.MIN_VALUE;
        }
        pressed = true;
        super.mousePressed(e);
        System.out.println("mouse pressed");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (oldX == Integer.MIN_VALUE && oldY == Integer.MIN_VALUE) {
            oldX = e.getX();
            oldY = e.getY();
            return;
        }
        if (oldX == e.getX() && oldY == e.getY()) {
            return;
        }

        int deltaX = oldX - e.getX();
        int deltaY = oldY - e.getY();
        plotPanel.setCenterX(plotPanel.getCenterX() - deltaX);
        plotPanel.setCenterY(plotPanel.getCenterY() - deltaY);
        plotPanel.repaint();
        oldX = e.getX();
        oldY = e.getY();
        super.mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        super.mouseReleased(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println(e.getWheelRotation());
        int wheelRotation = e.getWheelRotation();

        System.out.println(e.getScrollAmount());
        System.out.println(e.getScrollType());

        if (e.getWheelRotation() > 0) {
            plotPanel.setScale(plotPanel.getScale() + SCALE_INCREMENT);
        } else {
            plotPanel.setScale(plotPanel.getScale() - SCALE_INCREMENT);
        }
        System.out.println(plotPanel.getScale());
        plotPanel.repaint();

        System.out.println("mouse wheel changed");
        super.mouseWheelMoved(e);
    }
}
