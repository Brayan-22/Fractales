
package logica;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import presentacion.Modelo;

/**
 *
 * @author alejandro
 */
public class Mandelbrot implements Fractal{
    private BufferedImage buffer;
    private Canvas lienzo;
    private int width;
    private int height;
    private int maxSize;
    private int maxI;
    private double newMinX,newMaxX,newMinY,newMaxY;
    private Graphics g;
    private Modelo modelo;
    private int maxColors=16;
    public Mandelbrot(Modelo m){
        this.modelo=m;
        maxI=1000;
    }

    @Override
    public double normalizar(double x, double min, double max, double newMin, double newMax) {
        return (x-min)/(max-min) * (newMax-newMin)+newMin;
    }

    @Override
    public BufferedImage dibujo(double newMinX,double newMaxX,double newMinY,double newMaxY) {
        this.newMinX=newMinX;
        this.newMaxX=newMaxX;
        this.newMinY=newMinY;
        this.newMaxY=newMaxY;
        double a,b,ca,cb;
        maxSize=4;
        lienzo=modelo.getVentana().getLienzo();
        buffer=new BufferedImage(lienzo.getWidth(), lienzo.getHeight(), BufferedImage.TYPE_INT_ARGB);
        g=buffer.getGraphics();
        width=lienzo.getWidth();
        height=lienzo.getHeight();
        for(int x=0;x<=width;x++){
            for(int y=0;y<=height;y++){
                int n=0;
                a=normalizar(x,0,width,newMinX,newMaxX);
                b=normalizar(y,0,height,newMinY,newMaxY);
                ca=a;
                cb=b;
                while(n<maxI && (a*a + b*b)<maxSize){
                    double aa=a*a - b*b;
                    double bb=2*a*b;
                    a=aa+ca;
                    b=bb+cb;
                    n++;
                }
                g.drawLine(x, y, x, y);
                if(n>=0 && n<maxI){
                    g.setColor(new Color(getColor(n%maxColors)));
                }else{
                    g.setColor(new Color(0, 0, 0));
                }
            }
        }
        return buffer;
    }

    @Override
    public int getColor(int code) {
        int rgb;
        if(code==8){
            rgb=0x404040;
        }else{
            int i=0x7f | ((code & 8)<<4);
            rgb=(((code&4)*i)<<(16-2))+(((code&2)*i)<<(8-1))+((code&1)*i);
        }
        return rgb;
    }
    
    public double getNewMinX() {
        return newMinX;
    }

    public void setNewMinX(double newMinX) {
        this.newMinX = newMinX;
    }

    public double getNewMaxX() {
        return newMaxX;
    }

    public void setNewMaxX(double newMaxX) {
        this.newMaxX = newMaxX;
    }

    public double getNewMinY() {
        return newMinY;
    }

    public void setNewMinY(double newMinY) {
        this.newMinY = newMinY;
    }

    public double getNewMaxY() {
        return newMaxY;
    }

    public void setNewMaxY(double newMaxY) {
        this.newMaxY = newMaxY;
    }

    public void setMaxI(int maxI) {
        this.maxI = maxI;
    }
    
}
