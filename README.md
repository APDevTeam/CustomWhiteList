## CustomWhitelist
CustomWhitelist allows modification to the whitelist even when Mojang UUID queries are rejected.

### Commands
**/customwhitelist** `<add/remove>`  
Displays Custom Whitelist help  
Aliases: cw

**/customwhitelist add** `<player>`  
Adds `<player>` to the whitelist  
Permission: `customwhitelist.add`

**/customwhitelist remove** `<player>`  
Removes `<player>` from the whitelist  
Permission: `customwhitelist.remove`

**/customwhitelist check** `<player>`  
Checks if `<player>` is on the whitelist  
Permission: `customwhitelist.check`

**/customwhitelist list**  
Lists all players on the whitelist  
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
Spigot 1.8.7+ [Direct download](https://dl.dropboxusercontent.com/u/49422983/AirshipPirates/Plugins/libs/spigot-1.8.7.jar)  
Java 7 [Get Java](https://www.java.com/en/)

Dev binaries aren't available.  
Older binaries may not be available  
Version v0.5-RELEASE [Direct download](https://github.com/AP-Programmers/CustomWhitelist/releases/download/v0.5-release-beta/CustomWhitelist_v0.5-RELEASE.jar)  
**Version v0.6-RELEASE** [Direct download](https://github.com/AP-Programmers/CustomWhitelist/releases/download/v0.6-release-beta/CustomWhitelist_v0.6-RELEASE.jar)

### Things left to do before v1.0
* Make commands async
* Add capability to process multiple users at a time
* Add more sources
