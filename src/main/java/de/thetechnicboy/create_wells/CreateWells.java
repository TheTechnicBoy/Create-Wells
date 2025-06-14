package de.thetechnicboy.create_wells;

import com.mojang.logging.LogUtils;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.client.WellRenderer;
import de.thetechnicboy.create_wells.item.ModItems;
import de.thetechnicboy.create_wells.ponder.ModPonder;
import de.thetechnicboy.create_wells.recipe.ModRecipes;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

@Mod(CreateWells.MODID)
public class CreateWells {
    public static final String MODID = "create_wells";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation genRL(String key) { return ResourceLocation.tryBuild(MODID, key); }
    public static ResourceLocation parseRL(String key) { return ResourceLocation.parse(key); }

    public CreateWells(IEventBus modEventBus, ModContainer modContainer) {

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModCreativeTab.register(modEventBus);

        modEventBus.addListener(this::onClientSetup);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
    }

    private void onClientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.BLACK_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.BLUE_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.BROWN_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.CYAN_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.GRAY_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.GREEN_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.LIGHT_BLUE_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.LIGHT_GRAY_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.LIME_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.MAGENTA_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.ORANGE_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.PINK_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.PURPLE_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.RED_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.WHITE_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.YELLOW_MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        PonderIndex.addPlugin(new ModPonder());
    }

}
