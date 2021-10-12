import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Jeux extends JFrame{
    
    private static final long serialVersionUID = 1L;
    /**
     * Qui sera la boite mère de toute l'affichage  Son Layout sera mise a null
     */
    public final static int LONGUEUR = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height; //y
    public final static int LARGEUR = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width; //x
    
    private final Container princ = getContentPane();

    /**
     * Permettra de raffraichir certaines informations quand on passe la sourie dessus
     */
    private JPanel eventPanel;
    
    private JPanel panelPlateau; //Object Plateau
    /**
     * Ici s'affichera les Pieces destines aux joueurss
     */
    private JPanel panelJoueur;
    /**
     * Ici s'affichera les propositions de jeux 
     */
    private JPanel panelChoix;
    /**
     * Ici sera le lieu ou l'on peut communiquer avec le joueur
     */
    private JPanel panelInfo;

    private JButton dominojeux;
    private JButton dominoGojeux;
    private JButton saboteurjeux;
    private JButton puzzlejeux;
    
    
    public Jeux(){
        init();
        giveListener();
        setSize(LARGEUR, LONGUEUR); //width,heigth
        setTitle("Le Super jeu d'Orlando et Kawthar !!!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Initialise le jeu, remet à 0
     */
    private void init(){

        //initialisations
        this.panelPlateau = new JPanel();
        this.panelJoueur = new JPanel();
        this.panelChoix = new JPanel();
        this.panelInfo = new JPanel();
        this.eventPanel = new JPanel();

        this.dominojeux = new JButton("Domino");
        this.dominoGojeux = new JButton("Domino Gommette");
        this.saboteurjeux = new JButton("Saboteur");
        this.puzzlejeux = new JButton("Puzzle");

        // Utile si l'on souhaite rejouer : remet les boîtes à leur état initial
        Boites.viderLesBoites();
        Boites.remettreAZero();
        
        //choix des Layouts     
        this.princ.setLayout(null); //permet d'avoir une liberte absolu sur les emplacements
        this.panelPlateau.setLayout(null);
        this.panelChoix.setLayout(new GridLayout(4,1));
        
        //definition des tailles
        this.panelPlateau.setBounds(0,0,LARGEUR*3/4-2,LONGUEUR);
        this.eventPanel.setBounds(LARGEUR*3/4-2,0,2,LONGUEUR);
        this.panelChoix.setBounds(LARGEUR/3, LONGUEUR/12, LARGEUR/3, LONGUEUR/2);
        this.panelJoueur.setBounds(LARGEUR*3/4,0,LARGEUR*1/4,LONGUEUR*5/6);
        this.panelInfo.setBounds(LARGEUR*3/4, LONGUEUR*5/6, LARGEUR*1/4, LONGUEUR/6);
        
        //definition de la hierarchie
        this.princ.add(this.panelPlateau);
        this.princ.add(this.eventPanel); 
        this.princ.add(this.panelJoueur);
        this.princ.add(this.panelInfo);
        
        this.panelPlateau.add(this.panelChoix);

        this.panelChoix.add(this.saboteurjeux);
        this.panelChoix.add(this.puzzlejeux);
        this.panelChoix.add(this.dominojeux);
        this.panelChoix.add(this.dominoGojeux);
        repaint();
    }

    /**
     * Remplit le paneau du joueur à chaque tour et permet d'accéder aux différents boutons
     * @param arr : cartes du joueur
     * @param cartesTra : cartes chemins du joueur
     * @param cartesAct : cartes action du joueur
     * @param chemin : liste d'indices liés aux cartes chemin du joueur
     * @param action : liste d'indices liés aux cartes action du joueur
     * @param grd : grille du paneau
     * @param handicap : définit si le joueur a un handicap ou non
     * @param plateau : plateau du jeu
     * @param suiv : bouton accédant au joueur suivant
     */
    private void remplirPanelJoueurSaboteur (LinkedList<? extends Piece> arr, LinkedList<? extends Piece> cartesTra, LinkedList<? extends Piece> cartesAct,LinkedList<Integer> chemin, LinkedList<Integer> action,GridLayout grd, boolean handicap,Plateau plateau,JButton suiv){
        this.panelJoueur.setVisible(false);
        this.panelJoueur.removeAll();
        if (!(((PlateauSaboteur)plateau).aDesCartes()) && Boites.getPaquetDeCartes().isEmpty()) {
            // fin du jeu pour le joueur s'il n'a plus de cartes et que la pioche est vide
            this.panelJoueur.add(new JLabel ("Vous ne pouvez plus jouer"));
        } else {
            this.panelJoueur.setLayout(grd);
            String s = "";
            if (handicap) {
                s += "Vous avez un handicap. ";
            }
            s += "Vous pouvez : \n";
            JLabel start = new JLabel (s);
            this.panelJoueur.add(start);
            for (int i=0; i < consignes(arr,cartesTra,cartesAct,chemin,action,grd,handicap,plateau,suiv).length; i++) {
                // ajoute les différentes consignes utilisant tous les paramètres au panneau
                this.panelJoueur.add(consignes(arr,cartesTra,cartesAct,chemin,action,grd,handicap,plateau,suiv)[i]);
            }
            // panneau inférieur contenant les cartes du joueur
            JPanel inf = new JPanel();
            inf.setLayout(new GridLayout(1,arr.size()));
            for(int i=0;i<arr.size();i++){
                inf.add(arr.get(i));
            }
            this.panelJoueur.add(inf);
        }
        this.panelJoueur.setVisible(true);  
    }

    /**
     * Il est conseillee de bien adapter la taille du layout a l'ajout des Pieces
     * @param arr LinkedList<? extends Piece>
     * @param grd GridLayout
     */
    private void remplirPanelJoueurPuzzle(LinkedList<? extends Piece> arr, GridLayout grd ){
        this.panelJoueur.setVisible(false);
        this.panelJoueur.removeAll();
        this.panelJoueur.setLayout(grd);
        for(int i=0;i<arr.size();i++){
            this.panelJoueur.add(arr.get(i));
        }
        this.panelJoueur.setVisible(true);
    }

    /**
     * "active" les bouttons permettant d'accéder aux jeux
     */
    private void giveListener(){
        this.dominojeux.addActionListener( event ->{
            // Boutton valider : n'accède pas à ce jeu car non disponible en IG
            JButton valider = new JButton("Valider");
            valider.addActionListener(e->{
                this.panelChoix.setVisible(false);
                this.panelInfo.setVisible(false);
                this.panelInfo.removeAll();
                this.panelInfo.setLayout(new GridLayout(2,1));
                this.panelInfo.add(new JLabel("Jeu en disponible en exclisivité sur Console"));
                this.panelInfo.add(acceuil());
                this.panelInfo.setVisible(true);
            });
            // Boutton réutilisé par la suite : permet de lancer le jeu sur le terminal
            JButton terminal = new JButton ("Lancement sur terminal");
            terminal.addActionListener(term->{
                this.setState(ICONIFIED);
                repaint();
                JeuDomino j = new JeuDomino(false);
                j.LancerPartieSurTerminal();
            });
            changerpanelChoix(4,"Donnez les noms des Joueurs (2-4 joueurs)",valider,terminal);            
        });

        this.dominoGojeux.addActionListener(event->{
            JButton valider = new JButton("Valider");
            valider.addActionListener(e->{
                this.panelChoix.setVisible(false);
                this.panelInfo.setVisible(false);
                this.panelInfo.removeAll();
                this.panelInfo.setLayout(new GridLayout(2,1));
                this.panelInfo.add(new JLabel("Jeu en disponible en exclisivité sur Console"));
                this.panelInfo.add(acceuil());
                this.panelInfo.setVisible(true);
            });
            JButton terminal = new JButton ("Lancement sur terminal");
            terminal.addActionListener(term->{
                this.setState(ICONIFIED);
                repaint();
                JeuDomino j = new JeuDomino(true);
                j.LancerPartieSurTerminal();
            });
            changerpanelChoix(4,"Donnez les noms des Joueurs (2-4 joueurs)",valider,terminal);      

        });

        this.saboteurjeux.addActionListener(event->{
            JButton valider = new JButton("Valider");
            JButton terminal = new JButton ("Lancement sur terminal");
            terminal.addActionListener(term->{
                this.setState(ICONIFIED);
                repaint();
                JeuSaboteur j = new JeuSaboteur();
                j.LancerPartieSurTerminal();
                
            });
            changerpanelChoix(8,"Donnez les noms des Joueurs (3-8 joueurs)",valider,terminal); 
            valider.addActionListener(e->{
                // lance le jeu uniquement si 3 joueurs ou plus sont entrés et qu'ils ont des noms différents 
                if (nbJoueur() >= 3 && enDouble(names(nbJoueur()))) {
                    JeuSaboteur jeu = new JeuSaboteur();
                    this.eventPanel.setBackground(Color.DARK_GRAY);
                    jeu.initialisation(nbJoueur(),names(nbJoueur()));
                    this.panelInfo.setLayout(new GridLayout(3,2));
                    JButton suiv = new JButton ("Valider");
                    suiv.addActionListener(e3->{
                        // Cas où le joueur a gagné
                        if (((PlateauSaboteur)jeu.getPlateau()).getJoueurGagnant() != null) {
                            // Partie inutile pour l'instant, censé raffraîchir le plateau en raffraîchissant toutes les cases
                            this.panelPlateau.repaint();
                            this.panelJoueur.setVisible(false);
                            this.panelJoueur.removeAll();
                            this.panelJoueur.add(new JLabel("Vous avez gagné !!!!!!"));
                            this.panelJoueur.setVisible(true);
                        } else {
                            // passe au joueur suivant et actualise l'affichage
                            JoueurSuivant(jeu.getPlateau(),suiv);
                            update((PlateauSaboteur)jeu.getPlateau(),suiv);
                        }
                    });
                    // On ne peut pas passer au suivant tant qu'on a rien fait
                    suiv.setEnabled(false);
                    // donne les bouttons et infos au joueur
                    this.panelInfo.add(acceuil());
                    this.panelInfo.add(suiv);
                    this.panelInfo.add(jeu.getnomJoueur());
                    this.panelInfo.add(jeu.getnbPieces());
                    // affiche les cartes action posées devant le joueur
                    if (jeu.handicap()) {
                        PlateauSaboteur j = (PlateauSaboteur) jeu.getPlateau();
                        for (int i=0; i < j.voirHandicap().size(); i++) {
                            this.panelInfo.add(j.voirHandicap().get(i));
                        }
                    }
                    this.panelInfo.repaint();   
                    this.panelPlateau.setVisible(false);
                    this.panelPlateau.removeAll();
                    this.panelPlateau.add(jeu.getPlateau());
                    this.panelPlateau.setVisible(true);
                    // remplit le panneau du joueur à l'aide des méthodes dans la classe jeuSaboteur
                    remplirPanelJoueurSaboteur(jeu.getPiecesJoueurs(),jeu.getCarteTrajet(),jeu.getCarteAction(),jeu.getIndiceTrajet(),jeu.getIndiceAction(),new GridLayout(5,1),jeu.handicap(),jeu.getPlateau(),suiv);

                } else {
                    changerpanelChoix(8,"Veuillez respecter les consignes",valider,terminal); 
                }
                
            });
        });
        this.puzzlejeux.addActionListener(event->{
            JButton valider = new JButton("Valider");
            changerpanelChoix(1, "Donne nous ton nom ", valider);
            valider.addActionListener(e->{
                if (nbJoueur() == 1){
                    runPuzzle();
                }
            });            
        });        
    }

    /**
     * Permet de lancer le jeu Puzzle
     */
    private void runPuzzle(){
        this.eventPanel.setBackground(Color.DARK_GRAY);
        JeuPuzzle jeu = new JeuPuzzle((names(1)[0]==null)?"Anonymous":"Nom du joueur : "+names(1)[0]);
        
        this.panelInfo.setLayout(new GridLayout(4,1));
        JLabel help = new JLabel("Choisissez une des Pieces proposees");
        this.panelInfo.add(acceuil());
        this.panelInfo.add(jeu.getnomJoueur());
        this.panelInfo.add(help);
        this.panelInfo.repaint();

        this.eventPanel.addMouseListener(new Controleur(){
            @Override
            public void mouseEntered(MouseEvent e) { 
                if(jeu.estComplet()){
                    if(jeu.aGagner()){
                        help.setText("Felicitation !!! vous avez gagner");
                    }else{
                        help.setText("Vous avez Perdu, reessayer en revenant au menu principal");
                    }
                }else{
                    if(Boites.aUnePieceENregistre()){
                        help.setText("Placer la piece choisie sur le plateau ci-dessus");
                    }else{
                        help.setText("Choisissez une des Pieces proposees");
                        remplirPanelJoueurPuzzle(jeu.getPiecesJoueurs(), new GridLayout(jeu.getPlateau().getNbPiecesCurr(),1)); 
                    }
                }
                //actualise
                panelPlateau.setVisible(false);
                panelPlateau.setVisible(true);
                help.repaint();
                
            }
        });
        this.panelJoueur.addMouseListener(new Controleur(){
            @Override
            public void mouseEntered(MouseEvent e) { 
                if(jeu.estComplet()){
                    if(jeu.aGagner()){
                        help.setText("Felicitation !!! vous avez gagner");
                    }else{
                        help.setText("Vous avez Perdu, reessayer en revenant au menu principal");
                    }
                }else{
                    if(Boites.aUnePieceENregistre()){
                        help.setText("Placer la piece choisie sur le plateau ci-dessus");
                    }else{
                        help.setText("Choisissez une des Pieces proposees");
                        remplirPanelJoueurPuzzle(jeu.getPiecesJoueurs(), new GridLayout(jeu.getPlateau().getNbPiecesCurr(),1)); 
                    }
                }
                //actualise
                panelPlateau.setVisible(false);
                panelPlateau.setVisible(true);
                help.repaint();
                
            }
        });

        this.panelPlateau.setVisible(false);
        this.panelPlateau.removeAll();
        this.panelPlateau.add(jeu.getPlateau());
        remplirPanelJoueurPuzzle(jeu.getPiecesJoueurs(), new GridLayout(jeu.getPlateau().getNbPiecesCurr()+1,1)); 
        this.panelPlateau.setVisible(true);

    }

    /**
     * Est obtenu apes le choix d'un jeu
     * @param nbJoueur int
     * @param labelInfo String
     * @param valider JButton
     */
    private void changerpanelChoix(int nbJoueur, String labelInfo, JButton valider){
        //Netoyer panelChoix pour preparer l'entrer des utilisateurs
        this.panelChoix.setVisible(false); //cacher le panel durant la modification
        this.panelChoix.removeAll();
        this.panelChoix.setLayout(new GridLayout(nbJoueur+3,1)); //+2 Label & Button
        this.panelChoix.add(new JLabel(labelInfo));
        for(int i=0;i<nbJoueur;i++){
            this.panelChoix.add(new JTextField());
        }
        
        JPanel inferieur = new JPanel();
        inferieur.setLayout(new GridLayout(1,2));
        this.panelChoix.add(inferieur);
        inferieur.add(acceuil()); inferieur.add(valider);
        //afficher les modification
        this.panelChoix.setVisible(true);
    }

    private void changerpanelChoix(int nbJoueur, String labelInfo, JButton valider, JButton terminal){
        changerpanelChoix(nbJoueur, labelInfo, valider);
        this.panelChoix.add(terminal);
    }

    /**
     * Similaire àla fonction remplir le pannel du joueur mais utile uniqument en cas d'erreur
     * @param info : indique l'erreur du joueur
     */
    private void affichageChoix (LinkedList<? extends Piece> cartes, LinkedList<? extends Piece> cartesTra, LinkedList<? extends Piece> cartesAct,LinkedList<Integer> chemin, LinkedList<Integer> action,GridLayout gr,boolean handicap,Plateau plateau,JButton suiv,JLabel info) {
        this.panelJoueur.setVisible(false);
        this.panelJoueur.removeAll();
        this.panelJoueur.setLayout(new GridLayout(5,1));
        String s = "";
        if (handicap) {
            s += "Vous avez un handicap. ";
        }
        s += "Vous pouvez : \n";
        JLabel start = new JLabel (s);
        this.panelJoueur.add(start);
        for (int i=0; i < consignes(cartes,cartesTra,cartesAct,chemin,action,gr,handicap,plateau,suiv).length; i++) {
            this.panelJoueur.add(consignes(cartes,cartesTra,cartesAct,chemin,action,gr,handicap,plateau,suiv)[i]);
        }
        this.panelJoueur.add(info);
        this.panelJoueur.setVisible(true);
    }

    /**
     * 
     * @param nbJoueur int
     * @return String[] noms des Joueurs
     */
    private String[] names (int nbJoueur) {
        String [] n = new String [nbJoueur];
        int x = 0;
        while (x < nbJoueur) {
            for (int i=0; i < this.panelChoix.getComponentCount(); i++) {
                if (this.panelChoix.getComponent(i)  instanceof JTextField) {
                    // remplit le tableau de joueurs en prenant les cases non vides
                    if (!((JTextField)this.panelChoix.getComponent(i)).getText().isEmpty()) {
                        n[x] = ((JTextField)this.panelChoix.getComponent(i)).getText();
                        x++;
                    }
                }
            }
        }
        return n;
    }

    /**
     * Permet de connaître le nombre de joueurs en comptant les cases non vides
     */
    private int nbJoueur () {
        int c = 0;
        for (int i=0; i < this.panelChoix.getComponentCount(); i++) {
            if (this.panelChoix.getComponent(i)  instanceof JTextField) {
                if (!((JTextField)this.panelChoix.getComponent(i)).getText().isEmpty()) {
                    c++;
                }
            }
        }
        return c;
    }

    /**
     * Affiche les bouttons des différentes options du joueur et ajoute ces options aux bouttons
     */
    public JRadioButton [] consignes (LinkedList<? extends Piece> cartes, LinkedList<? extends Piece> cartesTra, LinkedList<? extends Piece> cartesAct, LinkedList<Integer> chemin, LinkedList<Integer> action, GridLayout gr, boolean handicap, Plateau plateau,JButton suiv) {
        if (handicap) {
            // Si le joueur a un handicap, il n'a que deux options
            JRadioButton [] c = new JRadioButton [2];
            c[0] = new JRadioButton ("Jeter une carte");
            c[0].addActionListener(event -> {
                    jouer (cartes,null,new JLabel("Veuillez jeter une des cartes ci-dessus"),'n',plateau,suiv,handicap);
            });
            c[1] = new JRadioButton ("Jouer une carte action");
            c[1].addActionListener(event -> {
                if (cartesAct.isEmpty()) {
                    affichageChoix(cartes, cartesTra, cartesAct, chemin, action,gr, handicap, plateau, suiv,new JLabel("Vous n'avez pas de carte action. Veuillez faire autre chose."));
                } else {
                    jouer (cartesAct,action,new JLabel("Veuillez jouer une des cartes ci-dessus"),'a',plateau,suiv,handicap);
                }
            });
            return c;
        } 
        JRadioButton [] c = new JRadioButton [3];
        c[0] = new JRadioButton("Poser une carte");
        c[0].addActionListener(event -> {
            if (cartesTra.isEmpty()) {
                // Si le joueur n'a pas de carte chemin, on réaffiche le panneau en lui indiquant
                affichageChoix(cartes, cartesTra, cartesAct,chemin, action, gr, handicap, plateau, suiv,new JLabel("Vous n'avez pas de carte chemin. Veuillez faire autre chose."));
            } else {
                jouer (cartesTra,chemin,new JLabel("Veuillez placer une des cartes ci-dessus"),'t',plateau,suiv,handicap);
            }
        });
        c[1] = new JRadioButton("Jeter une carte");
        c[1].addActionListener(event -> {
            jouer (cartes,null,new JLabel("Veuillez jeter une des cartes ci-dessus"),'n',plateau,suiv,handicap);
        });
        c[2] = new JRadioButton("Jouer une carte action");
        c[2].addActionListener(event -> {
            if (cartesAct.isEmpty()) {
                // Même chose si le joueur n'a pas de carte action
                affichageChoix(cartes, cartesTra, cartesAct,chemin, action, gr, handicap, plateau, suiv,new JLabel("Vous n'avez pas de carte action. Veuillez faire autre chose."));
            } else {
                jouer (cartesAct,action,new JLabel("Veuillez jouer une des cartes ci-dessus"),'a',plateau,suiv,handicap);
            }
        });
        return c;
    }

    /**
     * Permet de donner des actions à la souris et d'utiliser les différentes fonctions de plateau en fonction de l'action choisie
     * @param Cartes : Liste de cartes utilisées (normales, chemin ou action)
     * @param indices : Liste d'indices liés aux cartes
     * @param : information donéée au joueur
     * @param action : définit l'action du joueur (n : jeter,t : poser ou a : jouer une carte action)
     */
    private void jouer (LinkedList<? extends Piece> Cartes, LinkedList<Integer> indices,JLabel info, char action, Plateau plateau,JButton suiv,boolean handicap) {
        this.panelJoueur.setVisible(false);
        this.panelJoueur.removeAll();
        this.panelJoueur.setLayout(new GridLayout(2,Cartes.size()));
        JPanel sup = new JPanel ();
        sup.setLayout(new GridLayout(1,Cartes.size()));
        // affiche les cartes utilisables
        for (int i=0; i < Cartes.size(); i++) {
            sup.add(Cartes.get(i));
        }
        this.panelJoueur.add(sup);
        if (action == 't') {
            for (int i=0; i < indices.size(); i++) {
                CarteTrajet c = ((CarteTrajet)Cartes.get(i));
                c.addMouseListener(new Controleur(){
                    @Override
                    public void mousePressed(MouseEvent e) {
                        // Place la carte à l'aide des méthodes enregistrées dans le plateau
                        Boites.enregistrer(c); 
                        panelJoueur.setVisible(false);
                        panelJoueur.removeAll();
                        // Empêche de placer une autre carte (pas le droit à l'erreur)
                        info.setText("Vous venez de placer une carte, piochez.");
                        panelJoueur.add(info);
                        panelJoueur.setVisible(true);
                        // Rend le bouton 'Valider' cliquable pour passer au joueur suivant
                        suiv.setEnabled(true);
                    }
                });
            }
        } else if (action == 'n') {
            for (int i=0; i < Cartes.size(); i++) {
                Carte c = (Carte) Cartes.get(i);
                c.addMouseListener(new Controleur(){
                    @Override
                    public void mousePressed(MouseEvent e) {
                        // jette la carte
                        ((PlateauSaboteur)plateau).jeter(c); 
                        panelJoueur.setVisible(false);
                        panelJoueur.removeAll();
                        info.setText("Vous venez de jeter une carte, piochez.");
                        panelJoueur.add(info);
                        panelJoueur.setVisible(true);
                        suiv.setEnabled(true);
                    }
                });
            }
        } else {
            for (int i=0; i < indices.size(); i++) {
                CarteAction c = (CarteAction) Cartes.get(i);
                int x = indices.get(i);
                c.addMouseListener(new Controleur(){
                    @Override
                    public void mousePressed(MouseEvent e) {
                        // sépare les cas en fonction de la nature de la carte
                        if (((CarteAction)c).getReparation()) {
                            Joueurs(plateau,new JLabel("Cette carte est une carte réparation. Veuillez choisir le joueur que vous souhaitez aider."),x,true,handicap,suiv);
                        } else {
                            Joueurs(plateau,new JLabel("Veuillez choisir l'un des joueurs."),x,false,handicap,suiv);
                        }
                        suiv.setEnabled(true);
                    }
                });
            }
        }
        this.panelJoueur.add(info);
        this.panelJoueur.setVisible(true);
    }

    /**
     * Permet de jouer une carte action
     * @param c : indice de la carte à jouer
     * @param handicap : si la carte est une carte réparation ou non
     * @param handicapgeneral : si le joueur a un handicap ou non
     */
    private void Joueurs (Plateau plateau, JLabel info, int c, boolean handicap,boolean handicapgeneral,JButton suiv) {
        this.panelJoueur.setVisible(false);
        this.panelJoueur.removeAll();
        this.panelJoueur.setLayout(new GridLayout(2,nbJoueur()-1));
        if (!handicap) {
            // carte handicap
            JRadioButton [] j = new JRadioButton [nbJoueur()-1];
            for (int i=0; i < ((PlateauSaboteur) plateau).getAutresInd().size(); i++) {
                // ajoute tous les joueurs sauf le joueur courant
                j[i] = new JRadioButton(((PlateauSaboteur) plateau).JoueurString(((PlateauSaboteur) plateau).getJoueurAutre(i)));
                int x = i;
                j[i].addActionListener(event->{
                    // joue la carte si c'est possible et affiche une erreur sinon
                    if (((PlateauSaboteur)plateau).handicapPossible(((PlateauSaboteur) plateau).getJoueurAutre(x),c)) {
                        ((PlateauSaboteur)plateau).handicap(((PlateauSaboteur)plateau).getJoueurAutre(x),c);
                    } else {
                        affichageChoix(((PlateauSaboteur)plateau).getPiecesJoueur(),((PlateauSaboteur)plateau).trajet(),((PlateauSaboteur)plateau).aJouerP(),((PlateauSaboteur)plateau).trajetIndice(),((PlateauSaboteur)plateau).aJouerIndice(),new GridLayout(5,2),handicapgeneral,plateau,suiv,new JLabel("Vous ne pouvez pas jouer cette carte sur ce joueur."));
                    }
                });
                this.panelJoueur.add(j[i]);
            }
        } else {
            // Même chose que pour la carte handicap mais propose tous les joueurs et fonctionne seulement si la réparation est possible
            JRadioButton [] j = new JRadioButton [nbJoueur()];
            for (int i=0; i < nbJoueur(); i++) {
                j[i] = new JRadioButton(((PlateauSaboteur) plateau).JoueurString(((PlateauSaboteur) plateau).getJoueurs()[i]));
                int x = i;
                j[i].addActionListener(event->{
                    if (((PlateauSaboteur)plateau).reparationPossible(((PlateauSaboteur) plateau).getJoueurs()[x],c)) {
                        ((PlateauSaboteur)plateau).reparation(((PlateauSaboteur)plateau).getJoueurs()[x],c);
                    } else {
                        affichageChoix(((PlateauSaboteur)plateau).getPiecesJoueur(),((PlateauSaboteur)plateau).trajet(),((PlateauSaboteur)plateau).aJouerP(),((PlateauSaboteur)plateau).trajetIndice(),((PlateauSaboteur)plateau).aJouerIndice(),new GridLayout(5,2),handicapgeneral,plateau,suiv,new JLabel("Vous ne pouvez pas jouer cette carte sur ce joueur."));
                    }
                });
                this.panelJoueur.add(j[i]);
            }
        }
        this.panelJoueur.add(info);
        this.panelJoueur.setVisible(true);
    }

    private void JoueurSuivant (Plateau plateau,JButton suiv) {
        if (!(Boites.getPaquetDeCartes().isEmpty())) {
            // pioche tant que la pioche contient des cartes
            ((PlateauSaboteur)plateau).piocher();
        } else {
            // ne fonctionne pas, est censé changer le nom du boutton quand la pioche est vide
            suiv.setVisible(false);
            suiv.setName("Au suivant");
            suiv.setVisible(true);
        }
        // passe au joueur suivant et rend le boutton Valider non cliquable
        ((PlateauSaboteur)plateau).setJSuiv((((PlateauSaboteur)plateau).getCour()+1)%nbJoueur());
        suiv.setEnabled(false);
    }

    /**
     * Met à jour l'affichage des panneaux infos et joueur (et plateau mais ne fonctionne pas pour l'instant)
     * Signale la fin du jeu (ne fonctionne pas pour l'instant)
     */
    private void update (PlateauSaboteur plateau,JButton suiv) {
        this.panelInfo.setVisible(false);
        this.panelInfo.removeAll();
        this.panelInfo.setLayout(new GridLayout(3,2));

        this.panelInfo.add(acceuil());
        this.panelInfo.add(suiv);
        this.panelInfo.add(new JLabel(plateau.getJString()));
        this.panelInfo.add(new JLabel("Nombre de cartes : "+plateau.getNbPiecesCurr()));
        if (plateau.estBloque()) {
        for (int i=0; i < plateau.voirHandicap().size(); i++) {
                this.panelInfo.add(plateau.voirHandicap().get(i));
            }            
        }
        this.panelInfo.repaint(); 
        this.panelInfo.setVisible(true); 
        this.panelInfo.repaint(); 
        if (fin(plateau)) {
            this.panelJoueur.setVisible(false);
            this.panelJoueur.removeAll();
            this.panelJoueur.add(new JLabel("Plus personne ne peut jouer, fin du jeu"));
            this.panelJoueur.setVisible(true);
        } else {
            remplirPanelJoueurSaboteur(plateau.getPiecesJoueur(),plateau.trajet(),plateau.aJouerP(),plateau.trajetIndice(),plateau.aJouerIndice(),new GridLayout(5,2),plateau.estBloque(),plateau,suiv);
        }
    }


    /**
     * @return false si un des noms des joueurs apparaît plus d'une fois, true sinon
     */
    public boolean enDouble (String [] names) {
        for (int i=0; i < names.length; i++) {
            int c = 0;
            for (int j=0; j < names.length; j++) {
                if (names[j].equals(names[i])) {
                    c++;
                }
            }
            if (c > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true si aucun joueur n'a de cartes et que la pioche est vide
     */
    public boolean fin (PlateauSaboteur plateau) {
        int c = 0;
        for (int i=0; i < plateau.joueursLength(); i++) {
            if (plateau.plusDeCartes(plateau.getJoueurs()[i])) {
                c++;
                System.out.println(c);
            }
        }
        if (c == plateau.joueursLength() && Boites.getPaquetDeCartes().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Boutton réutilisé par la suite : réinitialise 
     * tous les jeux et revient à l'affichage du menu
     * @return JButton
     */
    private JButton acceuil(){
        JButton quitter = new JButton("Retour au menu");
        quitter.addActionListener(evt->{
            princ.setVisible(false);
            princ.removeAll();
            init();
            giveListener();
            princ.setVisible(true);
        });
        return quitter;
    }
}