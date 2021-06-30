package com.graduation.supermarket.Service;



import com.graduation.supermarket.face.FacebaiduDeal;
import com.graduation.supermarket.face.Setingmodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    FacebaiduDeal facebaiduDeal;
//    BaiduAIFace faceapi;
    @Autowired
    Setingmodel setingmodel;
    public Map<String,Object> searchface(StringBuffer imagebase64) throws IOException {
        String substring = imagebase64.substring(imagebase64.indexOf(",")+1, imagebase64.length());
        setingmodel.setImgpath(substring);
        setingmodel.setGroupID("face_test");
        System.out.println(substring);
        Map map = facebaiduDeal.FaceSearch(setingmodel);
        return map;

    }
}