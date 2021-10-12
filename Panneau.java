import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


/**
 * Un JPanel Etiquete avec des coordonnees (x,y)
 * Peut éventuelment prendre une image de fond
 */
public class Panneau extends JPanel{

    private static final long serialVersionUID = 1L;

    /**
     * Coordonner de la case du plateau ou il se trouve
     */
    final int empl_x, empl_y;


    /**
     * 
     * @param x int
     * @param y int
     */
    public Panneau(int x,int y){
        empl_x=x; empl_y = y;
        addMouseListener(new Controleur(){
            @Override
            public void mouseEntered(MouseEvent e) {  
                setBorder(BorderFactory.createLineBorder(Color.BLACK) );
            }
            @Override
            public void mouseExited(MouseEvent e) {  
                setBorder(null);
            }
        });
    }

    @Override
    public String toString(){
        return "Panneau("+this.empl_x+","+this.empl_y+")";
    }
}