package fungorium.ReViews;

import fungorium.ReModels.Tektonrész;

public class RovarView extends EntitásView {
    public RovarView(Tektonrész tr) {
        super(tr);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
