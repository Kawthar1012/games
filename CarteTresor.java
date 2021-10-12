import java.awt.Image;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;

public class CarteTresor extends CarteTrajet {
    /**
     * Une carte trésor représente la même chose qu'une carte trajet à l'exception qu'elle possède un trésor
     */

    public CarteTresor (boolean g, boolean d, boolean h, boolean b, char s) {
        super (g,d,h,b,s);
    }

    public String toString () {
        return getFace()?super.toString():"0";
    }

    @Override
    public void setInvisible () {
        super.setInvisible();
    }

}