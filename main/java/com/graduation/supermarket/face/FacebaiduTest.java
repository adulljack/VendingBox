package com.graduation.supermarket.face;

import java.io.IOException;

import com.baidu.aip.util.Base64Util;
import com.graduation.supermarket.face.util.FileUtil;

public class FacebaiduTest {

    public static void main(String[] args) {

        try {
            byte[] imgData = FileUtil.readFileByBytes("C:\\Users\\CodeTyro\\Pictures\\Saved Pictures\\3.jpg");
            String imgStr = Base64Util.encode(imgData);
            FacebaiduMain facedeal = new FacebaiduMain();
            facedeal.Faceinfomain("recognition", imgStr, "123", "admin");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
