# CustomBrewRecipes
A fabric mod that allows developers to create new Brewing Recipes that also support custom non potion items and NBT data. 

Detailed instructions can be found in the javadoc, but all you have to do is calling 
```java
CustomBrewRecipeRegister.registerCustomRecipe()
```
 and supply the items for the recipe.
Or if you need nbt, use the 
```java
CustomBrewRecipeRegister.registerCustomRecipeNbt()
```
and either use itemstacks that already have nbt or supply the nbt to the method.

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


[![bisecthosting](https://github.com/Emafire003/ColoredGlowLib/assets/29462910/973c0c1a-062c-4c4a-aa04-f02e184fd5d7)](https://www.bisecthosting.com/LightDev)
