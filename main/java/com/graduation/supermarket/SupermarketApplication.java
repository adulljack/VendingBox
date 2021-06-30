package com.graduation.supermarket;

import com.graduation.supermarket.AssitClass.CameraThread;
import org.mybatis.spring.annotation.MapperScan;
import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@MapperScan("com.graduation.supermarket.dao")
public class SupermarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupermarketApplication.class, args);
		CameraThread cameraThread=new CameraThread();
		cameraThread.setThreadName("camera01");
		cameraThread.start();
	}

}

