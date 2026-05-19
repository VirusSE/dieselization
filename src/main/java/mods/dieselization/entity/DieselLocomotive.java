package mods.dieselization.entity;

import mods.dieselization.item.DieselizationItems;
import mods.dieselization.sounds.DieselizationSoundEvents;
import mods.dieselization.world.entity.DieselizationEntities;
import mods.railcraft.api.core.CompoundTagKeys;
import mods.railcraft.particle.RailcraftParticleTypes;
import mods.railcraft.season.Seasons;
import mods.railcraft.util.container.ContainerMapper;
import mods.railcraft.util.container.ContainerTools;
import mods.railcraft.util.fluids.FluidTools;
import mods.railcraft.world.item.TicketItem;
import mods.railcraft.world.level.material.StandardTank;
import mods.railcraft.world.level.material.TankManager;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class DieselLocomotive extends BaseFuelLocomotive implements WorldlyContainer {

    private static final int[] SLOTS = ContainerTools.buildSlotArray(0, 5);
    private static final EntityDataAccessor<Boolean> SMOKE =
            SynchedEntityData.defineId(DieselLocomotive.class, EntityDataSerializers.BOOLEAN);
    private static final int FUEL_PER_REQUEST = 3;
    protected final StandardTank fuelTank =
            StandardTank.ofBuckets(8)
                    .fillProcessor(this::checkFill)
                    .filter(FluidTags.WATER);
    protected final ContainerMapper invFuelContainer = ContainerMapper.make(this, SLOT_FUEL_INPUT, 3).ignoreItemChecks();
    private final TankManager tankManager = new TankManager(this.fuelTank);

    /**
     * Constructor of DieselLocomotive
     * @param type
     * @param level
     */
    public DieselLocomotive(EntityType<?> type, Level level) {
        super(type, level);

        // TODO insert initializing engine stuff
    }

    /**
     *
     * @param itemStack
     * @param x
     * @param y
     * @param z
     * @param serverLevel
     */
    public DieselLocomotive(ItemStack itemStack, double x, double y, double z, ServerLevel serverLevel) {
        super(itemStack, DieselizationEntities.DIESEL_LOCOMOTIVE.get(), x, y, z, serverLevel);

        // TODO insert initializing engine stuff
    }

    @Override
    public int getContainerSize() {
        return 5;
    }

    @Override
    public Speed getMaxReverseSpeed() {
        return Speed.SLOWEST;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SMOKE, false);
    }

    @Override
    public boolean isAllowedMode(Mode mode) {
        return this.fuelTank.isEmpty() && mode == Mode.SHUTDOWN || super.isAllowedMode(mode);
    }

    @Override
    public SoundEvent getWhistleSound() {
        return DieselizationSoundEvents.DIESEL_WHISTLE.get();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        return FluidTools.interactWithFluidHandler(player, hand, this.getTankManager())
                ? InteractionResult.SUCCESS : super.interact(player, hand);
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory) {
        return null;
    }

    public TankManager getTankManager() {
        return this.tankManager;
    }

    public boolean isSmoking() {
        return this.entityData.get(SMOKE);
    }

    private void setSmoking(boolean smoke) {
        this.entityData.set(SMOKE, smoke);
    }

    public int retrieveFuel() {
        // TODO Stub: implement fuel retrieve
        return 0;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put(CompoundTagKeys.TANK_MANAGER, this.getTankManager().serializeNBT(this.registryAccess()));
        //tag.put(CompoundTagKeys.PROCESS_STATE, this.processState.getSerializedName());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getTankManager().deserializeNBT(this.registryAccess(),
                tag.getList(CompoundTagKeys.TANK_MANAGER, CompoundTag.TAG_COMPOUND));
        //this.processState = FluidTools.ProcessState.fromTag(tag);
    }

    public boolean isSafeToFill() {
        // TODO implement engine temperature?
        //return !this.engine
        return true;
    }

    @Override
    public boolean needsFuel() {
        // TODO implement "needs" fuel calculation --> steamer return true for <33% of 1) water 2) "burn fuel"
        return false;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return SLOTS;
    }

    @Override
    protected @NotNull Item getDropItem() {
        return DieselizationItems.DIESEL_LOCOMOTIVE.get();
    }

    @Override
    protected DyeColor getDefaultPrimaryColor() {
        return DyeColor.LIGHT_BLUE;
    }

    @Override
    protected DyeColor getDefaultSecondaryColor() {
        return DyeColor.RED;
    }

    @Override
    protected void serverTick(ServerLevel level) {
        super.serverTick(level);
        if (this.fuelTank.isEmpty()) {
            this.setMode(Mode.SHUTDOWN);
        }/*

        // Todo Handle refueling from "extra" fuel tanks
        this.setSmoking(null);

        if (++this.fluidProcessingTimer >= FluidTools.BUCKET_FILL_TIME) {
            this.fluidProcessingTimer = 0;
            this.processState = FluidTools.processContainer(this.invFuelContainer, this.fuelTank,
                    FluidTools.ProcessType.DRAIN_ONLY, this.processState);
        }*/
    }

    @Override
    protected void clientTick(Level level) {
        super.clientTick(level);

        // Todo Rework Smoking --> extract to engine, cause the "engines" emits specific types of smoke
        double rads = Math.toRadians(renderYaw);
        if (this.isSmoking()) {
            float offset = 0.4f;

            var x = this.getX() - Math.cos(rads) * offset;
            var y = this.getY() + 1.5;
            var z = this.getZ() - Math.sin(rads) * offset;

            // @Todo Stub: ash & camp fire particle?
            SimpleParticleType particle;
            if (Seasons.isHalloween() && this.random.nextInt(4) == 0) {
                particle = RailcraftParticleTypes.PUMPKIN.get();
            } else {
                particle = ParticleTypes.CAMPFIRE_SIGNAL_SMOKE;
            }
            level.addParticle(particle, x, y, z, 0, 0.02, 0);
        }
    }
}