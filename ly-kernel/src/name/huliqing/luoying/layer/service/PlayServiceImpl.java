/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.luoying.layer.service;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import name.huliqing.luoying.LuoYing;
import name.huliqing.luoying.Factory;
import name.huliqing.luoying.object.entity.Entity;
import name.huliqing.luoying.object.entity.TerrainEntity;
import name.huliqing.luoying.object.game.Game;
import name.huliqing.luoying.object.scene.Scene;

/**
 *
 * @author huliqing
 */
public class PlayServiceImpl implements PlayService {
    private static final Logger LOG = Logger.getLogger(PlayServiceImpl.class.getName());
    
    // 当前游戏实例
    private static Game game;
    
    private ActorService actorService;
    private SkillService skillService;
    private LogicService logicService;
    private ActionService actionService;
    private SkinService skinService;
    
    @Override
    public void inject() {
        actorService = Factory.get(ActorService.class);
        skillService = Factory.get(SkillService.class);
        logicService = Factory.get(LogicService.class);
        actionService = Factory.get(ActionService.class);
        skinService = Factory.get(SkinService.class);
    }

    @Override
    public void registerGame(Game game) {
        PlayServiceImpl.game = game;
    }
    
    @Override
    public Game getGame() {
        if (game == null) {
            LOG.log(Level.WARNING, "Game not found or not registered! "
                    + "Use playService to registerGame or use GameAppState to attach to StateManager.");
        }
        return game;
    }

    @Override
    public void addEntity(Entity entity) {
        game.getScene().addEntity(entity);
    }
    
    @Override
    public void addGuiEntity(Entity entity) {
        game.getGuiScene().addEntity(entity);
    }
    
    @Override
    public void addEntity(Scene scene, Entity entity) {
        scene.addEntity(entity);
    }

    @Override
    public void removeEntity(Entity entity) {
        entity.getScene().removeEntity(entity);
    }

    @Override
    public Entity getEntity(long entityId) {
        Entity entity = game.getScene().getEntity(entityId);
        if (entity != null) {
            return entity;
        }
        return game.getGuiScene().getEntity(entityId);
    }

    @Override
    public <T extends Entity> List<T> getEntities(Class<T> type, List<T> store) {
        return game.getScene().getEntities(type, store);
    }
    
    @Override
    public float getScreenWidth() {
        return LuoYing.getSettings().getWidth();
    }

    @Override
    public float getScreenHeight() {
        return LuoYing.getSettings().getHeight();
    }

    @Override
    public Vector3f getTerrainHeight(Scene scene, float x, float z) {
        // 在场景载入完毕之后将植皮位置移到terrain节点的上面。
        List<TerrainEntity> sos = scene.getEntities(TerrainEntity.class, new ArrayList<TerrainEntity>());
        Vector3f result = null;
        for (TerrainEntity terrain : sos) {
            Vector3f tp = terrain.getHeight(x, z);
            if (tp != null) {
                if (result == null || tp.y > result.y) {
                    result = tp;
                }
            }
        }
        return result;
    }
    
    @Override
    public void attack(Entity actor, Entity target) {
        // remove20161102
//         // 如果角色已经死亡
//        if (actorService.isDead(actor)) {
//            return;
//        }
        
        // 执行战斗行为
        if (target != null) {
            actionService.playFight(actor, target, null);
        }

        // remove20161103
//        // 打开或关闭侦察敌人的逻辑,autoDetect不需要广播到客户端，因为客户端不会有
//        // 逻辑
//        logicService.setAutoDetect(actor, skinService.isWeaponTakeOn(actor));
    }
    
    
    
    
    // --------------------------------------------------------------------------------------------------------------------------------
//    @Override
//    public void addPlayObject(PlayObject playObject) {
//        Ly.getPlayState().addObject(playObject, false);
//    }
//
//    @Override
//    public void removePlayObject(PlayObject playObject) {
//        Ly.getPlayState().removeObject(playObject);
//    }
    
//    @Override
//    public void addObject(Object object, boolean gui) {
//        if (object instanceof Actor) {
//            addActor((Actor) object);
//            return;
//        }
//        
//        if (object instanceof Effect) {
//            addEffect((Effect) object);
//            return;
//        }
//        
//        if (object instanceof Bullet) {
//            addBullet((Bullet) object);
//            return;
//        }
//        
//        if (object instanceof View) {
//            addView((View) object);
//            return;
//        }
//        
//        // 最后判断是不是Actor
//        if (object instanceof Spatial) {
//            Spatial ss = (Spatial) object;
//            if (ss.getControl(Actor.class) != null) {
//                addActor(ss.getControl(Actor.class));
//                return;
//            }
//        }
//        
//        Ly.getPlayState().addObject(object, gui);
//    }
//
//    @Override
//    public void addObject(Object object) {
//        addObject(object, false);
//    }
//
//    @Override
//    public void addObjectGui(Object object) {
//        addObject(object, true);
//    }
//    
//    @Override
//    public void removeObject(Object object) {
//        PlayState ps = Ly.getPlayState();
//        if (ps == null)
//            return;
//        ps.removeObject(object);
//    }
//    
//    @Override
//    public void addActor(Actor actor) {
//        Ly.getPlayState().addObject(actor, false);
//    }
////
////    @Override
////    public void addSimplePlayer(Actor actor) {
////        logicService.resetPlayerLogic(actor);
////        // 暂时以1作为默认分组
////        actorService.setTeam(actor, 1);
////        actorService.setPlayer(actor, true);
////        skillService.playSkill(actor, skillService.getSkillWaitDefault(actor), false);
////        addActor(actor);
////    }
////
////    @Override
////    public void addEffect(Effect effect) {
////        EffectManager.getInstance().addEffect(effect);
////    }
////
////    @Override
////    public void addBullet(Bullet bullet) {
////        BulletManager.getInstance().addBullet(bullet);
////    }
////    
////    @Override
////    public void addView(View view) {
////        Ly.getPlayState().addObject(view, true);
////    }
////
////    @Override
////    public void addAnimation(Anim animation) {
////        Ly.getPlayState().addObject(animation, false);
////        animation.start();
////    }
//
//    @Override
//    public void removeAnimation(Anim animation) {
//        Ly.getPlayState().removeObject(animation);
//    }
//
//    @Override
//    public void addMessage(String message, MessageType type) {
//        Ly.getPlayState().addMessage(message, type);
//    }
//
//    @Override
//    public void addMessage(Actor actor, String message, MessageType type) {
//        Ly.getPlayState().addMessage(message, type);
//    }
//
//    @Override
//    public void addShortcut(Actor actor, ObjectData data) {        
//        ShortcutManager.addShortcut(actor, data);
//    }
//    
//    @Override
//    public List<Actor> findAllActor() {
//        PlayState ps = Ly.getPlayState();
//        if (ps != null) {
//            return ps.getActors();
//        }
//        return Collections.EMPTY_LIST;
//    }

//    @Override
//    public List<Actor> findOrganismActors(int group, List<Actor> store) {
//        if (store == null) {
//            store = new ArrayList<Actor>();
//        }
//        List<Actor> all = LY.getPlayState().getActors();
//        for (Actor actor : all) {
//            if (actorService.getGroup(actor) == group) {
//                // 一般质量大于０(能移动)的都可视为有机生命
//                if (actorService.getMass(actor) > 0) {
//                    store.add(actor);
//                }
//            }
//        }
//        return store;
//    }
////    
////    @Override
////    public Actor findActor(String id) {
////        List<Actor> actors = Ly.getPlayState().getActors();
////        if (actors == null || actors.isEmpty())
////            return null;
////        for (Actor actor : actors) {
////            if (actor.getData().getId().equals(id)) {
////                return actor;
////            }
////        }
////        return null;
////    }
////    
////    @Override
////    public Actor findActor(long actorUniqueId) {
////        if (actorUniqueId <= 0) {
////            return null;
////        }
////        List<Actor> actors = Ly.getPlayState().getActors();
////        if (actors == null || actors.isEmpty())
////            return null;
////        for (Actor actor : actors) {
////            if (actor.getData().getUniqueId() == actorUniqueId) {
////                return actor;
////            }
////        }
////        return null;
////    }
////
////    @Override
////    public List<View> findAllViews() {
////        return Ly.getPlayState().getViews();
////    }
////
////    @Override
////    public View findView(long uniqueId) {
////        List<View> views = Ly.getPlayState().getViews();
////        for (int i = 0; i < views.size(); i++) {
////            if (views.get(i).getData().getUniqueId() == uniqueId) {
////                return views.get(i);
////            }
////        }
////        return null;
////    }
////
////    @Override
////    public NetworkObject findSyncObject(long objectId) {
////        return Ly.getPlayState().getSyncObjects(objectId);
////    }
////    
////    @Override
////    public NavMeshPathfinder createPathfinder() {
//////        return Common.getPlayState().createPathfinder();
////        // 暂不支持寻路
////        return null;
////    }
//
//    @Override
//    public Spatial getTerrain() {
//        PlayState playState = Ly.getPlayState();
//        if (playState == null || playState.getGameState().getGame() == null) 
//            return null;
//        
//        Scene scene = playState.getGameState().getScene();
//        if (scene != null) {
//            return scene.getTerrain();
//        }
//        return null;
//    }
//    
//    @Override
//    public float getTerrainHeight(float x, float z) {
//        Spatial terrain = getTerrain();
//        float height = 0;
//        if (terrain == null) {
//            return height;
//        }
//
//        TempVars tv = TempVars.get();
//        Vector3f origin = tv.vect1.set(x, Float.MIN_VALUE, z);
//        Vector3f dir = tv.vect2.set(0,1,0);
//        Ray ray = new Ray(origin, dir);
//        CollisionResults results = new CollisionResults();
//        terrain.collideWith(ray, results);
//        if (results.size() > 0) {
//            height = results.getFarthestCollision().getContactPoint().getY();
//        }
//        tv.release();
//        return height;
//    }
//
//    @Override
//    public Vector3f getActorForwardPosition(Actor actor, float distance, Vector3f store) {
//        if (store == null) {
//            store = new Vector3f();
//        }
//        TempVars tv = TempVars.get();
//        Vector3f dirByLength = tv.vect1.set(actorService.getViewDirection(actor))
//                .normalizeLocal().multLocal(distance);
//        store.set(actor.getSpatial().getWorldTranslation()).addLocal(dirByLength);
//        store.y = getTerrainHeight(store.x, store.z);
//        tv.release();
//        
//        return store;
//    }
//
//    @Override
//    public Actor getPlayer() {
//        return Ly.getPlayState().getPlayer();
//    }
//
//    @Override
//    public Vector3f getRandomTerrainPoint(Vector3f store) {
//        if (store == null) {
//            store = new Vector3f();
//        }
//        Spatial terrain = getTerrain();
//        if (terrain == null) {
//            store.set(0, 0, 0);
//            return store;
//        }
//        BoundingBox bb = (BoundingBox) terrain.getWorldBound();
//        float xe = bb.getXExtent() * 0.5f; // x 0.5防止掉出边界
//        float ze = bb.getZExtent() * 0.5f;
//        
//        store.set(FastMath.nextRandomFloat() * xe * 2 - xe, 0, FastMath.nextRandomFloat() * ze * 2 - ze);
//        store.setY(getTerrainHeight(store.x, store.z));
//        return store;
//    }

    // remove20161006
//    @Override
//    public void moveObject(Actor actor, Vector3f position) {
//        actorService.setLocation(actor, position);
//    }
//
//    @Override
//    public Scene getScene() {
//        PlayState ps = Ly.getPlayState();
//        if (ps == null || ps.getGameState().getGame() == null) 
//            return null;
//        
//        return ps.getGameState().getScene();
//    }
//
//    @Override
//    public boolean isInScene(Actor actor) {
//        return Ly.getPlayState().isInScene(actor.getSpatial());
//    }
//
//    @Override
//    public Actor getTarget() {
//        return Ly.getPlayState().getTarget();
//    }
//
//    @Override
//    public void setTarget(Actor target) {
//        Ly.getPlayState().setTarget(target);
//    }
//
//    @Override
//    public Vector3f moveUpToTerrain(Vector3f position) {
//        float terrainHeight = getTerrainHeight(position.x, position.z);
//        if (terrainHeight > position.y) {
//            // 注意：如果该点已经在地面上，则不要作多余的修改
//            position.setY(terrainHeight);
//        }
//        return position;
//    }

//    @Override
//    public void setMainPlayer(Actor actor) {
//        Ly.getPlayState().setPlayer(actor);
//    }

//    @Override
//    public boolean isInScene(Spatial spatial) {
//        return Ly.getPlayState().isInScene(spatial);
//    }
//
//    @Override
//    public void saveCompleteStage(int stage) {
//        PlayState ps = Ly.getPlayState();
//        if (!(ps instanceof StoryServerPlayState)) {
//            return;
//        }
//        StoryServerPlayState ss = (StoryServerPlayState) ps;
//        SaveStory saveStory = ss.getSaveStory();
//        if (saveStory == null) {
//            saveStory = new SaveStory();
//        }
//        saveStory.setStoryCount(stage);
//        saveStory.setPlayer(ps.getPlayer().getData());
//        SaveHelper.saveStoryLast(saveStory);
//    }
//
//    @Override
//    public Application getApplication() {
//        return Ly.getApp();
//    }
//
//    @Override
//    public void changeGame(String gameId) {
//        changeGame(gameService.loadGameData(gameId));
//    }
//
//    @Override
//    public void changeGame(GameData gameData) {
//        GameState gameState = new SimpleGameState(gameData);
//        LoadingState loadingState = new LoadingState(Ly.getPlayState(), gameState);
//        Ly.getApp().getStateManager().attach(loadingState);
//    }
//
//    @Override
//    public String getGameId() {
//        PlayState ps = Ly.getPlayState();
//        if (ps == null)
//            return null;
//        
//        GameState gameState = ps.getGameState();
//        if (gameState == null)
//            return null;
//        
//        return gameState.getGame().getData().getId();
//    }

    // remove20161006
//    @Override
//    public void addMessageOnlyClients(String message, MessageType type) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }


//    @Override
//    public void syncGameInitToClient(HostedConnection client) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void syncObject(NetworkObject object, SyncData syncData, boolean reliable) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }


    
    
}
