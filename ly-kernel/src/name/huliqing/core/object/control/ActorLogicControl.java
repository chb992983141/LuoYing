/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.object.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.util.SafeArrayList;
import java.util.ArrayList;
import java.util.List;
import name.huliqing.core.data.ActorLogicData;
import name.huliqing.core.data.ControlData;
import name.huliqing.core.loader.Loader;
import name.huliqing.core.object.actor.Actor;
import name.huliqing.core.object.actorlogic.ActorLogic;
import name.huliqing.core.xml.DataFactory;

/**
 * 逻辑控制器，控制角色的所有逻辑的运行。
 * @author huliqing
 */
public class ActorLogicControl extends ActorControl<ControlData> {

    private Actor actor;
    private final SafeArrayList<ActorLogic> logics = new SafeArrayList<ActorLogic>(ActorLogic.class);
    private final List<ActorLogicData> logicDatas = new ArrayList<ActorLogicData>();

    @Override
    public void initialize(Actor actor) {
        super.initialize(actor);
        this.actor = actor;

        // 从存档中载入状态，如果不是存档则从原始参数中获取
        List<ActorLogicData> logicInits = (List<ActorLogicData>) data.getAttribute("logicDatas");
        if (logicInits == null) {
            String[] logicArr = data.getAsArray("logics");
            if (logicArr != null) {
                logicInits = new ArrayList<ActorLogicData>(logicArr.length);
                for (String logicId : logicArr) {
                    logicInits.add((ActorLogicData) DataFactory.createData(logicId));
                }
            }
        }
        if (logicInits != null) {
            for (ActorLogicData stateData : logicInits) {
                addLogic((ActorLogic)Loader.load(stateData));
            }
        }
        
        data.setAttribute("logicDatas", logicDatas);
    }

    @Override
    public void cleanup() {
        for (ActorLogic logic : logics) {
            logic.cleanup();
        }
        logics.clear();
        logicDatas.clear();
        super.cleanup(); 
    }
    
    @Override
    public void actorUpdate(float tpf) {
        for (ActorLogic logic : logics.getArray()) {
            logic.update(tpf);
        }
    }

    @Override
    public void actorRender(RenderManager rm, ViewPort vp) {}
    
    public void addLogic(ActorLogic logic) {
        if (!logics.contains(logic)) {
            logic.setActor(actor);
            logic.initialize();
            logics.add(logic);
            logicDatas.add(logic.getData());
        }
    }

    public boolean removeLogic(ActorLogic logic) {
        if (!logics.contains(logic))
            return false;
        
        logics.remove(logic);
        logicDatas.remove(logic.getData());
        logic.cleanup();
        return true;
    }
    
    public List<ActorLogic> getLogics() {
        return logics;
    }
}