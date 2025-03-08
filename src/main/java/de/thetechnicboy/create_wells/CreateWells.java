package de.thetechnicboy.create_wells;

import com.mojang.logging.LogUtils;
import com.simibubi.create.AllCreativeModeTabs;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.client.WellRenderer;
import de.thetechnicboy.create_wells.item.ModItems;
import de.thetechnicboy.create_wells.ponder.ModPonder;
import de.thetechnicboy.create_wells.recipe.ModRecipes;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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

    public CreateWells() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::buildContents);


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void buildContents(BuildCreativeModeTabContentsEvent event){
        if(event.getTab() == AllCreativeModeTabs.BASE_CREATIVE_TAB.get()) event.accept(ModItems.MECHANICAL_WELL);
    }
    private void onClientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> BlockEntityRenderers.register(ModBlocks.MECHANICAL_WELL_BLOCKENTITY.get(), WellRenderer::new));
        PonderIndex.addPlugin(new ModPonder());
    }

}
