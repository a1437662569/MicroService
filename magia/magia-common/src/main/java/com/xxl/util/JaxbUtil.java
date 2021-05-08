package com.xxl.util;

import com.xxl.common.enums.CommonConsts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/** 
 * Jaxb2工具类 
 * @author      xiashunhui 
 * @create      2017-06-01 下午2:40:14 
 */  
public class JaxbUtil {  
	protected final static Logger logger = LogManager.getLogger(JaxbUtil.class);
    /** 
     * JavaBean转换成xml 
     * 默认编码UTF-8 
     * @param obj
     * @return
     */  
    public static String convertToXml(Object obj) {  
        return convertToXml(obj, "utf-8");  
    }  
  
    /** 
     * JavaBean转换成xml 
     * @param obj 
     * @param encoding  
     * @return  
     */  
    public static String convertToXml(Object obj, String encoding) {  
        String result = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
            Marshaller marshaller = context.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString().replace(" standalone=\"yes\"", "");
        } catch (Exception e) {  
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));
        }  
  
        return result;  
    }  
  
    /** 
     * xml转换成JavaBean 
     * @param xml 
     * @param c 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T converyToJavaBean(String xml, Class<T> c) {  
        T t = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(c);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            t = (T) unmarshaller.unmarshal(new StringReader(xml));  
        } catch (Exception e) {  
            logger.error(CommonConsts.LOG_ERROR_STR, StringUtil.string2Json(StringUtil.getStackTraceInfo(e)));  
        }  
  
        return t;  
    }  
}  
