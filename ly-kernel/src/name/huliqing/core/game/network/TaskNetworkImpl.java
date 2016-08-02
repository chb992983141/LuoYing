/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.game.network;

import java.util.List;
import name.huliqing.core.Factory;
import name.huliqing.core.data.TaskData;
import name.huliqing.core.game.service.TaskService;
import name.huliqing.core.network.Network;
import name.huliqing.core.game.mess.MessTaskAdd;
import name.huliqing.core.game.mess.MessTaskApplyItem;
import name.huliqing.core.game.mess.MessTaskComplete;
import name.huliqing.core.object.actor.Actor;
import name.huliqing.core.object.task.Task;

/**
 *
 * @author huliqing
 */
public class TaskNetworkImpl implements TaskNetwork {
    private final static Network network = Network.getInstance();
    private TaskService taskService;
    
    @Override
    public void inject() {
        taskService = Factory.get(TaskService.class);
    }

    @Override
    public Task loadTask(String taskId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Task loadTask(TaskData taskData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addTask(Actor actor, Task task) {
        if (network.isClient())
            return;
        
        if (network.hasConnections()) {
            MessTaskAdd mess = new MessTaskAdd();
            mess.setActorId(actor.getData().getUniqueId());
            mess.setTaskData(task.getData());
            network.broadcast(mess);
        }
        
        taskService.addTask(actor, task);
    }

    @Override
    public Task getTask(Actor actor, String taskId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TaskData> getTaskDatas(Actor actor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Task> getTasks(Actor actor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean checkCompletion(Actor actor, Task task) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void completeTask(Actor actor, Task task) {
        if (network.isClient())
            return;
        
        if (network.hasConnections()) {
            MessTaskComplete mess = new MessTaskComplete();
            mess.setActorId(actor.getData().getUniqueId());
            mess.setTaskId(task.getId());
            network.broadcast(mess);
        }
        
        taskService.completeTask(actor, task);
    }

    @Override
    public void applyItem(Actor actor, Task task, String itemId, int amount) {
        if (network.isClient())
            return;
        
        if (network.hasConnections()) {
            MessTaskApplyItem mess = new MessTaskApplyItem();
            mess.setActorId(actor.getData().getUniqueId());
            mess.setAmount(amount);
            mess.setItemId(itemId);
            mess.setTaskId(task.getId());
            network.broadcast(mess);
        }
        
        taskService.applyItem(actor, task, itemId, amount);
    }

    @Override
    public int getItemTotal(Actor actor, Task task, String itemId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}