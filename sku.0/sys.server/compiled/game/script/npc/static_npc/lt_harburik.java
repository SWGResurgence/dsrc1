package script.npc.static_npc;

import script.library.ai_lib;
import script.obj_id;

public class lt_harburik extends script.base_script
{

    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        setName(self, "Lt. Harburik");
        return SCRIPT_CONTINUE;
    }
}
