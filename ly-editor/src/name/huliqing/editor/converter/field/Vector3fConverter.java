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
package name.huliqing.editor.converter.field;

import com.jme3.math.Vector3f;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import name.huliqing.editor.constants.StyleConstants;
import name.huliqing.editor.converter.SimpleFieldConverter;
import name.huliqing.luoying.xml.Converter;

/**
 *
 * @author huliqing
 */
public class Vector3fConverter extends SimpleFieldConverter{
    
    private final VBox layout = new VBox();
    
    private final HBox xLayout = new HBox();
    private final Label xLabel = new Label("X");
    private final TextField xField = new TextField("");
    
    private final HBox yLayout = new HBox();
    private final Label yLabel = new Label("Y");
    private final TextField yField = new TextField("");
    
    private final HBox zLayout = new HBox();
    private final Label zLabel = new Label("Z");
    private final TextField zField = new TextField("");
    
    private String lastX = "";
    private String lastY = "";
    private String lastZ = "";
    private Vector3f lastValue;
    
    private final ChangeListener<Boolean> focusedListener = (ObservableValue<? extends Boolean> observable
            , Boolean oldValue, Boolean newValue) -> {
        if (newValue) {
            return;
        }
        updateAndSave();
    };
    private final EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                updateAndSave();
            }
        }
    };
    
    public Vector3fConverter() {
        layout.getStyleClass().add(StyleConstants.CLASS_HVBOX);
        xLayout.getStyleClass().add(StyleConstants.CLASS_HVBOX);
        yLayout.getStyleClass().add(StyleConstants.CLASS_HVBOX);
        zLayout.getStyleClass().add(StyleConstants.CLASS_HVBOX);
        
        layout.getChildren().addAll(xLayout, yLayout, zLayout);
        xLayout.getChildren().addAll(xLabel, xField);
        yLayout.getChildren().addAll(yLabel, yField);
        zLayout.getChildren().addAll(zLabel, zField);
        
        xLayout.setAlignment(Pos.CENTER_LEFT);
        yLayout.setAlignment(Pos.CENTER_LEFT);
        zLayout.setAlignment(Pos.CENTER_LEFT);
        
        xField.focusedProperty().addListener(focusedListener);
        yField.focusedProperty().addListener(focusedListener);
        zField.focusedProperty().addListener(focusedListener);
        xField.setOnKeyPressed(keyHandler);
        yField.setOnKeyPressed(keyHandler);
        zField.setOnKeyPressed(keyHandler);
        
        xLabel.setMinWidth(15);
        yLabel.setMinWidth(15);
        zLabel.setMinWidth(15);
        layout.setSpacing(3);
    }

    @Override
    protected Node createLayout() {
        return layout;
    }
    
    private void updateAndSave() {
        boolean changed = false;
        if (!xField.getText().equals(lastX)) {
            lastX = xField.getText();
            changed = true;
        }
        if (!yField.getText().equals(lastY)) {
            lastY = yField.getText();
            changed = true;
        }
        if (!zField.getText().equals(lastZ)) {
            lastZ = zField.getText();
            changed = true;
        }
        if (changed) {
            
            try {
                Vector3f newVec = new Vector3f(Float.parseFloat(lastX), Float.parseFloat(lastY),Float.parseFloat(lastZ));
                updateAttribute(newVec);
                addUndoRedo(lastValue, new Vector3f(newVec));
                lastValue = new Vector3f(newVec);
            } catch (NumberFormatException e) {
                // 如果全为空则清除该参数
                if (lastX.equals("") && lastY.equals("") && lastZ.equals("")) {
                    updateAttribute(null);
                    addUndoRedo(lastValue, null);
                    lastValue = null;
                }
            }
        }
    }

    @Override
    protected void updateUI() {
        Object propertyValue = data.getAttribute(field);
        lastValue = Converter.getAsVector3f(propertyValue);
        if (lastValue != null) {
            xField.setText(lastValue.x + "");
            yField.setText(lastValue.y + "");
            zField.setText(lastValue.z + "");
        } else {
            xField.setText("");
            yField.setText("");
            zField.setText("");
        }
        lastX = xField.getText();
        lastY = yField.getText();
        lastZ = zField.getText();
    }
    
    
}
