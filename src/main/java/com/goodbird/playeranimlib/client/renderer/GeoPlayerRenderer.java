package com.goodbird.playeranimlib.client.renderer;

import com.goodbird.playeranimlib.client.model.GeoPlayerModel;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.core.IAnimatableModel;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GeoPlayerRenderer extends GeoEntityRenderer {
    public static GeoPlayerRenderer INSTANCE = new GeoPlayerRenderer();

    public GeoPlayerRenderer() {
        super(new GeoPlayerModel());
    }

    @Override
    public Color getRenderColor(Object obj, float partialTicks) {
        EntityPlayer animatable = (EntityPlayer) obj;
        return animatable.hurtTime <= 0 && animatable.deathTime <= 0 ? Color.ofRGBA(255, 255, 255, 255) : Color.ofRGBA(255, 153, 153, 255);
    }

    @Override
    public boolean isBoneRenderOverriden(Object animatable, GeoBone bone) {
        return bone.getName().equals("held_item") || bone.getName().equals("left_held_item");
    }

    public void renderAfter(GeoModel model, Object animatable, float ticks, float red, float green, float blue, float alpha) {
        super.renderAfter(model, animatable, ticks, red, green, blue, alpha);
        if (model.getBone("held_item").isPresent() && ((EntityPlayer)animatable).getHeldItem() != null) {
            GeoBone bone = model.getBone("held_item").get();
            this.renderItem(((EntityPlayer)animatable), ((EntityPlayer)animatable).getHeldItem(), bone);
        }
    }

    public void renderItem(EntityPlayer animatable, ItemStack stack, GeoBone locator) {
        GL11.glPushMatrix();
        float scale = 0.5F;
        GL11.glScaled(scale, scale, scale);
        GeoBone[] bonePath = this.getPathFromRoot(locator);

        for (GeoBone b : bonePath) {
            GL11.glTranslatef(b.getPositionX() / (16.0F * scale), b.getPositionY() / (16.0F * scale), b.getPositionZ() / (16.0F * scale));
            GL11.glTranslatef(b.getPivotX() / (16.0F * scale), b.getPivotY() / (16.0F * scale), b.getPivotZ() / (16.0F * scale));
            GL11.glRotated((double) b.getRotationZ() / Math.PI * 180.0, 0.0, 0.0, 1.0);
            GL11.glRotated((double) b.getRotationY() / Math.PI * 180.0, 0.0, 1.0, 0.0);
            GL11.glRotated((double) b.getRotationX() / Math.PI * 180.0, 1.0, 0.0, 0.0);
            GL11.glScalef(b.getScaleX(), b.getScaleY(), b.getScaleZ());
            GL11.glTranslatef(-b.getPivotX() / (16.0F * scale), -b.getPivotY() / (16.0F * scale), -b.getPivotZ() / (16.0F * scale));
        }
        GL11.glRotatef(250.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(40.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.4F, -0.55F, 1.7F);
        RenderManager.instance.itemRenderer.renderItem(animatable, stack, 0, IItemRenderer.ItemRenderType.INVENTORY);
        GL11.glPopMatrix();
    }

    static {
        AnimationController.addModelFetcher((object) -> {
            return object instanceof EntityPlayer ? (IAnimatableModel)INSTANCE.getGeoModelProvider() : null;
        });
    }
}
