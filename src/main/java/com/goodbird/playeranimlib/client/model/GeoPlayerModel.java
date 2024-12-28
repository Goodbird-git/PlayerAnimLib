package com.goodbird.playeranimlib.client.model;

import com.goodbird.playeranimlib.common.entity.ExtendedPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
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
        ExtendedPlayer player = ExtendedPlayer.get((EntityPlayer) entity);
        return new ResourceLocation(player.getGeckoAnimFilePath());
    }

    public ResourceLocation getModelLocation(Object entity) {
        ExtendedPlayer player = ExtendedPlayer.get((EntityPlayer) entity);
        return new ResourceLocation(player.getGeckoModelPath());
    }

    public ResourceLocation getTextureLocation(Object entity) {
        EntityPlayer player = (EntityPlayer) entity;
        ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);
        if(extendedPlayer.isUseSkin()){
            return ((AbstractClientPlayer)player).getLocationSkin();
        }
        return new ResourceLocation(extendedPlayer.getGeckoTexturePath());
    }

    public void setLivingAnimations(Object entity, Integer uniqueID, AnimationEvent animationEvent) {
        super.setLivingAnimations((IAnimatable) entity, uniqueID, animationEvent);
        ExtendedPlayer extendedPlayer = ExtendedPlayer.get((EntityPlayer) entity);
        for(String headBone : extendedPlayer.getHeadBones()) {
            IBone head = this.getAnimationProcessor().getBone(headBone);
            EntityModelData extraData = (EntityModelData) animationEvent.getExtraDataOfType(EntityModelData.class).get(0);
            head.setRotationX(extraData.headPitch * 0.017453292F);
            head.setRotationY(extraData.netHeadYaw * 0.017453292F);
        }
    }
}
