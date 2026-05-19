package mods.dieselization.world.entity;

import mods.dieselization.api.core.DieselizationConstants;
import mods.dieselization.entity.DieselLocomotive;
import mods.dieselization.entity.DieselShunterLocomotive;
import mods.dieselization.screen.locomotive.VariablePoweredSteamLocomotive;
import mods.railcraft.world.entity.vehicle.locomotive.SteamLocomotive;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DieselizationEntities {

    private static final DeferredRegister<EntityType<?>> deferredRegister =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, DieselizationConstants.MODID);

    public static void register(IEventBus modEventBus) {
        deferredRegister.register(modEventBus);
    }

    public static final DeferredHolder<EntityType<?>, EntityType<DieselLocomotive>> DIESEL_LOCOMOTIVE =
            deferredRegister.register("diesel_locomotive",
                    () -> create("diesel_locomotive",
                            EntityType.Builder
                                    .<DieselLocomotive>of(DieselLocomotive::new, MobCategory.MISC)
                                    .clientTrackingRange(256)
                                    .updateInterval(2)
                                    .sized(0.98F, 1F)));
    public static final DeferredHolder<EntityType<?>, EntityType<DieselShunterLocomotive>> DIESEL_SHUNTER_LOCOMOTIVE =
            deferredRegister.register("diesel_shunter_locomotive",
                    () -> create("diesel_shunter_locomotive",
                            EntityType.Builder
                                    .<DieselShunterLocomotive>of(DieselShunterLocomotive::new, MobCategory.MISC)
                                    .clientTrackingRange(256)
                                    .updateInterval(2)
                                    .sized(0.98F, 1F)));
    public static final DeferredHolder<EntityType<?>, EntityType<SteamLocomotive>> STEAM_POWERED_LOCOMOTIVE =
            deferredRegister.register("steam_powered_locomotive",
                    () -> create("steam_powered_locomotive",
                            EntityType.Builder
                                    .<SteamLocomotive>of(VariablePoweredSteamLocomotive::new, MobCategory.MISC)
                                    .clientTrackingRange(256)
                                    .updateInterval(2)
                                    .sized(0.98F, 1F)));

    private static <T extends Entity> EntityType<T> create(String registryName,
                                                           EntityType.Builder<T> builder) {
        return builder.build(DieselizationConstants.rl(registryName).toString());
    }
}
