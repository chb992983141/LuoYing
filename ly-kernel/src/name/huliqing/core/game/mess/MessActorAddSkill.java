/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.game.mess;

import com.jme3.network.serializing.Serializable;
import name.huliqing.core.Factory;
import name.huliqing.core.game.service.PlayService;
import name.huliqing.core.game.service.SkillService;
import name.huliqing.core.object.actor.Actor;

/**
 * 通知客户端给角色添加一个技能
 * @author huliqing
 */
@Serializable
public class MessActorAddSkill extends MessBase {
    
    // 要添加技能的角色
    private long actorId;
    // 被添加的技能ID,因为角色技能不可重复，所以这里不需要唯一ID
    private String skillId;

    public long getActorId() {
        return actorId;
    }

    public void setActorId(long actorId) {
        this.actorId = actorId;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    @Override
    public void applyOnClient() {
        PlayService playService = Factory.get(PlayService.class);
        SkillService skillService = Factory.get(SkillService.class);
        Actor actor = playService.findActor(actorId);
        if (actor != null) {
            skillService.addSkill(actor, skillId);
        }
    }
    
}