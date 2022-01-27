# Registrator [![release](https://github.com/ApexModder/Registrator/actions/workflows/release.yml/badge.svg)](https://github.com/ApexModder/Registrator/actions/workflows/release.yml) ![license](https://img.shields.io/github/license/ApexModder/Registrator) ![last commit](https://img.shields.io/github/last-commit/ApexModder/Registrator/master) ![release](https://img.shields.io/github/v/release/ApexModder/Registrator)
An Auto-Magic registration system built ontop of [**Registrate (_1.16.5-1.0.10_)**](https://github.com/tterrag1098/Registrate/tree/1.16).

This project makes use of my [**Java-Utilities**](https://github.com/ApexModder/JavaUtilities) library.

[**Registrate**](https://github.com/tterrag1098/Registrate/tree/1.16) will be shaded into the Jar file when built, under the package `xyz.apex.repack.<registrate>`<br>
Although Registrate will be shaded & accessible via `AbstractRegistrator#backend`, you should never have to use it outside more advanced use cases.

This project makes use of both mixins & an access transformer.<br>
The access transformer is used to make various elements across Minecraft public-ly visible for better regitration of various objects.<br>
Mixins are used to implement the various `<Object>Like` interfaces (under `xyz.apex.forge.utility.registrator.entry.similar`) onto the correct object types.

<details>
<summary><b>Registrator vs Registrate</b></summary>

- Adds support for various new Registration types
  - `PaintingType`
  - `PointOfInterestType`
  - `IRecipeSerializer`
  - `SoundEvent`
  - `VillagerProfession`
  - `Structure`

- Custom `RegistryEntry` types & builders
  - Custom builders not only gives access to using custom `RegistryEntry` types, but also more freedom in what can be registered during the building process.
  - Custom `RegistryEntry` types allows for things like `ItemProviderEntry` to implement `IItemProvider` and be considered an `Item` object by the game. This allows for passing in your custom item entries right into places that would usually expect an `Item`.
  - Sub registration type helpers.
    - This is mainly helpful for easier sub `Item` registrations.
    - Things like `ToolItem`, `ArmorItem` use different constructor parameters, many helper methods have been implemented into `AbstractRegistor` to make registering these kinds of items easier.
- Custom DataProviders
  - An extended LanguageProvider (`RegistrateLangExtProvider`) has been implemented and many similar `lang()` methods exist, to allow registering translation for various languages. Whereas vanilla Registrate only supports `en_us` (& auto `en_ud`) by default.
    - To use this provider, look for any `lang` method whose first parameter is `languageKey` this is the 'language code' you wish to add a translation for, constants for all vanilla Minecraft supported language codes can be found in `RegistrateLangExtProvider`.
    - **Note**: `RegistrateLangExtProvider` **DOES NOT** support registering for the `en_us` language code, use the vanilla `RegistrateLangProvider` provider type & methods for this.
  - Various new tags providers for the missing tag types (`Potion`, `Enchantment` & `BlockEntity` / `TileEntity`).
  - A new Sound provider is implemented, although it is not recommended being used. Use the `SoundBuiler` (`AbstractRegistrator#sound(<soundName>, [parentObj])`) methods to build a SoundEvent (RegistryEntry `SoundEntry`).
    - This builder has many useful methods to make building your custom sounds easier, and also automatically generates & appends to your mods `sounds.json` file.
  - TemplatePoolProvider
    - This provider type has not been thoroughly tested but should allow you to generate template pools used during world generation.
    - Use at your own risk.
</details>

<details>
<summary><b>How to Use</b></summary>

For an example mod on how to use Registrator, see the [TestMod here](https://github.com/ApexModder/Registrator/blob/master/src/testmod/java/xyz/apex/forge/testmod/init/TRegistry.java).

This system is used in a very similar way to how vanilla Registrate is used. So their _How to Use_ guide should also be applicable here.
Just substitute any Registrate references for Registrator versions.

Firstly you will need to create a lazily loaded `AbstractRegistrator` object, which will be used & referenced across your entire project.<br>
The easiest method to do this is via the `Registrator#create(<mod_id>)` method.

```java
public static final Lazy<Registrator> REGISTRY = Registrator.create("<YOUR_MOD_ID_HERE>")
```

Once you have a Registrator instance you can then begin building & registering your various objects.

The following will build & register a generic block with US & British translations, require a pickaxe to be broken as well as a generic BlockItem.
```java
public static final BlockEntry<Block> YOUR_BLOCK = REGISTRY.get()
        .block("<your_block_name_here>")
            .lang("Some US Name Here")
            .lang(RegistrateLangExtProvider.EN_GB, "Some British Name Here")
            .harvestTool(ToolType.PICKAXE)
            .requireCorrectToolForDrops()
            .strength(3F, 3F)
        
            .simpleItem()
        .register();
```

To register more advanced object types, a factory method can be appended to the end of any object method.
`block("<your_block_name_here>", [parentObj], <some_block_factory>)`
These factory types take the default constructor parameters of what ever type you are trying to build.
For example, a `BlockFactory` takes in the finalized `AbstractBlock.Properties` object (Similar to how the `Block` constructor takes in a `AbstractBlock.Properties` object).
</details>

<details>
<summary><b>Project Setup</b></summary>

Due to Registrator including a `mods.toml` file, it has to be added as a dependency to your mod.
Unlike how vanilla Registrate includes no mod data and can be shaded right into your mod jar.

To do this, add the following snippet of code to your `mods.toml` file.
```toml
[[dependencies.<YOUR_MOD_ID_HERE>]]
    modId="registrator"
    mandatory=true
    versionRange="[<REGISTRATOR_VERSION>,)"
    ordering="NONE"
    side="BOTH"
```

To include Registrator in your mods `build.gradle` file, We make use of the [CurseMaven Gradle plugin](https://www.cursemaven.com/). <br>
Add the following snippet of code to your `repositories { }` section

```groovy
maven { // CurseForge
    url 'https://cursemaven.com'
    content { includeGroup 'curse.maven' }
}
```

Add the following snippet of code to your `dependencies { }` section<br>
See below for table of CurseForge version IDs

```groovy
compileOnly fg.deobf('curse.maven:registrator-550146:<REGISTRATOR_CURSE_ID>')
runtimeOnly fg.deobf('curse.maven:registrator-550146:<REGISTRATOR_CURSE_ID>')
```

<details>
<summary><b>The following is only required when shipping your mod via CurseForge.</b></summary>

While uploading / editing uploaded releases, find the `Releated Projects` section.<br>
![Releated-Projects](https://i.imgur.com/Lj5jXZj.png)

Search for `Registrator` and click `Add Project` on the right.<br>
This will add Registrator as a dependency on CurseForge. When users download your mod via the CurseForge launcher (or any launchers using CurseForges API), Registrator will automatically be downloaded alongside it.

</details>
</details>

<details>
<summary><b>CurseForge Versions</b></summary>

<details>
<summary><b><i>1.16.5</i></b></summary>

| Registrator Version  | CurseForge File ID |
|:--------------------:|:---:|
| <b><i>1.0.21</i></b> | [3547961](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3624977) |
| <b><i>1.0.20</i></b> | [3547961](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3624959) |
| <b><i>1.0.19</i></b> | [3547961](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3547961) |
| <b><i>1.0.18</i></b> | [3543275](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3543275) |
| <b><i>1.0.17</i></b> | [3543253](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3543253) |
| <b><i>1.0.16</i></b> | [3543232](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3543232) |
| <b><i>1.0.15</i></b> | [3543223](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3543223) |
| <b><i>1.0.14</i></b> | [3543214](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3543214) |
| <b><i>1.0.13</i></b> | [3541415](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3541415) |
| <b><i>1.0.12</i></b> | [3541261](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3541261) |
| <b><i>1.0.11</i></b> | [3541252](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3541252) |
| <b><i>1.0.10</i></b> | [3541180](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3541180) |
| <b><i>1.0.9</i></b>  | [3541175](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3541175) |
| <b><i>1.0.8</i></b>  | [3541170](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3541170) |
| <b><i>1.0.7</i></b>  | [3539396](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3539396) |
| <b><i>1.0.6</i></b>  | [3539331](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3539331) |
| <b><i>1.0.5</i></b>  | [3538337](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3538337) |
| <b><i>1.0.4</i></b>  | [3537821](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3537821) |
| <b><i>1.0.3</i></b>  | [3537477](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3537477) |
| <b><i>1.0.2</i></b>  | [3537398](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3537398) |
| <b><i>1.0.1</i></b>  | [3537338](https://www.curseforge.com/minecraft/mc-mods/registrator/files/3537338) |

</details>

</details>