package fungorium.ReViews;

import java.awt.Color;
import java.awt.Graphics;

import fungorium.ReModels.*;

public class RovarView extends EntitásView {
    private Rovar rovar;

    public RovarView(Rovar rovar) {
        this.rovar = rovar;
    }

    @Override
    public boolean isValid() {
        return rovar != null && rovar.érvényesE();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (rovar.getFaj()) {
            case Aegithus:
                g.setColor(Color.PINK);
                break;
            case Bystodes:
                g.setColor(Color.CYAN);
                break;
            case Cryptophilus:
                g.setColor(Color.ORANGE);
                break;
            case Danae:
                g.setColor(Color.WHITE);
                break;
        }

        int[] xPoints = { getWidth() / 2, (getWidth() - 20) / 2, (getWidth() + 20) / 2 };
        int[] yPoints = { (getHeight() - 20) / 2, (getHeight() + 20) / 2, (getHeight() + 20) / 2 };
        g.fillPolygon(xPoints, yPoints, 3);

        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 3);
    }

    public Rovar getRovar() {
        return rovar;
    }
}
