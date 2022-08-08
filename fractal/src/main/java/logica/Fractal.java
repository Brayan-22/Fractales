package logica;

import java.awt.image.BufferedImage;


/**
 *
 * @author alejandro
 */
public interface Fractal {
    double normalizar(double x,double min,double max,double newMin,double newMax);
    BufferedImage dibujo(double newMinx,double newMaxX,double newMinY,double newMaxY);
    int getColor(int code);
}
