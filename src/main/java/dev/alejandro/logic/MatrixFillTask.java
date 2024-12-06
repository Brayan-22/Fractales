package dev.alejandro.logic;

import dev.alejandro.utils.Constants;
import dev.alejandro.utils.Utils;

import java.awt.*;
import java.util.concurrent.RecursiveAction;

public class MatrixFillTask extends RecursiveAction {

    private static final int THRESHOLD = 20;
    private final int startRow;
    private final int endRow;
    private final int cols;
    private final double newMinx;
    private final double newMaxx;
    private final double newMiny;
    private final double newMaxy;
    private final int maxSize;
    private final int maxIterations;
    private final int width;
    private final int height;
    private double ca;
    private double cb;
    private final int caso;
    private int[][] matrix;

    public MatrixFillTask(int[][] matrix,int startRow,int endRow,int cols,
                          double newMinx,
                          double newMaxx,
                          double newMiny,
                          double newMaxy,
                          int caso){
        this.matrix = matrix;
        this.startRow = startRow;
        this.endRow = endRow;
        this.cols = cols;
        this.newMinx = newMinx;
        this.newMaxx = newMaxx;
        this.newMiny = newMiny;
        this.newMaxy = newMaxy;
        this.maxIterations = Constants.MAX_ITERATIONS;
        this.maxSize = Constants.MAX_SIZE;
        this.width = Constants.WIDTH;
        this.height = Constants.HEIGHT;
        this.caso = caso;
        switch (caso){
            case 2 -> {this.ca = -1.25;this.cb = 0;}
            case 3 -> {this.ca = -0.70176;this.cb = -0.3842;}
            case 4 -> {this.ca = -0.835;this.cb = -0.2321;}
            case 5 -> {this.ca = -0.8;this.cb = 0.156;}
            case 6 -> {this.ca = 0.285;this.cb = 0.01;}
            case 7 -> {this.ca = -0.4;this.cb = 0.6;}
            case 8 -> {this.ca = 0;this.cb = -0.8;}
            case 9 -> {this.ca = -0.621;this.cb = 0;}
            case 10 -> {this.ca = -0.59;this.cb = 0;}
            default -> {this.ca = 0;this.cb = 0;}
        }
    }

    @Override
    protected void compute() {
        if (endRow - startRow < THRESHOLD){
            for (int i = startRow; i < endRow ; i++) {
                for (int j = 0; j < cols ; j++) {
                    matrix[i][j] = computeValue(i,j);
                }
            }
        }else{
            int mid = (startRow + endRow) / 2;
            MatrixFillTask left = new MatrixFillTask(matrix,startRow,mid,cols,newMinx,newMaxx,newMiny,newMaxy,caso);
            MatrixFillTask right = new MatrixFillTask(matrix,mid,endRow,cols,newMinx,newMaxx,newMiny,newMaxy,caso);
            invokeAll(left,right);
        }
    }

    private int computeValue(int i, int j){
        double a = Utils.normalizar(i,0,width,newMinx,newMaxx);
        double b = Utils.normalizar(j,0,height,newMiny,newMaxy);
        int n = 0;
        if (caso >= 2 && caso <=8){
            while (n<maxIterations && (a*a + b*b) < maxSize){
                double aa = a*a - b*b;
                double bb = 2*a*b;
                a = aa +ca;
                b = bb +cb;
                n++;
            }
            if (n>=0 && n<maxIterations){
                return Utils.getColor(n%Constants.MAX_COLORS);
            }else {
                return new Color(0,0,0).getRGB();
            }
        }else if(caso == 1){
            ca = a;
            cb = b;
            while (n<maxIterations && (a*a + b*b) < maxSize){
                double aa = a*a - b*b;
                double bb = 2*a*b;
                a = aa+ca;
                b = bb+cb;
                n++;
            }
            if (n>=0 && n<maxIterations){
                return Utils.getColor(n%Constants.MAX_COLORS);
            }else{
                return new Color(0,0,0).getRGB();
            }
        }else{
            while (n<maxIterations && (a*a + b*b) < maxSize){
//                double aa = Math.sin(a)*Math.cosh(b);
//                double bb = Math.cos(a)*Math.sinh(b);
                double aa=(Math.exp((Math.pow(a, 3))-3*a*Math.pow(b, 2)))*Math.cos((3*b*Math.pow(a, 2))-Math.pow(b, 3));
                double bb=(Math.exp((Math.pow(a, 3))-3*a*Math.pow(b, 2)))*Math.sin((3*b*Math.pow(a, 2))-Math.pow(b, 3));
                a = aa +ca;
                b = bb +cb;
                n++;
            }
            return (new Color(4*n%255, n%3*30, 50).getRGB());
        }
    }
}
