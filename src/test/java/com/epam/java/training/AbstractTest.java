package com.epam.java.training;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.epam.java.training.SpringBooRestServicesApiApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBooRestServicesApiApplication.class)
public abstract class AbstractTest {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}