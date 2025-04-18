package de.thetechnicboy.create_wells;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config
{
    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_SPEC;

    public static ForgeConfigSpec.IntValue MECHANICAL_WELL_STRESS;
    public static ForgeConfigSpec.IntValue MECHANICAL_WELL_CAPACITY;

    static {
        COMMON_BUILDER.comment("Mechanical Well").push("mechanical_well");
        MECHANICAL_WELL_STRESS = COMMON_BUILDER.comment("Mechanical Well base stress impact.")
                        .defineInRange("mechanical_well_stress", 4, 0, 1024);
        MECHANICAL_WELL_CAPACITY = COMMON_BUILDER.comment("Mechanical Well fluid capacity.")
                .defineInRange("mechanical_well_capacity", 4000, 1000, 64000 );
        COMMON_BUILDER.pop();


        COMMON_SPEC = COMMON_BUILDER.build();
    }

}
