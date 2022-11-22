package script.theme_park.world_boss;

import script.dictionary;
import script.library.*;
import script.library.ai_lib;
import script.library.static_item;
import script.library.utils;
import script.obj_id;

public class loot_controller_peko extends script.base_script {
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException {
        if (pet_lib.isPet(killer)) {
			sendSystemMessageGalaxyTestingOnly("ATTENTION GALACTIC BOUNTY HUNTERS: The Abomination, The Mutated Peko-Peko Empress has been reported to have been destroyed and the Czerka Corporation has paid out the bounty to " + getPlayerName(pet_lib.getMaster(killer)));
		}
        return SCRIPT_CONTINUE;
    }
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null) {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(self)) {
            return SCRIPT_CONTINUE;
        }
        createMyLoot(self);
        return SCRIPT_CONTINUE;
    }
    public void createMyLoot(obj_id self) throws InterruptedException {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null) {
            return;
        }
        String mobType = ai_lib.getCreatureName(self);
        if (mobType == null) {
            return;
        }
        int x = rand(1, 100);
        if (x < 91) { // 90% Drop Rate: TCG - Ball of Peace
            static_item.createNewItemFunction("item_tcg_loot_reward_series4_ball_of_peace_02_01", corpseInventory);
        }
        if (x < 26) { // 25% Drop Rate: TCG - Toxic Peko-Peko Mount
            static_item.createNewItemFunction("item_tcg_loot_reward_series4_peko_peko_mount_02_01", corpseInventory);
        }
        /*String myLoot1 = "";
        createObject(myLoot1, corpseInventory, "");
        String myLoot2 = "";
        createObject(myLoot2, corpseInventory, "");*/
        return;
    }
}
