## VoidSpawn - Folia Support

This is in no means a full Folia port, but rather a quick patch to get VoidSpawn working on Folia servers.\
I got no intentions of maintaining this fork unless there's a bug that needs to be fixed or adding removed modes back,
so use at your own risk.\
Some features may not work as intended or at all.\
Some of the modes have been removed due to incompatibility with Folia and me being too lazy to fix/edit those.

This fork also has an optimization that _should_ improve the plugin's performance a lot.\
Instead of checking for void players on move event, it uses a repeating task that checks the player's Y level every
second.\
Task interval is hardcoded because, well, this is intended to be for our private use.

[For original repository, click here.](https://github.com/endercrest/VoidSpawn)