
package presentacion;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 *
 * @author alejandro
 */
public class Controlador implements ActionListener,MouseListener,KeyListener{
    private Modelo modelo;
    public Controlador(Vista aThis){
        this.modelo=aThis.getModelo();
    }
    public double normalizar(double x, double min, double max, double newMin, double newMax) {
        return (x-min)/(max-min) * (newMax-newMin)+newMin;
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        try {
            if(evento.getActionCommand().equals("DIBUJAR") && (modelo.getVentana().rbtnSeleccionado()==0)){
            JOptionPane.showMessageDialog(modelo.getVentana(), "Por favor seleccione una opcion");
        }else{
            if(evento.getActionCommand().equals("DIBUJAR")){
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(),-2.0,2.0,-2.0,2.0);
                modelo.getVentana().apagarBotones();
                modelo.getVentana().getBoton().setText("BORRAR");
            }
            if(evento.getActionCommand().equals("BORRAR")){
                modelo.borrar();
                modelo.getVentana().encenderBotones();
                modelo.getVentana().getBoton().setText("DIBUJAR");
            }
            if(evento.getActionCommand().equals("INICIAR")){
                modelo.iniciar();
            }
        }
        } catch (HeadlessException e) {
        }

        
    }
    //
    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        try {
        if(modelo.getVentana().rbtnSeleccionado()>1 && modelo.getVentana().rbtnSeleccionado()<=10){
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                (modelo.getJulia().getNewMinX()*0.91), 
                (modelo.getJulia().getNewMaxX()*0.91), 
                (modelo.getJulia().getNewMinY()*0.91), 
                (modelo.getJulia().getNewMaxY()*0.91));
        }else if(modelo.getVentana().rbtnSeleccionado()==1){
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                (modelo.getMandelbrot().getNewMinX()*0.91), 
                (modelo.getMandelbrot().getNewMaxX()*0.91), 
                (modelo.getMandelbrot().getNewMinY()*0.91), 
                (modelo.getMandelbrot().getNewMaxY()*0.91));
        }
        } catch (Exception e) {
        }

    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //System.err.println("PRESS:"+ke.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //System.err.println("PRESS:"+ke.getKeyCode());
        try {
            if(modelo.getVentana().rbtnSeleccionado()==1){
            switch (ke.getKeyCode()) {
            case 37:
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                        modelo.getMandelbrot().getNewMinX()-0.019, 
                        modelo.getMandelbrot().getNewMaxX()-0.019, 
                        modelo.getMandelbrot().getNewMinY(),
                        modelo.getMandelbrot().getNewMaxY());
                break;
            case 38:
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                        modelo.getMandelbrot().getNewMinX(), 
                        modelo.getMandelbrot().getNewMaxX(), 
                        modelo.getMandelbrot().getNewMinY()-0.019,
                        modelo.getMandelbrot().getNewMaxY()-0.019);
                break;
            case 39:
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                        (modelo.getMandelbrot().getNewMinX()+0.019), 
                        (modelo.getMandelbrot().getNewMaxX()+0.019), 
                        (modelo.getMandelbrot().getNewMinY()), 
                        (modelo.getMandelbrot().getNewMaxY()));
                break;
            case 40:
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                        (modelo.getMandelbrot().getNewMinX()), 
                        (modelo.getMandelbrot().getNewMaxX()), 
                        (modelo.getMandelbrot().getNewMinY()+0.019), 
                        (modelo.getMandelbrot().getNewMaxY())+0.019);
                break;
            case 32:
                JOptionPane.showMessageDialog(modelo.getVentana(), "Intervalo X: "+modelo.getMandelbrot().getNewMinX()+" a "+modelo.getMandelbrot().getNewMaxX()+"\n"+
                        "Intervalo Y: "+modelo.getMandelbrot().getNewMinY()+" a "+modelo.getMandelbrot().getNewMaxY());
                break;
            case 10:
                JOptionPane.showMessageDialog(modelo.getVentana(), "INTRODUCE LOS INTERVALOS A DIBUJAR:");
                String minX=JOptionPane.showInputDialog("Introduce el intervalo izquiedo del eje X");
                String maxX=JOptionPane.showInputDialog("Introduce el intervalo derecho del eje X");;
                String minY=JOptionPane.showInputDialog("Introduce el intervalo izquiedo del eje Y");;
                String maxY=JOptionPane.showInputDialog("Introduce el intervalo derecho del eje Y");;
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                        Double.parseDouble(minX), 
                        Double.parseDouble(maxX), 
                        Double.parseDouble(minY), 
                        Double.parseDouble(maxY));
                break;
            default:
                break;
        }
        }else if(modelo.getVentana().rbtnSeleccionado()>1){
            switch (ke.getKeyCode()) {
            case 37:
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                        modelo.getJulia().getNewMinX()-0.007, 
                        modelo.getJulia().getNewMaxX()-0.007, 
                        modelo.getJulia().getNewMinY(),
                        modelo.getJulia().getNewMaxY());
                break;
            case 38:
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                        modelo.getJulia().getNewMinX(), 
                        modelo.getJulia().getNewMaxX(), 
                        modelo.getJulia().getNewMinY()-0.007,
                        modelo.getJulia().getNewMaxY()-0.007);
                break;
            case 39:
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                        (modelo.getJulia().getNewMinX()+0.007), 
                        (modelo.getJulia().getNewMaxX()+0.007), 
                        (modelo.getJulia().getNewMinY()), 
                        (modelo.getJulia().getNewMaxY()));
                break;
            case 40:
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                        (modelo.getJulia().getNewMinX()), 
                        (modelo.getJulia().getNewMaxX()), 
                        (modelo.getJulia().getNewMinY()+0.007), 
                        (modelo.getJulia().getNewMaxY())+0.007);
                break;
            case 32:
                JOptionPane.showMessageDialog(modelo.getVentana(), "Intervalo X: "+modelo.getJulia().getNewMinX()+" a "+modelo.getJulia().getNewMaxX()+"\n"+
                        "Intervalo Y: "+modelo.getJulia().getNewMinY()+" a "+modelo.getJulia().getNewMaxY());
                break;
                
            case 10:
                JOptionPane.showMessageDialog(modelo.getVentana(), "INTRODUCE LOS INTERVALOS A DIBUJAR:");
                String minX=JOptionPane.showInputDialog("Introduce el intervalo izquiedo del eje X");
                String maxX=JOptionPane.showInputDialog("Introduce el intervalo derecho del eje X");;
                String minY=JOptionPane.showInputDialog("Introduce el intervalo izquiedo del eje Y");;
                String maxY=JOptionPane.showInputDialog("Introduce el intervalo derecho del eje Y");;
                modelo.dibujar(modelo.getVentana().rbtnSeleccionado(), 
                        Double.parseDouble(minX), 
                        Double.parseDouble(maxX), 
                        Double.parseDouble(minY), 
                        Double.parseDouble(maxY));
                break;
            default:
                break;
        }            
        }
        } catch (HeadlessException | NumberFormatException e) {
        }
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //System.err.println("PRESS:"+ke.getKeyCode());
    }
}
