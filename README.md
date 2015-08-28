## CustomWhitelist
CustomWhitelist allows modification to the whitelist even when Mojang UUID queries are rejected

### Commands
**/customwhitelist** `<add/remove>`  
Displays Custom Whitelist help

**/cw** `<add/remove>`  
Same as /customwhitelist

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

### Binaries
All binaries require Spigot 1.8.7+ [Direct download](https://dl.dropboxusercontent.com/u/49422983/AirshipPirates/Plugins/libs/spigot-1.8.7.jar)  
Dev binaries aren't available.  
Version v0.1-RELEASE [Direct download](https://dl.dropboxusercontent.com/u/49422983/AirshipPirates/Plugins/CustomWhitelist_v0.1-RELEASE.jar)  
Version v0.2-RELEASE [Direct download](https://dl.dropboxusercontent.com/u/49422983/AirshipPirates/Plugins/CustomWhitelist_v0.2-RELEASE.jar)  
Version v0.3-RELEASE [Direct download](https://dl.dropboxusercontent.com/u/49422983/AirshipPirates/Plugins/CustomWhitelist_v0.3-RELEASE.jar)  
Version v0.4-RELEASE [Direct download](https://dl.dropboxusercontent.com/u/49422983/AirshipPirates/Plugins/CustomWhitelist_v0.4-RELEASE.jar)  
**Version v0.5-RELEASE** [Direct download](https://dl.dropboxusercontent.com/u/49422983/AirshipPirates/Plugins/CustomWhitelist_v0.5-RELEASE.jar)

### Things left to do before v1.0
* Make commands async  
* Add reload command  
* Add options to commands for advanced functions  
* Add capability to process multiple users at a time  
* Make code more resilient to unexpected exceptions
