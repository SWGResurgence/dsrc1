package script.conversation;

import script.*;
import script.library.ai_lib;
import script.library.chat;
import script.library.utils;

public class mustafar_token_vendor extends script.base_script
{
    public mustafar_token_vendor()
    {
    }
    public static String c_stringFile = "conversation/mustafar_token_vendor";
    public boolean mustafar_token_vendor_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public void mustafar_token_vendor_action_showTokenVendorUI(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(npc, "showInventorySUI", d, 0, false);
        return;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.mustafar_token_vendor");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
        setCondition(self, CONDITION_SPACE_INTERESTING);
        String name = "Khan Horne";
        String color_red = "\\#FF0000";
        String color_white = "\\#FFFFFF";
        setName(self, color_red + name + color_white);
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
    {
        int menu = menuInfo.addRootMenu(menu_info_types.CONVERSE_START, null);
        menu_info_data menuInfoData = menuInfo.getMenuItemById(menu);
        menuInfoData.setServerNotify(false);
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
        detachScript(self, "conversation.mustafar_token_vendor");
        return SCRIPT_CONTINUE;
    }
    public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses) throws InterruptedException
    {
        Object[] objects = new Object[responses.length];
        System.arraycopy(responses, 0, objects, 0, responses.length);
        return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
    }
    public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
    {
        obj_id npc = self;
        if (ai_lib.isInCombat(npc) || ai_lib.isInCombat(player))
        {
            return SCRIPT_OVERRIDE;
        }
        if (mustafar_token_vendor_condition__defaultCondition(player, npc))
        {
            mustafar_token_vendor_action_showTokenVendorUI(player, npc);
            string_id message = new string_id(c_stringFile, "s_6");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("mustafar_token_vendor"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.mustafar_token_vendor.branchId");
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.mustafar_token_vendor.branchId");
        return SCRIPT_CONTINUE;
    }
}