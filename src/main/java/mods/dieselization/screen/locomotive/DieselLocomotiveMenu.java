package mods.dieselization.screen.locomotive;


import mods.dieselization.api.core.DieselizationConstants;
import mods.dieselization.entity.BaseFuelLocomotive;
import mods.dieselization.entity.DieselLocomotive;
import mods.dieselization.screen.DieselizationMenuTypes;
import mods.railcraft.gui.widget.FluidGaugeWidget;
import mods.railcraft.gui.widget.GaugeWidget;
import mods.railcraft.world.inventory.LocomotiveMenu;
import mods.railcraft.world.inventory.slot.OutputSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DieselLocomotiveMenu extends LocomotiveMenu<BaseFuelLocomotive> {

    private static final ResourceLocation TEXTURE_LOCATION =
            ResourceLocation.fromNamespaceAndPath(DieselizationConstants.MODID, "textures/gui/diesel_locomotive_gui.png");
    public static final int HEIGHT = 205;

    public DieselLocomotiveMenu(int id, Inventory playerInv, BaseFuelLocomotive locomotive) {
        super(DieselizationMenuTypes.DIESEL_LOCOMOTIVE.get(), id, playerInv, locomotive, HEIGHT);

        this.addWidget(
                new FluidGaugeWidget(locomotive.getTankManager().get(0), 53, 23, 176, 0, 16, 47));
        /*this.addWidget(
                new );*/

        //this.addWidget(new GaugeWidget(locomotive);
        //this.addDataSlot(new SimpleDataSlot(() -> Math.round(loco
    }

    protected void addSlots(DieselLocomotive locomotive) {
        this.addSlot(new OutputSlot(locomotive, 1, 152, 56));
        this.addSlot(new OutputSlot(locomotive, 2, 116, 56));
    }
}
