/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.luoying.loader;

import java.util.ArrayList;
import java.util.List;
import name.huliqing.luoying.data.define.TradeInfo;
import name.huliqing.luoying.xml.Proto;

/**
 * 用于帮助载入tradeInfos参数。
 * @author huliqing
 */
public class TradeObjectLoaderHelper {
    
    /**
     * 载入TradeInfos信息，参数格式必须是:  tradeInfos="objectId|count,objectId|count,...", 如果没有指定tradeInfos则
     * 该方法返回null.
     * @param proto
     * @return 
     */
    public static List<TradeInfo> loadTradeInfos(Proto proto) {
        // format:  tradeInfos="objectId|count, objectId|count,..."
        String[] temps = proto.getAsArray("tradeInfos");
        if (temps != null) {
            List<TradeInfo> tradeInfos = new ArrayList<TradeInfo>();
            for (String temp : temps) {
                String[] tiArr = temp.split("\\|");
                TradeInfo tradeInfo = new TradeInfo();
                tradeInfo.setObjectId(tiArr[0]);
                if (tiArr.length > 1) {
                    tradeInfo.setCount(Integer.parseInt(tiArr[1]));
                } else {
                    tradeInfo.setCount(1);
                }
            }
            return tradeInfos;
        }
        return null;
    }
}
