package mods.dieselization.item;

import mods.dieselization.api.core.DieselizationConstants;
import mods.dieselization.entity.DieselShunterLocomotive;
import mods.dieselization.item.components.FuelFilterItem;
import mods.dieselization.entity.DieselLocomotive;
import mods.dieselization.screen.locomotive.VariablePoweredSteamLocomotive;
import mods.railcraft.api.item.MinecartFactory;
import mods.railcraft.world.entity.vehicle.locomotive.SteamLocomotive;
import mods.railcraft.world.item.LocomotiveItem;
import mods.railcraft.world.item.component.LocomotiveColorComponent;
import mods.railcraft.world.item.component.LocomotiveWhistlePitchComponent;
import mods.railcraft.world.item.component.RailcraftDataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.swing.*;

public class DieselizationItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DieselizationConstants.MODID);
    private static final DeferredRegister.Items deferredRegister = ITEMS;

    public static final DeferredItem<LocomotiveItem> DIESEL_LOCOMOTIVE = ITEMS.registerItem("diesel_locomotive",
            properties -> new DieselLocomotiveItem(DieselLocomotive::new, properties.stacksTo(1)
                    .component(RailcraftDataComponents.LOCOMOTIVE_COLOR,
                        new LocomotiveColorComponent(DyeColor.GREEN, DyeColor.RED))
                    .component(RailcraftDataComponents.LOCOMOTIVE_WHISTLE_PITCH,
                        LocomotiveWhistlePitchComponent.NO_WHISTLE)));
    public static final DeferredItem<LocomotiveItem> DIESEL_SHUNTER_LOCOMOTIVE = ITEMS.registerItem("diesel_shunter_locomotive",
            properties -> new DieselLocomotiveItem(DieselShunterLocomotive::new, properties.stacksTo(1)
                    .component(RailcraftDataComponents.LOCOMOTIVE_COLOR,
                            new LocomotiveColorComponent(DyeColor.ORANGE, DyeColor.RED))
                    .component(RailcraftDataComponents.LOCOMOTIVE_WHISTLE_PITCH,
                            LocomotiveWhistlePitchComponent.NO_WHISTLE)));
    public static final DeferredItem<LocomotiveItem> STEAM_POWERED_LOCOMOTIVE = ITEMS.registerItem("steam_powered_locomotive",
            properties -> new LocomotiveItem(VariablePoweredSteamLocomotive::new, properties.stacksTo(1)
                    .component(RailcraftDataComponents.LOCOMOTIVE_COLOR,
                            new LocomotiveColorComponent(DyeColor.PURPLE, DyeColor.RED))
                    .component(RailcraftDataComponents.LOCOMOTIVE_WHISTLE_PITCH,
                            LocomotiveWhistlePitchComponent.NO_WHISTLE)));

    /*public static final DeferredItem<LocomotiveItem> STEAM_LOCOMOTIVE =
            deferredRegister.registerItem("steam_locomotive", properties ->
                    new LocomotiveItem(SteamLocomotive::new,
                            properties.stacksTo(1)
                                    .component(RailcraftDataComponents.LOCOMOTIVE_COLOR,
                                            new LocomotiveColorComponent(DyeColor.LIGHT_GRAY, DyeColor.GRAY))
                                    .component(RailcraftDataComponents.LOCOMOTIVE_WHISTLE_PITCH,
                                            LocomotiveWhistlePitchComponent.NO_WHISTLE)));*/

    public static final DeferredItem<Item> FUEL_FILTER = ITEMS.register("fuel_filter", () -> new FuelFilterItem(new Item.Properties(), 0));
    public static final DeferredItem<Item> LOCOMOTIVE_WRENCH = registerBasic("locomotive_wrench");
    public static final DeferredItem<Item> HOTBULB_ENGINE = registerBasic("hotbulb_engine");
    //public static final DeferredItem<Item> TWOSTROKE_ENGINE = registerBasic("engine");
    //public static final DeferredItem<Item> FOURSTROKE_ENGINE = registerBasic("engine");

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private static DeferredItem<Item> registerBasic(String name) {
        return ITEMS.registerSimpleItem(name);
    }

    private static DeferredItem<Item> registerLocomotive(String name, MinecartFactory minecartPlacer, LocomotiveColorComponent locoColorComponent) {
        return ITEMS.registerItem(name, properties -> new LocomotiveItem(minecartPlacer,
                properties.stacksTo(1)
                    .component(RailcraftDataComponents.LOCOMOTIVE_COLOR, locoColorComponent)
                    .component(RailcraftDataComponents.LOCOMOTIVE_WHISTLE_PITCH, LocomotiveWhistlePitchComponent.NO_WHISTLE)));
    }

}