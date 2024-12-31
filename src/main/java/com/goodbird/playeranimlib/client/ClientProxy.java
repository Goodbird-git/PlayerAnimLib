package com.goodbird.playeranimlib.client;

import com.goodbird.playeranimlib.CommonProxy;
import com.goodbird.playeranimlib.client.handler.ClientEventHandler;
import com.goodbird.playeranimlib.client.control.PredicateRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        PredicateRegistry.init();
    }
}
