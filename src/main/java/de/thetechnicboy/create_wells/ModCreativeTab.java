package de.thetechnicboy.create_wells;

import de.thetechnicboy.create_wells.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber
public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateWells.MODID);

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }

    public static final RegistryObject<CreativeModeTab> CREATE_WELLS_TAB =
            CREATIVE_TABS.register("create_wells_tab", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.create_wells"))
                            .icon(() -> new ItemStack(ModItems.RED_MECHANICAL_WELL.get()))
                            .displayItems((params, output) -> {
                                output.accept(ModItems.BLACK_MECHANICAL_WELL.get());
                                output.accept(ModItems.BLUE_MECHANICAL_WELL.get());
                                output.accept(ModItems.BROWN_MECHANICAL_WELL.get());
                                output.accept(ModItems.CYAN_MECHANICAL_WELL.get());
                                output.accept(ModItems.GRAY_MECHANICAL_WELL.get());
                                output.accept(ModItems.GREEN_MECHANICAL_WELL.get());
                                output.accept(ModItems.LIGHT_BLUE_MECHANICAL_WELL.get());
                                output.accept(ModItems.LIGHT_GRAY_MECHANICAL_WELL.get());
                                output.accept(ModItems.LIME_MECHANICAL_WELL.get());
                                output.accept(ModItems.MAGENTA_MECHANICAL_WELL.get());
                                output.accept(ModItems.ORANGE_MECHANICAL_WELL.get());
                                output.accept(ModItems.PINK_MECHANICAL_WELL.get());
                                output.accept(ModItems.PURPLE_MECHANICAL_WELL.get());
                                output.accept(ModItems.RED_MECHANICAL_WELL.get());
                                output.accept(ModItems.WHITE_MECHANICAL_WELL.get());
                                output.accept(ModItems.YELLOW_MECHANICAL_WELL.get());
                            })
                            .build());

}
