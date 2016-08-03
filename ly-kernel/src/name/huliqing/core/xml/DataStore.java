/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.core.xml;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 数据容器，存储所有的最原始数据。
 * @author huliqing
 */
class DataStore {
    private final static Logger LOG = Logger.getLogger(DataStore.class.getName());
    
    private final static Map<String, Proto> PROTO_MAP = new HashMap<String, Proto>();
    private final static List<String> SCRIPTS = new ArrayList<String>();
    
    /**
     * 直接获取原形数据，这是最原始的数据，不会处理继承关系，作为内部方法调用，不要直接使用。外部调用应该使用
     * {@link DataFactory#getProto(java.lang.String) }
     * @param id
     * @return 
     */
    public Proto getProto(String id) {
        String tmp = id.trim();
        Proto result = PROTO_MAP.get(tmp);
        if (result != null) {
            return result;
        }
        LOG.log(Level.WARNING, "Unknow objectId={0}", tmp);
        return null;
    }
    
    /**
     * Get all javascripts, this method will return an unmodifiableList, so don't try to modify the list.
     * @return 
     */
    public List<String> getJavaScripts() {
        return Collections.unmodifiableList(SCRIPTS);
    }
    
    /**
     * Get all proto data, this will return an unmodifiable collection.
     * @return 
     */
    public Collection<Proto> findAll() {
        return Collections.unmodifiableCollection(PROTO_MAP.values());
    }
    
    /**
     * 载入数据，这些数据将会添加到当前数据容器中,如果容器中存在重复ID的数据，则数据会被覆盖替换。
     * @param dataFile e.g. "/data/xxx.xml"
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public void loadDataFile(String dataFile) throws ParserConfigurationException, SAXException, IOException  {
        Element root = XmlUtils.newDocument(readFile(dataFile))
                .getDocumentElement();
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            String tagName = ((Element) node).getTagName();
            Element ele = (Element) node;
            
            // 提取脚本
            if (tagName.equals("script")) {
                SCRIPTS.add(ele.getTextContent());
                continue;
            }
            
            Proto objectDef = new Proto(XmlUtils.getAttributes(ele), tagName);
            PROTO_MAP.put(objectDef.getId(), objectDef);
        }
    }
    
    /**
     * 将文件读取为字符串.
     * @param path
     * @return 
     */
    private String readFile(String path) {
        BufferedInputStream bis = null;
        String result = null;
        try {
        InputStream is = DataStore.class.getResourceAsStream(path);
            bis = new BufferedInputStream(is);
            byte[] buff = new byte[2048];
            int len;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = bis.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            // 必须指定编码，否则在win下可能中文乱码
            result = baos.toString("utf-8");
        } catch (IOException ioe) {
            Logger.getLogger(DataStore.class.getName())
                    .log(Level.SEVERE, "Couldnot read file: {0}", path);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    Logger.getLogger(DataStore.class.getName())
                            .log(Level.SEVERE, "Could not close stream!", e);
                }
            }
        }
        return result;
    }

}
