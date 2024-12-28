package com.goodbird.playeranimlib.client.renderer;

import com.goodbird.playeranimlib.client.model.GeoPlayerModel;
import com.goodbird.playeranimlib.common.entity.ExtendedPlayer;
import net.geckominecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Iterator;

public class GeoFirstPersonRender extends GeoEntityRenderer {
    public static GeoFirstPersonRender INSTANCE = new GeoFirstPersonRender();

    public GeoFirstPersonRender() {
        super(new GeoPlayerModel());
    }

    @Override
    public Color getRenderColor(Object obj, float partialTicks) {
        EntityPlayer animatable = (EntityPlayer) obj;
        return animatable.hurtTime <= 0 && animatable.deathTime <= 0 ? Color.ofRGBA(255, 255, 255, 255) : Color.ofRGBA(255, 153, 153, 255);
    }

    public boolean isBoneToDraw(GeoBone bone, Object animatable){
        ExtendedPlayer player = ExtendedPlayer.get((EntityPlayer) animatable);
        for(String name : player.getVisibleFPBones()){
            if(bone.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void renderRecursively(Tessellator builder, Object animatable, GeoBone bone, float red, float green, float blue, float alpha) {
        renderRecursively(builder, animatable, bone, red, green, blue, alpha, false);
    }

    public void renderRecursively(Tessellator builder, Object animatable, GeoBone bone, float red, float green, float blue, float alpha, boolean draw) {
        MATRIX_STACK.push();
        MATRIX_STACK.translate(bone);
        MATRIX_STACK.moveToPivot(bone);
        MATRIX_STACK.rotate(bone);
        MATRIX_STACK.scale(bone);
        MATRIX_STACK.moveBackFromPivot(bone);
        if (this.isBoneRenderOverriden(animatable, bone)) {
            this.drawOverridenBone(animatable, bone);
            MATRIX_STACK.pop();
        } else {
            Iterator var8;
            if (!bone.isHidden() && draw) {
                var8 = bone.childCubes.iterator();

                while(var8.hasNext()) {
                    GeoCube cube = (GeoCube)var8.next();
                    MATRIX_STACK.push();
                    GlStateManager.pushMatrix();
                    this.renderCube(builder, cube, red, green, blue, alpha);
                    GlStateManager.popMatrix();
                    MATRIX_STACK.pop();
                }
            }

            if (!bone.childBonesAreHiddenToo()) {
                var8 = bone.childBones.iterator();

                while(var8.hasNext()) {
                    GeoBone childBone = (GeoBone)var8.next();
                    boolean nextDraw = draw | isBoneToDraw(childBone, animatable);
                    this.renderRecursively(builder, animatable, childBone, red, green, blue, alpha, nextDraw);
                }
            }

            MATRIX_STACK.pop();
        }
    }

    static {
        AnimationController.addModelFetcher((object) -> {
            return object instanceof EntityPlayer ? (IAnimatableModel)INSTANCE.getGeoModelProvider() : null;
        });
    }
}
