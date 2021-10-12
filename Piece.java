import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;

import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Cette classe sera le modele du projet toute objet a afficher sur 
 * <p>un Plateau devra heriter de ce dernier</p>
 * <p>Elle etant JPanel donc est sera Container </p>
 */
public class Piece extends JPanel{

    /**
     * Obligatoire pour l'usage de l'heritage via JPanel
     */
    private static final long serialVersionUID = 1L;
    /*
     * Une référence unique pour chacune des pièces creer
     */ 
    private static int nbcreer = 0;
    /*
    * Un numéro unique pour chaque instance
    */
    protected final int numRef;
    
    /*
    * Renseignera sur la valeur d'une pièce
    */
    private final char symbole;

    /**
     * L'image qui va s'afficher sur une piece
     * pas final vue que Image est abstract
     */
    private Image IconBackGround;
    
    /**
     * Ici on pourra reguler la taille d'une Piece
     * a overrider dans les classes heritantes
     */
    private int size_iconX ;
    private int size_iconY ;

    public Piece(char sym){
        this.symbole = sym;
        this.numRef = ++nbcreer;

        this.size_iconX = 60;
        this.size_iconY = 60;
        try{
            this.IconBackGround = new ImageIcon(path()).getImage();
            Image newimg = this.IconBackGround.getScaledInstance(this.size_iconX, this.size_iconY, Image.SCALE_SMOOTH); //Redimentionne la piece
            this.IconBackGround = new ImageIcon(newimg).getImage();  // applique la redimension
            
        }catch (FileNotFoundException e){ }
        
    }

    /**
     * Permet de changer l'image en arriere plan des Pieces
     * @param img Image
     */
    protected void setImage(Image img){
        this.IconBackGround = img;
        repaint();
    }

    protected void setSizeicon(int x,int y){
        this.size_iconX=x; this.size_iconY=y;
    }

    /**
     * Une fonction obligatoire pour pouvoir afficher l'image 
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.IconBackGround, 0, 0, null);
    }

    public Piece(){ 
        this('#'); /* Une pièce sans valeur sera représenté par le symbole # */
    }

    public char getSymbole(){
        return this.symbole;
    }
    @Override
    public String toString(){
        return ""+this.symbole;
    }

    public int getsizeiconX () {
        return size_iconX;
    }

    public int getsizeiconY () {
        return size_iconY;
    }


    /**
     * Ne sera utile que quand la pièce a un int comme 
     * symbole comprise entre 0 et 6 inclus
     * @return -1 mauvaise utilisation
     */
    public int valeurEnEntier(){
        int res = Character.getNumericValue(this.symbole);
        if(res>=0&&res<=6){
            return res;
        }
        return -1;
    }   
    
    /**
     * renvoie le chemin de l'image adapter a la Piece
     * @return String path
     */
    private String path() throws FileNotFoundException{
        String path = "";
        switch(this.symbole){
            case '0' : path="Ressources/Dice0.png";break;
            case '1' : path="Ressources/Dice1.png";break;
            case '2' : path="Ressources/Dice2.png";break;
            case '3' : path="Ressources/Dice3.png";break;
            case '4' : path="Ressources/Dice4.png";break;
            case '5' : path="Ressources/Dice5.png";break;
            case '6' : path="Ressources/Dice6.png";break;
            case '+' : path="Ressources/SaboteurChemin4.png";break;
            case '|' : path="Ressources/SaboteurChemin8.png";break;
            case '-' : path="Ressources/SaboteurChemin5.png";break;
            case 'O' : path="Ressources/SaboteurOutil1.png";break;
            case 'T' : path="Ressources/SaboteurChariot1.png";break;
            case 'E' : path="Ressources/SaboteurLampe1.png";break;
            case 'o' : path="Ressources/SaboteurOutil0.png";break;
            case 't' : path="Ressources/SaboteurChariot0.png";break;
            case 'e' : path="Ressources/SaboteurLampe0.png";break;
            case 's' : path="Ressources/SaboteurDepart.png";break;
            case 'a' : path="Ressources/SaboteurChemin1.png";break;
            case 'b' : path="Ressources/SaboteurChemin2.png";break;
            case 'c' : path="Ressources/SaboteurChemin6.png";break;
            case 'd' : path="Ressources/SaboteurChemin3.png";break;
            case 'f' : path="Ressources/SaboteurCache.png";break;
            case 'g' : path="Ressources/SaboteurCache.png";break;
            case 'h' : path="Ressources/SaboteurCache.png";break;
            default : path=""; break;
        }
        if(path.isEmpty()) throw new FileNotFoundException();
        return path;
    }

    
}