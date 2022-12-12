package script.item;

import script.library.*;
import script.*;

import java.util.HashSet;

public class token_gift_box extends script.base_script {
    public static final String STF_FILE = "token_gift_box";
    public static obj_id[] grantTokenGift(obj_id player) throws InterruptedException {
        obj_id pInv = utils.getInventoryContainer(player);
        HashSet theSet = new HashSet();

        theSet.add(static_item.createNewItemFunction("item_event_halloween_coin", pInv, 2000));
        obj_id[] items = new obj_id[theSet.size()];
        theSet.toArray(items);
        showLootBox(player, items);
        return items;
    }
    
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException {
        int mnu2 = mi.addRootMenu(menu_info_types.ITEM_USE, new string_id(STF_FILE, "claim_token_gift"));
        return SCRIPT_CONTINUE;
    }
    
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException {
        if (item == menu_info_types.ITEM_USE) {
            sendSystemMessage(player, new string_id(STF_FILE, "opened_box"));
            obj_id[] allTheArmor = grantTokenGift(player);
            destroyObject(self);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
}
