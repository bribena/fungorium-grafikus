package fungorium.ReViews;

// import java.awt.event.KeyAdapter;
// import java.awt.event.KeyEvent;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import fungorium.ReModels.Fungorium;

public class FungoriumView extends JPanel {
    private Fungorium fungorium;
    
    public FungoriumView(Fungorium f) {
        fungorium = f;

        setLayout(new GridLayout(f.getWidth(), f.getHeight()));
        
        for (int y = 0; y < fungorium.getHeight(); ++y) {
            for (int x = 0; x < fungorium.getWidth(); ++x) {
                add(new TektonrészView(f, x, y));
            }
        }

        // addMouseListener(new MouseAdapter() {
        //     @Override
        //     public void mouseClicked(MouseEvent e) {
        //         TektonrészView tw = (TektonrészView)getComponentAt(e.getPoint());
        //         System.out.println(tw.x + ", " + tw.y);
        //     }
        // });

        // addKeyListener(new KeyAdapter() {
        //     @Override
        //     public void keyReleased(KeyEvent e) {
        //         f.törés();
        //         System.out.println("Törés");
        //     }
        // });

        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
