package com.goodbird.playeranimlib.client.control.predicate.builtin.parts;

import com.goodbird.playeranimlib.client.control.event.PlayerAnimationEvent;
import com.goodbird.playeranimlib.client.control.predicate.LayeredPredicate;
import net.minecraft.item.ItemBlock;
import software.bernie.geckolib3.core.builder.AnimationBuilder;

public class IdlePosePredicate extends LayeredPredicate {
    public IdlePosePredicate() {
        super("idle_pose");
        addLayer(this::holdBlockAnimation);
        addLayer(this::swimAnimation);
        addLayer(this::sleepAnimation);
    }

    public AnimationBuilder holdBlockAnimation(PlayerAnimationEvent animationEvent) {
        if(animationEvent.getAnimatable().getHeldItem()!=null && animationEvent.getAnimatable().getHeldItem().getItem() instanceof ItemBlock){
            return new AnimationBuilder().loop("hold_block");
        }
        return null;
    }

    public AnimationBuilder swimAnimation(PlayerAnimationEvent animationEvent) {
        if(animationEvent.getAnimatable().isInWater()){
            return new AnimationBuilder().loop("idle_swim");
        }
        return null;
    }

    public AnimationBuilder sleepAnimation(PlayerAnimationEvent animationEvent) {
        if(animationEvent.getAnimatable().isPlayerSleeping()){
            return new AnimationBuilder().loop("sleep");
        }
        return null;
    }
}
