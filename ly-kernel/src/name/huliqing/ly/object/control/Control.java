/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.ly.object.control;

//import com.jme3.scene.control.Control;
import name.huliqing.ly.data.ControlData;
import name.huliqing.ly.xml.DataProcessor;

/**
 *
 * @author huliqing
 * @param <T>
 */
public interface Control<T extends ControlData> extends DataProcessor<T> {
    
    /**
     * 获取控制器{@link com.jme3.scene.control.Control}
     * @return 
     * @see com.jme3.scene.control.Control
     */
    com.jme3.scene.control.Control getControl();
}
