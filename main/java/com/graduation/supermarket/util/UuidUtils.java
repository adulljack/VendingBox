package com.graduation.supermarket.util;

import java.util.UUID;

/***
 * uuid
 * Title:UuidUtils   
 * @author zhonglz
 * @date 2019年9月19日
 */
public class UuidUtils {

	/***
	 * 生成32位uuid唯一标识
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println("uuid:"+uuid());
	}
}
