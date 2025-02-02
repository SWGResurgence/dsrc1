package script.theme_park.emperors_retreat;

import script.*;
import script.library.*;

import java.util.Vector;

public class emperors_hand extends script.base_script
{
    public static final String VOLUME_NAME = "aggressive_area";
    public static final string_id FOUND_JEDI = new string_id("restuss_event/object", "jedi_located");
    public int OnAttach(obj_id self) throws InterruptedException
    {
        createTriggerVolume(VOLUME_NAME, 15.0f, true);
        messageTo(self, "setLoiter", null, 10.0f, false);
		sendSystemMessageGalaxyTestingOnly("ATTENTION IMPERIAL CIVILIANS: The Emperor's Hand has been located at the Emperor's Retreat.");
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        createTriggerVolume(VOLUME_NAME, 15.0f, true);
        messageTo(self, "setLoiter", null, 10.0f, false);
		sendSystemMessageGalaxyTestingOnly("ATTENTION IMPERIAL CIVILIANS: The Emperor's Hand has been located at the Emperor's Retreat.");
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
		sendSystemMessageGalaxyTestingOnly("ATTENTION IMPERIAL CIVILIANS: The Emperor's Hand has been slain by " + getName(killer));
        return SCRIPT_CONTINUE;
    }
    public int OnTriggerVolumeEntered(obj_id self, String volumeName, obj_id breacher) throws InterruptedException
    {
        prose_package pp = prose.getPackage(FOUND_JEDI);
        pp.target.set(breacher);
        if (utils.isProfession(breacher, utils.FORCE_SENSITIVE))
        {
            if (!ai_lib.isInCombat(self))
            {
                chat.chat(self, breacher, pp);
                addHate(self, breacher, 1000.0f);
                startCombat(self, breacher);
                return SCRIPT_CONTINUE;
            }
            else 
            {
                addHate(self, breacher, 500.0f);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int setLoiter(obj_id self, dictionary params) throws InterruptedException
    {
        ai_lib.setDefaultCalmBehavior(self, ai_lib.BEHAVIOR_LOITER);
        ai_lib.setLoiterRanges(self, 0.0f, 80.0f);
        return SCRIPT_CONTINUE;
    }
    /* public int cleanupCorpse(obj_id self, dictionary params) throws InterruptedException
    {
        trial.cleanupNpc(self);
        return SCRIPT_CONTINUE;
    } */
}
