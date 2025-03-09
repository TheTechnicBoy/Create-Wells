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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CreateWells.MODID)
public class CreateWells {

    public static final String MODID = "create_wells";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation genRL(String key) { return ResourceLocation.tryBuild(MODID, key); }
    public static ResourceLocation parseRL(String key) { return ResourceLocation.parse(key); }

    public CreateWells() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModCreativeTab.register(modEventBus);

        modEventBus.addListener(this::onClientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
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
