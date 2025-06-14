package de.thetechnicboy.create_wells.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.thetechnicboy.create_wells.CreateWells;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.List;


public class FluidExtractionRecipe implements Recipe<FluidExtractionContainer> {

    private static final boolean DEBUG_MODE_PRINTLN = false;
    private final ResourceLocation id;
    private final FluidOutput output;
    private final Condition condition;

    public static final Codec<FluidExtractionRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(FluidExtractionRecipe::getId),
            FluidOutput.CODEC.fieldOf("output").forGetter(FluidExtractionRecipe::getOutput),
            Condition.CODEC.fieldOf("condition").forGetter(FluidExtractionRecipe::getCondition)
    ).apply(instance, FluidExtractionRecipe::new));

    public Condition getCondition() {
        return condition;
    }
    public FluidOutput getOutput() {
        return output;
    }

    private FluidExtractionRecipe(ResourceLocation id, FluidOutput output, Condition condition) {
        this.id = id;
        this.output = output;
        this.condition = condition;

        if(DEBUG_MODE_PRINTLN) {
            System.out.println("---------------");
            System.out.println("[CW Recipes] " + id.toString());

            System.out.println("[CW Recipes] FLUID:");
            System.out.println("[CW Recipes]    " + output.fluid);
            System.out.println("[CW Recipes]    " + output.amount + " mb/tick");

            System.out.println("[CW Recipes] CONDITION:");
            System.out.println("[CW Recipes]     DIRECTION: " + condition.direction);
            System.out.println("[CW Recipes]     YMIN: " + condition.yMin);
            System.out.println("[CW Recipes]     YMAX: " + condition.yMax);
            System.out.println("[CW Recipes]     BIOMES:");
            for (ResourceLocation biome : condition.biome) System.out.println("          " + biome.toString());
            System.out.println("[CW Recipes]     DIMENSIONS:");
            for (ResourceLocation dimension : condition.dimension) System.out.println("          " + dimension.toString());
            System.out.println("[CW Recipes]     Block: " + (condition.BlockTag ? "#" : "") + condition.block);
            System.out.println("[CW Recipes]     NBT: " + condition.nbt);
            System.out.println("[CW Recipes]     RPM: " + condition.rpm);
            System.out.println("---------------");
        }
    }

    @Override
    public boolean matches(FluidExtractionContainer container, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(FluidExtractionContainer container, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }



    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FLUID_EXTRACTION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FLUID_EXTRACTION_TYPE;
    }


    public static FluidExtractionRecipe registerRecipe(ResourceLocation resourceLocation, FluidOutput output, Condition condition){
        if(output.amount <= 0 || output.fluid == null){
            CreateWells.LOGGER.error("Something is Wrong with the FLuid Output (speed|amount|fluid) of recipe: " + resourceLocation);
            return null;
        }

        if((condition.yMin > condition.yMax && condition.yMin > -255 && condition.yMax > -255) || (condition.yMin > 511 || condition.yMax > 511)){
            CreateWells.LOGGER.error("Something is Wrong with the yMin and yMax in the condition of recipe: " + resourceLocation);
            return null;
        }

        if(condition.direction == Direction.ERROR){
            CreateWells.LOGGER.error("Something is Wrong with the Direction in the condition of recipe: " + resourceLocation);
            return null;
        }

        if(!condition.BlockTag && condition.block != null){
            Block block = BuiltInRegistries.BLOCK.get(condition.getBlock());
            if(block.equals(Blocks.AIR)) return null;
        }

        if(condition.rpm < 0  || condition.rpm > 256){
            CreateWells.LOGGER.error("Something is Wrong with the RPM in the condition of recipe: " + resourceLocation);
            return null;
        }

        return new FluidExtractionRecipe(resourceLocation, output, condition);
    }



    public static class FluidOutput{
        private final Fluid fluid;
        private final int amount;

        public static final Codec<FluidOutput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("fluid").forGetter(fo -> BuiltInRegistries.FLUID.getKey(fo.fluid)),
                Codec.INT.fieldOf("amount").forGetter(FluidOutput::getAmount)
        ).apply(instance, (fluidRL, amount) -> new FluidOutput(BuiltInRegistries.FLUID.get(fluidRL), amount)));


        public int getAmount() {
            return amount;
        }
        public Fluid getFluid() {
            return fluid;
        }

        public FluidOutput(Fluid fluid, int amount) {
            this.fluid = fluid;
            this.amount = amount;
        }

        public static FluidOutput fromJSON(com.google.gson.JsonObject jsonObject){
            Fluid fluid = null;
            int amount = 0;

            try { fluid = BuiltInRegistries.FLUID.get(CreateWells.parseRL(jsonObject.get("fluid").getAsString())); } catch (Exception ignored) {};
            try { amount = jsonObject.get("amount").getAsInt(); } catch (Exception ignored) {};

            return new FluidOutput(fluid, amount);
        }

        public static FluidOutput fromPacket(FriendlyByteBuf buf) {
            Fluid fluid = BuiltInRegistries.FLUID.get(buf.readResourceLocation());
            int amount = buf.readInt();
            return new FluidOutput(fluid, amount);
        }

        public void writeToPacket(FriendlyByteBuf buf) {
            buf.writeResourceLocation(BuiltInRegistries.FLUID.getKey(this.fluid));
            buf.writeInt(this.amount);
        }
    }

    public static class Condition{
        private final Direction direction;
        private final List<ResourceLocation> biome;
        private final List<ResourceLocation> dimension;
        private final int yMin;
        private final int yMax;
        private final ResourceLocation block;
        private final boolean BlockTag;
        private final String nbt;
        private final int rpm;

        public static final Codec<Condition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Direction.CODEC.fieldOf("direction").forGetter(Condition::getDirection),
                Codec.list(ResourceLocation.CODEC).fieldOf("biome").forGetter(Condition::getBiome),
                Codec.list(ResourceLocation.CODEC).fieldOf("dimension").forGetter(Condition::getDimension),
                Codec.INT.fieldOf("yMin").forGetter(Condition::getYMin),
                Codec.INT.fieldOf("yMax").forGetter(Condition::getYMax),
                ResourceLocation.CODEC.fieldOf("block").forGetter(Condition::getBlock),
                Codec.BOOL.fieldOf("blockTag").forGetter(Condition::isBlockTag),
                Codec.STRING.fieldOf("nbt").forGetter(Condition::getNbt),
                Codec.INT.fieldOf("rpm").forGetter(Condition::getRPM)
        ).apply(instance, Condition::new));


        public Direction getDirection() {
            return direction;
        }
        public List<ResourceLocation> getBiome() {
            return biome;
        }
        public List<ResourceLocation> getDimension() {
            return dimension;
        }
        public int getYMin() {
            return yMin;
        }
        public int getYMax() {
            return yMax;
        }
        public ResourceLocation getBlock() {
            return block;
        }
        public boolean isBlockTag() {
            return BlockTag;
        }
        public String getNbt() {return nbt;}
        public int getRPM() {
            return rpm;
        }

        public Condition(Direction direction, List<ResourceLocation> biome, List<ResourceLocation> dimension, int yMin, int yMax, ResourceLocation block, boolean BlockTag, String nbt, int rpm) {
            this.direction = direction;
            this.biome = biome;
            this.dimension = dimension;
            this.yMin = yMin;
            this.yMax = yMax;
            this.block = block;
            this.BlockTag = BlockTag;
            this.nbt = nbt;
            this.rpm = rpm;
        }

        public static Condition fromJSON(com.google.gson.JsonObject jsonObject) {
            String _direction = null;
            List<ResourceLocation> dimensions = new ArrayList<>();
            List<ResourceLocation> biomes = new ArrayList<>();
            int yMin = -255;
            int yMax = -255;
            ResourceLocation block = null;
            String nbt = "[]";
            boolean blockTag = false;
            int rpm = 0;

            try{ _direction = jsonObject.get("direction").getAsString(); } catch (Exception ignored) {}
            Direction direction;
            if(_direction == null) direction = Direction.NORMAL;
            else if(_direction.equalsIgnoreCase("NORMAL")) direction = Direction.NORMAL;
            else if(_direction.equalsIgnoreCase("BOTH")) direction = Direction.BOTH;
            else if(_direction.equalsIgnoreCase("UPSIDE_DOWN")) direction = Direction.UPSIDE_DOWN;
            else direction = Direction.ERROR;

            try{
                com.google.gson.JsonArray biomesArray = jsonObject.getAsJsonArray("biome");
                for (int i = 0; i < biomesArray.size(); i++) {
                    biomes.add(CreateWells.parseRL(biomesArray.get(i).getAsString()));
                }
            } catch (Exception ignored) {}

            try{
                com.google.gson.JsonArray dimensionArray = jsonObject.getAsJsonArray("dimension");
                for (int i = 0; i < dimensionArray.size(); i++) {
                    dimensions.add(CreateWells.parseRL(dimensionArray.get(i).getAsString()));
                }
            } catch (Exception ignored) {}

            try{
                blockTag = jsonObject.get("block").getAsString().startsWith("#");
                if(blockTag) block = CreateWells.parseRL(jsonObject.get("block").getAsString().split("#")[1]);
                else block = CreateWells.parseRL(jsonObject.get("block").getAsString());
            } catch (Exception ignored) {}

            try{
                if(!blockTag){
                    String tempNbt = jsonObject.get("nbt").getAsString();
                    if(tempNbt.startsWith("[") && tempNbt.endsWith("]")){
                        nbt = tempNbt;
                    }
                }
            }catch(Exception ignored) {}

            try{ yMin = jsonObject.get("yMin").getAsInt(); } catch (Exception ignored) {}
            try{ yMax = jsonObject.get("yMax").getAsInt(); } catch (Exception ignored) {}

            try{ rpm = jsonObject.get("rpm").getAsInt(); } catch (Exception ignored) {}

            return new Condition(direction, biomes, dimensions, yMin, yMax, block, blockTag, nbt, rpm);
        }

        public static Condition fromPacket(FriendlyByteBuf buf) {
            String _direction = buf.readUtf();
            Direction direction;
            if(_direction.equals("UPSIDE_DOWN")) direction = Direction.UPSIDE_DOWN;
            else if(_direction.equals("NORMAL")) direction = Direction.NORMAL;
            else if(_direction.equals("BOTH")) direction = Direction.BOTH;
            else direction = Direction.NORMAL;

            int biomesSize = buf.readInt();
            List<ResourceLocation> biomes = new ArrayList<>();
            for (int i = 0; i < biomesSize; i++) {
                biomes.add(buf.readResourceLocation());
            }

            int dimensionsSize = buf.readInt();
            List<ResourceLocation> dimensions = new ArrayList<>();
            for (int i = 0; i < dimensionsSize; i++) {
                dimensions.add(buf.readResourceLocation());
            }

            int yMin = buf.readInt();
            int yMax = buf.readInt();

            ResourceLocation block = buf.readResourceLocation();
            boolean blockTag = buf.readBoolean();
            String nbt = buf.readUtf();

            int rpm = buf.readInt();

            return new Condition(direction, biomes, dimensions, yMin, yMax, block, blockTag, nbt, rpm);
        }

        public void writeToPacket(FriendlyByteBuf buf) {
            if(this.direction == Direction.UPSIDE_DOWN) buf.writeUtf("UPSIDE_DOWN");
            else if(this.direction == Direction.NORMAL) buf.writeUtf("NORMAL");
            else if(this.direction == Direction.BOTH) buf.writeUtf("BOTH");

            buf.writeInt(this.biome.size());
            for (ResourceLocation biome : this.biome) {
                buf.writeResourceLocation(biome);
            }

            buf.writeInt(this.dimension.size());
            for (ResourceLocation dimension : this.dimension) {
                buf.writeResourceLocation(dimension);
            }

            buf.writeInt(this.yMin);
            buf.writeInt(this.yMax);

            buf.writeResourceLocation(this.block);
            buf.writeBoolean(this.BlockTag);
            buf.writeUtf(this.nbt);

            buf.writeInt(this.rpm);
        }
    }

    public enum Direction{
        BOTH,
        UPSIDE_DOWN,
        NORMAL,
        ERROR;

        public static final Codec<Direction> CODEC = Codec.STRING.xmap(
        s -> {
            try {
                return Direction.valueOf(s.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ERROR;
            }
        },
        Direction::name
    );
    }
}
