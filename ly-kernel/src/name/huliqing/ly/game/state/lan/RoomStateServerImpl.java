/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.ly.game.state.lan;

import name.huliqing.core.network.Network;
import name.huliqing.core.network.GameServer;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import name.huliqing.core.LY;
import name.huliqing.ly.Fighter;
import name.huliqing.core.data.GameData;
import name.huliqing.core.data.ConnData;
import name.huliqing.ly.game.state.LanServerPlayState;
import name.huliqing.core.network.GameServer.ServerState;
import name.huliqing.core.manager.ResourceManager;
import name.huliqing.ly.game.view.HelpView;
import name.huliqing.core.ui.LinearLayout;
import name.huliqing.core.ui.UIFactory;
import name.huliqing.core.ui.state.UIState;

/**
 * 服务端在房间里面
 * @author huliqing
 */
public class RoomStateServerImpl extends AbstractAppState implements RoomState {
    private final static Logger LOG = Logger.getLogger(RoomStateClientImpl.class.getName());
    // 客户端连接的机器名称属性名
    public final static String ATTR_MACHINE_NAME = "machineName";
    private Fighter app;
    
    // 客户端连接列表
    private RoomStatePanel clientPanel;
    
    // 帮助
    private LinearLayout helpPanel;
    private HelpView helpInServerRoom; // lan.help.inServerRoom
    private HelpView helpLoading;
    
    // 控制按钮
    private RoomStateBtnPanel btnPanel;

    // 游戏关卡地图信息
    private GameData gameData;
    // 标记是否开始了游戏
    private boolean startGame;
    
    private GameServer gameServer;
    
    public RoomStateServerImpl(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        float width = LY.getSettings().getWidth();
        float height = LY.getSettings().getHeight();
        this.app = (Fighter) app;
        
        try {
            // ---- 界面UI
            float btnHeight = UIFactory.getUIConfig().getButtonHeight() * 1.5f;
            float helpHeight = UIFactory.getUIConfig().getButtonHeight();
            float clientHeight = height - btnHeight - helpHeight;
            
            clientPanel = new RoomStatePanel(width, clientHeight);
            clientPanel.setGameData(gameData);
            
            helpPanel = new LinearLayout(width, helpHeight);
            helpPanel.setMargin(10, 0, 0, 0);
            helpInServerRoom = new HelpView(width, helpHeight, ResourceManager.get("lan.help.inServerRoom"));
            helpLoading = new HelpView(width, helpHeight, ResourceManager.get("lan.help.loading"));
            helpPanel.addView(helpInServerRoom);
            
            btnPanel = new RoomStateBtnPanel(width, btnHeight, this);
            
            LinearLayout localUIRoot = new LinearLayout(width, height);
            localUIRoot.addView(clientPanel);
            localUIRoot.addView(helpPanel);
            localUIRoot.addView(btnPanel);
            UIState.getInstance().addUI(localUIRoot);
            
            // 开始Server
            gameServer = Network.getInstance().createGameServer(gameData);
            gameServer.setServerListener(new LanRoomServerListener(app));
            gameServer.start();
            
            // 刷新客户端列表
            refreshClients();
            
        } catch (Exception ex) {
            Logger.getLogger(RoomStateServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (startGame) {
            LOG.log(Level.INFO, "RoomStateServerImpl.startGame");
            gameServer.setServerListener(null);
            gameServer.setServerState(ServerState.loading);
            app.changeState(new LanServerPlayState(app, gameServer));
        }
    }
    
    @Override
    public void startGame() {
        // 开始游戏
        startGame = true;
        
        // help tip
        helpPanel.clearViews();
        helpPanel.addView(helpLoading);
    }

    @Override
    public void kickClient() {
        ConnData clientData = clientPanel.getSelected();
        if (clientData == null) {
            return;
        }
        gameServer.kickClient(clientData.getConnId(), "Kick");
    }

    /**
     * 返回局域网房间列表,返回之前需要关闭已经创建的服务器及监听器
     */
    @Override
    public void back() {
        // 在切换到lan状态时 cleanup会自动被调用,所以serverDiscover和server会被关闭
        app.changeLanState();
    }
    
    @Override
    public void cleanup() {
        gameServer.setServerListener(null);
        // 如果创建了游戏后没有开始，则应该在退出的时候关闭discover和server
        if (!startGame) {
            Network.getInstance().cleanup();
        }
        UIState.getInstance().clearUI();
        super.cleanup();
    }
    
    /**
     * 刷新客户端列表
     */
    private void refreshClients() {
        // 2.刷新服务端本地列表
        List<ConnData> clients = gameServer.getClients();
        clientPanel.setClients(clients);
    }
    
    private class LanRoomServerListener extends AbstractServerListener {

        public LanRoomServerListener(Application app) {
            super(app);
        }

        @Override
        protected void onClientsUpdated(GameServer gameServer) {
            super.onClientsUpdated(gameServer);
            refreshClients();
        }

        @Override
        protected void processServerMessage(GameServer gameServer, HostedConnection source, Message m) {}

        @Override
        protected void onClientRemoved(GameServer gameServer, HostedConnection conn) {}

        @Override
        public void update(float tpf, GameServer gameServer) {}

        @Override
        public void addSyncObject(Object syncObject) {}

        @Override
        public boolean removeSyncObject(Object syncObject) {return false;}

        @Override
        public void cleanup() {}
        
    }
    
}