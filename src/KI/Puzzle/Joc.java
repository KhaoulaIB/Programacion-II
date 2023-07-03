package KI.Puzzle;

/*
 *Autora: Khaoula Ikkene
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Objects;



public class Joc extends JFrame {
    private static Date data;

    private String directorio = "imatgesJOC"; //directori per defecte

    private JButton BOTONCONTINUAR;

    private JProgressBar barraTemporal;
    private Timer cronometro; // genera elements de Timer cada x temps
    private JMenu Menu;
    private JMenuBar barraMenu;

    private JToolBar iconesMenu;
    private final String[] Botones = {"NUEVA PARTIDA", "CLASIFICACION GENERAL", "HISTORIAL", "SALIR"};

    private final JButton[] botonesPanel = new JButton[4];
    private final String[] menuBarvariables = {"NUEVA PARTIDA", "CLASIFICACION GENERAL", "HISTORIAL", "CAMBIAR DIRECTORIO DE IMAGENES", "SALIR"};
    private final String[] SourceIcons = {"iconoNuevaPartida.jpg", "iconoHistorialGeneral.jpg", "iconoHistorialSelectivo.jpg", "iconoCambiarDIrectorio.jpg","iconoSalir.jpg"};

    private final String[] ICONES = {"novaPartidaIcona", "classificacioIcona", "historialIcona", "canviarDirectoriIcona", "sortirIcona"};
    private final char[] Mnemonics = {'N', 'C', 'H', 'C', 'S'};
    private final JButton[] botonesiconos = new JButton[5];

    private final JMenuItem[] menuBarbotonese = new JMenuItem[5];
    private JPanel PanelBottons, PanelPrincipal, PanelVisualisations;


    JSplitPane SplitPane1, SplitPane2, SplitPane3;
    Container panellContinuguts = this.getContentPane();

    private Partida partida = new Partida();

    /*
    *Constructor de la classe
    * Mostra per pantalla la finestra del joc
     */
   public Joc() { //constructor

        PanelBottons = new JPanel(new GridLayout(4, 1)); //panell de botones
        setTitle("PRÁCTICA-PROGRAMACIÓ II-2022-2023-KI-UIB");//títol del frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//
        setSize(1200, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("logo.jpg");
        // establició del menu, bottons, splitters i iconos en el frame
        SetbotonesMenu(this);
        SetPanelBottons();
        SetUibImage();
        Splitters();
        ICONOS();

    }

    /*
     *Metode que col·loca els botons del menu en la finestra i els assigna un action Listener : gestorEevnts
     *
     */
    public void SetbotonesMenu(JFrame f) {
        barraMenu = new JMenuBar();
        Menu = new JMenu("MENÚ");
        Menu.setForeground(Color.WHITE);
        barraMenu.add(Menu);
        barraMenu.setBackground(Color.black);
        for (int i = 0; i < menuBarvariables.length; i++) {
            menuBarbotonese[i] = new JMenuItem(menuBarvariables[i]); // assignar noms als botons del menu
            menuBarbotonese[i].setMnemonic(Mnemonics[i]); // assignar els mnemonics als botons del menu
            menuBarbotonese[i].addActionListener(gestorEvents); // assignar accions als botons
            Menu.add(menuBarbotonese[i]);
        }
        f.setJMenuBar(barraMenu);

    }


    /*
     * Metode que col·loca les icones en la finestra i les assigna un action Listener : gestorEevnts
     *
     */

    public void ICONOS() {
        iconesMenu = new JToolBar();
        iconesMenu.setBackground(Color.BLACK);
        iconesMenu.setFloatable(false);  // menu d'icnones no flotant
        for (int i = 0; i < botonesiconos.length; i++) {
            botonesiconos[i] = new JButton(ICONES[i]); // assingar noms als iconos
            botonesiconos[i].setBackground(Color.black);
            botonesiconos[i] = new JButton(new ImageIcon("iconos/"+SourceIcons[i]));// afegir les iconoes
            botonesiconos[i].setActionCommand(ICONES[i]); //assignar noms de command als iconoes
            botonesiconos[i].addActionListener(gestorEvents);
            iconesMenu.add(botonesiconos[i]);
        }
        panellContinuguts.add(iconesMenu, BorderLayout.NORTH);


    }


    public void SetPanelBottons() {
        PanelBottons = new JPanel(new GridLayout(4, 1)); //panel de botones
        for (int i = 0; i < Botones.length; i++) {
            botonesPanel[i] = new JButton(Botones[i]);
            botonesPanel[i].setBackground(Color.black);
            botonesPanel[i].setForeground(Color.BLUE);
            botonesPanel[i].setFont(new Font("Calibri", Font.BOLD, 15));
            botonesPanel[i].addActionListener(gestorEvents);
            PanelBottons.add(botonesPanel[i]);

        }
        panellContinuguts.add(PanelBottons, BorderLayout.WEST);
    }

    /*
     * Afegeix la imatge de fons a la finestra del joc
     */
    public void SetUibImage (){//Posar logo de uib
        PanelVisualisations = new JPanel(new BorderLayout());
        JLabel imagenUIB = new JLabel(new ImageIcon("imagenes/UIB.jpg"));
        PanelVisualisations.add(imagenUIB, BorderLayout.CENTER);
        panellContinuguts.add(PanelVisualisations, BorderLayout.EAST);
        PanelVisualisations.setBackground(Color.black);
    }

    /*
     *Crea 3 Splitters
     * SplitPane1-> separa horitzontalment el menu d'icones i el PanelVisualisations
     * SplitPane2-> separa verticalment el PanelBottons i el PanelVisualisations
     * SplitPane2-> separa horitzontalment el PanelPrincipal i el fi de la finestra
     */

    public void Splitters() {
        PanelPrincipal = new JPanel(new BorderLayout());

        SplitPane1 = createSplitPane(JSplitPane.VERTICAL_SPLIT, iconesMenu, PanelVisualisations);
        SplitPane2 = createSplitPane(JSplitPane.HORIZONTAL_SPLIT, PanelBottons, PanelVisualisations);
        SplitPane3 = createSplitPane(JSplitPane.VERTICAL_SPLIT, PanelPrincipal, null);

        panellContinuguts.add(SplitPane1);
        panellContinuguts.add(SplitPane2, BorderLayout.CENTER);
        panellContinuguts.add(SplitPane3, BorderLayout.SOUTH);
    }

    /*
    * Crea un SpliPane en base de la seva orientació i els elements que ha de separar
     */
    private JSplitPane createSplitPane(int orientation, Component leftComponent, Component rightComponent) {
        JSplitPane splitPane = new JSplitPane(orientation, leftComponent, rightComponent);
        splitPane.setDividerSize(8);
        splitPane.setOneTouchExpandable(false);
        return splitPane;
    }

    //Funcionalitats

    /*
    *Metode que permet fer el canvi de directori
     */
    public void CanviarDirectori() {
        try {
            String directorioPrograma = System.getProperty("user.dir"); // Ruta del directorio del programa
            JFileChooser ventanaSeleccion = new JFileChooser(directorioPrograma); // Crear un quadre de diàleg per seleccionar el directori
            ventanaSeleccion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Establir el mode de selecció només a directoris

            int op = ventanaSeleccion.showOpenDialog(this); // Mostrar el quadre de diàleg i esperar que l'usuari seleccioni una opció
            if (op == JFileChooser.APPROVE_OPTION) { // Si l'usuari selecciona "Aceptar"
                // Obtenir el directori seleccionat i establir-lo com el nou directori
                directorio = ventanaSeleccion.getSelectedFile().getAbsolutePath();
                ventanaInformativa("S'ha fet correctament el canvi de directori!");
            }

        } catch (NullPointerException e) {
            ventanaInformativa("Error: la selección del directorio es nula.");
        } catch (Exception e) {
            // Manejar otros errores inesperados
            System.err.println("Error inesperado al seleccionar el directorio.");
            System.out.println(e.getMessage());
        }
    }




    /*
    *Col·loca el boto Continuar en el panel que se'l passa per parametre
    * i le afegeix un action Listener encarregat de restablir el fons.
     */


    public void SetBotoContinuar(JPanel panel){
        BOTONCONTINUAR = new JButton("CONTINUAR");
        BOTONCONTINUAR.addActionListener(e -> {
            if (Objects.equals(e.getActionCommand(), "CONTINUAR")){
                RestablirFons();
            }
        });
        panel.add(BOTONCONTINUAR, BorderLayout.SOUTH);
    }

    /*
    *Mostra pel PanelVisualisations la imatge solucio del joc
     */

    public void ImatgeSolucio(){
        BufferedImage imatge = SubImagen.GetImatgeSolucio();
        JLabel label = new JLabel(new ImageIcon(imatge));
        JPanel panellImatgeSolucio = new JPanel(new BorderLayout());
        panellImatgeSolucio.add(label,BorderLayout.CENTER);
        SetBotoContinuar(panellImatgeSolucio);
        PanelVisualisations.removeAll();
        PanelVisualisations.add(panellImatgeSolucio);

        PanelVisualisations.revalidate(); // Vuelve a validar el panel
        PanelVisualisations.repaint(); // Vuelve a pintar el panel


    }

    public static Date GetData(){
        return data;
    }

    /*
    *ActionListener que controla el funcionamente de al barra de progres
    * Inicialitza el cronometre i crea una nova instancia de data que representa al data de començament del joc
    * Guarda les dades del jugadors
     */
    ActionListener gestorEventoTimer = new ActionListener() {
        //TRATAMIENTO EVENTO
        @Override
        public void actionPerformed(ActionEvent evento) {

            if (Partida.GetFiles()!=0){
                cronometro = Partida.GetCronometro();
                cronometro.start();
                GameOn = true;
                data = new Date(); // grabar la data de començament del joc
                PanelVisualisations.add(barraTemporal, BorderLayout.SOUTH);
            }
            if (evento.getSource() == cronometro) {
                int valor = barraTemporal.getValue();
                int valorMaximo = 100;
                if (valor < valorMaximo) {
                    barraTemporal.setValue(++valor);
                    boolean subImagenIsNull = SubImagen.GetImgs() == null;
                    if (!subImagenIsNull){
                        if ((SubImagen.EstaSolucionada())) {

                            GameOn = false;
                            cronometro.stop();
                            ventanaInformativa("HAS GUANYAT " + SubImagen.GetpuzzleJugador().length + " PUNTS!!");
                            partida.GuardarDades(true);
                            ImatgeSolucio();

                        }
                    }
                } else  {
                    ventanaInformativa("HAS PERDUT  \n" + " SORT EN EL PROXIM INTENT");
                    cronometro.stop();
                    ImatgeSolucio();
                    partida.GuardarDades(false);
                    GameOn = false;



                }
            }
        }
    };


    /*
    * Crea la barra temporal, instancia de la classe BarProgress
     */

    public void CrearBarra(){
        BarProgress barprog = new BarProgress();
        barraTemporal = new JProgressBar();
        barraTemporal= barprog.barratemporal();
    }


    /*
    *Resatbleix el fons del PanelVisualisations;Hi col·loca el logo de la UIB
     */
    public void RestablirFons(){
        JLabel imagenUIB=new JLabel(new ImageIcon("imagenes/UIB.jpg"));
        PanelVisualisations.removeAll();
        PanelVisualisations.add(imagenUIB, BorderLayout.CENTER);
        PanelVisualisations.revalidate();
        PanelVisualisations.repaint();
    }

    /*
    *Variable booleana que determina quin tractament tindrán les funcionalitats del joc
     */
    boolean GameOn = false;
    ActionListener gestorEvents = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evento) {
            if (!GameOn) {
                handleGameOffEvent(evento.getActionCommand()); // tractament normal de botons
            } else {
                handleGameOnEvent(evento.getActionCommand()); // tractament de botons durant la partida
            }
        }
    };


    /*
    * Metode que assigna funcionalitst als botons, a menuitems i a les icones. Els elements que
    * realitzen la mateixa funcionalitat s'han agrupat junts
     */

    private void handleGameOffEvent(String actionCommand) {
        switch (actionCommand) {
            case "NUEVA PARTIDA", "novaPartidaIcona" -> {
                RestablirFons();
                CrearBarra();
                partida = new Partida(directorio, PanelVisualisations, barraTemporal, gestorEventoTimer);
            }
            case "CLASIFICACION GENERAL", "classificacioIcona" -> {
                PanelVisualisations.removeAll();
                partida.ImprimirJugadors(PanelVisualisations, "resultats.dat");
            }
            case "SALIR", "sortirIcona" -> System.exit(0);
            case "CAMBIAR DIRECTORIO DE IMAGENES", "canviarDirectoriIcona" -> CanviarDirectori();
            case "HISTORIAL", "historialIcona" -> {
                try {
                    String jugador = GetUsuarioVentana().toUpperCase();
                    if (!partida.ExisteixJugador(jugador)) {
                        ventanaInformativa("EL JUGADOR NO EXISTEIX");
                        RestablirFons();
                    } else {
                        PanelVisualisations.removeAll();
                        partida.HistorialJugador(jugador, PanelVisualisations);
                    }
                } catch (NullPointerException ex) {
                    // Quan l'usuari ha cancelat l'ocpció
                }
            }

        }
    }

    private void handleGameOnEvent(String actionCommand) {
        switch (actionCommand) {
            case "SALIR", "sortirIcona" -> System.exit(0);
            default -> ventanaInformativa("HAS D'ACABAR LA PARTIDA PRIMER!!!");
        }
    }




    /*
    *metodes que permiten mostrar per la finestra una informacio donada
    * El metode GetUsuarioVentana serveix per demanar el nom del jugador que es desitja consultar el seu historial
     */
    public static void ventanaInformativa(String informacion) {
        setCustomUIManager();
        JOptionPane.showMessageDialog(null, informacion);
    }

    public String GetUsuarioVentana() {
        setCustomUIManager();
        return JOptionPane.showInputDialog("HISTORIAL D'USUARI\nUSUARI?");
    }

    private static void setCustomUIManager() {
        UIManager.put("Panel.background", Color.BLACK);
        UIManager.put("OptionPane.background", Color.BLACK);
        UIManager.put("OptionPane.messageFont", new Font("GEORGIA", Font.BOLD, 14));
        UIManager.put("OptionPane.messageForeground", Color.YELLOW);
    }


}
