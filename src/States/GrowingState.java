package States;

import Simulation.ReactionDiffusionSim;

import java.awt.*;

public class GrowingState implements State{

    @Override
    public void tick(ReactionDiffusionSim rds) {
        double A = 0;
        double B = 0;

        for (int i = 1; i < rds.getWidth() - 1; i++){
            for (int j = 1; j < rds.getHeight() - 1; j++){
                A = rds.getPrevA(i, j) + rds.dA(i, j);
                B = rds.getPrevB(i, j) + rds.dB(i, j);

                if (A > 1){
                   A = 1.0;
                } else if (A < 0) {
                    A = 0;
                }

                if (B > 1){
                    B = 1.0;
                } else if (B < 0) {
                    B = 0;
                }

                rds.setA(i, j, A);
                rds.setB(i, j, B);
            }
        }
    }

    @Override
    public void render(Graphics g, ReactionDiffusionSim rds) {
        for (int i = 1; i < rds.getWidth() - 1; i++) {
            for (int j = 1; j < rds.getHeight() - 1; j++) {
                g.setColor(rds.extractColor(i, j));
                g.drawRect(i, j, 1, 1);
            }
        }

        rds.setPrev(rds.getModel());
    }

}
