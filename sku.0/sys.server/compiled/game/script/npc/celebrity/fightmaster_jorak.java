package script.npc.celebrity;

/*
 * Copyright © SWG:Resurgence 2023.
 *
 * Unauthorized usage, viewing or sharing of this file is prohibited.
 */

import script.library.ai_lib;
import script.obj_id;

public class fightmaster_jorak extends script.base_script
{
    public static final String CONVO = "celebrity/fightmaster_jorak";

    public fightmaster_jorak()
    {
    }

    public int OnAttach(obj_id self) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_SENTINEL);
        detachScript(self, "npc.converse.npc_converse_menu");
        setInvulnerable(self, true);
        return SCRIPT_CONTINUE;
    }
}
