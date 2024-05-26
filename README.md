# CustomBrewRecipes
A fabric mod that allows developers to create new Brewing Recipes that also support custom non potion items and Components or NBT data. 

Detailed instructions can be found in the javadoc, but all you have to do is calling 
```java
CustomBrewRecipeRegister.registerCustomRecipe()
```
 and supply the items for the recipe.
 
Or if you need custom components, use the 
```java
CustomBrewRecipeRegister.registerCustomRecipeWithComponents()
```
and either use itemstacks that already have custom components or supply the nbt to the method.

There also is a NBT version of the method that saves stuff to the CustomData component.

You can also check for the presence of component types (or nbt-fields) and not their values! 
```java
CustomBrewRecipeRegister.registerCustomRecipeWithComponentPrescence()
```
NB: The output item will still need to have the whole ComponentMap/NBT, since it's the one which is going to be created!


You can also check for the presence of field/componet and specific value! You will need to use 
```java
CustomBrewRecipeRegister.registerCustomRecipeWithComponentType();
```
NB: The output item will still need to have the whole ComponentMap/NBT, since it's the one which is going to be created!



[![bisecthosting](https://github.com/Emafire003/ColoredGlowLib/assets/29462910/973c0c1a-062c-4c4a-aa04-f02e184fd5d7)](https://www.bisecthosting.com/LightDev)

## Setup

You can add it to your project by adding in your build.gradle:
```gradle
repositories {
    maven {
        name = "Modrinth"
        url = "https://api.modrinth.com/maven"
        content {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    modImplementation "maven.modrinth:custombrewrecipes:<version>"
}
```


## License
This mod is available under the CC0 license.
