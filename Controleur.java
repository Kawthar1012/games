import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/**
 * Cette classe permettra d'utiliser plus faciment les Interfaces
 * des Listener sans devoir a les declarers a chaque creation
 * Il suffut juste de les Overrider
 */
public class Controleur implements MouseListener{

    // Des methodes eventuels a reecrire dans les sous-classe de Pieces
    //Permettent d'inter-agir avec la sourie
    @Override
    public void mouseEntered(MouseEvent e) {  }
    
    @Override
    public void mouseExited(MouseEvent e) {  }

    @Override
    public void mousePressed(MouseEvent e) {  }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {    }
}