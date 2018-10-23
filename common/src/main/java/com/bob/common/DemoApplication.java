package com.bob.common;

import com.bob.common.service.DoSTH;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication  {
    org.slf4j.Logger Logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    DoSTH doSTH;

    public static void main(String[] args) {
        try {
            SpringApplication.run(DemoApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    @Override
    public void run(String... args) throws Exception {



    }*/
}
