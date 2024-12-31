package com.goodbird.playeranimlib.client.control;

import com.goodbird.playeranimlib.client.control.event.PredicateModifyEvent;
import com.goodbird.playeranimlib.client.control.predicate.IPlayerAnimationPredicate;
import com.goodbird.playeranimlib.client.control.event.PredicateRegisterEvent;
import com.goodbird.playeranimlib.client.control.predicate.builtin.DefaultPredicate;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

public class PredicateRegistry {
    private static final Map<ResourceLocation, IPlayerAnimationPredicate> registry = new HashMap<>();

    public static void register(ResourceLocation resourceLocation, IPlayerAnimationPredicate predicate){
        registry.put(resourceLocation, predicate);
    }

    public static IPlayerAnimationPredicate getPredicate(ResourceLocation resourceLocation){
        return registry.computeIfAbsent(resourceLocation, (resLoc)->registry.get(new ResourceLocation("playeranimlib", "default")));
    }

    public static void init(){
        register(new ResourceLocation("playeranimlib", "default"), new DefaultPredicate());
        MinecraftForge.EVENT_BUS.post(new PredicateRegisterEvent());
        MinecraftForge.EVENT_BUS.post(new PredicateModifyEvent());
    }
}
