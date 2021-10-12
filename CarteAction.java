public class CarteAction extends Carte {

    private final boolean reparation;

    /**
     * Trois types de carte action (o: outils, t: transports, e: eclairage)
     * @param r : définit si la carte est réparatrice ou non
     */
    public CarteAction (char s, boolean r) {
        // renvoie une erreur si s != 'o','t' ou 'e'
        super (s);
        // carte réparatrice ou non
        reparation = r;
    }

    public boolean getReparation () {
        return reparation;
    }

    /**
     * On précise d'abord si la carte est une carte réparation ou non avant de donner son rôle
     */
    public String toString () {
        String l = "";
        if (reparation) {
            l += "Réparation ";
        }
        if (getSymbole() == 'o') {
            l += "outils";
        } else if (getSymbole() == 't') {
            l += "transports";
        } else if (getSymbole() == 'e') {
            l += "eclairage";
        }
        return l;
    }

}
