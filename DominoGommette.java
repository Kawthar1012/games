public class DominoGommette extends DominoSimple {
    /*
    * On utilise un autre domino simple (donc avec deux couleurs différentes) pour les lier au domino gommette (d'où les deux couleurs dans le constructeur)
    */
    private final DominoSimple couleur;

    public DominoGommette (char val1, char coul1, char val2, char coul2) {
        //val représente les formes => C : Coeur; E : Etoile; L : Lune ;   O : Cercle
        super(val1,val2);
        // les couleur seront => b : bleu; j : jaune  r : rouge; v : vert ;
        this.couleur = new DominoSimple(coul1,coul2);
    }

    public DominoSimple getCouleur () {
        return this.couleur;
    }

    @Override
    public String toString(){
        return this.p1+"("+this.couleur.p1+")|"+this.p2+"("+this.couleur.p2+")";
    }


    @Override
    public boolean estCompatible (Domino d) {
        return super.estCompatible(d)||this.couleur.estCompatible(((DominoGommette)d).couleur);
    }

    @Override
    public int point(){
        //On sait pas encore comment comptabiliser
        return 1;
    }

    /**
     * Applique l'inversion sur le forme et la couleur
     */
    @Override
    public void inversionConditionnelle(Domino prec,boolean ajoutTete){
        super.inversionConditionnelle(prec, ajoutTete);
        this.couleur.inversionConditionnelle(prec, ajoutTete);
    }

    @Override
    public boolean peutEtreQueue(Domino d){
        return (super.peutEtreQueue(d)|| this.couleur.peutEtreQueue(d));
    }

    @Override
    protected Domino unionBordABord(Domino d){
        if(d==null){
            return null;
        }
        DominoGommette union = new DominoGommette(this.p1.getSymbole(),this.couleur.p1.getSymbole()
                                ,d.p2.getSymbole(),this.couleur.p2.getSymbole());
        return union;
    }

}