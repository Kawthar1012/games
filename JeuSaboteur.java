import java.util.*;

import javax.swing.JLabel;
public class JeuSaboteur implements InterfaceJeu { 

        /**
     * Pouvant acceuillir les 2 types de jeu (Gommette ou pas)
     */
    private PlateauSaboteur plateau;

    /**
     * true : quand c'est au tour du premier joueur de poser sa pièce
     */
    private boolean lancementinitiale;

    public JeuSaboteur () {
        //Au départ le plateau sera null
        this.plateau = null;
        this.lancementinitiale = true;
    }

    /**
     * Initialise le tableau ainsi que ses Joueurs
     * On place également la carte de départ et les cartes trésor/trajet de fin (qu'on rend invisible)
     * @param nbJoueur nombres de Joueurs [3-8]
     * @param noms un tableau de String contenant les pseudos des Joueurs
     */
    public void initialisation(int nbJoueur,String[] noms){
        this.plateau = new PlateauSaboteur(nbJoueur);
        Carte depart = new CarteTrajet(true,true,true,true,'s');
        this.plateau.setPiece(6,1,depart);
        CarteTrajet t1 = new CarteTrajet (false,true,false,true,'h');
        CarteTresor t2 = new CarteTresor (true,true,true,true,'f');
        CarteTrajet t3 = new CarteTrajet (true,false,false,true,'g');
        CarteTrajet [] tab = new CarteTrajet [3];
        // Permet de changer l'emplacement du trésor de façon aléatoire
        Random r = new Random ();
        int x = r.nextInt(3);
        int y = 0;
        int z = 0;
        if (x == 0) {
            y = 1;
            z = 2;
        } else if (x == 1) {
            y = 0;
            z = 2;
        } else {
            y = 0;
            z = 1;
        }
        tab[x] = t2;
        tab[y] = t1;
        tab[z] = t3;
        this.plateau.setPiece(4,13,(Piece)tab[0]);
        this.plateau.setPiece(6,13,(Piece)tab[1]);
        this.plateau.setPiece(8,13,(Piece)tab[2]);
        plateau.initialiserJoueurs(noms);
    }

    private LinkedList<CarteTrajet> CartesCompatiblesCurr(int x, int y){
        return this.plateau.CartesCompatiblesCurr(x,y);
    }
    private LinkedList<Integer> CartesCompatiblesCurrIndice(int x, int y){
        return this.plateau.CartesCompatiblesCurrIndice(x,y);
    }

    private boolean placerUnePiece (int x, int y, int i) {
        return this.plateau.placerUnePiece(x,y,i);
    }
    
    private void placerLaPiece(int i, int x, int y){
        this.plateau.placerLaPiece(i,x,y);
    }

    public void afficher () {
        this.plateau.afficherPlateau();
    }

    public void jeterCarte (int i) {
        this.plateau.jeterCarte(i);
    }

    public LinkedList<CarteAction> getCarteAction () {
        return this.plateau.aJouerP();
    }

    public LinkedList<Integer> getIndiceAction () {
        return this.plateau.aJouerIndice();
    }

    public LinkedList<CarteTrajet> getCarteTrajet () {
        return this.plateau.trajet();
    }

    public LinkedList<Integer> getIndiceTrajet () {
        return this.plateau.trajetIndice();
    }

    public boolean handicap () {
        return this.plateau.estBloque();
    }

    public JLabel getnbPieces () {
        int i = this.plateau.getNbPiecesCurr();
        return new JLabel ("Nombre de cartes : "+i);
    }

    @Override
    public Plateau getPlateau () {
        return plateau;
    }

    @Override
    public JLabel getnomJoueur() {
        return new JLabel(this.plateau.getJString());
    }

    @Override
    public LinkedList<Carte> getPiecesJoueurs () {
        return this.plateau.getPiecesJoueur();
    }

    /**
     * On lance la partie
     */
    public void LancerPartieSurTerminal(){
        // Le scanner nous permettra de lire les requêtes du joueur
        Scanner sc;
        boolean stop = false; 
        int i =0; int nbJoueur = 0;
        int choix = 0; int x = 0; int y = 0;
        while(!stop){
            switch(i){
                case 0 : System.out.println("|******************************************************|\n"
                                        +"               Début de la partie   ");
                        i++; break;
                case 1 : System.out.print("Combien de Joueurs seront de la Partie ? (entre 3 et 8) : ");
                        sc = new Scanner(System.in); int val = 0;
                        try {
                            val = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Veuillez entrer un nombre.");
                            i = 1;
                            break;
                        }
                        if(val<3||val>8){ System.out.println("\nVeuillez respecter les consignes !!!\n"); }
                        else{ nbJoueur=val; i++;    }
                        break;
                case 2 :  System.out.println("   Renseignez les noms de chaque joueur :");
                        int j =1; String[] noms = new String[nbJoueur];
                        while(j<=nbJoueur){
                            System.out.print("  j"+j+": ");
                            sc = new Scanner(System.in);
                            noms[j-1] = sc.next(); //prendra que le premier mot
                            j++;
                        }
                        initialisation(nbJoueur, noms); // decide du premier joueur 
                        i=21;break; 
                case 21: // Affiche le jouer actuel, son nombre de pièces et l'état du plateau
                        System.out.println("C'est au tour de "+this.plateau.getJString()
                        +"\nNombre de pièces restantes : "+this.plateau.getNbPiecesCurr());
                        System.out.println("Voici l'état du plateau actuellement.");
                        this.plateau.affichageSurTerminal();
                        i=22; break;
                case 22 : // Affiche les cartes du joueur si il en a, pioche sinon
                        if (this.plateau.aDesCartes()) {
                            System.out.println("Voici vos cartes : ");
                            this.plateau.afficherPlateau();
                                // Montre ce que peut faire le joueur si il a des cartes posées devant lui ou non
                                if (!this.plateau.estBloque()) {
                                    System.out.println("\nVous pouvez :");
                                    System.out.println("1) Poser une carte");
                                    System.out.println("2) Jeter une carte");
                                    System.out.println("3) Jouer une carte action");
                                    sc = new Scanner (System.in);
                                    try {
                                        choix = sc.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Veuillez entrer un nombre.");
                                        i = 22;
                                        break;
                                    }
                                    if (choix == 1) {
                                        i=23;
                                    } else if (choix == 2) {
                                        i=24;
                                    } else if (choix == 3) {
                                        i=25;
                                    } else {
                                        System.out.println("Pas inclus dans les choix proposés !");
                                        i=22;
                                    }
                                } else {
                                    System.out.print("Vous avez un handicap : ");
                                    this.plateau.afficherHandicap();
                                    System.out.println("\nVous ne pouvez donc pas placer de piece. En revanche, vous pouvez :");
                                    System.out.println("1) Jeter une carte");
                                    System.out.println("2) Jouer une carte action");
                                    sc = new Scanner (System.in); 
                                    try {
                                        choix = sc.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Veuillez entrer un nombre.");
                                        i = 22;
                                        break;
                                    }
                                    if (choix == 1) {
                                        i=24;
                                    } else if (choix == 2) {
                                        i=25;
                                    } else {
                                        System.out.println("Pas inclus dans les choix proposés !");
                                        i=22;
                                    }
                                }
                            } else {
                                System.out.println("Vous n'avez plus de cartes, vous devez piocher.");
                                i = 5;
                            }
                            break;
               case 23 : // On peut poser une carte seulement si on a des cartes chemins
                        if (!this.plateau.pasDeTrajets()) {
                            if(this.lancementinitiale) {
                                i=231; // case dans lequel on passera uniquement lors du lancement initial
                            } else {
                                System.out.println("\nVeuillez choisir l'emplacement que vous souhaitez.");
                                System.out.print(" x : ");
                                sc = new Scanner (System.in);
                                try {
                                    x = sc.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Veuillez entrer un nombre.");
                                    i = 23;
                                    break;
                                }
                                System.out.print(" y : ");
                                sc = new Scanner (System.in); 
                                try {
                                    y = sc.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Veuillez entrer un nombre.");
                                    i = 23;
                                    break;
                                }
                                if(afficherUneListe(CartesCompatiblesCurr(x,y),CartesCompatiblesCurrIndice(x,y))){ //affichage des choix
                                    System.out.println("\nVeuillez choisir une des pièces proposées.");
                                    sc = new Scanner(System.in); 
                                    try {
                                        choix = sc.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Veuillez entrer un nombre.");
                                        i = 23;
                                        break;
                                    }
                                    if (placerUnePiece(x,y,choix)) { // On place la pièce choisie par le joueur si elle est dans les choix proposés
                                        placerLaPiece(choix,x,y);
                                        if (this.plateau.getJoueurGagnant() != null) { // Cas où le joueur a gagné
                                            i = 3;
                                        } else {
                                            i = 5;
                                        }
                                    } else {
                                        System.out.println("Choix ou emplacement incorrect !!! "); // On redemande de choisir une pièce sinon 
                                        i = 23; 
                                    }
                                } else { 
                                // Si on ne possède aucune pièce compatible avec l'emplacement choisi
                                System.out.println("Pas de pièces compatibles avec cet emplacement ou pièce non compatible avec l'emplacement choisi. Veuillez faire autre chose.");
                                    i=22; 
                                } 
                            }
                        } else {
                            System.out.println("Vous n'avez pas de carte chemin. Veuillez faire autre chose.");
                            i = 22;
                        }
                        break;
                case 231 : // Case où l'on passe uniquement lors du lancement intiale -> utilité ?
                        System.out.println("\nPlacez votre premiere piece. Pour cela veuillez renseigner l'emplacement où vous souhaitez la placer.");
                        System.out.print(" x : ");
                        sc = new Scanner (System.in);
                        try {
                            x = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Veuillez entrer un nombre.");
                            i = 231;
                            break;
                        }
                        System.out.print(" y : ");
                        sc = new Scanner (System.in); 
                        try {
                            y = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Veuillez entrer un nombre.");
                            i = 231;
                            break;
                        }
                        if(afficherUneListe(CartesCompatiblesCurr(x,y),CartesCompatiblesCurrIndice(x,y))){
                            System.out.println("Veuillez choisir une des pièces proposées.");
                            sc = new Scanner (System.in);  // choix du joueur
                            try {
                                choix = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Veuillez entrer un nombre.");
                                i = 231;
                                break;
                            }
                            if (placerUnePiece(x,y,choix)) {
                                placerLaPiece(choix,x,y);
                                this.lancementinitiale=false;i=5; 
                            } else {
                                System.out.println("Choix ou emplacement incorrect !!!"); i=23;
                            }
                        } else { 
                            System.out.println("Pas de pièces compatibles avec cet emplacement ou pièce non compatible avec l'emplacement choisi. Veuillez faire autre chose.");
                            i=22; 
                        } 
                        break; // on passe le lancement initial à false quand on l'a posé 
                case 24 :   afficher(); // On affiche toutes les cartes du joueur
                            System.out.println("Veuillez choisir une carte à jeter");
                            sc = new Scanner (System.in); 
                            try {
                                choix = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Veuillez entrer un nombre.");
                                i = 24;
                                break;
                            }
                            if (this.plateau.estDansCartes(choix)) {
                                jeterCarte(choix);
                                i=5;
                            } else {
                                System.out.println("Veuillez choisir une des cartes proposées.");
                                i = 24;
                            }
                            break;
                case 25 : System.out.println("Voici les cartes que vous pouvez jouer :"); // affiche les cartes action du joueur
                        if (afficherUneListe(this.plateau.aJouerP(),this.plateau.aJouerIndice())) {
                            System.out.println("Veuillez choisir une des cartes proposées.");
                            sc = new Scanner (System.in);
                            try {
                                choix = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Veuillez entrer un nombre.");
                                i = 25;
                                break;
                            } 
                            if (this.plateau.estDansAction(choix)) {
                                // On divise les case si la carte est une carte réparatrice ou non
                                if (((CarteAction) this.plateau.getCarte(choix)).getReparation()) { 
                                    i = 251;
                                } else {
                                    i = 252;
                                }
                            } else {
                                System.out.println("Veuillez choisir une des cartes proposées.");
                                i = 25;
                            }  
                        } else {
                            System.out.println("Vous n'avez plus de cartes actions. Veuillez faire autre chose.");
                            i = 22;
                        }
                        break;
                case 251 : // Cas où la carte est une carte réparation : on demandera si on souhaite à l'appliquer à soi ou à un autre joueur
                            System.out.println("Cette carte est une carte réparation. Souhaitez vous l'appliquer à vous ou à quelqu'un d'autre ? (moi (1) ou autre(2))");
                            sc = new Scanner (System.in); int reponse = 0;
                            try {
                                reponse = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Veuillez entrer un nombre.");
                                i = 251;
                                break;
                            }
                            if (reponse == 1) {
                                if (this.plateau.reparationPossible(this.plateau.getJoueursCourant(),choix)) {
                                    this.plateau.reparationPerso(choix);
                                    i = 5;
                                } else {
                                    System.out.println("Vous ne pouvez pas jouer cette carte sur vous.");
                                    i = 22;
                                }
                            } else if (reponse == 2) {
                                System.out.println("Veuillez sélecitonner un joueurs parmi les suivants.");
                                this.plateau.afficherJoueurs(); // On affiche les joueurs à qui on peut appliquer la carte
                                sc = new Scanner (System.in); int cjoueur = 0;
                                try {
                                    cjoueur = sc.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Veuillez entrer un nombre.");
                                    i = 251;
                                    break;
                                }
                                if (cjoueur > this.plateau.joueursLength()|| cjoueur < 0 || cjoueur == this.plateau.getCour()) {
                                    System.out.println("Veuillez choisir un des joueurs proposés.");
                                    i = 251;
                                } else {
                                    if (this.plateau.reparationPossible(this.plateau.getThisJoueur(cjoueur),choix)) {
                                        this.plateau.reparation(this.plateau.getThisJoueur(cjoueur),choix);
                                        i = 5;
                                    } else {
                                        System.out.println("Vous ne pouvez pas jouer cette carte sur ce joueur.");
                                        i = 22;
                                    }
                                }
                            } else {
                                System.out.println("La valeur entrée n'est pas prise en compte.");
                                i = 25;
                            }
                            break;
                case 252 : // Cas où la carte n'est pas réparatrice
                            System.out.println("Cette carte est une carte handicap. Veuillez choisir le joueur sur lequel vous souhaitez l'appliquer.");
                            this.plateau.afficherJoueurs();
                            sc = new Scanner (System.in); int cjoueur2 = 0;
                            try {
                                cjoueur2 = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Veuillez entrer un nombre.");
                                i = 252;
                                break;
                            }
                            if (cjoueur2 > this.plateau.joueursLength() || cjoueur2 < 0 || cjoueur2 == this.plateau.getCour()) {
                                System.out.println("Veuillez choisir un des joueurs proposés.");
                                i = 252;
                            } else {
                                if (this.plateau.handicapPossible(this.plateau.getThisJoueur(cjoueur2),choix)) {
                                    this.plateau.handicap(this.plateau.getThisJoueur(cjoueur2),choix);
                                    i = 5;
                                } else {
                                    System.out.println("Vous ne pouvez pas jouer cette carte sur ce joueur.");
                                    i = 22;
                                }
                            }
                            break;
                case 3 : this.plateau.affichageSurTerminal();
                        if(this.plateau.getJoueurGagnant()==null){ // on change de joueur tant qu'il n'y a pas de gagnant
                            i= 21;
                        }else{
                            i = 6;
                        }
                         break;
                case 5 : if(this.plateau.piocher()){ // Tous les joueurs passent par la pioche après avoir joué
                            System.out.print("Pioche automatique pour "); 
                        }else { 
                            System.out.print("Plus de pièces disponible dans la boite, ");
                        }
                        System.out.println(this.plateau.getJoueursCourantString());
                        i=3; 
                        break;

                case 6 : System.out.println(
                    this.plateau.getJoueurGagnant()+" viens de gagner la partie!!!!!\n"); i++;
                    break;

                default : stop=true;break;
            }
        }
        
    }

    /**
     * 
     * @param choix Cartes que l'utilisateur peut placer
     * @param empl indice des Cartes que l'utilisateur peut placer
     * @return false si choix est vide
     */
    public boolean afficherUneListe(LinkedList<? extends Carte> choix,LinkedList<Integer> empl){
        if(choix!=null&&!choix.isEmpty()){
                for(int i=0; i<choix.size();i++){
                    System.out.print(empl.get(i)+") "+
                    choix.get(i)+"  ");
                }
                System.out.println();
                return true;
        }
            
        return false;
    }


}