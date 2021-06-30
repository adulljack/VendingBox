package com.graduation.supermarket;

import org.bytedeco.javacv.FrameFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Test
{
    public static void main(String args[]) {
        String result="";
        String cmd = "cmd /c python yolov3_infer.py test3.jpg";
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
        System.out.println("result"+": "+result);
        List<Integer> integerList=new ArrayList<>();
        if(result.length()>2) {
            result = result.substring(1, result.length() - 2);
            System.out.println(result);
            String[] arr = result.split(". ");
            System.out.println(result);
            for (String x : arr) {
                integerList.add(x.charAt(0) - '0');
            }
        }
        for (int x : integerList) {
            System.out.println(x);
        }
//        String cmd="explorer index.html";
//        try {
//            final Process process = Runtime.getRuntime().exec(cmd);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(Test.class.getClassLoader().getResource("").getFile().toString());
    }
}
