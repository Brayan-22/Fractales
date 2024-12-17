package dev.alejandro.utils;

import java.awt.*;

public final class Constants {
    private Constants() {}


    public static Font FONT_MINI = new Font("Arial", Font.BOLD, 10);
    public static Font FONT_SMALL = new Font("Arial", Font.BOLD, 12);
    public static Font FONT_MEDIUM = new Font("Arial", Font.BOLD, 14);
    public static Font FONT_LARGE = new Font("Arial", Font.BOLD, 16);
    public static Font FONT_XLARGE = new Font("Arial", Font.BOLD, 20);

    public static final String START = "Start";
    public static final String RECORD = "Record";
    public static final String RESTART = "Restart";


    public static final int WIDTH = Integer.parseInt(Utils.getProperties().getProperty("app.canvas.width", "800"));
    public static final int HEIGHT = Integer.parseInt(Utils.getProperties().getProperty("app.canvas.height", "700"));
    public static final int MAX_SIZE = Integer.parseInt(Utils.getProperties().getProperty("app.fractal.maxsize", "1000"));
    public static final int MAX_ITERATIONS = Integer.parseInt(Utils.getProperties().getProperty("app.fractal.maxiterations","1000"));
    public static final int MAX_COLORS = Integer.parseInt(Utils.getProperties().getProperty("app.colors.max", "16"));
    public static final int VIEW_WIDTH = Integer.parseInt(Utils.getProperties().getProperty("app.view.width", "1200"));
    public static final int VIEW_HEIGHT = Integer.parseInt(Utils.getProperties().getProperty("app.view.height", "800"));
}
