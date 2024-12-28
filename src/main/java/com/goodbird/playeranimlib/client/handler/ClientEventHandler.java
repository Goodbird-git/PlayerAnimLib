package com.goodbird.playeranimlib.client.handler;

import com.goodbird.playeranimlib.client.renderer.GeoFirstPersonRender;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderHandEvent;

public class ClientEventHandler {
    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event){
        if(Minecraft.getMinecraft().gameSettings.thirdPersonView==0) {
            event.setCanceled(true);
            Entity player = (Entity) Minecraft.getMinecraft().thePlayer;
            GeoFirstPersonRender.INSTANCE.doRender(player, 0, -1.75, 0, event.partialTicks, event.renderPass);
        }
    }
}
