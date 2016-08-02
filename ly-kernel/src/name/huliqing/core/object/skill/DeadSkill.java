/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.object.skill;

import name.huliqing.core.Factory;
import name.huliqing.core.data.SkillData;
import name.huliqing.core.game.service.PlayService;
import name.huliqing.core.game.service.StateService;
import name.huliqing.core.object.skill.AbstractSkill;

/**
 * 执行死亡技能,简单的死亡效果
 * @author huliqing
 * @param <T>
 */
public class DeadSkill<T extends SkillData> extends AbstractSkill<T> {
    private final PlayService playService = Factory.get(PlayService.class);
    private final StateService stateService = Factory.get(StateService.class);;
    
    // 是否死亡后立即移出场景
    private boolean remove;
    
    @Override
    public void setData(T data) {
        super.setData(data); 
       remove = data.getAsBoolean("remove", remove);
    }

    @Override
    public void init() {
        super.init(); 
        
        if (channelProcessor == null && channelProcessor != null) {
            // Reset，对于没有“死亡”动画的角色，在死亡时必须让它“静止”
            // 让角色的其它动画停止播放，以防止角色在死亡之后仍然在做其它动作的奇怪现象。
            channelProcessor.reset();
        }
    }
    
    @Override
    protected void doUpdateLogic(float tpf) {
        // ignore
    }

    @Override
    protected void end() {
        // 注意这里不能放在cleanup中进行，不要再在cleanup中去调用service.
        stateService.clearStates(actor);
        playService.removeObject(actor.getModel());
        super.end();
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    
}