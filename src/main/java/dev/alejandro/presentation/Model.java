package dev.alejandro.presentation;

import dev.alejandro.logic.RecursiveFractal;
import dev.alejandro.utils.Constants;
import dev.alejandro.utils.ImageConverter;
import dev.alejandro.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Model {
    private View ventana;
    private final RecursiveFractal fractal;
    private final List<BufferedImage> images = new LinkedList<>();
    public Model(){
        fractal = new RecursiveFractal();
    }

    public void initApp(){
        getVentana();
    }

    public synchronized void dibujarSecuencia(){
        Graphics g = ventana.getLienzo().getGraphics();
        images.forEach(img -> {
            try{
                g.drawImage(img, 0, 0, ventana.getLienzo());
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
    }


    public synchronized void dibujar(double newMinX, double newMaxX, double newMinY, double newMaxY) throws AWTException {
        int caso = ventana.rbSelected();
        fractal.setCaso(caso);
        BufferedImage img = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.getGraphics();
        try {
            int[][] matriz = fractal.dibujar(newMinX, newMaxX, newMinY, newMaxY);
            for (int i = 0; i < Constants.WIDTH; i++) {
                for (int j = 0; j < Constants.HEIGHT; j++) {
                    g.setColor(new Color(matriz[i][j]));
                    g.drawLine(i, j, i, j);
                }
            }
            Graphics g2 = ventana.getLienzo().getGraphics();
            g2.drawImage(img, 0, 0, ventana.getLienzo());
            images.add(img);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(ventana, "No se ha seleccionado un fractal", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(e.getMessage());
            throw new AWTException("No se ha seleccionado un fractal");
        }
    }
    public void cleanImages(){
        images.clear();
    }

    public View getVentana(){
        if (ventana == null){
            ventana = new View(this);
        }
        return ventana;
    }

    public synchronized void changeTextButtonStart() {
        if (ventana.getStartButton().getText().equals(Constants.START)) {
            ventana.getStartButton().setText(Constants.RESTART);
        }else{
            ventana.getStartButton().setText(Constants.START);
        }
    }
    public void createImages(){
        if (images.isEmpty()) return;
        Utils.deleteFolderContent(Utils.getTempDir().toAbsolutePath().toString());
        //Mostrar la barra de progreso
        JDialog progressDialog = new JDialog(this.ventana,"Procesando...",true);
        progressDialog.setSize(300, 100);
        progressDialog.setLayout(new BorderLayout());
        progressDialog.setLocationRelativeTo(null);
        // Crear la barra de progreso
        JProgressBar progressBar = new JProgressBar(0, images.size());
        progressBar.setStringPainted(true);
        progressDialog.add(new JLabel("Procesando..."), BorderLayout.NORTH);
        progressDialog.add(progressBar, BorderLayout.CENTER);
        AtomicBoolean isRunning = new AtomicBoolean(true);
        SwingWorker<Void,Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (BufferedImage img : images) {
                    ImageConverter.saveAsPng(img, String.format("%s/frame_%06d.png",Utils.getTempDir().toAbsolutePath().toString(),images.indexOf(img)+1),true);
                    publish(images.indexOf(img));
                }
                return null;
            }

            protected void process(List<Integer> chunks) {
                progressBar.setValue(chunks.get(0));
            }
            protected void done() {
                progressDialog.dispose();
            }
        };
        worker.execute();
        progressDialog.setVisible(true);
        while (isRunning.get()) {
            try {
                Thread.sleep(10);
                if (worker.isDone()) {
                    isRunning.set(false);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cleanCanvas() {
        Graphics g = ventana.getLienzo().getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
    }
}
