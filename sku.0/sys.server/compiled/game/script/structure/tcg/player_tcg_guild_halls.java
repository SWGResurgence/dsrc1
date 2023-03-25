package script.structure.tcg;

import script.base_script;
import script.library.buff;
import script.obj_id;

public class player_tcg_guild_halls extends base_script {
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException {
        buff.applyBuff(item, "tcg_guild_halls_buff");
    return SCRIPT_CONTINUE;
    }
}
