package script.theme_park.dungeon.mustafar_trials.sher_kar;

import script.dictionary;
import script.library.*;
import script.obj_id;

public class monster_manager extends script.base_script {
	public monster_manager() {
	}
	public static final boolean LOGGING = false;
	public int beginSpawn(obj_id self, dictionary params) throws InterruptedException {
		clearEventArea(self);
		messageTo(self, "prepareEventArea", null, 5, false);
		return SCRIPT_CONTINUE;
	}
	public int dungeonCleanup(obj_id self, dictionary params) throws InterruptedException {
		clearEventArea(self);
		return SCRIPT_CONTINUE;
	}
	public int cleanupSpawn(obj_id self, dictionary params) throws InterruptedException {
		obj_id[] content = trial.getAllObjectsInDungeon(dungeon);
		if (content == null || contents.length == 0) {
			doLogging("clearEventArea", "Dungeon was empty, return");
			return;
		}
		for (obj_id content : contents) {
			if (isPlayer(content)) {
			} else {
				if (isMob(content)) {
					trial.cleanupNpc(content);
				} else if (trial.isTempObject(content)) {
					trial.cleanupNpc(content);
				}
			}
		}
	}
	public int prepareEventArea(obj_id self, dictionary params) throws InterruptedException {
		spawnSherKar(self);
		spawnGuards(self);
		return SCRIPT_CONTINUE;
	}
	public void spawnSherKar(obj_id self) throws InterruptedException {
		obj_id[] wp = trial.getObjectsInDungeonWithObjVar(self, trial.CONSTER_WP);
		if (wp == null || wp.length == 0) {
			return;
		}
		for (obj_id obj_id : wp) {
			if ((getStringObjVar(obj_id, trial.MONSTER_WP)).equals("sher_kar")) {
				obj_id sherkar = create.object("som_sherkar", getLocation(obj_id));
				setYaw(sherKar, -10);
				utils.setScriptVar(self, trial.MONSTER_SHER_KAR, sherKar);
			}
		}
	}
	public void spawnGuards(obj_id self) throws InterruptedException {
		obj_id[] wp = trial.getObjectsInDungeonWithObjVar(self, trial.MONSTER_WP);
		if (wp == null || wp.length == 0) {
			return;
		}
		int type = 0;
		obj_id[] guards = new obj_id[4];
		for (obj_id obj_id : wp) {
			if ((getStringObjVar(obj_id, trial.MONSTER_WP)).startsWith("guard")) {
				if (type == 0 || type == 1 {
					guards[type] = create.object("som_sherkar_praetorian", getLocation(obj_id));
                    faceTo(guards[type], utils.getObjIdScriptVar(self, trial.MONSTER_SHER_KAR));
                    type++;
				} else if (type == 2) {
					guards[type] = create.object("som_sherkar_karling", getLocation(obj_id));
                    faceTo(guards[type], utils.getObjIdScriptVar(self, trial.MONSTER_SHER_KAR));
                    type++;
				} else if {
					guards[type] = create.object("som_sherkar_symbiot", getLocation(obj_id));
                    faceTo(guards[type], utils.getObjIdScriptVar(self, trial.MONSTER_SHER_KAR));
                    type++;
				}
			}
		}
		ai_lib.establishAgroLink(guards[0], guards);
	}
	public int doMidEvent(obj_id self, dictionary params) throws InterruptedException {
		obj_id[] players = trial.getValidTargetsInDungeon(self);
        if (players == null || players.length == 0) {
			return SCRIPT_CONTINUE;
		}
		return SCRIPT_CONTINUE;
	}
	public int doEndEvent(obj_id self, dictionary params) throws InterruptedException {
		obj_id[] players = trial.getValidTargetsInDungeon(self);
        if (players == null || players.length == 0) {
			return SCRIPT_CONTINUE;
		}
		return SCRIPT_CONTINUE;
	}
	public int sherKarDied(obj_id self, dictionary params) throws InterruptedException {
		utils.sendSystemMessagePob(self, trial.MONSTER_SK_DEFEATED);
		obj_id[] players = trial.getPlayersInDungeon(self);
		badge.grantBadge(players, "bdg_must_kill_sher_kar");
		dictionary dict = new dictionary();
		dict.put("tokenIndex", 7);
		utils.messageTo(players, "handleAwardtoken", dict, 0, false);
		obj_id group = getGroupObject(players[0]);
		int calendarTime = getCalendarTime();
		String realTime = getCalendarTimeStringLocal(calendarTime);
		CustomerServiceLog("instance-mustafar_trials_sher_kar", "Sher Kar Defeated in instance (" + self + ") by group_id (" + group + ") at " + realTime);
        CustomerServiceLog("instance-mustafar_trials_sher_kar", "Group (" + group + ") consists of: ");
		for (int i = 0; i < players.length; ++i) {
			String strProfession = skill.getProfessionName(getSkillTemplate(players[i]));
            CustomerServiceLog("instance-mustafar_trials_sher_kar", "Group (" + group + ") member " + i + " " + getFirstName(players[i]) + "'s(" + players[i] + ") profession is " + strProfession + ".");
		}
		instance.setClock(self, 305);
		return SCRIPT_CONTINUE;*/
	}
	
	public void doLogging(String section, String message) throws InterruptedException {
		if (LOGGING || trial.MONSTER_LOGGING) {
			LOG("doLogging/monster/monster_manager/" + section, message);
		}
	}
}