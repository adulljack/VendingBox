package com.graduation.supermarket.face;

import net.sf.json.JSONObject;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FacebaiduMain {


    /**
     *人脸信息处理的主方法：向外部提供该方法，
     *通过传过来的标志进行相关方法的调用
     *imgStr:人脸信息，经base64转码后的
     *faceflag:人脸信息处理标志，如下
     *detect:人脸信息检测
     *add:向人脸库中增加人脸信息
     *recognition：人脸识别，判断指定人脸是否在人脸库中
     *update：修改库中已存在的人脸信息
     *delete：删除库中指定人脸信息
     *select：查询库中指定人脸信息
     * @throws Exception
     * @throws UnsupportedEncodingException
     */
    public void Faceinfomain(String faceflag,String imgStr,String uid,String userinfo) throws Exception{

        try {
            //人脸处理类
            FacebaiduDeal facedeal = new FacebaiduDeal();

            String imgParam = null;

            if(faceflag.equals("detect")||faceflag.equals("add")||faceflag.equals("recognition")||faceflag.equals("update")){

                if(imgStr==null){

                    throw new Exception("人脸信息不能为空！");
                }
                imgParam = URLEncoder.encode(imgStr, "UTF-8");
            }

            //人脸检测,imgParam
            if(faceflag.equals("detect")){

                facedeal.detect(imgParam);

            }
            //人脸信息入人脸库
            if(faceflag.equals("add")){
                //入库之前先检测下人脸信息是否合规
                facedeal.detect(imgParam);
                JSONObject jsonObject = facedeal.faceadd(imgParam, uid, userinfo);
                if(jsonObject==null){
                    throw new Exception("脸部信息入库失败！");
                }
                String errorcode = jsonObject.optString("error_code");
                if(!errorcode.equals("")){
                    String errormsg = jsonObject.optString("error_msg");
                    System.out.println("errormsg:"+errormsg);
                    throw new Exception("脸部信息入库失败！报错信息为："+errormsg);
                }
            }

            //人脸信息识别，判断指定人脸是否在人脸库中
            if(faceflag.equals("recognition")){
                JSONObject jsonjt = facedeal.facerecognition(imgParam);
                if(jsonjt.optJSONArray("result")!=null){
                    JSONObject jsonObjectd = JSONObject.fromObject(jsonjt.optJSONArray("result").get(0));
                    String group_id = jsonObjectd.optString("group_id");
                    String uidkc = jsonObjectd.optString("uid");
                    String user_info = jsonObjectd.optString("user_info");
                    String[] jsscores =  jsonObjectd.optString("scores").replace("[", "").replace("]","").split(",");
                    double scores = Double.valueOf(jsscores[0]);
                    if(scores<80){
                        throw new Exception("该人脸信息在人脸库中不存在！");
                    }
                    System.out.println("group_id:"+group_id);
                    System.out.println("uidkc:"+uidkc);
                    System.out.println("user_info:"+user_info);
                    System.out.println("scores:"+scores);
                }else{
                    String errorcode = jsonjt.optString("error_msg");
                    System.out.println("errorcode:"+errorcode);
                }
            }
            //人脸信息更新
            if(faceflag.equals("update")){
                //入库之前先检测下人脸信息是否合规
                facedeal.detect(imgParam);
                JSONObject jsonObject = facedeal.faceupdate(imgParam, userinfo,uid);
                String errorcode = jsonObject.optString("error_code");
                if(errorcode!=null){
                    String errormsg = jsonObject.optString("error_msg");
                    System.out.println("errormsg:"+errormsg);
                }
                jsonObject.optDouble("log_id");
            }
            //人脸信息删除
            if(faceflag.equals("delete")){
                JSONObject jsonObject = facedeal.facedelete(uid);
                String errorcode = jsonObject.optString("error_code");
                if(errorcode!=null){
                    String errormsg = jsonObject.optString("error_msg");
                    System.out.println("errormsg:"+errormsg);
                }
                jsonObject.optDouble("log_id");
            }
            //人脸信息查询
            if(faceflag.equals("select")){
                JSONObject sjson = facedeal.faceselect(uid);
                if(sjson.optJSONObject("result")!=null){
                    JSONObject jsonObjectd = JSONObject.fromObject(sjson.optJSONObject("result").get(String.valueOf(0)));
                    String group_id = jsonObjectd.optString("group_id");
                    String uidkc = jsonObjectd.optString("uid");
                    String user_info = jsonObjectd.optString("user_info");
                    System.out.println("group_id:"+group_id);
                    System.out.println("uidkc:"+uidkc);
                    System.out.println("user_info:"+user_info);
                }else{
                    String errorcode = sjson.optString("error_msg");
                    System.out.println("errorcode:"+errorcode);
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new Exception(ExceptionUtils.getStackTrace(e));
        }
    }
}