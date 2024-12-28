package com.goodbird.playeranimlib.common.entity;

import com.goodbird.playeranimlib.common.network.NetworkWrapper;
import com.goodbird.playeranimlib.common.network.SyncExtPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties {
    private static final String EXT_PROP_NAME = "PlayerAnimLibExtendedPlayer";
    private final EntityPlayer player;
    private String geckoModelPath;
    private String geckoAnimFilePath;
    private String geckoTexturePath;
    private String[] visibleFPBones;
    private String[] headBones;
    private boolean useSkin;

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(EXT_PROP_NAME, new ExtendedPlayer(player));
    }

    public static ExtendedPlayer get(EntityPlayer player) {
        return (ExtendedPlayer)player.getExtendedProperties(EXT_PROP_NAME);
    }

    public ExtendedPlayer(EntityPlayer player) {
        this.player = player;
        geckoModelPath = "geckolib3:geo/bat.geo.json";
        geckoAnimFilePath = "geckolib3:animations/bat.animation.json";
        geckoTexturePath = "geckolib3:textures/model/entity/bat.png";
        visibleFPBones = new String[]{"rightArm", "leftArm"};
        headBones = new String[]{"head"};
        useSkin = false;
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setString("geckoModelPath", geckoModelPath);
        compound.setString("geckoAnimFilePath", geckoAnimFilePath);
        compound.setString("geckoTexturePath", geckoTexturePath);
        NBTTagList visibleFPBonesTag = new NBTTagList();
        for(String entry : visibleFPBones){
            visibleFPBonesTag.appendTag(new NBTTagString(entry));
        }
        compound.setTag("visibleFPBones", visibleFPBonesTag);
        NBTTagList headBonesTag = new NBTTagList();
        for(String entry : headBones){
            headBonesTag.appendTag(new NBTTagString(entry));
        }
        compound.setTag("headBones", headBonesTag);
        compound.setBoolean("useSkin", useSkin);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        geckoModelPath = compound.getString("geckoModelPath");
        geckoAnimFilePath = compound.getString("geckoAnimFilePath");
        geckoTexturePath = compound.getString("geckoTexturePath");
        NBTTagList visibleFPBonesTag = compound.getTagList("visibleFPBones", 8);
        visibleFPBones = new String[visibleFPBonesTag.tagCount()];
        for(int i=0;i<visibleFPBonesTag.tagCount();i++){
            visibleFPBones[i]=visibleFPBonesTag.getStringTagAt(i);
        }
        NBTTagList headBonesTag = compound.getTagList("headBones", 8);
        headBones = new String[headBonesTag.tagCount()];
        for(int i=0;i<headBonesTag.tagCount();i++){
            headBones[i]=headBonesTag.getStringTagAt(i);
        }
        useSkin = compound.getBoolean("useSkin");
    }

    public void syncToOthers(){
        NetworkWrapper.sendToAllTracking(new SyncExtPlayer(player), player);
    }

    @Override
    public void init(Entity entity, World world) {

    }

    public String getGeckoModelPath() {
        return geckoModelPath;
    }

    public void setGeckoModelPath(String geckoModelPath) {
        this.geckoModelPath = geckoModelPath;
        syncToOthers();
    }

    public String getGeckoAnimFilePath() {
        return geckoAnimFilePath;
    }

    public void setGeckoAnimFilePath(String geckoAnimFilePath) {
        this.geckoAnimFilePath = geckoAnimFilePath;
        syncToOthers();
    }

    public String getGeckoTexturePath() {
        return geckoTexturePath;
    }

    public void setGeckoTexturePath(String geckoTexturePath) {
        this.geckoTexturePath = geckoTexturePath;
        syncToOthers();
    }

    public String[] getVisibleFPBones() {
        return visibleFPBones;
    }

    public void setVisibleFPBones(String[] visibleFPBones) {
        this.visibleFPBones = visibleFPBones;
        syncToOthers();
    }

    public boolean isUseSkin() {
        return useSkin;
    }

    public void setUseSkin(boolean useSkin) {
        this.useSkin = useSkin;
        syncToOthers();
    }

    public String[] getHeadBones() {
        return headBones;
    }

    public void setHeadBones(String[] headBones) {
        this.headBones = headBones;
        syncToOthers();
    }

    public void setHeadBone(String headBone) {
        this.headBones = new String[]{headBone};
        syncToOthers();
    }
}
