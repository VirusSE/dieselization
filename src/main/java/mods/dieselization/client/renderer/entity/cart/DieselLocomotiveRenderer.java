package mods.dieselization.client.renderer.entity.cart;

import com.mojang.blaze3d.vertex.PoseStack;

import mods.dieselization.api.core.DieselizationConstants;
import mods.dieselization.client.model.DieselLocomotiveModel;
import mods.dieselization.client.model.DieselShunterLocomotiveModel;
import mods.railcraft.client.renderer.entity.cart.DefaultLocomotiveRenderer;
import mods.railcraft.world.entity.vehicle.locomotive.Locomotive;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import mods.dieselization.client.model.DieselizationModelLayers;

public class DieselLocomotiveRenderer extends DefaultLocomotiveRenderer {
    /*private final ElectricLocomotiveLampModel lampModel;
    private final ResourceLocation lampTextureOn;
    private final ResourceLocation lampTextureOff;*/

    public DieselLocomotiveRenderer(EntityRendererProvider.Context context, String modelTag,
                                    EntityModel<? super Locomotive> model,
                                    EntityModel<? super Locomotive> snowLayer) {
        super(context, modelTag, model, snowLayer, new ResourceLocation[]{
            DieselizationConstants.rl("textures/entity/locomotive/" + modelTag + "/primary.png"),
            DieselizationConstants.rl("textures/entity/locomotive/" + modelTag + "/secondary.png"),
            DieselizationConstants.rl("textures/entity/locomotive/" + modelTag + "/nocolor.png"),
            DieselizationConstants.rl("textures/entity/locomotive/" + modelTag + "/snow.png")});
    }

    public DieselLocomotiveRenderer(EntityRendererProvider.Context context) {
        this(context, "diesel",
                new DieselLocomotiveModel(context.bakeLayer(DieselizationModelLayers.DIESEL_LOCOMOTIVE)),
                new DieselLocomotiveModel(
                        context.bakeLayer(DieselizationModelLayers.DIESEL_LOCOMOTIVE_SNOW)));
        /*this.lampModel = new ElectricLocomotiveLampModel(
                context.bakeLayer(RailcraftModelLayers.ELECTRIC_LOCOMOTIVE_LAMP));

        this.lampTextureOn = DieselizationConstants.rl("textures/entity/locomotive" + modelTag + "lamp_on.png");
        this.lampTextureOff = DieselizationConstants.rl("textures/entity/locomotive" + modelTag + "lamp_off.png");*/
    }
/*
    @Override
    public void renderBody(Locomotive cart, float time, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int packedLight, int color) {
        super.renderBody(cart, time, poseStack, renderTypeBuffer, packedLight, color);
        poseStack.pushPose();
        poseStack.scale(-1, -1, 1);
        poseStack.translate(0.05F, 0, 0);

        boolean bright = cart.getMode() == Locomotive.Mode.RUNNING;

        //var vertexBuilder = renderTypeBuffer.getBuffer(this.lampModel.renderType(bright ? this.lampTextureOn : this.lampTextureOff));

        //this.lampModel.renderToBuffer(poseStack, vertexBuilder, bright ? RenderUtil.FULL_LIGHT : packedLight, OverlayTexture.NO_OVERLAY, color);
        poseStack.popPose();
    }*/

}
