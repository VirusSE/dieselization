package mods.dieselization.item.components;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class FuelFilterItem extends Item {
    private int burnTime = 0;
    private int pollutionDegree = 0;

    public FuelFilterItem(Properties properties, int pollutionDegree) {
        super(properties);
        this.pollutionDegree = pollutionDegree;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
//        level.getBlockEntity(context.getClickedPos())

        return null;
    }
}
