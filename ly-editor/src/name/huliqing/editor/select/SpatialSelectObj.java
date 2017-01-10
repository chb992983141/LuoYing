/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.editor.select;

import com.jme3.scene.Spatial;

/**
 *
 * @author huliqing
 */
public class SpatialSelectObj implements SelectObj<Spatial> {

    private Spatial spatial;
    
    public SpatialSelectObj() {}
    
    public SpatialSelectObj(Spatial object) {
        this.spatial = object;
    }
    
    @Override
    public void setObject(Spatial object) {
        this.spatial = object;
    }

    @Override
    public Spatial getObject() {
        return spatial;
    }

    @Override
    public Spatial getSelectedSpatial() {
        return spatial;
    }
    
}