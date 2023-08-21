# RandomSpawn

### What is this?
Simple Project Poseidon Uberbukkit plugin to set a random spawn point in an X and Z position based range in a config file.
It spawns on the top most block availble at the randomly selected location.

### Project Poseidon Uberbukkit source
https://github.com/Moresteck/Project-Poseidon-Uberbukkit Here you can see how to go about building Project Poseidon Uberbukkit from source.

### Version support
This plugin has been tested and works with alpha 1.2.x using Project Poseidon Uberbukkit.

### Releases
- 1.0.0 ~ Randomly spawns players
- 1.1.0 ~ Ability to reload configuration (config.yml) via a command (see below)
- 1.2.0 [BROKEN] ~ Sets spawn range in the configuration file (config.yml) via a command (see below)
- 1.2.1 ~ Changed + fixed command added in 1.2.0
- 1.2.2 ~ Fixed respawns upon death not being the one you got when joining server.

### Commands
All commands requires the player to be a server operator.

- **/rs reload** ~ Reload the config.yml file with any changes you've made. [Added in 1.1.0]
- **/rs set** ~ Sets spawn points in the config.yml file. [Added in 1.2.0, fixed in 1.2.1]
  
#### Usage example:

/rs set x -100 100

/rs set z -100 100

This example will set the range of spawning between x -100 and 100 and z -100 and 100.

### Features and future plans
- [x] Teleporting to random location
- [x] Simple config file to use
- [x] Reloading the config file via command [Added in 1.1.0]
- [x] Commands to configure the spawn area [Added in 1.2.0, fixed in 1.2.1]
- [ ] Blacklist/Whitelist of what blocks you can spawn or not spawn on
