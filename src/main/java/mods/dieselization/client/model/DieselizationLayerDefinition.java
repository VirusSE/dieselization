package mods.dieselization.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class DieselizationLayerDefinition {

    private static final CubeDeformation SNOW_DEFORMATION = new CubeDeformation(0.125F);

    public static void createRoots(
            BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> consumer) {
        consumer.accept(DieselizationModelLayers.DIESEL_LOCOMOTIVE,
                () -> DieselLocomotiveModel.createBodyLayer(CubeDeformation.NONE));
        consumer.accept(DieselizationModelLayers.DIESEL_LOCOMOTIVE_SNOW,
                () -> DieselLocomotiveModel.createBodyLayer(SNOW_DEFORMATION));

        consumer.accept(DieselizationModelLayers.DIESEL_SHUNTER_LOCOMOTIVE,
                () -> DieselShunterLocomotiveModel.createBodyLayer(CubeDeformation.NONE));
        consumer.accept(DieselizationModelLayers.DIESEL_SHUNTER_LOCOMOTIVE_SNOW,
                () -> DieselShunterLocomotiveModel.createBodyLayer(SNOW_DEFORMATION));
    }
}
