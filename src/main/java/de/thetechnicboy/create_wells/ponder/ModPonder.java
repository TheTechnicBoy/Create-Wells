package de.thetechnicboy.create_wells.ponder;


import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.item.ModItems;
import de.thetechnicboy.create_wells.ponder.scenes.MechanicalWell;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class ModPonder implements PonderPlugin {

    @Override
    public String getModId() { return CreateWells.MODID;}

    public static final ResourceLocation WELL = new ResourceLocation(CreateWells.MODID, "mechanical_well");

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper){
        helper.forComponents(ModBlocks.MECHANICAL_WELL.getId())
                .addStoryBoard("mechanical_well", MechanicalWell::ponder, WELL);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper){
        helper.registerTag(WELL)
                .addToIndex()
                .item(ModBlocks.MECHANICAL_WELL.get(), true, true)
                .title("Create Wells")
                .description("Wells using Create Kinetics")
                .register();

    }
}
