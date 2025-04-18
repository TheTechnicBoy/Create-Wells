package de.thetechnicboy.create_wells.ponder.scenes;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.block.mechanical_well.MechanicalWellBlock;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.registries.RegistryObject;

public class MechanicalWell {
    public static void ponder(SceneBuilder _scene, SceneBuildingUtil util, Block baseColor){
        CreateSceneBuilder scene = new CreateSceneBuilder(_scene);
        scene.title("mechanical_well", "Generating Fluids using the Mechanical Well");
        scene.configureBasePlate(0, 0, 4);
        scene.setSceneOffsetY(-0.5F);
        scene.scaleSceneView(0.8F);
        scene.world().setKineticSpeed(util.select().everywhere(), 0f);
        scene.showBasePlate();

        scene.idle(5);


        BlockPos _WellTop = new BlockPos(1,3,1);
        BlockPos _WellMain = new BlockPos(1,2,1);
        BlockPos _WellSource = new BlockPos(1,1,1);

        Block[] SourceBlock = {Blocks.STONE, Blocks.BEDROCK, Blocks.DIRT};
        Block[] WellBlock = {ModBlocks.WHITE_MECHANICAL_WELL.get(), ModBlocks.GREEN_MECHANICAL_WELL.get(), ModBlocks.PURPLE_MECHANICAL_WELL.get()};

        Selection WellTop = util.select().position(_WellTop);
        Selection WellMain = util.select().position(_WellMain);
        Selection WellSource = util.select().position(_WellSource);

        scene.world().setBlock(_WellTop, baseColor.defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.UPPER).setValue(MechanicalWellBlock.UPSIDE_DOWN, false), false);
        scene.world().setBlock(_WellMain, baseColor.defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER).setValue(MechanicalWellBlock.UPSIDE_DOWN, false), false);

        scene.world().showSection(WellSource, Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(WellMain, Direction.DOWN);
        scene.world().showSection(WellTop, Direction.DOWN);

        scene.idle(10);
        scene.overlay().showText(30)
                .colored(PonderPalette.MEDIUM)
                .text("This is the Mechanical Well")
                .pointAt(util.vector().centerOf(_WellTop))
                .placeNearTarget();

        scene.idle(40);

        scene.overlay().showText(60)
                .colored(PonderPalette.MEDIUM)
                .text("The Fluid it is generating is depended on the Block it is standing on.")
                .pointAt(util.vector().centerOf(_WellSource))
                .placeNearTarget()
                .attachKeyFrame();

        scene.idle(20);

        for(Block block: SourceBlock) {
            scene.overlay().showControls(util.vector().centerOf(_WellSource), Pointing.UP, 10).withItem(block.asItem().getDefaultInstance());
            scene.world().hideSection(WellSource, Direction.EAST);
            scene.idle(16);
            scene.world().setBlock(_WellSource, block.defaultBlockState(), false);
            scene.world().showSection(WellSource, Direction.EAST);
            scene.idle(16);
        }

        scene.idle(20);

        Selection[] rotation = {
                util.select().position(1,1,3),
                util.select().position(1,2,3),
                util.select().position(1,2,2)
        };
        for(Selection block: rotation){
            scene.world().showSection(block, Direction.WEST);
        }
        scene.idle(16);
        scene.overlay().showText(90)
                .colored(PonderPalette.INPUT)
                .text("The Well needs specific rotational energy to generate fluids.")
                .pointAt(util.vector().centerOf(1,2,3))
                .placeNearTarget()
                .attachKeyFrame();
        scene.idle(25);
        UpdateKinetics(scene, util);
        scene.idle(5);
        scene.effects().rotationSpeedIndicator(util.grid().at(1,2,3));
        scene.idle(60);

        scene.overlay().showText(70)
                .colored(PonderPalette.INPUT)
                .text("The fluid can simply be extracted using pipes.")
                .pointAt(util.vector().centerOf(_WellMain))
                .placeNearTarget()
                .attachKeyFrame();
        scene.idle(30);
        scene.world().setKineticSpeed(util.select().position(2,1,1), 0.f);
        scene.world().showSection(util.select().position(2,1,1), Direction.WEST);
        scene.idle(20);
        scene.world().showSection(util.select().position(2,2,1), Direction.WEST);
        scene.idle(20);
        scene.world().setKineticSpeed(util.select().position(2,1,1), 32.f);
        scene.effects().rotationSpeedIndicator(util.grid().at(2,1,1));
        scene.idle(20);


        scene.overlay().showText(60)
                .colored(PonderPalette.MEDIUM)
                .text("For aesthetic reasons, there are different colors of the Mechanical Wells.")
                .pointAt(util.vector().centerOf(_WellMain).add(0,0.5,0))
                .placeNearTarget()
                .attachKeyFrame();

        scene.idle(20);

        for(Block block: WellBlock) {
            scene.overlay().showControls(util.vector().centerOf(_WellMain).add(1,0.5,0), Pointing.RIGHT, 10).withItem(block.asItem().getDefaultInstance());
            scene.world().hideSection(WellMain, Direction.EAST);
            scene.world().hideSection(WellTop, Direction.EAST);
            scene.idle(20);

            scene.world().setBlock(_WellTop, block.defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.UPPER).setValue(MechanicalWellBlock.UPSIDE_DOWN, false), false);
            scene.world().setBlock(_WellMain, block.defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER).setValue(MechanicalWellBlock.UPSIDE_DOWN, false), false);

            scene.world().showSection(WellMain, Direction.EAST);
            scene.world().showSection(WellTop, Direction.EAST);
            UpdateKinetics(scene, util);
            scene.idle(20);
        }

        scene.idle(20);


        scene.overlay().showText(60)
                .colored(PonderPalette.MEDIUM)
                .text("Some recipes require a specific height.")
                .pointAt(util.vector().centerOf(_WellMain).add(0,0.5,0))
                .placeNearTarget()
                .attachKeyFrame();
        scene.idle(20);
        scene.overlay().showOutline(PonderPalette.MEDIUM, new Object(), util.select().fromTo(1,0,1, 1,6,1),  40);
        scene.idle(50);

        scene.overlay().showText(60)
                .colored(PonderPalette.MEDIUM)
                .text("Some recipes also require a specific biome or dimension.")
                .pointAt(util.vector().centerOf(_WellMain).add(0,0.5,0))
                .placeNearTarget()
                .attachKeyFrame();
        scene.idle(20);
        scene.overlay().showOutline(PonderPalette.MEDIUM, new Object(), util.select().fromTo(0,0,0, 2,0,2),  115);
        scene.idle(30);

        scene.world().setBlocks(util.select().fromTo(0,0,0, 0,0,2), Blocks.GRASS_BLOCK.defaultBlockState(), true);
        scene.idle(5);
        scene.world().setBlocks(util.select().fromTo(1,0,0, 1,0,2), Blocks.GRASS_BLOCK.defaultBlockState(), true);
        scene.idle(5);
        scene.world().setBlocks(util.select().fromTo(2,0,0, 2,0,2), Blocks.GRASS_BLOCK.defaultBlockState(), true);
        scene.idle(5);
        scene.idle(30);

        scene.world().setBlocks(util.select().fromTo(0,0,0, 2,0,0), Blocks.NETHERRACK.defaultBlockState(), true);
        scene.idle(5);
        scene.world().setBlocks(util.select().fromTo(0,0,1, 2,0,1), Blocks.NETHERRACK.defaultBlockState(), true);
        scene.idle(5);
        scene.world().setBlocks(util.select().fromTo(0,0,2, 2,0,2), Blocks.NETHERRACK.defaultBlockState(), true);
        scene.idle(5);
        scene.idle(30);

        scene.world().setBlocks(util.select().position(0,0,0), Blocks.END_STONE.defaultBlockState(), true);
        scene.world().setBlocks(util.select().position(2,0,0), Blocks.END_STONE.defaultBlockState(), true);
        scene.world().setBlocks(util.select().position(0,0,2), Blocks.END_STONE.defaultBlockState(), true);
        scene.world().setBlocks(util.select().position(2,0,2), Blocks.END_STONE.defaultBlockState(), true);
        scene.idle(5);
        scene.world().setBlocks(util.select().position(1,0,0), Blocks.END_STONE.defaultBlockState(), true);
        scene.world().setBlocks(util.select().position(0,0,1), Blocks.END_STONE.defaultBlockState(), true);
        scene.world().setBlocks(util.select().position(2,0,1), Blocks.END_STONE.defaultBlockState(), true);
        scene.world().setBlocks(util.select().position(1,0,2), Blocks.END_STONE.defaultBlockState(), true);
        scene.idle(5);
        scene.world().setBlocks(util.select().position(1,0,1), Blocks.END_STONE.defaultBlockState(), true);
        scene.idle(5);
        scene.world().setBlock(util.grid().at(1,1,0), Blocks.FIRE.defaultBlockState(), false);
        scene.idle(30);


        scene.overlay().showText(50)
                .colored(PonderPalette.MEDIUM)
                .text("There is also a special thing about the Mechanical Well.")
                .pointAt(util.vector().centerOf(_WellMain).add(0,0.5,0))
                .placeNearTarget()
                .attachKeyFrame();
        scene.idle(20);
        scene.world().hideSection(WellTop, Direction.EAST);
        scene.world().hideSection(WellMain, Direction.EAST);
        scene.world().hideSection(WellSource, Direction.EAST);
        scene.idle(20);


        scene.world().setBlock(_WellSource, baseColor.defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.UPPER).setValue(MechanicalWellBlock.UPSIDE_DOWN, true), false);
        scene.world().setBlock(_WellMain, baseColor.defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER).setValue(MechanicalWellBlock.UPSIDE_DOWN, true), false);
        scene.world().setBlock(_WellTop, SourceBlock[SourceBlock.length -1].defaultBlockState(), false);
        UpdateKinetics(scene, util);
        scene.world().showSection(WellTop, Direction.EAST);
        scene.world().showSection(WellMain, Direction.EAST);
        scene.world().showSection(WellSource, Direction.EAST);

    }

    public static void UpdateKinetics(CreateSceneBuilder scene, SceneBuildingUtil util){
        scene.world().setKineticSpeed(util.select().everywhere(), 32.f);
        scene.world().setKineticSpeed(util.select().position(1,1,3), -32.f);
    }
}
