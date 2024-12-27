package com.goodbird.playeranimlib.client;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GeoPlayerModel extends AnimatedTickingGeoModel {
    public GeoPlayerModel() {
    }

    public ResourceLocation getAnimationFileLocation(Object entity) {
        return new ResourceLocation("geckolib3", "animations/bat.animation.json");
    }

    public ResourceLocation getModelLocation(Object entity) {
        return new ResourceLocation("geckolib3", "geo/bat.geo.json");
    }

    public ResourceLocation getTextureLocation(Object entity) {
        return new ResourceLocation("geckolib3", "textures/model/entity/bat.png");
    }

    public void setLivingAnimations(Object entity, Integer uniqueID, AnimationEvent animationEvent) {
        super.setLivingAnimations((IAnimatable) entity, uniqueID, animationEvent);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData)animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * 0.017453292F);
        head.setRotationY(extraData.netHeadYaw * 0.017453292F);
    }
}
