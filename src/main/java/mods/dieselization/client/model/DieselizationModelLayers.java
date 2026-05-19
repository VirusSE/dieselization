package mods.dieselization.client.model;

import com.google.common.collect.Sets;
import mods.dieselization.api.core.DieselizationConstants;
import mods.railcraft.client.model.RailcraftModelLayers;
import net.minecraft.client.model.geom.ModelLayerLocation;

import java.util.Set;

public class DieselizationModelLayers {

    private static final Set<ModelLayerLocation> allModels = Sets.newHashSet();

    public static final ModelLayerLocation DIESEL_LOCOMOTIVE = register("diesel_locomotive");
    public static final ModelLayerLocation DIESEL_LOCOMOTIVE_SNOW = register("diesel_locomotive_snow");

    public static final ModelLayerLocation DIESEL_SHUNTER_LOCOMOTIVE = register("diesel_shunter_locomotive");
    public static final ModelLayerLocation DIESEL_SHUNTER_LOCOMOTIVE_SNOW = register("diesel_shunter_locomotive_snow");

    private static ModelLayerLocation register(String model) {
        return register(model, "main");
    }

    private static ModelLayerLocation register(String model, String layer) {
        var layerLocation = new ModelLayerLocation(DieselizationConstants.rl(model), layer);
        if (!allModels.add(layerLocation)) {
            throw new IllegalStateException("Duplicate registration for " + layerLocation);
        } else {
            return layerLocation;
        }
    }
}
