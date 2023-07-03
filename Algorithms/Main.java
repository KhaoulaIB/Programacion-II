public class Main {
    public void start(){
        int [] t = {23,-1,3,5,6,7};
        insercioDirecta(t);
        System.out.println();
        seleccioDirecta(t);
        System.out.println();
        bombollaMillorada(t);
        System.out.println();
        espolsada(t);

    }


    // algoritm de inserció directa:col·oca cada element en la nova [] en ordre creixent
  /*
  El mètode insercioDirecta ordena una taula de N elements emprant el mètode
 * que insereix l'element i-èssim a la seva posició de la part oredena de la
 * taula.
   */
    public static String escriureTaula(int[] t) {
        String resultat = "";
        for (int i = 0; i < t.length; i++) {
            resultat += t[i] + " ";
        }
        return resultat;
    }
    private static void insercioDirecta(int[] t) {
        final int N = t.length;
        int j;
        int x;

        for (int i = 1; i < N; i++) {
            x = t[i];
            j = i - 1;
            while ((j >= 0) && (x < t[j])) {
                t[j + 1] = t[j]; // Moure l'element cap a la dreta
                j--;
            }
            //j és el primer o és menor que l'i-èssim
            t[j + 1] = x;	 // inserir el valor de l'element  i-èssim
            System.out.println("Passada " + i + ": " + escriureTaula(t));
        }
    }

 // -----------------------------------------Selccio directa-------------------
    /*


 * Selecció directa que es basa en fer N-1 passades i a cada passada cerca
 * l'element més petit i el posa a la posició i-èssima, basten N-1 passades
 * per que el darrer element ja estarà ordenat.
     */

    private static void seleccioDirecta(int[] t) {
        final int N = t.length;
        int jmin;
        int min;

        for (int i = 0; i < N - 1; i++) {
            min = t[i];
            jmin = i;
            for (int j = i + 1; j < N; j++) {
                if (t[j] < min) {
                    min = t[j];
                    jmin = j;
                }
            }
            t[jmin] = t[i];
            t[i] = min;
            System.out.println("Passada " + i + ": " + escriureTaula(t));
        }
    }

    //-------------------------Bombolla/intercanvi directa-----------------

    /*
    Ordenació de taules amb el mètode de la bombolla millorada.
 * Millora la bombolla de manera que quan es detecta que no s'ha canviat cap
 * element l'algorisme acaba.
 * En lloc d'incrementar un a un l'index i usa una variable lj (last j) que
 * guarda la posició del darrer intercamvi, quan se'n produeix almenys un j=i
 * si no n'hi ha cap lj=N i acaba la repetició.
     */

    private static void bombollaMillorada(int[] t) {
        final int N = t.length;
        int x;
        int i = 0;
        while (i < N - 1) {
            int lj = N;
            for (int j = N - 2; j >= i; j--) {
                if (t[j + 1] < t[j]) {
                    x = t[j + 1];
                    t[j + 1] = t[j];
                    t[j] = x;
                    lj = j;
                }
            }
            i = lj + 1;
            System.out.println("Índex " + i + ": " + escriureTaula(t));
        }
    }


    //------------------------AlgorismeDeLEspolsada-------------
    /*
    *Es una mejora del algoritmo de burbuja en el que se registra la ocurrencia de un intercambio y
    * el índice del último intercambio y se alterna la dirección de las pasadas consecutivas.
    * Se plenteja com un metode de doble bombolla
    * *
    * Ordenació de taules amb el mètode de la espolçada.
     * Aquest mètode du a terme dues bombolles millorades (una en cada sentit) per a
     * cada passada. Usa dos índex esq i dre que quan es trobin acabarà l'algorisme.
     * S'actualitzen amb una variable lj similar a la que s'usa a l'algorisme de
     * la bombolla millorada i que detecta les posicions on hi hagut l'intercanvi.
    *
     */

    private static void espolsada(int [] t) {
        final int N = t.length;
        int x;
        int esq = 0, dre = N - 1;
        while (esq < dre) {
            int lj = dre;
            for (int j = dre - 1; j >= esq; j--) {
                if (t[j + 1] < t[j]) {
                    x = t[j + 1];
                    t[j + 1] = t[j];
                    t[j] = x;
                    lj = j;
                }
            }
            esq = lj + 1;
            for (int j = esq; j <= dre - 1; j++) {
                if (t[j + 1] < t[j]) {
                    x = t[j + 1];
                    t[j + 1] = t[j];
                    t[j] = x;
                    lj = j + 1;
                }
            }
            dre = lj - 1;
            System.out.println("dre = " + dre + " esq = " + esq +
                    ": " + escriureTaula(t));
        }
    }

    //---------------------Cerca_Dicotomica------------------------------
    /*
    Exemple de la cerca dicotòmica dins TAULES ORDENADES.
 *
 * S'usen dos índex que indiquen els límits superior i inferior de la taula es
 * calcula el punt intermig i si és l'element que cercam ja està si no
 * actualitzam el índex depenents is l'element a cercar és menor o major que el
 * punt intermig, si és menor ens quedam amb la part baixa de la taula i si és
 * major en la part alta.
     */

    private static int cercaDicotomica(int[] t, int x) {
        int inf = 0, sup = t.length - 1;
        int mig = (sup + inf) / 2;
        while ((inf < sup) && (t[mig] != x)) {
            if (t[mig] < x) {
                inf = mig + 1;
            } else {
                sup = mig - 1;
            }
            mig = (sup + inf) / 2;
        }
        return (t[mig] == x ? mig : -1); //  l'operador ternari ? :  retorna la posició de l'element x en l'array t si aquest es troba
                                            // a l'array, o bé -1 si l'element no està present.


    }
    public static void main(String[] args) { (new Main()).start();

    }
}