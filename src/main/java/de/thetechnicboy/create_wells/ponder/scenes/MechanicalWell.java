package de.thetechnicboy.create_wells.ponder.scenes;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.block.mechanical_well.MechanicalWellBlock;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.registries.ForgeRegistries;

public class MechanicalWell {
    public static void ponder(SceneBuilder scene, SceneBuildingUtil util){
        scene.title("mechanical_well", "Generating Fluids using the Mechanical Well");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.setSceneOffsetY(-0.5F);
        scene.scaleSceneView(0.8F);

        scene.idle(5);


        BlockPos _WellTop = new BlockPos(1,3,1);
        BlockPos _WellMain = new BlockPos(1,2,1);
        BlockPos _WellSource = new BlockPos(1,1,1);

        Block[] SourceBlock = {Blocks.STONE, Blocks.BEDROCK, Blocks.DIRT};

        Selection WellTop = util.select.position(_WellTop);
        Selection WellMain = util.select.position(_WellMain);
        Selection WellSource = util.select.position(_WellSource);

        scene.world.setBlock(_WellTop, ModBlocks.MECHANICAL_WELL.get().defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.UPPER).setValue(MechanicalWellBlock.UPSIDE_DOWN, false), false);
        scene.world.setBlock(_WellMain, ModBlocks.MECHANICAL_WELL.get().defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER).setValue(MechanicalWellBlock.UPSIDE_DOWN, false), false);

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
                .placeNearTarget()
                .attachKeyFrame();

        scene.idle(20);

        for(Block block: SourceBlock) {
            scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(_WellSource), Pointing.UP).withItem(block.asItem().getDefaultInstance()), 20);
            scene.world.hideSection(WellSource, Direction.EAST);
            scene.idle(16);
            scene.world.setBlock(_WellSource, block.defaultBlockState(), false);
            scene.world.showSection(WellSource, Direction.EAST);
            scene.idle(16);
        }

        scene.idle(20);

        Selection[] rotation = {
                util.select.position(0,0,3),
                util.select.position(1,1,3),
                util.select.position(1,2,3),
                util.select.position(1,2,2)
        };
        for(Selection block: rotation){
            scene.world.showSection(block, Direction.WEST);
        }
        scene.idle(16);
        scene.overlay.showText(60)
                .colored(PonderPalette.INPUT)
                .text("The Well needs rotational energy to generate fluids.")
                .pointAt(util.vector.centerOf(1,2,3))
                .placeNearTarget()
                .attachKeyFrame();
        scene.idle(60);

        scene.overlay.showText(40)
                .colored(PonderPalette.INPUT)
                .text("The fluid can simply be extracted using pipes.")
                .pointAt(util.vector.centerOf(_WellMain))
                .placeNearTarget()
                .attachKeyFrame();
        scene.idle(30);
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(_WellMain.east()), Pointing.RIGHT).withItem(AllBlocks.MECHANICAL_PUMP.get().asItem().getDefaultInstance()), 40);
        scene.idle(40);
    }
}
