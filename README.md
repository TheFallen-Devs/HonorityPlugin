# HonorityPlugin
Minecraft Bukkit plugin which adds one simple value, "honority"(same as honor) system in server.

## Commands
/honority show (playername) : Shows honority value of given player.\n
/honority set (playername) (value) : set player's honority value into given value.\n
/honority reset (playername) : set player's honority value into initial value(normally, 0). this is an alias of /honority (playername) set 0\n
/honority add (playername) (value) : add given value into player's honority value. you can add negative number, too. only integer can be used on value.\n
/honority help (command) : show description about given command. you can put add, set, reset or add in (command).\n

Command usage video is [here](https://www.youtube.com/watch?v=t0xNXX55Bhg)

## Skript Support
Based on Skript 2.4, this plugin also works as Skript Addon. If Skript is in your server, Skript Addon feature will be enabled.
Skript can access to honority value and get, modify it.
