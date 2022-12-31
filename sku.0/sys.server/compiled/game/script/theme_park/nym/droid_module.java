package script.theme_park.nym;

import script.*;
import script.library.groundquests;

public class droid_module extends script.base_script
{

    public static final String STF_FILE = "pet/droid_modules";
    public static final string_id RETRIEVE = new string_id("celebrity/jinkins", "retrieve");
    public int makeNewModule(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id module = createObject("object/tangible/loot/quest/nym_droid_memory_chip.iff", self, "");
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info item) throws InterruptedException
    {
        item.addRootMenu(menu_info_types.ITEM_USE, RETRIEVE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        location here = getLocation(player);
        location term = getLocation(self);
        float dist = getDistance(here, term);
        if (isDead(player) || isIncapacitated(player) || dist > 6.0)
        {
            return SCRIPT_CONTINUE;
        }
        if (item != menu_info_types.ITEM_USE)
        {
            return SCRIPT_CONTINUE;
        }
        if (!groundquests.isTaskActive(player, "u16_nym_themepark_pirate_hideout", "findDroidDisk"))
        {
            sendSystemMessage(player, new string_id("quest/groundquests", "retrieve_item_no_interest"));
            return SCRIPT_CONTINUE;
        }
        groundquests.sendSignal(player, "hasFoundDroidDisk");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (hasScript(self, "item.container.base.base_container"))
        {
            detachScript(self, "item.container.base.base_container");
        }
        return SCRIPT_CONTINUE;
    }
}
