package com.graduation.supermarket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SupermarketApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SupermarketApplicationTests {

	@Test
	public void contextLoads() {
	}

}

