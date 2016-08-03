/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.object.game.impl;

import com.jme3.util.TempVars;
import java.util.List;
import name.huliqing.core.Factory;
import name.huliqing.core.constants.IdConstants;
import name.huliqing.core.constants.ResConstants;
import name.huliqing.core.data.GameLogicData;
import name.huliqing.core.data.TalentData;
import name.huliqing.core.enums.MessageType;
import name.huliqing.core.enums.SkillType;
import name.huliqing.core.mvc.network.ActorNetwork;
import name.huliqing.core.mvc.network.PlayNetwork;
import name.huliqing.core.mvc.service.ActorService;
import name.huliqing.core.mvc.service.LogicService;
import name.huliqing.core.mvc.service.PlayService;
import name.huliqing.core.mvc.service.SkillService;
import name.huliqing.core.mvc.service.TalentService;
import name.huliqing.core.mvc.service.ViewService;
import name.huliqing.core.loader.Loader;
import name.huliqing.core.logic.scene.ActorBuildLogic;
import name.huliqing.core.xml.DataFactory;
import name.huliqing.core.object.actor.Actor;
import name.huliqing.core.object.actorlogic.PositionActorLogic;
import name.huliqing.core.object.gamelogic.AbstractGameLogic;
import name.huliqing.core.object.view.TextView;
import name.huliqing.core.object.view.View;

/**
 *
 * @author huliqing
 * @param <T>
 */
public class SurvivalBoss<T extends GameLogicData> extends AbstractGameLogic<T> {
    private final ActorService actorService = Factory.get(ActorService.class);
    private final TalentService talentService = Factory.get(TalentService.class);
    private final ViewService viewService = Factory.get(ViewService.class);
    private final LogicService logicService = Factory.get(LogicService.class);
    private final SkillService skillService = Factory.get(SkillService.class);
    private final PlayService playService = Factory.get(PlayService.class);
    private final PlayNetwork playNetwork = Factory.get(PlayNetwork.class);
    private final ActorNetwork actorNetwork = Factory.get(ActorNetwork.class);
    
    private final SurvivalGame game;
    private final SurvivalLevelLogic levelLogic;
    private final ActorBuildLogic actorBuilder;
    
    private final int raptorLevel;
    private boolean raptorAdded;
    
    private final int sinbadLevel;
    private boolean sinbadAdded;
    
    private final int trexLevel;
    private boolean trexAdded;
    
    private final int bossLevel;
    private boolean bossAdded;
    
    private Actor boss;
    private boolean bossDead;
    
    public SurvivalBoss(SurvivalGame game, ActorBuildLogic actorBuilder, SurvivalLevelLogic levelLogic) {
        this.interval = 5;
        this.game = game;
        this.actorBuilder = actorBuilder;
        this.levelLogic = levelLogic;
        int bossLevelUp = levelLogic.getMaxLevel() / 4;
        raptorLevel = bossLevelUp;
        sinbadLevel = bossLevelUp * 2;
        trexLevel = bossLevelUp * 3;
        bossLevel = bossLevelUp * 4;
    }
    
    public boolean isBoosDead() {
        return bossDead;
    }

    @Override
    protected void doLogic(float tpf) {
        
        if (bossAdded && !bossDead) {
            if (actorService.isDead(boss)) {
                killAllEnemy();
                killAllLogic();
                View successView = viewService.loadView(IdConstants.VIEW_TEXT_SUCCESS);
                successView.setUseTime(-1);
                playNetwork.addView(successView);
                playNetwork.addMessage(ResConstants.TASK_SUCCESS, MessageType.item);
                bossDead = true;
            }
        }
        
        if (levelLogic.getLevel() >= bossLevel) {
            if (!bossAdded) {
                boss = loadBoss();
                bossAdded = true;
            }
            return;
        }
        
        if (levelLogic.getLevel() >= trexLevel) {
            if (!trexAdded) {
                actorBuilder.addId(IdConstants.ACTOR_TREX);
                trexAdded = true;
            }
            return;
        }
        
        if (levelLogic.getLevel() >= sinbadLevel) {
            if (!sinbadAdded) {
                actorBuilder.addId(IdConstants.ACTOR_SINBAD);
                sinbadAdded = true;
            }
            return;
        }
         
        if (levelLogic.getLevel() >= raptorLevel) {
            if (!raptorAdded) {
                actorBuilder.addId(IdConstants.ACTOR_RAPTOR);
                raptorAdded = true;
            }
        }

    }
    
    // 查询出BOSS所有的小弟并杀死,结束游戏
    private void killAllEnemy() {
        List<Actor> enemies = actorService.findNearestFriendly(boss, Float.MAX_VALUE, null);
        if (!enemies.isEmpty()) {
            for (Actor a : enemies) {
                actorNetwork.kill(a);
            }
        }
    }
    
    private void killAllLogic() {
        game.removeLogic(levelLogic);
        game.removeLogic(actorBuilder);
        game.removeLogic(this);
    }
    
    private Actor loadBoss() {
        Actor locBoss = actorService.loadActor(IdConstants.ACTOR_FAIRY);
        locBoss.setLocation(actorBuilder.getRandomPosition());
        actorService.setLevel(locBoss, levelLogic.getMaxLevel() + 5);
        actorService.setGroup(locBoss, game.GROUP_ENEMY);
        skillService.playSkill(locBoss, skillService.getSkill(locBoss, SkillType.wait).getId(), false);
        
        // 添加逻辑
        addPositionLogic(locBoss);
                
        // 为BOSS添加特殊天赋
        TalentData attack = DataFactory.createData(IdConstants.TALENT_ATTACK);
        TalentData defence = DataFactory.createData(IdConstants.TALENT_DEFENCE);
        TalentData defenceMagic = DataFactory.createData(IdConstants.TALENT_DEFENCE_MAGIC);
        TalentData lifeRestore = DataFactory.createData(IdConstants.TALENT_LIFE_RESTORE);
        TalentData moveSpeed = DataFactory.createData(IdConstants.TALENT_MOVE_SPEED);
        attack.setLevel(attack.getMaxLevel());
        defence.setLevel(defence.getMaxLevel());
        defenceMagic.setLevel(defenceMagic.getMaxLevel());
        moveSpeed.setLevel(moveSpeed.getMaxLevel());
        lifeRestore.setMaxLevel(15);
        lifeRestore.setLevel(15);
        talentService.addTalent(locBoss, attack);
        talentService.addTalent(locBoss, defence);
        talentService.addTalent(locBoss, defenceMagic);
        talentService.addTalent(locBoss, lifeRestore);
        talentService.addTalent(locBoss, moveSpeed);
        
        // 添加BOSS，并让所有小弟都跟随BOSS
        playNetwork.addActor(locBoss);
        List<Actor> xd = actorService.findNearestFriendly(locBoss, interval, null);
        if (!xd.isEmpty()) {
            for (Actor a : xd) {
                actorNetwork.setFollow(a, locBoss.getData().getUniqueId());
            }
        }
        
        TextView view = (TextView) viewService.loadView(IdConstants.VIEW_WARN);
        view.setText("Boss");
        playNetwork.addView(view);
        
        return locBoss;
    }
    
    private void addPositionLogic(Actor actor) {
        TempVars tv = TempVars.get();
        tv.vect1.set(game.treasurePos);
        tv.vect1.setY(playService.getTerrainHeight(tv.vect1.x, tv.vect1.z));
        PositionActorLogic runLogic = (PositionActorLogic) Loader.loadLogic(IdConstants.LOGIC_POSITION);
        runLogic.setInterval(3);
        runLogic.setPosition(tv.vect1);
        runLogic.setNearestDistance(game.nearestDistance);
        logicService.addLogic(actor, runLogic);
        tv.release();
    }
}
