/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.luoying.loader;

import java.util.logging.Level;
import java.util.logging.Logger;
import name.huliqing.luoying.data.AttributeData;
import name.huliqing.luoying.data.DropData;
import name.huliqing.luoying.data.LogicData;
import name.huliqing.luoying.data.ChannelData;
import name.huliqing.luoying.data.ChatData;
import name.huliqing.luoying.data.EntityData;
import name.huliqing.luoying.data.ItemData;
import name.huliqing.luoying.xml.Proto;
import name.huliqing.luoying.data.ObjectData;
import name.huliqing.luoying.data.ResistData;
import name.huliqing.luoying.data.SkillData;
import name.huliqing.luoying.data.SkinData;
import name.huliqing.luoying.data.TalentData;
import name.huliqing.luoying.manager.ResManager;
import name.huliqing.luoying.xml.DataFactory;

/**
 *
 * @author huliqing
 */
public class ActorDataLoader extends EntityDataLoader {
//    private static final Logger LOG = Logger.getLogger(ActorDataLoader.class.getName());

    @Override
    public void load(Proto proto, EntityData data) {
        super.load(proto, data);
        
        data.setAttribute("name", ResManager.get(proto.getId() + ".name"));
        data.setAttribute("mat", proto.getAsString("mat"));
        
        // ==== 2.items 
        String[] itemsTemp = proto.getAsArray("items");
        if (itemsTemp != null && itemsTemp.length > 0) {
            for (String item : itemsTemp) {
                if (item == null || item.trim().equals("")) {
                    continue;
                }
                String[] itemArr = item.split("\\|");
                String itemId = itemArr[0];
                int itemTotal = 1;
                if (itemArr.length >= 2) {
                    itemTotal = Integer.parseInt(itemArr[1]);
                }
                ObjectData itemData = DataFactory.createData(itemId);
                if (itemData instanceof ItemData) {
                    ((ItemData)itemData).setTotal(itemTotal);
                } else {
                    continue;
                }
                data.addObjectData(itemData);
            }
        }
        
        // skinBase 基本皮肤,
        // 注1：可能部分角色没有基本皮肤，如不可换装备的角色类型
        // 注2: 基本皮肤不会存放在itemStore中，而skinOutfit会存放在itemStore
        String[] skinBases = proto.getAsArray("skinBase");
        if (skinBases != null) {
            for (String sbt : skinBases) {
                SkinData sdb = DataFactory.createData(sbt);
                sdb.setBaseSkin(true);
                sdb.setUsed(false);
                data.addObjectData(sdb);
            }            
        }
        
        // 包裹上的装备，没穿在身上
        String[] skinsArr = proto.getAsArray("skins");
        if (skinsArr != null && skinsArr.length > 0) {
            for (String skinStr : skinsArr) {
                String[] skinArr = skinStr.split("\\|");
                SkinData skinData = DataFactory.createData(skinArr[0]);
                skinData.setTotal(skinArr.length > 1 ? Integer.parseInt(skinArr[1]) : 1);
                skinData.setBaseSkin(false);
                skinData.setUsed(false);
                data.addObjectData(skinData);
            }
        }
        
        // skinOutfit
        String[] skinOutfit = proto.getAsArray("skinOutfit");
        if (skinOutfit != null) {
            for (String skinId : skinOutfit) {
                SkinData sdb = DataFactory.createData(skinId);
                sdb.setBaseSkin(false);
                sdb.setUsed(true);
                data.addObjectData(sdb);
            }
        }
        
        // weapon - 拿在手上的武器
        String[] weaponIds = proto.getAsArray("skinWeapon");
        if (weaponIds != null) {
            for (String wid : weaponIds) {
                SkinData weaponData = (SkinData) DataFactory.createData(wid);
                weaponData.setBaseSkin(false);
                weaponData.setUsed(true);
                data.addObjectData(weaponData);
            }
        }
        
        // 角色动画通道
        String[] channels = proto.getAsArray("channels");
        if (channels != null) {
            for (String id : channels) {
                data.addObjectData((ChannelData)DataFactory.createData(id));
            }
        }
        
        // 物品掉落设置
        String[] drops = proto.getAsArray("drops");
        if (drops != null && drops.length > 0) {
            for (String id : drops) {
                data.addObjectData((DropData)DataFactory.createData(id));
            }
        }
        
        String chat = proto.getAsString("chat");
        if (chat != null) {
            data.addObjectData((ChatData) DataFactory.createData(chat));
        }
        
        // ==== 载入技能
        String[] skillIds = proto.getAsArray("skills");
        if (skillIds != null && skillIds.length > 0) {
            for (String skillId : skillIds) {
                data.addObjectData((SkillData)DataFactory.createData(skillId));
            }
        }
        
        // ==== 载入逻辑
        String[] logics = proto.getAsArray("logics");
        if (logics != null && logics.length > 0) {
            for (String logicId : logics) {
                data.addObjectData((LogicData) DataFactory.createData(logicId));
            }
        }
        
        // 角色属性
        String[] attributeArr = proto.getAsArray("attributes");
        if (attributeArr != null) {
            for (String attrId : attributeArr) {
                data.addObjectData((AttributeData)DataFactory.createData(attrId));
            }
        }
        
        // Resist
        String resist = proto.getAsString("resist");
        if (resist != null) {
            data.addObjectData((ResistData)DataFactory.createData(resist));
        }
        
        // talents
        String[] talentArr = proto.getAsArray("talents");
        if (talentArr != null) {
            for (String talent : talentArr) {
                data.addObjectData((TalentData)DataFactory.createData(talent));
            }
        }
        
        // remove20161010,move to EntityDataLoader.class
//        // 载入模块配置,并根据ModuleOrder进行排序
//        String[] moduleArr = proto.getAsArray("modules");
//        if (moduleArr != null) {
//            data.setModuleDatas(new ArrayList<ModuleData>(moduleArr.length));
//            for (String mid : moduleArr) {
//                data.getModuleDatas().add((ModuleData) DataFactory.createData(mid));
//            }
//            Collections.sort(data.getModuleDatas(), new Comparator<ModuleData>() {
//                @Override
//                public int compare(ModuleData o1, ModuleData o2) {
//                    return o1.getModuleOrder() - o2.getModuleOrder();
//                }
//            });
//        }
        
        
    }
    
    
    
}