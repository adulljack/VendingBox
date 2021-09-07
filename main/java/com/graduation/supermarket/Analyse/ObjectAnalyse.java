package com.graduation.supermarket.Analyse;


import com.graduation.supermarket.Controller.ImgController;
import com.graduation.supermarket.face.util.GsonUtils;
import javafx.scene.shape.HLineTo;
import org.apache.jasper.tagplugins.jstl.core.Url;
import org.bytedeco.javacpp.annotation.Properties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
public class ObjectAnalyse
{


    public static URL uploadImgUrl;
    @Value("${url.uploadImgUrl}")
    public void setUploadImgUrl(URL uploadImgUrl){ObjectAnalyse.uploadImgUrl=uploadImgUrl;}
    public List<Integer> uploadFile(String fileName) {

        String result="";
        try {
            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            // 定义数据分隔线
            String BOUNDARY = "========7e31753050e48";
            // 服务器的域名
//            URL url = new URL("http://127.0.0.1:8011/vending/upload_pic");
            URL url =  uploadImgUrl;


            System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // 上传文件
            File file = new File(fileName);
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: multipart/form-data;name=\"pic\";filename=\"" + fileName
                    + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(new FileInputStream(
                    file));

            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                    .getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();

            //定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result+=line;
                System.out.println(result);
            }

        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        List<Integer> integerList = new ArrayList<>();
        if( result.length()>2 ) {
            result = result.substring(18, result.length() - 19);
            System.out.println(result);
            String[] arr = result.split(",");
            if (result.length() > 0) {
                for (String x : arr) {
                    integerList.add(x.charAt(0) - '0');
                }
            }
        }
        return integerList;
    }







    public HashMap<Integer, Integer> objectDetect(String image)
    {
        System.out.println("detect start");
//        List<Integer> integerList=getObjectAnalyse(image);
        List<Integer> integerList=uploadFile(image);
//        List<Integer> integerList = Arrays.asList(0,0,1,1, 2, 3);
        HashMap<Integer,Integer> result=new HashMap<>();
        for( int x:integerList ){
            if( result.containsKey(x) ){
                int value=result.get(x);
                result.replace(x,value,value+1);
            }
            else{
                result.put(x,1);
            }
        }
        return result;
    }

    public List<Integer> getObjectAnalyse(String imageName){
        String result="";
        String cmd = "cmd /c python yolov3_infer.py "+imageName;
        String dir = new File("").getAbsolutePath();
        dir = dir + "\\py";
        File directory = new File(dir);
        try {
            final Process process = Runtime.getRuntime().exec(cmd, null, directory);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            try {
                while ((line = in.readLine()) != null) {
                    result+=line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Integer> integerList = new ArrayList<>();
        if( result.length()>2 ) {
            result = result.substring(1, result.length() - 2);
            System.out.println(result);
            String[] arr = result.split(". ");

            for (String x : arr) {
                integerList.add(x.charAt(0) - '0'+1);
            }
        }
        return integerList;
    }
}
