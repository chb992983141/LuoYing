/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.editor.action;

/**
 *
 * @author huliqing
 */
public class ActionManager  {
//    private static final Logger LOG = Logger.getLogger(EditorInputManager.class.getName());
//    private Editor editor;
//    private boolean initialized;
//    
//    private final String MAP_ML = "ML";
//    private final String MAP_MR = "MR";
//    private final String MAP_MM = "MM";
//    private final String MAP_KEY_SHIFT = "SHIFT";
//    
//    // 控制模式：全局、局部
//    private final String MAP_MODE = "Mode";
//    private final int KEY_MODE = KeyInput.KEY_TAB;
//    private int modeIndex;
//    
//    // 控制行为: 移动、旋转、缩放
//    private final String MAP_ACTION_MOVE = "G";
//    private final String MAP_ACTION_ROTATION = "R";
//    private final String MAP_ACTION_SCALE = "S";
//    private final int KEY_ACTION_MOVE = KeyInput.KEY_G;
//    private final int KEY_ACTION_ROTATION = KeyInput.KEY_R;
//    private final int KEY_ACTION_SCALE = KeyInput.KEY_S;
//    
//    private boolean mouse_left_pressed;
//    private boolean mouse_middle_pressed;
//    private boolean mouse_right_pressed;
//    private boolean key_shift_pressed;
//    
//    // 最近一次记录光标的位置
//    private final Vector2f lastCursorPos = new Vector2f();
//    // 最近一次记录的相机所跟随的物体的位置
//    private final Vector3f lastChasePos = new Vector3f();
//    // 相机的移动速度，默认1.0f
//    private final float camMoveSpeed = 1.0f;
//    // 相机的缩放速度
//    private final float camZoomSpeed = 1.0f;
//    
//    private final SafeArrayList<SimpleAction> simpleActions = new SafeArrayList<SimpleAction>(SimpleAction.class);
//    private final SafeArrayList<ComplexAction> complexActions = new SafeArrayList<ComplexAction>(ComplexAction.class);
//    
//    public void initialize(Editor editor) {
//        if (initialized) {
//            return;
//        }
//        initialized = true;
//        this.editor = editor;
//        
//        lastCursorPos.set(editor.getInputManager().getCursorPosition());
//        if (editor.getForm() != null) {
//            lastChasePos.set(editor.getForm().getChaseObj().getLocalTranslation());
//        }
//        
//        // 鼠标按键
//        InputManager inputManager = editor.getInputManager();
//        Trigger triggerML = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
//        Trigger triggerMR = new MouseButtonTrigger(MouseInput.BUTTON_RIGHT);
//        Trigger triggerMM= new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE);
//        inputManager.addMapping(MAP_ML, triggerML);
//        inputManager.addMapping(MAP_MR, triggerMR);
//        inputManager.addMapping(MAP_MM, triggerMM);
//        
//        // 键盘按键SHIFT
//        Trigger triggerKLShift = new KeyTrigger(KeyInput.KEY_LSHIFT);
//        Trigger triggerKRShift = new KeyTrigger(KeyInput.KEY_RSHIFT);
//        inputManager.addMapping(MAP_KEY_SHIFT, triggerKLShift, triggerKRShift);
//        
//        // 键盘按键: 控制模式
//        inputManager.addMapping(MAP_MODE, new KeyTrigger(KEY_MODE));
//        
//        // 键盘按键: 控制操作行为：移动、旋转、缩放
//        inputManager.addMapping(MAP_ACTION_MOVE, new KeyTrigger(KEY_ACTION_MOVE));
//        inputManager.addMapping(MAP_ACTION_ROTATION, new KeyTrigger(KEY_ACTION_ROTATION));
//        inputManager.addMapping(MAP_ACTION_SCALE, new KeyTrigger(KEY_ACTION_SCALE));
//        
//        // --------- mapping
//        String[] mpNames = new String[] {
//                // 鼠标
//              MAP_ML, MAP_MR, MAP_MM 
//                // 左右Shift
//            , MAP_KEY_SHIFT 
//                // 切换模式
//            , MAP_MODE
//                // 切换操作行为
//            , MAP_ACTION_MOVE, MAP_ACTION_ROTATION, MAP_ACTION_SCALE 
//        };
//        inputManager.addListener(this, mpNames);
//        // 跟随距离来调整相机的缩放强度，距离越大，缩放强度越大
//        inputManager.addListener(this, CameraInput.CHASECAM_ZOOMIN);
//        inputManager.addListener(this, CameraInput.CHASECAM_ZOOMOUT);
//        
//        // 添加行为
////        addAction(new TranslationAction(editor), MAP_ML);
////        addAction(new ScaleAction(editor), MAP_ML);
////        addAction(new RotationAction(editor), MAP_ML);
//    }
//    
//    private void addAction(Action action, String... mappingNames) {
//        editor.getInputManager().addListener(action, mappingNames);
//        if (action instanceof SimpleAction) {
//            if (!simpleActions.contains((SimpleAction)action)) {
//                simpleActions.add((SimpleAction) action);
//            }
//        } else if (action instanceof ComplexAction) {
//            if (!complexActions.contains((ComplexAction) action)) {
//                complexActions.add((ComplexAction) action);
//            }
//        } else {
//            throw new UnsupportedOperationException("Unsupported action type=" + action);
//        }
//    }
//    
//    @Override
//    public void onAction(String name, boolean isPressed, float tpf) {
//        
//        // 鼠标: 左\中\右键操作
//        if (MAP_ML.equals(name)) {
//            mouse_left_pressed = isPressed; 
//            lastCursorPos.set(editor.getInputManager().getCursorPosition());
//            lastChasePos.set(editor.getForm().getChaseObj().getLocalTranslation());
//        } 
//        
////        // 鼠标选择物体
////        else if (MAP_MR.equals(name)) {
////            mouse_right_pressed = isPressed;
////            lastCursorPos.set(editor.getInputManager().getCursorPosition());
////            lastChasePos.set(editor.getForm().getChaseObj().getLocalTranslation());
////            // 调用pick方法
////            editor.getForm().onPick();
////        } 
//        
////        // 鼠标中键
////        else if (MAP_MM.equals(name)) {
////            mouse_middle_pressed = isPressed;
////            lastCursorPos.set(editor.getInputManager().getCursorPosition());
////            lastChasePos.set(editor.getForm().getChaseObj().getLocalTranslation());
////        } 
////        
////        // 按键: SHIFT(左、右)
////        else if (MAP_KEY_SHIFT.equals(name)) {
////            key_shift_pressed = isPressed;
////            lastCursorPos.set(editor.getInputManager().getCursorPosition());
////            lastChasePos.set(editor.getForm().getChaseObj().getLocalTranslation());
////            
////        } 
//        
////        // 切换模式
////        else if (MAP_MODE.equals(name)) {
////            if (isPressed) return;
////            modeIndex++;
////            if (modeIndex >= Mode.values().length) {
////                modeIndex = 0;
////            }
////            editor.getForm().setTransformMode(Mode.values()[modeIndex]);
////        }
////        
////        // 切换操作行为
////        else if (MAP_ACTION_MOVE.equals(name)) {
////            if (isPressed) return;
////            editor.getForm().setTransformType(TransformType.LOCATION);
////        } else if (MAP_ACTION_ROTATION.equals(name)) {
////            if (isPressed) return;
////            editor.getForm().setTransformType(TransformType.ROTATION);
////        } else if (MAP_ACTION_SCALE.equals(name)) {
////            if (isPressed) return;
////            editor.getForm().setTransformType(TransformType.SCALE);
////        }
//        
//        // 跟随距离来调整相机的缩放强度，距离越大，缩放强度越大
//        else if (CameraInput.CHASECAM_ZOOMIN.equals(name) || CameraInput.CHASECAM_ZOOMOUT.equals(name)) {
//            float distance = editor.getCamera().getLocation().distance(editor.getForm().getChaseObj().getLocalTranslation());
//            editor.getForm().getEditorCamera().setZoomSensitivity(distance * 0.1f * camZoomSpeed);
//            
//        } 
//        actionCheck();
//    }
//    
//    private void actionCheck() {
//        if (mouse_middle_pressed && key_shift_pressed) {
//            editor.getForm().getEditorCamera().setEnableRotation(false);
//            editor.getForm().getEditorCamera().setEnabledRotationV(false);
//        } else {
//            editor.getForm().getEditorCamera().setEnableRotation(true);
//            editor.getForm().getEditorCamera().setEnabledRotationV(true);
//        }
//    }
//
//    public void update(float tpf) {
//        for (ComplexAction action : complexActions.getArray()) {
//            action.update(tpf);
//        }
//        
//        // 平移操作
//        if (mouse_middle_pressed && key_shift_pressed) {
//           Vector2f cp = editor.getInputManager().getCursorPosition();
//           float xDist = cp.x - lastCursorPos.x;
//           float yDist = cp.y - lastCursorPos.y;
//           if (xDist != 0 || yDist != 0) {
//               TempVars tv = TempVars.get();
//               Vector3f camDir = editor.getCamera().getDirection(tv.vect1);
//               Vector3f right = tv.vect2.set(camDir).crossLocal(Vector3f.UNIT_Y).normalizeLocal();
//               Vector3f up = tv.vect3.set(right).crossLocal(camDir).normalizeLocal();
//               // 允许根据距离来调节移动速度
//               float distance = editor.getCamera().getLocation().distance(lastChasePos)  * 0.002f;
//               Vector3f chasePos = tv.vect4.set(lastChasePos);
//               chasePos.subtractLocal(right.multLocal(xDist * camMoveSpeed * distance));
//               chasePos.subtractLocal(up.multLocal(yDist * camMoveSpeed * distance));
//               editor.getForm().getChaseObj().setLocalTranslation(chasePos);
//               tv.release();
//           }
//        }
//    }


}
