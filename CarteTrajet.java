import java.awt.Image;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;

public class CarteTrajet extends Carte {
    private boolean gauche;
    private boolean droite;
    private boolean haut;
    private boolean bas;

    /**
     * Rend la carte invisible automatiquement s'il s'agit d'une carte trésor
     * @param g : chemin vers la gauche
     * @param d : chemin vers la droite
     * @param h : chemin vers le haut
     * @param b : chemin vers le bas
     * @param s : caractère permettant d'identifier plus facilement le type de pièce
     */
    public CarteTrajet (boolean g, boolean d, boolean h, boolean b, char s) {
        super (s);
        if (s == 'h' || s == 'f' || s == 'g') {
            this.setInvisible();
        }
        gauche = g;
        droite = d;
        bas = b;
        haut = h; 
    }

    public boolean getDroite () {
        return droite;
    }

    public boolean getGauche () {
        return gauche;
    }
    
    public boolean getHaut () {
        return haut;
    }

    public boolean getBas () {
        return bas;
    }

    /**
     * Vérifie la compatibilité avec lea carte du haut
     * Idem pour les fonctions qui suivent
     * @param c : carte se situant au dessus de celle qu'on souhaite poser
     * @return
     */
    public boolean estCompatibleHaut (Carte c) {
        if (c == null) {
            return true;
        } else if (c instanceof CarteTrajet) {
            return ((this.haut && ((CarteTrajet )c).getBas()) || (!(this.haut) && !((CarteTrajet )c).getBas()));
        } else {
            return false;
        }
    }

    public boolean estCompatibleBas (Carte c) {
        if (c == null) {
            return true;
        } else if (c instanceof CarteTrajet) {
            return ((this.bas && ((CarteTrajet )c).getHaut()) || (!(this.bas) && !((CarteTrajet )c).getHaut()));
        } else {
            return false;
        }
    }

    public boolean estCompatibleDroite (Carte c) {
        if (c == null) {
            return true;
        } else if (c instanceof CarteTrajet) {
            return ((this.droite && ((CarteTrajet )c).getGauche()) || (!(this.droite) && !((CarteTrajet )c).getGauche()));
        } else {
            return false;
        }
    }

    public boolean estCompatibleGauche (Carte c) {
        if (c == null) {
            return true;
        } else if (c instanceof CarteTrajet) {
            return ((this.gauche && ((CarteTrajet )c).getDroite()) || (!(this.gauche) && !((CarteTrajet )c).getDroite()));
        } else {
            return false;
        }
    }

    /**
     * Vérifie la compatibilité avec toutes les cartes autour
     * @return true si toutes les fonctions précédentes renvoient true, return false sinon
     */
    public boolean estCompatible (Carte haut, Carte bas, Carte gauche, Carte droite) {
        if (haut == null && bas == null && gauche == null && droite == null) {
            return false;
        }
        return (this.estCompatibleHaut(haut) && this.estCompatibleBas(bas) && this.estCompatibleGauche(gauche) && this.estCompatibleDroite(droite));
    }

    /**
     * Retourne la carte
     */
    public void inverserValeurs () {
        boolean temp = droite;
        droite = gauche;
        gauche = temp;
        boolean temp2 = haut;
        haut = bas;
        bas = temp2;
    }

    /**
     * Affichage indépendant en fonction du type de pièces
     */
    public String toString() {
        if (!getFace()) {
            return ("0");
        } else {
            String l = "";
            if (getSymbole() == 'a') {
                l += "-'-";
            } else if (getSymbole() == 'b') {
                l += " |-";
            } else if (getSymbole() == '|') {
                l += " | ";
            } else if (getSymbole() == '+' || getSymbole() == 's' || getSymbole() == 'f') {
                l += " + ";
            } else if (getSymbole() == '-') {
                l += " - ";
            } else if (getSymbole() == 'c' || getSymbole() == 'h') {
                l += " ,-";
            } else if (getSymbole() == 'd') {
                l += "-  ";
            } else if (getSymbole() == 'g') {
                l += "-, ";
            }
            return l;
        }
    }

    @Override
    public void setVisible () {
        super.setVisible();
        String path = "";
        switch (this.getSymbole()) {
            case 'g' : path = "Ressources/SaboteurTresor0bis.png"; break;
            case 'h' : path = "Ressources/SaboteurTresor0.png"; break;
            case 'f' : path = "Ressources/SaboteurTresor1.png"; break;
        }
        //try{
            Image i = new ImageIcon(path).getImage();
            Image newimg = i.getScaledInstance(this.getsizeiconX(), this.getsizeiconY(), Image.SCALE_SMOOTH); //Redimentionne la piece
            setImage(newimg);
        //}catch (FileNotFoundException e){ }
    }
    




}