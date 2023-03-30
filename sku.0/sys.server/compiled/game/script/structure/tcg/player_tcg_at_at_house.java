package script.structure.tcg;

import script.base_script;
import script.library.buff;
import script.library.utils;
import script.obj_id;

public class player_tcg_at_at_house extends base_script {
    public int OnReceivedItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException {
        if (isPlayer(item) && utils.isProfession(item, utils.TRADER)) {
            buff.applyBuff(item, "tcg_at_at_house_1_buff");
            buff.applyBuff(item, "tcg_at_at_house_2_buff");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnAboutToLoseItem(obj_id self, obj_id srcContainer, obj_id transferer, obj_id item) throws InterruptedException {
        if (isPlayer(item) && buff.hasBuff(item, "tcg_at_at_house_1_buff")) {
            buff.removeBuff(item, "tcg_at_at_house_1_buff");
        }
        if (isPlayer(item) && buff.hasBuff(item, "tcg_at_at_house_2_buff")) {
            buff.removeBuff(item, "tcg_at_at_house_2_buff");
        }
        return SCRIPT_CONTINUE;
    }
}
