package com.graduation.supermarket.util;

/**
 * 微信支付配置文件
 * @author zhonglz
 * 
 */
public class WeixinPayConfig {

	public static final String appid="wxab03664a5c68f838"; // 公众账号ID
	
	public static final String mch_id="1494819742"; // 商户号
	
	public static final String device_info="WEB"; // 设备号
	
	public static final String set_order_url="https://api.mch.weixin.qq.com/pay/unifiedorder"; //统一下单
	
	public static final String select_order_url="https://api.mch.weixin.qq.com/pay/orderquery";//查询订单地址
	
	public static final String close_order_url="https://api.mch.weixin.qq.com/pay/closeorder";//关闭订单
	
	public static final String notify_url="http://aeyjp8.natappfree.cc"; // 异步通知url
	
	public static final String key="9shE11ik6KFID7HAhfRkdI4iO4cXn57S"; // 商户的key【API密钥】
	
	//jsapi支付统一下单
	public static final String jsapi_set_order_url="https://api.mch.weixin.qq.com/pay/unifiedorder";
		
	// jsapi支付通知url
	public static final String jsapi_notify_url="http://网址域名/WeChatPay/wx/jsApi/wxPayNotify";

}
