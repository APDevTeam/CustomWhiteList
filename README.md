## CustomWhiteList
CWL allows modification to the white list even when vanilla uuid/username lookups are rejected.  
All lookups are async so the world will not stop. You may add/remove/check one or more users at a time.

### Commands
**/customwhitelist** `<add/remove/check/list/on/off/reload>`  
Displays CWL help  
Aliases: cwl

**/customwhitelist add** `<username/uuid ...>`  
Adds users to the white list  
You may specify one or more usernames and/or UUIDs  
Permission: `customwhitelist.add`

**/customwhitelist remove** `<username/uuid ...>`  
Removes users from the white list  
You may specify one or more usernames and/or UUIDs  
Permission: `customwhitelist.remove`

**/customwhitelist check** `<username/uuid ...>` [-r]  
Checks if users are on the white list  
You may specify one or more usernames and/or UUIDs  
You may use option -r to resolve UUIDs to usernames   
Permission: `customwhitelist.check`

**/customwhitelist list** [-r]  
Lists all players on the white list, you may choose option -r to resolve UUIDs to usernames  
Permission: `customwhitelist.list`

**/customwhitelist on**  
Turns on the white list  
Permission: `customwhitelist.on`

**/customwhitelist off**  
Turns off the white list  
Permission: `customwhitelist.off`

**/customwhitelist reload**  
Reloads the white list  
Permission: `customwhitelist.reload`  
Note: Remember that you should never have to reload the white list unless the file was changed outside this plugin

### Binaries
Requirements:  
Spigot 1.8.7+ [Get Spigot](https://www.spigotmc.org/wiki/spigot/)  
Java 7 [Get Java](https://www.java.com/en/)  
For v1.1 and before: Minecraft Server WhiteListEntry class patch -- See source [Direct download Spigot 1.8.7 with WLE patch](https://dl.dropboxusercontent.com/u/49422983/AirshipPirates/Plugins/libs/spigot-1.8.7-WLEpatch.jar)  
See [Releases](https://github.com/WhiteWolfdoge/CustomWhiteList/releases) for plugin binaries

### License
This plugin and its source are licensed under the [GNU GPLv3](https://gnu.org/licenses/gpl-3.0-standalone.html) license.
