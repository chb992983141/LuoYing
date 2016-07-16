/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.fighter.object;

import name.huliqing.fighter.data.ProtoData;

/**
 *
 * @author huliqing
 * @param <T>
 */
public interface DataProcessor<T extends ProtoData> {

    /**
     * 设置data
     * @param data 
     */
    void setData(T data);
    
    /**
     * 获取data
     * @return 
     */
    T getData();

}
