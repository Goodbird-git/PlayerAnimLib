package com.goodbird.playeranimlib.client.control.predicate;

import com.goodbird.playeranimlib.client.control.event.PlayerAnimationEvent;
import com.goodbird.playeranimlib.mixin.IEntityPlayer;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;

public class BuilderControlledPredicate implements IPlayerAnimationPredicate {

    private String name;

    public BuilderControlledPredicate(String name){
        this.name = name;
    }

    @Override
    public AnimationBuilder getAnimation(PlayerAnimationEvent animationEvent) {
        AnimationBuilder builder = ((IEntityPlayer)animationEvent.getAnimatable()).getBuilderByName(name);
        if(builder==null) return null;
        if(animationEvent.getController().currentAnimationBuilder==builder && animationEvent.getController().getAnimationState() == AnimationState.Stopped){
            ((IEntityPlayer)animationEvent.getAnimatable()).setBuilderByName(name, null);
            return null;
        }
        return builder;
    }

    public String getName() {
        return name;
    }
}
