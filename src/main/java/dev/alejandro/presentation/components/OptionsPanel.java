package dev.alejandro.presentation.components;

import dev.alejandro.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OptionsPanel extends JPanel {

    private ButtonGroup group;
    private List<JRadioButton> buttons = new ArrayList<>();
    private JButton startButton;
    private JButton recordButton;
    private JLabel titleLabel;
    public OptionsPanel() {
        this.setSize(500, 800);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.group = new ButtonGroup();
        this.titleLabel = new JLabel("Seleccione un fractal");
        this.titleLabel.setFont(Constants.FONT_LARGE);
        this.titleLabel.setForeground(Color.BLACK);
        this.titleLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 3;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        for (int i = 0; i < 10; i++) {
            JRadioButton button = new JRadioButton("Fractal " + (i + 1));
            button.setFont(Constants.FONT_MEDIUM);
            button.setForeground(Color.BLACK);
            button.setActionCommand(String.valueOf(i + 1));
            group.add(button);
            buttons.add(button);
            gbc.gridx = 1;
            gbc.gridy = i + 1;
            gbc.gridwidth = 2;
            gbc.gridheight = 1;
            gbc.weightx = 2;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;
            add(button, gbc);
        }
        startButton = new JButton("INICIAR");
        startButton.setFont(Constants.FONT_MEDIUM);
        startButton.setForeground(Color.BLACK);
        startButton.setSize(200, 100);
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 3;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.CENTER;
        add(startButton, gbc);
        recordButton = new JButton("GRABAR");
        recordButton.setFont(Constants.FONT_MEDIUM);
        recordButton.setForeground(Color.BLACK);
        recordButton.setSize(200, 100);
        gbc.gridx = 3;
        gbc.gridy = 11;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 3;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.CENTER;
        add(recordButton, gbc);
    }




    public ButtonGroup getGroup() {
        return group;
    }

    public void setGroup(ButtonGroup group) {
        this.group = group;
    }

    public List<JRadioButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<JRadioButton> buttons) {
        this.buttons = buttons;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public void setTitleLabel(JLabel titleLabel) {
        this.titleLabel = titleLabel;
    }


    public JButton getRecordButton() {
        return recordButton;
    }

    public void enableButtons(boolean b) {
        for (JRadioButton button : buttons) {
            button.setEnabled(b);
        }
    }
    public void enableRecordButton(boolean b){
        recordButton.setEnabled(b);
    }
}
