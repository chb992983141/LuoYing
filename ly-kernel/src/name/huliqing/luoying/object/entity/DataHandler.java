/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.luoying.object.entity;

import name.huliqing.luoying.xml.ObjectData;

/**
 * DataHandler是用来作为处理Entity数据的勾子, 当向Entity添加、删除、使用数据时会通过这些DataHandler的处理。
 * 实体模块（Entity Module）可以通过实现这个接口来处理流转到实体的数据。
 * 每一个不同的实体模块应该尽量明确、并且只处理自己需要的一种类型的数据。
 * @author huliqing
 * @param <T>
 */
public interface DataHandler<T extends ObjectData> {
    
    /**
     * 指定一个要处理的数据类型，当数据流转到实体内时只有属于这个类型的数据才会触发这个DataHandler来对数据进行处理。
     * 每个DataHandler都应该尽量明确只处理自己需要的类型的数据。
     * @return 
     */
    Class<T> getHandleType();
    
    /**
     * 处理Entity数据的添加，当外部向Entity添加数据时这个方法会被调用，
     * 实体模块（Entity Module）通过实现这个方法来处理外部进入的数据。
     * @param data
     * @param amount 
     */
    void handleDataAdd(T data, int amount);
    
    /**
     * 处理Entity数据的移除，当外部从Entity移除数据时这个方法会被调用，
     * 实体模块（Entity Module）通过实现这个方法来处理数据的移除。
     * @param data
     * @param amount 
     */
    void handleDataRemove(T data, int amount);
    
    /**
     * 处理Entity数据的使用，当Entity使用数据时这个方法会被调用。
     * 实体模块（Entity Module）通过实现这个方法来确定要如何使用指定的数据。
     * @param data 
     */
    void handleDataUse(T data);
}