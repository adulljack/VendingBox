package com.graduation.supermarket.AssitClass;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_videoio;

public class CameraThread extends Thread{

    private Thread t;

    private String threadName;

    public static boolean close=false;

    public static opencv_core.Mat image=null;

    public void start() {
        System.out.println("start " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public void run()
    {
        opencv_videoio.VideoCapture camera=new opencv_videoio.VideoCapture();
        camera.open(0);
        try {
            System.out.println("初始化完毕,请开始购物");
        } catch (Exception e) {
            e.printStackTrace();
        }
        while( !close )
        {
            if( !camera.isOpened() )
            {
                System.out.println("camera error");
                break;
            }
            opencv_core.Mat frame=new opencv_core.Mat();
            camera.read(frame);
            image=frame;
//            System.out.println(System.currentTimeMillis());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public static void closeCamera(){
        close=true;
    }

    public static opencv_core.Mat getImage(){
        return image;
    }

}
