package com.graduation.supermarket.Controller;

import com.graduation.supermarket.Entity.Orders;
import com.graduation.supermarket.Service.OrderService;
import com.graduation.supermarket.util.*;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 微信Native支付接口
 * @author zhonglz
 * 2020年2月25日
 */
@Controller
@RequestMapping("wx/pay")
public class WxPayController {

	private static Logger logger = Logger.getLogger(WxPayController.class);

	@Autowired
	private OrderService orderService;
	
	/***
	 * 统一下单 生成二维码链接地址
	 * @return
	 */
	@ResponseBody
		@RequestMapping(value="/wxPayQrcode",method = RequestMethod.POST)
	public Result wxPayQrcode(HttpServletRequest request, HttpServletResponse response, @RequestParam("orderId")String orderId){
		Result result=new Result();
		Orders order = new Orders();
		order = orderService.findById(orderId);
//		String orderNo= DateUtil.getCurrentDateStr(); // 生成订单号
//		logger.info("订单号："+orderNo);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("appid", WeixinPayConfig.appid); // 公众账号ID
		logger.info("公众账号ID："+WeixinPayConfig.appid);
		map.put("mch_id", WeixinPayConfig.mch_id); // 商户号
		logger.info("商户号："+WeixinPayConfig.mch_id);
		map.put("device_info", WeixinPayConfig.device_info); // 设备号
		logger.info("设备号："+WeixinPayConfig.device_info);
		map.put("notify_url", WeixinPayConfig.notify_url); // 异步通知地址
		map.put("trade_type", "NATIVE"); // 交易类型
		map.put("out_trade_no", orderId); // 商户订单号
		map.put("body", new String("微信支付测试z1")); // 商品描述
		BigDecimal bd = order.getTotalFee();
		BigDecimal totalFee = bd.movePointRight(2);
//		map.put("total_fee", 1);
		map.put("total_fee", totalFee); // 标价金额   微信的金额单位：分
		map.put("spbill_create_ip", WxPayUtil.getIp(request)); // 终端IP
		map.put("nonce_str", UuidUtils.uuid()); // 随机字符串
		map.put("sign", WxPayUtil.getSign(map)); // 签名
		String xml= XmlUtil.genXml(map);
		logger.info(xml);
		InputStream in = null;
		String code_url="";
		try {
			// 发现xml消息
			in = HttpClientUtil.sendXMLDataByPost(WeixinPayConfig.set_order_url, xml).getEntity().getContent();
			code_url =WxPayUtil.getElementValue(in,"code_url"); // 获取二维码地址
			logger.info("地址是:"+code_url);
			if(ObjectUtils.isNotEmpty(code_url)){
				//二维码地址存起来。
				result.setCode(Result.CODE_SUCCESS);
				result.setMsg("生成支付二维码成功");
				Map<String,Object> retMap=new HashMap<String,Object>();
				retMap.put("code_url", code_url);
				retMap.put("orderNo", orderId);
				result.setObject(retMap);
			}else{
				result.setCode(Result.CODE_ERROR);
				result.setMsg("生成支付二维码失败");
			}
		} catch (Exception e) {
			result.setCode(Result.CODE_EXCEPTION);
			result.setMsg("发送异常");
		}finally{
			// 释放资源
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
		return result;
	}
	
	
	/***
	 * 查询订单支付情况接口
	 * @param orderNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/wxPaySelectOrder")
	public  Result wxPaySelectOrder(String orderNo,HttpServletRequest request, HttpServletResponse response){
		logger.info("-------------查询支付结果执行开始------------");
		Result result=new Result();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("appid", WeixinPayConfig.appid); // 公众账号ID
		//logger.info("公众账号ID："+WeixinPayConfig.appid);
		
		map.put("mch_id", WeixinPayConfig.mch_id); // 商户号
		//logger.info("商户号："+WeixinPayConfig.mch_id);
		
		//map.put("transaction_id", transaction_id);//微信订单号
		//logger.info("微信订单号:"+transaction_id);
		
		map.put("out_trade_no", orderNo);//商户订单号
		logger.info("商户订单号："+orderNo);
		
		String uuid = UuidUtils.uuid();
		map.put("nonce_str", uuid); // 随机字符串
		//logger.info("随机字符串："+uuid);
		String sign=WxPayUtil.getSign(map);
		map.put("sign", sign); // 签名
		//logger.info("签名："+sign);
		
		String xml=XmlUtil.genXml(map); 
		//logger.info(xml);
		InputStream in = null;
		try {
			in = HttpClientUtil.sendXMLDataByPost(WeixinPayConfig.select_order_url, xml).getEntity().getContent();
			SAXReader reader = new SAXReader();
	        Document document=null;
			try {
				document = reader.read(in);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
	        Element root = document.getRootElement();
	        List<Element> childElements = root.elements();
	        logger.info("返回结果如下：");
	        String return_code="";// 返回状态码
	        String result_code="";// 业务结果
	        String trade_state="";// 获取支付状态
	        for (Element child : childElements) {
	        	if("return_code".equals(child.getName())){
	        		return_code = child.getStringValue();
	           	}
	        	if("result_code".equals(child.getName())){
	        		result_code = child.getStringValue();
	           	}
	        	if("trade_state".equals(child.getName())){
	        		trade_state = child.getStringValue();
	           	}
	        	logger.info(child.getName()+":"+child.getStringValue());
	        }
			logger.info("状态返回码:"+return_code);
			if("SUCCESS".equals(return_code)){
				logger.info("业务结果:"+result_code);
				if("SUCCESS".equals(result_code)){
					logger.info("支付状态:"+trade_state);
					if(trade_state.equals("SUCCESS")){
						//这里写处理订单后续逻辑嘛
						result.setCode(Result.CODE_SUCCESS);
						result.setMsg("支付成功");
					}else{
						result.setCode(Result.CODE_ERROR);
						result.setMsg("支付失败");
					}
				}else{
					result.setCode(Result.CODE_ERROR);
					result.setMsg("业务处理失败");
				}
			}else{
				result.setCode(Result.CODE_ERROR_PARAM);
				result.setMsg("请求参数错误");
			}
			
		} catch (Exception e) {
			result.setCode(Result.CODE_EXCEPTION);
			result.setMsg("发送异常");
		}finally{
			// 释放资源
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
		logger.info("--------------------执行结束-------------------");
		return result;
	}

	
	/****
	 * 微信支付回调方法
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/wxPayNotify")
	public void wxPayNotify(HttpServletRequest request, HttpServletResponse response){
		logger.info("--------------------微信回调地址-------------------");
		// 拿到微信回调信息
        try {
        	PrintWriter writer = response.getWriter();
			InputStream in = request.getInputStream();
			SAXReader reader = new SAXReader();
	        Document document=null;
			try {
				document = reader.read(in);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
	        Element root = document.getRootElement();
	        List<Element> childElements = root.elements();
	        logger.info("返回结果如下：");
	        String return_code="";// 返回状态码
	        String result_code="";// 业务结果
	        String mch_id="";// 商户号
	        String nonce_str=""; //随机字符串
	        String transaction_id="";//微信支付订单号
	        String out_trade_no="";//商户订单号
	        int total_fee=0; //订单金额
	        for (Element child : childElements) {
	        	if("return_code".equals(child.getName())){
	        		return_code = child.getStringValue();
	           	}
	        	if("result_code".equals(child.getName())){
	        		result_code = child.getStringValue();
	           	}
	        	if("mch_id".equals(child.getName())){
	        		mch_id = child.getStringValue();
	           	}
	        	if("nonce_str".equals(child.getName())){
	        		nonce_str = child.getStringValue();
	           	}
	        	if("transaction_id".equals(child.getName())){
	        		transaction_id = child.getStringValue();
	           	}
	        	if("out_trade_no".equals(child.getName())){
	        		out_trade_no = child.getStringValue();
	           	}
	        	if("total_fee".equals(child.getName())){
	        		total_fee = Integer.valueOf(child.getStringValue());
	           	}
	        	logger.info(child.getName()+":"+child.getStringValue());
	        }
	        Result result=new Result();
	       
	        logger.info("状态返回码:"+return_code);
			if("SUCCESS".equals(return_code)){
				logger.info("业务结果:"+result_code);
				if("SUCCESS".equals(result_code)){
					logger.info("支付订单号:"+out_trade_no);
					logger.info("这里写业务逻辑");
					/*** 实际验证过程建议商户务必添加以下校验：
				        *  1、需要验证该通知数据中的 out_trade_no 是否为商户系统中创建的订单号
				        *  2、判断 total_fee 是否确实为该订单的实际金额（即商户订单创建时的金额）
						*
						**/
					
					
					// 通知微信订单处理成功
	                String noticeStr = WxPayUtil.setXML("SUCCESS", "");
	                writer.write(noticeStr);
	                writer.flush();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
        logger.info("--------------------微信回调结束-------------------");
	}
	
	/***
	 * 关闭订单
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/wxPayOrderByClose")
	public Result wxPayOrderByClose(String orderId,HttpServletRequest request, HttpServletResponse response) {
		Result result=new Result();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("appid", WeixinPayConfig.appid); // 公众账号ID
		map.put("mch_id", WeixinPayConfig.mch_id); // 商户号
		map.put("out_trade_no", orderId);//商户订单号
		String uuid = UuidUtils.uuid();
		map.put("nonce_str", uuid); // 随机字符串
		String sign=WxPayUtil.getSign(map);
		map.put("sign", sign); // 签名
		String xml=XmlUtil.genXml(map); 
		logger.info(xml);
		InputStream in = null;
		String return_code="";// 返回状态码
	    String result_code="";// 业务结果
		try {
			// 发现xml消息
			in = HttpClientUtil.sendXMLDataByPost(WeixinPayConfig.close_order_url, xml).getEntity().getContent();
			SAXReader reader = new SAXReader();
	        Document document=null;
			try {
				document = reader.read(in);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
	        Element root = document.getRootElement();
	        List<Element> childElements = root.elements();
	        logger.info("返回结果如下：");
	        for (Element child : childElements) {
	        	if("return_code".equals(child.getName())){
	        		return_code = child.getStringValue();
	           	}
	        	if("result_code".equals(child.getName())){
	        		result_code = child.getStringValue();
	           	}
	        	//logger.info(child.getName()+":"+child.getStringValue());
	        }
	        logger.info("状态返回码:"+return_code);
			if("SUCCESS".equals(return_code)){
				logger.info("业务结果:"+result_code);
				if("SUCCESS".equals(result_code)){
						result.setCode(Result.CODE_SUCCESS);
						result.setMsg("关闭订单");
				}else{
					result.setCode(Result.CODE_ERROR);
					result.setMsg("业务处理失败");
				}
			}else{
				result.setCode(Result.CODE_ERROR_PARAM);
				result.setMsg("请求参数错误");
			}
		} catch (Exception e) {
			result.setCode(Result.CODE_EXCEPTION);
			result.setMsg("发送异常");
		}finally{
			// 释放资源
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
		
		return result;
	}
}
