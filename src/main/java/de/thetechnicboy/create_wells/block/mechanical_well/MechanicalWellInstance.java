package de.thetechnicboy.create_wells.block.mechanical_well;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.ShaftInstance;

public class MechanicalWellInstance extends ShaftInstance<MechanicalWellEntity> {
    public MechanicalWellInstance(MaterialManager materialManager, MechanicalWellEntity blockEntity) {
        super(materialManager, blockEntity);
    }
}
