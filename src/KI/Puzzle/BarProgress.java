

/*
*Autora: Khaoula Ikkene
*/

package KI.Puzzle;

import javax.swing.*;
import java.awt.*;

public class BarProgress extends JProgressBar {
    //DECLARACIÓN ATRIBUTOS
    private int valorMinimo = 0;

    private JProgressBar barraTemporal;
    private int valorMaximo=100;
    private final int ANCHO_BARRA=1002;
    private final int ALTURA_BARRA = 25;

    /*
    *Crea un barra de progress i defineix els seus atributs
     */
    public JProgressBar barratemporal ( ) {
        barraTemporal = new JProgressBar();
        barraTemporal.setMinimum(valorMinimo);
        barraTemporal.setMaximum(valorMaximo);
        barraTemporal.setValue(0);
        barraTemporal.setStringPainted(true);
        barraTemporal.setPreferredSize(new Dimension(ANCHO_BARRA,ALTURA_BARRA));
        barraTemporal.setForeground(Color.RED);
        barraTemporal.setBackground(Color.YELLOW);
        barraTemporal.setVisible(true);
        return barraTemporal;
    }





}