package com.goodbird.playeranimlib.common.entity;

import com.goodbird.playeranimlib.common.network.NetworkWrapper;
import com.goodbird.playeranimlib.common.network.SyncExtPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedPlayer implements IExtendedEntityProperties {
    private static final String EXT_PROP_NAME = "PlayerAnimLibExtendedPlayer";
    private final EntityPlayer player;
    private String geckoModelPath;
    private String geckoAnimFilePath;
    private String geckoTexturePath;
    private String[] includeFPBones;
    private String[] excludeFPBones;
    private String[] headBones;
    private boolean useSkin;
    private ResourceLocation animationPredicate;

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(EXT_PROP_NAME, new ExtendedPlayer(player));
    }

    public static ExtendedPlayer get(EntityPlayer player) {
        return (ExtendedPlayer)player.getExtendedProperties(EXT_PROP_NAME);
    }

    public ExtendedPlayer(EntityPlayer player) {
        this.player = player;
        geckoModelPath = "playeranimlib:geo/player_model.geo.json";
        geckoAnimFilePath = "playeranimlib:animations/player.animation.json";
        geckoTexturePath = "playeranimlib:textures/model/entity/bat.png";
        includeFPBones = new String[]{"body"};
        excludeFPBones = new String[]{"head"};
        headBones = new String[]{"head"};
        useSkin = true;
        animationPredicate = new ResourceLocation("playeranimlib", "default");
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setString("geckoModelPath", geckoModelPath);
        compound.setString("geckoAnimFilePath", geckoAnimFilePath);
        compound.setString("geckoTexturePath", geckoTexturePath);

        NBTTagList includeFPBonesTag = new NBTTagList();
        for(String entry : includeFPBones){
            includeFPBonesTag.appendTag(new NBTTagString(entry));
        }
        compound.setTag("includeFPBones", includeFPBonesTag);

        NBTTagList excludeFPBonesTag = new NBTTagList();
        for(String entry : excludeFPBones){
            excludeFPBonesTag.appendTag(new NBTTagString(entry));
        }
        compound.setTag("excludeFPBones", excludeFPBonesTag);

        NBTTagList headBonesTag = new NBTTagList();
        for(String entry : headBones){
            headBonesTag.appendTag(new NBTTagString(entry));
        }
        compound.setTag("headBones", headBonesTag);

        compound.setBoolean("useSkin", useSkin);
        compound.setString("animationPredicate", animationPredicate.toString());
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        geckoModelPath = compound.getString("geckoModelPath");
        geckoAnimFilePath = compound.getString("geckoAnimFilePath");
        geckoTexturePath = compound.getString("geckoTexturePath");

        NBTTagList includeFPBonesTag = compound.getTagList("includeFPBones", 8);
        includeFPBones = new String[includeFPBonesTag.tagCount()];
        for(int i=0;i<includeFPBonesTag.tagCount();i++){
            includeFPBones[i]=includeFPBonesTag.getStringTagAt(i);
        }

        NBTTagList excludeFPBonesTag = compound.getTagList("excludeFPBones", 8);
        excludeFPBones = new String[excludeFPBonesTag.tagCount()];
        for(int i=0;i<excludeFPBonesTag.tagCount();i++){
            excludeFPBones[i]=excludeFPBonesTag.getStringTagAt(i);
        }

        NBTTagList headBonesTag = compound.getTagList("headBones", 8);
        headBones = new String[headBonesTag.tagCount()];
        for(int i=0;i<headBonesTag.tagCount();i++){
            headBones[i]=headBonesTag.getStringTagAt(i);
        }

        useSkin = compound.getBoolean("useSkin");
        animationPredicate = new ResourceLocation(compound.getString("animationPredicate"));
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

    public String[] getIncludeFPBones() {
        return includeFPBones;
    }

    public void setIncludeFPBones(String[] includeFPBones) {
        this.includeFPBones = includeFPBones;
        syncToOthers();
    }

    public String[] getExcludeFPBones() {
        return excludeFPBones;
    }

    public void setExcludeFPBones(String[] excludeFPBones) {
        this.excludeFPBones = excludeFPBones;
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
