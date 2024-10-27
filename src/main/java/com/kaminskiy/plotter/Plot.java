package com.kaminskiy.plotter;

import javax.swing.*;
import java.util.List;

public class Plot {

    private List<Double> x;
    private List<Double> y;
    private String title = "Unnamed plot";

    public Plot(List<Double> x, List<Double> y, String title) {
        if (x.size() != y.size()) {
            throw PlotterException.getIncorrectListSizesException();
        }
        this.x = x;
        this.y = y;
    }

    public void draw() {
        PlotPanel plotPanel = new PlotPanel(x, y, 400, 300, 1);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);
        frame.setBounds(100, 100, 800, 600);
        frame.setContentPane(plotPanel);
        plotPanel.init();
        frame.setVisible(true);
    }
}
