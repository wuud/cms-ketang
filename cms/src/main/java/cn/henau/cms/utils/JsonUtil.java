package cn.henau.cms.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	
	/**
	 * 将数据转成JSON格式
	 */
	public static String getJSONString(int code,String msg) {
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toJSONString();
		
	}
	/**
	 * 将对象转为JSON字符串
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj) {
		String string = JSON.toJSONString(obj);
		return string;
	}
	
	/**
	 * 将JSON字符串转为Java对象
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T jsonToObject(String json,Class<T> clazz) {
		T t=(T) JSON.parseObject(json, clazz);
		return t;
	}
	

}
