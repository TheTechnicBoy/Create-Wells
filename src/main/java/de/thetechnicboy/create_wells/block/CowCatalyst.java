package de.thetechnicboy.create_wells.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.PushReaction;

public class CowCatalyst extends Block {
    public CowCatalyst() {
        super(Properties.of()
                .strength(3.0f, 10.0f)
                .pushReaction(PushReaction.IGNORE)
                .noOcclusion());
    }
}
