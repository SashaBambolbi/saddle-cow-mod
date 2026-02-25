package com.example.saddlecow.entity.client;

import com.example.saddlecow.SaddleCowMod;
import com.example.saddlecow.entity.SaddleCowEntity;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SaddleCowRenderer extends MobRenderer<SaddleCowEntity, CowModel<SaddleCowEntity>> {
    private static final ResourceLocation COW_TEXTURE = new ResourceLocation("textures/entity/cow/cow.png");
    private static final ResourceLocation SADDLED_COW_TEXTURE = new ResourceLocation(SaddleCowMod.MOD_ID, "textures/entity/saddled_cow.png");

    public SaddleCowRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(ModelLayers.COW)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(SaddleCowEntity entity) {
        if (entity.isSaddled()) {
            return SADDLED_COW_TEXTURE;
        }
        return COW_TEXTURE;
    }
}
