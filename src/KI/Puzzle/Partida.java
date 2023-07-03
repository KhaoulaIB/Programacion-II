
package KI.Puzzle;


/*
 *Autora: Khaoula Ikkene
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Partida extends JFrame {

    private JPanel panelopcions;
    private JLabel[] labels;
    private JTextField[] Labeldades;
    private String nombreJugador, SubDivHoriz, SubDivVert;

    private static int files, columnes;

    private static final double TIEMPO_FACTOR = 2;


    public static int GetFiles() {
        return files;
    }

    public static int GetColumnes() {
        return columnes;
    }

    public void SetFiles(int f) {
        files = f;
    }

    public void SetColumnes(int c) {
        columnes = c;
    }

    public Partida(){
        //constructor buid
    }



    public Partida(String directorio, JPanel PanelVisualisation, JProgressBar barp, ActionListener L) {

        setTitle("INTRODUCCIÓ DE DADES");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        ImageIcon image = new ImageIcon("logo.jpg");
        setIconImage(image.getImage());
        initComponents(directorio, PanelVisualisation, barp, L);
        PanelVisualisation.revalidate();
        PanelVisualisation.repaint();
    }

    /*
    *Crea la finestra d'introducció de dades del joc
     */
    private void initComponents(String directorio, JPanel PanelVisualisation, JProgressBar barp, ActionListener L) {
        panelopcions = new JPanel(new BorderLayout());
        setPreferredSize(new Dimension(700, 200));
        labels = new JLabel[3];
        String[] info = {"Nombre Jugador", "Número subdivisions horitzontal ", "Número subdivisions Vertical"};
        Labeldades = new JTextField[3];
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBackground(Color.BLACK); // Establecer fondo blanco para el panel
        panel.setPreferredSize(new Dimension(700, 75));
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(info[i]);
            labels[i].setFont(new Font("Courier", Font.ITALIC, 17));
            labels[i].setForeground(Color.WHITE);
            panel.add(labels[i]); // Agregar JLabels directamente al panel
            Labeldades[i] = new JTextField();
            panel.add(Labeldades[i]); // Agregar JTextFields directamente al panel
        }

        panelopcions.add(panel, BorderLayout.CENTER);
        ObtenirDadesJoc(directorio, PanelVisualisation, barp, L);
    }

    SubImagen subimg = new SubImagen();
    BarProgress barprog = new BarProgress();


    private static Timer cronometro; // genera elements de Timer cada x temps

    public static Timer GetCronometro() {
        return cronometro;
    }

    public static void SetCronometro(Timer cr) {
        cronometro = cr;
    }

    public double obtenerTiempoEstimado(double subdivisiones) {
        return Math.pow(Math.E, subdivisiones*1.45);
    }


    /*
    *Verifica les entrades del jugador i utilitza el número de subdivisions per poder calcular el factor d'increment
    * del cronometre
    *
     */
    public void ObtenirDadesJoc(String directorio, JPanel panelVisualisation, JProgressBar progressBar, ActionListener actionListener) {
        JButton continuarButton = new JButton("Confirmar");

        continuarButton.addActionListener(e -> {
            boolean entradasValidas = true;
            SetNombreJugador(Labeldades[0].getText());
            SetSubdivisionsHorizontales(Labeldades[1].getText());
            SetSubdivisionsVerticales(Labeldades[2].getText());
            try {
                SetFiles(Integer.parseInt(SubDivHoriz));
                SetColumnes(Integer.parseInt(SubDivVert));

                if (getNombreJugador().isEmpty()) {
                    entradasValidas = false;
                    Joc.ventanaInformativa("Has d'introduir totes les dades!!!");
                }

                if (files <= 1 || columnes <= 1) {
                    entradasValidas = false;
                    Joc.ventanaInformativa("El número de les subdivisions ha de ser major que 1");
                }

                if (files != columnes) {
                    entradasValidas = false;
                    Joc.ventanaInformativa("Les subdivisiones han de ser iguals");
                }

                if (entradasValidas) {
                    Window windowAncestor = SwingUtilities.getWindowAncestor(panelopcions);
                    windowAncestor.dispose();
                    SubImagen.SetFilas(files);
                    SubImagen.SetColumnas(columnes);

                    try {
                        subimg.ImagePartida(directorio, panelVisualisation, progressBar);
                        barprog.barratemporal();

                        int tiempoEstimado = (int) obtenerTiempoEstimado(GetFiles());
                        int tiempoCronometro = (int) (tiempoEstimado * TIEMPO_FACTOR);

                        SetCronometro(new Timer(tiempoCronometro, actionListener));
                        cronometro.start();

                        panelVisualisation.add(progressBar, BorderLayout.SOUTH);
                        panelVisualisation.revalidate();
                        panelVisualisation.repaint();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } catch (NumberFormatException error) {
                Joc.ventanaInformativa("Has d'introduir totes les dades!!!");
            }
        });

        panelopcions.add(continuarButton, BorderLayout.SOUTH);
        add(panelopcions);

        pack();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setVisible(true);
    }


//Getters & Setters dels atributs d'un jugador

    public String getNombreJugador() {
        return this.nombreJugador;
    }

    public void SetNombreJugador(String x) {
        this.nombreJugador = x;
    }

    public void SetSubdivisionsHorizontales(String k) {
        this.SubDivHoriz = k;
    }

    public void SetSubdivisionsVerticales(String y) {
        this.SubDivVert = y;
    }






    /*
    *Guarda els jugadors existents en el fitxer resultats en una llista a la qual afegeix el nou jugador
    * En el fitxer resultats sobreescriu els jugadors de lA llista i en acabar escriu la sentinella.
    * Si la boolean guanyat és true els punts que es guarden del nou jugador són el total
    * de les subdivisions. En cas contrari aconsegueix 0 punts.
     */


   public void GuardarDades(Boolean guanyat) {
        try {
            // LLegir els jugadors existents
            ArrayList<Jugador> jugadores = new ArrayList<>();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resultats.dat"));

            Jugador jugador = (Jugador) ois.readObject();
            while (!jugador.EsCentinela()) {
                jugadores.add(jugador);
                jugador = (Jugador) ois.readObject();
            }
            ois.close();

            // Afegir el nou jugador a la llista
            Date data = Joc.GetData();
            String inputFormat = "EEE MMM dd HH:mm:ss z yyyy";
            DateFormat inputDateFormat = new SimpleDateFormat(inputFormat, Locale.ENGLISH);
            String outputFormat = "HH:mm dd/MM/yyyy";
            DateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
            Jugador nuevoJugador;
            if (guanyat) {
                 nuevoJugador = new Jugador(GetColumnes() * GetFiles(), getNombreJugador(), outputDateFormat.format(inputDateFormat.parse(data.toString())));
            }else {
                 nuevoJugador = new Jugador(0, getNombreJugador(), outputDateFormat.format(inputDateFormat.parse(data.toString())));

            }
            jugadores.add(nuevoJugador);

            // Guardar la llista actualitzada
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resultats.dat"));
            for (Jugador j : jugadores) {
                oos.writeObject(j);
            }
            oos.writeObject(Jugador.Centinela);
            oos.close();

        } catch (IOException | ParseException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    JTextArea areaVisualitzacioResultats;


/*
 *Imprimeix pel panel l'historial d'un jugador donat.
 */

    public void ImprimirJugadors(JPanel panel, String fichero) { // imprimeix tots els jugadors d'un fitxer i els afegeix al panell "panel"
        JPanel panellHistorial = new JPanel(new BorderLayout());
        StringBuilder text = new StringBuilder();

        areaVisualitzacioResultats = new JTextArea();

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), "HISTORIAL",
                TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
        titledBorder.setTitleFont(new Font("COURIER NEW", Font.BOLD, 26));
        areaVisualitzacioResultats.setBorder(titledBorder);

        JScrollPane scrollPane = new JScrollPane(areaVisualitzacioResultats,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        try {
            FileInputStream fis = new FileInputStream(fichero);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Jugador jugador = (Jugador) ois.readObject();
            while (!jugador.EsCentinela()) {
                text.append(jugador);
                text.append("\n");
                jugador = (Jugador) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
            areaVisualitzacioResultats.setText(text.toString());
            areaVisualitzacioResultats.setFont(new Font("COURIER NEW", Font.ITALIC, 24));
            areaVisualitzacioResultats.setEditable(false);
            panellHistorial.add(scrollPane); // Agregar el JScrollPane al JPanel

            panel.add(panellHistorial);
            panel.revalidate();
            panel.repaint();


    }


    /*
    *Imprimeix pel panel l'historial d'un jugador donat.
    * Només es crida quan s'ha verificat que el jugador té historial
     */
    public void HistorialJugador(String nombreJugador, JPanel panel) {

        try{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resultats.dat"));
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("noms.dat"));

         Jugador jug = (Jugador) ois.readObject();
            while (!jug.EsCentinela()) {
                String nom = jug.getNom();
                if (nom.equals(nombreJugador)) { // copiar l'hisotirla en el fitcer noms.dat
                    oos.writeObject(jug);
                }
                jug = (Jugador) ois.readObject();
            }


            oos.writeObject(Jugador.Centinela); //marcar el fi del fitxer noms.dat
            ImprimirJugadors( panel,"noms.dat");


        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }


    }

    /*
    *Verifica si un jugador passar per parametre existeix en l'historial de jugadors.
    * Controla els erros que es poden generar
     */

  public boolean ExisteixJugador(String nombreJugador){

        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resultats.dat"));

            Jugador jugador = (Jugador) ois.readObject();

            while (!jugador.EsCentinela()){
                String nom = jugador.getNom();
                if (nom.equals(nombreJugador)){
                    return true;
                }else{
                    jugador = (Jugador) ois.readObject();
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
return false;
  }
}