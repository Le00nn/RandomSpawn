# Example config

#### A config.yml may look something like the following:

    location:
        a:
            x: -500
            z: -500
        b:
            x: 500
            z: 500
    config:
        version: 1003
    settings:
        spawns:
            allowWater: false
    spawns:
        TestPlayer: -361.7,65.0,442.3
        Le00nn: 153.7,74.0,-178.3


## Parameters

### Location

`location` - Used to determine the range of spawns.

`location.a` - Contains corner a of your spawn (x and z position).

`location.b` - Contains corner b of your spawn (x and z position).

`location.*.x` - The x position of corner a or b.

`location.*.z` - The z position of corner a or b.

### Config

`config` Contains configuration information.

`config.version` - The current configuration version (1003).

### Spawns

`spawns` Contains a list of all players that have previously spawned in.

`spawns.*` * represents a player and it will contains their spawn coords set by this plugin.
