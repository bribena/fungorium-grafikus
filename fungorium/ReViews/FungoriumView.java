package fungorium.ReViews;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import fungorium.ReModels.Fungorium;

public class FungoriumView extends JPanel {
    public Fungorium fungorium;
    
    public FungoriumView(Fungorium f) {
        fungorium = f;
        setLayout(new GridLayout(f.getWidth(), f.getHeight()));
        
        for (int y = 0; y < fungorium.getHeight(); ++y) {
            for (int x = 0; x < fungorium.getWidth(); ++x) {
                add(new TektonrészView(f, x, y));
            }
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TektonrészView tw = (TektonrészView)getComponentAt(e.getPoint());
                System.out.println(tw.x + ", " + tw.y);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
