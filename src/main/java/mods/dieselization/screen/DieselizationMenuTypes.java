package mods.dieselization.screen;

import mods.dieselization.api.core.DieselizationConstants;
import mods.dieselization.entity.BaseFuelLocomotive;
import mods.dieselization.entity.DieselLocomotive;
import mods.dieselization.entity.DieselShunterLocomotive;
import mods.dieselization.screen.locomotive.DieselLocomotiveMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DieselizationMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, DieselizationConstants.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<DieselLocomotiveMenu>> DIESEL_LOCOMOTIVE =
            MENUS.register("diesel_locomotive", () -> entityMenu(BaseFuelLocomotive.class, DieselLocomotiveMenu::new));
    /*public static final DeferredHolder<MenuType<?>, MenuType<DieselLocomotiveMenu>> DIESEL_SHUNTER_LOCOMOTIVE =
            MENUS.register("diesel_shunter_locomotive", () -> entityMenu(DieselShunterLocomotive.class, DieselLocomotiveMenu::new));*/

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }

    private static <T extends AbstractContainerMenu, E extends BlockEntity> MenuType<T>
    blockEntityMenu(Class<E> entityType, CustomMenuFactory<T, E> factory) {
        IContainerFactory<T> containerFactory =  (id, inventory, packetBuffer) -> {
            BlockPos blockPos = packetBuffer.readBlockPos();
            BlockEntity entity = inventory.player.level().getBlockEntity(blockPos);
            if (entityType.isInstance(entity)) {
                return factory.create(id, inventory, entityType.cast(entity));
            }
            throw new IllegalStateException(
                    "Cannot find block entity of type %s at [%s]".formatted(entityType.getName(), blockPos));
        };
        return new MenuType<>(containerFactory, FeatureFlags.DEFAULT_FLAGS);
    }

    private static <T extends AbstractContainerMenu, E extends Entity> MenuType<T> entityMenu(
            Class<E> entityType, CustomMenuFactory<T, E> factory) {
        IContainerFactory<T> containerFactory = (id, inventory, packetBuffer) -> {
            int entityId = packetBuffer.readVarInt();
            Entity entity = inventory.player.level().getEntity(entityId);
            if (entityType.isInstance(entity)) {
                return factory.create(id, inventory, entityType.cast(entity));
            }
            throw new IllegalStateException(
                    "Cannot find entity of type %s with ID %s".formatted(entityType.getName(), entityId));
        };
        return new MenuType<>(containerFactory, FeatureFlags.DEFAULT_FLAGS);
    }

    private interface CustomMenuFactory<C extends AbstractContainerMenu, T> {
        C create(int id, Inventory inventory, T data);
    }
}
