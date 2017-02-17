/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.editor.ui;

import java.io.File;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import name.huliqing.editor.constants.DataFormatConstants;
import name.huliqing.editor.component.ComponentDefine;
import name.huliqing.editor.constants.ConfigConstants;
import name.huliqing.editor.manager.ComponentManager;
import name.huliqing.editor.manager.ConfigManager.ConfigChangedListener;
import name.huliqing.editor.manager.Manager;
import name.huliqing.fxswing.Jfx;

/**
 *
 * @author huliqing
 */
public class ComponentsForm extends ListView<ComponentDefine> implements ConfigChangedListener{
//    private static final Logger LOG = Logger.getLogger(ComponentsView.class.getName());
    
    public ComponentsForm() {

        setCellFactory((ListView<ComponentDefine> param) -> new ListCell<ComponentDefine>() {
            @Override
            protected void updateItem(ComponentDefine item, boolean empty) {
                super.updateItem(item, empty);
                String name = null;
                Node icon = null;
                if (item != null && !empty) {
                    name = item.getId();
                }
                setText(name);
                setGraphic(icon);
            }
        });

        setOnDragDetected(this::doDragDetected);
        setOnDragDone(this::doDragDone);
        
        updateAassetDir();
        
        // 切换资源目录的时候要重置组件面板
        Manager.getConfigManager().addListener(this);
    }
    
    @Override
    public void onConfigChanged(String key) {
        if (key.equals(ConfigConstants.KEY_MAIN_ASSETS)) {
            Jfx.runOnJfx(() -> updateAassetDir());
        }
    }
    
    private void updateAassetDir() {
        getItems().clear();
        List<ComponentDefine> cds = ComponentManager.getComponentsByType("entity");
        if (cds != null) {
            getItems().addAll(cds);
        }
    }

    private ComponentDefine getMainSelectItem() {
        ObservableList<ComponentDefine> items = getSelectionModel().getSelectedItems();
        if (items.isEmpty()) {
            return null;
        }
        for (ComponentDefine c : items) {
            if (c != null) {
                return c;
            }
        }
        return null;
    }

    private void doDragDetected(MouseEvent e) {
//        LOG.log(Level.INFO, "EntityComponents: doDragDetected.");
        ComponentDefine selected = getMainSelectItem();
        if (selected == null) {
            e.consume();
            return;
        }
        Dragboard db = startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.put(DataFormatConstants.COMPONENT_DEFINE, selected);
        db.setContent(clipboardContent);
        e.consume();
    }

    private void doDragDone(DragEvent e) {
        e.consume(); // 不要直接取消事件传递
//        LOG.log(Level.INFO, "EntityComponents: doDragDone.");
    }

}