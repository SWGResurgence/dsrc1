package script.systems.crafting.armor.component;

/*
 * Copyright © SWG:Resurgence 2023.
 *
 * Unauthorized usage, viewing or sharing of this file is prohibited.
 */

import script.draft_schematic;
import script.library.craftinglib;
import script.obj_id;
import script.resource_weight;

public class crafting_new_armor_tailor_final extends script.systems.crafting.armor.crafting_new_armor_clothing_nostats
{
    public static final String VERSION = "v0.00.00";
    public static final String[] REQUIRED_SKILLS =
            {
                    "crafting_tailor_novice"
            };
    public static final String[] ASSEMBLY_SKILL_MODS =
            {
                    "clothing_assembly"
            };
    public static final String[] EXPERIMENT_SKILL_MODS =
            {
                    "clothing_experimentation"
            };
    public static final String[] CUSTOMIZATION_SKILL_MODS =
            {
                    "clothing_customization"
            };
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES =
            {
                    new resource_weight("condition", new resource_weight.weight[]
                            {
                                    new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
                                    new resource_weight.weight(craftinglib.RESOURCE_DECAY_RESIST, 1),
                                    new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1)
                            })
            };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES =
            {
                    new resource_weight("condition", new resource_weight.weight[]
                            {
                                    new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
                                    new resource_weight.weight(craftinglib.RESOURCE_DECAY_RESIST, 1),
                                    new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1)
                            })
            };

    public crafting_new_armor_tailor_final()
    {
    }

    public String[] getRequiredSkills() throws InterruptedException
    {
        return REQUIRED_SKILLS;
    }

    public String[] getAssemblySkillMods() throws InterruptedException
    {
        return ASSEMBLY_SKILL_MODS;
    }

    public String[] getExperimentSkillMods() throws InterruptedException
    {
        return EXPERIMENT_SKILL_MODS;
    }

    public String[] getCustomizationSkillMods() throws InterruptedException
    {
        return CUSTOMIZATION_SKILL_MODS;
    }

    public resource_weight[] getResourceMaxResourceWeights() throws InterruptedException
    {
        return OBJ_MAX_ATTRIBUTE_RESOURCES;
    }

    public draft_schematic.custom[] getCustomizations(obj_id player, draft_schematic.custom[] customizations) throws InterruptedException
    {
        if (customizations == null || customizations.length == 0)
        {
            return null;
        }
        String[] skills = getCustomizationSkillMods();
        float playerCustomizationMod = 0;
        if (skills != null)
        {
            int[] mods = getEnhancedSkillStatisticModifiers(player, skills);
            for (int mod : mods)
            {
                playerCustomizationMod += mod;
            }
        }
        playerCustomizationMod = 255;
        int max_colors = 4;
        int threshold = 20;
        for (int i = 0; i < customizations.length; ++i)
        {
            if (i >= max_colors)
            {
                customizations[i] = null;
            }
            else if (playerCustomizationMod < threshold)
            {
                customizations[i] = null;
            }
            else if ((customizations[i].name).contains("index_color_0") && customizations[i].maxValue == 1)
            {
                customizations[i] = null;
            }
            else
            {
                int max_maxValue = customizations[i].maxValue;
                customizations[i].maxValue = (int) (playerCustomizationMod * ((customizations[i].maxValue + 1) / 255.0f));
                if (customizations[i].maxValue > max_maxValue)
                {
                    customizations[i].maxValue = max_maxValue;
                }
                if (customizations[i].maxValue < customizations[i].minValue)
                {
                    customizations[i].maxValue = customizations[i].minValue;
                }
                threshold += 20;
            }
        }
        return customizations;
    }

    public resource_weight[] getAssemblyResourceWeights() throws InterruptedException
    {
        return OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES;
    }
}
