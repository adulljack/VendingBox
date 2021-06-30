package com.graduation.supermarket.face;

import com.graduation.supermarket.face.util.HttpUtil;
import com.graduation.supermarket.face.util.GsonUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class FacebaiduDeal {

    private static String token = AuthService.getAuth();

    /**
     * 百度云AK
     */
    private static final String API_KEY = "xM3qh6n1TXYKPai4Q05fdOln";
    /**
     * 百度云SK
     */
    private static final String SECRET_KEY = "ytqqLRgeIknhizZCHq9gNvRSCec07lUY";
    /**
     * 获取access_token的接口地址
     */
    private static final String AUTH_HOST = "https://aip.baidubce.com/oauth/2.0/token?";
    /**
     * 进行人脸检测的接口地址
     */
    private static final String DETECT_HOST = "https://aip.baidubce.com/rest/2.0/face/v1/detect";
    /**
     * 进行人脸入人脸库的接口地址
     */
    private static final String FACEADD_HOST = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
    /**
     * 进行人脸识别的接口地址
     */
    private static final String FACRENZ_HOST = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
    /**
     * 进行人脸更新的接口地址
     */
    private static final String FACERGX_HOST = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/update";
    /**
     * 进行人脸删除的接口地址
     */
    private static final String FACEDELE_HOST = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";
    /**
     * 进行人脸信息查询的接口地址
     */
    private static final String FACESELECT_HOST = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/get";

    /**
     * 人脸探测
     * @return
     * @throws Exception
     */
    public void detect(String imgParam) throws Exception {
        JSONObject jsonObject = null;
        String param = "max_face_num=" + 1
                + "&face_fields="
                + "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities"
                + "&image=" + imgParam;
        String accessToken = getAuth();
        String result = HttpUtil.post(DETECT_HOST, accessToken, param);
        jsonObject = JSONObject.fromObject(result);
        JSONObject jsonObjectd = JSONObject.fromObject(jsonObject.optJSONArray("result").get(0));
        int blur = jsonObjectd.optInt("blur");//人脸模糊程度，范围[0~1]，0表示清晰，1表示模糊
        JSONObject jsontype = JSONObject.fromObject(JSONObject.fromObject(jsonObjectd.getString("qualities")).getString("type"));//真实人脸置信度，[0~1]，大于0.5可以判断为人脸
        double human = jsontype.getDouble("human");//真实人脸置信度，[0~1]，大于0.5可以判断为人脸
        //1表示人脸模糊
        if(blur==1){
            throw new Exception("人脸模糊！");
        }
        //是否是人类的脸
        if(human<0.5){
            throw new Exception("提供的人脸照片非人类的脸！");
        }
        System.out.println("blur:"+blur);
        System.out.println("human:"+human);
    }

    /**
     * 人脸信息入人脸库
     * @return
     * imgParam:人脸base64串
     * uid：人脸id（例如身份证等）
     * @throws Exception
     */
    public JSONObject faceadd(String imgParam,String uid,String userinfo) throws Exception {
        JSONObject jsonObject = null;
        if(userinfo==null){
            userinfo = uid;
        }
        String param = "uid=" + uid
                + "&group_id=face_test"
                + "&image=" + imgParam
                + "&user_info=" + userinfo
                + "&action_type=replace";

        String accessToken = getAuth();
        String result = HttpUtil.post(FACEADD_HOST, accessToken, param);

        jsonObject = JSONObject.fromObject(result);

        return jsonObject;
    }


    /**
     * 人脸信息识别，判断指定人脸是否存在库中
     * @param imgParam:人脸信息，base64转码后的串
     * @return
     */
    public JSONObject facerecognition(String imgParam) {
        JSONObject jsonObject = null;

        try {

            String param = "&group_id=face_test"
                    + "&image=" + imgParam
                    + "&user_top_num=1";

            String accessToken = getAuth();
            String result = HttpUtil.post(FACRENZ_HOST, accessToken, param);
            jsonObject = JSONObject.fromObject(result);
            return jsonObject;

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }


    /**
     * 人脸查找
     * @return
     */
    public Map FaceSearch(Setingmodel setingmodel) throws IOException {
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
//        byte[] bytes = Files.readAllBytes(Paths.get(setingmodel.getImgpath()));
//        String imagebase64 = Base64Util.encode(bytes);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", setingmodel.getImgpath());
            map.put("liveness_control", setingmodel.getLiveness_Control());
            map.put("group_id_list", setingmodel.getGroupID());
            map.put("image_type", setingmodel.getImage_Type());
            map.put("quality_control", setingmodel.getQuality_Control());
            String param = GsonUtils.toJson(map);
            String result = HttpUtil.post(url, token, "application/json", param);
            Map<String,Object> resultmaps = GsonUtils.fromJson(result, Map.class);
            System.out.println("cuowudaima"+resultmaps.get("error_code"));
            if(!resultmaps.get("error_code").toString().equals("222202.0")){
                String resultlist = resultmaps.get("result").toString();
                String substring = resultlist.substring(1, resultlist.length() - 1);
                String regEx="[\n`~!@#$%^&*()+|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
                String aa = "";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(substring);//这里把想要替换的字符串传进来
                String newString = m.replaceAll(aa).trim();
                String[] split = newString.split(",");
                split[1]=split[1].substring(10, split[1].length());
                String face_token=split[0].substring(11,split[0].length());
                String group_id=split[1].substring(9,split[1].length());
                String user_id=split[2].substring(8,split[2].length());
                String user_info=split[3].substring(10,split[3].length());
                String score=split[4].substring(6,split[4].length());
                System.out.println(face_token);
                resultmaps.put("face_token",face_token);
                resultmaps.put("group_id",group_id);
                resultmaps.put("user_id",user_id);
                resultmaps.put("user_info",user_info);
                resultmaps.put("score",score);
                return resultmaps;

            }else {
                System.out.println("失败分支");
                return resultmaps;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 人脸信息库存更新
     * @param imgParam
     * @param userinfo
     * @return
     */
    public JSONObject faceupdate(String imgParam,String userinfo,String uid) {
        JSONObject jsonObject = null;

        try {

            String param = "&uid="+uid
                    + "&image=" + imgParam
                    + "&user_info=" + userinfo
                    +"&group_id=face_test"
                    + "&action_type=replace";

            String accessToken = getAuth();
            String result = HttpUtil.post(FACERGX_HOST, accessToken, param);
            jsonObject = JSONObject.fromObject(result);
            return jsonObject;

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 指定人脸信息删除
     * @param uid
     * @return
     */
    public JSONObject facedelete(String uid) {

        JSONObject jsonObject = null;

        try {
            String param = "&uid="+uid
                    +"&group_id=face_test";

            String accessToken = getAuth();
            String result = HttpUtil.post(FACEDELE_HOST, accessToken, param);
            jsonObject = JSONObject.fromObject(result);
            return jsonObject;

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
    public JSONObject faceselect(String uid) {

        JSONObject jsonObject = null;

        try {
            String param = "&uid="+uid
                    +"&group_id=face_test";

            String accessToken = getAuth();
            String result = HttpUtil.post(FACESELECT_HOST, accessToken, param);
            jsonObject = JSONObject.fromObject(result);

            return jsonObject;

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取权限token
     * @return
     */
    public  String getAuth(){
        // 获取token地址
        String getAccessTokenUrl = AUTH_HOST
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + API_KEY
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + SECRET_KEY;
        JSONObject jsonObject = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            jsonObject = JSONObject.fromObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}