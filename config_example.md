# Example config

A config.yml may look something like the following:

    location:
        a:
            x: -500
            z: -500
        b:
            x: 500
            z: 500
        hastp:
            Le00nn: true
    config:
        version: 1002
    spawns:
        Le00nn: -361.0,65.0,442.0


`location` Used to determine the range of spawns.
**a** and **b** are two have one corner of x and z position and another corner for another x and z position. Spawns occur inside the square of twose two points.

`hastp` Contains a list of players with a value true.
Used to prevent players from resetting their spawns on every join, if a player is removed from the list their spawn will reset.

`config` Contains the version key.
The current config version, as of 1.2.x it is 1002.

`spawns` Contains a list of all players that have previously spawned in.
The keys for each player is mapped to their spawnpoints.
