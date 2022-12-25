package script.developer.soe.test;

import script.obj_id;

public class report_posture extends script.base_script
{

    public int OnHearSpeech(obj_id self, obj_id speaker, String text) throws InterruptedException
    {
        if (text.equals("report"))
        {
            debugSpeakMsg(self, "Posture is " + Integer.toString(getPosture(self)) + "Locomotion is " + Integer.toString(getLocomotion(self)));
        }
        return SCRIPT_CONTINUE;
    }
}
