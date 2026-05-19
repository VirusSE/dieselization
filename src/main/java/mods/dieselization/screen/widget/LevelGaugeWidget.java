package mods.dieselization.screen.widget;

import mods.railcraft.gui.widget.Widget;
import mods.railcraft.world.level.material.StandardTank;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.List;

public class LevelGaugeWidget extends Widget {
    public final StandardTank tank;

    public LevelGaugeWidget(StandardTank tank, int x, int y, int u, int v, int w, int h) {
        super(x, y, u, v, w, h);
        this.tank = tank;

    }

    public List<Component> getToolTip() {
        return this.tank.getTooltip();
    }


}
