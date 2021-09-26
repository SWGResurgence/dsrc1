package script.theme_park.restuss_event;

import script.dictionary;
import script.library.ai_lib;
import script.library.static_item;
import script.library.utils;
import script.obj_id;

import java.util.ArrayList;
import java.util.List;

public class emperors_hand_loot extends script.base_script {
	public emperors_hand_loot() {
	}
	public int aiCorpsePrepared(obj_id self, dictionary params) throws InterruptedException {
		obj_id corpseInventory = utils.
		getInventoryContainer(self);
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
		obj_id corpseInventory = utils.
		getInventoryContainer(self);
		if (corpseInventory == null) {
			return;
		}
		String mobType = ai_lib.getCreatureName(self);
		if (mobType == null) {
			return;
		}
		int x = rand(1, 100);
		if (x < 100) { /* 100% Drop Chance */
			static_item.createNewItemFunction("rare_loot_chest_quality_3", corpseInventory);
		}
			if (x < 100) { /* 100% Drop Chance */
			static_item.createNewItemFunction("item_collection_sith_holocron_01_04", corpseInventory);
		}
		/* String myLoot1 = "object/tangible/loot/loot_schematic/generic_limited_use_flashy.iff";
		createObject(myLoot1, corpseInventory, ""); */
		
		obj_id[] handLootedItems = new obj_id[items.size()];
		items.toArray(lootedItems);
		showLootBox(self, lootedItems);
		return;
	}
}
		