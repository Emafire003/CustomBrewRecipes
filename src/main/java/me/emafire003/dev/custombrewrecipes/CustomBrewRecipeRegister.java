package me.emafire003.dev.custombrewrecipes;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomBrewRecipeRegister {

    //public static final Logger LOGGER = LoggerFactory.getLogger("custombrewrecipes");

    private static final List<CustomRecipe<Item>> CUSTOM_RECIPES = new ArrayList<>();
    private static final List<CustomRecipeV2> CUSTOM_RECIPES_COMPONENTS = new ArrayList<CustomRecipeV2>();
    private static final List<CustomRecipeNBTOnly> CUSTOM_RECIPES_NBT = new ArrayList<>();


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
    
    //TODO add a way to only register custom data, aka the old NBT, instead of all the components and stuff. 

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
    public static void registerCustomRecipeWithComponents(Item input, Item ingredient, Item output, @Nullable ComponentMap input_nbt, @Nullable ComponentMap ingredient_nbt, @Nullable ComponentMap output_nbt) {
        CUSTOM_RECIPES_COMPONENTS.add(new CustomRecipeV2(input, ingredient, output, input_nbt, ingredient_nbt, output_nbt));
    }

    /**Use this method to register new recipes using custom items!
     * This also supports custom components!
     * Call this on initialization!
     *
     * @param input The input itemstack, the "base" item like a water_bottle for normal recipes. This should already have NBT set by you.
     * @param ingredient The ingredient itemstack, like spider's eye, glowstone dust ecc. This should already have NBT set by you.
     * @param output The output itemstack, the one that will result from this recipe. This should already have NBT set by you.
     * */
    public static void registerCustomRecipeWithComponents(ItemStack input, ItemStack ingredient, ItemStack output) {
        CUSTOM_RECIPES_COMPONENTS.add(new CustomRecipeV2(input.getItem(), ingredient.getItem(), output.getItem(), input.getComponents(), ingredient.getComponents(), output.getComponents()));
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
    public static void registerCustomRecipeWithNbt(Item input, Item ingredient, Item output, @Nullable NbtCompound input_nbt, @Nullable NbtCompound ingredient_nbt, @Nullable NbtCompound output_nbt) {
        //TODO implement checks
        CUSTOM_RECIPES_NBT.add(new CustomRecipeNBTOnly(input, ingredient, output, input_nbt, ingredient_nbt, output_nbt));
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
     * @param input_component_type A DataComponentType that must be present on the item, regardless of its value
     * @param ingredient_component_type A DataComponentType that must be present on the item, regardless of its value
     * @param output_components A ComponentMap that will be attached to the output item. You can use item.getComponents().add(yourstuff).
     * */
    //TODO make an NBT only version
    //TODO may need yet another list to check the presence of only the DataComponentType
    public static void registerCustomRecipeWithComponentPresence(Item input, Item ingredient, Item output, @Nullable DataComponentType<?> input_component_type, @Nullable DataComponentType<?> ingredient_component_type, @Nullable ComponentMap output_components) {
        CUSTOM_RECIPES_COMPONENTS.add(new CustomRecipeV2(input, ingredient, output, ComponentMap.builder().add(input_component_type, null).build(), input_component_type, ComponentMap.builder().add(ingredient_component_type, null).build(), ingredient_component_type, output_components));
    }
    //TODO implement a check to see if the component map is made up of only one thing, and if it is
    

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
     * @param input_component_type The {@link DataComponentType} of the component that must be on the input item
     * @param input_component_value The value of the component that the input item must have
     * @param ingredient_component_type The {@link DataComponentType} of the component that must be on the ingredient item
     * @param ingredient_component_value The value of the component that the ingredient item must have
     * @param output_components A {@link ComponentMap} that will be attached to the output item. Use null if you don't want to add NBT to this item
     * */
    //TODO add the proper check
    public static <T, U> void registerCustomRecipeWithComponentType(Item input, Item ingredient, Item output, @Nullable DataComponentType<T> input_component_type, @Nullable T input_component_value, @Nullable DataComponentType<U> ingredient_component_type, @Nullable U ingredient_component_value, @Nullable ComponentMap output_components) {
        CUSTOM_RECIPES_COMPONENTS.add(new CustomRecipeV2(input, ingredient, output, ComponentMap.builder().add(input_component_type, input_component_value).build(), input_component_type, ComponentMap.builder().add(ingredient_component_type, ingredient_component_value).build(), ingredient_component_type, output_components));
    }





    public static boolean equalsComponentsNew(ItemStack item, Item recipe_item, @Nullable ComponentMap recipe_components, @Nullable DataComponentType<?> recipe_component_type){

        //Checks if they item doesn't have components and if the recipe components & types are null, in which case it returns true only if the item and the one on the recipe are the same type
        if(item.getComponents().isEmpty() && (recipe_components == null && recipe_component_type == null)){
            return item.isOf(recipe_item);
        }

        //If the recipe component type parameter is not null it means it wants to check for the presence
        // of a filed/component (and potentially its contents)
        if(recipe_component_type != null){

            //Checks if there is a ComponentMap, in which case it means a check for the value is needed as well
            //(as long as there is only one component. not necessarily.) TODO maybe I should add a check for multiple components with values
            //The recipe components can be null since they are user specified, while the item components can't. At most they will be empty
            if(recipe_components != null){
                if(item.contains(recipe_component_type)){
                    //No need to check if it's null because the recipe's value shouldn't be null in here.
                    return item.getComponents().get(recipe_component_type).equals(recipe_components.get(recipe_component_type));
                }
                return false;
                /*
                if(item.hasNbt() && item.getNbt() != null && item.getNbt().contains(recipe_nbt_field)){

                    return Objects.requireNonNull(item.getNbt().get(recipe_nbt_field)).equals(recipe_nbt_value);
                }
                return false;*/
            }

            //If the ComponentMap is null then only check for the presence of the field.
            return item.getComponents().contains(recipe_component_type);
        }

        //If we are here it means that Component Types have been checked already,
        //so if the component value is null and the item does not have components we simply check if the item is the same
        if(item.getComponents().isEmpty() && recipe_components == null){
            return item.isOf(recipe_item);
        }

        //If the thing is null, it means it wants to check for the whole nbt compound to be equal
        if(recipe_components == null){
            //Since only the recipe_nbt_is false but the item still has it, they can't be the same.
            return false;
        }
        //TODO may need an iterator and such
        if(recipe_components.equals(item.getComponents())){
            return item.isOf(recipe_item);
        }
        return false;
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
        for(CustomRecipeV2 recipe : CUSTOM_RECIPES_COMPONENTS){
            if(equalsComponentsNew(item, recipe.input, recipe.ingredient_components, recipe.ingredient_component_type)){
                return true;
            }
        }
        return false;
    }

    /**Checks if two itemstacks have the same NBT. Or not, if they shouldn't have any NBT data*/
    /*public static boolean equalsComponents(ItemStack recipe_item, ItemStack item){

        if(!recipe_item.hasNbt() && !item.hasNbt()){
            return recipe_item.isOf(item.getItem());
        }

        return recipe_item.isOf(item.getItem()) && recipe_item.hasNbt() && item.hasNbt() && Objects.requireNonNull(recipe_item.getNbt()).equals(item.getNbt());
    }*/


    /*Used to check if an itemstack and an item share the same NBT data.
     * This also applies to not having nbt data.
     * This is also used to check if the itemstack has an NBT field present,
     * but not necessarily have the same values as well
     *
     * @param item The itemstack that is used and should have nbt data
     * @param recipe_item The item from the recipe
     * @param recipe_nbt_value An ComponentMap value that will need to correspond either to the whole ComponentMap of the item, or to a value stored in a filed if the next parameter isn't null
     * @param recipe_nbt_field A String representing the field that has to be present on the item if the above parameter is null, or if isn't the filed to check the value of.
     *
     * @return true if the item from the stack and the one from the recipe have the same nbt, or don't have nbt.
     * */
    //TODO rewrite
    /*public static boolean equalsComponents(ItemStack item, Item recipe_item, @Nullable ComponentMap recipe_nbt_value, @Nullable String recipe_nbt_field){

        //Checks if they item doesn't have NBT and if the recipe nbt & field are null, in which case it returns true only if the item and the one on the recipe are the same type
        if(!item.getComponents().isEmpty() && (recipe_nbt_value == null && recipe_nbt_field == null)){
            return item.isOf(recipe_item);
        }
        
        
        
        //If the string is not null it means it wants to check for the presence of a filed (and potentially its contents)
        if(recipe_nbt_field != null){

            //Checks if there is a ComponentMap, in which case it means a check for the value is needed as well
            if(recipe_nbt_value != null){
                

                if(item.hasNbt() && item.getNbt() != null && item.getNbt().contains(recipe_nbt_field)){
                    //No need to check if it's null because the recipe's value surely isn't null in here.
                    return Objects.requireNonNull(item.getNbt().get(recipe_nbt_field)).equals(recipe_nbt_value);
                }
                return false;
            }

            //If the ComponentMap is null then only check for the presence of the field.

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
    }*/
    
    

    public static List<CustomRecipe<Item>> getCustomRecipes(){
        return CUSTOM_RECIPES;
    }

    public static List<CustomRecipeV2> getCustomRecipesComponents(){
        return CUSTOM_RECIPES_COMPONENTS;
    }

    public record CustomRecipe<T>(T input, T ingredient, T output) {
    }

    public static class CustomRecipeV2{
        public Item input;
        public Item ingredient;
        public Item output;
        @Nullable
        public ComponentMap input_components;
        @Nullable
        public  ComponentMap ingredient_components;
        @Nullable
        public ComponentMap output_components;
        @Nullable
        public DataComponentType<?> input_component_type;
        @Nullable
        public DataComponentType<?> ingredient_component_type;

        public CustomRecipeV2(Item input, Item ingredient, Item output){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
        }

        public CustomRecipeV2(Item input, Item ingredient, Item output, @Nullable ComponentMap input_components, @Nullable ComponentMap ingredient_components, @Nullable ComponentMap output_components){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
            this.input_components = input_components;
            this.ingredient_components = ingredient_components;
            this.output_components = output_components;
        }

        public CustomRecipeV2(Item input, Item ingredient, Item output, @Nullable DataComponentType<?> input_component_type, @Nullable DataComponentType<?> ingredient_component_type, @Nullable ComponentMap output_components){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
            this.input_component_type = input_component_type;
            this.ingredient_component_type = ingredient_component_type;
            this.output_components = output_components;
        }

        public CustomRecipeV2(Item input, Item ingredient, Item output, @Nullable ComponentMap input_components, @Nullable DataComponentType<?> input_component_type, @Nullable ComponentMap ingredient_components, @Nullable DataComponentType<?> ingredient_component_type, @Nullable ComponentMap output_components){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
            this.input_components = input_components;
            this.ingredient_components = ingredient_components;
            this.input_component_type = input_component_type;
            this.ingredient_component_type = ingredient_component_type;
            this.output_components = output_components;
        }

    }

    public static class CustomRecipeNBTOnly {
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

        public CustomRecipeNBTOnly(Item input, Item ingredient, Item output){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
        }

        public CustomRecipeNBTOnly(Item input, Item ingredient, Item output, @Nullable NbtElement input_nbt, @Nullable NbtElement ingredient_nbt, @Nullable NbtElement output_nbt){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
            this.input_nbt = input_nbt;
            this.ingredient_nbt = ingredient_nbt;
            this.output_nbt = output_nbt;
        }

        public CustomRecipeNBTOnly(Item input, Item ingredient, Item output, @Nullable String input_nbt_field, @Nullable String ingredient_nbt_field, @Nullable NbtElement output_nbt){
            this.input = input;
            this.ingredient = ingredient;
            this.output = output;
            this.input_nbt_field = input_nbt_field;
            this.ingredient_nbt_field = ingredient_nbt_field;
            this.output_nbt = output_nbt;
        }

        public CustomRecipeNBTOnly(Item input, Item ingredient, Item output, @Nullable NbtElement input_nbt, @Nullable String input_nbt_field, @Nullable NbtElement ingredient_nbt, @Nullable String ingredient_nbt_field, @Nullable NbtElement output_nbt){
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