package com.goodbird.playeranimlib.client.handler;

import com.goodbird.playeranimlib.client.renderer.GeoFirstPersonRender;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderHandEvent;

public class ClientEventHandler {
    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event){
        if(Minecraft.getMinecraft().gameSettings.thirdPersonView==0) {
            event.setCanceled(true);
            Entity player = (Entity) Minecraft.getMinecraft().thePlayer;
            double scale = 0.25;
            float angleRad = (float) (player.rotationYaw/180f*Math.PI);
            GeoFirstPersonRender.INSTANCE.doRender(player, Math.sin(angleRad)*scale, -1.75, -Math.cos(angleRad)*scale, event.partialTicks, event.renderPass);
        }
    }
}
