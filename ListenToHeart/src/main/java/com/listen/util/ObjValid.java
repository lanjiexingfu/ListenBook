package com.listen.util;

import java.util.List;

/**
 * obj验证条件集合类
 * @author Andyxixi
 *
 */
public class ObjValid {

	/**
	 * 判断条件是否有效
	 * @param objs
	 */
	public static boolean isValid(Object... objs) {
		for (int i = 0; i < objs.length; i++) {
			if(null == objs[i]  ) {
				return false;
			}
			if(objs[i] instanceof String) {
				String key = (String)objs[i];
				if(key.trim().equals("")) {
					return false;
				}
				if(key.trim().equals("-1")) {
					return false;
				}
			} else if(objs[i] instanceof Integer) {
				Integer key = (Integer)objs[i];
				if(key.intValue()<=0) {
					return false;
				}
			} else if(objs[i] instanceof Double) {
				Double key = (Double)objs[i];
				if(key.doubleValue()<=0) {
					return false;
				}
			} else if(objs[i] instanceof Byte) {
				Byte key = (Byte)objs[i];
				if(key.byteValue() < 0) {
					return false;
				}
			}else if (objs[i] instanceof Long) {
				Long key = (Long)objs[i];
				if(key.longValue() <= 0) {
					return false;
				}
			}else if (objs[i] instanceof List<?>) {
				List<?> key = (List<?>)objs[i];
				if(key.size() <= 0) {
					return false;
				}
			}
		}
		return true;
	}
}
