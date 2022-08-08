
package presentacion;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import logica.*;

/**
 *
 * @author alejandro
 */
public class Modelo {
    private Julia julia;
    private Mandelbrot mandelbrot;
    private Canvas lienzo;
    private Graphics g;
    private int width;
    private int height;
    private Vista ventana;
    private boolean isDraw;
    private BufferedImage buffer;//Utilizado para evitar el mal renderizado del dibujo
    public Modelo(){
        isDraw=false;
    }
    public Vista getVentana(){
        if(ventana==null){
            ventana=new Vista(this);
        }
        return ventana;
    } 
    public void iniciarApp(){
        getVentana().iniciarApp();
    }
    public void iniciar() {
        getVentana().getContentPane().removeAll();
        getVentana().iniciarEjecucion();
        getVentana().setSize(1200,800);
        getVentana().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getVentana().setResizable(false);
        getVentana().setVisible(true);
        getVentana().setLocationRelativeTo(null);
        getVentana().setTitle("FRACTALES");
        getVentana().getBoton().setText("DIBUJAR");
        getVentana().validate();
    }
    public void dibujar(int caso,double newMinX,double newMaxX,double newMinY,double newMaxY){
        lienzo= getVentana().getLienzo();
        g=lienzo.getGraphics();
        if(caso==1){
            this.mandelbrot = new Mandelbrot(this);
            g.drawImage(mandelbrot.dibujo(newMinX,newMaxX,newMinY,newMaxY), 0, 0, lienzo);
        }else{
            this.julia = new Julia(this, caso);
            g.drawImage(julia.dibujo(newMinX,newMaxX,newMinY,newMaxY), 0, 0, lienzo);
        }
    }
    
    public void borrar(){
        lienzo=getVentana().getLienzo();
        BufferedImage bufferTemp = new BufferedImage(lienzo.getWidth(),lienzo.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics g2= bufferTemp.getGraphics();
        g=lienzo.getGraphics();
        for(int x=0;x<=lienzo.getWidth();x++){
            for(int y=0;y<=lienzo.getHeight();y++){
                g2.drawLine(x, y, x, y);
                g2.setColor(new Color(0, 0, 0));
            }
        }
        g.drawImage(bufferTemp, 0, 0, lienzo);
    }

    public Julia getJulia() {
        return julia;
    }

    public void setJulia(Julia julia) {
        this.julia = julia;
    }

    public Mandelbrot getMandelbrot() {
        return mandelbrot;
    }

    public void setMandelbrot(Mandelbrot mandelbrot) {
        this.mandelbrot = mandelbrot;
    }
    
}
