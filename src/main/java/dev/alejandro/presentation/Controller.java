package dev.alejandro.presentation;

import dev.alejandro.utils.ImageConverter;
import dev.alejandro.utils.Utils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class Controller implements ActionListener,
        MouseListener, KeyListener ,MouseWheelListener, ChangeListener {

    private final Model m;
    private double newMinX;
    private double newMaxX;
    private double newMinY;
    private double newMaxY;
    private int ZOOM;
    public Controller(View aThis) {
        this.m = aThis.getModel();
        resetValues();
        ZOOM = 32;
    }
    private void moveToRight(){
        double x = newMaxX - newMinX;
        newMinX += x/ZOOM;
        newMaxX += x/ZOOM;
    }
    private void moveToLeft(){
        double x = newMaxX - newMinX;
        newMinX -= x/ZOOM;
        newMaxX -= x/ZOOM;
    }
    private void moveToUp(){
        double y = newMaxY - newMinY;
        newMinY -= y/ZOOM;
        newMaxY -= y/ZOOM;
    }
    private void moveToDown(){
        double y = newMaxY - newMinY;
        newMinY += y/ZOOM;
        newMaxY += y/ZOOM;
    }
    private void zoom(){
        double x = newMaxX - newMinX;
        double y = newMaxY - newMinY;
        newMinX += x/ZOOM;
        newMaxX -= x/ZOOM;
        newMinY += y/ZOOM;
        newMaxY -= y/ZOOM;
    }
    private void resetValues(){
        newMinX = -2;
        newMaxX = 2;
        newMinY = -2;
        newMaxY = 2;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "INICIAR" -> {
                try {
                    m.dibujar(newMinX, newMaxX, newMinY, newMaxY);
                    m.getVentana().enableButtons(false);
                    m.changeTextButtonStart();
                    m.getVentana().enableRecordButton(true);
                }catch (AWTException ex){
                    ex.printStackTrace();
                }
            }
            case "GRABAR" -> {
                JOptionPane.showMessageDialog(m.getVentana(), "La secuencia a grabar es la siguiente:");
                m.dibujarSecuencia();
                int response = JOptionPane.showConfirmDialog(m.getVentana(), "¿Confirmar la secuencia?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    m.createImages();
                    String imagesPath = Utils.getTempDir().toAbsolutePath().toString();
                    File dir = new File(imagesPath);
                    File[] files = dir.listFiles();
                    if (files == null || files.length == 0) {
                        JOptionPane.showInternalMessageDialog(m.getVentana(), "The directory is empty");
                        throw new IllegalArgumentException("The directory is empty");
                    }
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.setDialogTitle("Seleccione donde guardar el video y el nombre del archivo en formato .avi");
                    int userSelection = fileChooser.showSaveDialog(null);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        String output = fileToSave.getAbsolutePath();
                        if (!output.toLowerCase().endsWith(".avi") ) {
                            output += ".avi";
                        }
                        int videoDuration = 10;
                        double speedFactor = 1;
                        try {
                            videoDuration = Integer.parseInt(JOptionPane.showInputDialog(m.getVentana(), "Ingrese la duración del video en segundos"));
                            speedFactor = Double.parseDouble(JOptionPane.showInputDialog(m.getVentana(), "Ingrese el factor de velocidad"));
                        }catch (NumberFormatException ex){
                            JOptionPane.showMessageDialog(m.getVentana(), "Ingrese un número válido");
                            throw new RuntimeException("Ingrese un número válido");
                        }
                        ImageConverter.recordFractal(m.getVentana(),Utils.getTempDir().toAbsolutePath().toString(),output,videoDuration,speedFactor);
                    }
                    JOptionPane.showMessageDialog(m.getVentana(), "La secuencia ha sido grabada");
                }
                m.getVentana().enableButtons(true);
                m.getVentana().enableRecordButton(false);
                m.changeTextButtonStart();
                resetValues();
                m.cleanCanvas();
                m.cleanImages();
            }
            case "REINICIAR" -> {
                m.getVentana().enableButtons(true);
                m.getVentana().enableRecordButton(false);
                m.changeTextButtonStart();
                m.cleanCanvas();
                resetValues();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        try{
            zoom();
            m.dibujar(newMinX, newMaxX, newMinY, newMaxY);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        try {
            if (e.getWheelRotation() < 0) {
                zoom();
                m.dibujar(newMinX, newMaxX, newMinY, newMaxY);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            ZOOM = source.getValue();
        }
    }

    public enum KeyEnum{
        RIGHT(39),
        LEFT(37),
        UP(38),
        DOWN(40);
        private final int value;
        KeyEnum(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        try {

            switch (ke.getKeyCode()) {
                case KeyEvent.VK_RIGHT-> {moveToRight();m.dibujar(newMinX,newMaxX,newMinY,newMaxY);}
                case KeyEvent.VK_LEFT -> {moveToLeft();m.dibujar(newMinX,newMaxX,newMinY,newMaxY);}
                case KeyEvent.VK_UP -> {moveToUp();m.dibujar(newMinX,newMaxX,newMinY,newMaxY);}
                case KeyEvent.VK_DOWN -> {moveToDown();m.dibujar(newMinX,newMaxX,newMinY,newMaxY);}
                default -> {}
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
