package com.goodbird.playeranimlib.client.control.predicate.builtin.parts;

import com.goodbird.playeranimlib.client.control.event.PlayerAnimationEvent;
import com.goodbird.playeranimlib.client.control.predicate.LayeredPredicate;
import software.bernie.geckolib3.core.builder.AnimationBuilder;

public class WalkPredicate extends LayeredPredicate {
    public WalkPredicate() {
        super("walk");
        addLayer(this::defaultWalkAnimation);
    }

    public AnimationBuilder defaultWalkAnimation(PlayerAnimationEvent animationEvent) {
        if(Math.abs(animationEvent.getLimbSwingAmount())>0.15){
            return new AnimationBuilder().loop("walk");
        }
        return null;
    }
}
