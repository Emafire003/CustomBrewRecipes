package me.emafire003.dev.custombrewrecipes;

import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomBrewRecipeRegister {

    private static final Logger LOGGER = LoggerFactory.getLogger("custombrewrecipes");

    private static final List<CustomRecipe<Item>> CUSTOM_RECIPES = new ArrayList<>();
    private static final HashMap<CustomRecipe<Item>, CustomRecipe<Pair<NbtCompound, String>>> CUSTOM_RECIPES_NBT_MAP = new HashMap<>();


    /**Use this method to register new recipes using custom items!
     * Call this on initialization!
     *
     * @param input The input item, the "base" item like a water_bottle for normal recipes
     * @param ingredient The ingredient item, like spider's eye, glowstone dust ecc
     * @param output The output item, the one that will result from this recipe
     * */
    public static void registerCustomRecipe(Item input, Item ingredient, Item output) {
        CUSTOM_RECIPES.add(new CustomRecipe<>(input, ingredient, output));
    }

    /**Use this method to register new recipes using custom items.
     * This also supports nbt!
     * Call this on initialization!
     *
     * @param input The input item, the "base" item like a water_bottle for normal recipes
     * @param ingredient The ingredient item, like spider's eye, glowstone dust ecc
     * @param output The output item, the one that will result from this recipe
     * @param input_nbt An NBT compound that will be attached to the input item. Use null if you don't want to add NBT to this item
     * @param ingredient_nbt An NBT compound that will be attached to the ingredient item. Use null if you don't want to add NBT to this item
     * @param output_nbt An NBT compound that will be attached to the output item. Use null if you don't want to add NBT to this item
     * */
    public static void registerCustomRecipeNbt(Item input, Item ingredient, Item output, @Nullable NbtCompound input_nbt, @Nullable NbtCompound ingredient_nbt, @Nullable NbtCompound output_nbt) {
        CUSTOM_RECIPES_NBT_MAP.put(new CustomRecipe<>(input, ingredient, output), new CustomRecipe<>(new Pair<>(input_nbt, null), new Pair<>(ingredient_nbt, null), new Pair<>(output_nbt, null)));
        //CUSTOM_RECIPES_NBT_MAP.put(new CustomRecipe<>(input, ingredient, output), new CustomRecipe<>(input_nbt, ingredient_nbt, output_nbt));
    }

    /**Use this method to register new recipes using custom items.
     * This also supports nbt, but in a different way:
     * it will check only for the presence of a field, and not its values.
     * For example a Glass Bottle with an NBT field of "filledWith" might have values of "air", "water", "random_stuff" or whatever
     * and still be valid.
     * The output item will still need a nbt compound value thogh!
     *
     * Call this on initialization!
     *
     * @param input The input item, the "base" item like a water_bottle for normal recipes
     * @param ingredient The ingredient item, like spider's eye, glowstone dust ecc
     * @param output The output item, the one that will result from this recipe
     * @param input_nbt_field A String that corresponds to a field that must be present on the item, regardless of its value
     * @param ingredient_nbt_field A String that corresponds to a field that must be present on the item, regardless of its value
     * @param output_nbt An NBT compound that will be attached to the output item. Use null if you don't want to add NBT to this item
     * */
    public static void registerCustomRecipeFieldOnlyNbt(Item input, Item ingredient, Item output, @Nullable String input_nbt_field, @Nullable String ingredient_nbt_field, @Nullable NbtCompound output_nbt) {
        CUSTOM_RECIPES_NBT_MAP.put(new CustomRecipe<>(input, ingredient, output), new CustomRecipe<>(new Pair<>(null, input_nbt_field), new Pair<>(null, ingredient_nbt_field), new Pair<>(output_nbt, null)));
    }

    /**Use this method to register new recipes using custom items!
     * This also supports nbt!
     * Call this on initialization!
     *
     * @param input The input itemstack, the "base" item like a water_bottle for normal recipes. This should already have NBT set by you.
     * @param ingredient The ingredient itemstack, like spider's eye, glowstone dust ecc. This should already have NBT set by you.
     * @param output The output itemstack, the one that will result from this recipe. This should already have NBT set by you.
     * */
    public static void registerCustomRecipeNbt(ItemStack input, ItemStack ingredient, ItemStack output) {
        CUSTOM_RECIPES_NBT_MAP.put(new CustomRecipe<>(input.getItem(), ingredient.getItem(), output.getItem()), new CustomRecipe<>(new Pair<>(input.getNbt(), null), new Pair<>(ingredient.getNbt(), null), new Pair<>(output.getNbt(), null)));
    }



    /**Used (internally) to check if an item is a valid input/base, like
     * a bottle or an akward potion.
     * Also checks for nbt if necessary.
     *
     * @param item The item to test as a valid input
     * @return true if the item is a valid input*/
    public static boolean isValidCustomInput(ItemStack item){
        for(CustomRecipe<Item> recipe : CUSTOM_RECIPES){
            if(recipe.input.equals(item.getItem())){
                return true;
            }
        }
        for(CustomRecipe<Item> recipe : CUSTOM_RECIPES_NBT_MAP.keySet()){
            if(equalsNbt(item, recipe.input, CUSTOM_RECIPES_NBT_MAP.get(recipe).input)){
                return true;
            }
        }
        return false;
    }

    /**Checks if two itemstacks have the same NBT. Or not, if they shouldn't have any NBT data*/
    public static boolean equalsNbt(ItemStack recipe_item, ItemStack item){

        if(!recipe_item.hasNbt() && !item.hasNbt()){
            return recipe_item.isOf(item.getItem());
        }

        return recipe_item.isOf(item.getItem()) && recipe_item.hasNbt() && item.hasNbt() && recipe_item.getNbt().equals(item.getNbt());
    }

    /**Used to check if an itemstack and an item share the same NBT data.
     * This also applies to not having nbt data.
     * This is also used to check if the itemstack has an NBT field present,
     * but not necessarily have the same values as well
     *
     * @param item The itemstack that is used and should have nbt data
     * @param recipe_item The item from the recipe
     * @param nbt_with_field A pair of NbtCompound and a String representing a filed that item nbt must contain.
     *
     * @return true if the item from the stack and the one from the recipe have the same nbt, or don't have nbt.
     * */
    public static boolean equalsNbt(ItemStack item, Item recipe_item, @Nullable Pair<NbtCompound, String> nbt_with_field){

        //This checks if the item doesn't have nbt and if the pair is null or both of the string filed and nbt are null, in which case the recipe data is null so we simply check to see if the item is the same one
        if(!item.hasNbt() && (nbt_with_field == null || (nbt_with_field.getFirst() == null && nbt_with_field.getSecond() == null))){
            //LOGGER.info("Returning " + item.isOf(recipe_item) + " beacuse both don't have nbt or strings");
            return item.isOf(recipe_item);
        }
        if(nbt_with_field == null){
            return false;
        }

        //If the string is not null it means it wants to check for the presence of a filed and not its contents
        if(nbt_with_field.getSecond() != null){

            String nbt_field = nbt_with_field.getSecond();
            if(item.hasNbt() && item.getNbt() != null){
                return item.getNbt().contains(nbt_field);
            }
            //If the string is not null, but it's not present on the item return false.
            return false;
        }

        //If we are here it means that strings have been checked already,
        //so if the NBT value is null and the item does not have nbt we simply check if the item is the same
        if(!item.hasNbt() && nbt_with_field.getFirst() == null){
            return item.isOf(recipe_item);
        }

        //If the thing is null, it means it wants to check for the whole nbt compound to be equal
        NbtCompound nbt = nbt_with_field.getFirst();
        if(nbt == null){
            return false;
        }

        if(nbt.equals(item.getNbt())){
            return item.isOf(recipe_item);
        }
        return false;
    }

    public static List<CustomRecipe<Item>> getCustomRecipes(){
        return CUSTOM_RECIPES;
    }

    public static HashMap<CustomRecipe<Item>, CustomRecipe<Pair<NbtCompound, String>>> getCustomRecipesNBTMap(){
        return CUSTOM_RECIPES_NBT_MAP;
    }

    public record CustomRecipe<T>(T input, T ingredient, T output) {
    }
}
