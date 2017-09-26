package States;

import Simulation.ReactionDiffusionSim;

import java.awt.*;

public interface State {
    public void tick(ReactionDiffusionSim rds);
    public void render(Graphics g, ReactionDiffusionSim rds);
}
