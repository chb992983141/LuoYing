/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.fighter.game.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import java.util.List;
import name.huliqing.fighter.Factory;
import name.huliqing.fighter.object.actor.Actor;
import name.huliqing.fighter.constants.IdConstants;
import name.huliqing.fighter.data.GameData;
import name.huliqing.fighter.enums.Sex;
import name.huliqing.fighter.game.service.ActorService;
import name.huliqing.fighter.game.service.SkillService;
import name.huliqing.fighter.game.service.StateService;
import name.huliqing.fighter.enums.SkillType;
import name.huliqing.fighter.game.service.SceneService;
import name.huliqing.fighter.object.env.CameraChaseEnv;
import name.huliqing.fighter.object.game.Game;
import name.huliqing.fighter.object.scene.SceneUtils;
import name.huliqing.fighter.utils.CollisionChaseCamera;

/**
 * 测试
 * @author huliqing
 */
public class LabPlayState extends NetworkPlayState {
    
    private final ActorService actorService = Factory.get(ActorService.class);
    private final StateService stateService = Factory.get(StateService.class);
    private final SkillService skillService = Factory.get(SkillService.class);
    private final SceneService sceneService = Factory.get(SceneService.class);
    
    private Actor npc1;
    private Actor npc2;
    private final int level = 60;
    
    // 场景跟随相机
    protected CollisionChaseCamera chaseCamera;
    
    private final String[] actorIds = new String[] {
        IdConstants.ACTOR_HARD
        ,IdConstants.ACTOR_HARD
        ,IdConstants.ACTOR_SINBAD
    };

    public LabPlayState(Application app, GameData gameData) {
        super(app, gameData);
    }
    
    @Override
    public void initialize(AppStateManager stateManager, final Application app) {
        super.initialize(stateManager, app);
    }
    
    @Override
    public void changeGameState(GameState newGameState) {
        super.changeGameState(newGameState);
        gameState.getGame().addListener(new Game.GameListener() {
            @Override
            public void onGameStarted(Game game) {
                // 载入NPC
                npc1 = loadActor(FastMath.nextRandomInt(0, actorIds.length - 1));
                npc1.setLocation(new Vector3f(10, 1, 0));
                actorService.setGroup(npc1, 1);

                npc2 = loadActor(FastMath.nextRandomInt(0, actorIds.length - 1));
                npc2.setLocation(new Vector3f(-10, 1, 0));
                actorService.setGroup(npc2, 2);

                addObject(npc1.getModel(), false);
                addObject(npc2.getModel(), false);

                gameState.getTeamView().setMainActor(npc1);
                gameState.setTarget(npc2);
                
                CameraChaseEnv cce = SceneUtils.findEnv(gameState.getScene(), CameraChaseEnv.class);
                if (cce != null) {
                    cce.setChase(npc1.getModel());
                }
            }
        });
        
    }
    
    private Actor loadActor(int idIndex) {
        Actor actor;
        if (idIndex <= 1) {
            actor = actorService.loadActor(actorIds[idIndex]);
            actorService.setName(actor, actorService.createRandomName(Sex.female));
        } else {
            actor = actorService.loadActor(actorIds[idIndex]);
        }
        actor.setLocation(new Vector3f(-5, 2, 0));
        actorService.setLevel(actor, level);
        skillService.playSkill(actor, skillService.getSkill(actor, SkillType.wait).getId(), false);
        return actor;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }

    @Override
    public List<ConnData> getClients() {
        return null;
    }

    @Override
    public void kickClient(int connId) {
        // ignore
    }

    @Override
    protected void onSelectPlayer(String actorId, String actorName) {
        // ignore
    }
}
