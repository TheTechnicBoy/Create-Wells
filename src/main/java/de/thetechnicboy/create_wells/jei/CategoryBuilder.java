package de.thetechnicboy.create_wells.jei;

import com.simibubi.create.compat.jei.CreateJEI;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.infrastructure.config.CRecipes;
import de.thetechnicboy.create_wells.CreateWells;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CategoryBuilder<T extends Recipe<?>> {
    private final Class<? extends T> recipeClass;
    private Predicate<CRecipes> predicate = cRecipes -> true;

    private IDrawable background;
    private IDrawable icon;

    private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList<>();
    private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();

    public CategoryBuilder(Class<? extends T> recipeClass) {
        this.recipeClass = recipeClass;
    }

    public CategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
        recipeListConsumers.add(consumer);
        return this;
    }

    public CategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType) {
        return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipes::add, recipeType.get()));
    }

    public CategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
        catalysts.add(supplier);
        return this;
    }

    public CategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
        return catalystStack(() -> new ItemStack(supplier.get()
                .asItem()));
    }

    public CategoryBuilder<T> icon(IDrawable icon) {
        this.icon = icon;
        return this;
    }

    public CategoryBuilder<T> itemIcon(ItemLike item) {
        icon(new ItemIcon(() -> new ItemStack(item)));
        return this;
    }

    public CategoryBuilder<T> background(IDrawable background) {
        this.background = background;
        return this;
    }

    public CategoryBuilder<T> emptyBackground(int width, int height) {
        background(new EmptyBackground(width, height));
        return this;
    }

    public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
        Supplier<List<T>> recipesSupplier;
        recipesSupplier = () -> {
            List<T> recipes = new ArrayList<>();
            for (Consumer<List<T>> consumer : recipeListConsumers)
                consumer.accept(recipes);
            return recipes;
        };

        CreateRecipeCategory.Info<T> info = new CreateRecipeCategory.Info<>(
                new mezz.jei.api.recipe.RecipeType<>(new ResourceLocation(CreateWells.MODID, name), recipeClass),
                Component.literal("Fluid Extraction"), background, icon, recipesSupplier, catalysts);
        CreateRecipeCategory<T> category = factory.create(info);
        return category;
    }
}
