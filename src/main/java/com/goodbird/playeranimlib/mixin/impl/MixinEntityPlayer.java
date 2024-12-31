package com.goodbird.playeranimlib.mixin.impl;

import com.goodbird.playeranimlib.mixin.IEntityPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends EntityLivingBase implements IAnimationTickable, IAnimatable, IEntityPlayer {
    @Unique
    private final AnimationFactory factory = new AnimationFactory(this);

    public MixinEntityPlayer(World p_i1594_1_) {
        super(p_i1594_1_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(!((EntityPlayer)event.getAnimatable()).onGround){
            event.getController().setAnimation((new AnimationBuilder()).addAnimation("idle", true));
            return PlayState.CONTINUE;
        }
        if(event.isMoving()) {
            event.getController().setAnimation((new AnimationBuilder()).addAnimation("walk2", true));
        }else {
            event.getController().setAnimation((new AnimationBuilder()).addAnimation("idle", true));
        }
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 2.0F, this::predicate));
    }

    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }
}
