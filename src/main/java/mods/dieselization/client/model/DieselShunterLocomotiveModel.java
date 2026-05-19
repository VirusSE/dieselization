package mods.dieselization.client.model;

import mods.railcraft.world.entity.vehicle.locomotive.Locomotive;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

public class DieselShunterLocomotiveModel extends HierarchicalModel<Locomotive> {

    private final ModelPart root;

    public DieselShunterLocomotiveModel(ModelPart root) {
        super(RenderType::entityTranslucentCull);
        this.root = root;
    }

    public static LayerDefinition createBodyLayer(CubeDeformation deformation) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // loco.setTextureOffset(1, 25).addBox(-20.0F, -5.0F, -16.0F, 23, 2, 16, scale);  // Wheels
        root.addOrReplaceChild("wheels",
                CubeListBuilder.create()
                        .texOffs(1, 25)
                        .addBox(-20, -5, -16, 23, 2, 16, deformation),
                PartPose.offset(8, 8, 8));
        // loco.setTextureOffset(1, 1).addBox(-21.0F, -8.0F, -17.0F, 25, 3, 18, scale);   // Frame
        root.addOrReplaceChild("frame",
                CubeListBuilder.create()
                        .texOffs(1, 1)
                        .addBox(-21, -7, -17, 25 ,2, 18, deformation),
                PartPose.offset(8, 8 ,8));
        //loco.setTextureOffset(74, 9).addBox(-6.0F, -21.0F, -16.0F, 6, 13, 16, scale);
        root.addOrReplaceChild("cab",
                CubeListBuilder.create()
                        .texOffs(74, 9)
                        .addBox(-6, -19, -16, 6, 12, 16, deformation),
                PartPose.offset(8, 8, 8));/*
        // loco.setTextureOffset(32, 46).addBox(-20.0F, -19.0F, -11.0F, 14, 11, 6, scale);
        root.addOrReplaceChild("radiator",
                CubeListBuilder.create()
                        .texOffs(32, 46)
                        .addBox(-20, -19, -11, 6, 11, 6, deformation),
                PartPose.offset(8, 8, 8));*/
        // loco.setTextureOffset(1, 55).addBox(-21.0F, -20.0F, -10.0F, 6, 4, 4, scale);
        root.addOrReplaceChild("light",
                CubeListBuilder.create()
                        .texOffs(1, 55)
                        .addBox(-21, -19, -10, 6, 4, 4, deformation),
                PartPose.offset(8, 8, 8));
        // loco.setTextureOffset(73, 41).addBox(-19.0F, -18.0F, -14.0F, 13, 10, 12, scale);
        root.addOrReplaceChild("engine",
                CubeListBuilder.create()
                        .texOffs(73, 44)
                        .addBox(-20, -17, -12, 14, 11, 8, deformation),
                PartPose.offset(8, 8, 8));
        // loco.setTextureOffset(107, 3).addBox(0.0F, -19.0F, -11.0F, 3, 11, 6, scale);
        root.addOrReplaceChild("cabBack",
                CubeListBuilder.create()
                        .texOffs(107, 3)
                        .addBox(0, -17, -11, 3, 10, 6, deformation),
                PartPose.offset(8, 8, 8));/*
        // loco.setTextureOffset(23, 50).addBox(0.0F, -21.0F, -14.0F, 2, 11, 2, scale);
        root.addOrReplaceChild("leftExhausMuffler",
                CubeListBuilder.create()
                        .texOffs(23, 50)
                        .addBox(0, -21, -14, 3, 11, 2, deformation),
                PartPose.offset(8, 8, 8));
        // loco.setTextureOffset(25, 46).addBox(0.5F, -23.0F, -13.5F, 1, 2, 1, scale);
        root.addOrReplaceChild("leftExhausPipe",
                CubeListBuilder.create()
                        .texOffs(25, 46)
                        .addBox(0.5F, -23, -13.5F, 1, 2, 1, deformation),
                PartPose.offset(8, 8, 8));
        // loco.setTextureOffset(18, 47).addBox(-0.5F, -10.0F, -13.5F, 2, 1, 1, scale);
        root.addOrReplaceChild("leftExhausBend",
                CubeListBuilder.create()
                        .texOffs(18, 47)
                        .addBox(0.5F, -10, -13.5F, 2, 1, 1, deformation),
                PartPose.offset(8, 8, 8));*/
        // loco.setTextureOffset(18, 47).addBox(-0.5F, -10.0F, -3.5F, 2, 1, 1, scale);
        root.addOrReplaceChild("rightExhausBend",
                CubeListBuilder.create()
                        .texOffs(18, 47)
                        .addBox(-8.5F, -10, -4, 1, 1, 2, deformation),
                PartPose.offset(8, 8, 8));
        // loco.setTextureOffset(23, 50).addBox(0.0F, -21.0F, -4.0F, 2, 11, 2, scale);
        root.addOrReplaceChild("rightExhausMuffler",
                CubeListBuilder.create()
                        .texOffs(119, 48)
                        .addBox(-9, -21, -4, 2, 11, 2, deformation),
                PartPose.offset(8, 8, 8));
        // loco.setTextureOffset(25, 46).addBox(0.5F, -23.0F, -3.5F, 1, 2, 1, scale);
        root.addOrReplaceChild("rightExhausPipe",
                CubeListBuilder.create()
                        .texOffs(18, 47)
                        .addBox(-8.5F, -23, -3.5F, 1, 2, 1, deformation),
                PartPose.offset(8, 8, 8));

        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(Locomotive entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public ModelPart root() {
        return this.root;
    }
}
