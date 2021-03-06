/*
 * LuoYing is a program used to make 3D RPG game.
 * Copyright (c) 2014-2016 Huliqing <31703299@qq.com>
 * 
 * This file is part of LuoYing.
 *
 * LuoYing is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LuoYing is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with LuoYing.  If not, see <http://www.gnu.org/licenses/>.
 */
package name.huliqing.editor.tools.terrain;

import com.jme3.gde.terraineditor.tools.AbstractTerrainToolAction;
import com.jme3.gde.terraineditor.tools.RoughExtraToolParams;
import com.jme3.gde.terraineditor.tools.RoughTerrainToolAction;
import com.jme3.math.Vector3f;
import name.huliqing.editor.edit.controls.entity.EntityControlTile;

/**
 *
 * @author huliqing
 */
public class RoughTool extends AdjustTerrainTool {
    
    public RoughTool(String name, String tips, String icon) {
        super(name, tips, icon);
    }
    
    @Override
    protected AbstractTerrainToolAction createAction(float radius, float weight, Vector3f markerWorldLoc, EntityControlTile terrain) {
        RoughExtraToolParams params = new RoughExtraToolParams();
        params.lacunarity = toolbar.getRoughParamsTool().getLacunarity().getValue().floatValue();
        params.octaves = toolbar.getRoughParamsTool().getOctaves().getValue().floatValue();;
        params.scale = toolbar.getRoughParamsTool().getScale().getValue().floatValue();
        
        RoughTerrainToolAction action = new RoughTerrainToolAction(terrain, markerWorldLoc, radius, weight, params);
        setModified(true);
        return action;
    }
    
}
