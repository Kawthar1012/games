import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;

/**
 * La classe Domino est abstraite pour empeicher la creation d'une
 * Elle est heritee par les classes DominoSimple et DominoDouble
 */
public abstract class Domino extends Piece{

    private static final long serialVersionUID = 1L;
    /**
     * Un domino se composé de deux Pièce, le haut et le bas
     */
    protected Piece p1,p2;

     /**
     * Une bloc d'initiation specifique a DominoDouble car 
     * elle dois etre afficher en Verticale
     */
    {
        this.setLayout(new GridLayout(2,1));
    }

    public Domino(int val1, int val2){
        // 0-1-2-3-4-5-6
        String val = ""+(val1%7)+""+(val2%7);
        this.p1 = new Piece(val.charAt(0));
        this.p2 = new Piece(val.charAt(1));
        this.setLayout(null);
        this.add(this.p1);
        this.add(this.p2);

    }

    public Domino(char val1, char val2){
        this.p1 = new Piece(val1);
        this.p2 = new Piece(val2);
    }

    @Override
    public String toString(){
        return p1.toString()+"|"+p2.toString();
    }
    /**
     * 
     * @param d Domino de type DominoSimple ou DominoDouble
     * @return true si 2 Domino peuvent être placer côte à côte
     */
    public abstract boolean estCompatible(Domino d);
    /**
     * valeur P1+P2 
     * @return nombre de point d'un Domino
     */
    public abstract int point();
    
    /**
     * this.P1|domino.P2 où this est la tête de file
     * @param d Domino queue
     * @return Union de Tête et Queue
     */
    protected Domino unionBordABord(Domino d){
        return(d==null)?null:new DominoSimple(this.p1.getSymbole(),d.p2.getSymbole());
    }

    /**
     * 
     * @param prec Domino tete ou queue
     * @param ajoutTete un boolean true si le domino est placé en tête de la file, false sinon
     */
    public void inversionConditionnelle(Domino prec,boolean ajoutQueue){
        //ne fais rien si appeler dans DominoDuble
        //Overrider dans DominoSimple
    }

    /**
     * 
     * @param d seulement si d = (tete U queue)
     * @return true si this.p1==Union.p2
     */
    public boolean peutEtreQueue(Domino d){
        return (this.p1.getSymbole()==d.p2.getSymbole());
    }

    /**
     * Change les positions des pieces
     */
    public abstract void tourner();

}