package de.thetechnicboy.create_wells.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;




public class FluidExtractionRecipe implements Recipe<Inventory> {

    private static final boolean DEBUG_MODE_PRINTLN = false;
    private final ResourceLocation id;
    private final FluidOutput output;
    private final Condition condition;

    public Condition getCondition() {
        return condition;
    }
    public FluidOutput getOutput() {
        return output;
    }
    public ResourceLocation getID() {
        return id;
    }

    public FluidExtractionRecipe(ResourceLocation id, FluidOutput output, Condition condition) {
        this.id = id;
        this.output = output;
        this.condition = condition;

        ModRecipes.RECIPES.add(this);

        if(DEBUG_MODE_PRINTLN) {
            System.out.println("---------------");
            System.out.println("[CW Recipes] " + id.toString());

            System.out.println("[CW Recipes] FLUID:");
            System.out.println("[CW Recipes]    " + output.fluid);
            System.out.println("[CW Recipes]    " + output.amount + " mb");
            System.out.println("[CW Recipes]    " + output.speed + " tick(s)");

            System.out.println("[CW Recipes] CONDITION:");
            System.out.println("[CW Recipes]     DIRECTION: " + condition.direction);
            System.out.println("[CW Recipes]     YMIN: " + condition.yMin);
            System.out.println("[CW Recipes]     YMAX: " + condition.yMax);
            System.out.println("[CW Recipes]     BIOMES:");
            for (String biome : condition.biome) System.out.println("          " + biome);
            System.out.println("[CW Recipes]     DIMENSIONS:");
            for (String dimension : condition.dimension) System.out.println("          " + dimension);
            System.out.println("---------------");
        }
    }


    @Override
    public boolean matches(Inventory inventory, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(Inventory inventory, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
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



    public static class FluidOutput{
        private final Fluid fluid;
        private final int amount;
        private final int speed;

        public int getAmount() {
            return amount;
        }
        public Fluid getFluid() {
            return fluid;
        }
        public int getSpeed(){
            return speed;
        }

        public FluidOutput(Fluid fluid, int amount, int speed) {
            this.fluid = fluid;
            this.amount = amount;
            this.speed = speed;
        }

        public static FluidOutput fromJSON(JsonObject jsonObject){
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(jsonObject.get("fluid").getAsString()));
            int amount = jsonObject.get("amount").getAsInt();
            int speed = jsonObject.get("speed").getAsInt();
            return new FluidOutput(fluid, amount, speed);
        }

        public static FluidOutput fromPacket(FriendlyByteBuf buf) {
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(buf.readResourceLocation());
            int amount = buf.readInt();
            int speed = buf.readInt();
            return new FluidOutput(fluid, amount, speed);
        }

        public void writeToPacket(FriendlyByteBuf buf) {
            buf.writeResourceLocation(ForgeRegistries.FLUIDS.getKey(this.fluid));
            buf.writeInt(this.amount);
            buf.writeInt(this.speed);
        }
    }

    public static class Condition{
        private final Direction direction;
        private final String[] biome;
        private final String[] dimension;
        private final int yMin;
        private final int yMax;

        public Direction isUpsideDown() {
            return direction;
        }
        public String[] getBiome() {
            return biome;
        }
        public String[] getDimension() {
            return dimension;
        }
        public int getYMin() {
            return yMin;
        }
        public int getYMax() {
            return yMax;
        }

        public Condition(Direction direction, String[] biome, String[] dimension, int yMin, int yMax) {
            this.direction = direction;
            this.biome = biome;
            this.dimension = dimension;
            this.yMin = yMin;
            this.yMax = yMax;
        }

        public static Condition fromJSON(JsonObject jsonObject) {
            String _direction = "";
            String[] dimensions = new String[0];
            String[] biomes = new String[0];
            int yMin = -1;
            int yMax = -1;

            try{ _direction = jsonObject.get("direction").getAsString(); } catch (Exception ex) {}
            Direction direction;
            if(_direction.equals("UPSIDE_DOWN")) direction = Direction.UPSIDE_DOWN;
            else if(_direction.equals("NORMAL")) direction = Direction.NORMAL;
            else if(_direction.equals("BOTH")) direction = Direction.BOTH;
            else direction = Direction.NORMAL;


            try{
                JsonArray biomesArray = jsonObject.getAsJsonArray("biome");
                biomes = new String[biomesArray.size()];
                for (int i = 0; i < biomesArray.size(); i++) {
                    biomes[i] = biomesArray.get(i).getAsString();
                }
            } catch (Exception ex) {}

            try{
                JsonArray dimensionArray = jsonObject.getAsJsonArray("dimension");
                dimensions = new String[dimensionArray.size()];
                for (int i = 0; i < dimensionArray.size(); i++) {
                    dimensions[i] = dimensionArray.get(i).getAsString();
                }
            } catch (Exception ex) {}

            try{ yMin = jsonObject.get("yMin").getAsInt(); } catch (Exception ex) {}
            try{ yMax = jsonObject.get("yMax").getAsInt(); } catch (Exception ex) {}

            return new Condition(direction, biomes, dimensions, yMin, yMax);
        }

        public static Condition fromPacket(FriendlyByteBuf buf) {
            String _direction = buf.readUtf();
            Direction direction;
            if(_direction.equals("UPSIDE_DOWN")) direction = Direction.UPSIDE_DOWN;
            else if(_direction.equals("NORMAL")) direction = Direction.NORMAL;
            else if(_direction.equals("BOTH")) direction = Direction.BOTH;
            else direction = Direction.NORMAL;

            int biomesSize = buf.readInt();
            String[] biomes = new String[biomesSize];
            for (int i = 0; i < biomesSize; i++) {
                biomes[i] = buf.readUtf();
            }

            int dimensionsSize = buf.readInt();
            String[] dimensions = new String[dimensionsSize];
            for (int i = 0; i < dimensionsSize; i++) {
                dimensions[i] = buf.readUtf();
            }

            int yMin = buf.readInt();
            int yMax = buf.readInt();

            return new Condition(direction, biomes, dimensions, yMin, yMax);
        }

        public void writeToPacket(FriendlyByteBuf buf) {
            if(this.direction == Direction.UPSIDE_DOWN) buf.writeUtf("UPSIDE_DOWN");
            else if(this.direction == Direction.NORMAL) buf.writeUtf("NORMAL");
            else if(this.direction == Direction.BOTH) buf.writeUtf("BOTH");

            buf.writeInt(this.biome.length);
            for (String biome : this.biome) {
                buf.writeUtf(biome);
            }

            buf.writeInt(this.dimension.length);
            for (String dimension : this.dimension) {
                buf.writeUtf(dimension);
            }

            buf.writeInt(this.yMin);
            buf.writeInt(this.yMax);
        }
    }

    public enum Direction{
        BOTH,
        UPSIDE_DOWN,
        NORMAL,
    }
}
