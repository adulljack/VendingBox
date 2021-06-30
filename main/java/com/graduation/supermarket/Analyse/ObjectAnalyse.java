package com.graduation.supermarket.Analyse;


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectAnalyse
{
    public HashMap<Integer, Integer> objectDetect(String image)
    {
        System.out.println("detect start");
        List<Integer> integerList=getObjectAnalyse(image);
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
