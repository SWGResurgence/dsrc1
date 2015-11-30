package script.library;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.utils;

public class trainerlocs extends script.base_script
{
    public trainerlocs()
    {
    }
    public static obj_id getTrehlaKeeloLocation(obj_id player, String profession, String track) throws InterruptedException
    {
        location targetLoc = new location();
        targetLoc.area = track;
        String waypName = "Trehla Keelo";
        targetLoc.x = 3484f;
        targetLoc.y = 0f;
        targetLoc.z = -4808f;
        obj_id wayp = createWaypointInDatapad(player, targetLoc);
        String playerArea = getLocation(player).area;
        if (playerArea != null && playerArea.equals(targetLoc.area))
        {
            setWaypointActive(wayp, true);
        }
        setWaypointName(wayp, waypName);
        return wayp;
    }
    public static obj_id getTrainerLocationWaypoint(obj_id player, String profession, String track) throws InterruptedException
    {
        location targetLoc = new location();
        targetLoc.area = track;
        String waypName = "error";
        if (track.equals("naboo"))
        {
            if (profession.equals("imperial"))
            {
                targetLoc.x = 5182f;
                targetLoc.y = -192f;
                targetLoc.z = 6750f;
                waypName = "barn_sinkko";
            }
            else if (profession.equals("rebel"))
            {
                targetLoc.x = 4767f;
                targetLoc.y = 4.22f;
                targetLoc.z = -4812f;
                waypName = "v3_fx";
            }
            else 
            {
                targetLoc.x = -5495f;
                targetLoc.y = 14.00f;
                targetLoc.z = 4476f;
                waypName = "dinge";
            }
        }
        else if (track.equals("corellia"))
        {
            if (profession.equals("imperial"))
            {
                targetLoc.x = -2184f;
                targetLoc.y = 20.0f;
                targetLoc.z = 2273f;
                targetLoc.area = "talus";
                waypName = "hakassha_sireen";
            }
            else if (profession.equals("rebel"))
            {
                targetLoc.x = -5170f;
                targetLoc.y = 21.00f;
                targetLoc.z = -2295f;
                waypName = "kreezo";
            }
            else 
            {
                targetLoc.x = -275f;
                targetLoc.y = 28f;
                targetLoc.z = -4695f;
                waypName = "rhea";
            }
        }
        else 
        {
            if (profession.equals("imperial"))
            {
                targetLoc.x = -1132f;
                targetLoc.y = 13.32f;
                targetLoc.z = -3542f;
                waypName = "akal_colzet";
            }
            else if (profession.equals("rebel"))
            {
                targetLoc.x = -2991f;
                targetLoc.y = 5f;
                targetLoc.z = 2123f;
                waypName = "da_la_socuna";
            }
            else 
            {
                targetLoc.x = 3381f;
                targetLoc.y = 5f;
                targetLoc.z = -4799f;
                waypName = "dravis";
            }
        }
        obj_id wayp = createWaypointInDatapad(player, targetLoc);
        String playerArea = getLocation(player).area;
        if (playerArea != null && playerArea.equals(targetLoc.area))
        {
            setWaypointActive(wayp, true);
        }
        setWaypointName(wayp, utils.packStringId(new string_id("npc_spawner_n", waypName)));
        return wayp;
    }
}
