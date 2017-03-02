/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.editor.converter;

import java.util.List;
import java.util.Map;
import name.huliqing.editor.converter.define.Feature;

/**
 * @author huliqing
 */
public class FeatureHelper {
    
    protected Map<String, Feature> features;
    
    public FeatureHelper(Map<String, Feature> features) {
        this.features = features;
    }
    
    public String getAsString(String key) {
        if (features == null)
            return null;
        
        Feature feature = features.get(key);
        if (feature != null) {
            return feature.getValue();
        }
        return null;
    }
    
    /**
     * 以boolean方式获取参数，如果不存在指定参数则返回false.
     * @param key
     * @return 
     */
    public boolean getAsBoolean(String key) {
        if (features == null)
            return false;
        
        Feature feature = features.get(key);
        return feature != null && feature.getAsBoolean();
    }
    
    /**
     * 以数组方式获取参数，如果原数据是字符串形式，则该方法将使用半角逗号","来拆分为数组
     * @param key
     * @return 
     */
    public String[] getAsArray(String key) {
        if (features == null)
            return null;
        
        Feature feature = features.get(key);
        if (feature == null)
            return null;
        
        return feature.getAsArray();
    }
    
    /**
     * 以List方式获取参数，如果原数据是字符串形式，则该方法将使用半角逗号","来拆分为列表
     * @param key
     * @return 
     */
    public List<String> getAsList(String key) {
        if (features == null)
            return null;
        
        Feature feature = features.get(key);
        if (feature == null)
            return null;
        
        return feature.getAsList();
    }
    
}
