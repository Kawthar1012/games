import java.util.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.lang.*;
public class PlateauSaboteur extends Plateau {

    private Joueur [] joueurs;

    /**
     * null au départ, devient le joueur courant quand quelqu'un gagne
     */
    private Joueur joueurGagnant;


    /**
     * Joueur actuel qui est le premier pour ce jeu (on ne sélectionne pas de joueur particulier)
     */
    private int JoueurCourant=0;

    private static final int X_SIZE = 15; //à modifier pour régler la taille du tableau
    private static final int Y_SIZE = 15;

    /**
     * On obtient le nombre de joueurs grâce à une fonction plus bas puis on donne ses dimensions
     */
    public PlateauSaboteur (int nbJoueurs) {
        super(X_SIZE,Y_SIZE);
        if (nbJoueurs >= 3 && nbJoueurs <= 8) {
            this.joueurs = new Joueur [nbJoueurs]; 
        }
        setBounds(0,0,Jeux.LARGEUR*9/16,Jeux.LONGUEUR);
    }

    /**
     * Passe au joueur suivant
     * Ici tournera en boucle grace au modulo les joueurs
     * @return le joueur courant
     */
    public Joueur getJCourant () {
        int curr = JoueurCourant;
        //passe au suivant
        JoueurCourant = ((JoueurCourant+1)%this.joueurs.length);
        return this.joueurs[curr];
    }

    /**
     * @return le joueur actuel
     */
    public Joueur getJoueursCourant() {
        int c = JoueurCourant;
        return this.joueurs[c];
    }

    /**
     * Affiche le joueur courant
     */
    public String getJString () {
        return this.joueurs[JoueurCourant].toString();
    }

    public String getJoueursCourantString () {
        return getJCourant().toString();
    }

    public Joueur getThisJoueur (int i) {
        return joueurs[i];
    }

    public Joueur getJoueurGagnant () {
        return joueurGagnant;
    }

    public void setJSuiv (int j) {
        JoueurCourant = j;
    }

    public int getCour () {
        return JoueurCourant;
    }

    public Joueur [] getJoueurs () {
        return joueurs;
    }

    public int joueursLength () {
        return joueurs.length;
    }


    /**
     * Sera appeler dans la classe JeuSaboteur
     * Donne les pseudos des Joueurs et distribue les pièces selon le nombres de Joueurs
     * @param names listes des noms des Joueurs
     */
    public void initialiserJoueurs(String[] names){
        for(int i=0;i<this.joueurs.length;i++){
            this.joueurs[i] = new Joueur(names[i]);
            this.joueurs[i].distrubuerCartes(this.joueurs.length);
        }
        // On choisit le premier joueur au hasard
        JoueurCourant = (int) Math.random()*(this.joueurs.length-1);
    }

    private class Joueur {

        private String pseudo;
        /** 
        * pièces que possèdent le joueur
        */
        private final LinkedList<Carte> possedees;

        /**
         * cartes actions posées devant le joueur
         */
        private LinkedList<CarteAction> posees;

        /**
         * @param p Le pseudo du joueurs
         */
        public Joueur (String p) {
            this.pseudo = p; 
            this.possedees = new LinkedList<>();
            this.posees = new LinkedList<>();
        }

        public String getPseudo () {
            return pseudo;
        }

        /**
         *  @return joueur : "+this.pseudo
         */
        @Override
        public String toString () {
            return "joueur : "+this.pseudo;
        }

        public LinkedList<CarteAction> getPosees () {
            return posees;
        }

        public Carte getCarte (int i) {
            return possedees.get(i);
        }

        /** 
         * Affiche toutes les cartes du joueur
         */
        public void afficher () {
            for (int i=0; i < possedees.size(); i++) {
                System.out.print(i+")"+possedees.get(i)+"  ");
            }
        }

        /**
         * Affiche seulement les carte qui représentent des chemins du joueur
         */
        public void afficherTrajet () {
            for (int i=0; i < possedees.size(); i++) {
                if (possedees.get(i) instanceof CarteTrajet) {
                    System.out.print(i+")"+possedees.get(i)+"  ");
                }
            }
        }

        /**
         * Utile pour savoir ce que le joueur a le droit de faire
         * @return true si le joueur a une carte posée devant lui
         */
        public boolean estBloque () {
            return !posees.isEmpty();
        }

        /**
         * Utile pour savoir ce que le joueur peut faire
         * @return true si le joueur a des cartes
         */
        public boolean aDesCartes () {
            return !possedees.isEmpty();
        }

        /**
         * Supprime la carte sélectionnée de la main du joueur
         */
        public void jeterCarte (int i) {
            possedees.remove(i);
        }

        public void jeter (Carte c) {
            possedees.remove(c);
        }

        public LinkedList<Carte> getPossedees () {
            return possedees;
        }

        /**
         * Utile pour les IG
         * @return une liste chaînée des cartes trajets du joueur
         */
        public LinkedList<CarteTrajet> trajet () {
            LinkedList<CarteTrajet> l = new LinkedList<>();
            for (int i=0; i < possedees.size(); i++) {
                if (possedees.get(i) instanceof CarteTrajet) {
                    l.add((CarteTrajet)possedees.get(i));
                } 
            }
            return l;
        }

        /**
         * Utile pour les IG
         * @return les indices des cartes trajets du joueur
         */
        public LinkedList<Integer> trajetIndice () {
            LinkedList<Integer> l = new LinkedList<>();
            for (int i=0; i < possedees.size(); i++) {
                if (possedees.get(i) instanceof CarteTrajet) {
                    l.add(i);
                } 
            }
            return l;
        }

        /**
         * 
         * @return true si la carte peut construire un chemin
         */
        public boolean estTrajet (int i) {
            return (possedees.get(i) instanceof CarteTrajet);
        }

        /**
         * @return true si l'indice retournée est dans la liste possédée du joueur
         */
        public boolean estDansCartes (int i) {
            if (i > possedees.size() || i < 0) {
                return false;
            } 
            return true;
        } 

        /**
         * Utile pour savoir ce que le joueur peut faire
         * @return true si le joueur ne possède pas de carte Trajet, false sinon
         */
        public boolean pasDeTrajets () {
            for (Carte c : possedees) {
                if (c instanceof CarteTrajet) {
                    return false;
                }
            }
            return true;
        }

        /**
         * prendre au hasard dans tableau pioche 
         * et l'ajoute dans la main du Joueurs
         */
        private boolean piocher () {
            int i = (int) (Math.random()*28);
            Carte c = Boites.piocherUneCarte(i);
            if (c != null) {
                possedees.add(c);
                return true;
            }
            return false;
        }


        /**
         * @return la taille de la liste possedees de Joueur
         */
        protected int nombreDePiece(){
            return this.possedees.size();
        }

        /**
        * On remplit la liste possédées du joueur avec 5 cartes peu importe le nombre de joueurs
        */
        private void distrubuerCartes(int nbJoueurs) {
            if (nbJoueurs >= 3 && nbJoueurs <= 8) {
                int c = 0;
                while (c < 5) {
                    piocher();     
                    c++;
                }
            }
        }

        /**
         * Prend une pièce à l'indice i et renvoie la pièce
         */
        public Carte choisirPiece(int i) {
            Carte choix = this.possedees.get(i);
            return choix;
        }

        /**
         * @return une liste des pièces que l'on peut placer
         */
        public LinkedList<CarteTrajet> ListedesCompatibles (Carte haut, Carte bas, Carte gauche, Carte droite) {
            LinkedList<CarteTrajet> l = new LinkedList<>(); 
            for (int i=0; i < possedees.size(); i++) {
                if (possedees.get(i) instanceof CarteTrajet) {
                    if (((CarteTrajet)possedees.get(i)).estCompatible(haut,bas,gauche,droite)) {
                        l.add(((CarteTrajet)possedees.get(i)));
                    } 
                } 
            } 
            return l;
        }

        /**
         * 
         * @return int : une liste d'entier referencant le la liste chainee
         */
        public LinkedList<Integer> ListeIndiceCompatibles (Carte haut, Carte bas, Carte gauche, Carte droite) {
            LinkedList<Integer> l = new LinkedList<>(); 
            for (int i=0; i < possedees.size(); i++) {
                if (possedees.get(i) instanceof CarteTrajet) {
                    if (((CarteTrajet)possedees.get(i)).estCompatible(haut,bas,gauche,droite)) {
                        l.add(i);
                    } 
                } 
            } 
            return l;
        }

        /**
         * Affiche ce que contient la liste posées du joueur
         * Permet de connaître les handicap du joueur
         */
        public void afficherHandicap () {
            if (!posees.isEmpty()) {
                for (CarteAction a : posees) {
                    System.out.print(" "+a.toString()+"  ");
                }
            }
        }

        /**
         * Renvoie une liste des carte Action que possède le joueur
         */
        public LinkedList<CarteAction> aJouer () {
            LinkedList<CarteAction> j = new LinkedList<CarteAction> ();
            for (Carte c : possedees) {
                if (c instanceof CarteAction) {
                    j.add((CarteAction) c);
                }
            }
            return j;
        }

        /**
         * Liste d'indice liés au carte action
         */
        public LinkedList<Integer> aJouerIndice () {
            LinkedList<Integer> j = new LinkedList<Integer> ();
            for (int i=0; i < possedees.size(); i++) {
                if (possedees.get(i) instanceof CarteAction) {
                    j.add(i);
                } 
            }
            return j;
        }

        /**
         * Permet de poser une carte action devant un joueur si
         * il a moins de 3 cartes posées devant lui et si il n'a pas déjà une carte du même type posée devant lui
         * @param j : joueur à qui on souhaite donner l'handicap
         * @param i : carte action que l'on joue
         */
        public void handicap (Joueur j, int i) {
            if (possedees.get(i) instanceof CarteAction) {
                CarteAction c = (CarteAction) possedees.get(i);
                boolean ok = true;
                if (j.getPosees().isEmpty()) {
                    j.getPosees().add(c);
                    possedees.remove(c);
                } else {
                    if (j.getPosees().size() < 3) {
                        for (CarteAction a : j.getPosees()) {
                            if (c.getSymbole() == a.getSymbole()) {
                                ok = false;
                            }
                        }
                        if (ok) {
                            j.getPosees().add(c);
                            possedees.remove(c);
                        }
                    } 
                }
            }
        }

        /**
         * Permet d'enlever une des cartes posées devant soi si on a la carte réparation approriée
         * @param i : carteAction choisie
         */
        public void reparationPerso (int i) {
            if (possedees.get(i) instanceof CarteAction) {
                CarteAction c = (CarteAction) possedees.get(i);
                if (c.getReparation()) {
                    for (CarteAction a : posees) {
                        if ((c.getSymbole() == 'O' && a.getSymbole() == 'o') || (c.getSymbole() == 'T' && a.getSymbole() == 't') || (c.getSymbole() == 'E' && a.getSymbole() == 'e')) {
                            posees.remove(a);
                            possedees.remove(c);
                        }
                    }
                }
            }
        }

        /**
         * Permet d'enlever une des cartes posées devant un joueur pour l'aider si on a la carte réparation approriée
         * @param j : Joueur que l'on souhaite aider
         * @param i : carteAction choisie
         */
        public void reparation (Joueur j, int i) {
            if (possedees.get(i) instanceof CarteAction) {
                CarteAction c = (CarteAction) possedees.get(i);
                for (CarteAction a : j.getPosees()) {
                    if ((c.getSymbole() == 'O' && a.getSymbole() == 'o') || (c.getSymbole() == 'T' && a.getSymbole() == 't') || (c.getSymbole() == 'E' && a.getSymbole() == 'e')) {
                        j.getPosees().remove(a);
                        possedees.remove(c);
                    }
                }
            }
        }

        /**
         * Permet de savoir si on peut jouer une carte réparation
         * @return true si notre carte est la réparation d'une des cartes posées du joueur
         */
        public boolean reparationPossible (Joueur j, int i) {
            if (j.getPosees().isEmpty()) {
                return false;
            }
            if (possedees.get(i) instanceof CarteAction) {
                CarteAction c = (CarteAction) possedees.get(i);
                for (CarteAction a : j.getPosees()) {
                    if ((c.getSymbole() == 'O' && a.getSymbole() == 'o') || (c.getSymbole() == 'T' && a.getSymbole() == 't') || (c.getSymbole() == 'E' && a.getSymbole() == 'e')) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Permet de savoir si on peut jouer une carte handicap
         * @return true si on peut poser cette carte devant le joueur sélectionné
         */
        public boolean handicapPossible (Joueur j, int i) {
            if (possedees.get(i) instanceof CarteAction) {
                CarteAction c = (CarteAction) possedees.get(i);
                boolean ok = true;
                if (j.getPosees() == null || j.getPosees().isEmpty()) {
                    return true;
                } else {
                    if (j.getPosees().size() < 3) {
                        for (CarteAction a : j.getPosees()) {
                            if (c.getSymbole() == a.getSymbole()) {
                                return false;
                            }
                        }
                        return true;
                    } 
                }
            }
            return false;
        }

    } // fin joueur

    @Override
    public int getNbPiecesCurr(){
        return this.joueurs[JoueurCourant].nombreDePiece();
    }

    /**
     * @param x : indice en hauteur où l'on souhaite poser la carte
     * @param y : indice en largeur où l'on souhaite poser la carte
     * @return une liste de cartes compatibles avec les cartes entourant l'emplacement qu'on a choisi
     */
    public LinkedList<CarteTrajet> CartesCompatiblesCurr(int x, int y){
        return this.joueurs[JoueurCourant].ListedesCompatibles((Carte)this.getPiece(x-1,y),(Carte)this.getPiece(x+1,y),(Carte)this.getPiece(x,y-1),(Carte)this.getPiece(x,y+1));
    }

    /**
    * @return la liste des indices correspondant aux cartes compatibles
    */
    public LinkedList<Integer> CartesCompatiblesCurrIndice(int x, int y){
        return this.joueurs[JoueurCourant].ListeIndiceCompatibles((Carte)this.getPiece(x-1,y),(Carte)this.getPiece(x+1,y),(Carte)this.getPiece(x,y-1),(Carte)this.getPiece(x,y+1));
    }

    /**
     * @return la liste de cartes action du joueur
     */
    public LinkedList<CarteAction> aJouerP () {
        return this.joueurs[JoueurCourant].aJouer();
    }

    /**
     * @return la liste d'indice correspondant à la fonction précédente
     */
    public LinkedList<Integer> aJouerIndice () {
        return this.joueurs[JoueurCourant].aJouerIndice();
    }

    /**
     * @return true si l'indice sélectionné est dans la liste précédente
     */
    public boolean estDansAction (int c) {
        for (int i : aJouerIndice()) {
            if (c == i) {
                return true;
            }
        }
        return false;
    }

    public boolean aDesCartes () {
        return this.joueurs[JoueurCourant].aDesCartes();
    }

    public boolean estBloque () {
        return this.joueurs[JoueurCourant].estBloque();
    }

    public void handicap (Joueur j, int i) {
        this.joueurs[JoueurCourant].handicap(j,i);
    }

    public void reparationPerso (int i) {
        this.joueurs[JoueurCourant].reparationPerso(i);
    }

    public void reparation (Joueur j, int i) {
        this.joueurs[JoueurCourant].reparation(j,i);
    }

    /**
     * @param c : choix du joueur
     * @param x : emplacement choisi par le joueur
     * @param y : emplacement choisi par le joueur
     * @return vrai si le choix du joueur est dans la liste des cartes compatibles, faux sinon
     */
    public boolean estDansCompatbile (int c, int x, int y) {
        for (int i : CartesCompatiblesCurrIndice(x,y)) {
            if (c == i) {
                return true;
            }
        }
        return false;
    }

    public boolean estInCompatible (Carte c, int x, int y) {
        for (Carte i : CartesCompatiblesCurr(x,y)) {
            if (c == i) {
                return true;
            }
        }
        return false;
    }

    public boolean estDansCartes (int i) {
        return this.joueurs[JoueurCourant].estDansCartes(i);
    }

    public boolean pasDeTrajets () {
        return this.joueurs[JoueurCourant].pasDeTrajets();
    }

    public boolean estTrajet (int i) {
        return this.joueurs[JoueurCourant].estTrajet(i);
    }

    @Override
    public boolean piocher() {
        return this.joueurs[JoueurCourant].piocher();
    }

    public void afficherPlateau () {
        this.joueurs[JoueurCourant].afficher();
    }

    public void afficherTrajet () {
        this.joueurs[JoueurCourant].afficherTrajet();
    }

    public void afficherHandicap () {
        this.joueurs[JoueurCourant].afficherHandicap();
    }
    
    public void jeterCarte (int i) {
        this.joueurs[JoueurCourant].jeterCarte(i);
    }

    public void jeter (Carte c) {
        this.joueurs[JoueurCourant].jeter(c);
    }

    public Carte getCarte (int i) {
        return this.joueurs[JoueurCourant].getCarte(i);
    }

    public boolean reparationPossible (Joueur j, int i) {
        return this.joueurs[JoueurCourant].reparationPossible(j,i);
    }

    public boolean handicapPossible (Joueur j, int i) {
        return this.joueurs[JoueurCourant].handicapPossible(j,i);
    }

    public LinkedList<Carte> getPiecesJoueur () {
        return this.joueurs[JoueurCourant].getPossedees(); 
    }

    public LinkedList<CarteAction> voirHandicap () {
        return this.joueurs[JoueurCourant].getPosees();
    }

    public LinkedList<CarteTrajet> trajet () {
        return this.joueurs[JoueurCourant].trajet();
    }

    public LinkedList<Integer> trajetIndice () {
        return this.joueurs[JoueurCourant].trajetIndice();
    }

    /**
     * @return les joueurs qui ne sont pas le joueur courant
     */
    public LinkedList<Joueur> getAutres () {
        LinkedList<Joueur> l = new LinkedList <>();
        for (int i=0; i < joueurs.length; i++) {
            if (i != JoueurCourant) {
                l.add(joueurs[i]);
            }
        }
        return l;
    }

    /**
     * @return les indices de la liste chainée précédente
     */
    public LinkedList<Integer> getAutresInd () {
        LinkedList<Integer> l = new LinkedList <>();
        for (int i=0; i < joueurs.length; i++) {
            if (i != JoueurCourant) {
                l.add(i);
            }
        }
        return l;
    }

    public Joueur getJoueurAutre (int i) {
        return getAutres().get(i);
    }


    public String JoueurString (Joueur j) {
        return j.toString();
    }

    /**
     * On suppose que la carte à l'indice i est compatible 
     * @param i choix du jouer
     * x et y représente l'emplacement de la carte
     * On modifie joueurGagnant si on peut atteindre une carte trésor à partir de celle qu'on vient de placer 
     */
    public void placerLaPiece (int i, int x, int y) {
        //on stock la pièce choisi par le joueur
        Carte c = getJoueursCourant().choisirPiece(i);
        super.setPiece(x,y,c);
        getJoueursCourant().jeterCarte(i);
        rendreVisible(((CarteTrajet)c),(CarteTrajet)this.getPiece(x-1,y),(CarteTrajet)this.getPiece(x+1,y),(CarteTrajet)this.getPiece(x,y-1),(CarteTrajet)this.getPiece(x,y+1));
        gagnant(c,x,y);
    }

    public void gagnant (Carte c, int x, int y) {
        if (((CarteTrajet)this.getPiece(x-1,y)) instanceof CarteTresor && ((CarteTrajet)c).getHaut() && ((CarteTrajet)this.getPiece(x-1,y)).getBas()) {
            joueurGagnant = getJoueursCourant();
        }
        if (((CarteTrajet)this.getPiece(x+1,y)) instanceof CarteTresor && ((CarteTrajet)c).getBas() && ((CarteTrajet)this.getPiece(x+1,y)).getHaut()) {
            joueurGagnant = getJoueursCourant();
        }
        if (((CarteTrajet)this.getPiece(x,y-1)) instanceof CarteTresor && ((CarteTrajet)c).getGauche() && ((CarteTrajet)this.getPiece(x,y-1)).getDroite()) {
            joueurGagnant = getJoueursCourant();
        }
        if (((CarteTrajet)this.getPiece(x,y+1)) instanceof CarteTresor && ((CarteTrajet)c).getDroite() && ((CarteTrajet)this.getPiece(x,y+1)).getGauche()) {
            joueurGagnant = getJoueursCourant();
        }

    }

    /**
     * Utile pour les IG
     */
    public void PlacerLaCarte (Carte c, int x, int y) {
        //if (placerUneCarte(c,x,y)) {
            super.setPiece(x,y,c);
            getJoueursCourant().jeter(c);
            rendreVisible(((CarteTrajet)c),(CarteTrajet)this.getPiece(x-1,y),(CarteTrajet)this.getPiece(x+1,y),(CarteTrajet)this.getPiece(x,y-1),(CarteTrajet)this.getPiece(x,y+1));
            gagnant(c,x,y);
        //}
    }

    /**
     * Utile pour les IG
     */
    public boolean placerUneCarte (Carte c, int x, int y) {
        if (x >= X_SIZE || x < 1 || y >= Y_SIZE || y < 1) {
            return false;
        }
        if (estInCompatible(c,x,y)) {
            if (c instanceof CarteAction) {
                return false;
            } else {
                if ((((CarteTrajet)c).estCompatible((Carte)this.getPiece(x-1,y),(Carte)this.getPiece(x+1,y),(Carte)this.getPiece(x,y-1),(Carte)this.getPiece(x,y+1)))) {
                    return true;
                } 
                return false;
            }
        }
        return false;
    }

    /** 
     * @param i : choix du joueur
     * @param x et @param y représentent l'emplacement choisis par le joueur
     * @return true si les dimensions données sont correctes, si la carte sélectionnée est bien une carte chemin et si elle est compatible avec les cartes l'entourant
     * false sinon
     */
    public boolean placerUnePiece(int x, int y, int i){
        if (x > X_SIZE || x < 0 || y > Y_SIZE || y < 0) {
            return false;
        }
        if (estDansCompatbile(i,x,y)) {
            Carte c = getJoueursCourant().choisirPiece(i);
            if (c instanceof CarteAction) {
                return false;
            } else {
                if ((((CarteTrajet)c).estCompatible((Carte)this.getPiece(x-1,y),(Carte)this.getPiece(x+1,y),(Carte)this.getPiece(x,y-1),(Carte)this.getPiece(x,y+1)))) {
                    return true;
                } 
                return false;
            }
        }
        return false;
    }

    @Override
    public void affichageSurTerminal(){
        for (int c=0; c < Y_SIZE; c++) {
            System.out.print("  "+c+" ");
        }
        System.out.println();
        for(int i=0; i<X_SIZE;i++){
            System.out.print(i+"  ");
            for(int j=0;j<Y_SIZE;j++){
                String p = (super.getPiece(i, j)==null)?"   ":super.getPiece(i, j).toString();
                System.out.print(p+" ");
            }
            System.out.println();
        }
    } 

    /**
     * Affiche tous les joueurs sauf le joueur courant
     * Utile pour sélectionner un joueur sur qui jouer une carte action
     */
    public void afficherJoueurs () {
        for (int i=0; i < joueurs.length; i++) {
            if (i != JoueurCourant) {
                System.out.print(i+") "+joueurs[i].getPseudo()+"  ");
            }
        }
    }

    /**
     * Permet de rendre la carte visible si celle-ci est retournée
     */
    public void rendreVisible (CarteTrajet c,CarteTrajet haut, CarteTrajet bas, CarteTrajet gauche, CarteTrajet droite) {
        if (haut != null && !haut.getFace() && ((CarteTrajet)c).getHaut() && haut.getBas()) {
            haut.setVisible();
        }
        if (bas != null && !(bas.getFace()) && ((CarteTrajet)c).getBas() && bas.getHaut()) {
            bas.setVisible();
        }
        if (gauche != null && !(gauche.getFace()) && ((CarteTrajet)c).getGauche() && gauche.getDroite()) {
            gauche.setVisible();
        }
        if (droite != null && !(droite.getFace()) && ((CarteTrajet)c).getDroite() && droite.getGauche()) {
            droite.setVisible();
        }
    }

    @Override 
    public void ajouterUnListener(Panneau pan){
        pan.addMouseListener(new Controleur(){
            @Override
            public void mousePressed(MouseEvent e) {  
                Carte save =(Carte) Boites.retirerPieceEnregistrer();
                if(save!=null){
                    if (placerUneCarte(save,pan.empl_x, pan.empl_y)) {
                        // Place la carte à l'emplacement cliqué
                        PlacerLaCarte(save,pan.empl_x, pan.empl_y);
                    } else {
                        joueurs[JoueurCourant].getPossedees().add(save);
                    }  
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                //Mauvaise reponse
                pan.setBackground(Color.red);
            }
            @Override
            public void mouseExited(MouseEvent e) { pan.setBackground(null); }

        });
    }

    public boolean plusDeCartes (Joueur j) {
        return j.getPossedees().isEmpty();
    }

    
}