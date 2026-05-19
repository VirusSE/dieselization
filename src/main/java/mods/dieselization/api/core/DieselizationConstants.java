
package mods.dieselization.api.core;

import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.resources.ResourceLocation;

public final class DieselizationConstants {

    public static final String MODID = "dieselization";
    public static final String NAME = "Railcraft Reborn - Dieselization";
    private static final String RAILCRAFT_PLAYER = "[" + MODID + "]";
    public static final GameProfile FAKE_GAMEPROFILE =
            new GameProfile(UUID.nameUUIDFromBytes(RAILCRAFT_PLAYER.getBytes()), RAILCRAFT_PLAYER);

    private DieselizationConstants() {
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static String makeTranslationKey(String type, String name) {
        return type + "." + MODID + "." + name;
    }
}