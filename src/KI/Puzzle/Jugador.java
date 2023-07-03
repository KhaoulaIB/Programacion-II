
package KI.Puzzle;

import java.io.Serializable;

/**
 *
 * @author h
 */



public class Jugador implements Serializable {
/*@Serial
private static final long serialVersionUID = 1924451968313706819L;*/



    private int punts;

    private String Nom;
    private String data;

    public Jugador(int punts, String nom, String data) {
        this.punts = punts;
        Nom = nom;
        this.data = data;
    }

    public static final Jugador Centinela = new Jugador(0,"aaa", null);


    public boolean EsCentinela(){
        return Nom.equals(Centinela.Nom);

    }



    public String getNom() {
        return Nom.toUpperCase();
    }





@Override
    public String toString (){
        return "- JUGADOR: "+ Nom.toUpperCase() + "  "+ "   - DATA: "+ data +   "   - PUNTS: " + punts;
    }
}