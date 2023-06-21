package script.quest.ep3;

/*
 * Copyright © SWG:Resurgence 2023.
 *
 * Unauthorized usage, viewing or sharing of this file is prohibited.
 */

import script.library.utils;
import script.obj_id;

public class loot_ep3_clone_relics_jedi_starfighter_spawner extends script.base_script
{
    public loot_ep3_clone_relics_jedi_starfighter_spawner()
    {
    }

    public int OnInitialize(obj_id self) throws InterruptedException
    {
        obj_id oldBox = utils.getObjIdScriptVar(self, "newBox");
        if (isIdValid(oldBox))
        {
            if (exists(oldBox))
            {
                return SCRIPT_CONTINUE;
            }
        }
        obj_id newBox = createObject("object/tangible/quest/quest_start/ep3_clone_relics_jedi_starfighter_container.iff", getLocation(self));
        utils.setScriptVar(self, "newBox", newBox);
        return SCRIPT_CONTINUE;
    }
}
