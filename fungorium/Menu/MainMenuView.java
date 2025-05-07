package fungorium.Menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuView extends JPanel {

    public MainMenuView(MenuController controller) {
        // A JPanel használata BoxLayout-tal, hogy a komponensek függőlegesen középen helyezkedjenek el
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Beállítjuk a méreteket
        setPreferredSize(new Dimension(200, 200));

        // Menü felirat
        JLabel titleLabel = new JLabel("Menü", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Nagyobb, vastag betűk a címnek
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Középre igazítjuk

        // Játékosok gomb
        JButton playerBtn = new JButton("Játékosok");
        playerBtn.setBackground(Color.BLACK);
        playerBtn.setForeground(Color.WHITE);
        playerBtn.setFont(new Font("Arial", Font.PLAIN, 18)); // A gomb szövege nagyobb
        playerBtn.setAlignmentX(Component.CENTER_ALIGNMENT); // Középre igazítjuk
        playerBtn.addActionListener(e -> controller.showPlayerEdit());

        // Játék kezdése gomb
        JButton startBtn = new JButton("Játék kezdése");
        startBtn.setBackground(Color.BLACK);
        startBtn.setForeground(Color.WHITE);
        startBtn.setFont(new Font("Arial", Font.PLAIN, 18)); // A gomb szövege nagyobb
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT); // Középre igazítjuk
        startBtn.addActionListener(e -> controller.startGame());

        // Komponensek hozzáadása
        add(titleLabel);  // Menü cím
        add(Box.createRigidArea(new Dimension(0, 20))); // Köztes tér a cím és a gombok között
        add(playerBtn);   // Játékosok gomb
        add(Box.createRigidArea(new Dimension(0, 10))); // Köztes tér a két gomb között
        add(startBtn);    // Játék kezdése gomb
    }
}
