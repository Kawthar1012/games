import java.util.LinkedList;

import javax.swing.JLabel;

public class JeuPuzzle implements InterfaceJeu{

    private final PlateauPuzzle plateau;
    private final String nomJoueur;

    public JeuPuzzle(String nomJ){
        this.plateau = new PlateauPuzzle();
        this.nomJoueur = nomJ;        
    }

    public boolean aGagner(){
        return this.plateau.aGagner();
    }

    public boolean estComplet(){
        return this.plateau.estComplet();
    }
    
    @Override
    public Plateau getPlateau(){
        return this.plateau;
    }
    @Override
    public JLabel getnomJoueur(){
        return new JLabel(this.nomJoueur);
    }
    @Override
    public LinkedList<PuzzlePiece> getPiecesJoueurs(){
        return this.plateau.getPiecesJoueurs();
    }

}