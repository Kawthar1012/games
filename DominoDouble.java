import java.awt.GridLayout;

public class DominoDouble extends Domino{

    public DominoDouble(int val){
        super(val,val);
    }
    public DominoDouble(char val){
        super(val, val);
        
    }

    /*
    * Affichage différent pour un domino double (en décalé)
    * Affichage par défaut
    */
    @Override
    public String toString () {
        return super.toString();
    }
    /**
     * 
     * @param d = un domino avec 2 bords 
     * @return true si une des bords de d est égal à un bord de this
     * 
     */
    @Override
    public boolean estCompatible(Domino d){
        if(d instanceof DominoDouble){
            //car on ne peut avoir deux domino double de même valeur
            return false;
        }
        String bordDeThis = this.p1.toString();
        return (bordDeThis.equals(d.p1.toString())|| bordDeThis.equals(d.p2.toString()) );
    }

    @Override
    public int point(){
        return this.p1.valeurEnEntier()*2;
    }

    @Override 
    public void tourner(){
        this.setLayout(new GridLayout(1,2));
    }    
}