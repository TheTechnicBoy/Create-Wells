package de.thetechnicboy.create_wells.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.item.ModItems;
import de.thetechnicboy.create_wells.ponder.scenes.MechanicalWell;

public class ModPonder {
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CreateWells.MODID);

    public static void register(){
        HELPER.addStoryBoard(ModItems.MECHANICAL_WELL.getId(), "mechanical_well", MechanicalWell::ponder, AllPonderTags.KINETIC_APPLIANCES);

        PonderRegistry.TAGS.forTag(AllPonderTags.KINETIC_APPLIANCES)
                .add(ModBlocks.MECHANICAL_WELL.get());
    }
}
