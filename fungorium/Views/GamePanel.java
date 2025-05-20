package fungorium.Views;

import fungorium.Controllers.*;
import fungorium.Controllers.GombászListeners.*;
import fungorium.Controllers.RovarászListeners.*;
import fungorium.Models.Játék;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
<<<<<<< HEAD
    private GameController controller; // A játék vezérlő objektuma, amely a logikát irányítja
    private JLabel statusLabel; // Az aktuális játékos nevét mutató státuszcímke

    public GamePanel() {
        Játék játék = new Játék(); // Új játék példány létrehozása
        FungoriumView fungoriumView = new FungoriumView(játék.getFungorium()); // A fungórium megjelenítését szolgáló nézet
        this.controller = new GameController(fungoriumView, játék); // A vezérlő összekapcsolása a nézettel és a játékkal
=======
    private GameController controller;
    private JLabel statusLabel;
    private Runnable showVictoryScreen;

    public GamePanel() {
        this(null);
    }
    
    public GamePanel(Runnable showVictoryScreen) {
        this.showVictoryScreen = showVictoryScreen;
        Játék játék = new Játék();
        FungoriumView fungoriumView = new FungoriumView(játék.getFungorium());
        this.controller = new GameController(fungoriumView, játék);
>>>>>>> fdde37d0261b0411e39e250832773c43362ef950

        setLayout(new BorderLayout()); 
        
        add(fungoriumView, BorderLayout.CENTER);

        // Status JLabel az alsó bal sarokba
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setForeground(Color.BLACK);
        add(statusLabel, BorderLayout.SOUTH);

        updateStatusLabel(); // A státusz címke frissítése az aktuális játékszemélyre vonatkozó adatokkal

        // MouseListener hozzáadása a fungórium nézethez, a felhasználói interakciók kezeléséhez
        fungoriumView.addMouseListener(new FungoriumSelectionMouseAdapter(controller));

        fungoriumView.addMouseListener(new GombászSpóraSzórásMouseAdapter(controller));

        fungoriumView.addKeyListener(new GombászFonalNövesztésKeyAdapter(controller));
        fungoriumView.addKeyListener(new GombászSpóraSzórásKeyAdapter(controller));
        fungoriumView.addKeyListener(new GombászTestFejlesztésKeyAdapter(controller));
        fungoriumView.addKeyListener(new GombászTestNövesztésKeyAdapter(controller));

        fungoriumView.addMouseListener(new RovarászVágásMouseAdapter(controller));
        
        fungoriumView.addKeyListener(new RovarászElhelyezésKeyAdapter(controller));
        fungoriumView.addKeyListener(new RovarászMozgatásKeyAdapter(controller));
        fungoriumView.addKeyListener(new RovarászSpóraEvésKeyAdapter(controller));
        fungoriumView.addKeyListener(new RovarászVágásKeyAdapter(controller));

        fungoriumView.addKeyListener(new KörVégeKeyAdapter(controller));

        fungoriumView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateStatusLabel();
            }
        });

        fungoriumView.requestFocusInWindow();
        fungoriumView.setFocusable(true);
    }

    public GameController getController() {
        return controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void updateStatusLabel() {
        String név = controller.getJáték().getAktuálisJátékos().getName();
        statusLabel.setText("Aktuális játékos: " + név);

        if (controller.getJáték().vége() && showVictoryScreen != null) {
            showVictoryScreen.run();
        }
    }

    public FungoriumView getFungoriumView() {
        return controller.getFungoriumView();
    }
}
