package com.goodbird.playeranimlib.client.control.event;

import net.minecraft.entity.player.EntityPlayer;
import software.bernie.geckolib3.core.controller.AnimationController;

public class PlayerAnimationEvent {
    private final EntityPlayer animatable;
    public double animationTick;
    private final float limbSwing;
    private final float limbSwingAmount;
    private final float partialTick;
    protected AnimationController controller;

    public PlayerAnimationEvent(EntityPlayer animatable, float limbSwing, float limbSwingAmount, float partialTick) {
        this.animatable = animatable;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.partialTick = partialTick;
    }

    public double getAnimationTick() {
        return this.animationTick;
    }

    public EntityPlayer getAnimatable() {
        return this.animatable;
    }

    public float getLimbSwing() {
        return this.limbSwing;
    }

    public float getLimbSwingAmount() {
        return this.limbSwingAmount;
    }

    public float getPartialTick() {
        return this.partialTick;
    }

    public AnimationController getController() {
        return this.controller;
    }

    public void setController(AnimationController controller) {
        this.controller = controller;
    }
}

