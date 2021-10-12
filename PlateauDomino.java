import java.lang.IndexOutOfBoundsException;
import java.util.LinkedList;
/**
 * Ici tous les actions qui consernent le Joueurs resteront privés
 */
public class PlateauDomino extends Plateau{

    private static final long serialVersionUID = 1L;
    /**
     * Permet de savoir l'emplacement du premier domino placé t[0] = référencie l
     *  ligne du tableau t[1] = référencie la colonne du tableau
     */
    private int[] tete ={7,7};
    /**
     * Permet de savoir l'emplacement du dernier domino
     * t[0] = référencie la ligne du tableau
     * t[1] = référencie la colonne du tableau
     */
    private int[] queue ={7,7};

    private static final int X_SIZE = 15; //à modifier pour régler la taille du tableau
    private static final int Y_SIZE = 15;

    /**
     * Les joueurs sont sur le plateau et joueurCourant est l'indice dans le tableau pour savoir quel joueur joue actuellement
     */
    private Joueur[] joueurs;
    /**
     * Doit boucler entre 0-nombres de joueurs
     */
    private static int joueurCourant = 0; //reste 0 pour gommette

    /**
     * Lance une exception si le nombre de joueurs n'est pas respeter
     */
    public PlateauDomino (int nbJoueur) throws IndexOutOfBoundsException{
        super(X_SIZE,Y_SIZE);
        if(nbJoueur>=2&&nbJoueur<=4){
            //C'est après que l'on initialisera les joeurs
            this.joueurs = new Joueur [nbJoueur];
        }else throw new IndexOutOfBoundsException();
        
    }

    /**
     * Ici tournera en boucle grace au modulo les joueurs
     * @return le prochain joueurs Joueur
     */
    public Joueur getJoueursCourant(){
        int curr = joueurCourant;
        //passe au suivant
        joueurCourant = ((joueurCourant+1)%this.joueurs.length);
        return this.joueurs[curr];
    }

    public String getJoueursCourantSring(){
        return joueurs[joueurCourant].toString();
    }

    /**
     * Sera appeler dans la classe JeuDomino
     * Donne les pseudos des Joueurs et distribue les pièces selon le nombres de Joueurs
     * @param names listes des noms des Joueurs
     */
    public void initialiserJoueurs(String[] names, boolean jouerGommette){
        
        for(int i=0;i<this.joueurs.length;i++){
            this.joueurs[i] = new Joueur(names[i]);
            this.joueurs[i].distrubuerDomino(this.joueurs.length,jouerGommette);
        }
        //etape pour préciser le premier Joueurs
        if(!jouerGommette){
            for(int j =1; j<this.joueurs.length;j++){
                if(this.joueurs[j].doubleLePlusEleve()>this.joueurs[joueurCourant].doubleLePlusEleve()){
                    joueurCourant=j;
                }
            }
        }
    }


    /**
     * Private pour éviter les création extérieurs d'un Object Joeurs
     * Eviter le conflit avec d'autres classe qui pourront l'avoir.
     * Ici les composent de la classe Joueurs sont accéssibles uniquement depuis
     * La classe PlateauDomino
     */
    private class Joueur {

        private String pseudo;
        /** 
        * pièces que possèdent le joueur
        */
        private final LinkedList<Domino> possedees;

        /**
         * @param p Le pseudo du joueurs
         */
        public Joueur (String p) {
            this.pseudo = p; 
            this.possedees = new LinkedList<>();
        }


        /**
         *  @return joueur : "+this.pseudo
         */
        @Override
        public String toString () {
            return "joueur : "+this.pseudo;
        }

        /**
         * prendre au hasard dans tableau pioche 
         * et l'ajoute dans la main du Joueurs
         * @param jeuGommette decide de quel type de jeux s'agira t'il
         * @return vrai si la pioche s'est bien passé
         */
        private boolean piocherJ(boolean jeuGommette) {
            int i = (int) (Math.random()*28);
            Domino d = (jeuGommette)?Boites.piocherUnDominoGom(i): Boites.piocherUnDomino(i);
            if(d!=null){
                possedees.add(d); 
                return true;
            }
            return false;
        }


        /**
         * @return la taille de la liste possedeer de Joueur
         */
        protected int nombreDePiece(){
            return this.possedees.size();
        }

        /**
        * On remplit le tableau posssédés avec des dominos (le nombre varie en fonction du nombre de joueurs)
        * Faire attention à donner le bon nombre de pieces pour ne pas dépasser les limites du tableau
        */
        private void distrubuerDomino(int nbJoueurs,boolean jeuGommette) {
            if (nbJoueurs == 2) {
                int c = 0;
                while (c < 7) {
                    piocherJ(jeuGommette);     c++;
                }
            } else if (nbJoueurs == 3 || nbJoueurs == 4) {
                int c = 0;
                while (c < 6) {
                    piocherJ(jeuGommette);    c++;
                }
            }
        }

        /**
         * Prend une pièce à l'indice i et renvoie la pièce
         * la supprime ensuite
         */
        public Domino choisirPiece(int i) {
            Domino choix = this.possedees.get(i);
            this.possedees.remove(i);
            return choix;
        }

        /**
         * @parm d un domino quelconque
         * @parm first savoir si c'est la première posé du jeu
         * @return une liste des pièces que l'on peut placer
         */
        public LinkedList<Domino> ListedesCompatibles (Domino d,boolean first) {
            LinkedList<Domino> l = new LinkedList<>(); 
            for (int i=0; i < possedees.size(); i++) {
                if(first||d==null){
                    l.add(possedees.get(i));
                }else if (d.estCompatible(possedees.get(i))) {
                    l.add(possedees.get(i));
                }
            }
            return l;
        }
        /**
         * Surcharge de la methode ListedesCompatibles (Domino d,boolean first)
         *  ou firt = false
         * @param d 
         * @return Liste des Compatibles
         */
        public LinkedList<Domino> ListedesCompatibles (Domino d){
            return ListedesCompatibles(d,false);
        }

        /**
         * 
         * @param d un domino quelconque
         * @param first savoir si c'est la première posé du jeu
         * @return int : une liste d'entier referencant le la liste chainee
         */
        public LinkedList<Integer> ListeIndiceCompatibles (Domino d,boolean first) {
            LinkedList<Integer> l = new LinkedList<>(); 
            for (int i=0; i < possedees.size(); i++) {
                if(first||d==null){
                    l.add(i);
                }else if (d.estCompatible(possedees.get(i))) {
                    l.add(i);
                }
            }
            return l;
        }

        /**
         * @return un entier correspondant au double le plus élevé du Joueur
         */
        private int doubleLePlusEleve(){
            int max = 0;
            for(Domino d:this.possedees){
                if(d instanceof DominoDouble){
                    max =d.point();
                }
            }
            return max;
        }

        public int points () {
            int p = 0;
            for (Domino d : possedees) {
                p += d.point();
            }
            return p;
        }

    }//fin classe Joueur------------------- 

    /**
     * Sera null au premier appel
     * @return tete.p1|queue.p2
     */
    private Domino getTeteUQueue(){
        Domino dtete = (Domino) getPiece(tete[0], tete[1]);
        Domino dqueue = (Domino) getPiece(queue[0], queue[1]);
        return (dtete==null||dqueue==null)?null:dtete.unionBordABord(dqueue);
    }

    
    @Override
    public int getNbPiecesCurr(){
        return this.joueurs[joueurCourant].nombreDePiece();
    }

    /**
     * @param first : booleen lancement initiale; utiler dans la classe Joueur 
     * @return une liste de domino compatible avec l'union de Tete et queue
     */
    public LinkedList<Domino> dominoCompatiblesJCurr(boolean first){
        return this.joueurs[joueurCourant].ListedesCompatibles(getTeteUQueue(),first);
    }

    /**
    * @param first : booleen lancement initiale; utiler dans la classe Joueur 
    * @return la combinaison des indices des dominos compatibles au domino tête et au domino queue
    */
    public LinkedList<Integer> dominoCompatiblesJCurrIndice(boolean first){
        return this.joueurs[joueurCourant].ListeIndiceCompatibles(getTeteUQueue(),first);
    }

    /**
     * @param x : choix du joueur
     * @param first : boolean lancement initiale
     * @return vrai si le choix du joueur est dans la liste des dominos compatibles, faux sinon
     */
    public boolean estDansCompatbile (int x, boolean first) {
        for (int i : dominoCompatiblesJCurrIndice(first)) {
            if (x == i) {
                return true;
            }
        }
        return false;
    }

    /**
     * On suppose que le domino à l'idice i est compatible 
     * @param i prends l'indice de l'emplacement 
     */
    public void placerLaPiece (int i) {
        //on stock la pièce choisi par le joueur
        Domino d = getJoueursCourant().choisirPiece(i);
        Domino dtete = (Domino) getPiece(tete[0], tete[1]);
        Domino dqueue = (Domino) getPiece(queue[0], queue[1]);
        if(getTeteUQueue()==null){//premiere fois
            super.setPiece(queue[0], queue[1], d); //ici tete=queue
        }else{
            if (d.p2.getSymbole()==dtete.p1.getSymbole()) {
                poser (d,false);
            } else if (d.p1.getSymbole()==dqueue.p2.getSymbole()) {
                poser (d,true);
            } else {
                Domino trans = d;
                trans.inversionConditionnelle(getTeteUQueue(),d.peutEtreQueue(getTeteUQueue()));
                if (trans.p2.getSymbole()==dtete.p1.getSymbole()) {
                    poser (trans,false);
                } else if (trans.p1.getSymbole()==dqueue.p2.getSymbole()) {
                    poser (trans,true);
                }
            }
        }
    }

    /**
     * Sert de remplissage du plateau en vérifiant tout les paramètres
     * @param d
     * @param estQueue
     */
    private void poser(Domino d,boolean estQueue){
        //d.inversionConditionnelle(getTeteUQueue(), estQueue);
        if(estQueue){
            if(queue[1]+1<Y_SIZE){ //prevoir l'incrementation
                placerUnePiece(queue,'d', d);
            }else if (queue[0]+1<X_SIZE){
                placerUnePiece(queue,'b', d);
            }else{
                placerUnePiece(queue,'g', d); //danger à revoir
            }
        }else{
            if(tete[1]-1>0){
                placerUnePiece(tete,'g', d);
            }else if(tete[0]-1>0){
                placerUnePiece(tete,'h', d);
            }else{
                placerUnePiece(tete,'d', d); //danger à revoir
            }
        }
    }
    /** Outil
     * @param curseur peut être queue ou tete
     * @param direction 'h':haut 'b':bas 'g':gauche 'd':Droite
     * @param d un Domino
     */
    private void placerUnePiece(int[] curseur,char direction, Domino d){
        int x = curseur[0];
        int y = curseur[1];
        switch(direction){
            case 'h' : super.setPiece(x-1, y, d);curseur[0]--; break; //haut
            case 'b' : super.setPiece(x+1, y, d);curseur[0]++; break; //bas
            case 'g' : super.setPiece(x, y-1, d);curseur[1]--; break; //gauche
            case 'd' : super.setPiece(x, y+1, d);curseur[1]++; break; //droite
            default : System.out.println("Direction non prise en charge");
        }
    }

    @Override
    public void affichageSurTerminal(){
        for(int i=0; i<X_SIZE;i++){
            for(int j=0;j<Y_SIZE;j++){
                String p = (super.getPiece(i, j)==null)?"   ":super.getPiece(i, j).toString();
                System.out.print(p+" ");
            }
            System.out.println();
        }
    } 

    
    @Override
    public boolean piocher() {
        return getJoueursCourant().piocherJ(false);
    }

    /**
     * décide du joueur qui gagne
     * @return le joueurs ayant le plus de points si tout le monde est bloqué sinon le joueur qui ne possède plus de domino
     */
    public Joueur joueurGagnant () {
        if (impasse()) {
            Joueur max = joueurs[0];
            for (Joueur j : joueurs) {
                if (j.points() > max.points()) {
                    max = j;
                }
            }
            return max;
        } else {
            for (Joueur j : joueurs) {
                if (j.possedees.isEmpty()) {
                    return j; 
                }
            }
            return null;
        }
    }
     /**
      * @return calcule les points du joueur en fonction des domino qu'il possède
      */
    public int pointGagnant () {
        Joueur g = joueurGagnant();
        int pg = g.points();
        for (Joueur j : joueurs) {
            if (j != g) {
                pg += j.points();
            }
        }
        return pg;
    }
    /**
     * quand on est bloqué
     * @return true si aucune pièces des joueurs n'est compatible avec la tête ou la queue, false sinon 
     */
    private boolean impasse () {
        boolean b = true;
        if(getTeteUQueue()==null){
            return false;
        }
        for (Joueur j : joueurs) {
            if (!j.ListedesCompatibles(getTeteUQueue()).isEmpty()) {
                b = false;
            }
        }
        return b;
    }
    @Override public void ajouterUnListener(Panneau pan){}

}   