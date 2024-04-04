package me.emafire003.dev.custombrewrecipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomBrewRecipeRegister {

    //public static final Logger LOGGER = LoggerFactory.getLogger("custombrewrecipes");

    private static final List<CustomRecipe<Item>> CUSTOM_RECIPES = new ArrayList<>();
    private static final List<CustomRecipeV2> CUSTOM_RECIPES_NBT = new ArrayList<>();


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
        CUSTOM_RECIPES_NBT.add(new CustomRecipeV2(input, ingredient, output, input_nbt, ingredient_nbt, output_nbt));
    }

    /**Use this method to register new recipes using custom items.
     * This also supports nbt, but in a different way:
     * it will check only for the presence of a field, and not its values.
     * For example a Glass Bottle with an NBT field of "filledWith" might have values of "air", "water", "random_stuff" or whatever
     * and still be valid.
     * The output item will still need a nbt compound value thogh!
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
        CUSTOM_RECIPES_NBT.add(new CustomRecipeV2(input, ingredient, output, input_nbt_field, ingredient_nbt_field, output_nbt));
    }

    /**Use this method to register new recipes using custom items.
     * This also supports nbt, but in a different way:
     * it will check for the presence of a field, its values.
     * For example a Glass Bottle with an NBT field of "filledWith" might have values of "air", "water", "random_stuff" or whatever
     * and only "air" will be valid. If the NBT also contains other fieldds, they will be ignored.
     * The output item will still need a nbt compound value!
     * Call this on initialization!
     *
     * @param input The input item, the "base" item like a water_bottle for normal recipes
     * @param ingredient The ingredient item, like spider's eye, glowstone dust ecc
     * @param output The output item, the one that will result from this recipe
     * @param input_nbt_field A String that corresponds to a field that must be present on the item, regardless of its value
     * @param input_nbt_value An NBTElement that will need to correspond the value stored inside the specified field
     * @param ingredient_nbt_field A String that corresponds to a field that must be present on the item, regardless of its value
     * @param ingredient_nbt_value An NBTElement that will need to correspond the value stored inside the specified field
     * @param output_nbt An NBT compound that will be attached to the output item. Use null if you don't want to add NBT to this item
     * */
    public static void registerCustomRecipeNbtField(Item input, Item ingredient, Item output, @Nullable String input_nbt_field, @Nullable NbtElement input_nbt_value, @Nullable String ingredient_nbt_field, @Nullable NbtElement ingredient_nbt_value, @Nullable NbtCompound output_nbt) {
        CUSTOM_RECIPES_NBT.add(new CustomRecipeV2(input, ingredient, output, input_nbt_value, input_nbt_field, ingredient_nbt_value, ingredient_nbt_field, output_nbt));
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
        CUSTOM_RECIPES_NBT.add(new CustomRecipeV2(input.getItem(), ingredient.getItem(), output.getItem(), input.getNbt(), ingredient.getNbt(), output.getNbt()));
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
        for(CustomRecipeV2 recipe : CUSTOM_RECIPES_NBT){
            if(equalsNbt(item, recipe.input, recipe.ingredient_nbt, recipe.ingredient_nbt_field)){
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

        return recipe_item.isOf(item.getItem()) && recipe_item.hasNbt() && item.hasNbt() && Objects.requireNonNull(recipe_item.getNbt()).equals(item.getNbt());
    }

    /**Used to check if an itemstack and an item share the same NBT data.
     * This also applies to not having nbt data.
     * This is also used to check if the itemstack has an NBT field present,
     * but not necessarily have the same values as well
     *
     * @param item The itemstack that is used and should have nbt data
     * @param recipe_item The item from the recipe
     * @param recipe_nbt_value An NBTElement value that will need to correspond either to the whole NBTCompound of the item, or to a value stored in a filed if the next parameter isn't null
     * @param recipe_nbt_field A String representing the field that has to be present on the item if the above parameter is null, or if isn't the filed to check the value of.
     *
     * @return true if the item from the stack and the one from the recipe have the same nbt, or don't have nbt.
     * */
    public static boolean equalsNbt(ItemStack item, Item recipe_item, @Nullable NbtElement recipe_nbt_value, @Nullable String recipe_nbt_field){

        //Checks if they item doesn't have NBT and if the recipe nbt & field are null, in which case it returns true only if the item and the one on the recipe are the same type
        if(!item.hasNbt() && (recipe_nbt_value == null && recipe_nbt_field == null)){
            return item.isOf(recipe_item);
        }

        //If the string is not null it means it wants to check for the presence of a filed (and potentially its contents)
        if(recipe_nbt_field != null){

            //Checks if there is an NbtElement, in which case it means a check for the value is needed as well
            if(recipe_nbt_value != null){
                if(item.hasNbt() && item.getNbt() != null && item.getNbt().contains(recipe_nbt_field)){
                    //No need to check if it's null because the recipe's value surely isn't null in here.
                    return Objects.requireNonNull(item.getNbt().get(recipe_nbt_field)).equals(recipe_nbt_value);
                }
                return false;
            }

            //If the NbtElement is null then only check for the presence of the field.

            if(item.hasNbt() && item.getNbt() != null){
                return item.getNbt().contains(recipe_nbt_field);
            }
            //If the string is not null, but it's not present on the item return false.
            return false;
        }

        //If we are here it means that strings have been checked already,
        //so if the NBT value is null and the item does not have nbt we simply check if the item is the same
        if((!item.hasNbt() || item.getNbt()==null) && recipe_nbt_value == null){
            return item.isOf(recipe_item);
        }

        //If the thing is null, it means it wants to check for the whole nbt compound to be equal
        if(recipe_nbt_value == null){
            //Since only the recipe_nbt_is false but the item still has it, they can't be the same.
            return false;
        }
        if(recipe_nbt_value.equals(item.getNbt())){
            return item.isOf(recipe_item);
        }
        return false;
    }

    public static List<CustomRecipe<Item>> getCustomRecipes(){
        return CUSTOM_RECIPES;
    }

    public static List<CustomRecipeV2> getCustomRecipesNBTMap(){
        return CUSTOM_RECIPES_NBT;
    }

    public record CustomRecipe<T>(T input, T ingredient, T output) {
    }

    public static class CustomRecipeV2{
        public Item input;
        public Item ingredient;
        public Item output;
        @Nullable
        public NbtElement input_nbt;
        @Nullable
        public  NbtElement ingredient_nbt;
        @Nullable
        public NbtElement output_nbt;
        @Nullable
        public String input_nbt_field;
        @Nullable
        public String ingredient_nbt_field;

        public CustomRecipeV2(Item input, Item ingredient, Item output){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
        }

        public CustomRecipeV2(Item input, Item ingredient, Item output, @Nullable NbtElement input_nbt, @Nullable NbtElement ingredient_nbt, @Nullable NbtElement output_nbt){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
            this.input_nbt = input_nbt;
            this.ingredient_nbt = ingredient_nbt;
            this.output_nbt = output_nbt;
        }

        public CustomRecipeV2(Item input, Item ingredient, Item output, @Nullable String input_nbt_field, @Nullable String ingredient_nbt_field, @Nullable NbtElement output_nbt){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
            this.input_nbt_field = input_nbt_field;
            this.ingredient_nbt_field = ingredient_nbt_field;
            this.output_nbt = output_nbt;
        }

        public CustomRecipeV2(Item input, Item ingredient, Item output, @Nullable NbtElement input_nbt, @Nullable String input_nbt_field, @Nullable NbtElement ingredient_nbt, @Nullable String ingredient_nbt_field, @Nullable NbtElement output_nbt){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
            this.input_nbt = input_nbt;
            this.ingredient_nbt = ingredient_nbt;
            this.input_nbt_field = input_nbt_field;
            this.ingredient_nbt_field = ingredient_nbt_field;
            this.output_nbt = output_nbt;
        }

    }

}