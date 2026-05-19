package mods.dieselization.sounds;

import mods.dieselization.api.core.DieselizationConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DieselizationSoundEvents {

    private static final DeferredRegister<SoundEvent> deferredRegister =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, DieselizationConstants.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> DIESEL_WHISTLE =
            register("locomotive.diesel.whistle");

    public static void register(IEventBus modEventBus) {
        deferredRegister.register(modEventBus);
    }

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return deferredRegister.register(name,
                () -> SoundEvent.createVariableRangeEvent(DieselizationConstants.rl(name)));
    }
}
