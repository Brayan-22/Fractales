package dev.alejandro;

import dev.alejandro.presentation.Model;


public class Launcher {

    private final Model miApp;
    public Launcher(){
        miApp = new Model();
        miApp.getVentana().setVisible(true);
    }

    public static void main(String[] args) {
      new Launcher();
    }
}
