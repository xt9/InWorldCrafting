# 1.12.2

## 1.0
- Removed the item blacklist, changed approach to crafting so we don't cannibalized custom EntityItem classes. (Might be a bit more expensive on the cpu, so marking this version as a beta)
- Fixed a bug where Fluid to Item recipes that consumes the fluid could craft in flowing fluid.
- Inputs that are not consumed during crafting will now properly update it's shrunken state.
- Removed config as the Blacklist is no longer a thing, the config file can be removed without breaking anything.

## 0.13
- Added NBT matching for ingredients

## 0.12
- Fixed a crash for OpenJDK users.

## 0.11
- Added Input blacklist for items from other mods that require their own EntityItem logic.
- Added config option to disable said blacklist.

## 0.10
- Merged custom entities so that one specific input can do multiple types of crafting instead of picking the first matching recipe.
- Added Fire Crafting.
- Added Explosion Crafting.
- Refactored project to reflect the new Name.
- Will break current scripts. Change imports to mods.inworldcrafting instead of mods.fluidtransformtweaker