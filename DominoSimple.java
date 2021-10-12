import java.awt.GridLayout;

public class DominoSimple extends Domino{

    public DominoSimple(int val1,int val2){
        super(val1,val2);
        
    }

    public DominoSimple (char val1, char val2) {
        super (val1,val2);
    }

    @Override
    public String toString(){
        return p1.toString()+"|"+p2.toString();
    }

    /*
     * p1 --> p2 p2 --->p1
     */
    private void inverserValeurs(){
        Piece temp = this.p1;
        this.p1=this.p2; 
        this.p2 = temp;
    }  

    @Override
    public boolean estCompatible (Domino d) {
        if (d instanceof DominoDouble) {
            // On vérifie si les deux côtés sont compatibles avec un seul des côtés du domino d car ses deux côtés sont les mêmes
            return (this.p1.toString().equals(d.p1.toString()) || this.p2.toString().equals(d.p1.toString()));
        } else {
            // On vérifie les compatibilités de tous les côtés possibles
            return (d.p1.toString().equals(this.p1.toString()) || d.p2.toString().equals(this.p1.toString()) 
                || d.p1.toString().equals(this.p2.toString()) || d.p2.toString().equals(this.p2.toString()));
        }
    }

    @Override
    public int point(){
        return this.p1.valeurEnEntier()+this.p2.valeurEnEntier();
    }

    @Override
    public void inversionConditionnelle(Domino prec,boolean ajoutQueue){
        if(ajoutQueue){
            if(this.p1.getSymbole()!=prec.p2.getSymbole()){
                this.inverserValeurs();
            }
        }else{
            if(this.p2.getSymbole()!=prec.p1.getSymbole()){
                this.inverserValeurs();
            }
        }
    }

    @Override 
    public void tourner(){
        this.setLayout(new GridLayout(2,1));
    } 
}