package com.goodbird.playeranimlib.mixin;

import software.bernie.geckolib3.core.builder.AnimationBuilder;

public interface IEntityPlayer {
    AnimationBuilder getBuilderByName(String name);

    void setBuilderByName(String name, AnimationBuilder builder);
}
