package me.emafire003.dev.custombrewrecipes.mixin;

import me.emafire003.dev.custombrewrecipes.CustomBrewRecipeRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrewingRecipeRegistry.class)
public abstract class CustomBrewRecipesMixin{

    @Inject(method = "craft", at = @At(value = "TAIL"), cancellable = true)
    private void customCraftInject(ItemStack ingredient, ItemStack input, CallbackInfoReturnable<ItemStack> cir) {
        if (input == null || ingredient == null) {
            return;
        }

        for (CustomBrewRecipeRegister.CustomRecipe<Item> recipe : CustomBrewRecipeRegister.getCustomRecipes()) {
            if (recipe.input().equals(input.getItem()) && recipe.ingredient().equals(ingredient.getItem())) {
                cir.setReturnValue(new ItemStack(recipe.output()));
                return;
            }
        }

        for (CustomBrewRecipeRegister.CustomRecipeV2 recipe : CustomBrewRecipeRegister.getCustomRecipesComponents()) {
            if (CustomBrewRecipeRegister.equalsComponentsNew(ingredient, recipe.ingredient, recipe.ingredient_components, recipe.ingredient_component_type)
                    && CustomBrewRecipeRegister.equalsComponentsNew(input, recipe.input, recipe.input_components, recipe.input_component_type)
            ){
                ItemStack out = new ItemStack(recipe.output);
                if(recipe.output_components != null){
                    out.applyComponentsFrom(recipe.output_components);
                }else{
                    //TODO remove
                    System.out.println("\n\n THE RECIPE COMPONENTS ARE NULL SKIPPING \n\n");
                }

                //out.set(DataComponentTypes.CUSTOM_DATA, recipe.output_components);
                cir.setReturnValue(out);
                return;
            }

        }
    }

    @Inject(method = "hasRecipe", at = @At(value = "HEAD"), cancellable = true)
    private void injectHasRecipeCustom(ItemStack input, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir){
        if(hasCustomRecipe(input, ingredient)){
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "isValidIngredient", at = @At(value = "HEAD"), cancellable = true)
    private void injectIsValidIngredientCustom(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if(isCustomRecipeIngredient(stack)){
            cir.setReturnValue(true);
        }
    }

    @Unique
    private boolean hasCustomRecipe(ItemStack input, ItemStack ingredient) {
        for(CustomBrewRecipeRegister.CustomRecipe<Item> recipe : CustomBrewRecipeRegister.getCustomRecipes()) {
            if (recipe.input().equals(input.getItem()) && recipe.ingredient().equals(ingredient.getItem())) {
                return true;
            }
        }
        for (CustomBrewRecipeRegister.CustomRecipeV2 recipe : CustomBrewRecipeRegister.getCustomRecipesComponents()) {
            if (CustomBrewRecipeRegister.equalsComponentsNew(ingredient, recipe.ingredient, recipe.ingredient_components, recipe.ingredient_component_type)
                    && CustomBrewRecipeRegister.equalsComponentsNew(input, recipe.input, recipe.input_components, recipe.input_component_type)
            ){
                return true;
            }
        }

        return false;
    }

    @Unique
    private boolean isCustomRecipeIngredient(ItemStack stack) {
        for(CustomBrewRecipeRegister.CustomRecipe<Item> recipe : CustomBrewRecipeRegister.getCustomRecipes()) {
            if (recipe.ingredient() == stack.getItem()) {
                return true;
            }
        }
        for(CustomBrewRecipeRegister.CustomRecipeV2 recipe : CustomBrewRecipeRegister.getCustomRecipesComponents()){
            if(CustomBrewRecipeRegister.equalsComponentsNew(stack, recipe.ingredient, recipe.ingredient_components, recipe.ingredient_component_type)){
                return true;
            }
        }
        return false;
    }

}
