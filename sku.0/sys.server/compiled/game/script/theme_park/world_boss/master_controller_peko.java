package script.theme_park.world_boss;

/*
 * Copyright © SWG:Resurgence 2023.
 *
 * Unauthorized usage, viewing or sharing of this file is prohibited.
 */

import script.library.*;
import script.location;
import script.obj_id;

public class master_controller_peko extends script.base_script
{
    public static final String VOLUME_NAME = "aggressive_area";
    public String[] SQUAWK_MSGS = {
            "<LOUDS AVIAN NOISES>",
            "<ANGRY AVIAN NOISES>",
            "<UPSET AVIAN NOISES>",
            "<DISPLEASED AVIAN NOISES>",
            "<RIGHTEOUS AVIAN NOISES>",
            "<DISTURBING AVIAN NOISES>",
    };

    public int OnAttach(obj_id self) throws InterruptedException
    {
        obj_id tatooine = getPlanetByName("tatooine");
        if (hasObjVar(tatooine, "dungeon_finder.world_boss.peko"))
        {
            removeObjVar(tatooine, "dungeon_finder.world_boss.peko");
        }
        setObjVar(tatooine, "dungeon_finder.world_boss.peko", "Active");
        resurgence.doWorldBossAnnounce(self, resurgence.WORLD_BOSS_PEKO);
        return SCRIPT_CONTINUE;
    }

    public int OnDeath(obj_id self, obj_id killer, obj_id corpseId) throws InterruptedException
    {
        obj_id[] allPlayersNearby = getAllPlayers(getLocation(self), 128.0f);
        if (allPlayersNearby != null)
        {
            for (obj_id nearby : allPlayersNearby)
            {
                groundquests.sendSignal(nearby, "collectedPekoPekoAlbatross");
            }
        }
        return SCRIPT_CONTINUE;
    }

    public int OnIncapacitated(obj_id self, obj_id killer) throws InterruptedException
    {
        if (isGod(killer))
        {
            return SCRIPT_CONTINUE;
        }
        obj_id tatooine = getPlanetByName("tatooine");
        if (hasObjVar(tatooine, "dungeon_finder.world_boss.peko"))
        {
            removeObjVar(tatooine, "dungeon_finder.world_boss.peko");
        }
        setObjVar(tatooine, "dungeon_finder.world_boss.peko", "Inactive");
        resurgence.doWorldBossDeathMsg(self); //only thing that is needed.
        return SCRIPT_CONTINUE;
    }

    public int OnCreatureDamaged(obj_id self, obj_id attacker, obj_id wpn, int[] damage) throws InterruptedException
    {
        obj_id[] players = getPlayerCreaturesInRange(self, 64.0f);
        int health = getHealth(self);
        int maxHealth = getMaxHealth(self);
        int percentHealth = (health * 100) / maxHealth;
        if (attacker == self)
        {
            return SCRIPT_CONTINUE;
        }
        if (!utils.hasScriptVar(self, "chirp"))
        {
            chat.chat(self, SQUAWK_MSGS[rand(0, SQUAWK_MSGS.length - 1)]);
            utils.setScriptVar(self, "chirp", 1);
        }
        if (percentHealth <= 75)
        {
            if (!utils.hasScriptVar(self, "hasSpawned"))
            {
                resurgence.createCircleSpawn(self, self, "peko_peko", 12, 24);
                utils.setScriptVar(self, "hasSpawned", 1);
                return SCRIPT_CONTINUE;
            }
        }
        if (percentHealth <= 50)
        {
            if (!utils.hasScriptVar(self, "hasKnockedBack"))
            {
                chat.chat(self, SQUAWK_MSGS[rand(0, SQUAWK_MSGS.length - 1)]);
                staggerPlayers(self, players);
                utils.setScriptVar(self, "hasKnockedBack", 1);
            }
        }
        if (percentHealth <= 25)
        {
            if (!utils.hasScriptVar(self, "hasDisarmed"))
            {
                chat.chat(self, SQUAWK_MSGS[rand(0, SQUAWK_MSGS.length - 1)]);
                for (obj_id who : players)
                {
                    broadcast(who, "The most recent attack from " + getFirstName(attacker) + " caused the Peko-Peko Empress to become enraged to try to know everyone's weapon out of their hands.");
                }
                utils.setScriptVar(self, "hasDisarmed", 1);
            }
        }
        if (percentHealth <= 10)
        {
            if (!utils.hasScriptVar(self, "hasLastStand"))
            {
                buff.removeAllBuffs(self);
                resurgence.createCircleSpawn(self, self, "peko_peko_albatross_high", 2, 12);
                staggerPlayers(self, players);
                for (obj_id who : players)
                {
                    broadcast(who, "The Mutated Peko-Peko Empress has called upon her whelps to aid her in her final stand!");
                }
                utils.setScriptVar(self, "hasLastStand", 1);
            }
        }
        return SCRIPT_CONTINUE;
    }

    public void staggerPlayers(obj_id self, obj_id[] targets) throws InterruptedException
    {
        playClientEffectObj(targets, "clienteffect/cr_bodyfall_huge.cef", self, "");
        if (targets == null)
        {
            return;
        }
        for (obj_id iTarget : targets)
        {
            int playerHealth = getHealth(iTarget);
            int playerAction = getAction(iTarget);
            int statDrain = playerHealth / 2;
            int actionDrain = playerAction / 2;
            setHealth(iTarget, playerHealth - statDrain);
            setAction(iTarget, playerAction - actionDrain);
            location stagger = getLocation(iTarget);
            stagger.x = stagger.x + rand(-64.0f, 64.0f);
            stagger.z = stagger.z + rand(-64.0f, 64.0f);
            stagger.y = getHeightAtLocation(stagger.x, stagger.z);
            stagger.area = getCurrentSceneName();
            warpPlayer(iTarget, stagger.area, stagger.x, stagger.y, stagger.z, null, 0, 0, 0);
            broadcast(iTarget, "The wind from the Mutated Peko-Peko's wings knocked you back!");
            faceTo(iTarget, self);
        }
    }
}
