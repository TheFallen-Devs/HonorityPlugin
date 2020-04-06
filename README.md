# HonorityPlugin
Minecraft Bukkit plugin which adds one simple value, "honority"(same as honor) system in server.

## Commands
/honority (playername) show : Shows honority value of given player. [Only Player Can Use This Command]
/honority (playername) set (value) : set player's honority value into given value.
/honority (playername) reset : set player's honority value into initial value(normally, 0). this is an alias of /honority (playername) set 0
/honority (playername) add (value) : add given value into player's honority value. you can add negative number, too. only integer can be used on value.
/honority help (command) : show description about given command. you can put add, set, reset or add in (command).

## Skript Support
Based on Skript 2.4, this plugin also works as Skript Addon. If Skript is in your server, Skript Addon feature will be enabled.
Skript can access to honority value and get, modify it.
