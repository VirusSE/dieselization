package mods.dieselization.tags;

import mods.dieselization.api.core.DieselizationConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import javax.swing.text.html.HTML;

public class DieselizationTags {
    public static class Items {
        //public static final TagKey<Item> = tag("name")
        public static final TagKey<Item> OIL_SAND = oresTag("oil_sand");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(DieselizationConstants.rl(name));
        }

        public static TagKey<Item> commonTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }

        public static TagKey<Item> oresTag(String name) {
            return commonTag("ores/" + name);
        }

        public static TagKey<Item> rawMaterialsTag(String name) {
            return commonTag("raw_materials/" + name);
        }

        public static TagKey<Item> ingotsTag(String name) {
            return commonTag("ingots/" + name);
        }

        public static TagKey<Item> nuggetsTag(String name) {
            return commonTag("nuggets/" + name);
        }

        public static TagKey<Item> platesTag(String name) {
            return commonTag("plates/" + name);
        }

        public static TagKey<Item> gearsTag(String name) {
            return commonTag("gears/" + name);
        }

        public static TagKey<Item> dustsTag(String name) {
            return commonTag("dusts/" + name);
        }

        public static TagKey<Item> storageBlocksTag(String name) {
            return commonTag("storage_blocks/" + name);
        }
    }

    public static class Blocks {

        //public static final TagKey<Block>

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(DieselizationConstants.rl(name));
        }

        private static TagKey<Block> commonTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }

    public static class Fluids {

        public static final TagKey<Fluid> CRUDE_OIL = commonTag("creosote");

        private static TagKey<Fluid> commonTag(String name) {
            return FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }
}