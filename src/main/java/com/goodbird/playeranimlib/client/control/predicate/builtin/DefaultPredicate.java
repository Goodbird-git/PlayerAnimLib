package com.goodbird.playeranimlib.client.control.predicate.builtin;

import com.goodbird.playeranimlib.client.control.predicate.BuilderControlledPredicate;
import com.goodbird.playeranimlib.client.control.predicate.LayeredPredicate;
import com.goodbird.playeranimlib.client.control.predicate.builtin.parts.IdlePosePredicate;
import com.goodbird.playeranimlib.client.control.predicate.builtin.parts.WalkPredicate;
import software.bernie.geckolib3.core.builder.AnimationBuilder;

public class DefaultPredicate extends LayeredPredicate {
    public DefaultPredicate() {
        super("default");
        setDefaultAnim(new AnimationBuilder().loop("idle"));
        addLayer(new LayeredPredicate("idle_random").addLayer(new BuilderControlledPredicate("item_random")));
        addLayer(new IdlePosePredicate());
        addLayer(new WalkPredicate());
        addLayer(new LayeredPredicate("item_use").addLayer(new BuilderControlledPredicate("item_use")));
        addLayer(new LayeredPredicate("manual").addLayer(new BuilderControlledPredicate("manual")));
    }
}
