# Create Wells

This mod adds wells to Minecraft, enhancing the exploration and fluid generation experience. It is an extension and adaptation of cubicoder's Well Mod, which you can find [here](https://github.com/cubicoder/Well-Mod).

## Features
- **Custom Recipes**: Create your own recipes for wells using datapacks.
- **Biome/Dimension-specific Fluids**: Depending on the biome or dimension where the well is placed, it can generate different types of fluids.
- **Upside-down Placement**: Build wells in unconventional orientations, maybe yo need this in some recipes.
- **Integration with Create Mod**: The wells will require mechanical rotation from the [Create Mod](https://github.com/Creators-of-Create/Create) for operation.
- **Integration with JEI**: The recipes of the wells will be also displayed in the [JEI](https://github.com/mezz/JustEnoughItems) menu
- **Block Condition**: Depending on the block it is located on, it will generate other fluids.

## Adding Cutom Recepies
### Basic Layout
```jsonc
{
    "type": "create_wells:fluid_extraction",
    "condition": { },
    "output": { }
}
```

### Condition Object
```jsonc
{
    //The Direction of the Well, ceiling / floor.
    //Default Value: NORMAL
    //Acceptable Values: NORMAL, UPSIDE_DOWN, BOTH
    "direction": "BOTH",

    //The Biomes in which the FLuid will be generated
    //Default Value: empty
    //Acceptable Values: Array of Minecraft or Modded Biomes
    "biome": ["minecraft:taiga"],

    //The Dimensions in which the FLuid will be generated
    //Default Value: empty
    //Acceptable Values: Array of Minecraft or Modded Dimensions
    "dimension": ["minecraft:overworld"],

    //The Bounds between where the Well has to be placed
    //Default Value: -255 - No Upper/Lower Bound
    "yMin": 64,
    "yMax": 64,
  
    //The Block which the Wells should stand on
    // tags are also supported -> #forge:stones
    "block": "minecraft:dirt",

    //The Minimum Spped of the Create Network
    "rpm": 128
}
```

### Fluid Object
```jsonc
{
    //Which Fluid should be generated
    //Acceptable Value: Resource Location of a Minecraft or Modded FLuid
    "fluid": "minecraft:water",

    //The Amount (in mB/tick) which will be generated
    //Acceptable Value: amont > 0
    "amount": 1
}
```

### Examples
This Recipe will create 1mB Water in every Tick, if the well is placed in the biome minecraft:plains in the dimension minecraft:overworld. In addition, it has to be placed on exactly Y-height 64, but the direction is not important. It must be placed on a dirt block and the input speed should be at least 64 rpm
```jsonc
{
    "type": "create_wells:fluid_extraction",
    "condition": {
        "direction": "BOTH",
        "biome": [ "minecraft:plains" ],
        "dimension": [ "minecraft:overworld" ],
        "yMin": 64,
        "yMax": 64,
        "block": "minecraft:dirt",
        "rpm": 64
    },
    "output": {
        "fluid": "minecraft:water",
        "amount": 1
    }
}
```

### KubeJS
This Recipe will create 100mB Water in every Tick, if the well is placed in the dimension minecraft:the_nether. In addition it has to be placed UpsideDown but the Y-height doesn't matter.
```js
event.custom({
    "type": "create_wells:fluid_extraction",
    "condition": {
        "direction": "UPSIDE_DOWN",
        "biome": [  ],
        "dimension": [ "minecraft:the_nether" ],
        "yMin": -255,
        "yMax": -255
    },
    "output": {
        "fluid": "minecraft:lava",
        "amount": 100
    }
});
```
