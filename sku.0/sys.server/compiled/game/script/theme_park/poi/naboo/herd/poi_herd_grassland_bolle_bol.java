package script.theme_park.poi.naboo.herd;

import script.dictionary;
import script.location;
import script.obj_id;

public class poi_herd_grassland_bolle_bol extends script.theme_park.poi.base
{

    public int OnAttach(obj_id self) throws InterruptedException
    {
        poiCreateObject("object/creature/monster/bolle_bol/bolle_bol_grassland_large.iff", 8, 8);
        setObjVar(self, "spawned", 1);
        int x = rand(7, 11);
        setObjVar(self, "max", x);
        messageTo(self, "spawnCreatures", null, 4, true);
        return SCRIPT_CONTINUE;
    }
    public String getBolle(obj_id self) throws InterruptedException
    {
        String bolle_bolSpawn = "object/creature/monster/bolle_bol/bolle_bol.iff";
        int bolle_bolType = rand(1, 2);
        switch (bolle_bolType)
        {
            case 1:
            bolle_bolSpawn = "object/creature/monster/bolle_bol/bolle_bol_grassland_medium.iff";
            break;
            case 2:
            bolle_bolSpawn = "object/creature/monster/bolle_bol/bolle_bol_grassland_small.iff";
            break;
        }
        return bolle_bolSpawn;
    }
    public int spawnCreatures(obj_id self, dictionary params) throws InterruptedException
    {
        location here = new location(getLocation(self));
        int i = getIntObjVar(self, "spawned");
        int max = getIntObjVar(self, "max");
        if (i <= max)
        {
            here.x = rand(-10, 10);
            here.y = rand(-10, 10);
            poiCreateObject(getBolle(self), here.x, here.y);
            i = i + 1;
            setObjVar(self, "spawned", i);
            messageTo(self, "spawnCreatures", null, 3, true);
        }
        return SCRIPT_CONTINUE;
    }
}
