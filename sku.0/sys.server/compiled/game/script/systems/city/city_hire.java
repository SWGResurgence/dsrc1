package script.systems.city;/*
@Filename: script.systems.city.city_hire
@Author: BubbaJoeX
@Purpose: Dragging (OnGiveItem) onto a mobile will make a token that spawns the npc in the city.
*/

import script.library.create;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class city_hire extends script.base_script
{
    public static String TOOL = "object/tangible/loot/tool/datapad_broken.iff";

    public int OnAttach(obj_id self)
    {
        return SCRIPT_CONTINUE;
    }

    public int OnInitialize(obj_id self)
    {
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (hasObjVar(self, "tokenUsed"))
        {
            mi.addRootMenu(menu_info_types.SERVER_MENU50, new string_id("Place Actor"));
        }
        else
        {
            broadcast(player, "You must drag this token onto a mobile to hire it.");
        }
        return SCRIPT_CONTINUE;
    }

    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (isInWorldCell(player))
        {
            if (item == menu_info_types.SERVER_MENU50)
            {
                if (hasObjVar(self, "tokenUsed"))
                {
                    if (hasObjVar(self, "city_hire.mobile"))
                    {
                        obj_id actor = create.createObject(getStringObjVar(self, "city_hire.mobile"), getLocation(player));
                        attachScript(actor, "systems.city.city_actor");
                        broadcast(player, "Hired:  \"" + getName(actor) + "\"");
                    }
                }
                else
                {
                    broadcast(player, "You must drag this token onto a mobile to hire it.");
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
}

