## CustomWhitelist
CustomWhitelist allows modification to the whitelist even when Mojang UUID queries are rejected.  
Username lookups are async so the world will not stop.

### Commands
**/customwhitelist** `<add/remove>`  
Displays Custom Whitelist help  
Aliases: cw, cwl

**/customwhitelist add** `<username/uuid ...>`  
Adds `<username/uuid ...>` to the whitelist, you may specify multiple usernames or UUIDs or a mix  
Permission: `customwhitelist.add`

**/customwhitelist remove** `<username/uuid ...>`  
Removes `<username/uuid ...>` from the whitelist, you may specify multiple usernames or UUIDs or a mix  
Permission: `customwhitelist.remove`

**/customwhitelist check** `<username/uuid ...>` [-r]  
Checks if `<username/uuid ...>` is on the whitelist, you may specify multiple usernames or UUIDs or a mix, you may choose option -r to resolve UUIDs to usernames   
Permission: `customwhitelist.check`

**/customwhitelist list** [-r]  
Lists all players on the whitelist, you may choose option -r to resolve UUIDs to usernames  
Permission: `customwhitelist.list`

**/customwhitelist on**  
Turns on the whitelist  
Permission: `customwhitelist.on`

**/customwhitelist off**  
Turns off the whitelist  
Permission: `customwhitelist.off`

**/customwhitelist reload**  
Reloads the whitelist  
Permission: `customwhitelist.reload`  
Note: Remember that you should never have to reload the whitelist unless the file was changed outside this plugin

### Binaries
Requirements:  
Spigot 1.8.7+ [Get Spigot](https://www.spigotmc.org/wiki/spigot/)  
Java 7 [Get Java](https://www.java.com/en/)  
Minecraft Server WhiteListEntry class patch -- See source [Direct download Spigot 1.8.7 with WLE patch](https://dl.dropboxusercontent.com/u/49422983/AirshipPirates/Plugins/libs/spigot-1.8.7-WLEpatch.jar)  
See [Releases](https://github.com/AP-Programmers/CustomWhitelist/releases) for plugin binaries

### License
This plugin and its source are licensed under the [GNU GPLv3](https://gnu.org/licenses/gpl-3.0-standalone.html) license.
