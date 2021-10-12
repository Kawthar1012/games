import java.util.LinkedList;
import javax.swing.JLabel;

public interface InterfaceJeu{

    /**
     * Sera utiliser dans le JPanel de la vue
     * Renseignera l'evolution du tableau
     * @return Plateau
     */
    public Plateau getPlateau();

    public JLabel getnomJoueur();

    public LinkedList<? extends Piece> getPiecesJoueurs();

}