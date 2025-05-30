package de.thetechnicboy.create_wells.ponder;

import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.ponder.scenes.MechanicalWell;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class ModPonder implements PonderPlugin {

    @Override
    public String getModId() { return CreateWells.MODID;}

    public static final ResourceLocation WELL = CreateWells.genRL("mechanical_well");

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper){
        ArrayList<RegistryObject<Block>> wells = new ArrayList<>();
        wells.add(ModBlocks.BLACK_MECHANICAL_WELL);
        wells.add(ModBlocks.BLUE_MECHANICAL_WELL);
        wells.add(ModBlocks.BROWN_MECHANICAL_WELL);
        wells.add(ModBlocks.CYAN_MECHANICAL_WELL);
        wells.add(ModBlocks.GRAY_MECHANICAL_WELL);
        wells.add(ModBlocks.GREEN_MECHANICAL_WELL);
        wells.add(ModBlocks.LIGHT_BLUE_MECHANICAL_WELL);
        wells.add(ModBlocks.LIGHT_GRAY_MECHANICAL_WELL);
        wells.add(ModBlocks.LIME_MECHANICAL_WELL);
        wells.add(ModBlocks.MAGENTA_MECHANICAL_WELL);
        wells.add(ModBlocks.ORANGE_MECHANICAL_WELL);
        wells.add(ModBlocks.PINK_MECHANICAL_WELL);
        wells.add(ModBlocks.PURPLE_MECHANICAL_WELL);
        wells.add(ModBlocks.RED_MECHANICAL_WELL);
        wells.add(ModBlocks.WHITE_MECHANICAL_WELL);
        wells.add(ModBlocks.YELLOW_MECHANICAL_WELL);

        wells.forEach(w -> {
            helper.forComponents(w.getId())
                    .addStoryBoard("mechanical_well", (s,u) ->
                            MechanicalWell.ponder(s, u, w.get() ), WELL);
        });
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper){
        helper.registerTag(WELL)
                .addToIndex()
                .item(ModBlocks.RED_MECHANICAL_WELL.get(), true, true)
                .title("Create Wells")
                .description("Wells using Create Kinetics")
                .register();

        helper.addToTag(WELL)
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.BLACK_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.BLUE_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.BROWN_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.CYAN_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.GRAY_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.GREEN_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.LIGHT_BLUE_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.LIGHT_GRAY_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.LIME_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.MAGENTA_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.ORANGE_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.PINK_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.PURPLE_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.WHITE_MECHANICAL_WELL.get()))
                .add(ForgeRegistries.BLOCKS.getKey(ModBlocks.YELLOW_MECHANICAL_WELL.get()));

    }
}
