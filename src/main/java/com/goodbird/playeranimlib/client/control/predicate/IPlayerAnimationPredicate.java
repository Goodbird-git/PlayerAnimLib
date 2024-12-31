package com.goodbird.playeranimlib.client.control.predicate;

import com.goodbird.playeranimlib.client.control.event.PlayerAnimationEvent;
import net.minecraft.entity.player.EntityPlayer;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public interface IPlayerAnimationPredicate extends AnimationController.IAnimationPredicate {

    default PlayState test(AnimationEvent animationEvent){
        PlayerAnimationEvent event = new PlayerAnimationEvent(
                                         (EntityPlayer) animationEvent.getAnimatable(),
                                         animationEvent.getLimbSwing(),
                                         animationEvent.getLimbSwingAmount(),
                                         animationEvent.getPartialTick()
                                     );

        animationEvent.getController().setAnimation(getAnimation(event));
        return PlayState.CONTINUE;
    }

    AnimationBuilder getAnimation(PlayerAnimationEvent animationEvent);
}
