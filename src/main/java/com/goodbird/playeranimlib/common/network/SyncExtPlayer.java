package com.goodbird.playeranimlib.common.network;

import com.goodbird.playeranimlib.common.entity.ExtendedPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class SyncExtPlayer implements IMessage, IMessageHandler<SyncExtPlayer, IMessage> {
    public int playerId;
    public NBTTagCompound extPlayerData;

    public SyncExtPlayer(){}
    public SyncExtPlayer(EntityPlayer player){
        playerId = player.getEntityId();
        extPlayerData = new NBTTagCompound();
        ExtendedPlayer.get(player).saveNBTData(extPlayerData);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        buf.writeInt(playerId);
        ByteBufUtils.writeTag(buf, extPlayerData);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        playerId = buf.readInt();
        extPlayerData = ByteBufUtils.readTag(buf);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(SyncExtPlayer message, MessageContext ctx) {
        EntityPlayer otherPlayer = (EntityPlayer) Minecraft.getMinecraft().theWorld.getEntityByID(message.playerId);
        ExtendedPlayer.get(otherPlayer).loadNBTData(message.extPlayerData);
        return null;
    }
}
