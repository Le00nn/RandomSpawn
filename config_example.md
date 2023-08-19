# Example config

A config.yml may look something like the following:

    location:
        a:
            x: 200
            z: 200
        b:
            x: 1000
            z: 400
        hastp:
            Le00nn: true
        config:
            version: 1002

The config.yml file will be located in the /plugins/RandomSpawn/ directory.
hastp contains all players that initially joined (this is to prevent them from being randomly teleported on every join after their initial join.
location contains the keys a and b with x and z positions each, these can be configured manually, the type of the x and z key values must always be integers or plugin will malfunction.
You can set the locations using the `/rs set` command - see commands.md for details.
