package mods.dieselization.entity;

import mods.dieselization.screen.locomotive.DieselLocomotiveMenu;
import mods.railcraft.api.carts.FluidTransferHandler;
import mods.railcraft.api.carts.RollingStock;
import mods.railcraft.api.core.CompoundTagKeys;
import mods.railcraft.particle.RailcraftParticleTypes;
import mods.railcraft.season.Seasons;
import mods.railcraft.util.container.ContainerMapper;
import mods.railcraft.util.fluids.FluidTools;
import mods.railcraft.world.entity.vehicle.locomotive.Locomotive;
import mods.railcraft.world.item.TicketItem;
import mods.railcraft.world.level.material.StandardTank;
import mods.railcraft.world.level.material.TankManager;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseFuelLocomotive extends Locomotive implements WorldlyContainer, FluidTransferHandler {

    protected static final int SLOT_FUEL_INPUT = 0;
    protected static final int SLOT_FUEL_PROCESSING = 1;
    protected static final int SLOT_FUEL_OUTPUT = 2;
    protected static final int TICKET_SLOT = 3;

    private static final EntityDataAccessor<Boolean> SMOKE =
            SynchedEntityData.defineId(BaseFuelLocomotive.class, EntityDataSerializers.BOOLEAN);

    private static final byte TICKS_PER_BOILER_CYCLE = 2;
    private static final int FUEL_PER_REQUEST = 3;

    protected final StandardTank fuelTank =
            StandardTank.ofBuckets(22)
                    .fillProcessor(this::checkFill)
                    .filter(Tags.Fluids.WATER);
                    //.filter(DieselizationTags.Fluids.FUEL);

    protected final ContainerMapper invFuelontainers =
            ContainerMapper.make(this, SLOT_FUEL_INPUT, 3).ignoreItemChecks();
    protected final ContainerMapper ticketContainer =
            new ContainerMapper(this, TICKET_SLOT, 2).ignoreItemChecks();

    private final TankManager tankManager = new TankManager(this.fuelTank);

    private int fluidProcessingTimer = 0;
    private FluidTools.ProcessState processState = FluidTools.ProcessState.RESET;

    /**
     * Default Constructor of BaseFuelLocomotive
     * @param type
     * @param level
     */
    protected BaseFuelLocomotive(EntityType<?> type, Level level) {
        super(type, level);
    }

    protected BaseFuelLocomotive(ItemStack itemStack, EntityType<?> type, double x, double y, double z, ServerLevel serverLevel) {
        super(itemStack, type, x,y ,z, serverLevel);
        this.loadFromItemStack(itemStack);
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
    public InteractionResult interact(Player player, InteractionHand hand) {
        return FluidTools.interactWithFluidHandler(player, hand, this.getTankManager())
                ? InteractionResult.SUCCESS
                : super.interact(player, hand);
    }

    public TankManager getTankManager() {
        return this.tankManager;
    }

    @Override
    public SoundEvent getWhistleSound() {
        return null; // Todo implement DieselizationSoundsEvents.FUEL_WHISTLE.get();
    }

    protected void serverTick(ServerLevel level) {
        super.serverTick(level);
        if (this.fuelTank.isEmpty()) {
            this.setMode(Mode.SHUTDOWN);
        }
        /*
        if (this.steamTank.getRemainingSpace() >= SteamConstants.STEAM_PER_UNIT_WATER
                || this.isShutdown()) {
            this.boiler.tick(1);

            this.setSmoking(this.boiler.isBurning());

            // TODO: make venting a toggleable thing (why autodump while train has no coal??)
            if (!this.boiler.isBurning()) {
                this.ventSteam();
            }
        }*/

        if (++this.fluidProcessingTimer >= FluidTools.BUCKET_FILL_TIME) {
            this.fluidProcessingTimer = 0;
            this.processState = FluidTools.processContainer(this.invFuelontainers,
                    this.fuelTank, FluidTools.ProcessType.DRAIN_ONLY, this.processState);
        }
    }

    @Override
    protected void clientTick(Level level) {
        super.clientTick(level);
        // future information: renderYaw FACES at -x when at 0deg
        double rads = Math.toRadians(renderYaw);
        if (this.isSmoking()) {
            float offset = 0.4f;

            var x = this.getX() - Math.cos(rads) * offset;
            var y = this.getY() + 1.5;
            var z = this.getZ() - Math.sin(rads) * offset;

            SimpleParticleType particle;
            if (Seasons.isHalloween() && this.random.nextInt(4) == 0) { // 20%?
                particle = RailcraftParticleTypes.PUMPKIN.get();
            } else {
                // smog, obviously.
                particle = ParticleTypes.CAMPFIRE_COSY_SMOKE;
            }
            level.addParticle(particle, x, y, z, 0, 0.02, 0);
        }
        // steam spawns ON the engine itself, spreading left or right
        // as the pistons are on the train's sides
        /*if (this.isSteaming()) {
            float offset = 0.5f;
            double ninetyDeg = Math.toRadians(90) + Math.toRadians(this.random.nextInt(10)); // 10* bias
            double steamAngularSpeed = 0.01;
            double yCoord = this.getY() + 0.15;

            var vx = steamAngularSpeed * Math.cos(rads - ninetyDeg);
            var vz = steamAngularSpeed * Math.sin(rads - ninetyDeg);

            level.addParticle(RailcraftParticleTypes.STEAM.get(),
                    this.getX() - Math.cos(rads + ninetyDeg) * offset, yCoord,
                    this.getZ() - Math.sin(rads + ninetyDeg) * offset, vx,
                    0.02 + (this.random.nextDouble() * 0.01), vz);

            level.addParticle(RailcraftParticleTypes.STEAM.get(),
                    this.getX() - Math.cos(rads - ninetyDeg) * offset, yCoord,
                    this.getZ() - Math.sin(rads - ninetyDeg) * offset, vx,
                    0.02 + (this.random.nextDouble() * 0.01), vz);
        }*/
    }

    public boolean isSmoking() {
        return this.entityData.get(SMOKE);
    }

    private void setSmoking(boolean smoke) {
        this.entityData.set(SMOKE, smoke);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put(CompoundTagKeys.TANK_MANAGER, this.getTankManager().serializeNBT(this.registryAccess()));
        //tag.put(CompoundTagKeys.BOILER, this.boiler.serializeNBT(this.registryAccess()));
        tag.putString(CompoundTagKeys.PROCESS_STATE, this.processState.getSerializedName());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.getTankManager().deserializeNBT(this.registryAccess(),
                tag.getList(CompoundTagKeys.TANK_MANAGER, Tag.TAG_COMPOUND));
        //this.boiler.deserializeNBT(this.registryAccess(), tag.getCompound(CompoundTagKeys.BOILER));
        this.processState = FluidTools.ProcessState.fromTag(tag);
    }

    @Override
    protected @NotNull Container ticketContainer() {
        return this.ticketContainer;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack itemStack, @Nullable Direction side) {
        return canPlaceItem(slot, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, @NotNull ItemStack itemStack, @NotNull Direction side) {
        return slot < TICKET_SLOT;
    }

    @Override
    public boolean canPassFluidRequests(FluidStack fluidStack) {
        return false; // Todo implements fluidStack.is(DieselizationTags.Fluids.FUEL);
    }

    @Override
    public boolean canAcceptPushedFluid(RollingStock requester, FluidStack fluidStack) {
        return false; // Todo implement fluidStack.is(DieselizationTags.Fluids.FUEL);
    }

    @Override
    public boolean canProvidePulledFluid(RollingStock rollingStock, FluidStack fluidStack) {
        return false; // Todo imeplement solution
    }

    protected FluidStack checkFill(FluidStack resource) {
        return FluidStack.EMPTY; // Todo implements fluidStack
    }

    public Component engine() {
        return Component.literal("Diesel");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int id, @NotNull Inventory playerInventory) {
        return new DieselLocomotiveMenu(id, playerInventory, this);
    }

    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return switch(slot) {
            case SLOT_FUEL_INPUT -> FluidTools.containsFluid(stack, Fluids.WATER);
            case TICKET_SLOT -> TicketItem.FILTER.test(stack);
            default -> false;
        };
    }
}
