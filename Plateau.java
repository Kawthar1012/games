import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;


/**
 * Sera la vue de nos modeles, toute classe heritant de Plateau devront
 * presenter une vue adapter a ce dernier
 */
public abstract class Plateau extends JPanel{

    private static final long serialVersionUID = 1L;

    /**
     * Une fois initialiser la taille d'un Plateau dois rester inchangeable 
     */
    private Piece[][] pieces;

    public Plateau(int x,int y){
        this.pieces = new Piece[x][y];
        this.setLayout(new GridLayout(x,y));
    }
    
    /**
     * 
     * @param x ligne 
     * @param y colonne
     * @return un objet Piece à l'emplacement (x,y)
     */
    public Piece getPiece(int x, int y){
        return this.pieces[x][y];
    }

    /**
     * 
     * @param x = ligne  
     * @param y = colonne
     * @param p = La pièce à introduire à l'indice (i,j)
     * renvoie faux si l'emplacement demandé n'est pas vide
     * @return
     */
    public boolean emplacementVide(int x, int y){
        if(this.pieces[x][y]==null){
            return true;
        }
        return false;
        
    }

    /**
     * Verifie si la case n'est pas deja occupee 
     * Rempli la case et met à jours l'interface Graphique
     * @param x  int
     * @param y int
     * @param p Piece
     */
    public void setPiece (int x, int y, Piece p) {
        if (emplacementVide(x,y)) {
            this.pieces[x][y] = p;
            miseAJour();
        }
    }

    /**
     * <p>Permet de raffraichir le plateau </p>
     * <p>Insertion de Panneau dans les emplacement vide </p>
     * <p>Permet aussi aux Case vide de renseigner leurs coordonnees</p>
     */
    public void miseAJour(){
        this.removeAll();//Netoye le Conteneur
        for(int i =0;i<this.pieces.length;i++){
            for(int j=0;j<this.pieces[i].length;j++){
                Panneau pan = new Panneau(i, j);
                ajouterUnListener(pan);
                this.add((pieces[i][j]==null)? pan:pieces[i][j]);//pour eviter null
            }
        }
    }

    /**
     * Permet de savoir si une Piece existe deja dans le plateau
     * @param p Piece
     * @return boolean
     */
    public boolean estPresent(Piece p){
        for(int i=0;i<this.pieces.length;i++){
            for(int j=0;j<this.pieces[i].length;j++){
                if(p.equals(this.pieces[i][j])){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Fonction auxilliaire pour metttre à jour le JPanel
     */
    public void raffraichir(){
        setVisible(false);setVisible(true);
    }

    /**
     * Renvoie vrai si l'ensemble du tableau pieces est rempli
     * @return boolean
     */
    public boolean estComplet(){
        for(int i = 0; i<this.pieces.length;i++){
            for(int j=0;j<this.pieces[i].length;j++){
                if(this.pieces[i][j]==null){
                    return false;
                }
            }
        }
        return true;
    }

    public abstract void affichageSurTerminal();


    /**
     * 
     * @return true si la pioche aete fait avec succes
     */
    public abstract boolean piocher();

    /**
     * 
     * @return le nombre de Piece qui reste dans la main du Joueuer courant
     */
    public abstract int getNbPiecesCurr();

    /**
     * Les instructions a suivre quand le Panneau est cliquer (ex)
     * doit declarer un addMouseListener(...)
     * @param pan
     */
    public abstract void ajouterUnListener(Panneau pan);
}