// ======================================================================
//
// skyann_langen.java
// Copyright 2004, Sony Online Entertainment
// All Rights Reserved.
//
// Created with SwgConversationEditor 1.37 - DO NOT EDIT THIS AUTO-GENERATED FILE!
//
// ======================================================================

package script.conversation;

// ======================================================================
// Library Includes
// ======================================================================

import script.library.ai_lib;
import script.library.chat;
import script.library.conversation;
import script.library.utils;
import script.library.groundquests;
import script.*;

public class skyann_langen extends script.base_script
{
	public skyann_langen()

	{

	}

// ======================================================================
// Script Constants
// ======================================================================

public static String c_stringFile = "conversation/skyann_langen";

// ======================================================================
// Script Conditions
// ======================================================================

	public boolean skyann_langen_condition__defaultCondition (obj_id player, obj_id npc) throws InterruptedException
	{
		return true;
	}

// ----------------------------------------------------------------------

	public boolean skyann_langen_condition_readyForReward (obj_id player, obj_id npc) throws InterruptedException
	{
        faceTo(npc, player);
        if (groundquests.isTaskActive(player, "quest/world_boss_naboo_peko", "returnSkyannLangen"))
        {
            return true;
        }
        return false;
	}
// ----------------------------------------------------------------------

	public boolean skyann_langen_condition_isOnQuest (obj_id player, obj_id npc) throws InterruptedException
	{
        if (groundquests.isQuestActive(player, "quest/world_boss_naboo_peko"))
        {
            return true;
        }
        return false;
	}

// ======================================================================
// Script Actions
// ======================================================================

	public void skyann_langen_action_sendRewardSignal (obj_id player, obj_id npc) throws InterruptedException
	{
        groundquests.sendSignal(player, "completedWorldBossNabooPeko");
    }

// ----------------------------------------------------------------------

	public void skyann_langen_action_grantQuest (obj_id player, obj_id npc) throws InterruptedException
	{
        groundquests.grantQuest(player, "quest/world_boss_naboo_peko");
        return;
	}

// ======================================================================
// Script %TO Tokens
// ======================================================================

// ======================================================================
// Script %DI Tokens
// ======================================================================

// ======================================================================
// Script %DF Tokens
// ======================================================================

// ======================================================================
// handleBranch<n> Functions 
// ======================================================================

int skyann_langen_handleBranch4 (obj_id player, obj_id npc, string_id response) throws InterruptedException
{
	//-- [BRANCH NOTE] 
	//-- NPC: Hey, you're a new face around these parts. Are you looking for them too?

	//-- [RESPONSE NOTE] 
	//-- PLAYER: Them? What are you talking about?
	if (response.equals("s_4"))
	{
		//-- [NOTE] 
		if (skyann_langen_condition__defaultCondition (player, npc))
		{
			//-- NPC: Look, no one just comes into Mos Taike who isn't a local unless they're looking for them. 
			string_id message = new string_id (c_stringFile, "s_5");
			int numberOfResponses = 0;

			boolean hasResponse = false;

			//-- PLAYER: It would be helpful if you would tell me what you mean by them?
			boolean hasResponse0 = false;
			if (skyann_langen_condition__defaultCondition (player, npc))
			{
				++numberOfResponses;
				hasResponse = true;
				hasResponse0 = true;
			}

			if (hasResponse)
			{
				int responseIndex = 0;
				string_id responses [] = new string_id [numberOfResponses];

				if (hasResponse0)
					responses [responseIndex++] = new string_id (c_stringFile, "s_6");

				utils.setScriptVar (player, "conversation.skyann_langen.branchId", 5);

				npcSpeak (player, message);
				npcSetConversationResponses (player, responses);
			}
			else
			{
				utils.removeScriptVar (player, "conversation.skyann_langen.branchId");

				npcEndConversationWithMessage (player, message);
			}

			return SCRIPT_CONTINUE;
		}

	}

	return SCRIPT_DEFAULT;
}

// ----------------------------------------------------------------------

int skyann_langen_handleBranch5 (obj_id player, obj_id npc, string_id response) throws InterruptedException
{
	//-- [BRANCH NOTE] 
	//-- NPC: Look, no one just comes into Mos Taike who isn't a local unless they're looking for them. 

	//-- [RESPONSE NOTE] 
	//-- PLAYER: It would be helpful if you would tell me what you mean by them?
	if (response.equals("s_6"))
	{
		//-- [NOTE] 
		if (skyann_langen_condition__defaultCondition (player, npc))
		{
			//-- NPC: That's easy, I'm talking about Krayts, Krayt Dragons. 
			string_id message = new string_id (c_stringFile, "s_7");
			int numberOfResponses = 0;

			boolean hasResponse = false;

			//-- PLAYER: I've heard about the graveyard already. Your stories travel fast. I'm interested in your newest piece of information.
			boolean hasResponse0 = false;
			if (skyann_langen_condition__defaultCondition (player, npc))
			{
				++numberOfResponses;
				hasResponse = true;
				hasResponse0 = true;
			}

			if (hasResponse)
			{
				int responseIndex = 0;
				string_id responses [] = new string_id [numberOfResponses];

				if (hasResponse0)
					responses [responseIndex++] = new string_id (c_stringFile, "s_8");

				utils.setScriptVar (player, "conversation.skyann_langen.branchId", 6);

				npcSpeak (player, message);
				npcSetConversationResponses (player, responses);
			}
			else
			{
				utils.removeScriptVar (player, "conversation.skyann_langen.branchId");

				npcEndConversationWithMessage (player, message);
			}

			return SCRIPT_CONTINUE;
		}

	}

	return SCRIPT_DEFAULT;
}

// ----------------------------------------------------------------------

int skyann_langen_handleBranch6 (obj_id player, obj_id npc, string_id response) throws InterruptedException
{
	//-- [BRANCH NOTE] 
	//-- NPC: That's easy, I'm talking about Krayts, Krayt Dragons. 

	//-- [RESPONSE NOTE] 
	//-- PLAYER: I've heard about the graveyard already. Your stories travel fast. I'm interested in your newest piece of information.
	if (response.equals("s_8"))
	{
		//-- [NOTE] 
		if (skyann_langen_condition__defaultCondition (player, npc))
		{
			//-- NPC: Well that's going to cost you. It seems I shouldn't have had such a low rate on the Krayt Graveyard's location now that I think about it. 
			string_id message = new string_id (c_stringFile, "s_9");
			int numberOfResponses = 0;

			boolean hasResponse = false;

			//-- PLAYER: How much? Couldn't I just stumble around the graveyard and find it?
			boolean hasResponse0 = false;
			if (skyann_langen_condition__defaultCondition (player, npc))
			{
				++numberOfResponses;
				hasResponse = true;
				hasResponse0 = true;
			}

			if (hasResponse)
			{
				int responseIndex = 0;
				string_id responses [] = new string_id [numberOfResponses];

				if (hasResponse0)
					responses [responseIndex++] = new string_id (c_stringFile, "s_10");

				utils.setScriptVar (player, "conversation.skyann_langen.branchId", 7);

				npcSpeak (player, message);
				npcSetConversationResponses (player, responses);
			}
			else
			{
				utils.removeScriptVar (player, "conversation.skyann_langen.branchId");

				npcEndConversationWithMessage (player, message);
			}

			return SCRIPT_CONTINUE;
		}

	}

	return SCRIPT_DEFAULT;
}

// ----------------------------------------------------------------------

int skyann_langen_handleBranch7 (obj_id player, obj_id npc, string_id response) throws InterruptedException
{
	//-- [BRANCH NOTE] 
	//-- NPC: Well that's going to cost you. It seems I shouldn't have had such a low rate on the Krayt Graveyard's location now that I think about it. 

	//-- [RESPONSE NOTE] 
	//-- PLAYER: How much? Couldn't I just stumble around the graveyard and find it?
	if (response.equals("s_10"))
	{
		//-- [NOTE] 
		if (skyann_langen_condition__defaultCondition (player, npc))
		{
			//-- NPC: You could also just starting digging holes in the sand until you find treasure, but a map helps considerably. 
			string_id message = new string_id (c_stringFile, "s_11");
			int numberOfResponses = 0;

			boolean hasResponse = false;

			//-- PLAYER: Fair enough, how much is this going to set me back?
			boolean hasResponse0 = false;
			if (skyann_langen_condition__defaultCondition (player, npc))
			{
				++numberOfResponses;
				hasResponse = true;
				hasResponse0 = true;
			}

			if (hasResponse)
			{
				int responseIndex = 0;
				string_id responses [] = new string_id [numberOfResponses];

				if (hasResponse0)
					responses [responseIndex++] = new string_id (c_stringFile, "s_12");

				utils.setScriptVar (player, "conversation.skyann_langen.branchId", 8);

				npcSpeak (player, message);
				npcSetConversationResponses (player, responses);
			}
			else
			{
				utils.removeScriptVar (player, "conversation.skyann_langen.branchId");

				npcEndConversationWithMessage (player, message);
			}

			return SCRIPT_CONTINUE;
		}

	}

	return SCRIPT_DEFAULT;
}

// ----------------------------------------------------------------------

int skyann_langen_handleBranch8 (obj_id player, obj_id npc, string_id response) throws InterruptedException
{
	//-- [BRANCH NOTE] 
	//-- NPC: You could also just starting digging holes in the sand until you find treasure, but a map helps considerably. 

	//-- [RESPONSE NOTE] 
	//-- PLAYER: Fair enough, how much is this going to set me back?
	if (response.equals("s_12"))
	{
		//-- [NOTE] 
		if (skyann_langen_condition__defaultCondition (player, npc))
		{
			skyann_langen_action_grantQuest (player, npc);

			//-- NPC: Arguably your life, if you're not successful. Listen, kid here's the location to the Tusken Isolationist Cave. I want some valuables brought back and in return I'll give you a fair reward. 
			string_id message = new string_id (c_stringFile, "s_13");
			utils.removeScriptVar (player, "conversation.skyann_langen.branchId");

			npcEndConversationWithMessage (player, message);

			return SCRIPT_CONTINUE;
		}

	}

	return SCRIPT_DEFAULT;
}

// ----------------------------------------------------------------------

// ======================================================================
// User Script Triggers
// ======================================================================

	public int OnInitialize(obj_id self) throws InterruptedException
	{
		if ((!isMob (self)) || (isPlayer (self)))
		{
			detachScript(self, "conversation.skyann_langen");
		}

	setCondition (self, CONDITION_CONVERSABLE);

	return SCRIPT_CONTINUE;
	}

public int OnAttach(obj_id self) throws InterruptedException
{
	setCondition (self, CONDITION_CONVERSABLE);

	return SCRIPT_CONTINUE;
}

public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info menuInfo) throws InterruptedException
{
	int menu = menuInfo.addRootMenu (menu_info_types.CONVERSE_START, null);
	menu_info_data menuInfoData = menuInfo.getMenuItemById (menu);
	menuInfoData.setServerNotify (false);
	setCondition (self, CONDITION_CONVERSABLE);

	return SCRIPT_CONTINUE;
}

public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
{
	clearCondition (self, CONDITION_CONVERSABLE);
	detachScript (self, "conversation.skyann_langen");

	return SCRIPT_CONTINUE;
}

// ======================================================================
// Script Triggers
// ======================================================================

//-- This function should move to base_class.java
public boolean npcStartConversation(obj_id player, obj_id npc, String convoName, string_id greetingId, prose_package greetingProse, string_id[] responses)
{
	Object[] objects = new Object[responses.length];
	System.arraycopy(responses, 0, objects, 0, responses.length);
	return npcStartConversation(player, npc, convoName, greetingId, greetingProse, objects);
}

// ----------------------------------------------------------------------

public int OnStartNpcConversation(obj_id self, obj_id player) throws InterruptedException
{
	obj_id npc = self;

	if (ai_lib.isInCombat (npc) || ai_lib.isInCombat (player))
		return SCRIPT_OVERRIDE;

	//-- [NOTE] 
	if (skyann_langen_condition_readyForReward (player, npc))
	{
		skyann_langen_action_sendRewardSignal (player, npc);

		//-- NPC: I see you're still alive and you have what I asked for. Here's the reward I promised. 
		string_id message = new string_id (c_stringFile, "s_14");
		chat.chat (npc, player, message);

		return SCRIPT_CONTINUE;
	}

	//-- [NOTE] 
	if (skyann_langen_condition_isOnQuest (player, npc))
	{
		//-- NPC: You handled yourself pretty well. I've sent a few spacers that way before you came along and needless to say none have returned. Don't be a stranger, I may have more jobs for you.
		string_id message = new string_id (c_stringFile, "s_16");
		chat.chat (npc, player, message);

		return SCRIPT_CONTINUE;
	}

	//-- [NOTE] 
	if (skyann_langen_condition__defaultCondition (player, npc))
	{
		//-- NPC: Hey, you're a new face around these parts. Are you looking for them too?
		string_id message = new string_id (c_stringFile, "s_3");
		int numberOfResponses = 0;

		boolean hasResponse = false;

		//-- PLAYER: Them? What are you talking about?
		boolean hasResponse0 = false;
		if (skyann_langen_condition__defaultCondition (player, npc))
		{
			++numberOfResponses;
			hasResponse = true;
			hasResponse0 = true;
		}

		if (hasResponse)
		{
			int responseIndex = 0;
			string_id responses [] = new string_id [numberOfResponses];

			if (hasResponse0)
				responses [responseIndex++] = new string_id (c_stringFile, "s_4");

			utils.setScriptVar (player, "conversation.skyann_langen.branchId", 4);

			npcStartConversation (player, npc, "skyann_langen", message, responses);
		}
		else
		{
			chat.chat (npc, player, message);
		}

		return SCRIPT_CONTINUE;
	}

	chat.chat (npc, "Error:  All conditions for OnStartNpcConversation were false.");

	return SCRIPT_CONTINUE;
}

// ----------------------------------------------------------------------

public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
{
	if (!conversationId.equals("skyann_langen"))
		return SCRIPT_CONTINUE;

	obj_id npc = self;

	int branchId = utils.getIntScriptVar (player, "conversation.skyann_langen.branchId");

	if (branchId == 4 && skyann_langen_handleBranch4 (player, npc, response) == SCRIPT_CONTINUE)
		return SCRIPT_CONTINUE;

	if (branchId == 5 && skyann_langen_handleBranch5 (player, npc, response) == SCRIPT_CONTINUE)
		return SCRIPT_CONTINUE;

	if (branchId == 6 && skyann_langen_handleBranch6 (player, npc, response) == SCRIPT_CONTINUE)
		return SCRIPT_CONTINUE;

	if (branchId == 7 && skyann_langen_handleBranch7 (player, npc, response) == SCRIPT_CONTINUE)
		return SCRIPT_CONTINUE;

	if (branchId == 8 && skyann_langen_handleBranch8 (player, npc, response) == SCRIPT_CONTINUE)
		return SCRIPT_CONTINUE;

	chat.chat (npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");

	utils.removeScriptVar (player, "conversation.skyann_langen.branchId");

	return SCRIPT_CONTINUE;
}

// ======================================================================

}