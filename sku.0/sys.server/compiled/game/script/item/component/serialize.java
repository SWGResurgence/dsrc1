package script.item.component;

/*
 * Copyright © SWG:Resurgence 2023.
 *
 * Unauthorized usage, viewing or sharing of this file is prohibited.
 */

import script.obj_id;

public class serialize extends script.base_script
{
    public serialize()
    {
    }

    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id serial = getTopMostContainer(self);
        if (!isIdValid(serial))
        {
            long id = 192837465;
            serial = getObjIdWithNull(id);
        }
        setCraftedId(self, serial);
        setCrafter(self, serial);
        detachScript(self, "item.component.serialize");
        return SCRIPT_CONTINUE;
    }
}
