package script.conversation;

import script.*;
import script.library.*;

public class lifeday_faction_vendor_imperial extends script.base_script
{
    public lifeday_faction_vendor_imperial()
    {
    }
    public static String c_stringFile = "conversation/lifeday_faction_vendor_imperial";
    public boolean lifeday_faction_vendor_imperial_condition__defaultCondition(obj_id player, obj_id npc) throws InterruptedException
    {
        return true;
    }
    public boolean lifeday_faction_vendor_imperial_condition_alreadyWorking(obj_id player, obj_id npc) throws InterruptedException
    {
        int lifeDayBuff = buff.getBuffOnTargetFromGroup(player, "lifeday_tracker");
        if (lifeDayBuff == 0)
        {
            return true;
        }
        return false;
    }
    public boolean lifeday_faction_vendor_imperial_condition_lockedOut(obj_id player, obj_id npc) throws InterruptedException
    {
        if (hasObjVar(player, "lifeday.locked_out"))
        {
            return true;
        }
        return false;
    }
    public boolean lifeday_faction_vendor_imperial_condition_godMode(obj_id player, obj_id npc) throws InterruptedException
    {
        return (isGod(player));
    }
    public boolean lifeday_faction_vendor_imperial_condition_scoreBoardNotEmpty(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id tatooine = getPlanetByName("tatooine");
        if (!isIdValid(tatooine))
        {
            return false;
        }
        if (!hasObjVar(tatooine, "lifeday.emptyScoreBoard"))
        {
            return false;
        }
        String emptyScoreBoard = getStringObjVar(tatooine, "lifeday.emptyScoreBoard");
        if (emptyScoreBoard == null || emptyScoreBoard.equals(""))
        {
            return false;
        }
        if (emptyScoreBoard.equals("true"))
        {
            return false;
        }
        return true;
    }
    public boolean lifeday_faction_vendor_imperial_condition_alreadyWorking_2(obj_id player, obj_id npc) throws InterruptedException
    {
        int lifeDayBuff = buff.getBuffOnTargetFromGroup(player, "lifeday");
        if (lifeDayBuff == 0)
        {
            return false;
        }
        return true;
    }
    public boolean lifeday_faction_vendor_imperial_condition_rebelPlayer(obj_id player, obj_id npc) throws InterruptedException
    {
        if (factions.isRebel(player))
        {
            return true;
        }
        int lifeDayBuff = buff.getBuffOnTargetFromGroup(player, "lifeday_tracker");
        int lifeDayBuffRebel = buff.getBuffOnTargetFromGroup(player, "lifeday_rebel");
        if (buff.hasBuff(player, "event_lifeday_casual_rebel_lock_in") || buff.hasBuff(player, "event_lifeday_competitive_rebel_lock_in"))
        {
            return true;
        }
        if (lifeDayBuffRebel != 0)
        {
            return true;
        }
        else if (lifeDayBuff == 0)
        {
            if (hasObjVar(player, "lifeday.neutral_rebel"))
            {
                removeObjVar(player, "lifeday.neutral_rebel");
                return false;
            }
        }
        return false;
    }
    public boolean lifeday_faction_vendor_imperial_condition_neutral(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!factions.isImperial(player))
        {
            return true;
        }
        return false;
    }
    public boolean lifeday_faction_vendor_imperial_condition_wookiee(obj_id player, obj_id npc) throws InterruptedException
    {
        int species = getSpecies(player);
        if (species == SPECIES_WOOKIEE)
        {
            return true;
        }
        return false;
    }
    public void lifeday_faction_vendor_imperial_action_showTokenVendorUI(obj_id player, obj_id npc) throws InterruptedException
    {
        dictionary d = new dictionary();
        d.put("player", player);
        messageTo(npc, "showInventorySUI", d, 0, false);
        return;
    }
    public void lifeday_faction_vendor_imperial_action_addCasualBuff(obj_id player, obj_id npc) throws InterruptedException
    {
        int lifeDayBuff = buff.getBuffOnTargetFromGroup(player, "lifeday_tracker");
        if (lifeDayBuff == 0)
        {
            if (!hasObjVar(player, "lifeday.locked_out"))
            {
                buff.applyBuff(player, "event_lifeday_casual_imperial_lock_in");
                if (!factions.isImperial(player))
                {
                    setObjVar(player, "lifeday.neutral_imperial", 1);
                }
            }
        }
        return;
    }
    public void lifeday_faction_vendor_imperial_action_addCompetitiveBuff(obj_id player, obj_id npc) throws InterruptedException
    {
        int lifeDayBuff = buff.getBuffOnTargetFromGroup(player, "lifeday_tracker");
        if (lifeDayBuff == 0)
        {
            if (!hasObjVar(player, "lifeday.locked_out"))
            {
                buff.applyBuff(player, "event_lifeday_competitive_imperial_lock_in");
                if (!factions.isImperial(player))
                {
                    setObjVar(player, "lifeday.neutral_imperial", 1);
                }
            }
        }
        return;
    }
    public void lifeday_faction_vendor_imperial_action_barkLeaders(obj_id player, obj_id npc) throws InterruptedException
    {
        if (!buff.hasBuff(npc, "event_lifeday_no_bark"))
        {
            obj_id tatooine = getPlanetByName("tatooine");
            if (!isIdValid(tatooine))
            {
                return;
            }
            if (!hasObjVar(tatooine, "lifeday.emptyScoreBoard"))
            {
                return;
            }
            String emptyScoreBoard = getStringObjVar(tatooine, "lifeday.emptyScoreBoard");
            if (emptyScoreBoard == null || emptyScoreBoard.equals(""))
            {
                return;
            }
            if (!emptyScoreBoard.equals("true"))
            {
                if (hasObjVar(tatooine, "lifeday.positionThree.playerScore"))
                {
                    int playerScore = getIntObjVar(tatooine, "lifeday.positionThree.playerScore");
                    String playerName = getStringObjVar(tatooine, "lifeday.positionThree.playerName");
                    String playerFaction = getStringObjVar(tatooine, "lifeday.positionThree.playerFaction");
                    chat.chat(npc, "In third place the " + playerFaction + " " + playerName + ", with " + playerScore + " tokens.");
                }
                if (hasObjVar(tatooine, "lifeday.positionTwo.playerScore"))
                {
                    int playerScore = getIntObjVar(tatooine, "lifeday.positionTwo.playerScore");
                    String playerName = getStringObjVar(tatooine, "lifeday.positionTwo.playerName");
                    String playerFaction = getStringObjVar(tatooine, "lifeday.positionTwo.playerFaction");
                    chat.chat(npc, "In second place the " + playerFaction + " " + playerName + ", with " + playerScore + " tokens.");
                }
                if (hasObjVar(tatooine, "lifeday.positionOne.playerScore"))
                {
                    int playerScore = getIntObjVar(tatooine, "lifeday.positionOne.playerScore");
                    String playerName = getStringObjVar(tatooine, "lifeday.positionOne.playerName");
                    String playerFaction = getStringObjVar(tatooine, "lifeday.positionOne.playerFaction");
                    chat.chat(npc, "In first place the " + playerFaction + " " + playerName + ", with " + playerScore + " tokens.");
                }
                buff.applyBuff(npc, "event_lifeday_no_bark");
            }
        }
    }
    public void lifeday_faction_vendor_imperial_action_barkLeadersSilent(obj_id player, obj_id npc) throws InterruptedException
    {
        obj_id tatooine = getPlanetByName("tatooine");
        if (!isIdValid(tatooine))
        {
            return;
        }
        if (!hasObjVar(tatooine, "lifeday.emptyScoreBoard"))
        {
            return;
        }
        String emptyScoreBoard = getStringObjVar(tatooine, "lifeday.emptyScoreBoard");
        if (emptyScoreBoard == null || emptyScoreBoard.equals(""))
        {
            return;
        }
        if (!emptyScoreBoard.equals("true"))
        {
            chat.chat(npc, "Today's propaganda masters are");
            if (hasObjVar(tatooine, "lifeday.positionThree.playerScore"))
            {
                int playerScore = getIntObjVar(tatooine, "lifeday.positionThree.playerScore");
                String playerName = getStringObjVar(tatooine, "lifeday.positionThree.playerName");
                String playerFaction = getStringObjVar(tatooine, "lifeday.positionThree.playerFaction");
                chat.chat(npc, player, "In third place the " + playerFaction + " " + playerName + ", with " + playerScore + " tokens.", chat.ChatFlag_targetOnly);
            }
            if (hasObjVar(tatooine, "lifeday.positionTwo.playerScore"))
            {
                int playerScore = getIntObjVar(tatooine, "lifeday.positionTwo.playerScore");
                String playerName = getStringObjVar(tatooine, "lifeday.positionTwo.playerName");
                String playerFaction = getStringObjVar(tatooine, "lifeday.positionTwo.playerFaction");
                chat.chat(npc, player, "In second place the " + playerFaction + " " + playerName + ", with " + playerScore + " tokens.", chat.ChatFlag_targetOnly);
            }
            if (hasObjVar(tatooine, "lifeday.positionOne.playerScore"))
            {
                int playerScore = getIntObjVar(tatooine, "lifeday.positionOne.playerScore");
                String playerName = getStringObjVar(tatooine, "lifeday.positionOne.playerName");
                String playerFaction = getStringObjVar(tatooine, "lifeday.positionOne.playerFaction");
                chat.chat(npc, player, "In first place the " + playerFaction + " " + playerName + ", with " + playerScore + " tokens.", chat.ChatFlag_targetOnly);
            }
        }
    }
    public int lifeday_faction_vendor_imperial_handleBranch2(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_45"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                lifeday_faction_vendor_imperial_action_showTokenVendorUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_95");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_52"))
        {
            lifeday_faction_vendor_imperial_action_barkLeadersSilent(player, npc);
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_54");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday_faction_vendor_imperial_handleBranch4(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_102"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                lifeday_faction_vendor_imperial_action_showTokenVendorUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_95");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_103"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                string_id message = new string_id(c_stringFile, "s_104");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_34"))
        {
            lifeday_faction_vendor_imperial_action_barkLeadersSilent(player, npc);
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_35");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday_faction_vendor_imperial_handleBranch7(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_97"))
        {
            doAnimationAction(player, "backhand_threaten");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "slow_down");
                string_id message = new string_id(c_stringFile, "s_100");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lifeday_faction_vendor_imperial_condition_scoreBoardNotEmpty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_38");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_49");
                    }
                    utils.setScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId", 8);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_98"))
        {
            doAnimationAction(player, "dismiss");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "implore");
                string_id message = new string_id(c_stringFile, "s_99");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday_faction_vendor_imperial_handleBranch8(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_38"))
        {
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_39");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lifeday_faction_vendor_imperial_condition_alreadyWorking(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lifeday_faction_vendor_imperial_condition_alreadyWorking(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lifeday_faction_vendor_imperial_condition_scoreBoardNotEmpty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_49"))
        {
            lifeday_faction_vendor_imperial_action_barkLeadersSilent(player, npc);
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_53");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday_faction_vendor_imperial_handleBranch9(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            doAnimationAction(player, "salute2");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                lifeday_faction_vendor_imperial_action_addCompetitiveBuff(player, npc);
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_73"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                lifeday_faction_vendor_imperial_action_addCasualBuff(player, npc);
                string_id message = new string_id(c_stringFile, "s_79");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_75"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            lifeday_faction_vendor_imperial_action_barkLeadersSilent(player, npc);
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday_faction_vendor_imperial_handleBranch12(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_85"))
        {
            doAnimationAction(player, "shrug_shoulders");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_91");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lifeday_faction_vendor_imperial_condition_alreadyWorking(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lifeday_faction_vendor_imperial_condition_alreadyWorking(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lifeday_faction_vendor_imperial_condition_scoreBoardNotEmpty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId", 17);
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcSpeak(player, pp);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                    prose_package pp = new prose_package();
                    pp.stringId = message;
                    pp.actor.set(player);
                    pp.target.set(npc);
                    npcEndConversationWithMessage(player, pp);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_87"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod_head_multiple");
                string_id message = new string_id(c_stringFile, "s_89");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_93"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                lifeday_faction_vendor_imperial_action_showTokenVendorUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_95");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_56"))
        {
            lifeday_faction_vendor_imperial_action_barkLeadersSilent(player, npc);
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_59");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday_faction_vendor_imperial_handleBranch13(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            doAnimationAction(player, "salute2");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                lifeday_faction_vendor_imperial_action_addCompetitiveBuff(player, npc);
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_73"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                lifeday_faction_vendor_imperial_action_addCasualBuff(player, npc);
                string_id message = new string_id(c_stringFile, "s_79");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_75"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            lifeday_faction_vendor_imperial_action_barkLeadersSilent(player, npc);
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday_faction_vendor_imperial_handleBranch16(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_60"))
        {
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "pound_fist_palm");
                string_id message = new string_id(c_stringFile, "s_70");
                int numberOfResponses = 0;
                boolean hasResponse = false;
                boolean hasResponse0 = false;
                if (lifeday_faction_vendor_imperial_condition_alreadyWorking(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse0 = true;
                }
                boolean hasResponse1 = false;
                if (lifeday_faction_vendor_imperial_condition_alreadyWorking(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse1 = true;
                }
                boolean hasResponse2 = false;
                if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse2 = true;
                }
                boolean hasResponse3 = false;
                if (lifeday_faction_vendor_imperial_condition_scoreBoardNotEmpty(player, npc))
                {
                    ++numberOfResponses;
                    hasResponse = true;
                    hasResponse3 = true;
                }
                if (hasResponse)
                {
                    int responseIndex = 0;
                    string_id responses[] = new string_id[numberOfResponses];
                    if (hasResponse0)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_71");
                    }
                    if (hasResponse1)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_73");
                    }
                    if (hasResponse2)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_75");
                    }
                    if (hasResponse3)
                    {
                        responses[responseIndex++] = new string_id(c_stringFile, "s_47");
                    }
                    utils.setScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId", 17);
                    npcSpeak(player, message);
                    npcSetConversationResponses(player, responses);
                }
                else 
                {
                    utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                    npcEndConversationWithMessage(player, message);
                }
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_62"))
        {
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                lifeday_faction_vendor_imperial_action_showTokenVendorUI(player, npc);
                string_id message = new string_id(c_stringFile, "s_95");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_63"))
        {
            lifeday_faction_vendor_imperial_action_barkLeadersSilent(player, npc);
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_65");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int lifeday_faction_vendor_imperial_handleBranch17(obj_id player, obj_id npc, string_id response) throws InterruptedException
    {
        if (response.equals("s_71"))
        {
            doAnimationAction(player, "salute2");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "salute2");
                lifeday_faction_vendor_imperial_action_addCompetitiveBuff(player, npc);
                string_id message = new string_id(c_stringFile, "s_77");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_73"))
        {
            doAnimationAction(player, "rub_chin_thoughtful");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                lifeday_faction_vendor_imperial_action_addCasualBuff(player, npc);
                string_id message = new string_id(c_stringFile, "s_79");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_75"))
        {
            doAnimationAction(player, "shake_head_no");
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                doAnimationAction(npc, "nod");
                string_id message = new string_id(c_stringFile, "s_81");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcEndConversationWithMessage(player, pp);
                return SCRIPT_CONTINUE;
            }
        }
        if (response.equals("s_47"))
        {
            lifeday_faction_vendor_imperial_action_barkLeadersSilent(player, npc);
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                string_id message = new string_id(c_stringFile, "s_51");
                utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
                npcEndConversationWithMessage(player, message);
                return SCRIPT_CONTINUE;
            }
        }
        return SCRIPT_DEFAULT;
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if ((!isTangible(self)) || (isPlayer(self)))
        {
            detachScript(self, "conversation.lifeday_faction_vendor_imperial");
        }
        setCondition(self, CONDITION_CONVERSABLE);
        return SCRIPT_CONTINUE;
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        setCondition(self, CONDITION_CONVERSABLE);
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
        detachScript(self, "conversation.lifeday_faction_vendor_imperial");
        return SCRIPT_CONTINUE;
    }
    public int OnDetach(obj_id self) throws InterruptedException
    {
        clearCondition(self, CONDITION_CONVERSABLE);
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
        if (lifeday_faction_vendor_imperial_condition_rebelPlayer(player, npc))
        {
            doAnimationAction(npc, "rub_chin_thoughtful");
            string_id message = new string_id(c_stringFile, "s_50");
            chat.chat(npc, player, message);
            return SCRIPT_CONTINUE;
        }
        if (lifeday_faction_vendor_imperial_condition_alreadyWorking_2(player, npc))
        {
            doAnimationAction(npc, "nod");
            doAnimationAction(player, "greet");
            lifeday_faction_vendor_imperial_action_barkLeaders(player, npc);
            string_id message = new string_id(c_stringFile, "s_43");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lifeday_faction_vendor_imperial_condition_scoreBoardNotEmpty(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_45");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_52");
                }
                utils.setScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId", 2);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lifeday_faction_vendor_imperial", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (lifeday_faction_vendor_imperial_condition_lockedOut(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            lifeday_faction_vendor_imperial_action_barkLeaders(player, npc);
            string_id message = new string_id(c_stringFile, "s_101");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lifeday_faction_vendor_imperial_condition_scoreBoardNotEmpty(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_102");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_103");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_34");
                }
                utils.setScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId", 4);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lifeday_faction_vendor_imperial", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (lifeday_faction_vendor_imperial_condition_wookiee(player, npc))
        {
            doAnimationAction(npc, "sweat");
            lifeday_faction_vendor_imperial_action_barkLeaders(player, npc);
            string_id message = new string_id(c_stringFile, "s_96");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lifeday_faction_vendor_imperial_condition_alreadyWorking(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_97");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_98");
                }
                utils.setScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId", 7);
                npcStartConversation(player, npc, "lifeday_faction_vendor_imperial", message, responses);
            }
            else 
            {
                chat.chat(npc, player, message);
            }
            return SCRIPT_CONTINUE;
        }
        if (lifeday_faction_vendor_imperial_condition_neutral(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "nod_head_once");
            lifeday_faction_vendor_imperial_action_barkLeaders(player, npc);
            string_id message = new string_id(c_stringFile, "s_83");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lifeday_faction_vendor_imperial_condition_alreadyWorking(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            boolean hasResponse3 = false;
            if (lifeday_faction_vendor_imperial_condition_scoreBoardNotEmpty(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse3 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_85");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_87");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_93");
                }
                if (hasResponse3)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_56");
                }
                utils.setScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId", 12);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lifeday_faction_vendor_imperial", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
        {
            doAnimationAction(npc, "salute2");
            doAnimationAction(player, "salute2");
            lifeday_faction_vendor_imperial_action_barkLeaders(player, npc);
            string_id message = new string_id(c_stringFile, "s_58");
            int numberOfResponses = 0;
            boolean hasResponse = false;
            boolean hasResponse0 = false;
            if (lifeday_faction_vendor_imperial_condition_alreadyWorking(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse0 = true;
            }
            boolean hasResponse1 = false;
            if (lifeday_faction_vendor_imperial_condition__defaultCondition(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse1 = true;
            }
            boolean hasResponse2 = false;
            if (lifeday_faction_vendor_imperial_condition_scoreBoardNotEmpty(player, npc))
            {
                ++numberOfResponses;
                hasResponse = true;
                hasResponse2 = true;
            }
            if (hasResponse)
            {
                int responseIndex = 0;
                string_id responses[] = new string_id[numberOfResponses];
                if (hasResponse0)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_60");
                }
                if (hasResponse1)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_62");
                }
                if (hasResponse2)
                {
                    responses[responseIndex++] = new string_id(c_stringFile, "s_63");
                }
                utils.setScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId", 16);
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                npcStartConversation(player, npc, "lifeday_faction_vendor_imperial", null, pp, responses);
            }
            else 
            {
                prose_package pp = new prose_package();
                pp.stringId = message;
                pp.actor.set(player);
                pp.target.set(npc);
                chat.chat(npc, player, null, null, pp);
            }
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  All conditions for OnStartNpcConversation were false.");
        return SCRIPT_CONTINUE;
    }
    public int OnNpcConversationResponse(obj_id self, String conversationId, obj_id player, string_id response) throws InterruptedException
    {
        if (!conversationId.equals("lifeday_faction_vendor_imperial"))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id npc = self;
        int branchId = utils.getIntScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
        if (branchId == 2 && lifeday_faction_vendor_imperial_handleBranch2(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 4 && lifeday_faction_vendor_imperial_handleBranch4(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 7 && lifeday_faction_vendor_imperial_handleBranch7(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 8 && lifeday_faction_vendor_imperial_handleBranch8(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 9 && lifeday_faction_vendor_imperial_handleBranch9(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 12 && lifeday_faction_vendor_imperial_handleBranch12(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 13 && lifeday_faction_vendor_imperial_handleBranch13(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 16 && lifeday_faction_vendor_imperial_handleBranch16(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        if (branchId == 17 && lifeday_faction_vendor_imperial_handleBranch17(player, npc, response) == SCRIPT_CONTINUE)
        {
            return SCRIPT_CONTINUE;
        }
        chat.chat(npc, "Error:  Fell through all branches and responses for OnNpcConversationResponse.");
        utils.removeScriptVar(player, "conversation.lifeday_faction_vendor_imperial.branchId");
        return SCRIPT_CONTINUE;
    }
}
