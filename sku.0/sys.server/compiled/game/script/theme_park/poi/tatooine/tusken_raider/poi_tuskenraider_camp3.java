package script.theme_park.poi.tatooine.tusken_raider;

import script.library.ai_lib;
import script.location;
import script.obj_id;

public class poi_tuskenraider_camp3 extends script.theme_park.poi.base
{

    public int OnAttach(obj_id self) throws InterruptedException
    {
        String objective = poiGetObjective(self);
        if (objective.equals("tuskens"))
        {
            obj_id tr1 = poiCreateNpc("tusken", -1, 2);
            obj_id tr2 = poiCreateNpc("tusken", -1, 3);
            obj_id tr3 = poiCreateNpc("tusken", 1, 3);
            String[] patrolPoints = new String[4];
            patrolPoints[0] = "-10, 10";
            patrolPoints[1] = "-10, -10.3";
            patrolPoints[2] = "10, 10";
            patrolPoints[3] = "10, -10";
            setPatrolPath(tr1, patrolPoints);
            String[] patrolPoints2 = new String[4];
            patrolPoints2[0] = "15, 15";
            patrolPoints2[1] = "15, -15.3";
            patrolPoints2[2] = "-15, -15";
            patrolPoints2[3] = "-15, 15";
            setPatrolPath(tr2, patrolPoints2);
            attachScript(tr3, "theme_park.poi.tatooine.behavior.tusken_stationary_poi");
            location here = getLocation(self);
            obj_id fire = poiCreateObject(self, "campfire", "object/static/particle/particle_campfire_style_1.iff", 1, 2);
            obj_id center = poiCreateObject(self, "center", "object/tangible/camp/campfire_logs_burnt.iff", 1, 2);
            return SCRIPT_CONTINUE;
        }
        return SCRIPT_CONTINUE;
    }
    public void setPatrolPath(obj_id npc, String[] patrolPoints) throws InterruptedException
    {
        ai_lib.setPatrolPath(npc, patrolPoints);
        return;
    }
}
