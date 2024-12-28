package com.goodbird.playeranimlib.mixin.impl;

import com.goodbird.playeranimlib.client.renderer.GeoPlayerRenderer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public abstract class MixinRenderPlayer extends RendererLivingEntity {

    public MixinRenderPlayer(ModelBase p_i1261_1_, float p_i1261_2_) {
        super(p_i1261_1_, p_i1261_2_);
    }

    @Inject(method = "Lnet/minecraft/client/renderer/entity/RenderPlayer;doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V", at = @At("HEAD"), cancellable = true)
    public void doRender(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_, CallbackInfo info) {
        if(p_76986_1_!=null) {
            info.cancel();
            GeoPlayerRenderer.INSTANCE.doRender((Entity) p_76986_1_, p_76986_2_, p_76986_4_ - 1.75, p_76986_6_, p_76986_8_, p_76986_9_);
        }
    }
}
