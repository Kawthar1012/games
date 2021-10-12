public class Carte extends Piece {
    /**
     * True si on voit la carte, false si elle est retournée
     */
    private boolean faceVisible; //= true;
   

    public Carte (char s) {
        super (s);
        faceVisible = true;
    }

    public String toString () {
        return super.toString();
    }

    public void setInvisible () {
        faceVisible = false;
    }

    public boolean getFace () {
        return faceVisible;
    }

    public void setVisible () {
        faceVisible = true;
    }


    

    
}