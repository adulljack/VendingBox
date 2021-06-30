package com.graduation.supermarket.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/***
 * 微信支付的一些工具方法
 * @author zhonglz
 *
 */
public class WxPayUtil {


	/**
     * 微信支付签名算法sign
     */
    public static String getSign(Map<String,Object> map) {
        StringBuffer sb = new StringBuffer();
        String[] keyArr = (String[]) map.keySet().toArray(new String[map.keySet().size()]);//获取map中的key转为array
        Arrays.sort(keyArr);//对array排序
        for (int i = 0, size = keyArr.length; i < size; ++i) {
            if ("sign".equals(keyArr[i])) {
                continue;
            }
            sb.append(keyArr[i] + "=" + map.get(keyArr[i]) + "&");
        }
        sb.append("key=" + WeixinPayConfig.key);
        String sign = Md5Util.string2MD5(sb.toString());
        return sign;
    }

    /**
   	 * 通过返回IO流获取支付地址
   	 * @param in
   	 * @return
   	 */
   	public static String getElementValue(InputStream in,String key){
   		SAXReader reader = new SAXReader();
           Document document=null;
   		try {
   			document = reader.read(in);
   		} catch (DocumentException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
           Element root = document.getRootElement();
           List<Element> childElements = root.elements();
           for (Element child : childElements) {
           	System.out.println(child.getName()+":"+child.getStringValue());
           	if(key.equals(child.getName())){
           		return child.getStringValue();
           	}
           }
           return null;
   	}
   	
   	/***
	 * 回调返回信息
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	public  static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }
	
	/**
     * 获取客户端真实ip地址
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request){
        /**
         * 获取距离服务器最远的那个ip
         */
        String ip = request.getHeader("x-forwarded-for");
        if (ipIsNullOrEmpty(ip)){
            /**
             * apache http服务代理加上的ip
             */
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ipIsNullOrEmpty(ip)){
            /**
             * weblogic插件加上的头
             */
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipIsNullOrEmpty(ip)){
            /**
             * 真实ip
             */
            ip = request.getHeader("X-Real-IP");
        }
        if (ipIsNullOrEmpty(ip)){
            /**
             * 最后真实的ip
             */
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 当前ip是否为空
     * @param ip
     * @return
     */
    public static boolean ipIsNullOrEmpty(String ip){
        if(ip == null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            return true;
        }
        return false;
    }
	
}
