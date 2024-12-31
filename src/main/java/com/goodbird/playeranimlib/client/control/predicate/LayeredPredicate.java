package com.goodbird.playeranimlib.client.control.predicate;

import com.goodbird.playeranimlib.client.control.event.PlayerAnimationEvent;
import com.google.common.collect.Lists;
import software.bernie.geckolib3.core.builder.AnimationBuilder;

import java.util.ArrayList;

public class LayeredPredicate implements IPlayerAnimationPredicate{
    private final ArrayList<IPlayerAnimationPredicate> layers = new ArrayList<>();
    private String name = "";
    private AnimationBuilder defaultAnim;

    public LayeredPredicate(String name){
        this.name = name;
    }

    @Override
    public AnimationBuilder getAnimation(PlayerAnimationEvent animationEvent) {
        for(IPlayerAnimationPredicate predicate : Lists.reverse(layers)){
            AnimationBuilder anim = predicate.getAnimation(animationEvent);
            if(anim!=null){
                return anim;
            }
        }
        return defaultAnim;
    }

    public LayeredPredicate addLayer(IPlayerAnimationPredicate predicate){
        layers.add(predicate);
        return this;
    }

    public ArrayList<IPlayerAnimationPredicate> getLayers(){
        return layers;
    }

    public String getName(){
        return name;
    }

    public AnimationBuilder getDefaultAnim() {
        return defaultAnim;
    }

    public void setDefaultAnim(AnimationBuilder defaultAnim) {
        this.defaultAnim = defaultAnim;
    }
}
