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
package name.huliqing.editor.edit.scene;

import name.huliqing.editor.edit.Mode;
import name.huliqing.editor.edit.controls.ControlTile;
import name.huliqing.luoying.data.EntityData;

/**
 *
 * @author huliqing
 * @param <T>
 */
public interface JfxSceneEditListener<T extends ControlTile> {
    
    /**
     * 当编辑模式发生变化时该方法被调用
     * @param mode 
     */
    void onModeChanged(Mode mode);
    
    /**
     * 当选择发生变化时该方法被调用
     * @param selectObj 
     */
    void onSelectChanged(T selectObj);
    
    void onEntityAdded(EntityData ed);
    
    void onEntityRemoved(EntityData ed);
}
