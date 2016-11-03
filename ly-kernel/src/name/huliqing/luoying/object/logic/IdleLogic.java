/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.luoying.object.logic;

import name.huliqing.luoying.Factory;
import name.huliqing.luoying.data.LogicData;
import name.huliqing.luoying.layer.service.ActionService;
import name.huliqing.luoying.layer.service.ElService;
import name.huliqing.luoying.object.action.Action;
import name.huliqing.luoying.object.el.SBooleanEl;

/**
 *
 * @author huliqing
 */
public class IdleLogic extends AbstractLogic {
    private final ActionService actionService = Factory.get(ActionService.class);
    private final ElService elService = Factory.get(ElService.class);
    
    // 普通的idle行为，在原地不动执行idle动作。
    private Action idleSimpleAction;
    // 巡逻行为，在一个地方走来走去
    private Action idlePatrolAction;
    
    // 这个EL用于判断在什么情况下当前逻辑可以使用Patrol的idle行为
    private SBooleanEl patrolCheckEl;

    @Override
    public void setData(LogicData data) {
        super.setData(data); 
        this.idleSimpleAction = actionService.loadAction(data.getAsString("idleSimpleAction"));
        this.idlePatrolAction = actionService.loadAction(data.getAsString("idlePatrolAction"));
        String tempEl = data.getAsString("patrolCheckEl");
        if (tempEl != null) {
            this.patrolCheckEl = elService.createSBooleanEl(tempEl);
        }
    }

    @Override
    public void initialize() {
        super.initialize();
        if (patrolCheckEl != null) {
            patrolCheckEl.setSource(actor.getAttributeManager());
        }
    }

    @Override
    protected void doLogic(float tpf) {
        // remove20161103
//        // 只有空闲时才执行IDLE：
//        // 1. 在没有跟随目标时执行巡逻行为，可走来走去
//        // 2. 在有跟随目标时则只执行普通idle行为，不可走来走去。
//        Action current = actionService.getPlayingAction(actor);
//        if (current == null) {
//            if (actorService.getFollow(actor) > 0) {
//                actionService.playAction(actor, idleSimpleAction);
//            } else {
//                actionService.playAction(actor, idlePatrolAction);
//            }
//        }

        // 只有空闲时才执行IDLE：
        // 1. 在没有跟随目标时执行巡逻行为，可走来走去
        // 2. 在有跟随目标时则只执行普通idle行为，不可走来走去。
        Action current = actionService.getPlayingAction(actor);
        if (current == null) {
            if (patrolCheckEl != null && patrolCheckEl.getValue()) {
                actionService.playAction(actor, idlePatrolAction);
            } else {
                actionService.playAction(actor, idleSimpleAction);
            }
        }
    }
    
}
