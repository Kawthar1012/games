import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;

public class PlateauPuzzle extends Plateau{

    private static final long serialVersionUID = 1L;
    /**
     * Utile pour la creation des pieces dans la boite
     * a modifie comme bon le semble
     */
    public static final int X_SIZE = 4;
    /**
     * Utile pour la creation des pieces dans la boite
     */
    public static final int Y_SIZE = 4;


    private final LinkedList<PuzzlePiece> listePieceJoueur;

    /**
     * Renseigne a la vue le nombre de Piece a afficher
     */
    private final int nbrMaxPieceAfficher = 4;
    
    /**
     * Au depart la premiere Piece est deja placer elle sert de reference
     */
    public PlateauPuzzle(){
        super(X_SIZE,Y_SIZE); //Un puzzle 4*4
        PuzzlePiece zero = Boites.piocherPuzzle(0);
        setBounds(1,1,zero.getGlobalWidth(),zero.getGlobalHeight());//permet d'afficher le plateau
        this.listePieceJoueur = new LinkedList<>();
        setPiece(0, 0, zero);
    }

    public LinkedList<PuzzlePiece> getPiecesJoueurs(){
        remplirMainJoueur();
        return this.listePieceJoueur;
    }

    /**
     * Permet de maintenir la case du joueur non vide 
     * tant qu'y a des pieces diponibles
     * <p> Ajout du listener eventuelement</p>
     * <p> Retire de la liste les elements deja presents
     * dans le plateau </p>
     */
    private void remplirMainJoueur(){
        //Netoyage
        for(int i=0;i<this.listePieceJoueur.size();i++){
            if(estPresent(this.listePieceJoueur.get(i))){
                this.listePieceJoueur.remove(i);
            }
        }
        for(int i=this.listePieceJoueur.size();i<this.nbrMaxPieceAfficher;i++){
            piocher();
        }
    }

    @Override
    public boolean piocher(){
        PuzzlePiece p = Boites.piocherPuzzle((int)(Math.random()*X_SIZE*Y_SIZE));
        if(p==null){             
            return false;
        }
        else{
            p.addMouseListener(new Controleur(){
                @Override
                public void mousePressed(MouseEvent e) {
                    Boites.enregistrer(p);  //ranger dans un compartiment de la boite
                }
            });
            this.listePieceJoueur.add(p);
        }
        return false;
    }

    @Override
    public int getNbPiecesCurr(){
        return nbrMaxPieceAfficher;
    }
    @Override public  void affichageSurTerminal(){
        throw new UnsupportedOperationException();
    }
    @Override
    public void ajouterUnListener(Panneau pan){
        pan.addMouseListener(new Controleur(){
            @Override
            public void mousePressed(MouseEvent e) {  
                PuzzlePiece save =(PuzzlePiece) Boites.retirerPieceEnregistrer();
                if(save!=null){
                    placerUnePiece(save, pan.empl_x, pan.empl_y);
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                //Mauvaise reponse
                pan.setBackground(Color.red);
            }
            @Override
            public void mouseExited(MouseEvent e) { pan.setBackground(null); }

        });
    }
     
    /**
     * Eviter de l'appeler si une des cases sont vides pour question de NullPointeurException
     * @return vrai si tout les elements place sont correct et aucune case n'est vide
     */
    public boolean aGagner(){
        if(estComplet()){
            for(int i=0;i<X_SIZE;i++){
                for(int j=0;j<Y_SIZE;j++){
                    PuzzlePiece p = (PuzzlePiece) getPiece(i, j);
                    if(!p.emplacementCorrect(i, j)){
                        return false;
                    }
                }
            }
            return true; //si tout vas bien
        }
        return false; 
    }

    /**
     * 
     * @param p La PuzzlePiece a placer sur le Plateau
     * @param x coordonnee X du Plateau
     * @param y coordonnee Y du Plateau
     * @return true si l'ajout est autoriser
     */
    public boolean placerUnePiece(PuzzlePiece p, int x, int y){
        if(p==null){return false;}
        if((p.getaUnBordHaut()==(y==0))
            &&(p.getaUnBordGauche()==(x==0))
            && ((p.getaUnBordBas()==(y==Y_SIZE-1))
            &&(p.getaUnBordDroite()==(x==X_SIZE-1))) 
            &&getPiece(x, y)==null){

            setPiece(x, y, p); //si tout est correct on place la piece
            raffraichir();
            return true;
        }
        return false;
    }
}