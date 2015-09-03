## CustomWhitelist
CustomWhitelist allows modification to the whitelist even when Mojang UUID queries are rejected.  
Username lookups are async so the world will not stop.

### Commands
**/customwhitelist** `<add/remove>`  
Displays Custom Whitelist help  
Aliases: cw

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
Spigot 1.8.7+ [Get Spigot](https://www.spigotmc.org/wiki/spigot/) [Direct download](https://dl.dropboxusercontent.com/u/49422983/AirshipPirates/Plugins/libs/spigot-1.8.7.jar)  
Java 7 [Get Java](https://www.java.com/en/)

Dev binaries aren't available.  
Older binaries may not be available  
v0.5-release-beta [Direct download](https://github.com/AP-Programmers/CustomWhitelist/releases/download/v0.5-release-beta/CustomWhitelist_v0.5-RELEASE.jar)  
v0.6-release-beta [Direct download](https://github.com/AP-Programmers/CustomWhitelist/releases/download/v0.6-release-beta/CustomWhitelist_v0.6-RELEASE.jar)  
v0.7-release-beta [Direct download](https://github.com/AP-Programmers/CustomWhitelist/releases/download/v0.7-release-beta/CustomWhitelist_v0.7-release-beta.jar)  
v0.8-release-beta [Direct download](https://github.com/AP-Programmers/CustomWhitelist/releases/download/v0.8-release-beta/CustomWhitelist_v0.8-release-beta.jar)  
**v1.0-release** [Direct download](https://github.com/AP-Programmers/CustomWhitelist/releases/download/v1.0-release/CustomWhitelist_v1.0-release.jar)
