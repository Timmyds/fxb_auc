package com.fxb.world.service.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;


public class StringTools {

	private static DecimalFormat formatDouble = new DecimalFormat("0.00");
	private static DecimalFormat dFormat2 = new DecimalFormat("0.00");
	private static DecimalFormat dFormat1 = new DecimalFormat("0.0");
	
	private static final String STR_FORMAT = "000000";

	/**
	 * 产生四位随机数
	 */
	public static String randomCode4() {
		int intCount = new Random().nextInt(9999);
		if (intCount < 1000)
			intCount += 1000;
		return intCount + "";
	}
	
	/**
	 * 产生六位随机数
	 */
	public static String randomCode6() {
		int intCount = new Random().nextInt(999999);
		if (intCount < 100000)
			intCount += 100000;
		return intCount + "";
	}

	// length表示生成字符串的长度
	public static String getRandomStr(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 一位小数点
	public static String formatDouble(Object obj) {
		if (obj != null) {
			return formatDouble.format(obj);
		}
		return "0.00";
	}



	// 参数判断，支持正则表达式
	public static boolean checkValue(Object obj, String[] rule) throws IllegalArgumentException {
		if (obj == null)
			obj = "";
		String str = obj.toString().trim();
		int strLen = str.length();

		String key = rule[0];
		int minLen = Integer.valueOf(rule[1]);
		int maxLen = Integer.valueOf(rule[2]);

		if (strLen > maxLen || strLen < minLen) {// 判断长度
			throw new IllegalArgumentException(UserConstants.RESULT_ILLEGAL_ARGUMENT_LEN.replace("{key}", key) + ",长度范围["
					+ minLen + "," + maxLen + "]");
		} else {// 正则判断
			if (StringUtils.isNotBlank(str) && rule[3] != null && !str.matches(rule[3]))
				throw new IllegalArgumentException(
						UserConstants.RESULT_ILLEGAL_ARGUMENT_FORMAT.replace("{key}", key) + "," + rule[4]);
		}
		return true;
	}
	
	// 按字节获取字符串长度（中文算三个字节）
	public static int getLen(String str) throws Exception {
		if (StringUtils.isNotBlank(str)) {
			return str.getBytes(UserConstants.CHARSET_UTF8).length;
		}
		return 0;
	}

	// 获取ARRAY
	public static String[] getArray(String fileName) {
		if (!StringUtils.isBlank(fileName)) {
			return fileName.split(",");
		}
		return new String[0];
	}

	// 一位小数点
	public static String dFormat1(Double val) {
		if (val != null) {
			return dFormat1.format(val);
		}
		return "0.0";
	}

	// 两位小数点
	public static String dFormat2(Double val) {
		if (val != null) {
			return dFormat2.format(val);
		}
		return "0.00";
	}

	// 二进制转字符串
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String tmp = "";
		for (int i = 0; i < b.length; i++) {
			tmp = Integer.toHexString(b[i] & 0XFF);
			if (tmp.length() == 1) {
				sb.append("0" + tmp);
			} else {
				sb.append(tmp);
			}

		}
		return sb.toString();
	}

	// 字符串转二进制
	public static byte[] hex2byte(String str) {
		if (str == null) {
			return null;
		}

		str = str.trim();
		int len = str.length();

		if (len == 0 || len % 2 == 1) {
			return null;
		}

		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 传入对象，通过实体设置的nullable,length 判断是否合法；
	 * @param obj
	 * @return 返回null 表示验证通过，返回其他值，则不通过
	 */
	public static String checkEntityProperty(Object obj) {
		return checkEntityProperty(obj, "add");
	}
	
	/**
	 * 针对修改操作验证
	 * 传入对象，通过实体设置的length 判断是否合法；
	 * @param obj
	 * @return 返回null 表示验证通过，返回其他值，则不通过
	 */
	public static String checkEntityPropertyForUpdate(Object obj) {
		return checkEntityProperty(obj, "update");
	}
	
	/**
	 * 传入对象，通过实体设置的nullable,length 判断是否合法；
	 * @param obj
	 * @param oper  如果是添加操作则验证实体属性非空和长度，是修改操作只验证实体属性长度
	 * @return 返回null 表示验证通过，返回其他值，则不通过
	 */
	private static String checkEntityProperty(Object obj,String oper) {
		String msg = null;
		if (obj == null) {
			return msg = "对象为空";
		}
		Class<? extends Object> clazz = obj.getClass();
		try {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				String name = method.getName();
				if (name.startsWith("get") && !"getClass".equals(name)) {
//					if (method.getReturnType() == String.class) {
					Class<?> returnType = method.getReturnType();
					if(!(returnType==int.class||returnType==byte.class||returnType==long.class
							||returnType==char.class||returnType==double.class||returnType==short.class
							||returnType==boolean.class)){
						String fieldName = name.substring(3);
						if (StringUtils.isNotBlank(fieldName) && fieldName.length() > 1) {
							char c = fieldName.charAt(0);
							fieldName = String.valueOf(c).toLowerCase() + fieldName.substring(1);
						}
						Field field = clazz.getDeclaredField(fieldName);
						if (field != null) {
							Column column = field.getAnnotation(Column.class);
							if (column != null) {
								Object value = method.invoke(obj);
								if ("add".equals(oper)) {
									//添加操作验证非空
									if (!column.nullable()) {
										// 不能为空
										if (value == null || StringUtils.isBlank(value.toString())) {
											return msg = "属性:" + fieldName + "的值为空";
										}
									}
								}
								if (value != null && StringUtils.isNotBlank(value.toString())) {
									if (value.toString().length() > column.length()) {
										return msg =  "属性:" + fieldName + "的值超出" + column.length() + "位长度";
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		} finally {
			return msg;
		}
	}

	/**
	 * unicode 转字符串
	 * @param utfString
	 * @return
	 */
	public static String unicodeToString(String utfString){  
	    StringBuilder sb = new StringBuilder();  
	    int i = -1;  
	    int pos = 0;  
	      
	    while((i=utfString.indexOf("\\u", pos)) != -1){  
	        sb.append(utfString.substring(pos, i));  
	        if(i+5 < utfString.length()){  
	            pos = i+6;  
	            sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));  
	        }  
	    }  
	      
	    return sb.toString();  
	}
	
	
	public static synchronized String numberAddOne(String serialNumber){  
        Integer newSerialNumber = Integer.parseInt(serialNumber);  
        newSerialNumber++;  
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        return df.format(newSerialNumber);
    }
	
	/** 
     *  MurMurHash算法，是非加密HASH算法，性能很高， 
     *  比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免） 
     *  等HASH算法要快很多，而且据说这个算法的碰撞率很低. 
     *  http://murmurhash.googlepages.com/ 
     */
	public static Long hash(String key) {  
        
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());  
        int seed = 0x1234ABCD;  
          
        ByteOrder byteOrder = buf.order();  
        buf.order(ByteOrder.LITTLE_ENDIAN);  
  
        long m = 0xc6a4a7935bd1e995L;  
        int r = 47;  
  
        long h = seed ^ (buf.remaining() * m);  
  
        long k;  
        while (buf.remaining() >= 8) {  
            k = buf.getLong();  
  
            k *= m;  
            k ^= k >>> r;  
            k *= m;  
  
            h ^= k;  
            h *= m;  
        }  
  
        if (buf.remaining() > 0) {  
            ByteBuffer finish = ByteBuffer.allocate(8).order(  
                    ByteOrder.LITTLE_ENDIAN);  
            // for big-endian version, do this first:   
            // finish.position(8-buf.remaining());   
            finish.put(buf).rewind();  
            h ^= finish.getLong();  
            h *= m;  
        }  
  
        h ^= h >>> r;  
        h *= m;  
        h ^= h >>> r;  
  
        buf.order(byteOrder);  
        return h;
    }  
	/**
	 * List<String> 转字符串','隔开
	 * @param list<String>
	 * @return
	 */
	public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (String str : list) {
            result.append("'"+str.toString()+"',");
        }
        String str = result.toString();
        if(str.length()>0){
        	str=str.substring(0,str.length()-1);
        }
        return str;
    }
	/**
	 * 去除字符串中的空格、回车、换行符、制表符 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
