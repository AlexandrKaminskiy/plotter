package com.kaminskiy.plotter;

public class PlotterException extends RuntimeException {
    public static final String INCORRECT_LIST_SIZE_MESSAGE = "X and Y list sizes are not this same";

    public PlotterException(String message) {
        super(message);
    }

    public static PlotterException getIncorrectListSizesException() {
        return new PlotterException(INCORRECT_LIST_SIZE_MESSAGE);
    }
}
