# Commands in-depth

### /rs reload
Reloads the config.yml file

### /rs set
Sets a range of positions (X and Z coordinates) of which you can spawn in.

    /rs set x x1 x2
    This will set the spawn x position to be between x1 and x2 (x1 and x2 must be integers, meaning no float values are allowed).

    /rs set z z1 z2
    This will set the spawn z position to be between z1 and z2 (z1 and z2 must be integers, meaning no float values are allowed).

Examples:

    /rs set z -250 250
    This will spawn the player randomly between z -250 and z 250.

    /rs set x 200 400
    This will spawn the player randomly between x 200 and 400.

**WARNING:** Failure to set the positions x and z correctly may result in error and plugin ignoring putting players at the random spawn.
