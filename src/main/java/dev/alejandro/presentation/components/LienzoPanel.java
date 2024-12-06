package dev.alejandro.presentation.components;

import dev.alejandro.utils.Constants;

import javax.swing.*;
import java.awt.*;


public class LienzoPanel extends JPanel {
    private Canvas canvas;
    private JSlider slider;
    public LienzoPanel() {
        //Panel de canvas
        JPanel panelCanvas = new JPanel();
        //Panel de slider
        JPanel panelSlider = new JPanel();
        panelSlider.setLayout(new GridBagLayout());
        //Label de zoom
        JLabel label = new JLabel("Zoom", SwingConstants.CENTER);
        label.setFont(Constants.FONT_MEDIUM);
        //Layout del panel de slider
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panelSlider.add(label, gbc);
        //Panel de canvas
        panelCanvas.setLayout(new FlowLayout());
        //Slider
        this.slider = new JSlider(SwingConstants.CENTER, 2, 64, 32);
        this.slider.setSize(200, 50);
        this.slider.setMajorTickSpacing(2);
        this.slider.setPaintTicks(true);
        this.slider.setPaintLabels(true);
        //Posición del slider
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 3;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panelSlider.add(slider, gbc);
        //Configuración del panel principal
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        //Configuración del canvas
        this.canvas = new Canvas();
        this.canvas.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.canvas.setBackground(new Color(0, 0, 0));
        panelCanvas.add(canvas);
        //Añadir componentes al panel principal
        this.add(panelSlider, BorderLayout.NORTH);
        this.add(panelCanvas, BorderLayout.CENTER);
        this.add(new JLabel("   "), BorderLayout.WEST);
        this.add(new JLabel("   "), BorderLayout.EAST);
        this.add(new JLabel("   "), BorderLayout.SOUTH);
    }

    public Canvas getCanvas() {
        return canvas;
    }


    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public JSlider getSlider() {
        return slider;
    }
}
