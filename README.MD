# Registrator
An Auto-Magic registration system built ontop of [**Registrate (_1.16.5-1.0.10_)**](https://github.com/tterrag1098/Registrate/tree/1.16).

This project makes use of my [**Java-Utilities**](https://github.com/ApexModder/JavaUtilities) library.

[**Registrate**](https://github.com/tterrag1098/Registrate/tree/1.16) will be shaded into the Jar file when built, under the package `xyz.apex.repack.<registrate>`<br>
Although Registrate will be shaded & accessible via `AbstractRegistrator#backend`, you should never have to use it outside more advanced use cases.

This project makes use of both mixins & an access transformer.<br>
The access transformer is used to make various elements across Minecraft public-ly visible for better regitration of various objects.<br>
Mixins are used to implement the various `<Object>Like` interfaces (under `xyz.apex.forge.utility.registrator.entry.similar) onto the correct object types.

### Registrator vs Registrate
- Adds support for various new Registration types
  - `PaintingType`
  - `PointOfInterestType`
  - `IRecipeSerializer`
  - `SoundEvent`
  - `VillagerProfession`

- Custom `RegistryEntry` types & builders
  - Custom builders not only gives access to using custom `RegistryEntry` types, but also more freedom in what can be registered during the building process.
  - Custom `RegistryEntry` types allows for things like `ItemProviderEntry` to implement `IItemProvider` and be considered an `Item` object by the game. This allows for passing in your custom item entries right into places that would usually expect an `Item`.
  - Sub registration type helpers.
    - This is mainly helpful for easier sub `Item` registrations.
    - Things like `ToolItem`, `ArmorItem` use different constructor parameters, many helper methods have been implemented into `AbstractRegistor` to make registering these kinds of items easier.
- Custom DataProviders
  - An extended LanguageProvider (`RegistrateLangExtProvider`) has been implemented and many similar `lang()` methods exist, to allow registering translation for various languages. Where as vanilla Registrate only supports `en_us` (& auto `en_ud`) ny default.
    - To use this provider, look for any `lang` method whose first parameter is `languageKey` this is the 'language code' you wish to add a translation for, constants for all vanilla Minecraft supported language codes can be found in `RegistrateLangExtProvider`.
    - **Note**: `RegistrateLangExtProvider` **DOES NOT** support registering for the `en_us` language code, use the vanilla `RegistrateLangProvider` provider type & methods for this.
  - Various new tags providers for the missing tag types (`Potion`, `Enchantment` & `BlockEntity` / `TileEntity`).
  - A new Sound provider is implemented, although it is not recommended being used. Use the `SoundBuiler` (`AbstractRegistrator#sound(<soundName>, [parentObj])`) methods to build a SoundEvent (RegistryEntry `SoundEntry`).
    - This builder has many useful methods to make building your custom sounds easier, and also automatically generates & appends to your mods `sounds.json` file.
  - TemplatePoolProvider
    - This provider type has not been thoroughly tested but should allow you to generate template pools used during world generation.
    - Use at your own risk.

### How to Use
For an example mod on how to use Registrator, see the [TestMod here](https://github.com/ApexModder/Registrator/blob/src/testmod/java/xyz/apex/forge/testmod/init/TRegistry.java).

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

### Project Setup
Due to Registrator including a `mods.toml` file, it has to be added as a dependency to your mod.
Unlike how vanilla Registrate includes no mod data and be be shaded right into your mod jar.

To do this, add the following snippet of code to your `mods.toml` file.
```toml
[[dependencies.<YOUR_MOD_ID_HERE>]]
    modId="registrator"
    mandatory=true
    versionRange="[<REGISTRATOR_VERSION>,)"
    ordering="NONE"
    side="BOTH"
```

#### The following is only required when shipping your mod via CurseForge.<br>
While uploading / editing uploaded releases, find the `Releated Projects` section.<br>
![Releated-Projects](https://i.imgur.com/Lj5jXZj.png)

Search for `Registrator` and click `Add Project` on the right.<br>
This will add Registrator as a dependency on CurseForge. When users download your mod via the CurseForge launcher (or any launchers using CurseForges API), Registrator will automatically be downloaded alongside it.