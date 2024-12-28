package com.goodbird.playeranimlib.common.network;

import com.goodbird.playeranimlib.PlayerAnimLib;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;

public class NetworkWrapper {
    private static final SimpleNetworkWrapper wrapper = new SimpleNetworkWrapper(PlayerAnimLib.MODID);

    public static void init() {
        wrapper.registerMessage(SyncExtPlayer.class, SyncExtPlayer.class, 0, Side.CLIENT);
    }

    public static void sendToPlayer(IMessage message, EntityPlayer player) {
        wrapper.sendTo(message, (EntityPlayerMP) player);
    }

    public static void sendToAll(IMessage message) {
        wrapper.sendToAll(message);
    }

    public static void sendToServer(IMessage message) {
        wrapper.sendToServer(message);
    }

    public static void sendToAllTracking(IMessage message, Entity tracking){
        for(EntityPlayer player : ((WorldServer)tracking.worldObj).getEntityTracker().getTrackingPlayers(tracking)){
            sendToPlayer(message, player);
        }
    }
}
