package com.xxl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

public class CommonUtils {
	private static final char[] charSource = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9' };
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		else if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;
		else if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();
		else if (obj instanceof Map)
			return ((Map) obj).isEmpty();
		else if (obj.getClass().isArray())
			return Array.getLength(obj) == 0;
		return false;
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	public static <T> T getBean(Class<T> theClass, HttpServletRequest reques) {
		T bean = null;
		try {
			bean = theClass.newInstance();
			// 没有Apache的包暂时注释一下
//			BeanUtils.populate(bean, reques.getParameterMap());
		} catch (Exception e) {
			LOGGER.error("map转换成bean对象出错", e);
		}
		return bean;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}

	public static String generateStr(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字		
			if ("char".equalsIgnoreCase(charOrNum)) // 字符串
				sb.append((char) (97 + random.nextInt(26)));
			else if ("num".equalsIgnoreCase(charOrNum)) // 数字
				sb.append(String.valueOf(random.nextInt(10)));				
		}
		String returnStr = sb.toString();
		if(isAllDigit(returnStr) || isAllLetter(returnStr))			
			generateStr(length);							
		return sb.toString();		
	}
	public static boolean isAllDigit(String str) {  
	    return str.matches("[0-9]{1,}");  
	} 
	public static boolean isAllLetter(String str) {  
		return str.matches("[a-zA-Z]+");  
	} 
	public static void main(String[] args) {		
		for (int i = 0; i < 1000000; i++) {
			System.err.println(generateStr(6));
		}			
	}
}
