package mods.dieselization.item;

import mods.dieselization.Dieselization;
import mods.dieselization.api.core.DieselizationConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class DieselizationCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DieselizationConstants.MODID);

    public static final Supplier<CreativeModeTab> DIESELIZATION_ITEMS_TAB = CREATIVE_MODE_TAB.register("dieselization_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(DieselizationItems.DIESEL_LOCOMOTIVE.get()))
                    .title(Component.translatable("creativetab.dieselization.items"))
                    .displayItems((itemDisplayParameter, output) -> {
                        output.accept(DieselizationItems.DIESEL_LOCOMOTIVE);
                        output.accept(DieselizationItems.DIESEL_SHUNTER_LOCOMOTIVE);
                        output.accept(DieselizationItems.STEAM_POWERED_LOCOMOTIVE);
                        output.accept(DieselizationItems.FUEL_FILTER);
                        output.accept(DieselizationItems.LOCOMOTIVE_WRENCH);
                        output.accept(DieselizationItems.HOTBULB_ENGINE);
                        //output.accept(DieselizationItems.TWOSTROKE_ENGINE);
                        //output.accept(DieselizationItems.FOURSTROKE_ENGINE);
        }).build());

    /*
    public static final Supplier<CreativeModeTab> DIESELIZATION_ITEMS_TAB = CREATIVE_MODE_TAB.register("dieselization_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(DieselizationItems.DIESEL_LOCOMOTIVE.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(DieselizationConstants.MODID, "dieselization_items_tab"))
                    .title(Component.translatable("creativetab.dieselization.items"))
                    .displayItems((itemDisplayParameter, output) -> {
                        output.accept(DieselizationItems.FUEL_FILTER);
        }).build());*/

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
