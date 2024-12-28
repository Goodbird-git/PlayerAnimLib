package com.goodbird.playeranimlib.client.renderer;

import com.goodbird.playeranimlib.client.model.GeoPlayerModel;
import net.minecraft.entity.player.EntityPlayer;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GeoPlayerRenderer extends GeoEntityRenderer {
    public static GeoPlayerRenderer INSTANCE = new GeoPlayerRenderer();

    public GeoPlayerRenderer() {
        super(new GeoPlayerModel());
    }

    @Override
    public Color getRenderColor(Object obj, float partialTicks) {
        EntityPlayer animatable = (EntityPlayer) obj;
        return animatable.hurtTime <= 0 && animatable.deathTime <= 0 ? Color.ofRGBA(255, 255, 255, 255) : Color.ofRGBA(255, 153, 153, 255);
    }

    static {
        AnimationController.addModelFetcher((object) -> {
            return object instanceof EntityPlayer ? (IAnimatableModel)INSTANCE.getGeoModelProvider() : null;
        });
    }
}
