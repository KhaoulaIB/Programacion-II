
package KI.Puzzle;
/*
 *Autora: Khaoula Ikkene
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.*;
import java.util.Random;

public class SubImagen extends  JPanel {
    JPanel panellPartida = new PanelSubImatges();

   private static int filas, columnas;

    public static BufferedImage image;

    public static void SetFilas(int f){
        filas = f;
    }

    public static void SetColumnas(int c){
        columnas = c;
    }

    public static int GetFilas (){
        return filas;
    }
    public static int GetColumnas(){
        return columnas;
    }

    private static BufferedImage[] imgs;

    private static BufferedImage []imagenesPuzzleInicial;
    public static void setImgs(BufferedImage[] imagenes){
        imagenesPuzzleInicial = imagenes;
    }

    public static BufferedImage [] GetImgs(){
        return imagenesPuzzleInicial;
    }


    /*
     *Metode que selecciona alteatoriement una imatge d'un direcotiri que es passa per parametre
     */
    private BufferedImage ImagenAleatoria(String directorio) {
        String RutaImagen = "";
        File folder = new File(directorio);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null && files.length > 0) {
                Random rand = new Random();
                int randomIndex = rand.nextInt(files.length);
                File randomFile = files[randomIndex];
                RutaImagen += randomFile;
            } else {
                System.out.println("El directorio está vacío.");//clase terminal
            }
        } else {
            System.out.println("El directorio no existe o no es válido.");
        }

        try {
            return ImageIO.read(new File(RutaImagen));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    /*
    *Mostra pel panel de PanelVisualisations la iamtge subdividida i reordenada del puzzle
    *
     */
    public void ImagePartida(String directorio, JPanel PanelVisualisations, JProgressBar pbar) throws IOException {
         try {
             image = ImagenAleatoria(directorio);
             if (image != null) {
                 Image imageescalada = image.getScaledInstance(1002, 667, Image.SCALE_DEFAULT);
                 BufferedImage imagenfinal = new BufferedImage(imageescalada.getWidth(null), imageescalada.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                 SetImatge(imagenfinal);
                 Graphics2D graphics = imagenfinal.createGraphics();
                 graphics.drawImage(imageescalada, 0, 0, null);
                 graphics.dispose();
                 imgs = subdividirImagen(image);
                 crearPanelPartida(GetFilas(), GetColumnas(), PanelVisualisations, pbar);
             }else{
                 image = ImagenAleatoria(directorio);
             }
         } catch(NullPointerException | ImagingOpException ex){
            System.out.println(ex.getMessage());

        }
    }


  /*
  *Getter i Setter de la imatge del joc
   */
    public static void SetImatge(BufferedImage foto){
        image = foto;
    }

    public static  BufferedImage GetImatgeSolucio(){
        return image;
    }




    /*
    *Algoritm que permet dividir la imatge en n parts iguals, sent n el numero de subdivisions
    * o bé n = Numero de files* Numero de columnes
     */

    private BufferedImage[] subdividirImagen(BufferedImage image) {
        int rows = GetFilas();
        int colums = GetColumnas();
        int subimage_Width = (image.getWidth()) / colums;
        int subimage_Height = (image.getHeight()) / rows;

      imgs = new BufferedImage[rows * colums];
        int current_img = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                imgs[current_img] = new BufferedImage(subimage_Width, subimage_Height, image.getType());
                Graphics2D img_creator = imgs[current_img].createGraphics();
                img_creator.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

                int src_first_x = j * subimage_Width;
                int src_first_y = i * subimage_Height;
                int dst_corner_x = src_first_x + subimage_Width;
                int dst_corner_y = src_first_y + subimage_Height;

                img_creator.drawImage(image, 0, 0, subimage_Width, subimage_Height, src_first_x, src_first_y,
                        dst_corner_x, dst_corner_y, null);
                current_img++;
            }
        }
        imagenesPuzzleInicial = imgs.clone();

        setImgs(imgs.clone()); // imgs incials sense canviar l'ordre dels subimatges
        return imgs;


    }
    public static  BufferedImage[] currentImgs;




    /*
    * Getter i Setter de l'array de les subimatges modificades pel jugador
     */
    public static  BufferedImage[] GetpuzzleJugador(){
        return currentImgs;
    }

    public static void SetpuzzleJugador (BufferedImage [] i){
        currentImgs = i;
    }


    /*
    *Metode que reordena aleatoriament les subimatges de la imatge inicial
     */
    private void reordenarSubiamges() {
        int n = imgs.length;
        Random random = new Random();

        for (int i = 0; i < n - 1; i++) {
            int j = i + random.nextInt(n - i);
            BufferedImage temp = imgs[j];
            imgs[j] = imgs[i];
            imgs[i] = temp;
        }
        SetpuzzleJugador(imgs);
    }

    private JLabel selectedLabel = null;

    /*
    +Metode que col·loca les subimatges en el panell de la partida i gestiona el moviment d'aquestes subimatges
     */
    private void crearPanelPartida(int filas, int columnas, JPanel PanelVisualisations, JProgressBar barporg) {
        panellPartida = new JPanel(new GridLayout(filas, columnas, 1, 1)); // Uso de GridLayout personalizado
        JLabel[] labels = new JLabel[filas * columnas];
        boolean[] l1Clicked = new boolean[filas * columnas];
         currentImgs = new BufferedImage[filas * columnas];

        reordenarSubiamges();
        for (int i = 0; i < filas * columnas; i++) {
            labels[i] = new JLabel(new ImageIcon(imgs[i]));
            labels[i].setPreferredSize(new Dimension(imgs[0].getWidth(), imgs[0].getWidth()));
            l1Clicked[i] = false;
            currentImgs[i] = imgs[i];

            labels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    JLabel targetLabel = (JLabel) e.getSource();

                    if (selectedLabel == null) {
                        selectedLabel = targetLabel;
                        selectedLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                    } else {
                        JLabel finalSelectedLabel = selectedLabel;
                        selectedLabel.setBorder(BorderFactory.createEmptyBorder());

                        BufferedImage selectedImg = currentImgs[panellPartida.getComponentZOrder(finalSelectedLabel)];
                        BufferedImage targetImg = currentImgs[panellPartida.getComponentZOrder(targetLabel)];

                        currentImgs[panellPartida.getComponentZOrder(finalSelectedLabel)] = targetImg;
                        currentImgs[panellPartida.getComponentZOrder(targetLabel)] = selectedImg;

                        finalSelectedLabel.setIcon(new ImageIcon(targetImg));
                        targetLabel.setIcon(new ImageIcon(selectedImg));

                        selectedLabel = null; // reiniciar selectedLabel
                    }
                }
            });
            panellPartida.add(labels[i]);
            SetpuzzleJugador(currentImgs);
        }

        PanelVisualisations.removeAll();
        PanelVisualisations.add(panellPartida, BorderLayout.CENTER);
        PanelVisualisations.add(barporg, BorderLayout.SOUTH);
        PanelVisualisations.revalidate();
        PanelVisualisations.repaint();
    }


    /*
    *Verifica si el jugador ha guanyat, es a dir, ha pogut solucion el puzzle
     */
    public static boolean EstaSolucionada() {
        for (int i = 0; i < columnas * filas; i++) {
            if (!GetpuzzleJugador()[i].equals(GetImgs()[i])) {

                return false;
            }
        }

        return true;
    }



}
