/**
 * ref : https://www.regles-de-jeux.com/regle-domino/
 * Nombre de joueurs : 2-4 Personnes
 * 
 * 7 dominos à 2 joueurs, 6 dominos à 3 ou 4 joueurs
 * 
 * Attention, les dominos doivent être distribués points cachés. 
 * Le reste des dominos fait office de pioche.
 * 
 * Le joueur ayant le double le plus élevé (le double 6 donc) commence la partie de domino.
 * 
 * Si le joueur possède un domino correspondant, il le pose à la suite du domino. 
 * Sinon, il pioche un domino et passe son tour.
 * 
 * Le but du jeu du domino est d’être le le premier joueur à avoir posé tous ses dominos.
 * 
 * Pour gagner au domino, il suffit d’être le premier joueur à avoir posé tous ses dominos. 
 * Il se peut que le jeu soit bloqué. Alors le joueur ayant le moins de points est déclaré vainqueur.
 */

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;


public class JeuDomino { //implemts Jeux

    /**
     * Pouvant acceuillir les 2 types de jeu (Gommette ou pas)
     */
    private PlateauDomino plateau;

    private final boolean estJeuGommette;

    /**
     * true : quand c'est au tour du premier joueur de poser sa pièce
     */
    private boolean lancementinitiale;

    public JeuDomino (boolean estJeuGommette) {
        //Au départ le plateau sera null
        this.plateau = null;
        this.lancementinitiale = true;
        this.estJeuGommette = estJeuGommette;
    }

    /**
     * Initialise le tableau ainsi que ses Joueurs
     * @param nbJoueur nombres de Joueurs [2-4]
     * @param noms un tableau de String contenant les pseudos des Joueurs
     */
    public void initialisation(int nbJoueur,String[] noms){
        this.plateau = new PlateauDomino(nbJoueur);
        plateau.initialiserJoueurs(noms,this.estJeuGommette);
    }

    private LinkedList<Domino> dominoCompatiblesJCurr(){
        return this.plateau.dominoCompatiblesJCurr(this.lancementinitiale);
    }
    private LinkedList<Integer> dominoCompatiblesJCurrIndice(){
        return this.plateau.dominoCompatiblesJCurrIndice(this.lancementinitiale);
    }
    
    private void placerLaPiece(int i){
        this.plateau.placerLaPiece(i);
    }

    public void LancerPartieSurTerminal(){
        Scanner sc; boolean stop = false; 
        int i =0; int nbJoueur = 0;
        
        while(!stop){
            switch(i){
                case 0 : System.out.println("|******************************************************|\n"
                                        +"               Debut de la partie   ");
                        i++; break;
                case 1 : System.out.print("Combien de Joueurs seront de la Partie ? 2 3 ou 4 : ");
                        sc = new Scanner(System.in); 
                        int val = 0;
                        try {
                            val = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Veuillez entrer un nombre.");
                            i = 1;
                            break;
                        }
                        if(val<2||val>4){ System.out.println("\nVeuillez respecter les consignes !!!\n"); }
                        else{ nbJoueur=val; i++;    }
                        break;
                case 2 :  System.out.println("   Renseignez les noms de chaque joueurs :");
                        int j =1; String[] noms = new String[nbJoueur];
                        while(j<=nbJoueur){
                            System.out.print("  j"+j+": ");
                            sc = new Scanner(System.in);
                            noms[j-1] = sc.next(); //prendra que le premier mot
                            j++;
                        }
                        initialisation(nbJoueur, noms); // decide du premier joueur 
                        i=21;break; 
                case 21: System.out.println("C'est au tour du "+this.plateau.getJoueursCourantSring()
                            +"\nNombre de pièces restantes : "+this.plateau.getNbPiecesCurr());
                        if(afficherUneListe(dominoCompatiblesJCurr(),dominoCompatiblesJCurrIndice())){ //affichage des choix
                            if(this.lancementinitiale)i=22; // case dans lequel on passera uniquement lors du lancement initial
                            else i=4; 
                        }else{ 
                            i=5; // pioche automatiquement si aucune pièce n'est compatible
                        } 
                        break;
                case 22 : System.out.println("\nPlacez votre premiere piece.");
                         sc = new Scanner (System.in); int choix = 0;
                         try {
                            choix = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Veuillez entrer un nombre.");
                            i = 22;
                            break;
                        }
                         if (choix < 0 || choix > 5) {
                            System.out.println("Choix incorrect !!!!");
                            i = 22;
                         } else {
                            placerLaPiece(choix);
                           this.lancementinitiale=false;i=3;  // on passe le lancement initial à false quand on l'a posé  
                         }
                         break;
                case 3 : this.plateau.affichageSurTerminal();
                        if(this.plateau.joueurGagnant()==null){ // on change de joueur tant qu'il n'y a pas de gagnant
                            i= 21;
                        }else{
                            i = 6;
                        }
                         break;
                case 4 : System.out.println("\nVeuillez choisir une des pieces proposees");
                        sc = new Scanner(System.in); int choix2 = 0;
                        try {
                            choix2 = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Veuillez entrer un nombre.");
                            i = 4;
                            break;
                        }
                        if (this.plateau.estDansCompatbile(choix2,lancementinitiale)) { // On place la pièce choisie par le joueur si elle est dans les choix proposés
                            placerLaPiece(choix2);
                            i = 3; 
                        } else {
                            System.out.println("Choix incorrect !!! "); // On redemande de choisir une pièce sinon 
                            i = 4; 
                        }
                        break;
                case 5 : if(this.plateau.piocher()){
                            System.out.print("Aucune piece compatible sur le plateau, pioche pour "); 
                        }else { 
                            System.out.print("Plus de pièces disponible dans la boite, ");
                        }
                        System.out.println(this.plateau.getJoueursCourantSring()+" passe son tour");
                        i=3; 
                        break;

                case 6 : System.out.println(
                    this.plateau.joueurGagnant()+" viens de gagner la partie!!!!!\n"
                    +"Voici son score : "+this.plateau.pointGagnant()+"\n"
                    ); i++;
                    break;

                default : stop=true;break;
            }
        }
        
    }

    /**
     * 
     * @param choix Dominos que l'utilisateur peut placer
     * @param empl indicce des Dominos que l'utilisateur peut placer
     * @return false si choix est vide
     */
    public boolean afficherUneListe(LinkedList<Domino> choix,LinkedList<Integer> empl){
        if(choix!=null&&!choix.isEmpty()){
            for(int i=0; i<choix.size();i++){
                System.out.print(empl.get(i)+") "+choix.get(i)+"  ");
            }
            System.out.println();
            return true;
        }
        return false;
    }


}