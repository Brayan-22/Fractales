
package presentacion;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author alejandro
 */
public class Vista extends JFrame{
    //MVC
    private ArrayList<JRadioButton> arregloRadioButtons;
    private Modelo modelo;
    private Controlador control;
    //componentes graficos
    private JLabel label1;
    private JButton boton;
    private ButtonGroup grupoBotones;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panelInicio;
    private Canvas lienzo;
    private JButton botonInicio;
    private JTextArea areaTexto;
    
    public Vista(Modelo m){
        this.modelo=m; 
    }
    public void iniciarApp(){
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setTitle("FRACTALES");
        this.setLayout(new BorderLayout());
        panelInicio = new JPanel();
        areaTexto = new JTextArea();
        botonInicio = new JButton();
        panelInicio.setLayout(null);
        panelInicio.setBounds(0, 0, 400, 400);
        areaTexto.setBounds(50, 250, 300, 200);
        areaTexto.setFocusable(false);
        areaTexto.setFont(new Font("Agency FB", Font.BOLD, 12));
        //areaTexto.setBackground(panelInicio.getBackground());
        areaTexto.setWrapStyleWord(true);
        areaTexto.setLineWrap(true);
        areaTexto.setText("Las nubes no son esferas, las montañas no son conos, las costas no son círculos, y "
                + "las cortezas de los árboles no son lisas, ni los relámpagos viajan en una línea recta.\n"
                + "                                         Benoît Mandelbrot");
        areaTexto.setEditable(false);
        areaTexto.setBackground(panelInicio.getBackground());
        botonInicio.setText("INICIAR");
        botonInicio.setFont(new Font("Agency FB", Font.BOLD, 14));
        botonInicio.addActionListener(getControl());
        botonInicio.setBounds(100, 80, 200, 50);
        panelInicio.add(areaTexto);
        panelInicio.add(botonInicio);
        this.add(panelInicio);
    }
    private void initComponents() {
        this.setLayout(null);
        panelUno();
        panelDos();
    }
    public void iniciarEjecucion(){
        initComponents();
        capturarEventos();
    }
    public Controlador getControl(){
        if(control==null){
            control=new Controlador(this);
        }
        return control;
    }
    private void panelUno(){
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEADING));
        lienzo = new Canvas();
        lienzo.setBackground(new Color(0, 0, 0));
        panel1.setBounds(350, 30, 800, 700);
        panel1.add(lienzo);
        lienzo.setSize(panel1.getWidth(), panel1.getHeight());
        lienzo.addMouseListener(getControl());
        lienzo.addKeyListener(getControl());
        this.add(panel1);
    }
    
    private void panelDos(){
        grupoBotones = new ButtonGroup();
        boton = new JButton();
        label1 = new JLabel("ESCOJA UN FRACTAL A DIBUJAR");
        label1.setFont(new Font("Agency FB",Font.BOLD , 14));
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBounds(30,620,310,110);
        panel2.setLayout(new GridLayout(11, 1, 1, 1));
        panel2.setBounds(30, 30, 310, 550);
        panel2.add(label1);
        arregloRadioButtons = new ArrayList<>();
        for(int i=1;i<=10;i++){
            JRadioButton radioButton = new JRadioButton("Fractal "+String.valueOf(i));
            radioButton.setFont(new Font("Agency FB", Font.BOLD, 14));
            arregloRadioButtons.add(radioButton);
            grupoBotones.add(radioButton);
            panel2.add(radioButton);
        }
        boton.setBounds(30, 0, 230, 110);
        panel3.add(boton);
        this.add(panel3);
        this.add(panel2);
    }
    public void apagarBotones(){
        for(int i=0;i<arregloRadioButtons.size();i++){
            arregloRadioButtons.get(i).setEnabled(false);
        }
    }
    public void encenderBotones(){
        for(int i=0;i<arregloRadioButtons.size();i++){
            arregloRadioButtons.get(i).setEnabled(true);
        }
    }
    public int rbtnSeleccionado(){
        int i=0;
        while (true) {         
            if(i==10){
                return 0;
            }else{
                if(arregloRadioButtons.get(i).isSelected()){
                    return i+1;
                }
            }
            i++;
        }
    }
    public Canvas getLienzo(){
        return this.lienzo;
    }
    public void setLienzo(Canvas lienzoNuevo){
        this.lienzo=lienzoNuevo;
    }
    public JButton getBoton(){
        return this.boton;
    }

    private void capturarEventos() {
        this.boton.addActionListener(getControl());
    }
    public Modelo getModelo(){
        return modelo;
    }
}
