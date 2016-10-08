/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package name.huliqing.ly.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.FormatterClosedException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 资源文件读取类
 * @author huliqing
 */
public class ResManager {
    private static final Logger LOG = Logger.getLogger(ResManager.class.getName());
    
    // KEY -> locale, Value = ResourceMap
    private final static Map<String, Map<String, String>> LOCALE_RES = new HashMap<String, Map<String, String>>();
    private final static Map<String, String> DEFAULT_RES = new HashMap<String, String>();
    
    /**
     * 当前要使用哪一个本地环境
     */
    private static String locale;
    
    /**
     * 载入classPath路径下的资源文件,示例: <br>
     * <code>
     * <pre>
     *  loadResource("/data/resource", "utf-8", "zh_CN")
     * </pre>
     * </code>
     * @param classPathFile classPath资源路径
     * @param encoding 字符集编码，如果没有指定则默认使用"utf-8"
     * @param locale 
     * @see #loadResource(java.io.InputStream, java.lang.String, java.lang.String) 
     */
    public static void loadResource(String classPathFile, String encoding, String locale) {
        loadResource(ResManager.class.getResourceAsStream(classPathFile), encoding, locale);
    }
    
    /**
     * 载入资源文件, 可以指定语言环境，如果没有指定，则载入到默认资源环境。
     * @param is 资源流
     * @param encoding 字符集编码，如果没有指定则默认使用"utf-8"
     * @param locale 指定一个语言环境, 如：en, en_US, zh, zh_CN, h_HK, zh_TW, ... 如果没有指定则载入到默认资源环境。
     * @see #loadResource(java.lang.String, java.lang.String, java.lang.String) 
     */
    public static void loadResource(InputStream is, String encoding, String locale) {
        Map<String, String> resLoaded = loadResource(is, encoding != null ? encoding : "utf-8");
        if (locale != null) {
            Map<String, String> resLocale = LOCALE_RES.get(locale);
            if (resLocale == null) {
                resLocale = new HashMap<String, String>();
                LOCALE_RES.put(locale, resLocale);
            }
            resLocale.putAll(resLoaded);
        } else {
            DEFAULT_RES.putAll(resLoaded);
        }
    }
    
    /**
     * 载入资源文件，如果resource指定的名称不是绝对路径，则默认从/data/font/下查找资源文件。
     * 使用"/"开头来指定绝对资源文件的路径。
     * @param resource
     * @return 
     */
    private static Map<String, String> loadResource(InputStream is, String encoding) {
        BufferedReader br = null;
        Map<String, String> result = new HashMap<String, String>();
        try {
            // 必须指定编码格式，否则在非utf-8平台（如win）下中文会乱码。
            br = new BufferedReader(new InputStreamReader(is, encoding));
            String line;
            int index;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                index = line.indexOf("=");
                if (index == -1) {
                    continue;
                }
                String key = line.substring(0, index).trim();
                String value = line.substring(index + 1); // 没有trim()
                result.put(key, value);
            }
        } catch (IOException ioe) {
            Logger.getLogger(ResManager.class.getName()).log(Level.SEVERE, ioe.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    Logger.getLogger(ResManager.class.getName()).log(Level.SEVERE, e.getMessage());
                }
            }
        }
        return result;
    }
    
    /**
     * 设置要特别使用的本地环境，如：en, en_US, zh, zh_CN, h_HK, zh_TW, ... 当设置这个本地环境之后，
     * 将一直使用，除非进行重新设置或清除. 
     * @param locale 
     */
    public static void setLocale(String locale) {
        ResManager.locale = locale;
    }
    
    /**
     * 获取当前使用的本地环境
     * @return 
     */
    public static String getLocale() {
        if (locale == null) {
            locale = Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry();
        }
        return locale;
    }
    
   /**
     * 清理所有已经载入的资源信息
     */
    public static void clearResources() {
        LOCALE_RES.clear();
        DEFAULT_RES.clear();
    }
    
   /**
     * 从默认资源文件中获取资源
     * @param key
     * @return 
     * @see #get(java.lang.String, java.lang.Object[]) 
     * 
     */
    public static String get(String key) {
        return get(key, null, getLocale());
    }
    
    /**
     * 从默认资源文件中获取资源
     * @param key
     * @param params 参数
     * @return 
     * @see #get(java.lang.String) 
     */
    public static String get(String key, Object[] params) {
        return get(key, params, getLocale());
    }
    
    /**
     * 从默认资源文件中获取资源
     * @param key
     * @param params
     * @param locale
     * @return 
     */
    private static String get(String key, Object[] params, String locale) {
        if (locale == null || !LOCALE_RES.containsKey(locale)) {
            return getString(DEFAULT_RES, key, params);
        }
        return getString(LOCALE_RES.get(locale), key, params);
    }
    
    private static String getString(Map<String, String> resource, String key, Object[] params) {
        String value = resource.get(key);
        if (value == null) {
            value = "<" + key + ">";
        }
        if (params != null) {
            try {
                value = String.format(value, params);
            } catch (FormatterClosedException fce) {
                LOG.log(Level.WARNING, fce.getMessage(), fce);
                value = "<" + key + ">";
            }
        }
        return value;
    }
    
}