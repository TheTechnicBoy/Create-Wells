package de.thetechnicboy.create_wells.ponder.scenes;

import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import net.minecraft.core.Direction;

public class MechanicalWell {
    public static void ponder(SceneBuilder scene, SceneBuildingUtil util){
        scene.title("mechanical_well", "Generating Fluids using the Mechanical Well");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.world.showSection(util.select.everywhere(), Direction.DOWN);
        scene.setSceneOffsetY(-1);


        scene.idle(20);
        scene.overlay.showText(60)
                .attachKeyFrame()
                .text("")
                .pointAt(util.vector.of(1,1,1))
                .placeNearTarget();
        scene.idle(40);
        scene.rotateCameraY(360);
    }
}
