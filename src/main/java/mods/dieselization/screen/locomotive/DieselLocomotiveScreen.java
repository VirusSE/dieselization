package mods.dieselization.screen.locomotive;

import mods.dieselization.api.core.DieselizationConstants;
import mods.railcraft.client.gui.screen.inventory.LocomotiveScreen;
import mods.railcraft.client.gui.screen.inventory.widget.FluidGaugeRenderer;
import mods.railcraft.client.gui.screen.inventory.widget.GaugeRenderer;
import mods.railcraft.gui.widget.FluidGaugeWidget;
import mods.railcraft.gui.widget.GaugeWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class DieselLocomotiveScreen extends LocomotiveScreen<DieselLocomotiveMenu> {

    private static final ResourceLocation TEXTURE_LOCATION =
            DieselizationConstants.rl("textures/gui/diesel_locomotive_gui.png");

    public DieselLocomotiveScreen(DieselLocomotiveMenu menu, Inventory inv, Component title) {
        super(menu, inv, title, "steam");
        this.imageHeight = DieselLocomotiveMenu.HEIGHT;
        this.inventoryLabelY = 110;
        for (var w : this.menu.getWidgets()) {
            if (w instanceof FluidGaugeWidget fluidGaugeWidget) {
                this.registerWidgetRenderer(new FluidGaugeRenderer(fluidGaugeWidget));
            }
            if (w instanceof GaugeWidget gaugeWidget) {
                this.registerWidgetRenderer(new GaugeRenderer(gaugeWidget));
            }
        }
    }

    @Override
    public @NotNull ResourceLocation getWidgetsTexture() {
        return TEXTURE_LOCATION;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
        int x = (width - this.getXSize()) / 2;
        int y = (height - this.getYSize()) / 2;
        /*
        // TODO refactor old boiler stuff
        if (this.menu.getLocomotive().boiler().hasFuel()) {
            int scale = this.menu.getLocomotive().boiler().getBurnProgressScaled(12);
            guiGraphics.blit(TEXTURE_LOCATION, x + 99, y + 33 - scale, 176, 59 - scale, 14, scale + 2);
        }*/
    }
}
