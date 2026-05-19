package mods.dieselization.screen.locomotive;

import mods.railcraft.api.carts.RollingStock;
import mods.railcraft.world.entity.vehicle.locomotive.SteamLocomotive;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VariablePoweredSteamLocomotive extends SteamLocomotive {
    private static final float HS_FORCE_BONUS = 3.5F;

    public VariablePoweredSteamLocomotive(EntityType<?> type, Level level) {
        super(type, level);
    }

    public VariablePoweredSteamLocomotive(ItemStack itemStack, double x, double y, double z,
                                          ServerLevel serverLevel) {
        super(itemStack, x, y, z, serverLevel);
    }

    @Override
    protected void applyNaturalSlowdown() {
        if (this.isRemoved()) {
            return;
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(this.getDrag(), 0.0D, this.getDrag()));

        if (this.isReverse() && this.getSpeed().getLevel() > this.getMaxReverseSpeed().getLevel()) {
            this.setSpeed(this.getMaxReverseSpeed());
        }

        if (!this.isRunning()) {
            return;
        }
        //RailcraftConfig.SERVER.locomotiveHorsepower.get()
        double force = 15.0
                * 0.01F
                * this.getPowerMultiplier(this.getSpeed());

        if (this.isReverse()) {
            force = -force;
        }

        if (this.getSpeed() == Speed.MAX
                && RollingStock.getOrThrow(this).isHighSpeed()) {
            force *= HS_FORCE_BONUS;
        }

        double yaw = this.getYRot() * Mth.DEG_TO_RAD;
        this.setDeltaMovement(
                this.getDeltaMovement().add(Math.cos(yaw) * force, 0, Math.sin(yaw) * force));
    }

    private double getPowerMultiplier(Speed speed) {
        return switch (speed) {
            case SLOWEST -> 0.25D;
            case SLOWER -> 0.50D;
            case NORMAL -> 0.75D;
            case MAX -> 1.00D;
        };
    }
}
