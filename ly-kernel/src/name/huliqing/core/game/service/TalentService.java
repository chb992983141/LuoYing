/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.game.service;

import java.util.List;
import name.huliqing.core.Inject;
import name.huliqing.core.data.TalentData;
import name.huliqing.core.object.actor.Actor;

/**
 *
 * @author huliqing
 */
public interface TalentService extends Inject {
    
    /**
     * 给角色添加一个天赋
     * @param actor
     * @param talentId 
     */
    void addTalent(Actor actor, String talentId);
    
    /**
     * 给角色添加一个天赋
     * @param actor
     * @param talentData 
     */
    void addTalent(Actor actor, TalentData talentData);
    
    /**
     * 移除角色身上指定的天赋
     * @param actor
     * @param talentId 
     */
    void removeTalent(Actor actor, String talentId);
    
    /**
     * 获取角色身上所有的天赋,如果没有任何天赋则返回null.
     * @param actor
     * @return 
     */
    List<TalentData> getTalents(Actor actor);
    
    /**
     * 增加角色某个天赋的点数,注：角色必须拥有足够的可用天赋点数才能增加。
     * 否则该方法将什么也不处理，当天赋的点数增加后，角色的可用天赋将会减少。
     * @param actor 指定的角色
     * @param talentId 天赋ID
     * @param points 增加的点数，必须是正数
     */
    void addTalentPoints(Actor actor, String talentId, int points);
}