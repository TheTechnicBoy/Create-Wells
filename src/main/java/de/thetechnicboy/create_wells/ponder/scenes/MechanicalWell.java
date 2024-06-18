package de.thetechnicboy.create_wells.ponder.scenes;

import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class MechanicalWell {
    public static void ponder(SceneBuilder scene, SceneBuildingUtil util){
        scene.title("mechanical_well", "Generating Fluids using the Mechanical Well");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.setSceneOffsetY(-0.5F);
        scene.scaleSceneView(0.8F);

        scene.idle(5);

        //

        BlockPos _WellMain = new BlockPos(2,2,2);
        BlockPos _WellSource = new BlockPos(2,1,2);
        BlockPos _WellTop = new BlockPos(2,3,2);

        Block[] SourceBlock = {Blocks.STONE, Blocks.BEDROCK, Blocks.DIRT};

        Selection WellMain = util.select.position(_WellMain);
        Selection WellSource = util.select.position(_WellSource);
        Selection WellTop = util.select.position(_WellTop);

        scene.world.showSection(WellSource, Direction.DOWN);
        scene.idle(10);
        scene.world.showSection(WellMain, Direction.DOWN);
        scene.world.showSection(WellTop, Direction.DOWN);

        scene.idle(10);
        scene.overlay.showText(30)
                .colored(PonderPalette.MEDIUM)
                .text("This is the Mechanical Well")
                .pointAt(util.vector.centerOf(_WellTop))
                .placeNearTarget();

        scene.idle(40);

        scene.overlay.showText(60)
                .colored(PonderPalette.MEDIUM)
                .text("The Fluid it is generating is depended on the Block it is standing on.")
                .pointAt(util.vector.centerOf(_WellSource))
                .placeNearTarget();

        scene.idle(20);

        for(Block block: SourceBlock) {
            scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(_WellSource), Pointing.DOWN).withItem(block.asItem().getDefaultInstance()), 20);
            scene.world.hideSection(WellSource, Direction.EAST);
            scene.idle(16);
            scene.world.setBlock(_WellSource, block.defaultBlockState(), false);
            scene.world.showSection(WellSource, Direction.EAST);
            scene.idle(16);
        }

        scene.idle(20);

        Selection[] rotation = {
                util.select.position(3,1,2),
                util.select.position(3,2,2),
                util.select.position(4,1,2),
                util.select.position(5,1,2),
                util.select.position(5,0,3) };
        for(Selection block: rotation){
            scene.world.showSection(block, Direction.WEST);
        }
        scene.idle(16);
        scene.overlay.showText(60)
                .colored(PonderPalette.INPUT)
                .text("The Well needs rotational energy to generate fluids")
                .pointAt(util.vector.centerOf(4,2,1))
                .placeNearTarget();
        scene.idle(60);
    }
}
