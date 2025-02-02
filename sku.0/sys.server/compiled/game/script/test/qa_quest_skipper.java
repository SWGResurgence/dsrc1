package script.test;

import script.dictionary;
import script.library.groundquests;
import script.library.qa;
import script.library.sui;
import script.library.utils;
import script.obj_id;
import script.string_id;

import java.util.Arrays;
import java.util.stream.Stream;

public class qa_quest_skipper extends script.base_script
{
    public qa_quest_skipper()
    {
    }
    public static final String QUEST_TOOL_TITLE = "QA Quest Tool";
    public static final String QUEST_TOOL_PROMPT = "Select a Quest or menu item.\n\n(A) Active Quest\n(C) Completed Quest";
    public static final String QUEST_TOOL_SUBPROMPT = "Be sure to select the correct option. The quest will be modified per your selection without any additional verification. You can also select an individual quest task to manage it. \n\nThe visible tasks of the quest are displayed below prepended with their ID followed by (A) for an active task, (C) for a completed task, or (I) for an inactive task. You can select a task to activate/complete/fail/reset it.";
    public static final String MANUAL_ADD_GROUND_PROMPT = "Add Ground Quest Manually: Type the quest string in the area provided.  When you select OK the quest will be added like you just received the quest.";
    public static final String MANUAL_ADD_GROUND_TITLE = "Add Ground Manually";
    public static final String MANUAL_ADD_SPACE_PROMPT = "Add Space Quest Manually: Type the quest string in the area provided.  When you select OK the quest will be added like you just received the quest.";
    public static final String MANUAL_ADD_SPACE_TITLE = "Add Space Manually";
    public static final String SEND_SIGNAL_PROMPT = "Enter the name of the signal you wish to send. Note that this signal will trigger all active quests expecting this signal.";
    public static final String SEND_SIGNAL_TITLE = "Send Quest Signal";
    public static final String TASK_MENU_PROMPT = "Be sure to select the correct option. The quest will be modified per your selection without any additional verification. Select the action you would like to perform for this Quest Task.";
    public static final String TASK_MENU_TITLE = "Quest Task Actions";
    public static final String[] QUEST_TOOL_MENU = 
    {
        "Add a ground quest manually",
        "Add a space quest manually",
        "Attain test quests",
        "Bulk Grant/Complete Tool",
        "Bulk Grant Tool",
        "Send Quest Signal to Self"
    };
    public static final String[] QUEST_MAIN_MENU = 
    {
        "Complete this quest",
        "Remove this quest"
    };
    public static final String[] QUEST_ALT_MENU = 
    {
        "Remove this quest",
        "Remove and re-grant this quest"
    };
    public static final String[] QUEST_DEMO_QUESTS = 
    {
        "quest/event_cantina_bossk_1",
        "quest/borvos_guard_dagorel",
        "quest/borvo_acklay_find_armorer ",
        "quest/build_speeder_quest",
        "quest/tatooine_eisley_gototrehla",
        "quest/ep3_stren_dorn_bounty_belga",
        "quest/c_newbie_start",
        "quest/build_speeder",
        "spacequest/destroy_surpriseattack/corellia_imperial_1"
    };
    public static final String[] SPACE_QUEST_TYPES = 
    {
        "assassinate",
        "delivery",
        "delivery_no_pickup",
        "destroy",
        "destroy_duty",
        "destroy_surpriseattack",
        "escort",
        "escort_duty",
        "inspect",
        "patrol",
        "recovery",
        "recovery_duty",
        "rescue",
        "rescue_duty",
        "space_battle",
        "survival"
    };
    public int OnAttach(obj_id self) throws InterruptedException
    {
        if (isGod(self))
        {
            if (getGodLevel(self) < 10)
            {
                detachScript(self, "test.qa_quest_skipper");
                sendSystemMessage(self, "You do not have the appropriate access level to use this script.", null);
            }
        }
        else if (!isGod(self))
        {
            detachScript(self, "test.qa_quest_skipper");
        }
        return SCRIPT_CONTINUE;
    }
    public int OnSpeaking(obj_id self, String text) throws InterruptedException
    {
        if (isGod(self))
        {
            if ((toLower(text)).equals("qaquest"))
            {
                showToolMainMenu(self);
                return SCRIPT_OVERRIDE;
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleMainMenuOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "qaquest.pid"))
            {
                qa.checkParams(params, "qaquest");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    String[] tool_options = dataTableGetStringColumn("datatables/test/qa_tool_menu.iff", "main_tool");
                    qa.refreshMenu(self, "Choose the tool you want to use", "QA Tools", tool_options, "toolMainMenu", true, "qatool.pid");
                    utils.removeScriptVarTree(self, "qaquest");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String[] previousMainMenuArray = utils.getStringArrayScriptVar(self, "qaquest.qaquestMenu");
                    String previousSelection = previousMainMenuArray[idx];
                    if (QUEST_TOOL_MENU[0].equals(previousSelection)) {
                        qa.createInputBox(self, MANUAL_ADD_GROUND_PROMPT, MANUAL_ADD_GROUND_TITLE, "handleAddGroundQuestManually", "qaquest.pid");
                    } else if (QUEST_TOOL_MENU[1].equals(previousSelection)) {
                        qa.createInputBox(self, MANUAL_ADD_SPACE_PROMPT, MANUAL_ADD_SPACE_TITLE, "handleAddSpaceQuestManually", "qaquest.pid");
                    } else if (QUEST_TOOL_MENU[2].equals(previousSelection)) {
                        boolean receivedTestQuests = getTestQuests(self);
                        if (receivedTestQuests) {
                            sendSystemMessageTestingOnly(self, "Test quests received.");
                        } else {
                            sendSystemMessageTestingOnly(self, "There was a problem giving the test character test quests.");
                        }
                        showToolMainMenu(self);
                    } else if (QUEST_TOOL_MENU[3].equals(previousSelection)) {
                        sendSystemMessageTestingOnly(self, "Type or paste quests into the window separated with a semicolon (;).");
                        if (!utils.hasScriptVar(self, "qaquest.textData")) {
                            bulkGrantAndCompleteQuestUi(self, "");
                        } else {
                            String textData = utils.getStringScriptVar(self, "qaquest.textData");
                            bulkGrantAndCompleteQuestUi(self, textData);
                        }
                    } else if (QUEST_TOOL_MENU[4].equals(previousSelection)) {
                        sendSystemMessageTestingOnly(self, "Type or paste quests into the window separated with a semicolon (;).");
                        if (!utils.hasScriptVar(self, "qaquest.textData")) {
                            bulkGrantQuestUi(self, "");
                        } else {
                            String textData = utils.getStringScriptVar(self, "qaquest.textData");
                            bulkGrantQuestUi(self, textData);
                        }
                    } else if (QUEST_TOOL_MENU[5].equals(previousSelection)) {
                        qa.createInputBox(self, SEND_SIGNAL_PROMPT, SEND_SIGNAL_TITLE, "handleSendQuestSignal", "qaquest.pid");
                    } else {
                        utils.setScriptVar(self, "qaquest.questString", previousSelection);
                        String[] subMenu = getCorrectMenu(self, previousSelection);
                        utils.setScriptVar(self, "qaquest.menu", subMenu);

                        // get tasks for selected quest if quest is active  (swg source addition)
                        if(!previousSelection.contains("(C)"))
                        {
                            int questId = groundquests.getQuestIdFromString(getQuestString(self));
                            String questName = questGetQuestName(questId);
                            String table = "datatables/questtask/" + questName + ".iff";
                            int numVisibleRows = Arrays.stream(dataTableGetIntColumn(table, "IS_VISIBLE")).filter(i -> i > 0).sum();
                            int rowCount = dataTableGetNumRows(table);
                            String[] menuItemsToAdd = new String[numVisibleRows];
                            if(rowCount > 0)
                            {
                                int elementPos = 0;
                                for(int i = 0; i < rowCount; i++)
                                {
                                    if(groundquests.isTaskVisible(questId, i))
                                    {
                                        String flag = groundquests.isTaskActive(self, getQuestString(self), i) ? "A" :
                                                groundquests.hasCompletedTask(self, getQuestString(self), i) ? "C" : "I";
                                        dictionary taskData = dataTableGetRow(table, i);
                                        String taskTitle = localize(new string_id(
                                                table.contains("spacequest") ? questName : "quest/ground/" + questName.substring(6),
                                                taskData.getString("JOURNAL_ENTRY_TITLE").split(":")[1]
                                        ));
                                        menuItemsToAdd[elementPos] = String.format("%d: (%s) %s [%s])",
                                                i, flag, taskData.getString("TASK_NAME"), taskTitle);
                                        elementPos++;
                                    }
                                }
                            }
                            if(menuItemsToAdd.length > 0) // add tasks to existing menu
                            {
                                utils.setScriptVar(self, "qaquest.allQuestTasks", menuItemsToAdd);
                                subMenu = Stream.concat(Arrays.stream(subMenu), Arrays.stream(menuItemsToAdd)).toArray(String[]::new);
                            }
                        }
                        qa.refreshMenu(self, QUEST_TOOL_SUBPROMPT, QUEST_TOOL_TITLE, subMenu, "handleQuestOptions", true, "qaquest.pid", "qaquest.qaquestSubMenu");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "qaquest.pid"))
            {
                qa.checkParams(params, "qaquest");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    showToolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    showToolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String[] previousMainMenuArray = utils.getStringArrayScriptVar(self, "qaquest.qaquestSubMenu");
                    String previousSelection = previousMainMenuArray[idx];
                    if (previousSelection.equals("Complete this quest"))
                    {
                        String questString = getQuestString(self);
                        if (!questString.equals(""))
                        {
                            boolean completeCorrectly = qa.completeActiveQuest(self, questString);
                            if (!completeCorrectly)
                            {
                                sendSystemMessageTestingOnly(self, "Something went wrong when completing the quest.");
                            }
                            showToolMainMenu(self);
                        }
                    }
                    else if (previousSelection.equals("Remove this quest"))
                    {
                        String questString = getQuestString(self);
                        if (!questString.equals("") && !questString.equals("Error"))
                        {
                            boolean clearCorrectly = qa.clearQuest(self, questString);
                            if (!clearCorrectly)
                            {
                                sendSystemMessageTestingOnly(self, "Something went wrong when attempting to clear the quest.");
                            }
                            showToolMainMenu(self);
                        }
                    }
                    else if (previousSelection.equals("Remove and re-grant this quest"))
                    {
                        String questString = getQuestString(self);
                        if (!questString.equals("") && !questString.equals("Error"))
                        {
                            qa.clearQuest(self, questString);
                            if(questString.contains("spacequest"))
                            {
                                qa.grantSpaceQuest(self, qa.getSpaceQuestType(self, questString), questString);
                            }
                            else
                            {
                                qa.grantGroundQuest(self, questString);
                            }
                            showToolMainMenu(self);
                        }
                    }
                    else 
                    {
                        String[] menuOptions = new String[2];
                        if(previousSelection.contains("(A)"))
                        {
                            menuOptions[0] = "Complete this Task";
                            menuOptions[1] = "Fail this Task";
                        }
                        else if (previousSelection.contains("(I)"))
                        {
                            menuOptions[0] = "Activate this Task";
                            menuOptions[1] = "Activate this Task & Complete All Other Active Tasks";
                        }
                        else if (previousSelection.contains("(C)"))
                        {
                            menuOptions[0] = "Clear this Task's Completion History";
                            menuOptions[1] = "Clear this Task and Activate It";
                        }
                        else
                        {
                            sendSystemMessageTestingOnly(self, "Something went wrong... aborting...");
                            return SCRIPT_CONTINUE;
                        }
                        utils.setScriptVar(self, "qaquest.taskToHandle", previousSelection);
                        qa.refreshMenu(self, TASK_MENU_PROMPT, TASK_MENU_TITLE, menuOptions, "handleQuestTaskOptions", true, "qaquest.pid", "qaquest.qaquestSubMenu");
                    }
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleQuestTaskOptions(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "qaquest.pid"))
            {
                qa.checkParams(params, "qaquest");
                int idx = sui.getListboxSelectedRow(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    showToolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else if (btn == sui.BP_REVERT)
                {
                    showToolMainMenu(self);
                    return SCRIPT_CONTINUE;
                }
                else
                {
                    String[] previousMainMenuArray = utils.getStringArrayScriptVar(self, "qaquest.qaquestSubMenu");
                    String previousSelection = previousMainMenuArray[idx];
                    int taskId = Integer.parseInt(utils.getStringScriptVar(self, "qaquest.taskToHandle").split(":")[0]);
                    switch(previousSelection)
                    {
                        case "Complete this Task":
                            groundquests.completeTask(self, getQuestString(self), taskId);
                            sendSystemMessageTestingOnly(self, "Completing task "+taskId+" for quest "+getQuestString(self));
                            break;
                        case "Fail this Task":
                            groundquests.failTask(self, getQuestString(self), taskId);
                            sendSystemMessageTestingOnly(self, "Failing task "+taskId+" for quest "+getQuestString(self));
                            break;
                        case "Activate this Task":
                            groundquests.activateTask(self, getQuestString(self), taskId);
                            sendSystemMessageTestingOnly(self, "Activating task "+taskId+" for quest "+getQuestString(self));
                            break;
                        case "Activate this Task & Complete All Other Active Tasks":
                            String[] tasks = utils.getStringArrayScriptVar(self, "qaquest.allQuestTasks");
                            if(tasks != null && tasks.length > 0)
                            {
                                for(String task : tasks)
                                {
                                    if(task.contains("(A)"))
                                    {
                                        int tempId = Integer.parseInt(task.split(":")[0]);
                                        groundquests.completeTask(self, getQuestString(self), tempId);
                                        sendSystemMessageTestingOnly(self, "Completing task "+tempId+" for quest "+getQuestString(self));
                                    }
                                }
                            }
                            groundquests.activateTask(self, getQuestString(self), taskId);
                            sendSystemMessageTestingOnly(self, "Activating task "+taskId+" for quest "+getQuestString(self));
                            break;
                        case "Clear this Task's Completion History":
                            groundquests.clearTask(self, getQuestString(self), taskId);
                            sendSystemMessageTestingOnly(self, "Clearing task "+taskId+" for quest "+getQuestString(self));
                            break;
                        case "Clear this Task and Activate It":
                            groundquests.clearTask(self, getQuestString(self), taskId);
                            sendSystemMessageTestingOnly(self, "Clearing task "+taskId+" for quest "+getQuestString(self));
                            groundquests.activateTask(self, getQuestString(self), taskId);
                            sendSystemMessageTestingOnly(self, "Activating task "+taskId+" for quest "+getQuestString(self));
                            break;
                    }
                    cleanAllScriptVars(self);
                    showToolMainMenu(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAddGroundQuestManually(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "qaquest.pid"))
            {
                qa.checkParams(params, "qaquest");
                String stringEntry = sui.getInputBoxText(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    showToolMainMenu(self);
                    return SCRIPT_OVERRIDE;
                }
                if (stringEntry.equals("") || stringEntry == null)
                {
                    qa.createInputBox(self, MANUAL_ADD_GROUND_PROMPT, MANUAL_ADD_GROUND_TITLE, "handleAddQuestManually", "qaquest.pid");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    qa.grantGroundQuest(self, stringEntry);
                    sendSystemMessageTestingOnly(self, "If the Quest wasn't added, check the spelling of the quest string");
                    showToolMainMenu(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleAddSpaceQuestManually(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "qaquest.pid"))
            {
                qa.checkParams(params, "qaquest");
                String stringEntry = sui.getInputBoxText(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    showToolMainMenu(self);
                    return SCRIPT_OVERRIDE;
                }
                if (stringEntry.equals("") || stringEntry == null)
                {
                    qa.createInputBox(self, MANUAL_ADD_SPACE_PROMPT, MANUAL_ADD_SPACE_TITLE, "handleAddQuestManually", "qaquest.pid");
                    return SCRIPT_CONTINUE;
                }
                else 
                {
                    String questType = qa.getSpaceQuestType(self, stringEntry);
                    if (!questType.equals("Error"))
                    {
                        sendSystemMessageTestingOnly(self, "questType: " + questType);
                        String questName = qa.getSpaceQuestName(self, stringEntry);
                        if (!questName.equals("Error"))
                        {
                            sendSystemMessageTestingOnly(self, "questType: " + questName);
                            qa.grantSpaceQuest(self, questType, questName);
                            sendSystemMessageTestingOnly(self, "If the Quest wasn't added, check the spelling of the quest string");
                        }
                        else 
                        {
                            sendSystemMessageTestingOnly(self, "The space quest string usually starts with spacequest/<questType>/");
                        }
                    }
                    else 
                    {
                        sendSystemMessageTestingOnly(self, "The space quest string usually starts with spacequest/");
                    }
                    showToolMainMenu(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int handleSendQuestSignal(obj_id self, dictionary params) throws InterruptedException
    {
        if (isGod(self))
        {
            if (utils.hasScriptVar(self, "qaquest.pid"))
            {
                qa.checkParams(params, "qaquest");
                String stringEntry = sui.getInputBoxText(params);
                int btn = sui.getIntButtonPressed(params);
                if (btn == sui.BP_CANCEL)
                {
                    cleanAllScriptVars(self);
                    showToolMainMenu(self);
                    return SCRIPT_OVERRIDE;
                }
                if (stringEntry == null || stringEntry.equals(""))
                {
                    qa.createInputBox(self, SEND_SIGNAL_PROMPT, SEND_SIGNAL_TITLE, "handleSendQuestSignal", "qaquest.pid");
                    return SCRIPT_CONTINUE;
                }
                else
                {
                    groundquests.sendSignal(self, stringEntry);
                    sendSystemMessageTestingOnly(self, "Sending quest signal "+stringEntry+" to your player...");
                    showToolMainMenu(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int revokeQuestHandler(obj_id self, dictionary params) throws InterruptedException
    {
        String textData = params.getString("Prompt.lblPrompt.LocalText");
        sendSystemMessageTestingOnly(self, "" + params);
        forceCloseSUIPage(params.getInt("pageId"));
        if (!textData.equals(""))
        {
            textData = textData.trim();
            utils.setScriptVar(self, "qaquest.textData", textData);
            String[] allData = split(textData, ';');
            for (int i = 0; i < allData.length; i++)
            {
                allData[i] = allData[i].trim();
            }
            if (allData.length > 0)
            {
                iterateClearQuestStrings(self, allData);
                showToolMainMenu(self);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "No arguments received. Cancelling macro.");
                showToolMainMenu(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int grantQuestHandler(obj_id self, dictionary params) throws InterruptedException
    {
        String widgetName = params.getString("eventWidgetName");
        String textData = params.getString("Prompt.lblPrompt.LocalText");
        if (widgetName.equalsIgnoreCase("%button0%"))
        {
            showToolMainMenu(self);
        }
        else if (widgetName.equalsIgnoreCase("%button1%"))
        {
            textData = textData.trim();
            utils.setScriptVar(self, "qaquest.textData", textData);
            String[] allData = split(textData, ';');
            for (int i = 0; i < allData.length; i++)
            {
                allData[i] = allData[i].trim();
            }
            if (allData.length > 0)
            {
                iterateGrantQuestStrings(self, allData, false);
                showToolMainMenu(self);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "No arguments received. Cancelling macro.");
                showToolMainMenu(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int grantAndCompleteQuestHandler(obj_id self, dictionary params) throws InterruptedException
    {
        String widgetName = params.getString("eventWidgetName");
        String textData = params.getString("Prompt.lblPrompt.LocalText");
        if (widgetName.equalsIgnoreCase("%button0%"))
        {
            showToolMainMenu(self);
        }
        else if (widgetName.equalsIgnoreCase("%button1%"))
        {
            textData = textData.trim();
            utils.setScriptVar(self, "qaquest.textData", textData);
            String[] allData = split(textData, ';');
            for (int i = 0; i < allData.length; i++)
            {
                allData[i] = allData[i].trim();
            }
            if (allData.length > 0)
            {
                iterateGrantQuestStrings(self, allData, true);
                showToolMainMenu(self);
            }
            else 
            {
                sendSystemMessageTestingOnly(self, "No arguments received. Cancelling macro.");
                showToolMainMenu(self);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int delay(obj_id self, dictionary params) throws InterruptedException
    {
        return SCRIPT_CONTINUE;
    }
    public void showToolMainMenu(obj_id self) throws InterruptedException
    {
        try
        {
            String[] allQuests = qa.getAllQuests(self);
            String[] combinedMenu = new String[allQuests.length + QUEST_TOOL_MENU.length];
            System.arraycopy(allQuests, 0, combinedMenu, 0, allQuests.length);
            System.arraycopy(QUEST_TOOL_MENU, 0, combinedMenu, allQuests.length, QUEST_TOOL_MENU.length);
            qa.refreshMenu(self, QUEST_TOOL_PROMPT, QUEST_TOOL_TITLE, combinedMenu, "handleMainMenuOptions", true, "qaquest.pid", "qaquest.qaquestMenu");
        }
        catch(Exception e)
        {
            qa.refreshMenu(self, QUEST_TOOL_PROMPT + "\n\nNo quests found on character", QUEST_TOOL_TITLE, QUEST_TOOL_MENU, "handleMainMenuOptions", true, "qaquest.pid", "qaquest.qaquestMenu");
        }
    }
    public void cleanAllScriptVars(obj_id self) throws InterruptedException
    {
        utils.removeScriptVarTree(self, "qaquest");
    }
    public boolean getTestQuests(obj_id self) throws InterruptedException
    {
        for (String questDemoQuest : QUEST_DEMO_QUESTS) {
            if (questDemoQuest.indexOf("spacequest/") == 0) {
                boolean successGrant = qa.evalSpaceQuestThenGrant(self, questDemoQuest);
            } else {
                qa.grantGroundQuest(self, questDemoQuest);
            }
        }
        return true;
    }
    public String getQuestString(obj_id self) throws InterruptedException
    {
        if (utils.hasScriptVar(self, "qaquest.questString"))
        {
            String questStringAndName = utils.getStringScriptVar(self, "qaquest.questString");
            if (!questStringAndName.equals(""))
            {
                int whiteSpaceIndex = questStringAndName.indexOf(" -");
                String questCodeString = questStringAndName.substring(4, whiteSpaceIndex);
                return questCodeString;
            }
        }
        return "Error";
    }
    public String[] getCorrectMenu(obj_id self, String questStringAndName) throws InterruptedException
    {
        if (!questStringAndName.equals(""))
        {
            if (questStringAndName.indexOf("(C") == 0)
            {
                return QUEST_ALT_MENU;
            }
            else 
            {
                return QUEST_MAIN_MENU;
            }
        }
        return QUEST_MAIN_MENU;
    }
    public void bulkGrantAndCompleteQuestUi(obj_id self, String textData) throws InterruptedException
    {
        String uiTitle = "Quest Macro";
        int page = createSUIPage("/Script.messageBox", self, self);
        if (!textData.equals(""))
        {
            setSUIProperty(page, "Prompt.lblPrompt", "LocalText", textData);
        }
        else 
        {
            setSUIProperty(page, "Prompt.lblPrompt", "LocalText", "");
        }
        setSUIProperty(page, "bg.caption.lblTitle", "Text", uiTitle);
        setSUIProperty(page, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(page, "Prompt.lblPrompt", "GetsInput", "true");
        setSUIProperty(page, "Prompt.lblPrompt", "Paste", "true");
        setSUIProperty(page, "btnCancel", "Visible", "true");
        setSUIProperty(page, "btnRevert", "Visible", "true");
        setSUIProperty(page, "btnRevert", sui.PROP_TEXT, "Bulk Clear");
        setSUIProperty(page, "btnOk", sui.PROP_TEXT, "Bulk Complete");
        subscribeToSUIPropertyForEvent(page, sui_event_type.SET_onClosedOk, "%button1%", "Prompt.lblPrompt", "LocalText");
        subscribeToSUIPropertyForEvent(page, sui_event_type.SET_onButton, "btnRevert", "Prompt.lblPrompt", "LocalText");
        subscribeToSUIEvent(page, sui_event_type.SET_onClosedOk, "%button1%", "grantAndCompleteQuestHandler");
        subscribeToSUIEvent(page, sui_event_type.SET_onButton, "btnRevert", "revokeQuestHandler");
        subscribeToSUIEvent(page, sui_event_type.SET_onClosedCancel, "%button0%", "grantAndCompleteQuestHandler");
        showSUIPage(page);
        flushSUIPage(page);
    }
    public void bulkGrantQuestUi(obj_id self, String textData) throws InterruptedException
    {
        String uiTitle = "Quest Macro";
        int page = createSUIPage("/Script.messageBox", self, self);
        if (!textData.equals(""))
        {
            setSUIProperty(page, "Prompt.lblPrompt", "LocalText", textData);
        }
        else 
        {
            setSUIProperty(page, "Prompt.lblPrompt", "LocalText", "");
        }
        setSUIProperty(page, "bg.caption.lblTitle", "Text", uiTitle);
        setSUIProperty(page, "Prompt.lblPrompt", "Editable", "true");
        setSUIProperty(page, "Prompt.lblPrompt", "GetsInput", "true");
        setSUIProperty(page, "Prompt.lblPrompt", "Paste", "true");
        setSUIProperty(page, "btnCancel", "Visible", "true");
        setSUIProperty(page, "btnRevert", "Visible", "true");
        setSUIProperty(page, "btnRevert", sui.PROP_TEXT, "Bulk Clear");
        setSUIProperty(page, "btnOk", sui.PROP_TEXT, "Bulk Grant");
        subscribeToSUIPropertyForEvent(page, sui_event_type.SET_onClosedOk, "%button1%", "Prompt.lblPrompt", "LocalText");
        subscribeToSUIPropertyForEvent(page, sui_event_type.SET_onButton, "btnRevert", "Prompt.lblPrompt", "LocalText");
        subscribeToSUIEvent(page, sui_event_type.SET_onClosedOk, "%button1%", "grantQuestHandler");
        subscribeToSUIEvent(page, sui_event_type.SET_onButton, "btnRevert", "revokeQuestHandler");
        subscribeToSUIEvent(page, sui_event_type.SET_onClosedCancel, "%button0%", "grantQuestHandler");
        showSUIPage(page);
        flushSUIPage(page);
    }
    public void iterateGrantQuestStrings(obj_id self, String[] allData, boolean completeFlag) throws InterruptedException
    {
        if (allData.length > 0)
        {
            for (String allDatum : allData) {
                if (!allDatum.equals("")) {
                    if (allDatum.indexOf("spacequest/") == 0) {
                        boolean questAttained = qa.evalSpaceQuestThenGrant(self, allDatum);
                        if (!questAttained) {
                            sendSystemMessageTestingOnly(self, "Quest " + allDatum + " could not be granted.");
                        }
                        if (completeFlag) {
                            messageTo(self, "delay", null, 1, false);
                            boolean completed = qa.completeActiveQuest(self, allDatum);
                            if (!completed) {
                                sendSystemMessageTestingOnly(self, "Quest " + allDatum + " could not be completed.");
                            }
                        }
                    } else if (allDatum.indexOf("quest/") == 0) {
                        qa.grantGroundQuest(self, allDatum);
                        if (completeFlag) {
                            messageTo(self, "delay", null, 1, false);
                            boolean completed = qa.completeActiveQuest(self, allDatum);
                            if (!completed) {
                                sendSystemMessageTestingOnly(self, "Quest " + allDatum + " could not be granted.");
                            }
                        }
                    } else {
                        sendSystemMessageTestingOnly(self, "Unknown quest string: " + allDatum);
                    }
                }
            }
        }
    }
    public void iterateClearQuestStrings(obj_id self, String[] allData) throws InterruptedException
    {
        if (allData.length > 0)
        {
            for (String allDatum : allData) {
                if (!allDatum.equals("")) {
                    if (allDatum.indexOf("spacequest/") == 0) {
                        String questName = qa.getSpaceQuestName(self, allDatum);
                        if (!questName.equals("")) {
                            String questType = qa.getSpaceQuestType(self, allDatum);
                            if (!questType.equals("")) {
                                boolean completed = qa.clearQuest(self, allDatum);
                                messageTo(self, "delay", null, 1, false);
                                if (!completed) {
                                    sendSystemMessageTestingOnly(self, "Quest " + allDatum + " could not be cleared.");
                                }
                            } else {
                                sendSystemMessageTestingOnly(self, "Unknown quest string: " + allDatum);
                            }
                        } else {
                            sendSystemMessageTestingOnly(self, "Unknown quest string: " + allDatum);
                        }
                    } else if (allDatum.indexOf("quest/") == 0) {
                        boolean completed = qa.clearQuest(self, allDatum);
                        messageTo(self, "delay", null, 1, false);
                        if (!completed) {
                            sendSystemMessageTestingOnly(self, "Quest " + allDatum + " could not be cleared.");
                        }
                    } else {
                        sendSystemMessageTestingOnly(self, "Unknown quest string: " + allDatum);
                    }
                }
            }
        }
    }
}
