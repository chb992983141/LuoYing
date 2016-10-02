/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.object.item;

import name.huliqing.core.data.ItemData;
import name.huliqing.core.object.actor.Actor;
import name.huliqing.core.xml.DataProcessor;

/**
 * 定义角色杂物品
 * @author huliqing
 */
public interface Item extends DataProcessor<ItemData> {
    
    /**
     * 获取物品类型id， 注：这个是物品的类型ID，并非唯一ID。
     * @return 
     */
    String getId();
    
    /**
     * 让角色使用指定的物品。注：这个方法会强制使用物品，如果要判断当前状态下角色是否可以使用这件物品，
     * 可以使用{@link #canUse(Actor) } 或 {@link #checkStateCode(Actor) }
     * @param actor 
     * @see #canUse(name.huliqing.core.object.actor.Actor) 
     * @see #checkStateCode(name.huliqing.core.object.actor.Actor) 
     */
    void use(Actor actor);
    
    /**
     * 判断角色是否可以使用当前物品, 也可以通过状态码来判断。
     * @param actor
     * @return 
     * @see #checkStateCode(name.huliqing.core.object.actor.Actor) 
     */
    boolean canUse(Actor actor);
    
    /**
     * 这个方法返回一个状态码，用于判断指定角色是否可以使用这个物品。
     * @param actor
     * @return 
     * @see #canUse(name.huliqing.core.object.actor.Actor) 
     */
    int checkStateCode(Actor actor);
    
}
