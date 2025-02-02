package script.theme_park.dungeon.geonosian_madbio_bunker;

import script.dictionary;
import script.library.ai_lib;
import script.library.static_item;
import script.library.utils;
import script.obj_id;

public class loot_controller_mutant_acklay extends script.base_script
{
    public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null)
        {
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(self))
        {
            return SCRIPT_CONTINUE;
        }
        createMyLoot(self);
        return SCRIPT_CONTINUE;
    }
    public void createMyLoot(obj_id self) throws InterruptedException
    {
        obj_id corpseInventory = utils.getInventoryContainer(self);
        if (corpseInventory == null)
        {
            return;
        }
        String mobType = ai_lib.getCreatureName(self);
        if (mobType == null)
        {
            return;
        }
        int x = rand(1, 100);
        if (x < 51){  // 50% Drop Chance
            static_item.createNewItemFunction("item_scorpion_kliknik_dna", corpseInventory);
        }
        if (x < 26){  // 25% Drop Chance
            static_item.createNewItemFunction("item_tcg_loot_reward_series4_geonosian_speeder_02_01", corpseInventory);
        }
        if (x < 2){  // 01% Drop Chance
            static_item.createNewItemFunction("item_tcg_loot_reward_series2_barn", corpseInventory);
        }

        /*String myLoot1 = "";
        String myLoot2 = "object/tangible/wearables/cybernetic/s02/cybernetic_s02_arm_r.iff";
        createObject(myLoot1, corpseInventory, "");
        createObject(myLoot2, corpseInventory, "");*/ 
        return;
    }
}
