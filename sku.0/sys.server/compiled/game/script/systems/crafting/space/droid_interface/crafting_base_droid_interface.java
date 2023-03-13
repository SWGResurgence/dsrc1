package script.systems.crafting.space.droid_interface;

import script.dictionary;
import script.draft_schematic;
import script.library.craftinglib;
import script.library.space_crafting;
import script.obj_id;

public class crafting_base_droid_interface extends script.systems.crafting.crafting_base
{
    public crafting_base_droid_interface()
    {
    }

    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes, dictionary craftingValuesDictionary) throws InterruptedException
    {
        for (draft_schematic.attribute itemAttribute : itemAttributes)
        {
            if (itemAttribute == null)
            {
                continue;
            }
            if (((itemAttribute.name).getAsciiId()).equals("mass") || ((itemAttribute.name).getAsciiId()).equals("energy_maintenance") || ((itemAttribute.name).getAsciiId()).equals("droid_command_speed"))
            {
                itemAttribute.currentValue = (itemAttribute.minValue + itemAttribute.maxValue) - itemAttribute.currentValue;
            }
        }
        super.calcAndSetPrototypeProperties(prototype, itemAttributes, craftingValuesDictionary);
    }

    public void calcAndSetPrototypeProperties(obj_id prototype, draft_schematic.attribute[] itemAttributes) throws InterruptedException
    {
        debugServerConsoleMsg(null, "Beginning assembly-phase prototype property setting");
        for (draft_schematic.attribute itemAttribute : itemAttributes)
        {
            if (itemAttribute == null)
            {
                continue;
            }
            if (!calcAndSetPrototypeProperty(prototype, itemAttribute))
            {
                if (((itemAttribute.name).getAsciiId()).equals("hitPointsMax"))
                {
                    space_crafting.setComponentMaximumHitpoints(prototype, itemAttribute.currentValue);
                    space_crafting.setComponentCurrentHitpoints(prototype, itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("efficiency"))
                {
                    space_crafting.setComponentGeneralEfficiency(prototype, itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("mass"))
                {
                    space_crafting.setComponentMass(prototype, itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("armorHpMax"))
                {
                    space_crafting.setComponentMaximumArmorHitpoints(prototype, itemAttribute.currentValue);
                    space_crafting.setComponentCurrentArmorHitpoints(prototype, itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("energy_efficiency"))
                {
                    space_crafting.setComponentEnergyEfficiency(prototype, itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("energy_maintenance"))
                {
                    space_crafting.setComponentEnergyMaintenance(prototype, itemAttribute.currentValue);
                }
                if (((itemAttribute.name).getAsciiId()).equals("droid_command_speed"))
                {
                    space_crafting.setDroidInterfaceCommandSpeed(prototype, itemAttribute.currentValue);
                }
                else
                {
                    setObjVar(prototype, craftinglib.COMPONENT_ATTRIBUTE_OBJVAR_NAME + "." + (itemAttribute.name).getAsciiId(), itemAttribute.currentValue);
                }
            }
        }
    }
}
