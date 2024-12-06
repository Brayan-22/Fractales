package dev.alejandro.logic;

import dev.alejandro.utils.Constants;

import java.util.concurrent.ForkJoinPool;

public class RecursiveFractal implements Fractal{

    private int caso;
    private final int width;
    private final int height;


    private double newMinx;
    private double newMaxx;
    private double newMiny;
    private double newMaxy;

    public RecursiveFractal(){
        this.width = Constants.WIDTH;
        this.height = Constants.HEIGHT;
    }


    @Override
    public int[][] dibujar(double newMinx, double newMaxx, double newMiny, double newMaxy) throws IllegalArgumentException {
        if (caso == 0){
            throw new IllegalArgumentException("No se ha seleccionado un fractal");
        }
        int [][] matriz = new int[width][height];
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MatrixFillTask(matriz, 0, width, height, newMinx, newMaxx, newMiny, newMaxy,caso));
        pool.close();
        return matriz;
    }

    public void setCaso(int caso) {
        this.caso = caso;
    }

    public int getCaso() {
        return caso;
    }

    public double getNewMinx() {
        return newMinx;
    }

    public void setNewMinx(double newMinx) {
        this.newMinx = newMinx;
    }

    public double getNewMaxx() {
        return newMaxx;
    }

    public void setNewMaxx(double newMaxx) {
        this.newMaxx = newMaxx;
    }

    public double getNewMiny() {
        return newMiny;
    }

    public void setNewMiny(double newMiny) {
        this.newMiny = newMiny;
    }

    public double getNewMaxy() {
        return newMaxy;
    }

    public void setNewMaxy(double newMaxy) {
        this.newMaxy = newMaxy;
    }
}
