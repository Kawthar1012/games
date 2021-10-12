import java.awt.EventQueue;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args){

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Jeux().setVisible(true);
            }
        });

        //JeuSaboteur j = new JeuSaboteur();
        //j.LancerPartieSurTerminal();
    }
}