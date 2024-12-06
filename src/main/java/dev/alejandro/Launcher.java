package dev.alejandro;

import dev.alejandro.presentation.Model;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Launcher {

    private final Model miApp;
    public Launcher(){
        miApp = new Model();
        miApp.getVentana().setVisible(true);
    }



    public static void main(String[] args) {
      new Launcher();
    }
    private void createAndShowGUI() {
        AtomicInteger counter = new AtomicInteger(0);
        JFrame frame = new JFrame("Ejemplo de barra de progreso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        JButton startButton = new JButton("Iniciar");
        frame.add(startButton);

        startButton.addActionListener(e -> {
            showProgressDialog(frame,counter);
        });
        frame.setVisible(true);
        for (int i = 0; i < 100 ; i++) {
            try{
                counter.incrementAndGet();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter.set(i);
        }
    }
    private void showProgressDialog(JFrame parent,AtomicInteger counter) {
        JDialog progressDialog = new JDialog(parent, "Procesando...", true);
        progressDialog.setSize(300, 100);
        progressDialog.setLayout(new BorderLayout());
        progressDialog.setLocationRelativeTo(parent);

        // Crear la barra de progreso
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setIndeterminate(false);
        progressDialog.add(new JLabel("Procesando..."), BorderLayout.NORTH);
        progressDialog.add(progressBar, BorderLayout.CENTER);

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < 100 ; i++) {
                    try{
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                progressBar.setValue(progress);
            }

            protected void done() {
                progressDialog.dispose();
                JOptionPane.showMessageDialog(parent, "Proceso completado", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        };
        worker.execute();
        progressDialog.setVisible(true);

    }
}
