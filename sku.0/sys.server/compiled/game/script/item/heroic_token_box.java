package script.item;

import script.library.trial;
import script.library.utils;
import script.obj_id;
import script.string_id;

import script.dictionary;
import script.menu_info;
import script.menu_info_types;
import script.library.static_item;
import script.library.sui;

public class heroic_token_box extends script.base_script
{
    private static final string_id MNU_WITHDRAW = new string_id("token_box", "withdraw");
    private static final String[] TOKEN_OPTIONS = {
        "Nightsister",
        "Tusken King",
        "Droid Factory",
        "Lost Star Destroyer",
        "Exar Kun",
        "Battle of Hoth",
        "Marauder",
        "Mustafar",
        "Veteran Reward",
        "Space Duty",
        "Rebel Battlefield",
        "Imperial Battlefield",
        "Imperial Space GCW",
        "Rebel Space GCW",
        "Rebel City Invasion",
        "Imperial City Invasion",
        "Imperial Restuss Commendation",
        "Rebel Restuss Commendation",
        "Empire Day",
        "Remebrance Day",
        "Rebel Life Day Life",
        "Imperial Life Day Propaganda",
        "Galactic Moon Festival",
        "Love Day Chak Heart",
        "Copper Chronicles",
        "Silver Chronicles",
        "Gold Chronicles",
        "Meatlump",
        "Midlithe Crystal",
        "Dathomir Amber",
        "Spider Silk",
        "Rancor Teeth",
        "Whuffa Leather",
        "Rare Dried Herb",
        "Spell Weaver Crystal"
    };
    private static final int[] TOKENS = {
        0,
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14,
        15,
        16,
        17,
        18,
        19,
        20,
        21,
        22,
        23,
        24,
        25,
        26,
        27,
        28,
        29,
        30,
        31,
        32,
        33,
        34
    };    
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "item.set.tokens_held"))
        {
            trial.initializeBox(self);
        }
        else 
        {
            trial.verifyBox(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "item.set.tokens_held"))
        {
            trial.initializeBox(self);
        }
        else 
        {
            trial.verifyBox(self);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int free = getFirstFreeIndex(names);
        if (free == -1)
        {
            return SCRIPT_CONTINUE;
        }
        if (!hasObjVar(self, "item.set.tokens_held"))
        {
            return SCRIPT_CONTINUE;
        }
        int[] tokenTypes = getIntArrayObjVar(self, "item.set.tokens_held");
        if (tokenTypes.length == trial.NUM_HEROIC_TOKEN_TYPES)
        {
            for (int i = 0; i < tokenTypes.length; i++)
            {
                names[free] = utils.packStringId(new string_id("static_item_n", trial.HEROIC_TOKENS[i]));
                attribs[free++] = Integer.toString(tokenTypes[i]);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException {
        if (utils.isNestedWithin(self, player)) {
            int mnuColor = mi.addRootMenu(menu_info_types.SERVER_MENU1, MNU_WITHDRAW);
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException {
        if (utils.isNestedWithin(self, player) && item == menu_info_types.SERVER_MENU1) {
            String title = "Heroic Token Box";
            String prompt = "Please select the token that you would like to withdraw.";
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, TOKEN_OPTIONS, "handleOptionSelect", true, false);
        }
        return SCRIPT_CONTINUE;
    }
    public int handleOptionSelect(obj_id self, dictionary params) throws InterruptedException {
        int bp = sui.getIntButtonPressed(params);
        if (bp == sui.BP_CANCEL) {
            return SCRIPT_CONTINUE;
        }
        int idx = sui.getListboxSelectedRow(params);
        if (idx == -1 || idx > TOKEN_OPTIONS.length)
            return SCRIPT_CONTINUE;
        setObjVar(self, "tokenType", idx);
        String title = "Heroic Token Box";
        String prompt = "Please select how many tokens you want to withdraw from your Token Box.";
        sui.filteredInputbox(self, sui.getPlayerId(params), prompt, title, "handleQuantitySelect", "");
        return SCRIPT_CONTINUE;
    }
    public int handleQuantitySelect(obj_id self, dictionary params) throws InterruptedException {
        obj_id player = utils.getContainingPlayer(self);
        obj_id tokenBox = trial.getTokenBox(player);
        int amount = Integer.parseInt(sui.getInputBoxText(params));
        int selectedRow = getIntObjVar(self, "tokenType");
        String tokenType = trial.HEROIC_TOKENS[TOKENS[selectedRow]];
        if (amount < 1 || amount > trial.getTokenAmountInBox(tokenBox, tokenType)) {
            sendSystemMessageTestingOnly(player, "Sorry, but that is an invalid amount.");
        } else {
            obj_id inv = getObjectInSlot(player, "inventory");
            if (getVolumeFree(inv) > 0) {
                trial.withdrawTokensFromBox(tokenBox, tokenType, amount);
                static_item.createNewItemFunction(tokenType, player, amount);
            } else {
                sendSystemMessageTestingOnly(player, "Please make space in your inventory.");
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnDestroy(obj_id self) throws InterruptedException
     {
         obj_id whoDat = getTopMostContainer(self);
         sendSystemMessage(whoDat, new string_id("spam", "can_not_destroy"));
         return SCRIPT_OVERRIDE;
     }
}
