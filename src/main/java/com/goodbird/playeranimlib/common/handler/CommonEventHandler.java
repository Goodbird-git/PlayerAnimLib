package com.goodbird.playeranimlib.common.handler;

import com.goodbird.playeranimlib.common.entity.ExtendedPlayer;
import com.goodbird.playeranimlib.common.network.NetworkWrapper;
import com.goodbird.playeranimlib.common.network.SyncExtPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CommonEventHandler {
    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if(event.entity instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer)event.entity) == null) {
            ExtendedPlayer.register((EntityPlayer)event.entity);
        }
    }

    @SubscribeEvent
    public void onPlayerCloneEvent(PlayerEvent.Clone event) {
        NBTTagCompound oldPlayerNBT = new NBTTagCompound();
        ExtendedPlayer oldPlayerEx = ExtendedPlayer.get(event.original);
        oldPlayerEx.saveNBTData(oldPlayerNBT);
        ExtendedPlayer newPlayerEx = ExtendedPlayer.get(event.entityPlayer);
        newPlayerEx.loadNBTData(oldPlayerNBT);
    }

    @SubscribeEvent
    public void onPlayerStartTracking(PlayerEvent.StartTracking event) {
        if(event.target instanceof EntityPlayer && !event.target.worldObj.isRemote) {
            NetworkWrapper.sendToPlayer(new SyncExtPlayer((EntityPlayer) event.target), event.entityPlayer);
        }
    }
}
