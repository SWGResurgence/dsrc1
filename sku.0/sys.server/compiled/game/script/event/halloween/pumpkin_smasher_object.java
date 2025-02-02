/*
@Purpose: Pumpkin Object for Galactic Moon Festival

@Author: BubbaJoe

@TODO: Add naysayer and collection reconcilation
 */
package script.event.halloween;

import script.library.*;
import script.menu_info;
import script.menu_info_types;
import script.obj_id;
import script.string_id;

public class pumpkin_smasher_object extends script.base_script
{
    public pumpkin_smasher_object()
    {
    }
    private static final String HALLOWEEN = "event/halloween";
    public static final string_id SID_USE = new string_id(HALLOWEEN, "smash_pumpkin");
    public static final String COLLECTION_NAME = "col_halloween_pumpkin_smasher";
    public static final String PULPED_ITER_OBJVAR = "halloween.pulped";
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        mi.addRootMenu(menu_info_types.ITEM_USE, SID_USE);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (item == menu_info_types.ITEM_USE)
        {
            int baseUse = 0;
            if (!hasObjVar(player, PULPED_ITER_OBJVAR))
            {
                setObjVar(player, PULPED_ITER_OBJVAR, baseUse);
            }
            if (!buff.hasBuff(player, "event_halloween_pumpkin_puree"))
            {
                buff.applyBuff(player, "event_halloween_pumpkin_puree");
            }
            int coinChance = rand(1,100);
            if (coinChance < 50 ) {
                broadcast(player, "You find some strange objects inside this pumpkin.");
                static_item.createNewItemFunction("item_event_halloween_coin", utils.getInventoryContainer(player), 15);
            }
            int mobChance = rand(1,100);
            if (mobChance <= 25 ) {
                broadcast(player, "An angry worm seems to have been living inside this pumpkin!");
                obj_id wormy = create.createCreature("angry_forage_worm", getLocation(player), true);
                setName(wormy, "an uprooted worm");
                setObjVar(wormy, "loot.lootTable", "treasure/treasure_81_90");
                setObjVar(wormy, "loot.numItems", 5);
                setScale(wormy, 1.5f);
                showFlyText(wormy, new string_id("*- SLIME -*"), 12.0f, colors.GREEN);
            }
            playClientEffectLoc(player, "clienteffect/egg_hatch_01.cef", getLocation(player), 0.0f);
            int currentSmashed = getIntObjVar(player, PULPED_ITER_OBJVAR);
            setObjVar(player, PULPED_ITER_OBJVAR, currentSmashed + 1);
            if (currentSmashed >= 100)
            {
                broadcast(player, "You have smashed " + currentSmashed + " pumpkins.");
                modifyCollectionSlotValue(player, COLLECTION_NAME, 1);
            }
            hideFromClient(self, true);
            destroyObject(self);
        }
        return SCRIPT_CONTINUE;
    }
}
