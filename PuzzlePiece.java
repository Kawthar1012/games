import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

public class PuzzlePiece extends Piece {
    
    private static final long serialVersionUID = 1L;

    /**
     * fait referece au tableau situer dans le plateau
     */
    private final int empl_x,empl_y;
    /**
     * L'image qui sera decouper pour 
     */
    private BufferedImage landscape; //herite de java.awt.Image

    /**
     * Renseigne sur la presence des bords de la Piece
     */
    private final boolean aUnBordHaut,aUnBordBas,aUnBordGauche,aUnBordDroite;

    /**
     * Aucune PuzzlePiece dois avoir le meme coordonnee (x,y)
     * @param x coordonee x
     * @param y coordonee y
     * @param h ?haut 
     * @param b ?bas
     * @param g ?gauche
     * @param d ?droite
     */
    public PuzzlePiece(int x, int y,boolean h, boolean b, boolean g, boolean d){
        super();
        try{
            this.landscape = ImageIO.read(new File("./Ressources/landscape.png"));
        }catch(IOException e){
            System.out.println("Erreur dans PuzzlePiece.java : fichier landscape introuvable");
        }
        this.empl_x = x; this.empl_y=y;
        this.aUnBordHaut = h;
        this.aUnBordBas = b;
        this.aUnBordGauche = g;
        this.aUnBordDroite = d;
        int x_dir = this.landscape.getWidth()/PlateauPuzzle.X_SIZE;
        int y_dir = this.landscape.getHeight()/PlateauPuzzle.Y_SIZE;

        setImage(this.landscape.getSubimage(x_dir*x, y_dir*y, x_dir,y_dir));
        setBorder(BorderFactory.createLineBorder(Color.BLACK) );

        addMouseListener(new Controleur(){
            @Override
            public void mouseEntered(MouseEvent e) {  
                setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY) );
            }
            @Override
            public void mouseExited(MouseEvent e) {  
                setBorder(BorderFactory.createLineBorder(Color.BLACK) );
            }
        });
    }

    public int getGlobalWidth () {
        return this.landscape.getWidth();
    }

    public int getGlobalHeight () {
        return this.landscape.getHeight();
    }



    public boolean getaUnBordHaut(){  return this.aUnBordHaut; }

    public boolean getaUnBordGauche(){  return this.aUnBordGauche; }

    public boolean getaUnBordBas(){  return this.aUnBordBas; }

    public boolean getaUnBordDroite(){  return this.aUnBordDroite; }

    @Override
    public String toString(){
        return "["+this.empl_x+";"+this.empl_y+"]";
    }

    /**
     * Verifie si la piece est sur le bon coordonne
     * @param x
     * @param y
     * @return true si le PuzzlePiece est a la bonne emplacement
     */
    public boolean emplacementCorrect(int x,int y){
        return (this.empl_x==x&&this.empl_y==y);
    }
    

}