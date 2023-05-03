package script.systems.crafting.chemistry.enzyme;

import script.library.craftinglib;
import script.resource_weight;

public class crafting_engineer_enzyme_processing extends script.systems.crafting.chemistry.enzyme.crafting_base_enzyme_processing
{
    public static final String VERSION = "v0.00.00";
    public static final String[] REQUIRED_SKILLS =
            {
                    "crafting_droidengineer_novice"
            };
    public static final String[] ASSEMBLY_SKILL_MODS =
            {
                    "droid_assembly"
            };
    public static final String[] EXPERIMENT_SKILL_MODS =
            {
                    "droid_experimentation"
            };
    public static final String[] CUSTOMIZATION_SKILL_MODS =
            {
                    "droid_customization"
            };
    public static final resource_weight[] OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES =
            {
                    new resource_weight("enzyme_purity", new resource_weight.weight[]
                            {
                                    new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
                                    new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
                            }),
                    new resource_weight("enzyme_mutagen", new resource_weight.weight[]
                            {
                                    new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
                                    new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1)
                            })
            };
    public static final resource_weight[] OBJ_MAX_ATTRIBUTE_RESOURCES =
            {
                    new resource_weight("enzyme_purity", new resource_weight.weight[]
                            {
                                    new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
                                    new resource_weight.weight(craftinglib.RESOURCE_TOUGHNESS, 1)
                            }),
                    new resource_weight("enzyme_mutagen", new resource_weight.weight[]
                            {
                                    new resource_weight.weight(craftinglib.RESOURCE_QUALITY, 1),
                                    new resource_weight.weight(craftinglib.RESOURCE_SHOCK_RESIST, 1)
                            })
            };
    public crafting_engineer_enzyme_processing()
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

    public resource_weight[] getAssemblyResourceWeights() throws InterruptedException
    {
        return OBJ_ASSEMBLY_ATTRIBUTE_RESOURCES;
    }
}
