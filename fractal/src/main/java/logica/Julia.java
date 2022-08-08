
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
public class Julia implements Fractal{
    private BufferedImage buffer;
    private Canvas lienzo;
    private int width;
    private int height;
    private int maxSize;
    private int maxI;
    private double newMinX;
    private double newMaxX;
    private double newMinY;
    private double newMaxY;
    private Graphics g;
    private Modelo modelo;
    private int maxColors=16;
    private int caso;
    public Julia(Modelo m,int caso){
        this.modelo=m;
        this.caso=caso;
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
        double ca=0;
        double cb=0;
        maxSize=4;
        maxI=1000;
        switch (caso) {
            case 2 : 
               /*
               xnewMin=-0.5;
               xnewMax=0.5;
               ynewMin=-0.5;
               ynewMax=0.5;
               */

                ca = -1.25;
                cb = 0;
                break;
            case 3 : 
                /*
                xnewMin=-0.205;
                xnewMax=0.005;
                ynewMin=-0.204;
                ynewMax=0.006;
                */
                ca = -0.70176;
                cb = -0.3842;
                break;
            case 4 :
                /*
                xnewMin=-0.05;
                xnewMax=0.05;
                ynewMin=-0.05;
                ynewMax=0.05;
                */
                ca = -0.835;
                cb = -0.2321;
                break;
            
            case 5 :
                /*
                xnewMin=-0.05;
                xnewMax=0.05;
                ynewMin=-0.05;
                ynewMax=0.05;
                */
                ca = -0.8;
                cb = 0.156;
                break;
            case 6 :
/*
                xnewMin=-0.01;
                xnewMax=0.81;
                ynewMin=-0.11;
                ynewMax=0.71;*/

                ca = 0.285;
                cb = 0.01;
                break;
            case 7 :
                /*
                xnewMin=-0.1;
                xnewMax=0.7;
                ynewMin=-0.7;
                ynewMax=0.1;
                */
                ca = -0.4;
                cb = 0.6;
                break;
            case 10 :
                /*
                xnewMin=-0.9;
                xnewMax=0.00000099;
                ynewMin=-0.9;
                ynewMax=0.00000099;
                */
                ca = -0.59;
                cb = 0;
                break;
            case 9 :
                //xnewMin=-0.9;
                //xnewMax=0.000009;
                //ynewMin=-0.9;
                //ynewMax=0.000009;
                ca = -0.621;
                cb = 0;
                break;
            case 8 :
                /*
                xnewMin=-0.5;
                xnewMax=0.5;
                ynewMin=-0.4;
                ynewMax=0.6;
                */
                ca = 0;
                cb = -0.8;
                break;
        }
        lienzo=modelo.getVentana().getLienzo();
        buffer=new BufferedImage(lienzo.getWidth(), lienzo.getHeight(),BufferedImage.TYPE_INT_ARGB );
        g=buffer.getGraphics();
        width=lienzo.getWidth();
        height=lienzo.getHeight();
        int maxIter=1000;
            if(caso==2||caso==3 || caso==4|| caso==5||caso==6|| caso==7||caso==8){
                for(int x=0;x<=width;x++){
                    for(int y=0;y<=height;y++){
                        double a=normalizar(x, 0, width, newMinX, newMaxX);
                        double b=normalizar(y, 0, height, newMinY, newMaxY);
                        int n=0;
                        while (n<maxIter && (a*a +b*b)<maxSize) {                            
                        double aa=a*a - b*b;
                        double bb=2*a*b;
                        a=aa+ca;
                        b=bb+cb;
                        n++;
                        }
                        g.drawLine(x, y, x, y);
                        /*
                        if(n>0 && n<150){
                            g.setColor(new Color(n%255, 2*n%255, 4*n%255));
                        }else if(n>=150 && n<300){
                            g.setColor(new Color(n%255, n%220, 2*n%255));
                        }else if(n>=300 && n<450){
                            g.setColor(new Color(2*n%80, 0*n%80, 0));
                        }else if(n>=450 && n<600){
                            g.setColor(new Color(2*n%50, 0*n%50, 20));
                        }else if(n>=600 && n<750){
                            g.setColor(new Color(n%255, 0*n%255, 0));
                        }else{
                            g.setColor(new Color(getColor(n%16)));
                        }*/
                        if(n>=0 && n<maxI){
                    g.setColor(new Color(getColor(n%maxColors)));
                }else{
                    g.setColor(new Color(0, 0, 0));
                }
                    }
                }
            }else{
                for(int x=0;x<=width;x++){
                    for(int y=0;y<=height;y++){
                        double a=normalizar(x, 0, width, newMinX, newMaxX);
                        double b=normalizar(y, 0, height, newMinY, newMaxY);
                        int n=0;
                        while (n<1000 && (a*a +b*b)<maxSize) {                            
                        double aa=(Math.exp((Math.pow(a, 3))-3*a*Math.pow(b, 2)))*Math.cos((3*b*Math.pow(a, 2))-Math.pow(b, 3));
                        double bb=(Math.exp((Math.pow(a, 3))-3*a*Math.pow(b, 2)))*Math.sin((3*b*Math.pow(a, 2))-Math.pow(b, 3));
                        a=aa+ca;
                        b=bb+cb;
                        n++;
                        }
                        g.drawLine(x, y, x, y);
                        g.setColor(new Color(4*n%255, n%3*30, 50));
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
    
}
