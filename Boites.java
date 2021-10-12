import java.util.ArrayList;

/**
 * Ici ser initialiser toutes les types de pièces requis dans le jeu
 * les objects seront privates et statics 
 * et des accesseur nous permettront d'interagir avec les Listes de pièces
 */

public abstract class Boites{

    /**
     * Permet de garder une Piece en souvenir lors des appels aux Listeners
     */
    private static Piece garder = null;
    
    /**
     * Des boites contenant les dominos et cartes
     */
    private static ArrayList<Domino> BoitesADomino = new ArrayList<>();
    private static ArrayList<DominoGommette> BoitesAdominoGommette = new ArrayList<>();
    private static ArrayList<Carte> PaquetDeCartes = new ArrayList<>();
    private static ArrayList<PuzzlePiece> paquetDePuzzle = new ArrayList<>();
    /**
     * permet de lancer automatiquement l'initialisation si 
     * elle n'est pas encore faire
     */
    private static boolean iniDomino = false;
    private static boolean iniDominoGommette = false;
    private static boolean iniSaboteur = false;
    private static boolean iniPuzzle = false;

    public static void remettreAZero(){
        iniDomino = false;
        iniDominoGommette = false;
        iniSaboteur = false;
        iniPuzzle = false;
        garder = null;
    }

    /**
     * 
     * @param p Piece
     */
    public static void enregistrer(Piece p){   garder = p;  }

    public static Piece retirerPieceEnregistrer(){
        Piece p = garder; garder=null;
        return p;
    }

    public static boolean aUnePieceENregistre(){
        return (garder!=null);
    }

    public static ArrayList<Carte> getPaquetDeCartes () {
        return PaquetDeCartes;
    }


    /**
     * créations de toutes les pièces de la boites à Domino
     * elle est privée pour éviter toute abus
     */
    private static void initilisationDomino(){
        for(int i=0;i<=6;i++){
            //plus i augmente plus le nombre de domino creer à chaque 
            //boucle diminu pour éviter les doublons ex: 1|2 et 2|1
            //total = 28 pièces de dominos
            BoitesADomino.add(new DominoDouble(i));
            if(i>0){BoitesADomino.add(new DominoSimple(0,i));}
            if(i>1){BoitesADomino.add(new DominoSimple(1,i));}
            if(i>2){BoitesADomino.add(new DominoSimple(2,i));}
            if(i>3){BoitesADomino.add(new DominoSimple(3,i));}
            if(i>4){BoitesADomino.add(new DominoSimple(4,i));}
            if(i>5){BoitesADomino.add(new DominoSimple(5,i));}
        }
        iniDomino = true; //la boite domino est pre
    }

    
    private static void initialisationDominoGom() {
        // On doit avoir au moins 4 domino avec une combinaison (ex : un cercle jaune doit apparaître 4 fois)
        char[] T = {'C','E','L','O'}; //Formes
        char[] C = {'b','j','r','v'}; //Couleurs
        /**
         * Nombre total de domino fournie 32
         * 16 motif différent T[]*C[] 
         * Qui apparaissent 4 fois
         * (16*4)/2 = 32  "/2"=>[p1|p2]
         */
        for(int i=0;i<4;i++){
            BoitesAdominoGommette.add(new DominoGommette(T[1],C[i],T[1],C[(i+1)%4]));
            BoitesAdominoGommette.add(new DominoGommette(T[2],C[i],T[2],C[(i+1)%4]));
            for(int j=0;j<3;j++){
                if(i==0){
                    BoitesAdominoGommette.add(new DominoGommette(T[0],C[i],T[j],C[(i+1)%3]));
                    BoitesAdominoGommette.add(new DominoGommette(T[3],C[i],T[j],C[(i+1)%3]));
                }else{
                    BoitesAdominoGommette.add(new DominoGommette(T[0],C[i],T[j],C[i]));
                    BoitesAdominoGommette.add(new DominoGommette(T[3],C[i],T[j],C[i]));
                }
            }
        }
        iniDominoGommette = true; 
    }

/**
     * Création de toutes les cartes de la pioche de saboteur
     */
    private static void initialisationSaboteur () {
        // On ajoute d'abord les cartes actions
        for (int i=0; i < 3; i++) {
            PaquetDeCartes.add(new CarteAction('O',true));
            PaquetDeCartes.add(new CarteAction('T',true));
            PaquetDeCartes.add(new CarteAction('E',true));
            PaquetDeCartes.add(new CarteAction('o',false));
            PaquetDeCartes.add(new CarteAction('t',false));
            PaquetDeCartes.add(new CarteAction('e',false));
            // Puis on ajoute les différents types de cartes
        }
        for (int i=0; i < 6; i++) {
            PaquetDeCartes.add(new CarteTrajet(true,true,true,true,'+'));
            PaquetDeCartes.add(new CarteTrajet(true,true,true,false,'a'));
            PaquetDeCartes.add(new CarteTrajet(false,true,true,true,'b'));
        }
        for (int i=0; i < 7; i++) {
            PaquetDeCartes.add(new CarteTrajet(true,true,false,false,'-'));
            PaquetDeCartes.add(new CarteTrajet(false,false,true,true,'|'));
            PaquetDeCartes.add(new CarteTrajet(false,true,false,true,'c'));
        }
        for (int i=0; i < 5; i++) {
            PaquetDeCartes.add(new CarteTrajet(true,false,false,false,'d'));
        }
        iniSaboteur = true;
    }  

    /**
     * 
     * @param i attends un chiffre aléatoire pour avoir un domino aléatoire
     * @return Objects : DominoSimple || DominoDouble
     */
    public static Domino piocherUnDomino(int i){
        if(!iniDomino){
            initilisationDomino();//à la fin iniDomino sera true
        }
        Domino res =null;
        try{
            //pour ne pas se préocuper de la taille de la liste
            res = BoitesADomino.get((i%BoitesADomino.size()));
            BoitesADomino.remove(i%BoitesADomino.size());
            
        }catch(ArithmeticException e){
            
        }
        return res;
    }

    /**
     * @param i un chiffre aleatoire pour que la pioche ne soit pas toujours le meme
     * @return un objet Carte
     */
    public static Carte piocherUneCarte (int i) {
        if (!iniSaboteur) {
            initialisationSaboteur();
        }
        Carte d = null;
        try {
            d = PaquetDeCartes.get(i%PaquetDeCartes.size());
            PaquetDeCartes.remove(i%PaquetDeCartes.size());
        } catch (ArithmeticException e) {
            System.out.println("Pioche vide");
        }
        return d;
    }

    /**
     * Rempli la boite de domino
     * @return vrai si les conditions respectées
     */
    public static boolean reinitialiserDomino(){
        if(BoitesADomino.isEmpty()){
            initilisationDomino();
            return true;
        }
        return false;
    }
    /**
     * Utile de faire pour la relance d'un jeux
     * Appel l'une des fonctions de réinitialisation ensuite
     */
    public static void viderLesBoites(){
        BoitesADomino = new ArrayList<>();
        BoitesAdominoGommette = new ArrayList<>();
        PaquetDeCartes = new ArrayList<>();
        paquetDePuzzle = new ArrayList<>();
    }
    /**
     * 
     * @param i un chiffre aleatoire pour que la pioche ne soit pas toujours le meme
     * @return un objet DominoGommette
     */
    public static DominoGommette piocherUnDominoGom(int i){
        if(!iniDominoGommette){
            initialisationDominoGom();//à la fin iniDomino sera true
        }
        DominoGommette res =null;
        try{
            //pour ne pas se préocuper de la taille de la liste
            res = BoitesAdominoGommette.get((i%BoitesAdominoGommette.size()));
            BoitesAdominoGommette.remove(i%BoitesAdominoGommette.size());
            
        }catch(ArithmeticException e){
        }
        return res;
    }

    /**
     * Rempli la boite de domino
     * @return vrai si les conditions respectées
     */
    public static boolean reinitialiserDominoGom(){
        if(BoitesAdominoGommette.isEmpty()){
            initialisationDominoGom();
            return true;
        }
        return false;
    }

    /**
     * Rempli le paquet de cartes
     * @return vrai si les conditions respectées
     */
    public static boolean reinitialiserSaboteur(){
        if(PaquetDeCartes.isEmpty()){
            initialisationSaboteur();;
            return true;
        }
        return false;
    }

    /**
     * 
     * @param width largeur x->
     * @param height hauteur y->
     */
    private static void initialisationPuzzle(int width, int height){
        for(int i=0; i<width;i++){
            for(int j=0;j<height;j++){
                paquetDePuzzle.add(new PuzzlePiece(i,j,(i==0),(i==width-1),(j==0),(j==height-1))); 
            }
        }
    }

    /**
     * 
     * @param rand de preferance un chiffre aleatoire
     * @return PuzzlePiece
     */
    public static PuzzlePiece piocherPuzzle(int rand){
        
        if(!iniPuzzle){
            initialisationPuzzle(PlateauPuzzle.X_SIZE,PlateauPuzzle.Y_SIZE);
            iniPuzzle=true;
        }
        try{
            rand = rand%paquetDePuzzle.size();
            if(!paquetDePuzzle.isEmpty()){
                PuzzlePiece save = paquetDePuzzle.get(rand);
                paquetDePuzzle.remove(rand);
                return save;
            }
        }catch (ArithmeticException e){}
            
        return null;
    } 
    
}
