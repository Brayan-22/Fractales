package dev.alejandro.presentation;

import dev.alejandro.presentation.components.LienzoPanel;
import dev.alejandro.presentation.components.OptionsPanel;
import dev.alejandro.utils.Constants;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private final Model model;
    private Controller controller;
    private final OptionsPanel optionsPanel;
    private final LienzoPanel lienzoPanel;
    public View(Model m) {
        //Configuración de la ventana
        this.setTitle("Fractales");
        this.setSize(Constants.VIEW_WIDTH,Constants.VIEW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        //Inicialización de componentes
        this.lienzoPanel = new LienzoPanel();
        this.optionsPanel = new OptionsPanel();
        this.model = m;
        //Configuración del panel principal
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //Panel opciones
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 3;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(optionsPanel,gbc);
        //Panel lienzo
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 3;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(lienzoPanel,gbc);
        //Añadir eventos
        optionsPanel.getRecordButton().setEnabled(false);
        addEvents();
    }

    public Controller getControl(){
        if (controller == null){
            controller = new Controller(this);
        }
        return controller;
    }

    public Canvas getLienzo(){
        return lienzoPanel.getCanvas();
    }

    public void addEvents(){
        optionsPanel.getRecordButton().addActionListener(getControl());
        optionsPanel.getStartButton().addActionListener(getControl());
        lienzoPanel.getCanvas().addMouseListener(getControl());
        lienzoPanel.getCanvas().addMouseWheelListener(getControl());
        lienzoPanel.getCanvas().addKeyListener(getControl());
        lienzoPanel.getSlider().addChangeListener(getControl());
    }

    public int rbSelected() {
        for (int i = 0; i < optionsPanel.getButtons().size(); i++) {
            if (optionsPanel.getButtons().get(i).isSelected()) {
                return i+1;
            }
        }
        return 0;
    }
    public synchronized void enableButtons(boolean b){
        optionsPanel.enableButtons(b);
    }
    public void enableRecordButton(boolean b){
        optionsPanel.enableRecordButton(b);
    }
    public Model getModel() {
        return model;
    }
    public JButton getStartButton(){
        return optionsPanel.getStartButton();
    }

    public synchronized int getValueSlider(){
        return lienzoPanel.getSlider().getValue();
    }
}
